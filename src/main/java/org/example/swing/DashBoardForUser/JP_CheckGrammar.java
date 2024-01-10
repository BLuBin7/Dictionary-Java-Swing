package org.example.swing.DashBoardForUser;

import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.rules.RuleMatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class JP_CheckGrammar extends JPanel {

//    private JLanguageTool langTool;

    /**
     * Create the panel.
     */
    private JTextArea textArea;
    private JLabel formattedTextField;
    public JP_CheckGrammar() {
        setLayout(null);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(28, 63, 556, 128);
        add(scrollPane);

        textArea = new JTextArea();
        textArea.setFont(new Font("Calibri", Font.PLAIN, 20));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setViewportView(textArea);


        JButton btnNewButton = new JButton("CHECK");

        formattedTextField = new JLabel();

        formattedTextField.setFont(new Font("Calibri", Font.PLAIN, 20));

        formattedTextField.setPreferredSize(new Dimension(556, 400));
        formattedTextField.setVerticalAlignment(SwingConstants.TOP);
        formattedTextField.setHorizontalAlignment(SwingConstants.LEFT);

        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnNewButton.setBounds(483, 201, 101, 36);
        add(btnNewButton);

        JLabel lblNewLabel = new JLabel("CHECK GRAMMAR");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(202, 20, 200, 27);
        add(lblNewLabel);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(28, 247, 556, 199);
        add(scrollPane_1);
        scrollPane_1.setViewportView(formattedTextField);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                check(textArea,formattedTextField);
            }
        });
        langTool = new JLanguageTool(Languages.getLanguageForShortCode("en-GB"));
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

    private void clearText() {
        textArea.setText("");
        formattedTextField.setText("");
    }
}
