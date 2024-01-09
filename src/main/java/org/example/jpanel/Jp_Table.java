package org.example.jpanel;

import org.example.model.Enum.Brand;
import org.example.model.Enum.Pos;
import org.example.util.JDBCUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jp_Table extends JPanel {
    private DefaultTableModel model;
    private DefaultTableModel originalModel;


    /**
     * Create the panel.
     */
    public Jp_Table() {
        super(new BorderLayout());

        Pos[] optionsPOS = {Pos.ALL,Pos.INDEFINITE_ARTICLE, Pos.PREPOSITION, Pos.VERB
                ,Pos.NOUN,Pos.ADJECTIVE,Pos.ADVERB};

        Brand[] optionsBrand = {Brand.ALL,Brand.A1, Brand.A2, Brand.B1,
                Brand.B2, Brand.C1, Brand.C2};

        JComboBox<Pos> optionPOS = new JComboBox<>(optionsPOS);
        optionPOS.setBounds(10, 150, 80, 30);
//        add(optionPOS);

        JComboBox<Brand> optionBrand = new JComboBox<>(optionsBrand);
        optionBrand.setBounds(10, 150, 80, 30);
//        add(optionBrand);
        JPanel comboBoxPanel = new JPanel(new GridLayout(1, 2)); // GridLayout with 1 row and 2 columns
        comboBoxPanel.add(optionPOS);
        comboBoxPanel.add(optionBrand);

        add(comboBoxPanel, BorderLayout.NORTH);

        originalModel = new DefaultTableModel();
        originalModel.addColumn("Column1");
        originalModel.addColumn("Column2");
        originalModel.addColumn("Column3");
        originalModel.addColumn("Column4");

        model = new DefaultTableModel();
        model.addColumn("Column1");
        model.addColumn("Column2");
        model.addColumn("Column3");
        model.addColumn("Column4");



        try {
            String query = "SELECT e.name, vn.name, " +
                    "tags.brand_tag_name, " +
                    "tags.Part_Of_Speech_tag_name " +
                    "FROM definition de " +
                    "JOIN english e ON de.english_id = e.id " +
                    "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                    "JOIN tags tags ON tags.id = e.id " ;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String column1Value = rs.getString(1);
                String column2Value = rs.getString(2);
                String column3Value = rs.getString(3);
                String column4Value = rs.getString(4);

                originalModel.addRow(new Object[]{column1Value, column2Value, column3Value,column4Value});
                model.addRow(new Object[]{column1Value, column2Value, column3Value,column4Value});
            }
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Set the font size for the table
        Font tableFont = new Font("Arial", Font.PLAIN, 14); // You can customize the font and size
        table.setFont(tableFont);

        JTableHeader tableHeader = table.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 14); // You can customize the font and size
        tableHeader.setFont(headerFont);

        TableColumnModel columnModel = table.getColumnModel();
        for (int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++) {
            columnModel.getColumn(columnIndex).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    cellComponent.setFont(tableFont);
                    return cellComponent;
                }
            });
        }

        Color tableBackgroundColor = new Color(200, 220, 240);
        table.setBackground(tableBackgroundColor);


        Color headerBackgroundColor = new Color(100, 120, 140);
        tableHeader.setBackground(headerBackgroundColor);

        setSize(600, 300);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        optionPOS.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateData((Pos) optionPOS.getSelectedItem(), (Brand) optionBrand.getSelectedItem());
                }
            }
        });

        optionBrand.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateData((Pos) optionPOS.getSelectedItem(), (Brand) optionBrand.getSelectedItem());
                }
            }
        });
    }
    Connection connection = JDBCUtil.getConnection();

    private void findBrandTagAndPosTag(Pos selectedPos, Brand selectedBrand) {
        model.setRowCount(0);
        try {
            String query = "SELECT e.name, vn.name, " +
                    "tags.brand_tag_name, " +
                    "tags.Part_Of_Speech_tag_name " +
                    "FROM definition de " +
                    "JOIN english e ON de.english_id = e.id " +
                    "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                    "JOIN tags tags ON tags.id = e.id " +
                    "WHERE tags.brand_tag_name = ? and tags.Part_Of_Speech_tag_name = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, selectedBrand.toString());
            preparedStatement.setString(2, selectedPos.toString());


            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String column1Value = rs.getString(1);
                String column2Value = rs.getString(2);
                String column3Value = rs.getString(3);
                String column4Value = rs.getString(4);
                model.addRow(new Object[]{column1Value, column2Value, column3Value,column4Value});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void findAll() {
        model.setRowCount(0);
        try {
            String query = "SELECT e.name, vn.name, " +
                    "tags.brand_tag_name, " +
                    "tags.Part_Of_Speech_tag_name " +
                    "FROM definition de " +
                    "JOIN english e ON de.english_id = e.id " +
                    "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                    "JOIN tags tags ON tags.id = e.id " ;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String column1Value = rs.getString(1);
                String column2Value = rs.getString(2);
                String column3Value = rs.getString(3);
                String column4Value = rs.getString(4);
                model.addRow(new Object[]{column1Value, column2Value, column3Value,column4Value});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void findBrandTag(Brand selectedBrand) {
        model.setRowCount(0);
        try {
            String query = "SELECT e.name, vn.name, " +
                    "tags.brand_tag_name, " +
                    "tags.Part_Of_Speech_tag_name " +
                    "FROM definition de " +
                    "JOIN english e ON de.english_id = e.id " +
                    "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                    "JOIN tags tags ON tags.id = e.id "+
                    "WHERE tags.brand_tag_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, selectedBrand.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String column1Value = rs.getString(1);
                String column2Value = rs.getString(2);
                String column3Value = rs.getString(3);
                String column4Value = rs.getString(4);
                model.addRow(new Object[]{column1Value, column2Value, column3Value,column4Value});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void findPosTag(Pos selectedPos) {
        model.setRowCount(0);
        try {
            String query = "SELECT e.name, vn.name, " +
                    "tags.brand_tag_name, " +
                    "tags.Part_Of_Speech_tag_name " +
                    "FROM definition de " +
                    "JOIN english e ON de.english_id = e.id " +
                    "JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
                    "JOIN tags tags ON tags.id = e.id "+
                    "WHERE tags.Part_Of_Speech_tag_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, selectedPos.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String column1Value = rs.getString(1);
                String column2Value = rs.getString(2);
                String column3Value = rs.getString(3);
                String column4Value = rs.getString(4);
                model.addRow(new Object[]{column1Value, column2Value, column3Value,column4Value});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void updateData(Pos selectedPos, Brand selectedBrand){
        if (selectedBrand.toString().equals("ALL") && selectedPos.toString().equals("ALL") ) {
            findAll();
        }
        else if(selectedBrand.toString().equals("ALL")){
            findPosTag(selectedPos);
        }else if(selectedPos.toString().equals("ALL")){
            findBrandTag(selectedBrand);
        }
        else {
            findBrandTagAndPosTag(selectedPos,selectedBrand);
        }
    }
}
