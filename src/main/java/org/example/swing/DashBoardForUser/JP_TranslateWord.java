package org.example.swing.DashBoardForUser;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import org.example.dao.EnglishDao;
import org.example.dao.TagDao;
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

public class JP_TranslateWord extends JPanel {
    private JTextField inputWord;
    private AdvancedPlayer player;
    private Thread playerThread;

    private JLabel pathAudioUS;
    private JLabel pathAudioUK;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    /**
     * Create the panel.
     */
    int userNameId;

    public void setUserNameId(int userNameId) {
        this.userNameId = userNameId;
    }
    private JLabel lblVietNamese;
    private JLabel lblTag;
    private JButton btnChange;
    public JP_TranslateWord(int userNameID) {
        setLayout(null);

        JLabel lblEnglish = new JLabel("New label");
        lblEnglish.setHorizontalAlignment(SwingConstants.LEFT);
        lblEnglish.setFont(new Font("Calibri", Font.BOLD, 25));
        lblEnglish.setBounds(28, 84, 150, 39);
        add(lblEnglish);
        lblEnglish.setVisible(false);

        JTextField inputWord = new JTextField();
        inputWord.setFont(new Font("Calibri", Font.PLAIN, 19));
        inputWord.setBounds(86, 42, 304, 32);
        add(inputWord);
        inputWord.setColumns(10);

        JPanel searchPanel = new JPanel();

        JButton btnSearch = new JButton("Search");
        btnSearch.setBackground(new Color(100, 180, 255));
//        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);

        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnSearch.setBounds(400, 39, 116, 33);
        add(btnSearch);

        JLabel lblVietNamese = new JLabel("New label");
        lblVietNamese.setHorizontalTextPosition(SwingConstants.LEFT);
        lblVietNamese.setHorizontalAlignment(SwingConstants.LEFT);
        lblVietNamese.setFont(new Font("Arial", Font.PLAIN, 23));
        lblVietNamese.setBounds(28, 144, 182, 39);
        add(lblVietNamese);

        lblTag = new JLabel();

        lblTag.setHorizontalAlignment(SwingConstants.LEFT);
        lblTag.setFont(new Font("Dialog", Font.ITALIC, 10));
        lblTag.setBounds(28, 120, 90, 23);
        add(lblTag);

        JLabel lblPOS = new JLabel("New label");
        lblPOS.setHorizontalAlignment(SwingConstants.LEFT);
        lblPOS.setFont(new Font("Dialog", Font.ITALIC, 16));
        lblPOS.setBounds(105, 117, 137, 23);
        add(lblPOS);

        JButton btnAudioUS = new JButton();
        btnAudioUS.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAudioUS.setBounds(189, 83, 57, 32);
        add(btnAudioUS);
        ImageIcon originalIcon_US = new ImageIcon("src/main/java/org/example/image/audio/audio_us.png");
        Image originalImage_US = originalIcon_US.getImage();
        Image resizedImage_US = originalImage_US.getScaledInstance(42, 20, Image.SCALE_SMOOTH);

        ImageIcon resizedIcon_US = new ImageIcon(resizedImage_US);
        btnAudioUS.setIcon(resizedIcon_US);

        JButton btnAudioUK = new JButton();
        btnAudioUK.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAudioUK.setBounds(266, 83, 57, 32);

        ImageIcon originalIcon_UK = new ImageIcon("src/main/java/org/example/image/audio/audio_uk.png");
        Image originalImage = originalIcon_UK.getImage();
        Image resizedImage = originalImage.getScaledInstance(42, 20, Image.SCALE_SMOOTH);

        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        btnAudioUK.setIcon(resizedIcon);


        add(btnAudioUK);

        pathAudioUS = new JLabel("New label");
        pathAudioUS.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
        pathAudioUS.setBounds(189, 471, 100, 13);
        add(pathAudioUS);
        pathAudioUS.setVisible(false);

        pathAudioUK = new JLabel("New label");
        pathAudioUK.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
        pathAudioUK.setBounds(63, 471, 212, 13);
        add(pathAudioUK);
        pathAudioUK.setVisible(false);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(41, 165, 540, 245);

        JButton btnNewButton = new JButton("Add to Favorite");
        btnNewButton.setFont(new Font("Arial", Font.BOLD, 16));
        btnNewButton.setBounds(434, 460, 161, 32);
        add(btnNewButton);

        JLabel lblNewLabel_3 = new JLabel("Search From English");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel_3.setBounds(173, 6, 224, 23);
        add(lblNewLabel_3);

        lblNewLabel_2 = new JLabel("New label");
        lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_2.setFont(new Font("Arial", Font.ITALIC, 18));
//        lblNewLabel_2.setBounds(32, 183, 540, 272);
//        add(lblNewLabel_2);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(28, 183, 567, 263);
        add(scrollPane);
        scrollPane.setViewportView(lblNewLabel_2);

//        JButton btnAddToFavorite = new JButton("Add to Favorite");
//        btnAddToFavorite.setFont(new Font("Arial", Font.BOLD, 16));
//        btnAddToFavorite.setBounds(434, 465, 161, 27);
//        add(btnAddToFavorite);
//
//
//        btnAddToFavorite.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                System.out.println(userNameId);
//                AddToFavorite(userNameId,inputWord.getText());
//            }
//
//
//        });

        btnChange = new JButton("EN");
        btnChange.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnChange.setBackground(new Color(153, 153,0 ));
//        btnChange.setForeground(new Color(202, 181, 181));
        btnChange.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnChange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(btnChange.getText().equals("EN")){
                    btnChange.setText("VI");
                }else {
                    btnChange.setText("EN");
                }
            }
        });
        btnChange.setBounds(522, 47, 57, 21);
        add(btnChange);

        lblVietNamese.setVisible(false);
        lblTag.setVisible(false);
        lblPOS.setVisible(false);
        btnAudioUS.setVisible(false);
        btnAudioUK.setVisible(false);
        lblNewLabel_2.setVisible(false);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(btnChange.getText().equals("EN")){
                    findWord(inputWord, lblEnglish, lblVietNamese, lblTag,
                            lblPOS, pathAudioUS, pathAudioUK, btnAudioUS, btnAudioUK,lblNewLabel_2);
                }else {
                    EnglishDao.getInstance().findWordFromSearch(inputWord,lblEnglish,lblVietNamese,lblTag,
                            lblPOS,pathAudioUS,pathAudioUK,btnAudioUS,btnAudioUK,lblNewLabel_2);
                }

            }
        });

        btnAudioUS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pathAudioUS.getText().contains("http")) {
                    playAudioFromURL(pathAudioUS.getText());
                } else {
                    playAudioFormFile(pathAudioUS.getText(), btnAudioUS);
                }
            }
        });
        btnAudioUK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pathAudioUS.getText().contains("http")) {
                    playAudioFromURL(pathAudioUK.getText());
                } else {
                    playAudioFormFile(pathAudioUK.getText(), btnAudioUK);
                }
            }
        });
    }


    Connection connection = JDBCUtil.getConnection();

    public void findWord(JTextField inputWord, JLabel lblEnglish, JLabel lblVietNamese,
                         JLabel lblTag, JLabel lblPOS, JLabel pathAudioUS, JLabel pathAudioUK
            , JButton btnAudioUS, JButton btnAudioUK,JLabel description) {
        String query = "SELECT e.word, vn.word, us.audio_source, " +
                "uk.audio_source, tags.brand_tag_name, " +
                "tags.Part_Of_Speech_tag_name,de.description " +
                "FROM definition de " +
                "JOIN english e ON de.english_id = e.id " +
                "JOIN audio_us us ON e.audio_id_us = us.id " +
                "JOIN audio_uk uk ON e.audio_id_uk = uk.id " +
                "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                "JOIN tags tags ON tags.id = e.id " +
                "WHERE e.word = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, inputWord.getText());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                lblEnglish.setText(rs.getString(1));
                lblVietNamese.setText(rs.getString(2));
                pathAudioUS.setText(rs.getString(3));
                pathAudioUK.setText(rs.getString(4));
//                lblTag.setText(rs.getString(5));
                lblPOS.setText(rs.getString(6));
                description.setText("<html>" +rs.getString(7)+ "</html>");

                // Set visible for labels
                TagDao.getInstance().setImageforBrandTag(rs.getString(5),lblTag);
                lblVietNamese.setVisible(true);
                lblEnglish.setVisible(true);
                lblTag.setVisible(true);
                lblPOS.setVisible(true);

                btnAudioUS.setVisible(true);
                btnAudioUK.setVisible(true);
                lblVietNamese.setVisible(true);
                lblEnglish.setVisible(true);
                lblTag.setVisible(true);
                lblPOS.setVisible(true);
                btnAudioUS.setVisible(true);
                btnAudioUK.setVisible(true);
                lblNewLabel_2.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Word not found: " + inputWord.getText(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void playAudioFormFile(String filePath, JButton btnAudio) {
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

    public void AddToFavorite(int userId,String word) {
        String query = "INSERT INTO favorite_word (user_id,word_id) values(?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, word);
            int rs = preparedStatement.executeUpdate();
            if (rs>0) {
                JOptionPane.showMessageDialog(this, "Add successfully: " + word, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
