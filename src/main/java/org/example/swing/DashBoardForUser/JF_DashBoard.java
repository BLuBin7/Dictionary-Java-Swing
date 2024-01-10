package org.example.swing.DashBoardForUser;

import org.example.swing.Login;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JF_DashBoard extends JFrame {
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JF_DashBoard frame = new JF_DashBoard("");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static JF_DashBoard getInstance() {
        return new JF_DashBoard("");
    }

    private int userNameID;

    public void setUserNameID(int userNameID) {
        this.userNameID = userNameID;
    }

    public int getUserNameID() {
        return userNameID;
    }

    public JF_DashBoard(String UserName) {
//        setBackground(new Color(255, 255, 128));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 550);
        contentPane = new JPanel();
        contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel sidePanel = new JPanel();
        sidePanel.setForeground(SystemColor.textHighlight);
        sidePanel.setBackground(new Color(102, 178, 255));
        sidePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        sidePanel.setPreferredSize(new Dimension(178, 464));
        contentPane.add(sidePanel, BorderLayout.WEST);
        sidePanel.setLayout(null);

        JButton btnCheckGramma = new JButton("Check Grammar");
        btnCheckGramma.setIcon(null);
        btnCheckGramma.setForeground(SystemColor.textHighlight);
        btnCheckGramma.setFont(new Font("Tahoma", Font.BOLD, 16));
//        btnCheckGramma.setBackground(new Color(143, 196, 248));
        btnCheckGramma.setBounds(0, 315, 178, 29);
        sidePanel.add(btnCheckGramma);

        JButton btnSearch = new JButton("Translate Word");
        btnSearch.setForeground(SystemColor.textHighlight);
        btnSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
//        btnSearch.setBackground(new Color(143, 196, 248));
        btnSearch.setBounds(0, 150, 178, 29);

        sidePanel.add(btnSearch);

        JButton btnQuiz = new JButton("Quiz");
        btnQuiz.setForeground(SystemColor.textHighlight);
        btnQuiz.setFont(new Font("Tahoma", Font.BOLD, 16));
//        btnQuiz.setBackground(new Color(143, 196, 248));
        btnQuiz.setBounds(0, 235, 178, 29);
        sidePanel.add(btnQuiz);

        JButton btnTable = new JButton("List All Word");
        btnTable.setForeground(SystemColor.textHighlight);
        btnTable.setFont(new Font("Tahoma", Font.BOLD, 16));
//        btnTable.setBackground(new Color(143, 196, 248));
        btnTable.setBounds(0, 274, 178, 29);
        sidePanel.add(btnTable);

        JButton btnSearchFromVietNam = new JButton("Multiple Languages");
        btnSearchFromVietNam.setForeground(SystemColor.textHighlight);
        btnSearchFromVietNam.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnSearchFromVietNam.setBounds(0, 195, 178, 29);
        sidePanel.add(btnSearchFromVietNam);

        JButton btnOcrToText = new JButton("Ocr To Text ");
        btnOcrToText.setForeground(SystemColor.textHighlight);
        btnOcrToText.setFont(new Font("Tahoma", Font.BOLD, 16));
//        btnOcrToText.setBackground(new Color(143, 196, 248));
        btnOcrToText.setBounds(0, 354, 178, 29);
        sidePanel.add(btnOcrToText);

        JLabel lblUser = new JLabel("Hi, "+ UserName);
        lblUser.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblUser.setBounds(50, 116, 125, 24);
        sidePanel.add(lblUser);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnLogout.setBounds(72, 453, 98, 24);
        sidePanel.add(btnLogout);
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logout();
            }
        });


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);

        JPanel panel_2 = new JP_TranslateWord(this.getUserNameID());
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
//        panel_2.setBackground(new Color(192, 192, 192));
        mainPanel.add(panel_2, "SEARCH");

        JPanel panel_3 = new JP_Quiz();
        mainPanel.add(panel_3, "QUIZ");

        JPanel panel_4 = new JP_ListAllWord();
        mainPanel.add(panel_4, "TABLE");

        JPanel panel_5 = new JP_CheckGrammar();
        mainPanel.add(panel_5, "GRAMMAR");
        JPanel panel_6 = new JP_TranslateMultipleLanguages();
        mainPanel.add(panel_6, "TRANSTALE");
        JPanel panel_7 = new JP_OcrToText();
        mainPanel.add(panel_7, "OCR_TO_TEXT");

        btnSearch.addActionListener(e -> showPanel("SEARCH"));
        btnQuiz.addActionListener(e -> showPanel("QUIZ"));
        btnTable.addActionListener(e -> showPanel("TABLE"));
        btnCheckGramma.addActionListener(e -> showPanel("GRAMMAR"));
        btnSearchFromVietNam.addActionListener(e -> showPanel("TRANSTALE"));
        btnOcrToText.addActionListener(e -> showPanel("OCR_TO_TEXT"));

        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setBounds(50, 22, 111, 84);
        ImageIcon originalLogo = new ImageIcon("C:\\Users\\builu\\Desktop\\New Folder\\popcorn_005.png");
        Image originalImage = originalLogo.getImage();
        Image resizedImage = originalImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH);

        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        lblNewLabel.setIcon(resizedIcon);

        sidePanel.add(lblNewLabel);


        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void showPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) ((JPanel) contentPane.getComponent(1)).getLayout();
        cardLayout.show(((JPanel) contentPane.getComponent(1)), panelName);
    }
    private void Logout(){
        this.setVisible(false);
        Login.getInstance().setVisible(true);
    }
}
