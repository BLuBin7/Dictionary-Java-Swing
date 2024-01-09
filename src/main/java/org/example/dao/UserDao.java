package org.example.dao;

import org.example.model.User;
import org.example.util.JDBCUtil;

import java.sql.*;

public class UserDao implements DAOinterface<User>{
    public static UserDao getInstance(){
        return new UserDao();
    }
    private Connection connection = JDBCUtil.getConnection();
    @Override
    public int insert(User user) {
        String query = "INSERT INTO Users (username,email,password) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassWord());

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
        return user.getId();
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public User findbyID(int ID) {
        return null;
    }
}
