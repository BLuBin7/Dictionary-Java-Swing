package org.example.swing.DashBoardForAdmin;

import org.example.dao.EnglishDao;
import org.example.model.Enum.Brand;
import org.example.model.Enum.Pos;
import org.example.util.JDBCUtil;

import java.awt.*;
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

public class JP_UpdateWord extends JPanel {
	private DefaultTableModel originalModel;
	private JTextField searchField;
	private JTextField Word;

	private JTextField pathAudioUS = new JTextField();
	private JTextField pathAudioUK = new JTextField();
	private JTextField Definition = new JTextField();

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
	};
	Brand[] optionsBrand = {Brand.A1, Brand.A2, Brand.B1,
			Brand.B2, Brand.C1, Brand.C2};
	private JComboBox<Pos> posComboBox = new JComboBox<>(optionsPOS);
	private JComboBox<Brand> brandComboBox = new JComboBox<>(optionsBrand);
	/**
	 * Create the panel.
	 */

	    
	public static JP_UpdateWord getInstance() {
		return new JP_UpdateWord();
	}
	
	public JP_UpdateWord() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel titleLabel = new JLabel("Display Table Update Word");
		titleLabel.setFont(new Font("Calibri", Font.BOLD, 30));

		// Create a Box to center the titleLabel horizontally
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.add(Box.createHorizontalGlue());
		horizontalBox.add(titleLabel);
		horizontalBox.add(Box.createHorizontalGlue());

		add(horizontalBox, BorderLayout.NORTH);

		Word = new JTextField();
		JPanel searchPanel = new JPanel();
		searchField = new JTextField(30);
		searchField.setFont(new Font("Calibri", Font.PLAIN, 14));
		searchPanel.add(searchField);

		searchField.setPreferredSize(new Dimension(searchField.getPreferredSize().width, 27));


		searchPanel.add(searchField);
		searchPanel.add(new JLabel());

		add(searchPanel);

		JButton searchButton = new JButton("Search");
		searchButton.setBackground(new Color(100, 180, 255));
		searchButton.setForeground(Color.WHITE);
		searchButton.setFocusPainted(false);
		searchPanel.add(searchButton);

		add(searchPanel);
		searchPanel.add(searchField);
		searchPanel.add(searchButton);
		add(searchPanel);

		setBackground(Color.decode("#e6e200"));

		add(new LineSeparator());

		originalModel = new DefaultTableModel();
		originalModel.addColumn("Word");
		originalModel.addColumn("Definition");
		originalModel.addColumn("Brand");
		originalModel.addColumn("Part Of Speech");


		JTable table = new JTable(originalModel);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		Font tableFont = new Font("Calibri", Font.PLAIN, 14);
		table.setFont(tableFont);

		JTableHeader tableHeader = table.getTableHeader();
		Font headerFont = new Font("Calibri", Font.BOLD, 16);
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
		// Set JPanel background color
		setBackground(new Color(240, 240, 240));


		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateButtonClicked(table.getSelectedRow());
			}
		});

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteButtonClicked(table.getSelectedRow());
			}
		});

		searchButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					findBrandTagAndPosTag(searchField.getText());
			}
			});
//			add(searchButton);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(Box.createHorizontalGlue());

		add(buttonPanel, BorderLayout.SOUTH);


	}
	Connection connection = JDBCUtil.getConnection();
	private void findBrandTagAndPosTag(String searchInput) {
		originalModel.setRowCount(0);
		try {
			String query = "SELECT e.word, vn.word, " +
					"tags.brand_tag_name , " +
					"tags.Part_Of_Speech_tag_name " +
					"FROM definition de " +
					"JOIN english e ON de.english_id = e.id " +
					"JOIN vietnamese vn ON vn.id = de.vietnamese_id " +
					"JOIN tags tags ON tags.id = e.id " +
					"WHERE e.word = ? ";
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
			Object WordObject = originalModel.getValueAt(selectedRow, 0);
			Object definitionObject = originalModel.getValueAt(selectedRow, 1);
			Object brandObject = originalModel.getValueAt(selectedRow, 2);
			Object posObject = originalModel.getValueAt(selectedRow, 3);

			Word.setText(WordObject.toString());
			String datadefinition = definitionObject.toString();
			String dataPos = posObject.toString();
			String dataBrand = brandObject.toString();

			int id = EnglishDao.getInstance().findIdEnglish(Word.getText(),datadefinition,dataPos,dataBrand);

			EnglishDao.getInstance().findWordToUpdate(Word,Word, Definition,
					posComboBox, brandComboBox, pathAudioUS, pathAudioUK);

            JF_UpdateFrame up = new JF_UpdateFrame(id,Word.getText(),Definition.getText(),posComboBox,
					brandComboBox,pathAudioUS.getText(),pathAudioUK.getText());
			up.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "Please select a row first.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

	private void deleteButtonClicked(int selectedRow){
		if (selectedRow != -1) {
			Object WordObject = originalModel.getValueAt(selectedRow, 0);
			Object definitionObject = originalModel.getValueAt(selectedRow, 1);
			Object brandObject = originalModel.getValueAt(selectedRow, 2);
			Object posObject = originalModel.getValueAt(selectedRow, 3);

			Word.setText(WordObject.toString());
			String datadefinition = definitionObject.toString();
			String dataPos = posObject.toString();
			String dataBrand = brandObject.toString();

			int id = EnglishDao.getInstance().findIdEnglish(Word.getText(),datadefinition,dataPos,dataBrand);

			EnglishDao.getInstance().findWord(Word,Word, Definition,
					posComboBox, brandComboBox, pathAudioUS, pathAudioUK);
			System.out.println(id);
			EnglishDao.getInstance().DeleteFromAdmin(id);
			originalModel.removeRow(selectedRow);

		} else {
			JOptionPane.showMessageDialog(this, "Please select a row first.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
		}
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		int y = getHeight() / 2;
		g2d.setColor(Color.BLACK);
		g2d.drawLine(0, y, getWidth(), y);
	}


	private static class LineSeparator extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;

			int y = getHeight() / 2;
			g2d.setColor(Color.DARK_GRAY);
			g2d.drawLine(2, y, getWidth(), y);
		}
	}
}