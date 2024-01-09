package org.example.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        ITesseract tesseract = new Tesseract();
        try {
            tesseract.setDatapath("C:\\Users\\builu\\Documents\\Tess4J-3.4.8-src\\Tess4J\\tessdata");

            File imageFile = new File("C:\\Users\\builu\\Documents\\untitled1\\src\\main\\java\\org\\example\\image\\ocr-test.png");
            System.out.println(imageFile.getPath());
            String result = tesseract.doOCR(imageFile);

            System.out.println(result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
}
