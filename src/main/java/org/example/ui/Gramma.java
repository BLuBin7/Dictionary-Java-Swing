package org.example.ui;

import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.rules.RuleMatch;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gramma extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Gramma frame = new Gramma();
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
    public Gramma() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 605, 428);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(18, 10, 536, 99);
        contentPane.add(scrollPane);

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setViewportView(textArea);


        JButton btnNewButton = new JButton("Check");

        JLabel formattedTextField = new JLabel();
        formattedTextField.setBounds(18, 176, 536, 204);
        contentPane.add(formattedTextField);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                check(textArea,formattedTextField);
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNewButton.setBounds(447, 134, 101, 21);
        contentPane.add(btnNewButton);


    }
    JLanguageTool langTool = new JLanguageTool(Languages.getLanguageForShortCode("en-GB"));
    public void check(JTextArea textArea,JLabel jlabel) {
        List<RuleMatch> matches = null;
        try {
            matches = langTool.check(textArea.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder errorDetails = new StringBuilder("<html>");
        for (RuleMatch match : matches) {
            errorDetails.append("Potential error at characters ")
                    .append(match.getFromPos())
                    .append("-")
                    .append(match.getToPos())
                    .append(": ")
                    .append(match.getMessage().replaceAll("(<suggestion>|</suggestion>)", "'"))
                    .append("<br>Suggested correction(s): ")
                    .append(match.getSuggestedReplacements())
                    .append("<br>");
        }
        errorDetails.append("</html>");
        jlabel.setText(errorDetails.toString());

    }
}

