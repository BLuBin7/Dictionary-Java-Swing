package org.example.swing.DashBoardForUser;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.suuft.libretranslate.Translator;
import org.example.model.Enum.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author BLuBin
 * @create 1/18/2024 8:32 PM
 * @project untitled1
 */
public class JP_OcrToText extends JPanel {
    private JTextField PathImage;
    private JTextArea textArea;
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
//    private JLabel lblTranslated;
    private JTextArea TextTranslated;
    /**
     * Create the panel.
     */
    public JP_OcrToText() {
        setLayout(null);

        JButton btnImportImage = new JButton("Choose Image");
        btnImportImage.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnImportImage.setBounds(448, 84, 137, 32);
        add(btnImportImage);

        JLabel lblNewLabel = new JLabel("Translate OCR To Text");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 22));
        lblNewLabel.setBounds(188, 10, 257, 41);
        add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Select image ");
        lblNewLabel_1.setFont(new Font("Calibri", Font.ITALIC, 25));
        lblNewLabel_1.setBounds(21, 61, 146, 30);
        add(lblNewLabel_1);

        PathImage = new JTextField();
        PathImage.setBounds(19, 88, 401, 30);
        PathImage.setFont(new Font("Arial", Font.BOLD, 22));
        add(PathImage);
        PathImage.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("OCR to Text");
        lblNewLabel_2.setFont(new Font("Calibri", Font.ITALIC, 25));
        lblNewLabel_2.setBounds(21, 129, 138, 33);
        add(lblNewLabel_2);

        JButton btnTranslate = new JButton("Translate");
        btnTranslate.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnTranslate.setBounds(462, 288, 121, 30);
        add(btnTranslate);

        optionsFromLanguage = new JComboBox<>(optionLanguage);
        optionsFromLanguage.setFont(new Font("Tahoma", Font.PLAIN, 16));
        optionsFromLanguage.setBounds(21, 289, 208, 32);
        add(optionsFromLanguage);

        optionsToLanguage = new JComboBox<>(optionLanguage);
        optionsToLanguage.setFont(new Font("Tahoma", Font.PLAIN, 16));
        optionsToLanguage.setBounds(246, 289, 208, 32);
        add(optionsToLanguage);

        JLabel lblNewLabel_1_1 = new JLabel("Choose Language In Text");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_1.setBounds(21, 262, 217, 29);
        add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_1_1 = new JLabel("Language You Want Translate");
        lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_1_1.setBounds(246, 262, 228, 29);
        add(lblNewLabel_1_1_1);

//        lblTranslated = new JLabel();
//        lblTranslated.setFont(new Font("Noto Sans", Font.BOLD, 20));
////        lblTranslated.setBounds(21, 331, 121, 25);
//        lblTranslated.setPreferredSize(new Dimension(556, 400));
//        lblTranslated.setVerticalAlignment(SwingConstants.TOP);
//        lblTranslated.setHorizontalAlignment(SwingConstants.LEFT);
        TextTranslated = new JTextArea();
        TextTranslated.setFont(new Font("Noto Sans", Font.BOLD, 20));
        TextTranslated.setLineWrap(true);
        TextTranslated.setWrapStyleWord(true);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 159, 562, 104);
        add(scrollPane);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(21, 333, 562, 138);
        add(scrollPane_1);
//        scrollPane_1.setViewportView(lblTranslated);
        scrollPane_1.setViewportView(TextTranslated);

        textArea = new JTextArea();
        textArea.setFont(new Font("Noto Sans", Font.ITALIC, 25));
//        JScrollPane scrollPane_2 = new JScrollPane();
//        scrollPane_2.setBounds(21, 172, 5, 22);

        scrollPane.setViewportView(textArea);
        add(scrollPane);
        btnImportImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                importImage();
            }
        });
        JButton btnNewButton = new JButton("Detect");
        btnNewButton.setFont(new Font("Cambria", Font.PLAIN, 17));
        btnNewButton.setBounds(448, 128, 137, 30);
        add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OcrToText();
            }
        });
        btnTranslate.addActionListener(new ActionListener() {
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

    public void importImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(java.io.File f) {
                return f.getName().toLowerCase().endsWith(".jpg") ||
                        f.getName().toLowerCase().endsWith(".jpeg") ||
                        f.getName().toLowerCase().endsWith(".png") ||
                        f.getName().toLowerCase().endsWith(".gif") ||
                        f.isDirectory();
            }
            @Override
            public String getDescription() {
                return "Image Files (*.jpg, *.jpeg, *.png, *.gif)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            PathImage.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }
    private void OcrToText(){
        ITesseract tesseract = new Tesseract();
        try {
            tesseract.setDatapath("C:\\Users\\builu\\Documents\\Tess4J-3.4.8-src\\Tess4J\\tessdata");

            File imageFile = new File(PathImage.getText());
            System.out.println(imageFile.getPath());
            String result = tesseract.doOCR(imageFile);

            textArea.setText(result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }

    private void Translate(){
        String translated = Translator.translate(net.suuft.libretranslate.Language.valueOf(optionsFromLanguage.getSelectedItem().toString()),
                net.suuft.libretranslate.Language.valueOf(optionsToLanguage.getSelectedItem().toString()), textArea.getText());

        TextTranslated.setText(translated);
    }

    private void clearText() {
        TextTranslated.setText("");
        PathImage.setText("");
        textArea.setText("");
    }
}
