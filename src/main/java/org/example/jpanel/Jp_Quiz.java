package org.example.jpanel;

import org.example.util.JDBCUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Jp_Quiz extends JPanel {
    private JLabel lblQuestion;
    private JTextField textField;

    public Jp_Quiz() {
        setLayout(null);

        lblQuestion = new JLabel("New label");
        lblQuestion.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 24));
        lblQuestion.setBounds(226, 24, 138, 20);
        add(lblQuestion);

        JButton btnA = new JButton("A");
        btnA.setFont(new Font("Cambria", Font.PLAIN, 21));
        btnA.setBounds(75, 82, 167, 33);
        add(btnA);

        JButton btnC = new JButton("C");
        btnC.setFont(new Font("Cambria", Font.PLAIN, 21));
        btnC.setBounds(341, 82, 151, 33);
        add(btnC);

        JButton btnB = new JButton("B");
        btnB.setFont(new Font("Cambria", Font.PLAIN, 21));
        btnB.setBounds(75, 136, 167, 33);
        add(btnB);

        JButton btnD = new JButton("D");
        btnD.setFont(new Font("Cambria", Font.PLAIN, 21));
        btnD.setBounds(338, 136, 154, 33);
        add(btnD);

        JButton btnNext = new JButton("NEXT");
        btnNext.setFont(new Font("Cambria", Font.PLAIN, 21));
        btnNext.setBounds(436, 258, 110, 21);
        add(btnNext);

        JButton btnExit = new JButton("EXIT");
        btnExit.setFont(new Font("Cambria", Font.PLAIN, 21));
        btnExit.setBounds(66, 258, 103, 21);
        add(btnExit);

        JFrame frame = new JFrame("Load Event Example");
        JButton[] buttons = {btnA, btnB, btnC, btnD};
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

    public java.util.List<Integer> random() {
        Random random = new Random();
        Set a = new LinkedHashSet<>();

        do{
            int tempRandom2 = random.nextInt(4);
            a.add(tempRandom2);
        }while(a.size()!=4);

        java.util.List<Integer> list = new ArrayList<>(a);
        return list;
    }
    List<Integer> list;
    public void quizRandom(JLabel lblQuestion, JButton[] buttons) {
        String query = "SELECT e.name, vn.name " +
                "FROM definition de " +
                "JOIN english e ON de.english_id = e.id " +
                "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                "WHERE e.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int randomNumber = random.nextInt(2) + 9;
            preparedStatement.setInt(1, 10);

            int randomIndex = random.nextInt(4);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                lblQuestion.setText(rs.getString(1));
                buttons[randomIndex].setText(rs.getString(2));
            }

            list = random();
            int correctAnswerIndex = list.indexOf(0);

            for (int i = 0; i < buttons.length; i++) {
                int currentButtonIndex = list.indexOf(i);
                if (currentButtonIndex == correctAnswerIndex) {
                    int finalI = i;
                    buttons[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Xử lý khi nút được nhấn (ví dụ: thay đổi màu sắc)
                            buttons[finalI].setBackground(Color.GREEN);
                        }
                    });
                } else {
                    int finalI = i;
                    buttons[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            buttons[finalI].setBackground(Color.RED);
                        }
                    });
                }
            }

            // Gọi quizRandom2 cho các nút khác
            int ansB = quizRandom2(buttons, randomIndex, correctAnswerIndex);
            int ansC = quizRandom2(buttons, randomIndex, ansB);
            quizRandom2(buttons, randomIndex, ansB);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int quizRandom2(JButton[] buttons, int correctIndex, int previousIndex) {
        String query = "SELECT vn.name " +
                "FROM vietnamese vn " +
                "WHERE vn.id NOT IN (?, ?) " +
                "ORDER BY RAND() " +
                "LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, correctIndex);
            preparedStatement.setInt(2, previousIndex);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                buttons[correctIndex].setText(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return correctIndex;
    }


//    public void quizRandom(JLabel lblQuestion,JButton[] buttons){
//        String query = "SELECT e.name, vn.name " +
//                "FROM definition de " +
//                "JOIN english e ON de.english_id = e.id " +
//                "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
//                "WHERE e.id = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            int randomNumber= random.nextInt(2) + 9;
//            preparedStatement.setInt(1, 10);
//
//            int[] temp = {0,0,0};
//            int randomIndex= random.nextInt(4);
//            int i = 0;
//            int randomTemp;
//            if(randomNumber != 0){
//                do{
//                    randomTemp = random.nextInt(4);
//                    temp[i] = randomTemp;
//
//                }while (randomTemp != randomIndex);
//            }
//
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                lblQuestion.setText(rs.getString(1));
//                buttons[randomIndex].setText(rs.getString(2));
//            }
////            int tempRandom2 = random.nextInt(10);
////            preparedStatement.setInt(1, tempRandom2);
//
//            list = random();
//            int a = list.indexOf(0);
//            int b = list.indexOf(1);
//            int c = list.indexOf(2);
//            int d = list.indexOf(3);
////            ResultSet rs = preparedStatement.executeQuery();
////            while (rs.next()) {
////                lblQuestion.setText(rs.getString(1));
////                buttons[a].setText(rs.getString(2));
////            }
//            int ansB = -1 , ansC = -1;
//            ansB = quizRandom2(buttons,b,randomIndex,ansB,ansC);
//            ansC = quizRandom2(buttons,c,randomIndex,ansB,ansC);
//            quizRandom2(buttons,d,randomIndex,ansB,ansC);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public int quizRandom2(JButton[] buttons,int index,int ans1,int ans2,int ans3){
//        String query = "SELECT vn.name " +
//                "FROM vietnamese vn " +
//                "WHERE vn.id NOT IN (?, ?, ?) " +
//                "ORDER BY RAND() " +
//                "LIMIT 1";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setInt(1, ans1);
//            preparedStatement.setInt(2, ans2);
//            preparedStatement.setInt(3, ans3);
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                buttons[index].setText(rs.getString(1));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 1;
//
//    }
}
