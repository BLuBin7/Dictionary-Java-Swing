package org.example.dao;

import org.example.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TagDao {
    public static TagDao getInstance(){
        return new TagDao();
    }
    private Connection connection = JDBCUtil.getConnection();

    public int insert(int english_id, Object brand, Object pos) {
        String query = "INSERT INTO tags(english_id,brand_tag_name,Part_Of_Speech_tag_name) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, english_id);
            preparedStatement.setString(2, brand.toString());
            preparedStatement.setString(3, pos.toString());

            int result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

}
