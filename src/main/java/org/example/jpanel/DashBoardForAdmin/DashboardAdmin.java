package org.example.jpanel.DashBoardForAdmin;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class DashboardAdmin extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DashboardAdmin frame = new DashboardAdmin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DashboardAdmin() {
		setBackground(new Color(255, 255, 128));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 781, 516);
        contentPane = new JPanel();
        contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel sidePanel = new JPanel();
        sidePanel.setForeground(SystemColor.textHighlight);
        sidePanel.setBackground(new Color(192, 192, 192));
        sidePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        sidePanel.setPreferredSize(new Dimension(178, 464));
        contentPane.add(sidePanel, BorderLayout.WEST);
        sidePanel.setLayout(null);

        
        JButton btnAddWord = new JButton("Add Word");
        btnAddWord.setIcon(null);
        btnAddWord.setForeground(SystemColor.textHighlight);
        btnAddWord.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnAddWord.setBackground(new Color(143, 196, 248));
        btnAddWord.setBounds(0, 91, 178, 29);
        sidePanel.add(btnAddWord);
        
        JButton btnUpdateWord = new JButton("Update");
        btnUpdateWord.setForeground(SystemColor.textHighlight);
        btnUpdateWord.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnUpdateWord.setBackground(new Color(143, 196, 248));
        btnUpdateWord.setBounds(0, 136, 178, 29);
        sidePanel.add(btnUpdateWord);
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.setForeground(SystemColor.textHighlight);
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnDelete.setBackground(new Color(143, 196, 248));
        btnDelete.setBounds(0, 176, 178, 29);
        sidePanel.add(btnDelete);
        
        JButton btnTable = new JButton("Table Pos & Brand");
        btnTable.setForeground(SystemColor.textHighlight);
        btnTable.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnTable.setBackground(new Color(143, 196, 248));
        btnTable.setBounds(0, 215, 178, 29);
        sidePanel.add(btnTable);
        
        JButton btnNewButton_4 = new JButton("New button");
        btnNewButton_4.setForeground(SystemColor.textHighlight);
        btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnNewButton_4.setBackground(new Color(143, 196, 248));
        btnNewButton_4.setBounds(0, 256, 178, 29);
        sidePanel.add(btnNewButton_4);
        
        JButton btnNewButton_5 = new JButton("New button");
        btnNewButton_5.setForeground(SystemColor.textHighlight);
        btnNewButton_5.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnNewButton_5.setBackground(new Color(143, 196, 248));
        btnNewButton_5.setBounds(0, 294, 178, 29);
        sidePanel.add(btnNewButton_5);
        
        JLabel lblUser = new JLabel("New label");
        lblUser.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
        lblUser.setBounds(42, 42, 87, 13);
        sidePanel.add(lblUser);
        
        JButton btnNewButton = new JButton("Logout");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnNewButton.setBounds(72, 441, 98, 24);
        sidePanel.add(btnNewButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);
        
        JPanel panel = new InsertWord();
        mainPanel.add(panel, "ADD");
        
        JPanel panel_1 = new UpdateWord();
        mainPanel.add(panel_1, "UPDATE");
        
        btnAddWord.addActionListener(e -> showPanel("ADD"));
        btnUpdateWord.addActionListener(e -> showPanel("UPDATE"));

        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	 private void showPanel(String panelName) {
	        CardLayout cardLayout = (CardLayout) ((JPanel) contentPane.getComponent(1)).getLayout();
	        cardLayout.show(((JPanel) contentPane.getComponent(1)), panelName);
	    }
}
