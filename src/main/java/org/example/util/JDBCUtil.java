package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    public static Connection getConnection(){
        Connection connection = null;
        String jdbcUrl = "jdbc:mySQL://localhost:3306/dictionary";
        String username = "root";
        String password = "blubin";

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Kết nối thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


}
