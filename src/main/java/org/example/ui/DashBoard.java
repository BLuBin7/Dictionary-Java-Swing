package org.example.ui;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import org.example.dao.AudioUKDao;
import org.example.dao.AudioUSDao;
import org.example.dao.EnglishDao;
import org.example.model.AudioUK;
import org.example.model.AudioUS;
import org.example.model.English;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class DashBoard extends JFrame {
    private JPanel contentPane;

    private JTextField inputWord;
    private JTextField inputDefinition;
    private AdvancedPlayer player;

    private JButton importButton;
    private JButton playButton;
    private JTextField filePathField;

    private Thread playerThread;
    private JTextField pathAudioUS;
    private JTextField pathAudioUK;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DashBoard frame = new DashBoard();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public DashBoard() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 601, 391);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Nhập từ");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setBounds(10, 46, 99, 14);
        contentPane.add(lblNewLabel);

        inputWord = new JTextField();
        inputWord.setBounds(119, 47, 167, 20);
        contentPane.add(inputWord);
        inputWord.setColumns(10);

        JButton btnCreate = new JButton("Create");
        btnCreate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCreate.setBounds(452, 307, 111, 35);
        contentPane.add(btnCreate);

        JLabel lblNewLabel_1 = new JLabel("Nhập nghĩa");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1.setBounds(10, 84, 120, 20);
        contentPane.add(lblNewLabel_1);

        inputDefinition = new JTextField();
        inputDefinition.setBounds(119, 88, 167, 20);
        contentPane.add(inputDefinition);
        inputDefinition.setColumns(10);

        JButton btnNewButton_1 = new JButton("Import MP3");
        btnNewButton_1.setBounds(399, 131, 89, 23);
        contentPane.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Play");
        btnNewButton_2.setBounds(486, 131, 89, 23);
        contentPane.add(btnNewButton_2);

        pathAudioUS = new JTextField();
        pathAudioUS.setBounds(206, 133, 175, 20);
        contentPane.add(pathAudioUS);
        pathAudioUS.setColumns(10);

        JLabel lblNewLabel_1_1 = new JLabel("Thêm Phiên âm Anh Mỹ");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1_1.setBounds(8, 129, 201, 20);
        contentPane.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_3 = new JLabel("Nhập Thẻ Brand");
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1_3.setBounds(10, 255, 142, 20);
        contentPane.add(lblNewLabel_1_3);

        JLabel lblNewLabel_1_1_1_1 = new JLabel("Thêm Phiên âm Anh Anh");
        lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1_1_1_1.setBounds(10, 185, 225, 20);
        contentPane.add(lblNewLabel_1_1_1_1);

        pathAudioUK = new JTextField();
        pathAudioUK.setColumns(10);
        pathAudioUK.setBounds(224, 185, 175, 20);
        contentPane.add(pathAudioUK);

        JButton btnNewButton_1_1 = new JButton("Import MP3");
        btnNewButton_1_1.setBounds(410, 187, 89, 23);
        contentPane.add(btnNewButton_1_1);

        JButton btnNewButton_2_1 = new JButton("Play");
        btnNewButton_2_1.setBounds(486, 187, 89, 23);
        contentPane.add(btnNewButton_2_1);

        String[] options = {"Option 1", "Option 2", "Option 3"};
        JComboBox<String> optionBrand = new JComboBox<>(options);
        optionBrand.setBounds(162, 257, 142, 22);
        contentPane.add(optionBrand);

        JLabel optionPOS = new JLabel("Nhập từ loại");
        optionPOS.setFont(new Font("Tahoma", Font.PLAIN, 18));
        optionPOS.setBounds(10, 307, 142, 20);
        contentPane.add(optionPOS);

        String[] options2 = {"Option 1", "Option 2", "Option 3"};
        JComboBox<String> comboBox_1 = new JComboBox<>(options);
        comboBox_1.setBounds(164, 307, 122, 21);
        contentPane.add(comboBox_1);

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData(importAudioUS(),importAudioUK());
            }
        });

        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importMP3();
            }
        });

        btnNewButton_1_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                playAudio(AudioUSDao.getInstance().findbyID(1));
                playMP3();;
            }
        });

        filePathField = new JTextField();

        filePathField.setBounds(131, 114, 86, 20);
        contentPane.add(filePathField);
        filePathField.setColumns(10);

        contentPane.add(importButton);
        contentPane.add(filePathField);
        contentPane.add(playButton);

        this.setVisible(true);
    }


    public void importMP3() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(java.io.File f) {
                return f.getName().toLowerCase().endsWith(".mp3") || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "MP3 Files (*.mp3)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            filePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void playMP3() {
        String filePath = filePathField.getText().trim();
        if (!filePath.isEmpty()) {
            if (player != null) {
                player.close();
            }

            try {
                FileInputStream fileInputStream = new FileInputStream(filePath);
                player = new AdvancedPlayer(fileInputStream);

                playerThread = new Thread(() -> {
                    try {
                        player.setPlayBackListener(new PlaybackListener() {
                            @Override
                            public void playbackFinished(PlaybackEvent evt) {
                                playButton.setEnabled(true);
                            }
                        });

                        playButton.setEnabled(false);
                        player.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                });

                playerThread.start();
            } catch (IOException | JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

    private void playAudio(AudioUS audioUS) {
        if (player != null) {
            player.close();
        }

        try {
            URL url = new URL(audioUS.getAudio_source());
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

    private void insertData(AudioUS audioUS, AudioUK audioUK) {
        English english = new English();
        english.setName(inputWord.getText());
        english.setAudioUS_id(audioUS.getId());
        english.setAudioUK_id(audioUK.getId());

        try {
            EnglishDao.getInstance().insertword(english,audioUS,audioUK);
            JOptionPane.showMessageDialog(this, "Data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inserting data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private AudioUS importAudioUS() {
        AudioUS audioUS = new AudioUS();
        String filePath = filePathField.getText().trim();

        try {
            String audioData = Arrays.toString(Files.readAllBytes(Paths.get(filePath)));
            audioUS.setAudio_source(audioData);

            audioUS.setId(1);

            AudioUSDao.getInstance().insert(audioUS);
            JOptionPane.showMessageDialog(this, "Data inserted successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inserting audio data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        AudioUSDao.getInstance().insert(audioUS);
        return audioUS;
    }

    private AudioUK importAudioUK() {
        AudioUK audioUK = new AudioUK();
        String filePath = filePathField.getText().trim();

        try {
            byte[] audioData = Files.readAllBytes(Paths.get(filePath));
            audioUK.setAudio_source("no");

            audioUK.setId(1);

            AudioUKDao.getInstance().insert(audioUK);
            JOptionPane.showMessageDialog(this, "Data inserted successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inserting audio data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        AudioUKDao.getInstance().insert(audioUK);
        return audioUK;
    }
}
