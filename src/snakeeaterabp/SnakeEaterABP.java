/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeeaterabp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

import javafx.scene.media.*; 
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author minak
 */
public class SnakeEaterABP extends Application {
    
   /* static String musicFile = "src/sonido/musicaMenu.wav";
    static Media sound = new Media(new File(musicFile).toURI().toString());
    static MediaPlayer mediaPlayer = new MediaPlayer(sound);*/
    
    /*public static void muteMenuMusic(){
        
        if (mediaPlayer.isMute()) {
                
            mediaPlayer.setMute(false);
        } 
        else {
                
            mediaPlayer.setMute(true);
        }
    }*/
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/TwoPlayerGameBoard.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setResizable(false);
        stage.setMaximized(false);
        
        stage.setScene(scene);
        stage.show();
        
        //mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        //mediaPlayer.play();  
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
