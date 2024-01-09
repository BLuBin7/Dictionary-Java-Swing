package org.example.ui;
import org.example.controller.AudioController;
import org.example.util.JDBCUtil;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SearchTag extends JFrame{
    private JFrame frame;
    private JPanel mainPane;
    private JPanel topPane;
    private JPanel tablePane;
    private JPanel bottomPane;

    private JLabel selectAccountLabel;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JLabel homeWorldLabel;

    private JTextField userNameField;
    private JTextField homeWorldField;
    private JPasswordField passwordField;

    private JCheckBox membersBox;
    private JCheckBox randomBox;

    private JButton selectAccountButton;
    private JButton addButton;
    private JButton deleteButton;

    private JTable table;

    private JScrollPane scroll;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SearchTag frame1 = new SearchTag();
                    frame1.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public SearchTag() {
        JFrame frame = new JFrame("JTable Example");

        Connection connection = JDBCUtil.getConnection();

        DefaultTableModel model;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT e.name, e.name, e.name FROM English e");
            ResultSet rs = preparedStatement.executeQuery();
            model = new DefaultTableModel();
            {
                while (rs.next()) {
                    String column1Value = rs.getString(1);
                    System.out.println(column1Value);
                    String column2Value = rs.getString(2);
                    String column3Value = rs.getString(3);

                    model.addRow(new Object[]{column1Value, column2Value, column3Value});
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quizRandom();
            }
        });
        JTable table = new JTable(model);

        // Tạo JScrollPane để chứa bảng nếu có nhiều dữ liệu
        JScrollPane scrollPane = new JScrollPane(table);

        // Thêm JScrollPane (hoặc table) vào JFrame
        frame.add(scrollPane);


        table.setPreferredScrollableViewportSize(new Dimension(420, 250));
        table.setFillsViewportHeight(true);
        selectAccountLabel = new JLabel("Select Account");
        userNameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        homeWorldLabel = new JLabel("Home world");

        selectAccountButton = new JButton("Select Account");
//        addButton = new JButton("Add");
        deleteButton = new JButton("Del");

        userNameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        homeWorldField = new JTextField(3);

        membersBox = new JCheckBox("Members");
        randomBox = new JCheckBox("Random world");

        topPane = new JPanel();
        topPane.setLayout(new BorderLayout());

        topPane.add(selectAccountLabel, BorderLayout.WEST);
        topPane.add(selectAccountButton, BorderLayout.EAST);

        tablePane = new JPanel();
        tablePane.add(scrollPane);

        bottomPane = new JPanel();
        bottomPane.setLayout(new GridLayout(0, 5, 3, 3));

        bottomPane.add(userNameLabel);
        bottomPane.add(userNameField);
        bottomPane.add(membersBox);
        bottomPane.add(addButton);
        bottomPane.add(deleteButton);
        bottomPane.add(passwordLabel);
        bottomPane.add(passwordField);
        bottomPane.add(randomBox);
        bottomPane.add(homeWorldLabel);
        bottomPane.add(homeWorldField);

        mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

        frame.add(topPane, BorderLayout.NORTH);
        frame.add(tablePane, BorderLayout.CENTER);
        frame.add(bottomPane, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    Connection connection = JDBCUtil.getConnection();
    public void quizRandom() {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT e.name, e.name, e.name FROM English e");
            ResultSet rs = preparedStatement.executeQuery();
            DefaultTableModel model = new DefaultTableModel();
            {

                while (rs.next()) {
                    String column1Value = rs.getString(1);
                    String column2Value = rs.getString(2);
                    String column3Value = rs.getString(3);

                    model.addRow(new Object[]{column1Value, column2Value, column3Value});
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

