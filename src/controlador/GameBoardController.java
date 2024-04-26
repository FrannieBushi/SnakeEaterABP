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
import javafx.scene.control.Label;

public class GameBoardController {

    @FXML
    private GridPane gameBoard;
    private Label   GameOver;
    
    @FXML
    private Label TimePlayed;
    
    @FXML
    private Label scoreLabel;
    
    private Timeline timeline;
    private int seconds = 0;
    
    private int playerPoints = 0;

    private final int BOARDWIDTH = 20; // Ancho del tablero en celdas
    private final int BOARDHEIGHT = 15; // Altura del tablero en celdas

    private Rectangle[][] boardCells; // Matriz para almacenar las celdas del tablero

    private int snakeLength = 1; // Longitud inicial de la serpiente
    private int snakeX = 5; // Posición X de la cabeza de la serpiente
    private int snakeY = 5; // Posición Y de la cabeza de la serpiente
    
    private int fruitX;
    private int fruitY;
    private boolean hasEatenFruit;

    private KeyCode direction = KeyCode.D; // Dirección inicial de movimiento de la serpiente
    private KeyCode lastDirection = KeyCode.D; // Dirección anterior de la serpiente

    private Timeline gameLoop; // Bucle de juego
    
    private List<Point> snakeBody = new ArrayList<>();
    
    public void startTimer() {
        timeline.play();
    }
     
    private void updateTimerLabel() {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        TimePlayed.setText(String.format("%02d:%02d", minutes, remainingSeconds));
    }
    
    private void updatePoints(int playerPoints) {
        scoreLabel.setText("Puntuación: "+playerPoints);
    }
    
    public void initialize() {
    // Inicializar el tablero y la serpiente
        initBoard();
        initSnake();
        initFruit();
    
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            seconds++;
            updateTimerLabel();
        }));
        
        timeline.setCycleCount(Timeline.INDEFINITE); // Hacer que el temporizador se ejecute indefinidamente
        startTimer(); // Iniciar el temporizador automáticamente al inicializar el controlador

    // Configurar el bucle de juego para actualizar la posición de la serpiente periódicamente
    gameLoop = new Timeline(new KeyFrame(Duration.millis(275), e -> moveSnake()));
    gameLoop.setCycleCount(Timeline.INDEFINITE);
    gameLoop.play();

    // Asegurar que el foco esté en el GridPane para que pueda detectar las teclas presionadas
    gameBoard.requestFocus();
    gameBoard.setFocusTraversable(true);
    //System.out.println("Foco del GridPane: " + gameBoard.isFocused());
}

    private void initBoard() {
        boardCells = new Rectangle[BOARDHEIGHT][BOARDWIDTH];
        for (int y = 0; y < BOARDHEIGHT; y++) {
            for (int x = 0; x < BOARDWIDTH; x++) {
                Rectangle cell = new Rectangle(30, 30); // Tamaño de la celda
                boardCells[y][x] = cell;
                gameBoard.add(cell, x, y);
            }
        }
        for (Point point : snakeBody) {
            //boardCells[point.y][point.x].setFill(javafx.scene.paint.Color.GREEN); // Dibujar el cuerpo de la serpiente
        }
    }

    private void initSnake() {
        boardCells[snakeY][snakeX].setFill(javafx.scene.paint.Color.GREEN); // Dibujar la cabeza de la serpiente
        snakeBody.add(new Point(snakeX, snakeY));
    }

    private void moveSnake() {
        // Guardar la posición anterior de la cabeza de la serpiente
        int prevX = snakeX;
        int prevY = snakeY;
        
        // Actualizar la dirección anterior
        lastDirection = direction;
        
        // Vincular las teclas de flecha a los métodos de cambio de dirección
        gameBoard.setOnKeyPressed(e -> {
            System.out.println("Tecla presionada: " + e.getCode());
            switch (e.getCode()) {
                case W:
                    if (lastDirection != KeyCode.S) {
                        direction = KeyCode.W;
                    }
                    break;
                case S:
                    if (lastDirection != KeyCode.W) {
                        direction = KeyCode.S;
                    }
                break;
                case A:
                    if (lastDirection != KeyCode.D) {
                        direction = KeyCode.A;
                    }
                    break;
                case D:
                    if (lastDirection != KeyCode.A) {
                        direction = KeyCode.D;
                    }
                    break;
        }
    });
         // Mover la cabeza de la serpiente en la dirección actual
        switch (direction) {
            case W:
                snakeY--;
                break;
            case S:
                snakeY++;
                break;
            case A:
                snakeX--;
                break;
            case D:
                snakeX++;
                break;
        }
        snakeBody.add(0, new Point(snakeX, snakeY)); // Añadir nueva posición al principio

        // Verificar si la serpiente ha alcanzado los bordes del tablero
        if (snakeX < 0 || snakeX >= BOARDWIDTH || snakeY < 0 || snakeY >= BOARDHEIGHT) {
            gameOver();
            return;
        }

        // Actualizar la posición de la cabeza de la serpiente en el tablero
        boardCells[prevY][prevX].setFill(javafx.scene.paint.Color.TRANSPARENT); // Limpiar la celda anterior
        boardCells[snakeY][snakeX].setFill(javafx.scene.paint.Color.GREEN);

        // Verificar si la serpiente ha chocado consigo misma
        if (snakeBody.stream().filter(p -> p.x == snakeX && p.y == snakeY).count() > 1) {
            gameOver();
            return;
        }
        if (snakeX == fruitX && snakeY == fruitY) {
            hasEatenFruit = true;
            playerPoints+=15;
            updatePoints(playerPoints);
            generateFruit();
        }

        if (!hasEatenFruit) {
            Point tail = snakeBody.remove(snakeBody.size() - 1); // Eliminar la última posición si no ha comido la fruta
            boardCells[tail.y][tail.x].setFill(javafx.scene.paint.Color.TRANSPARENT); // Limpiar la celda de la cola
        } else {
            hasEatenFruit = false;
        }

        // Actualizar la posición de la fruta en el tablero
        boardCells[fruitY][fruitX].setFill(javafx.scene.paint.Color.RED);

        // Incrementar la longitud de la serpiente solo si ha comido la fruta
        if (hasEatenFruit) {
            snakeLength++;
        }
        // Actualizar visualmente el cuerpo de la serpiente en el tablero
        for (Point point : snakeBody) {
            boardCells[point.y][point.x].setFill(javafx.scene.paint.Color.GREEN);
        }
        // Incrementar la longitud de la serpiente
        //snakeLength++;
    }

    private void gameOver() {
        // Detener el bucle de juego
        gameLoop.stop();
        System.out.println("Game Over");
        //GameOver.setVisible(true);
    }

    // Métodos para controlar el movimiento de la serpiente
   /* public void moveUp() { direction = KeyCode.UP; }
    public void moveDown() { direction = KeyCode.DOWN; }
    public void moveLeft() { direction = KeyCode.LEFT; }
    public void moveRight() { direction = KeyCode.RIGHT; }*/

    // Método para inicializar la fruta
    private void initFruit() {
        generateFruit();
        boardCells[fruitY][fruitX].setFill(javafx.scene.paint.Color.RED); // Dibujar la fruta en el tablero
    }

    // Método para generar una nueva fruta en una posición aleatoria
    private void generateFruit() {
        fruitX = (int) (Math.random() * BOARDWIDTH);
        fruitY = (int) (Math.random() * BOARDHEIGHT);
    }
}

