package org.example.jpanel;

import org.example.ui.Home;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DashBoard extends JFrame {
    private JPanel contentPane;
    private JPanel panel_2;

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

    private static DashBoard instance;

    public static DashBoard getInstance() {
        if (instance == null) {
            instance = new DashBoard();
        }
        return instance;
    }

    public DashBoard() {
        setBackground(new Color(255, 255, 128));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 781, 526);
        contentPane = new JPanel();
        contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel sidePanel = new JPanel();
        sidePanel.setForeground(SystemColor.textHighlight);
        sidePanel.setBackground(new Color(192, 192, 192));
        sidePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        sidePanel.setPreferredSize(new Dimension(178, 464));
        contentPane.add(sidePanel, BorderLayout.WEST);
        sidePanel.setLayout(null);

        JButton btnCheckGramma = new JButton("Check Gramma");
        btnCheckGramma.setIcon(null);
        btnCheckGramma.setForeground(SystemColor.textHighlight);
        btnCheckGramma.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnCheckGramma.setBackground(new Color(143, 196, 248));
        btnCheckGramma.setBounds(0, 91, 178, 29);
        sidePanel.add(btnCheckGramma);

        JButton btnSearch = new JButton("Translate");
        btnSearch.setForeground(SystemColor.textHighlight);
        btnSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnSearch.setBackground(new Color(143, 196, 248));
        btnSearch.setBounds(0, 136, 178, 29);
        sidePanel.add(btnSearch);

        JButton btnQuiz = new JButton("Quiz");
        btnQuiz.setForeground(SystemColor.textHighlight);
        btnQuiz.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnQuiz.setBackground(new Color(143, 196, 248));
        btnQuiz.setBounds(0, 176, 178, 29);
        sidePanel.add(btnQuiz);

        JButton btnTable = new JButton("Table Pos & Brand");
        btnTable.setForeground(SystemColor.textHighlight);
        btnTable.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnTable.setBackground(new Color(143, 196, 248));
        btnTable.setBounds(0, 215, 178, 29);
        sidePanel.add(btnTable);

        JButton btnNewButton_4 = new JButton("New button");
        btnNewButton_4.setForeground(SystemColor.textHighlight);
        btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnNewButton_4.setBackground(new Color(143, 196, 248));
        btnNewButton_4.setBounds(0, 256, 178, 29);
        sidePanel.add(btnNewButton_4);

        JButton btnNewButton_5 = new JButton("New button");
        btnNewButton_5.setForeground(SystemColor.textHighlight);
        btnNewButton_5.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnNewButton_5.setBackground(new Color(143, 196, 248));
        btnNewButton_5.setBounds(0, 294, 178, 29);
        sidePanel.add(btnNewButton_5);

        JLabel lblUser = new JLabel("New label");
        lblUser.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
        lblUser.setBounds(42, 42, 87, 13);
        sidePanel.add(lblUser);

        JButton btnNewButton = new JButton("Logout");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnNewButton.setBounds(72, 451, 98, 24);
        sidePanel.add(btnNewButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);

        panel_2 = new Jp_Search();
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_2.setBackground(new Color(192, 192, 192));
        mainPanel.add(panel_2, "SEARCH");

        JPanel panel_3 = new Jp_Quiz();
        mainPanel.add(panel_3, "QUIZ");

        JPanel panel_4 = new Jp_Table();
        mainPanel.add(panel_4, "TABLE");

        JPanel panel_5 = new Jp_CheckGramma();
        mainPanel.add(panel_5, "GRAMMAR");

        btnSearch.addActionListener(e -> showPanel("SEARCH"));
        btnQuiz.addActionListener(e -> showPanel("QUIZ"));
        btnTable.addActionListener(e -> showPanel("TABLE"));
        btnCheckGramma.addActionListener(e -> showPanel("GRAMMAR"));
        btnNewButton_4.addActionListener(e -> showPanel("OTHER"));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setBounds(0, 93, 178, 27);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(0, 128, 255));
        button.setForeground(new Color(255, 0, 128));
        button.setFont(new Font("Tahoma", Font.BOLD, 15));

        // Set button icon
//        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
//        button.setIcon(icon);

        return button;
    }

    private void showPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) ((JPanel) contentPane.getComponent(1)).getLayout();
        cardLayout.show(((JPanel) contentPane.getComponent(1)), panelName);
    }
}
