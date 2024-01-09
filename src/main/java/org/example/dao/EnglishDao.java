package org.example.dao;

import org.example.model.AudioUK;
import org.example.model.AudioUS;
import org.example.model.English;
import org.example.model.Enum.Brand;
import org.example.model.Enum.Pos;
import org.example.model.VietNamese;
import org.example.util.JDBCUtil;

import javax.swing.*;
import java.sql.*;

public class EnglishDao implements DAOinterface<English> {
    private Connection connection = JDBCUtil.getConnection();
    public static EnglishDao getInstance(){
        return new EnglishDao();
    }
    public int insertword(English english, AudioUS audioUS, AudioUK audioUK) {
        int audioUS_id = AudioUSDao.getInstance().insert(audioUS);
        english.setAudioUS_id(audioUS_id);
        int audioUk_id= AudioUKDao.getInstance().insert(audioUK);
        english.setAudioUS_id(audioUk_id);

        String query = "INSERT INTO ENGLISH (name, audio_id_us, audio_id_uk) VALUES ( ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setInt(1, english.getId());
            preparedStatement.setString(1, english.getName());

            // Insert the AudioUS record and get the generated ID
            preparedStatement.setInt(2, audioUS_id);

            // Set other properties and execute the query
            preparedStatement.setInt(3, audioUk_id);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return english.getId();

    }

    public void insertDefinition(int VietNamese_id ,int english_id){
        String query = "INSERT INTO Definition (english_id,vietnamese_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, VietNamese_id);

            preparedStatement.setInt(2, english_id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void findWord(JTextField inputWord, JTextField lblEnglish, JTextField lblVietNamese,
                         JComboBox<Pos> posComboBox, JComboBox<Brand> brandComboBox,
                         JTextField pathAudioUS, JTextField pathAudioUK) {
        String query = "SELECT e.name, vn.name, us.audio_source, " +
                "uk.audio_source, tags.brand_tag_name, " +
                "tags.Part_Of_Speech_tag_name " +
                "FROM definition de " +
                "JOIN english e ON de.english_id = e.id " +
                "JOIN audio_us us ON e.audio_id_us = us.id " +
                "JOIN audio_uk uk ON e.audio_id_uk = uk.id " +
                "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                "JOIN tags tags ON tags.id = e.id " +
                "WHERE e.name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, inputWord.getText());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                lblEnglish.setText(rs.getString(1));
                lblVietNamese.setText(rs.getString(2));
                pathAudioUS.setText(rs.getString(3));
                pathAudioUK.setText(rs.getString(4));
                setComboBoxSelectedItem(brandComboBox, Brand.valueOf(rs.getString(5)));
                setComboBoxSelectedItem(posComboBox, Pos.valueOf(rs.getString(6)));
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy từ: " + inputWord.getText(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private <T> void setComboBoxSelectedItem(JComboBox<T> comboBox, T selectedItem) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equals(selectedItem)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }




    @Override
    public int insert(English english) {
        return 0;
    }

    @Override
    public boolean update(English english) {
        return false;
    }

    @Override
    public boolean delete(English english) {
        return false;
    }

    @Override
    public English findbyID(int ID) {
        return null;
    }
    public void updateWord(int id,String inputWord, String VietNamese,
                         JComboBox<Pos> posComboBox, JComboBox<Brand> brandComboBox,
                           String  pathAudioUS, String pathAudioUK) {
        String query = "UPDATE definition de " +
                "JOIN english e ON de.english_id = e.id " +
                "JOIN audio_us us ON e.audio_id_us = us.id " +
                "JOIN audio_uk uk ON e.audio_id_uk = uk.id " +
                "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                "JOIN tags tags ON tags.id = e.id " +
                "SET vn.name = ?, e.name = ? ,us.audio_source = ?," +
                "uk.audio_source= ?, tags.brand_tag_name=?, " +
                "tags.Part_Of_Speech_tag_name = ?" +
                "WHERE e.id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, inputWord.getText());
            preparedStatement.setString(2, VietNamese.getText());
            preparedStatement.setString(3, pathAudioUS.getText());
            preparedStatement.setString(4, pathAudioUK.getText());
            preparedStatement.setString(5, posComboBox.getSelectedItem().toString());
            preparedStatement.setString(6, brandComboBox.getSelectedItem().toString());
            preparedStatement.setInt(7, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
//
                JOptionPane.showMessageDialog(null, "Updated: " + inputWord.getText(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy từ: " + inputWord.getText(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
