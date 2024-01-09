package org.example.controller;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import org.example.dao.*;
import org.example.model.*;
//import org.example.ui.DashBoard;
import org.example.ui.DashBoard1;
import org.example.ui.Home;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class AudioController {
    Home dashBoard = Home.getInstance();
    AdvancedPlayer player = dashBoard.getPlayer();
    private Thread thread = dashBoard.getPlayerThread();
    private JTextField pathAudioUS = dashBoard.getPathAudioUS();
    private JTextField pathAudioUK = dashBoard.getPathAudioUK();
    private JTextField inputWord = dashBoard.getInputWord();
    private JTextField inputDefinition = dashBoard.getInputDefinition();
    private static AudioController Instance;

    public AudioController(){}
    public static AudioController getInstance(){
        if (Instance == null) {
            return new AudioController();
        }
        return Instance;
    }

    private void setupEventHandlers() {
        // Xử lý các sự kiện liên quan đến âm thanh tại đây
        // Ví dụ: Gọi các phương thức từ DashBoard khi cần
//        dashBoard.getPlayButton().addActionListener(e -> playAudio());
        dashBoard.getImportButton().addActionListener(e -> importMP3());
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

        int result = fileChooser.showOpenDialog(dashBoard);
        if (result == JFileChooser.APPROVE_OPTION) {
            dashBoard.getPathAudioUS().setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    public void playAudioUS() {
        String filePath = dashBoard.getPathAudioUS().getText().trim();
        if (!filePath.isEmpty()) {
            if (dashBoard.getPlayer() != null) {
                dashBoard.getPlayer().close();
            }

            try {
                FileInputStream fileInputStream = new FileInputStream(filePath);
                dashBoard.setPlayer(new AdvancedPlayer(fileInputStream));

                dashBoard.setPlayerThread(new Thread(() -> {
                    try {
                        dashBoard.getPlayer().setPlayBackListener(new PlaybackListener() {
                            @Override
                            public void playbackFinished(PlaybackEvent evt) {
                                dashBoard.getBtnPlayAudioUS().setEnabled(true);
                            }
                        });

                        dashBoard.getBtnPlayAudioUS().setEnabled(false);
                        dashBoard.getPlayer().play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }));

                dashBoard.getPlayerThread().start();
            } catch (IOException | JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }


    public void playAudioUK() {
        String filePath = dashBoard.getPathAudioUS().getText().trim();
        if (!filePath.isEmpty()) {
            if (dashBoard.getPlayer() != null) {
                dashBoard.getPlayer().close();
            }

            try {
                FileInputStream fileInputStream = new FileInputStream(filePath);
                dashBoard.setPlayer(new AdvancedPlayer(fileInputStream));

                dashBoard.setPlayerThread(new Thread(() -> {
                    try {
                        dashBoard.getPlayer().setPlayBackListener(new PlaybackListener() {
                            @Override
                            public void playbackFinished(PlaybackEvent evt) {
                                dashBoard.getBtnPlayAudioUK().setEnabled(true);
                            }
                        });

                        dashBoard.getBtnPlayAudioUK().setEnabled(false);
                        dashBoard.getPlayer().play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }));

                dashBoard.getPlayerThread().start();
            } catch (IOException | JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

    public void playAudio(AudioUS audioUS) {
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

    public void insertData(AudioUS audioUS, AudioUK audioUK,Object Brand, Object POS) {
        English english = new English();
        english.setName(inputWord.getText());
        english.setAudioUS_id(audioUS.getId());
        english.setAudioUK_id(audioUK.getId());
        VietNamese VietNamese = new VietNamese();
        VietNamese.setWord(inputDefinition.getText());

        try {
            int english_id = EnglishDao.getInstance().insertword(english,audioUS,audioUK);
            int vietnamese_id = VietNameseDao.getInstance().insert(VietNamese);
            EnglishDao.getInstance().insertDefinition(english_id ,vietnamese_id);
            TagDao.getInstance().insert(english_id,Brand, POS);
            JOptionPane.showMessageDialog(dashBoard , "Data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(dashBoard , "Error inserting data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public AudioUS importAudioUS() {
        AudioUS audioUS = new AudioUS();
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

        int result = fileChooser.showOpenDialog(dashBoard);
        if (result == JFileChooser.APPROVE_OPTION) {
            dashBoard.getPathAudioUS().setText(fileChooser.getSelectedFile().getAbsolutePath());
            audioUS.setAudio_source(fileChooser.getSelectedFile().getAbsolutePath());
        }
        return audioUS;
    }

    public AudioUK importAudioUK() {
        AudioUK audioUK = new AudioUK();
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

        int result = fileChooser.showOpenDialog(dashBoard);
        if (result == JFileChooser.APPROVE_OPTION) {
            dashBoard.getPathAudioUK().setText(fileChooser.getSelectedFile().getAbsolutePath());
            audioUK.setAudio_source(fileChooser.getSelectedFile().getAbsolutePath());
        }
        return audioUK;
    }

}