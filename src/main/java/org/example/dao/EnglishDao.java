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

        String query = "INSERT INTO ENGLISH (word, audio_id_us, audio_id_uk) VALUES ( ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, english.getName());

            preparedStatement.setInt(2, audioUS_id);

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
        String query = "SELECT e.word, vn.word, us.audio_source, " +
                "uk.audio_source, tags.brand_tag_name, " +
                "tags.Part_Of_Speech_tag_name " +
                "FROM definition de " +
                "JOIN english e ON de.english_id = e.id " +
                "JOIN audio_us us ON e.audio_id_us = us.id " +
                "JOIN audio_uk uk ON e.audio_id_uk = uk.id " +
                "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                "JOIN tags tags ON tags.id = e.id " +
                "WHERE e.word = ?";
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

                JOptionPane.showMessageDialog(null, "Tìm thấy từ: " + inputWord.getText(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy từ: " + inputWord.getText(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void findWordToUpdate(JTextField inputWord, JTextField lblEnglish, JTextField lblVietNamese,
                         JComboBox<Pos> posComboBox, JComboBox<Brand> brandComboBox,
                         JTextField pathAudioUS, JTextField pathAudioUK) {
        String query = "SELECT e.word, vn.word, us.audio_source, " +
                "uk.audio_source, tags.brand_tag_name, " +
                "tags.Part_Of_Speech_tag_name " +
                "FROM definition de " +
                "JOIN english e ON de.english_id = e.id " +
                "JOIN audio_us us ON e.audio_id_us = us.id " +
                "JOIN audio_uk uk ON e.audio_id_uk = uk.id " +
                "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                "JOIN tags tags ON tags.id = e.id " +
                "WHERE e.word = ?";
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

            }
            else {
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

    public void findWordFromSearch(JTextField inputWord,JLabel lblEnglish, JLabel lblVietNamese,
                         JLabel lblTag,JLabel lblPOS,JLabel pathAudioUS, JLabel pathAudioUK
            ,JButton btnAudioUS, JButton btnAudioUK,JLabel description){
        String query = "SELECT e.word, vn.word, us.audio_source, " +
                "uk.audio_source, tags.brand_tag_name, " +
                "tags.Part_Of_Speech_tag_name,de.description " +
                "FROM definition de " +
                "JOIN english e ON de.english_id = e.id " +
                "JOIN audio_us us ON e.audio_id_us = us.id " +
                "JOIN audio_uk uk ON e.audio_id_uk = uk.id " +
                "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                "JOIN tags tags ON tags.id = e.id " +
                "WHERE vn.word = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, inputWord.getText());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                lblEnglish.setText(rs.getString(1));
                lblVietNamese.setText(rs.getString(2));
                pathAudioUS.setText(rs.getString(3));
                pathAudioUK.setText(rs.getString(4));
//                lblTag.setText(rs.getString(5));
                lblPOS.setText(rs.getString(6));
                description.setText("<html>" +rs.getString(7)+ "</html>");

                TagDao.getInstance().setImageforBrandTag(rs.getString(5),lblTag);
                lblVietNamese.setVisible(true);
                lblEnglish.setVisible(true);
                lblTag.setVisible(true);
                lblPOS.setVisible(true);
                btnAudioUS.setVisible(true);
                btnAudioUK.setVisible(true);
                description.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy từ: " + inputWord.getText(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return true;
    }

    @Override
    public English findbyID(int ID) {
        return null;
    }

    public void DeleteFromAdmin(int id){
       TagDao.getInstance().deleteTag(id);
       EnglishDao.getInstance().deleteDefinition(id);
       VietNameseDao.getInstance().deleteVietNamWord(id);
       EnglishDao.getInstance().deleteDefinition(id);
    }
    public void updateWord(int id, String inputWord, String VietNamese,
                           JComboBox<Pos> posComboBox, JComboBox<Brand> brandComboBox,
                           String pathAudioUS, String pathAudioUK) {
        String query = "UPDATE definition de " +
                "JOIN english e ON de.english_id = e.id " +
                "JOIN audio_us us ON e.audio_id_us = us.id " +
                "JOIN audio_uk uk ON e.audio_id_uk = uk.id " +
                "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                "JOIN tags tags ON tags.id = e.id " +
                "SET vn.word = ?, e.word = ?, us.audio_source = ?," +
                "uk.audio_source = ?, tags.brand_tag_name = ?," +
                "tags.Part_Of_Speech_tag_name = ? " +
                "WHERE e.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, VietNamese);
            preparedStatement.setString(2, inputWord);
            preparedStatement.setString(3, pathAudioUS);
            preparedStatement.setString(4, pathAudioUK);
            preparedStatement.setString(5, brandComboBox.getSelectedItem().toString());
            preparedStatement.setString(6, posComboBox.getSelectedItem().toString());
            preparedStatement.setInt(7, id);

            int rs = preparedStatement.executeUpdate();
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Updated: " + inputWord, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy từ: " + inputWord, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int findIdEnglish(String inputWord, String VietNamese,
                             String posComboBox, String brandComboBox) {
        String query = "SELECT e.id " +
                "FROM definition de " +
                "JOIN english e ON de.english_id = e.id " +
                "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                "JOIN tags tags ON tags.id = e.id " +
                "WHERE e.word = ? AND vn.word = ? " ;
//                "AND tags.brand_tag_name = ? AND tags.Part_Of_Speech_tag_name = ?";

        int englishId = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, inputWord);
            preparedStatement.setString(2, VietNamese);
//            preparedStatement.setString(3, posComboBox);
//            preparedStatement.setString(4, brandComboBox);


            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                englishId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return englishId;
    }

    private void deleteDefinition(int id){
        try {
            String query = " DELETE FROM definition de WHERE de.english_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int rs = preparedStatement.executeUpdate();
//            while (rs.next()) {
//                model.addRow(new Object[]{column1Value, column2Value, column3Value,column4Value});
//            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
