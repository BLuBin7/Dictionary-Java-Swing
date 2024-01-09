package org.example.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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

        JLabel lblNewLabel = new JLabel("Đăng nhập");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(347, 10, 121, 22);
        contentPane.add(lblNewLabel);

        JLabel lblTfaiKhon = new JLabel("Tài khoản");
        lblTfaiKhon.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblTfaiKhon.setBounds(218, 56, 91, 22);
        contentPane.add(lblTfaiKhon);

        JLabel lblMtKhu = new JLabel("Mật khẩu");
        lblMtKhu.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblMtKhu.setBounds(218, 127, 91, 22);
        contentPane.add(lblMtKhu);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        passwordField.setBounds(347, 127, 146, 24);
        contentPane.add(passwordField);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        textField.setBounds(347, 56, 141, 22);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("Đăng nhập");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnNewButton.setBounds(418, 184, 115, 29);
        contentPane.add(btnNewButton);

        JButton btnngK = new JButton("Đăng ký");
        btnngK.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnngK.setBounds(34, 184, 109, 29);
        contentPane.add(btnngK);
    }
}
