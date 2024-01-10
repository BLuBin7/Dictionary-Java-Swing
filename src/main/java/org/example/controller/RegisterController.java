package org.example.controller;

import org.example.swing.Register;

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




}
