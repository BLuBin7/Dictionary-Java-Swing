package org.example.dao;

import org.example.model.English;
import org.example.model.VietNamese;
import org.example.util.JDBCUtil;

import java.sql.*;

public class VietNameseDao implements DAOinterface<VietNamese>{
    public static VietNameseDao getInstance(){
        return new VietNameseDao();
    }
    private Connection connection = JDBCUtil.getConnection();
    @Override
    public int insert(VietNamese vietNamese) {
        String query = "INSERT INTO Vietnamese (word) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, vietNamese.getWord());

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
        return vietNamese.getId();
    }

    @Override
    public boolean update(VietNamese vietNamese) {
        return false;
    }

    @Override
    public boolean delete(VietNamese vietNamese) {
        return false;
    }

    @Override
    public VietNamese findbyID(int ID) {
        return null;
    }

    public void deleteVietNamWord(int id){
        try {
            String query = " DELETE FROM vietnamese WHERE id = ?;";
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
