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
import java.util.*;
import java.util.List;

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

    public List<Integer> random() {
        Random random = new Random();
        Set a = new LinkedHashSet<>();

        do{
            int tempRandom2 = random.nextInt(4);
            a.add(tempRandom2);
        }while(a.size()!=4);

        List<Integer> list = new ArrayList<>(a);
        return list;
    }
    List<Integer> list;

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
            int tempRandom2 = random.nextInt(10);
            preparedStatement.setInt(1, tempRandom2);

            list = random();
            int a = list.indexOf(0);
            int b = list.indexOf(1);
            int c = list.indexOf(2);
            int d = list.indexOf(3);
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                lblQuestion.setText(rs.getString(1));
//                buttons[a].setText(rs.getString(2));
//            }
            int ansB = -1 , ansC = -1;
            ansB = quizRandom2(buttons,b,tempRandom2,ansB,ansC);
            System.out.println(ansB);
            ansC = quizRandom2(buttons,c,tempRandom2,ansB,ansC);
            quizRandom2(buttons,d,tempRandom2,ansB,ansC);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int quizRandom2(JButton[] buttons,int index,int ans1,int ans2,int ans3){
        String query = "SELECT vn.name " +
                "FROM vietnamese vn " +
                "WHERE vn.id NOT IN (?, ?, ?) " +
                "ORDER BY RAND() " +
                "LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ans1);
            preparedStatement.setInt(2, ans2);
            preparedStatement.setInt(3, ans3);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                buttons[index].setText(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;

    }

}
