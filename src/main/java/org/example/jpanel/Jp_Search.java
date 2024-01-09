package org.example.jpanel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import org.example.util.JDBCUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jp_Search extends JPanel {
    private JTextField inputWord;
    private AdvancedPlayer player;
    private Thread playerThread;

    private JLabel pathAudioUS;
    private JLabel pathAudioUK;

    /**
     * Create the panel.
     */
    public Jp_Search() {
        setLayout(null);

        JLabel lblEnglish = new JLabel("New label");
        lblEnglish.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
        lblEnglish.setBounds(63, 58, 128, 13);
        add(lblEnglish);
        lblEnglish.setVisible(false);

        inputWord = new JTextField();
        inputWord.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
        inputWord.setBounds(149, 5, 213, 23);
        add(inputWord);
        inputWord.setColumns(10);

        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnSearch.setBounds(360, 7, 116, 21);
        add(btnSearch);

        JLabel lblVietNamese = new JLabel("New label");
        lblVietNamese.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
        lblVietNamese.setBounds(63, 124, 241, 13);
        add(lblVietNamese);

        JLabel lblTag = new JLabel("New label");
        lblTag.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
        lblTag.setBounds(63, 85, 143, 13);
        add(lblTag);

        JButton btnAudioUS = new JButton("New button");
        btnAudioUS.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAudioUS.setBounds(233, 56, 105, 19);
        add(btnAudioUS);

        JButton btnAudioUK = new JButton("New button");
        btnAudioUK.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAudioUK.setBounds(371, 57, 105, 17);
        add(btnAudioUK);

        JLabel lblPOS = new JLabel("New label");
        lblPOS.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
        lblPOS.setBounds(243, 85, 155, 13);
        add(lblPOS);

        pathAudioUS = new JLabel("New label");
        pathAudioUS.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
        pathAudioUS.setBounds(204, 338, 100, 13);
        add(pathAudioUS);
        pathAudioUS.setVisible(false);

        pathAudioUK = new JLabel("New label");
        pathAudioUK.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
        pathAudioUK.setBounds(92, 378, 212, 13);
        add(pathAudioUK);
        pathAudioUK.setVisible(false);

        lblVietNamese.setVisible(false);
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
            if (rs.next()) {
                lblEnglish.setText(rs.getString(1));
                lblVietNamese.setText(rs.getString(2));
                pathAudioUS.setText(rs.getString(3));
                pathAudioUK.setText(rs.getString(4));
                lblTag.setText(rs.getString(5));
                lblPOS.setText(rs.getString(6));

                lblVietNamese.setVisible(true);
                lblEnglish.setVisible(true);
                lblTag.setVisible(true);
                lblPOS.setVisible(true);
                btnAudioUS.setVisible(true);
                btnAudioUK.setVisible(true);
            } else {
                // Không tìm thấy từ
                JOptionPane.showMessageDialog(this, "Không tìm thấy từ: " + inputWord.getText(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
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
