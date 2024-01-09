package org.example.dao;

import org.example.model.AudioUK;
import org.example.model.AudioUS;
import org.example.model.English;
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

    public void findWord(JLabel jLabel){
        String query = "SELECT * FROM English ";
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                jLabel.setText(rs.getString(2));
                System.out.println(rs.getString(2));
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
        return false;
    }

    @Override
    public English findbyID(int ID) {
        return null;
    }
}
