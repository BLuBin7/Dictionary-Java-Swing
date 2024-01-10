package org.example.swing.DashBoardForAdmin;

import org.example.ui.Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class JF_DashBoardForAdmin extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JF_DashBoardForAdmin frame = new JF_DashBoardForAdmin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public static JF_DashBoardForAdmin getInstance(){
        return new JF_DashBoardForAdmin();
    }
	/**
	 * Create the frame.
	 */
	public JF_DashBoardForAdmin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel sidePanel = new JPanel();
        sidePanel.setForeground(SystemColor.textHighlight);
        sidePanel.setBackground(new Color(102, 178, 255));
        sidePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        sidePanel.setPreferredSize(new Dimension(178, 464));
        contentPane.add(sidePanel, BorderLayout.WEST);
        sidePanel.setLayout(null);

        JButton btnAddWord = new JButton("Add Word");

        btnAddWord.setIcon(null);
        btnAddWord.setForeground(SystemColor.textHighlight);
        btnAddWord.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnAddWord.setBackground(new Color(143, 196, 248));
        btnAddWord.setBounds(0, 159, 178, 29);
        sidePanel.add(btnAddWord);

        JButton btnUpdateWord = new JButton("Update & Delete");
        btnUpdateWord.setForeground(SystemColor.textHighlight);
        btnUpdateWord.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnUpdateWord.setBackground(new Color(143, 196, 248));
        btnUpdateWord.setBounds(0, 198, 178, 29);
        sidePanel.add(btnUpdateWord);

        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setBounds(50, 22, 111, 84);
        ImageIcon originalLogo = new ImageIcon("C:\\Users\\builu\\Desktop\\New Folder\\popcorn_005.png");
        Image originalImage = originalLogo.getImage();
        Image resizedImage = originalImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH);

        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        lblNewLabel.setIcon(resizedIcon);

        sidePanel.add(lblNewLabel);

        JLabel lblUser = new JLabel("Hi, Admin");
        lblUser.setFont(new Font("Candara", Font.BOLD | Font.ITALIC, 23));
        lblUser.setBounds(38, 113, 98, 36);
        sidePanel.add(lblUser);

        JButton btnNewButton = new JButton("Logout");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnNewButton.setBounds(72, 361, 98, 24);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        sidePanel.add(btnNewButton);

        sidePanel.setBackground(Color.decode("#a2add0"));


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);

        JPanel panel = new JP_InsertWord();
        mainPanel.add(panel, "ADD");

        JPanel panel_1 = new JP_UpdateWord();
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

    private void logout(){
        this.setVisible(false);
        Login.getInstance().setVisible(true);
    }
}
