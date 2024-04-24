package controlador;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class TwoPlayerGameBoardController {

    @FXML
    private GridPane gameBoard;

    private final int BOARDWIDTH = 20;
    private final int BOARDHEIGHT = 15;

    private Rectangle[][] boardCells;
    
    private int snakeX1, snakeY1, snakeX2, snakeY2;
    private KeyCode direction1 = KeyCode.RIGHT;
    private KeyCode direction2 = KeyCode.D;
    private List<Point> snakeBody1 = new ArrayList<>();
    private List<Point> snakeBody2 = new ArrayList<>();

    private KeyCode lastDirection1 = KeyCode.RIGHT; // Dirección anterior del jugador 1
    private KeyCode lastDirection2 = KeyCode.D; // Dirección anterior del jugador 2;

    private int fruitX, fruitY;
    private boolean hasEatenFruit = false;

    private Timeline gameLoop;

    public void initialize() {
        initBoard();
        initSnake(1, 5, 5, direction1, snakeBody1);
        initSnake(2, 15, 5, direction2, snakeBody2);
        initFruit();

        gameLoop = new Timeline(new KeyFrame(Duration.millis(275), e -> moveSnakes()));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();

        gameBoard.setOnKeyPressed(e -> {
            setDirection(e.getCode());
        });

        gameBoard.setOnKeyReleased(e -> {
            setDirection(e.getCode());
        });

        gameBoard.setFocusTraversable(true);
        gameBoard.requestFocus();
    }

    private void initBoard() {
        boardCells = new Rectangle[BOARDHEIGHT][BOARDWIDTH];
        for (int y = 0; y < BOARDHEIGHT; y++) {
            for (int x = 0; x < BOARDWIDTH; x++) {
                Rectangle cell = new Rectangle(30, 30);
                boardCells[y][x] = cell;
                gameBoard.add(cell, x, y);
            }
        }
    }

    private void initSnake(int player, int startX, int startY, KeyCode startDirection, List<Point> body) {
        body.clear();
        body.add(new Point(startX, startY));
        
        switch (player) {
            case 1:
                snakeX1 = startX;
                snakeY1 = startY;
                direction1 = startDirection;
                boardCells[snakeY1][snakeX1].setFill(javafx.scene.paint.Color.GREEN);
                break;
            case 2:
                snakeX2 = startX;
                snakeY2 = startY;
                direction2 = startDirection;
                boardCells[snakeY2][snakeX2].setFill(javafx.scene.paint.Color.BLUE);
                break;
        }
    }

    private void moveSnakes() {
        moveSnake(1, snakeX1, snakeY1, direction1, snakeBody1);
        moveSnake(2, snakeX2, snakeY2, direction2, snakeBody2);
        checkCollisions();
    }

    private void moveSnake(int player, int x, int y, KeyCode direction, List<Point> body) {
        int prevX = x;
        int prevY = y;

        switch (direction) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }

        if (x < 0 || x >= BOARDWIDTH || y < 0 || y >= BOARDHEIGHT) {
            gameOver();
            return;
        }

        Point newHead = new Point(x, y);
        body.add(0, newHead);

        if (!hasEatenFruit) {
            Point tail = body.remove(body.size() - 1);
            boardCells[tail.y][tail.x].setFill(javafx.scene.paint.Color.TRANSPARENT);
        } else {
            hasEatenFruit = false;  // Restablecer la variable hasEatenFruit
            generateNewFruit();     // Generar una nueva fruta
        }

        boardCells[prevY][prevX].setFill(javafx.scene.paint.Color.TRANSPARENT);
        boardCells[y][x].setFill(player == 1 ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.BLUE);

        if (player == 1) {
            snakeX1 = x;
            snakeY1 = y;
        } else {
            snakeX2 = x;
            snakeY2 = y;
        }
    }

    private void generateNewFruit() {
        int newX;
        int newY;
        do {
            newX = (int) (Math.random() * BOARDWIDTH);
            newY = (int) (Math.random() * BOARDHEIGHT);
        } while (snakeBody1.contains(new Point(newX, newY)) || snakeBody2.contains(new Point(newX, newY)));

        fruitX = newX;
        fruitY = newY;
        boardCells[fruitY][fruitX].setFill(javafx.scene.paint.Color.RED);
    }



    private void setDirection(KeyCode code) {
        if (direction1.equals(KeyCode.UP) || direction1.equals(KeyCode.DOWN) || direction1.equals(KeyCode.LEFT) || direction1.equals(KeyCode.RIGHT)) {
            switch (code) {
                case UP:
                    if (lastDirection1 != KeyCode.DOWN) {
                        direction1 = KeyCode.UP;
                        lastDirection1 = KeyCode.UP;
                    }
                    break;
                case DOWN:
                    if (lastDirection1 != KeyCode.UP) {
                        direction1 = KeyCode.DOWN;
                        lastDirection1 = KeyCode.DOWN;
                    }
                    break;
                case LEFT:
                    if (lastDirection1 != KeyCode.RIGHT) {
                        direction1 = KeyCode.LEFT;
                        lastDirection1 = KeyCode.LEFT;
                    }
                    break;
                case RIGHT:
                    if (lastDirection1 != KeyCode.LEFT) {
                        direction1 = KeyCode.RIGHT;
                        lastDirection1 = KeyCode.RIGHT;
                    }
                    break;
            }
        } else {
            switch (code) {
                case W:
                    if (lastDirection2 != KeyCode.S) {
                        direction2 = KeyCode.W;
                        lastDirection2 = KeyCode.W;
                    }
                    break;
                case S:
                    if (lastDirection2 != KeyCode.W) {
                        direction2 = KeyCode.S;
                        lastDirection2 = KeyCode.S;
                    }
                    break;
                case A:
                    if (lastDirection2 != KeyCode.D) {
                        direction2 = KeyCode.A;
                        lastDirection2 = KeyCode.A;
                    }
                    break;
                case D:
                    if (lastDirection2 != KeyCode.A) {
                        direction2 = KeyCode.D;
                        lastDirection2 = KeyCode.D;
                    }
                    break;
            }
        }
    }




    private void checkCollisions() {
        if (snakeBody1.stream().anyMatch(p -> p.equals(new Point(snakeX2, snakeY2)))) {
            gameOver();
        } else if (snakeBody2.stream().anyMatch(p -> p.equals(new Point(snakeX1, snakeY1)))) {
            gameOver();
        }
    }

    private void gameOver() {
        gameLoop.stop();
        System.out.println("Game Over");
    }

    private void initFruit() {
        do {
            fruitX = (int) (Math.random() * BOARDWIDTH);
            fruitY = (int) (Math.random() * BOARDHEIGHT);
        } while (snakeBody1.contains(new Point(fruitX, fruitY)) || snakeBody2.contains(new Point(fruitX, fruitY)));

        boardCells[fruitY][fruitX].setFill(javafx.scene.paint.Color.RED);
    }
}
