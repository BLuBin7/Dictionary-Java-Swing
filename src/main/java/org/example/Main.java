package org.example;

import org.example.ui.*;

import javax.swing.*;
import java.util.*;

public class Main {
    Set<Integer> a;
    public Integer[] random(int randomAnswer) {
        Random random = new Random();
        a = new LinkedHashSet<>();

        do{
            int tempRandom2 = random.nextInt(4);
            a.add(tempRandom2);
        }while(a.size()!=4);
        Integer[] output = a.toArray(new Integer[0]);
//        int[] intArray = Arrays.stream(output).mapToInt(Integer::intValue).toArray();
        return output;
    }
        public static void main(String[] args) {
            Main main = new Main();
//            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                Register.getInstance();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            Integer[] set = main.random(2);

            Random random = new Random();
            int tempRandom = random.nextInt(4);

            System.out.println(set[tempRandom]);


        }
}
