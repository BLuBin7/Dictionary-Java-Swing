package org.example.dao;

import org.example.jpanel.DashBoard;
import org.example.model.User;
import org.example.ui.Login;
import org.example.util.JDBCUtil;

import javax.swing.*;
import java.sql.*;
import java.util.Locale;

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

    public boolean Login(String userName,String password){
        String query = "SELECT username FROM Users WHERE username=? AND password=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
