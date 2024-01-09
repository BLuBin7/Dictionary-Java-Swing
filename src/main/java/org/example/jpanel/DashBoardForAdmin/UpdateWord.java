package org.example.jpanel.DashBoardForAdmin;

import org.example.dao.EnglishDao;
import org.example.model.Enum.Brand;
import org.example.model.Enum.Pos;
import org.example.util.JDBCUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class UpdateWord extends JPanel {
	private DefaultTableModel originalModel;
	private JLabel newJlabel;
	private JTextField searchField;

	private JTextField Word;
	private JTextField pathAudioUS = new JTextField();
	private JTextField pathAudioUK = new JTextField();
	private JTextField Definition = new JTextField();


	Pos[] optionsPOS = {Pos.INDEFINITE_ARTICLE, Pos.PREPOSITION, Pos.VERBS
			,Pos.NOUN,Pos.ADJECTIVE,Pos.ADVERB};
	Brand[] optionsBrand = {Brand.A1, Brand.A2, Brand.B1,
			Brand.B2, Brand.C1, Brand.C2};

	private JComboBox<Pos> posComboBox = new JComboBox<>(optionsPOS);
	private JComboBox<Brand> brandComboBox = new JComboBox<>(optionsBrand);
	/**
	 * Create the panel.
	 */

	    
	public static UpdateWord getInstance() {
		return new UpdateWord();
	}
	
	public UpdateWord() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add search bar
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel);

		Word = new JTextField();
        
		 originalModel = new DefaultTableModel();
	        originalModel.addColumn("Column1");
	        originalModel.addColumn("Column2");
	        originalModel.addColumn("Column3");
	        originalModel.addColumn("Column4");


	        JTable table = new JTable(originalModel);
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

			searchButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					findBrandTagAndPosTag(searchField.getText());
			}
			});
			add(searchButton);
	        
	        JButton updateButton = new JButton("Update");
	        updateButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                updateButtonClicked(table.getSelectedRow());
	            }
	        });
	        add(updateButton);
	        

	        setSize(600, 300);
	        

	}
	Connection connection = JDBCUtil.getConnection();
	private void findBrandTagAndPosTag(String searchInput) {
		originalModel.setRowCount(0);
		try {
			String query = "SELECT e.name, vn.name, " +
					"tags.brand_tag_name, " +
					"tags.Part_Of_Speech_tag_name " +
					"FROM definition de " +
					"JOIN english e ON de.english_id = e.id " +
					"JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
					"JOIN tags tags ON tags.id = e.id " +
					"WHERE e.name = ? ";
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, searchInput);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String column1Value = rs.getString(1);
				String column2Value = rs.getString(2);
				String column3Value = rs.getString(3);
				String column4Value = rs.getString(4);
				originalModel.addRow(new Object[]{column1Value, column2Value, column3Value,column4Value});
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	private void updateButtonClicked(int selectedRow) {
        if (selectedRow != -1) {
			Object wordObject = originalModel.getValueAt(selectedRow, 0);

			Word.setText(wordObject.toString());

			EnglishDao.getInstance().findWord(Word, Word, Definition,
					posComboBox, brandComboBox, pathAudioUS, pathAudioUK);

            this.setVisible(false);
            UpdateFrame up = new UpdateFrame(Word.getText(),Definition.getText(),posComboBox,
					brandComboBox,pathAudioUS.getText(),pathAudioUK.getText());
			System.out.println(posComboBox.getSelectedItem());
            up.setVisible(true);

            // Display data (you can modify this part based on your requirement)
//            JOptionPane.showMessageDialog(this, "Data1: " + data1 + "\nData2: " + data2 + "\nData3: " + data3 + "\nData4: " + data4, "Selected Row Data", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row first.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

}