package org.example.controller;

import org.example.model.Enum.Pos;
import org.example.ui.Home;

public class TagController {
    Home dashBoard = Home.getInstance();
    private Pos[] option = dashBoard.getOptionsPOS();
    public static TagController getInstance(){
        return new TagController();
    }
    public void insertPOS(Object POS){

    }
}
