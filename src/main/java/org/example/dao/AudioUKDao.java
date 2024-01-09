package org.example.dao;

import org.example.model.AudioUK;
import org.example.util.JDBCUtil;

import java.sql.*;

public class AudioUKDao implements DAOinterface<AudioUK> {
    public static AudioUKDao getInstance(){
        return new AudioUKDao();
    }
    private Connection connection = JDBCUtil.getConnection();
    @Override
    public int insert(AudioUK audioUK) {
        String query = "INSERT INTO Audio_UK (audio_source) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, audioUK.getAudio_source());
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
        return audioUK.getId();
    }

    @Override
    public boolean update(AudioUK audioUK) {
        return false;
    }

    @Override
    public boolean delete(AudioUK audioUK) {
        return false;
    }

    @Override
    public AudioUK findbyID(int ID) {
        return new AudioUK();
    }
}
