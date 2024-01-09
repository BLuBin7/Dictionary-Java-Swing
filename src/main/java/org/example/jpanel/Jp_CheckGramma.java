package org.example.jpanel;

import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.rules.RuleMatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class Jp_CheckGramma extends JPanel {
    private JFormattedTextField formattedTextField;

    /**
     * Create the panel.
     */
    public Jp_CheckGramma() {
        setLayout(null);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(18, 10, 536, 99);
        add(scrollPane);

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setViewportView(textArea);


        JButton btnNewButton = new JButton("Check");

        JLabel formattedTextField = new JLabel();
        formattedTextField.setBounds(18, 176, 536, 204);
        add(formattedTextField);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                check(textArea,formattedTextField);
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNewButton.setBounds(432, 134, 101, 21);
        add(btnNewButton);
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
