package org.example.dao;

import org.example.util.JDBCUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class QuizDao {
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
    Connection connection = JDBCUtil.getConnection();
    public void quizRandom(JLabel lblQuestion, JButton[] buttons){
        String query = "SELECT e.name, vn.name " +
                "FROM definition de " +
                "JOIN english e ON de.english_id = e.id " +
                "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                "WHERE e.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 10);
            List<Integer> list = random();

            int a = list.indexOf(0);
            int b = list.indexOf(1);
            int c = list.indexOf(2);
            int d = list.indexOf(3);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                lblQuestion.setText(rs.getString(1));
                buttons[a].setText(rs.getString(2));
            }

            String query2 = "SELECT vn.name, vn.name, vn.name " +
                    "FROM vietnamese vn " +
                    "JOIN english e ON vn.id = e.id " +
                    "ORDER BY RAND() " +
                    "LIMIT 10 ";
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            ResultSet rs2 = preparedStatement2.executeQuery();
            while (rs2.next()) {
                buttons[b].setText(rs2.getString(1));
                buttons[c].setText(rs2.getString(1));
                buttons[d].setText(rs2.getString(1));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
