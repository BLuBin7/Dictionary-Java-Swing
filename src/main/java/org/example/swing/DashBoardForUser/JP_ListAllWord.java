package org.example.swing.DashBoardForUser;

import org.example.dao.TagDao;
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

public class JP_ListAllWord extends JPanel {
    private DefaultTableModel model;
    private DefaultTableModel originalModel;


    /**
     * Create the panel.
     */
    public JP_ListAllWord() {
        super(new BorderLayout());

        Pos[] optionsPOS = {
                Pos.ADJECTIVE,
                Pos.ADVERB,
                Pos.AUXILIARY_VERB,
                Pos.CONJUNCTION,
                Pos.DEFINITE_ARTICLE,
                Pos.DETERMINER,
                Pos.EXCLAMATION,
                Pos.INDEFINITE_ARTICLE,
                Pos.INFINITIVE_MARKER,
                Pos.LINKING_VERB,
                Pos.MODAL_VERB,
                Pos.NOUN,
                Pos.NUMBER,
                Pos.ORDINAL_NUMBER,
                Pos.PREPOSITION,
                Pos.PRONOUN,
                Pos.VERB,
                Pos.ALL
        };

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
        originalModel.addColumn("Word");
        originalModel.addColumn("Definition");
        originalModel.addColumn("Brand");
        originalModel.addColumn("Part Of Speech");

        model = new DefaultTableModel();
        model.addColumn("Word");
        model.addColumn("Definition");
        model.addColumn("Brand");
        model.addColumn("Part Of Speech");


        try {
            String query = "SELECT e.word, vn.word, " +
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


        Color tableBackgroundColor = new Color(255, 255, 255);
        table.setBackground(tableBackgroundColor);


        Color headerBackgroundColor = new Color(153, 204, 255);
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

    private void updateData(Pos selectedPos, Brand selectedBrand){
        if (selectedBrand.toString().equals("ALL") && selectedPos.toString().equals("ALL") ) {
            TagDao.getInstance().findAll(model);
        }
        else if(selectedBrand.toString().equals("ALL")){
            TagDao.getInstance().findPosTag(selectedPos,model);
        }else if(selectedPos.toString().equals("ALL")){
            TagDao.getInstance().findBrandTag(selectedBrand,model);
        }
        else {
            TagDao.getInstance().findBrandTagAndPosTag(selectedPos,selectedBrand,model);
        }
    }
}
