package org.example.ui;

import org.example.controller.RegisterController;
import org.example.dao.UserDao;
import org.example.jpanel.DashBoard;

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

        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login();
            }
        });
    }

    public void Login(){
        boolean bool = UserDao.getInstance().Login(textField.getText(),passwordField.getText());
        if(bool){
            JOptionPane.showMessageDialog(this ,"thấy từ: " + textField.getText(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false);
            DashBoard.getInstance().setVisible(true);
        }else {
            JOptionPane.showMessageDialog(this,"Không tìm thấy từ: " + textField.getText(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        }
    }
}
