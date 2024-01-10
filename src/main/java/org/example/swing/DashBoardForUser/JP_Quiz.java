package org.example.swing.DashBoardForUser;

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
import java.util.*;
import java.util.List;

public class JP_Quiz extends JPanel {
    private JLabel lblQuestion;
    private JButton btnA, btnB, btnC, btnD;
    private JFrame frame;
    private boolean answerHandled = false;
    private JButton btnNext;
    private JButton btnExit;
    private JButton btnStart;
    private JLabel lblNewLabel;


    public JP_Quiz() {
        setLayout(null);

        lblQuestion = new JLabel("New label");
        lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
        lblQuestion.setFont(new Font("Cambria", Font.BOLD, 30));
        lblQuestion.setBounds(202, 86, 230, 39);
        add(lblQuestion);

        btnA = new JButton("A");
        btnA.setFont(new Font("Cambria", Font.PLAIN, 19));
        btnA.setBounds(77, 159, 191, 39);
        add(btnA);

        btnC = new JButton("C");
        btnC.setFont(new Font("Cambria", Font.PLAIN, 19));
        btnC.setBounds(343, 159, 175, 39);
        add(btnC);

        btnB = new JButton("B");
        btnB.setFont(new Font("Cambria", Font.PLAIN, 19));
        btnB.setBounds(77, 222, 191, 39);
        add(btnB);

        btnD = new JButton("D");
        btnD.setFont(new Font("Cambria", Font.PLAIN, 19));
        btnD.setBounds(343, 222, 175, 39);
        add(btnD);

        btnNext = new JButton("NEXT");
        btnNext.setFont(new Font("Cambria", Font.PLAIN, 21));
        btnNext.setBounds(440, 312, 110, 33);
        add(btnNext);

        btnExit = new JButton("EXIT");
        btnExit.setFont(new Font("Cambria", Font.PLAIN, 21));
        btnExit.setBounds(75, 312, 103, 33);
        add(btnExit);

        btnStart = new JButton("START");
        btnStart.setFont(new Font("Tahoma", Font.BOLD, 21));
        btnStart.setBounds(223, 186, 163, 39);
        add(btnStart);

        lblNewLabel = new JLabel("Challenge Quizzes");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblNewLabel.setBounds(144, 31, 316, 92);
        add(lblNewLabel);

        frame = new JFrame("Load Event Example");

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                quizRandom();
            }
        });

        lblQuestion.setVisible(false);
        btnA.setVisible(false);
        btnB.setVisible(false);
        btnC.setVisible(false);
        btnD.setVisible(false);
        btnNext.setVisible(false);
        btnExit.setVisible(false);

        lblNewLabel.setVisible(true);
        btnStart.setVisible(true);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Start();
                quizRandom();
            }
        });
        
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetButtonColors();
                answerHandled = false;
                quizRandom();
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Exit();
            }
        });

        // Thêm ActionListener cho từng nút
        addAnswerButtonListener(btnA, 0);
        addAnswerButtonListener(btnB, 1);
        addAnswerButtonListener(btnC, 2);
        addAnswerButtonListener(btnD, 3);
    }

    private void addAnswerButtonListener(JButton button, int answerIndex) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                handleAnswer(answerIndex);
            }
        });
    }

    Random random = new Random();
    Connection connection = JDBCUtil.getConnection();

    public java.util.List<Integer> random() {
        Random random = new Random();
        Set a = new LinkedHashSet<>();

        do {
            int tempRandom2 = random.nextInt(4);
            a.add(tempRandom2);
        } while (a.size() != 4);

        java.util.List<Integer> list = new ArrayList<>(a);
        return list;
    }

    public void quizRandom() {
        String query = "SELECT e.word, " +
                "       vn.word AS vn_name, " +
                "       (SELECT vn1.word FROM (SELECT word FROM vietnamese WHERE word <> vn.word ORDER BY RAND() LIMIT 1) vn1) AS vn1_name, " +
                "       (SELECT vn2.word FROM (SELECT word FROM vietnamese WHERE word <> vn.word ORDER BY RAND() LIMIT 1) vn2 WHERE vn2.word <> vn1_name) AS vn2_name, " +
                "       (SELECT vn_sub3.word FROM vietnamese vn_sub3 " +
                "        WHERE vn_sub3.id=? and vn_sub3.word <> vn.word AND vn_sub3.word <> vn1_name AND vn_sub3.word <> vn2_name) AS vn3_name " +
                "FROM definition de " +
                "JOIN english e ON de.english_id = e.id " +
                "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                "WHERE e.id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Random random = new Random();
            int randomIdEnglish = random.nextInt(5949);
            int randomIdVietnamese = random.nextInt(5949);
            preparedStatement.setInt(1, randomIdVietnamese);
            preparedStatement.setInt(2, randomIdEnglish);
            ResultSet rs = preparedStatement.executeQuery();


            List<String> answerList = new ArrayList<>();

            String correctAnswer;
            if (rs.next()) {
                answerList.add(rs.getString(2));
                answerList.add(rs.getString(3));
                answerList.add(rs.getString(4));
                answerList.add(rs.getString(5));

                int randomIndex = random.nextInt(answerList.size());
                lblQuestion.setText(rs.getString(1));

                btnA.setText(answerList.get(randomIndex));
                answerList.remove(randomIndex);

                randomIndex = random.nextInt(answerList.size());
                btnB.setText(answerList.get(randomIndex));
                answerList.remove(randomIndex);

                randomIndex = random.nextInt(answerList.size());
                btnC.setText(answerList.get(randomIndex));
                answerList.remove(randomIndex);

                btnD.setText(answerList.get(0));

                // Xác định đáp án đúng
                correctAnswer = rs.getString(2);;

                btnA.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleAnswer(btnA,btnA.getText(), correctAnswer);
                    }
                });

                btnB.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleAnswer(btnB,btnB.getText(), correctAnswer);
                    }
                });

                btnC.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleAnswer(btnC,btnC.getText(), correctAnswer);
                    }
                });

                btnD.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleAnswer(btnD,btnD.getText(), correctAnswer);
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resetButtonColors() {
        btnA.setBackground(null);
        btnB.setBackground(null);
        btnC.setBackground(null);
        btnD.setBackground(null);
    }

    private void handleAnswer(JButton btn,String selectedAnswer, String correctAnswer) {
        if (!answerHandled) {
            answerHandled = true;

            if (selectedAnswer.equals(correctAnswer)) {
                btn.setBackground(Color.GREEN);
                JOptionPane.showMessageDialog(this, "Chính xác!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                btn.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "Sai rồi. " + "Đáp án đúng là: " + correctAnswer, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void Start(){
        quizRandom();
        lblQuestion.setVisible(true);
        btnA.setVisible(true);
        btnB.setVisible(true);
        btnC.setVisible(true);
        btnD.setVisible(true);
        btnNext.setVisible(true);
        btnExit.setVisible(true);
        lblNewLabel.setVisible(false);
        btnStart.setVisible(false);

    }
    public void Exit(){
        lblQuestion.setVisible(false);
        btnA.setVisible(false);
        btnB.setVisible(false);
        btnC.setVisible(false);
        btnD.setVisible(false);
        btnNext.setVisible(false);
        btnExit.setVisible(false);
        btnStart.setVisible(true);
        lblNewLabel.setVisible(true);
    }
}