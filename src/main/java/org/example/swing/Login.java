package org.example.swing;

import org.example.dao.RoleDao;
import org.example.dao.UserDao;
import org.example.swing.DashBoardForAdmin.JF_DashBoardForAdmin;
import org.example.swing.DashBoardForUser.JF_DashBoard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JPanel contentPane;
    private JPasswordField passwordField;
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static Login instance;

    public static Login getInstance() {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }

    /**
     * Create the frame.
     */
    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 581, 275);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(372, 10, 121, 22);
        contentPane.add(lblNewLabel);

        JLabel lblTfaiKhon = new JLabel("Username");
        lblTfaiKhon.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblTfaiKhon.setBounds(248, 56, 91, 22);
        contentPane.add(lblTfaiKhon);

        JLabel lblMtKhu = new JLabel("Password");
        lblMtKhu.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblMtKhu.setBounds(248, 127, 91, 22);
        contentPane.add(lblMtKhu);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        passwordField.setBounds(347, 122, 186, 29);
        contentPane.add(passwordField);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        textField.setBounds(347, 56, 186, 29);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnNewButton.setBounds(418, 184, 115, 29);
        contentPane.add(btnNewButton);

        JButton btnngK = new JButton("Register");
        btnngK.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnngK.setBounds(34, 184, 109, 29);
        contentPane.add(btnngK);

        JLabel lblNewLabel_1 = new JLabel();
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon originalLogo = new ImageIcon("C:\\Users\\builu\\Desktop\\New Folder\\popcorn_005.png");
        Image originalImage = originalLogo.getImage();
        Image resizedImage = originalImage.getScaledInstance(140, 140, Image.SCALE_SMOOTH);
        lblNewLabel_1.setBounds(30, 28, 195, 142);
        lblNewLabel_1.setBackground(Color.cyan);


        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        lblNewLabel_1.setIcon(resizedIcon);
        contentPane.add(lblNewLabel_1);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login();
            }
        });
        btnngK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register();
            }
        });
    }

    public void Login(){
        boolean bool = UserDao.getInstance().Login(textField.getText(),passwordField.getText());
        if(bool){
            JOptionPane.showMessageDialog(this ,"Login Successful ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            this.setVisible(false);
            if(RoleDao.getInstance().getRoleUser(textField.getText())==2){
                int userNameID = UserDao.getInstance().getIdUser(textField.getText());
                JF_DashBoard dashBoard = new JF_DashBoard(textField.getText());
                dashBoard.setUserNameID(userNameID);
                dashBoard.setVisible(true);
            }else {
                JF_DashBoardForAdmin.getInstance().setVisible(true);
            }

        }else {
            JOptionPane.showMessageDialog(this,"User " + textField.getText()+" Not Found", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        }
    }
    public void Register(){
        this.setVisible(false);
        Register.getInstance().setVisible(true);
    }
}
