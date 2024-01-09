package org.example.controller;

import org.example.dao.UserDao;
import org.example.model.User;
import org.example.ui.Register;

import javax.swing.*;

public class RegisterController {
    private static RegisterController instance;

    public static RegisterController getInstance() {
        if (instance == null) {
            instance = new RegisterController();
        }
        return instance;
    }
    Register register = Register.getInstance();
    private JTextField username = register.getUsername();
    private JTextField email = register.getEmail();
    private JTextField password = register.getPassword();
    private JTextField confirmPassword = register.getConfirmPassword();

    public void Register() {
        User user = new User();
        user.setUserName(username.getText());
        user.setEmail(email.getText());
        if(password.getText().equals(confirmPassword.getText())){
            user.setPassWord(password.getText());
            UserDao.getInstance().insert(user);
            JOptionPane.showMessageDialog(register , "Register successfully!");
        }else {
            JOptionPane.showMessageDialog(register , "Data inserted successfully!");
        }
    }


}
