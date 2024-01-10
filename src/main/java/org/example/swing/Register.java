package org.example.swing;

import org.example.dao.UserDao;
import org.example.model.User;
import org.example.ui.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame {
    private JPanel contentPane;
    protected JTextField username;
    protected JTextField email;
    protected JPasswordField password;
    protected JPasswordField confirmPassword;
    protected JButton btnBack;
    protected JButton btnRegister;

    public JTextField getUsername() {
        return username;
    }

    public JTextField getEmail() {
        return email;
    }

    public JPasswordField getPassword() {
        return password;
    }

    public JPasswordField getConfirmPassword() {
        return confirmPassword;
    }

    public JButton getBtnBack() {
        return btnBack;
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }


    public static Register getInstance() {
        return new Register();
    }


    public Register() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 476, 347);
        contentPane = new JPanel();
        contentPane.setBorder(null);

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Đăng Ký Tài Khoản");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(131, 10, 180, 21);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Tên đăng nhập");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblNewLabel_1.setBounds(24, 45, 129, 21);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Email");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblNewLabel_1_1.setBounds(24, 92, 79, 21);
        contentPane.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Mật khẩu");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblNewLabel_1_2.setBounds(24, 145, 110, 21);
        contentPane.add(lblNewLabel_1_2);

        JLabel lblNewLabel_1_3 = new JLabel("Nhập lại mật khẩu");
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblNewLabel_1_3.setBounds(24, 201, 161, 21);
        contentPane.add(lblNewLabel_1_3);

        btnRegister = new JButton("Đăng kí");
        btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnRegister.setBounds(334, 249, 104, 31);
        contentPane.add(btnRegister);

        username = new JTextField();
        username.setFont(new Font("Tahoma", Font.PLAIN, 18));
        username.setBounds(193, 41, 180, 25);
        contentPane.add(username);
        username.setColumns(10);

        btnBack = new JButton("Quay lại");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnBack.setBounds(24, 249, 110, 31);
        contentPane.add(btnBack);

        email = new JTextField();
        email.setFont(new Font("Tahoma", Font.PLAIN, 18));
        email.setColumns(10);
        email.setBounds(193, 92, 180, 25);
        contentPane.add(email);

        password = new JPasswordField();
        password.setFont(new Font("Tahoma", Font.PLAIN, 18));
        password.setBounds(193, 145, 180, 28);
        contentPane.add(password);

        confirmPassword = new JPasswordField();
        confirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
        confirmPassword.setBounds(193, 197, 180, 28);
        contentPane.add(confirmPassword);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register();
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Back();
            }
        });
        this.setVisible(true);

    }
    public void Register() {
        User user = new User();
        user.setUserName(username.getText());
        user.setEmail(email.getText());
        if(password.getText().equals(confirmPassword.getText())){
            user.setPassWord(password.getText());
            UserDao.getInstance().insert(user);
            JOptionPane.showMessageDialog(this , "Register successfully!");
        }else {
            JOptionPane.showMessageDialog(this , "Data inserted successfully!");
        }
    }
    public void Back(){
        this.setVisible(false);
        Login.getInstance().setVisible(true);
    }
}
