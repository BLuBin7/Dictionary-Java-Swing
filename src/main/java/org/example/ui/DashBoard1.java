package org.example.ui;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import org.example.controller.AudioController;
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

public class DashBoard1 extends JFrame {
    private JPanel contentPane;
    private JTextField inputWord;
    private JTextField inputDefinition;
    private JButton importButton;
    private JButton playButton;
    private JTextField filePathField;
    private Thread playerThread;
    private JTextField pathAudioUS;
    private JTextField pathAudioUK;
    private AdvancedPlayer player;


    public void setContentPane(JPanel contentPane) {
        this.contentPane = contentPane;
    }

    public JTextField getInputWord() {
        return inputWord;
    }

    public void setInputWord(JTextField inputWord) {
        this.inputWord = inputWord;
    }

    public JTextField getInputDefinition() {
        return inputDefinition;
    }

    public void setInputDefinition(JTextField inputDefinition) {
        this.inputDefinition = inputDefinition;
    }

    public void setImportButton(JButton importButton) {
        this.importButton = importButton;
    }

    public JButton getPlayButton() {
        return playButton;
    }

    public void setPlayButton(JButton playButton) {
        this.playButton = playButton;
    }

    public JTextField getFilePathField() {
        return filePathField;
    }

    public void setFilePathField(JTextField filePathField) {
        this.filePathField = filePathField;
    }

    public Thread getPlayerThread() {
        return playerThread;
    }

    public void setPlayerThread(Thread playerThread) {
        this.playerThread = playerThread;
    }

    public JTextField getPathAudioUS() {
        return pathAudioUS;
    }

    public void setPathAudioUS(JTextField pathAudioUS) {
        this.pathAudioUS = pathAudioUS;
    }

    public JTextField getPathAudioUK() {
        return pathAudioUK;
    }

    public void setPathAudioUK(JTextField pathAudioUK) {
        this.pathAudioUK = pathAudioUK;
    }

    public AdvancedPlayer getPlayer() {
        return player;
    }

    public void setPlayer(AdvancedPlayer player) {
        this.player = player;
    }


    private static DashBoard1 instance;

    public static DashBoard1 getInstance() {
        if (instance == null) {
            instance = new DashBoard1();
        }
        return instance;
    }

    public DashBoard1() {
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

        JButton importAudioUS = new JButton("Import MP3");
        importAudioUS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        importAudioUS.setBounds(399, 131, 89, 23);
        contentPane.add(importAudioUS);

        JButton playAudioUS = new JButton("Play");
        playAudioUS.setBounds(486, 131, 89, 23);
        contentPane.add(playAudioUS);

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

        JButton importAudioUK = new JButton("Import MP3");
        importAudioUK.setBounds(410, 187, 89, 23);
        contentPane.add(importAudioUK);

        JButton playAudioUK = new JButton("Play");
        playAudioUK.setBounds(486, 187, 89, 23);
        contentPane.add(playAudioUK);

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
//                AudioController.getInstance().insertData(AudioController.getInstance().importAudioUS(),AudioController.getInstance().importAudioUK());
            }
        });


        importAudioUS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioController.getInstance().importAudioUS();
            }
        });

        playAudioUS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                AudioController.getInstance().playAudio(AudioUSDao.getInstance().findbyID(17));
                AudioController.getInstance().playAudioUS();;
            }
        });





    }

}