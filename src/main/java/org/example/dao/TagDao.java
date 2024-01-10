package org.example.dao;

import org.example.model.Enum.Brand;
import org.example.model.Enum.Pos;
import org.example.util.JDBCUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TagDao {
    public static TagDao getInstance(){
        return new TagDao();
    }
    public Connection connection = JDBCUtil.getConnection();

    public int insert(int english_id, String brand, String pos) {
        String query = "INSERT INTO tags (english_id,brand_tag_name, Part_Of_Speech_tag_name) VALUES (?,?, ?) ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, english_id);
            preparedStatement.setString(2, brand.toString());
            preparedStatement.setString(3, pos.toString());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Insertion successful. Rows affected: " + rowsAffected);
                return rowsAffected;
            } else {
                System.out.println("Insertion failed. No rows affected.");
                return 0;
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1452) {
                System.out.println("Insertion failed due to foreign key constraint. English_id: " +
                        english_id + " does not exist in the English table.");
            } else {
                e.printStackTrace();
            }
            return -1;
        }
    }



    public void findBrandTagAndPosTag(Pos selectedPos, Brand selectedBrand, DefaultTableModel model) {
        model.setRowCount(0);
        try {
            String query = "SELECT e.word, vn.word, " +
                    "tags.brand_tag_name, " +
                    "tags.Part_Of_Speech_tag_name " +
                    "FROM definition de " +
                    "JOIN english e ON de.english_id = e.id " +
                    "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                    "JOIN tags tags ON tags.id = e.id " +
                    "WHERE tags.brand_tag_name = ? and tags.Part_Of_Speech_tag_name = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, selectedBrand.toString());
            preparedStatement.setString(2, selectedPos.toString());


            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String column1Value = rs.getString(1);
                String column2Value = rs.getString(2);
                String column3Value = rs.getString(3);
                String column4Value = rs.getString(4);
                model.addRow(new Object[]{column1Value, column2Value, column3Value,column4Value});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void findAll(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            String query = "SELECT e.word, vn.word, " +
                    "tags.brand_tag_name, " +
                    "tags.Part_Of_Speech_tag_name " +
                    "FROM definition de " +
                    "JOIN english e ON de.english_id = e.id " +
                    "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                    "JOIN tags tags ON tags.id = e.id " ;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String column1Value = rs.getString(1);
                String column2Value = rs.getString(2);
                String column3Value = rs.getString(3);
                String column4Value = rs.getString(4);
                model.addRow(new Object[]{column1Value, column2Value, column3Value,column4Value});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void findBrandTag(Brand selectedBrand,DefaultTableModel model) {
        model.setRowCount(0);
        try {
            String query = "SELECT e.word, vn.word, " +
                    "tags.brand_tag_name, " +
                    "tags.Part_Of_Speech_tag_name " +
                    "FROM definition de " +
                    "JOIN english e ON de.english_id = e.id " +
                    "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                    "JOIN tags tags ON tags.id = e.id "+
                    "WHERE tags.brand_tag_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, selectedBrand.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String column1Value = rs.getString(1);
                String column2Value = rs.getString(2);
                String column3Value = rs.getString(3);
                String column4Value = rs.getString(4);
                model.addRow(new Object[]{column1Value, column2Value, column3Value,column4Value});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void findPosTag(Pos selectedPos,DefaultTableModel model) {
        model.setRowCount(0);
        try {
            String query = "SELECT e.word, vn.word, " +
                    "tags.brand_tag_name, " +
                    "tags.Part_Of_Speech_tag_name " +
                    "FROM definition de " +
                    "JOIN english e ON de.english_id = e.id " +
                    "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                    "JOIN tags tags ON tags.id = e.id "+
                    "WHERE tags.Part_Of_Speech_tag_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, selectedPos.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String column1Value = rs.getString(1);
                String column2Value = rs.getString(2);
                String column3Value = rs.getString(3);
                String column4Value = rs.getString(4);
                model.addRow(new Object[]{column1Value, column2Value, column3Value,column4Value});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTag(int id){
        try {
            String query = " delete from tags where english_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int rs = preparedStatement.executeUpdate();
//            while (rs>0) {
//                model.deleteo(new Object[]{column1Value, column2Value, column3Value,column4Value});
//            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setImageforBrandTag(String Brand,JLabel lblTag){
        String URL="";
        ImageIcon originalIcon;
        switch (Brand) {
            case "A1":
                originalIcon = new ImageIcon("src/main/java/org/example/image/brand/ox3000_a1.png");
                break;
            case "A2":
                originalIcon = new ImageIcon("src/main/java/org/example/image/brand/ox3000_a2.png");
                break;
            case "B1":
                originalIcon = new ImageIcon("src/main/java/org/example/image/brand/ox3000_b1.png");
                break;
            case "B2":
                originalIcon = new ImageIcon("src/main/java/org/example/image/brand/ox3000_b2.png");
                break;
            case "C1":
                originalIcon = new ImageIcon("src/main/java/org/example/image/brand/ox5000_c1.png");
                break;
            default:
                originalIcon = new ImageIcon();
        }

        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(42, 20, Image.SCALE_SMOOTH);

        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        lblTag.setIcon(resizedIcon);
    }

}
