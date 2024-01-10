package org.example.dao;

import edu.washington.cs.knowitall.logic.Expression;
import org.example.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author BLuBin
 * @create 1/11/2024 12:23 AM
 * @project untitled1
 */
public class RoleDao {
    Connection connection = JDBCUtil.getConnection();

    public static RoleDao getInstance() {
        return new RoleDao();
    }

    public int getRoleUser(String userName) {
        String query = "SELECT role_id  " +
                "FROM users_roles_nn " +
                "JOIN users ON users.id = users_roles_nn.user_id " +
                "WHERE username = ? ;";
        int role_id=0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                role_id = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return role_id;
    }
}
