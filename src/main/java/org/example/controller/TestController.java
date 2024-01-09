//package org.example.controller;
//
//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.advanced.AdvancedPlayer;
//import javazoom.jl.player.advanced.PlaybackEvent;
//import javazoom.jl.player.advanced.PlaybackListener;
//import org.example.ui.Home;
//import org.example.ui.Test;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//
//public class TestController {
//    Test dashBoard = Test.getInstance();
//    public void playAudioUK() {
//        String filePath = dashBoard.getPathAudioUS().getText().trim();
//        if (!filePath.isEmpty()) {
//            if (dashBoard.getPlayer() != null) {
//                dashBoard.getPlayer().close();
//            }
//
//            try {
//                FileInputStream fileInputStream = new FileInputStream(filePath);
//                dashBoard.setPlayer(new AdvancedPlayer(fileInputStream));
//
//                dashBoard.setPlayerThread(new Thread(() -> {
//                    try {
//                        dashBoard.getPlayer().setPlayBackListener(new PlaybackListener() {
//                            @Override
//                            public void playbackFinished(PlaybackEvent evt) {
//                                dashBoard.getBtnPlayAudioUK().setEnabled(true);
//                            }
//                        });
//
//                        dashBoard.getBtnPlayAudioUK().setEnabled(false);
//                        dashBoard.getPlayer().play();
//                    } catch (JavaLayerException e) {
//                        e.printStackTrace();
//                    }
//                }));
//
//                dashBoard.getPlayerThread().start();
//            } catch (IOException | JavaLayerException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
