package org.example.dao;

import org.example.model.AudioUK;
import org.example.model.AudioUS;
import org.example.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AudioUSDao implements DAOinterface<AudioUS>{
    private static AudioUSDao instance;
    private Connection connection = JDBCUtil.getConnection();

//    private AudioUSDao() {
//        this.connection = JDBCUtil.getConnection();
//    }

    public static AudioUSDao getInstance() {
        if (instance == null) {
            instance = new AudioUSDao();
        }
        return instance;
    }

    public List<AudioUS> getAll() {
        List<AudioUS> audioList = new ArrayList<>();
        String sql = "SELECT * FROM audio_us";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String audioSource = resultSet.getString("audio_source");

                AudioUS audioUS = new AudioUS(id, audioSource);
                audioList.add(audioUS);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return audioList;
    }


    @Override
    public int insert(AudioUS audioUS){
        String query = "INSERT INTO Audio_US (audio_source) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, audioUS.getAudio_source());
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
        return audioUS.getId();
    }

    @Override
    public boolean update(AudioUS audioUS) {
        return false;
    }

    @Override
    public boolean delete(AudioUS audioUS) {
        return false;
    }

    @Override
    public AudioUS findbyID(int ID) {
        String sql = "SELECT * FROM audio_us WHERE id = ?";
        String audioSource = "";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    audioSource = resultSet.getString("audio_source");

                    return new AudioUS(ID, audioSource);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new AudioUS(ID,audioSource);
    }
}
