package controlador;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameBoardController {

    @FXML
    private GridPane gameBoard;

    private int boardWidth = 20; // Ancho del tablero en celdas
    private int boardHeight = 15; // Altura del tablero en celdas

    private Rectangle[][] boardCells; // Matriz para almacenar las celdas del tablero

    private int snakeLength = 1; // Longitud inicial de la serpiente
    private int snakeX = 5; // Posición X de la cabeza de la serpiente
    private int snakeY = 5; // Posición Y de la cabeza de la serpiente

    private KeyCode direction = KeyCode.RIGHT; // Dirección inicial de movimiento de la serpiente

    private Timeline gameLoop; // Bucle de juego

    public void initialize() {
    // Inicializar el tablero y la serpiente
    initBoard();
    initSnake();

    // Configurar el bucle de juego para actualizar la posición de la serpiente periódicamente
    gameLoop = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> moveSnake()));
    gameLoop.setCycleCount(Timeline.INDEFINITE);
    gameLoop.play();

    // Vincular las teclas de flecha a los métodos de cambio de dirección
    gameBoard.setOnKeyPressed(e -> {
        switch (e.getCode()) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }
    });

    // Asegurar que el foco esté en el GridPane para que pueda detectar las teclas presionadas
    gameBoard.requestFocus();
}

    private void initBoard() {
        boardCells = new Rectangle[boardHeight][boardWidth];
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                Rectangle cell = new Rectangle(30, 30); // Tamaño de la celda
                boardCells[y][x] = cell;
                gameBoard.add(cell, x, y);
            }
        }
    }

    private void initSnake() {
        boardCells[snakeY][snakeX].setFill(javafx.scene.paint.Color.GREEN); // Dibujar la cabeza de la serpiente
    }

    private void moveSnake() {
        // Guardar la posición anterior de la cabeza de la serpiente
        int prevX = snakeX;
        int prevY = snakeY;
    
        // Mover la cabeza de la serpiente en la dirección actual
        switch (direction) {
            case UP:
                snakeY--;
                break;
            case DOWN:
                snakeY++;
                break;
            case LEFT:
                snakeX--;
                break;
            case RIGHT:
                snakeX++;
                break;
        }

        // Verificar si la serpiente ha alcanzado los bordes del tablero
        if (snakeX < 0 || snakeX >= boardWidth || snakeY < 0 || snakeY >= boardHeight) {
            gameOver();
            return;
        }

        // Actualizar la posición de la cabeza de la serpiente en el tablero
        boardCells[prevY][prevX].setFill(javafx.scene.paint.Color.TRANSPARENT); // Limpiar la celda anterior
        boardCells[snakeY][snakeX].setFill(javafx.scene.paint.Color.GREEN);

        // Verificar si la serpiente ha chocado consigo misma
        if (snakeLength > 1 && boardCells[snakeY][snakeX].getFill() == javafx.scene.paint.Color.GREEN) {
            gameOver();
            return;
        }

        // Incrementar la longitud de la serpiente
        snakeLength++;
    }

    private void gameOver() {
        // Detener el bucle de juego
        gameLoop.stop();
        System.out.println("Game Over");
    }

    // Métodos para controlar el movimiento de la serpiente
    public void moveUp() { direction = KeyCode.UP; }
    public void moveDown() { direction = KeyCode.DOWN; }
    public void moveLeft() { direction = KeyCode.LEFT; }
    public void moveRight() { direction = KeyCode.RIGHT; }
}

