package org.example.swing.DashBoardForUser;

import net.suuft.libretranslate.Translator;
import org.example.model.Enum.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author BLuBin
 * @create 1/18/2024 7:23 PM
 * @project untitled1
 */
public class JP_TranslateMultipleLanguages extends JPanel {
    private JTextArea textArea;
    /**
     * Create the panel.
     */
    Language[] optionLanguage = {Language.JAPANESE,
            Language.KOREAN,
            Language.CHINESE,
            Language.HINDI,
            Language.GERMAN,
            Language.ENGLISH,
            Language.INDONESIAN,
            Language.SWEDISH,
            Language.RUSSIAN,
            Language.DANISH,};
    JComboBox<Language> optionsFromLanguage;
    JComboBox<Language> optionsToLanguage;
    private JLabel formattedTextField;
    public JP_TranslateMultipleLanguages() {
        setLayout(null);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(28, 63, 556, 132);
        add(scrollPane);

        textArea = new JTextArea();
        textArea.setFont(new Font("Noto Sans", Font.PLAIN, 17));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setViewportView(textArea);


        JButton btnNewButton = new JButton("Translate");

        formattedTextField = new JLabel();
        formattedTextField.setFont(new Font("Noto Sans", Font.PLAIN, 18));
        formattedTextField.setVerticalAlignment(SwingConstants.TOP);
        formattedTextField.setHorizontalAlignment(SwingConstants.LEFT);

        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnNewButton.setBounds(440, 241, 129, 32);
        add(btnNewButton);

        JLabel lblNewLabel = new JLabel("Translate to Multiple Languages");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(129, 25, 372, 27);
        add(lblNewLabel);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(28, 283, 556, 190);
        add(scrollPane_1);
        scrollPane_1.setViewportView(formattedTextField);

        optionsFromLanguage = new JComboBox<>(optionLanguage);
        optionsFromLanguage.setFont(new Font("Tahoma", Font.PLAIN, 16));
        optionsFromLanguage.setBounds(254, 204, 159, 32);
        add(optionsFromLanguage);

        JLabel lblNewLabel_1 = new JLabel("Translate Language From");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_1.setBounds(28, 208, 217, 29);
        add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("To Language ");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_1_1.setBounds(28, 244, 217, 29);
        add(lblNewLabel_1_1);

        optionsToLanguage = new JComboBox<>(optionLanguage);
        optionsToLanguage.setFont(new Font("Tahoma", Font.PLAIN, 16));
        optionsToLanguage.setBounds(254, 240, 159, 32);
        add(optionsToLanguage);

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Translate();
            }
        });
        JButton btnNewButton_1 = new JButton("Clear");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearText();
            }
        });
        btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 17));
        btnNewButton_1.setBounds(462, 481, 94, 21);
        add(btnNewButton_1);
    }
    private void Translate(){
         String translated = Translator.translate(net.suuft.libretranslate.Language.valueOf(optionsFromLanguage.getSelectedItem().toString()),
                 net.suuft.libretranslate.Language.valueOf(optionsToLanguage.getSelectedItem().toString()), textArea.getText());
         formattedTextField.setText(translated);
    }

    private void clearText() {
        formattedTextField.setText("");
        textArea.setText("");
    }
}
