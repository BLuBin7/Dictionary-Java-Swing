package org.example.ui;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import org.example.controller.AudioController;
import org.example.controller.TagController;
import org.example.model.AudioUK;
import org.example.model.AudioUS;
import org.example.util.JDBCUtil;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;

public class Test extends JFrame {
    private JPanel contentPane;
    private JTextField inputWord;
    private AdvancedPlayer player;
    private Thread playerThread;

    private JLabel pathAudioUS;
    private JLabel pathAudioUK;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Test frame = new Test();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public Test() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 601, 391);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        inputWord = new JTextField();
        inputWord.setBounds(137, 10, 190, 19);
        getContentPane().add(inputWord);
        inputWord.setColumns(10);

        JButton btnSearch = new JButton("New button");
        btnSearch.setBounds(360, 10, 83, 21);
        getContentPane().add(btnSearch);

        JLabel lblEnglish = new JLabel("New label");
        lblEnglish.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblEnglish.setBounds(45, 61, 90, 19);
        getContentPane().add(lblEnglish);

        JLabel lblVietNamese = new JLabel("New label");
        lblVietNamese.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblVietNamese.setBounds(45, 97, 90, 19);
        getContentPane().add(lblVietNamese);

        JLabel lblTag = new JLabel("New label");
        lblTag.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblTag.setBounds(45, 135, 90, 19);
        getContentPane().add(lblTag);

        JButton btnAudioUS = new JButton("New button");
        btnAudioUS.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAudioUS.setBounds(172, 63, 105, 19);
        getContentPane().add(btnAudioUS);

        JButton btnAudioUK = new JButton("New button");
        btnAudioUK.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAudioUK.setBounds(172, 99, 105, 17);
        getContentPane().add(btnAudioUK);

        JLabel lblPOS = new JLabel("New label");
        lblPOS.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblPOS.setBounds(45, 177, 90, 19);
        getContentPane().add(lblPOS);

        pathAudioUS = new JLabel("New label");
        pathAudioUS.setFont(new Font("Tahoma", Font.PLAIN, 18));
        pathAudioUS.setBounds(50, 177, 90, 19);
        getContentPane().add(pathAudioUS);
        pathAudioUS.setVisible(false);

        pathAudioUK = new JLabel("New label");
        pathAudioUK.setFont(new Font("Tahoma", Font.PLAIN, 18));
        pathAudioUK.setBounds(52, 177, 90, 19);
        getContentPane().add(pathAudioUK);
        pathAudioUK.setVisible(false);

        lblVietNamese.setVisible(false);
        lblEnglish.setVisible(false);
        lblTag.setVisible(false);
        lblPOS.setVisible(false);
        btnAudioUS.setVisible(false);
        btnAudioUK.setVisible(false);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findWord(inputWord,lblEnglish,lblVietNamese,lblTag,
                        lblPOS,pathAudioUS,pathAudioUK,btnAudioUS,btnAudioUK);
            }
        });

        btnAudioUS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pathAudioUS.getText().contains("http")){
                    playAudioFromURL(pathAudioUS.getText());
                }
                else {
                    playAudioFormFile(pathAudioUS.getText() ,btnAudioUS);
                }
            }
        });
        btnAudioUK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pathAudioUS.getText().contains("http")){
                    playAudioFromURL(pathAudioUK.getText());
                }
                else {
                    playAudioFormFile(pathAudioUK.getText() ,btnAudioUK);
                }
            }
        });
    }

    Connection connection = JDBCUtil.getConnection();
    public void findWord(JTextField inputWord,JLabel lblEnglish, JLabel lblVietNamese,
                    JLabel lblTag,JLabel lblPOS,JLabel pathAudioUS, JLabel pathAudioUK
                    ,JButton btnAudioUS, JButton btnAudioUK){
        /*String query = "SELECT e.name, vn.name, us.audio_source," +
                "uk.audio_source, tags.brand_tag_name," +
                "tags.Part_Of_Speech_tag_name " +
                "FROM definition de, english e, " +
                "audio_us us, audio_uk uk, " +
                "vietnamese vn, tags tags " +
                "WHERE de.english_id = e.id AND " +
                "e.audio_id_us = us.id AND " +
                "e.audio_id_uk = uk.id AND " +
                "vn.id = de.vietnamese_id AND " +
                "tags.id = e.id AND " +
                "e.name = ?";*/
        String query = "SELECT e.name, vn.name, us.audio_source, " +
                "uk.audio_source, tags.brand_tag_name, " +
                "tags.Part_Of_Speech_tag_name " +
                "FROM definition de " +
                "JOIN english e ON de.english_id = e.id " +
                "JOIN audio_us us ON e.audio_id_us = us.id " +
                "JOIN audio_uk uk ON e.audio_id_uk = uk.id " +
                "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                "JOIN tags tags ON tags.id = e.id " +
                "WHERE e.name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, inputWord.getText());

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                lblEnglish.setText(rs.getString(1));
                lblVietNamese.setText(rs.getString(2));
                pathAudioUS.setText(rs.getString(3));
                pathAudioUK.setText(rs.getString(4));
                lblTag.setText(rs.getString(5));
                lblPOS.setText(rs.getString(6));
            }
            lblVietNamese.setVisible(true);
            lblEnglish.setVisible(true);
            lblTag.setVisible(true);
            lblPOS.setVisible(true);
            btnAudioUS.setVisible(true);
            btnAudioUK.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void playAudioFormFile(String filePath,JButton btnAudio) {
        if (!filePath.isEmpty()) {
            if (player != null) {
                player.close();
            }

            try {
                FileInputStream fileInputStream = new FileInputStream(filePath);
                player = new AdvancedPlayer(fileInputStream);

                playerThread = (new Thread(() -> {
                    try {
                       player.setPlayBackListener(new PlaybackListener() {
                            @Override
                            public void playbackFinished(PlaybackEvent evt) {
                                btnAudio.setEnabled(true);
                            }
                        });

                        btnAudio.setEnabled(false);
                        this.player.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }));

                playerThread.start();
            } catch (IOException | JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

    public void playAudioFromURL(String audio_source) {
        if (player != null) {
            player.close();
        }

        try {
            URL url = new URL(audio_source);
            InputStream inputStream = url.openStream();

            player = new AdvancedPlayer(inputStream);

//            playButton.setEnabled(false);
            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException | JavaLayerException e) {
            e.printStackTrace();
        }
    }


}
