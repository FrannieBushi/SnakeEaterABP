/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Color;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class snakeLogic {
    
    int snakeX, snakeY, snakeLength, res;
    Color colorsnake;
    List<int[]>snake;
    
    
    public snakeLogic(int snakeX, int snakeY, int snakeLength, Color colorsnake){
        
        this.snakeX = snakeX;
        this.snakeY = snakeY;
        this.snakeLength = snakeLength;
        this.colorsnake = colorsnake;
    }
}
