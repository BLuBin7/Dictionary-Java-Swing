package org.example.jpanel.DashBoardForAdmin;

import com.mysql.cj.x.protobuf.Mysqlx;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import org.example.controller.TagController;
import org.example.dao.EnglishDao;
import org.example.dao.TagDao;
import org.example.dao.VietNameseDao;
import org.example.model.AudioUK;
import org.example.model.AudioUS;
import org.example.model.English;
import org.example.model.Enum.Brand;
import org.example.model.Enum.Pos;
import org.example.model.VietNamese;
import org.example.util.JDBCUtil;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertWord extends JPanel {
    private JPanel contentPane;
    private JTextField inputWord;
    private JTextField inputDefinition;
    private Thread playerThread;
    private AdvancedPlayer player;
    private JTextField pathAudioUS;
    private JTextField pathAudioUK;
    private JButton importButton;

    private JButton btnPlayAudioUS;
    private JButton btnPlayAudioUK;
    private JButton btnImportAudioUK;
    private JButton btnImportAudioUS;

    private AudioUS audioUS;
    private AudioUK audioUK;

    Pos[] optionsPOS = {Pos.INDEFINITE_ARTICLE, Pos.PREPOSITION, Pos.VERB
            ,Pos.NOUN,Pos.ADJECTIVE,Pos.ADVERB};

    Brand[] optionsBrand = {Brand.A1, Brand.A2, Brand.B1,
            Brand.B2, Brand.C1, Brand.C2};
	/**
	 * Create the panel.
	 */
	public InsertWord() {
        setBounds(100, 100, 601, 391);
        setLayout(null);

        JLabel lblNewLabel = new JLabel("Nhập từ");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setBounds(10, 46, 99, 14);
        add(lblNewLabel);

        inputWord = new JTextField();
        inputWord.setBounds(119, 47, 167, 20);
        add(inputWord);
        inputWord.setColumns(10);

        JButton btnCreate = new JButton("Create");
        btnCreate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCreate.setBounds(452, 307, 111, 35);
        add(btnCreate);

        JLabel lblNewLabel_1 = new JLabel("Nhập nghĩa");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1.setBounds(10, 84, 120, 20);
        add(lblNewLabel_1);

        inputDefinition = new JTextField();
        inputDefinition.setBounds(119, 88, 167, 20);
        add(inputDefinition);
        inputDefinition.setColumns(10);

        btnImportAudioUS = new JButton("Import MP3");

        btnImportAudioUS.setBounds(399, 131, 89, 23);
        add(btnImportAudioUS);

        btnPlayAudioUS = new JButton("Play");
        btnPlayAudioUS.setBounds(486, 131, 89, 23);
        add(btnPlayAudioUS);

        pathAudioUS = new JTextField();
        pathAudioUS.setBounds(206, 133, 175, 20);
        add(pathAudioUS);
        pathAudioUS.setColumns(10);

        JLabel lblNewLabel_1_1 = new JLabel("Thêm Phiên âm Anh Mỹ");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1_1.setBounds(8, 129, 201, 20);
        add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_3 = new JLabel("Nhập Thẻ Brand");
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1_3.setBounds(10, 255, 142, 20);
        add(lblNewLabel_1_3);

        JLabel lblNewLabel_1_1_1_1 = new JLabel("Thêm Phiên âm Anh Anh");
        lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1_1_1_1.setBounds(10, 185, 225, 20);
        add(lblNewLabel_1_1_1_1);

        pathAudioUK = new JTextField();
        pathAudioUK.setColumns(10);
        pathAudioUK.setBounds(224, 185, 175, 20);
        add(pathAudioUK);

        btnImportAudioUK = new JButton("Import MP3");
        btnImportAudioUK.setBounds(410, 187, 89, 23);
        add(btnImportAudioUK);

        btnPlayAudioUK = new JButton("Play");
        btnPlayAudioUK.setBounds(486, 187, 89, 23);
        add(btnPlayAudioUK);

        JComboBox<Pos> optionPOS = new JComboBox<>(optionsPOS);
        optionPOS.setBounds(162, 257, 142, 22);
        add(optionPOS);

        JLabel lbloptionPOS = new JLabel("Nhập từ loại");
        lbloptionPOS.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbloptionPOS.setBounds(10, 307, 142, 20);
        add(lbloptionPOS);

        Brand[] optionsBrand = {Brand.A1, Brand.A2, Brand.B1,
                            Brand.B2, Brand.C1, Brand.C2};

        JComboBox<Brand> optionBrand = new JComboBox<>(optionsBrand);
        optionBrand.setBounds(164, 307, 122, 21);
        add(optionBrand);

        btnImportAudioUS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audioUS = importAudioUS();
            }
        });

        btnPlayAudioUS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                playAudio(AudioUSDao.getInstance().findbyID(17));
                playAudioUS();;
            }
        });

        btnImportAudioUK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audioUK = importAudioUK();
            }
        });

        btnPlayAudioUK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                playAudio(AudioUSDao.getInstance().findbyID(17));
                playAudioUK();;
            }
        });

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData(audioUS,audioUK,optionBrand.getSelectedItem(),optionPOS.getSelectedItem());
                inputWord.setText("");
                inputDefinition.setText("");
                pathAudioUS.setText("");
                pathAudioUK.setText("");
                optionBrand.setSelectedIndex(0);
                optionPOS.setSelectedIndex(0);
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
            pathAudioUS.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }
public void playAudioUS() {
    String filePath = pathAudioUS.getText().trim();
    if (!filePath.isEmpty()) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            AdvancedPlayer player = new AdvancedPlayer(fileInputStream);

            Thread playerThread = new Thread(() -> {
                try {
                    player.setPlayBackListener(new PlaybackListener() {
                        @Override
                        public void playbackFinished(PlaybackEvent evt) {
                            // Enable the button or perform other actions after playback finishes
                            // For example, you can re-enable the play button
                            btnPlayAudioUS.setEnabled(true);
                        }
                    });

                    // Disable the play button during playback
                    btnPlayAudioUS.setEnabled(false);
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

    public void playAudioUK() {
        String filePath = pathAudioUK.getText().trim();
        if (!filePath.isEmpty()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(filePath);
                AdvancedPlayer player = new AdvancedPlayer(fileInputStream);

                Thread playerThread = new Thread(() -> {
                    try {
                        player.setPlayBackListener(new PlaybackListener() {
                            @Override
                            public void playbackFinished(PlaybackEvent evt) {
                                btnPlayAudioUK.setEnabled(true);
                            }
                        });

                        // Disable the play button during playback
                        btnPlayAudioUK.setEnabled(false);
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
            JOptionPane.showMessageDialog(this , "Data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this , "Error inserting data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            pathAudioUS.setText(fileChooser.getSelectedFile().getAbsolutePath());
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

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            pathAudioUK.setText(fileChooser.getSelectedFile().getAbsolutePath());
            audioUK.setAudio_source(fileChooser.getSelectedFile().getAbsolutePath());
        }
        return audioUK;
    }
}
