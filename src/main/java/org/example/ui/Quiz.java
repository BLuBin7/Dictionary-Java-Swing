package org.example.ui;

import org.example.util.JDBCUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Quiz extends JFrame {

    private JPanel contentPane;
    private JLabel lblQuestion;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Quiz frame = new Quiz();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Quiz() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 473, 248);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblQuestion = new JLabel("New label");
        lblQuestion.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblQuestion.setBounds(181, 10, 110, 20);
        contentPane.add(lblQuestion);

        JButton btnA = new JButton("A");
        btnA.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnA.setBounds(28, 50, 130, 21);
        contentPane.add(btnA);

        JButton btnC = new JButton("C");
        btnC.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnC.setBounds(262, 50, 130, 21);
        contentPane.add(btnC);

        JButton btnB = new JButton("B");
        btnB.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnB.setBounds(28, 104, 130, 21);
        contentPane.add(btnB);

        JButton btnD = new JButton("D");
        btnD.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnD.setBounds(262, 104, 130, 21);
        contentPane.add(btnD);

        JButton btnNext = new JButton("NEXT");
        btnNext.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNext.setBounds(314, 160, 110, 21);
        contentPane.add(btnNext);

        JButton btnExit = new JButton("EXIT");
        btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnExit.setBounds(40, 160, 103, 21);
        contentPane.add(btnExit);

        JFrame frame = new JFrame("Load Event Example");

        JButton[] buttons = {btnA, btnB, btnC, btnD};

        // Thêm một WindowListener để bắt sự kiện khi cửa sổ được mở
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                quizRandom(lblQuestion,buttons);
            }
        });

        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quizRandom(lblQuestion,buttons);
            }
        });


    }
    Random random = new Random();
    Connection connection = JDBCUtil.getConnection();

    public void quizRandom(JLabel lblQuestion,JButton[] buttons){
        String query = "SELECT e.name, vn.name " +
                "FROM definition de " +
                "JOIN english e ON de.english_id = e.id " +
                "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                "WHERE e.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int randomNumber= random.nextInt(2) + 9;
            preparedStatement.setInt(1, 10);

            int[] temp = {0,0,0};
            int randoIndex= random.nextInt(4);
            int i = 0;
            int randomTemp;
            if(randomNumber != 0){
                do{
                    randomTemp = random.nextInt(4);
                    temp[i] = randomTemp;

                }while (randomTemp != randoIndex);
            }

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                lblQuestion.setText(rs.getString(1));
                buttons[randoIndex].setText(rs.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
