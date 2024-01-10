package org.example.ui;

import javazoom.jl.player.advanced.AdvancedPlayer;
import org.example.controller.AudioController;
import org.example.controller.TagController;
import org.example.model.AudioUK;
import org.example.model.AudioUS;
import org.example.model.Brand;
import org.example.model.Enum.Pos;
import org.example.util.JDBCUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Home extends JFrame {
    private JPanel contentPane;
    private JTextField inputWord;
    private JTextField inputDefinition;
    private Thread playerThread;
    private JTextField pathAudioUS;
    private JTextField pathAudioUK;
    private AdvancedPlayer player;

    private JButton importButton;
    //    private JButton playButton;
//    private JTextField filePathField;

    private JButton btnPlayAudioUS;
    private JButton btnPlayAudioUK;
    private JButton btnImportAudioUK;
    private JButton btnImportAudioUS;

    public JButton getBtnPlayAudioUK() {
        return btnPlayAudioUK;
    }

    public void setBtnPlayAudioUK(JButton btnPlayAudioUK) {
        this.btnPlayAudioUK = btnPlayAudioUK;
    }

    public JTextField getInputWord() {
        return inputWord;
    }

    public JTextField getInputDefinition() {
        return inputDefinition;
    }

    public JButton getImportButton() {
        return importButton;
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

    public AdvancedPlayer getPlayer() {
        return player;
    }

    public void setPlayer(AdvancedPlayer player) {
        this.player = player;
    }

    public JTextField getPathAudioUK() {
        return pathAudioUK;
    }

    public JButton getBtnPlayAudioUS() {
        return btnPlayAudioUS;
    }
    public static void setInstance(Home instance) {
        Home.instance = instance;
    }

    private static Home instance;

    public static Home getInstance() {
        if (instance == null) {
            instance = new Home();
        }
        return instance;
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Home.getInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private AudioUS audioUS;
    private AudioUK audioUK;

    Pos[] optionsPOS = {Pos.INDEFINITE_ARTICLE, Pos.PREPOSITION, Pos.VERB
            ,Pos.NOUN,Pos.ADJECTIVE,Pos.ADVERB};

    Brand[] optionsBrand = {Brand.A1, Brand.A2, Brand.B1,
            Brand.B2, Brand.C1, Brand.C2};

    public Pos[] getOptionsPOS() {
        return optionsPOS;
    }

    private JLabel test;

    public Home() {
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

        btnImportAudioUS = new JButton("Import MP3");
        btnImportAudioUS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnImportAudioUS.setBounds(399, 131, 89, 23);
        contentPane.add(btnImportAudioUS);

        btnPlayAudioUS = new JButton("Play");
        btnPlayAudioUS.setBounds(486, 131, 89, 23);
        contentPane.add(btnPlayAudioUS);

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

        btnImportAudioUK = new JButton("Import MP3");
        btnImportAudioUK.setBounds(410, 187, 89, 23);
        contentPane.add(btnImportAudioUK);

        btnPlayAudioUK = new JButton("Play");
        btnPlayAudioUK.setBounds(486, 187, 89, 23);
        contentPane.add(btnPlayAudioUK);


        JComboBox<Pos> optionPOS = new JComboBox<>(optionsPOS);
        optionPOS.setBounds(162, 257, 142, 22);
        contentPane.add(optionPOS);

        JLabel lbloptionPOS = new JLabel("Nhập từ loại");
        lbloptionPOS.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbloptionPOS.setBounds(10, 307, 142, 20);
        contentPane.add(lbloptionPOS);

        Brand[] optionsBrand = {Brand.A1, Brand.A2, Brand.B1,
                            Brand.B2, Brand.C1, Brand.C2};

        JComboBox<Brand> optionBrand = new JComboBox<>(optionsBrand);
        optionBrand.setBounds(164, 307, 122, 21);
        contentPane.add(optionBrand);

        test = new JLabel("Nhập");
        test.setFont(new Font("Tahoma", Font.PLAIN, 18));
        test.setBounds(15, 295, 142, 20);
        contentPane.add(test);

        btnImportAudioUS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audioUS = AudioController.getInstance().importAudioUS();
            }
        });

        btnPlayAudioUS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                AudioController.getInstance().playAudio(AudioUSDao.getInstance().findbyID(17));
                AudioController.getInstance().playAudioUS();;
            }
        });

        btnImportAudioUK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audioUK = AudioController.getInstance().importAudioUK();
            }
        });

        btnPlayAudioUK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                AudioController.getInstance().playAudio(AudioUSDao.getInstance().findbyID(17));
                AudioController.getInstance().playAudioUK();;
            }
        });

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findWord(test);
                System.out.println(optionPOS.getSelectedItem());
                TagController.getInstance().insertPOS(optionPOS.getSelectedItem());
                AudioController.getInstance().insertData(audioUS,audioUK,optionBrand.getSelectedItem().toString(),optionPOS.getSelectedItem().toString());
            }
        });


        this.setVisible(true);
    }
    Connection connection = JDBCUtil.getConnection();
    public void findWord(JLabel jLabel){
        String query = "SELECT * FROM English ";
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                jLabel.setText(rs.getString(2));
                System.out.println(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
