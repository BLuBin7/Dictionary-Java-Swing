package org.example;

import org.example.ui.*;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.rules.RuleMatch;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class Main {
    // comment in to use statistical ngram data:
    //langTool.activateLanguageModelRules(new File("/data/google-ngram-data"));

        public static void main(String[] args) {
            JLanguageTool langTool = new JLanguageTool(Languages.getLanguageForShortCode("en-GB"));
            Main main = new Main();
//            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                Register.getInstance();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            List<RuleMatch> matches = null;
            try {
                matches = langTool.check("A sentence with a error in the Hitchhiker's Guide tot he Galaxy");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (RuleMatch match : matches) {
                System.out.println("Potential error at characters " +
                        match.getFromPos() + "-" + match.getToPos() + ": " +
                        match.getMessage().replaceAll("(<suggestion>|</suggestion>)","'"));
                System.out.println("Suggested correction(s): " +
                        match.getSuggestedReplacements());
            }


//        public static void main(String[] args){
//                Main main = new Main();
//                try {
//                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                    Register.getInstance();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
        }
}
