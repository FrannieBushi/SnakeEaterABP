package controlador;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TwoPlayerGameBoardController {

    @FXML
    private GridPane gameBoard;
    private Label   GameOver;
    
    @FXML
    private Label TimePlayed;
    
    @FXML
    private Label ScorePlayer1;
    @FXML
    private Label ScorePlayer2;
    
    private Timeline timeline;
    private int seconds = 0;
    
    private int playerPointsJ1 = 0;
    private int playerPointsJ2 = 0;

    private final int BOARDWIDTH = 20; // Ancho del tablero en celdas
    private final int BOARDHEIGHT = 15; // Altura del tablero en celdas

    private Rectangle[][] boardCells; // Matriz para almacenar las celdas del tablero
    
    //J1
    private int snakeLength = 1; // Longitud inicial de la serpiente
    private int snakeX = 5; // Posición X de la cabeza de la serpiente
    private int snakeY = 5; // Posición Y de la cabeza de la serpiente
    private KeyCode direction = KeyCode.D; // Dirección inicial de movimiento de la serpiente
    private KeyCode lastDirection = KeyCode.D; // Dirección anterior de la serpiente
    private List<Point> snakeBody = new ArrayList<>();

    //J2
    private int snakeTwoLength = 1; // Longitud inicial de la serpiente
    private int snakeTwoX = 12; // Posición X de la cabeza de la serpiente
    private int snakeTwoY = 7; // Posición Y de la cabeza de la serpiente
    private KeyCode directionSn2 = KeyCode.LEFT; // Dirección inicial de movimiento de la serpiente
    private KeyCode lastDirectionSn2 = KeyCode.LEFT; // Dirección anterior de la serpiente
    private List<Point> snakeBodyTwo = new ArrayList<>();

    private int fruitX;
    private int fruitY;
    private boolean hasEatenFruitJ1;
    private boolean hasEatenFruitJ2;

    private Timeline gameLoop; // Bucle de juego
    
    
    
    public void startTimer() {
        timeline.play();
    }
     
    private void updateTimerLabel() {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        TimePlayed.setText(String.format("%02d:%02d", minutes, remainingSeconds));
    }
    
    private void updatePointsJ1(int playerPoints) {
        ScorePlayer1.setText("Puntuación J1: " + playerPoints);
    }

    private void updatePointsJ2(int playerPoints) {
        ScorePlayer2.setText("Puntuación J2: " + playerPoints);
    }
    
    public void initialize() {
    // Inicializar el tablero y la serpiente
        initBoard();
        initSnakes();
        initFruit();
    
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            seconds++;
            updateTimerLabel();
        }));
        
        timeline.setCycleCount(Timeline.INDEFINITE); // Hacer que el temporizador se ejecute indefinidamente
        startTimer(); // Iniciar el temporizador automáticamente al inicializar el controlador

    // Configurar el bucle de juego para actualizar la posición de la serpiente periódicamente
    gameLoop = new Timeline(new KeyFrame(Duration.millis(275), e -> 
        moveSnakes()
        //moveSnakeTwo()
        ));
    gameLoop.setCycleCount(Timeline.INDEFINITE);
    gameLoop.play();

    // Asegurar que el foco esté en el GridPane para que pueda detectar las teclas presionadas
    gameBoard.requestFocus();
    gameBoard.setFocusTraversable(true);
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

        }
    }

    private void initSnakes() {
        boardCells[snakeY][snakeX].setFill(javafx.scene.paint.Color.GREEN); // Dibujar la cabeza de la serpiente
        snakeBody.add(new Point(snakeX, snakeY));
        
        boardCells[snakeTwoY][snakeTwoX].setFill(javafx.scene.paint.Color.BLUE); // Dibujar la cabeza de la serpiente
        snakeBodyTwo.add(new Point(snakeTwoX, snakeTwoY));
    }

    private void moveSnakes() {
        // Guardar la posición anterior de la cabeza de la serpiente
        int prevX = snakeX;
        int prevY = snakeY;
        int prevXSn2 = snakeTwoX;
        int prevYSn2 = snakeTwoY;
        
        // Actualizar la dirección anterior
        lastDirection = direction;
        lastDirectionSn2 = directionSn2; 
        
        // Vincular las teclas de flecha a los métodos de cambio de dirección
        gameBoard.setOnKeyPressed(e -> {
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
                case UP:
                    if (lastDirectionSn2 != KeyCode.DOWN) {
                        directionSn2 = KeyCode.UP;
                    }
                    break;
                case DOWN:
                    if (lastDirectionSn2 != KeyCode.UP) {
                        directionSn2 = KeyCode.DOWN;
                    }
                break;
                case LEFT:
                    if (lastDirectionSn2 != KeyCode.RIGHT) {
                        directionSn2 = KeyCode.LEFT;
                    }
                    break;
                case RIGHT:
                    if (lastDirectionSn2 != KeyCode.LEFT) {
                        directionSn2 = KeyCode.RIGHT;
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
        
        switch (directionSn2) {
            case UP:
                snakeTwoY--;
                break;
            case DOWN:
                snakeTwoY++;
                break;
            case LEFT:
                snakeTwoX--;
                break;
            case RIGHT:
                snakeTwoX++;
                break;   
        }
        
        snakeBody.add(0, new Point(snakeX, snakeY)); // Añadir nueva posición al principio
        snakeBodyTwo.add(0, new Point(snakeTwoX, snakeTwoY)); // Añadir nueva posición al principio
        
        // Verificar si la serpiente ha alcanzado los bordes del tablero
        if (snakeX < 0 || snakeX >= BOARDWIDTH || snakeY < 0 || snakeY >= BOARDHEIGHT) {
            System.out.println("Fuera de tablero");
            gameOver();
            return;
        }
        
        if (snakeTwoX < 0 || snakeTwoX >= BOARDWIDTH || snakeTwoY < 0 || snakeTwoY >= BOARDHEIGHT) {
            System.out.println("Fuera de tablero");
            gameOver();
            return;
        }

        // Actualizar la posición de la cabeza de la serpiente en el tablero
        boardCells[prevY][prevX].setFill(javafx.scene.paint.Color.TRANSPARENT); // Limpiar la celda anterior
        boardCells[snakeY][snakeX].setFill(javafx.scene.paint.Color.GREEN);
        
        boardCells[prevYSn2][prevXSn2].setFill(javafx.scene.paint.Color.TRANSPARENT); // Limpiar la celda anterior
        boardCells[snakeTwoY][snakeTwoX].setFill(javafx.scene.paint.Color.BLUE);

        // Verificar si la serpiente ha chocado consigo misma
        if (snakeBody.stream().filter(p -> p.x == snakeX && p.y == snakeY).count() > 1) {
            System.out.println("Chocada contra sí");
            gameOver();
            return;
        }
        
        if (snakeBodyTwo.stream().filter(p -> p.x == snakeTwoX && p.y == snakeTwoY).count() > 1) {
            System.out.println("Chocada contra sí");
            gameOver();
            return;
        }
        
        if (snakeX == fruitX && snakeY == fruitY) {
            hasEatenFruitJ1 = true;
            playerPointsJ1 += 15; // Aumentar puntos del jugador 1
            updatePointsJ1(playerPointsJ1); // Actualizar la etiqueta de puntuación del jugador 1
            generateFruit();
        }
        
        if (snakeTwoX == fruitX && snakeTwoY == fruitY) {
            hasEatenFruitJ2 = true;
            playerPointsJ2 += 15; // Aumentar puntos del jugador 1
            updatePointsJ2(playerPointsJ2); // Actualizar la etiqueta de puntuación del jugador 1
            generateFruit();
        }

        if (!hasEatenFruitJ1) {
            Point tail = snakeBody.remove(snakeBody.size() - 1); // Eliminar la última posición si no ha comido la fruta
            boardCells[tail.y][tail.x].setFill(javafx.scene.paint.Color.TRANSPARENT); // Limpiar la celda de la cola
        } else {
            hasEatenFruitJ1 = false;
        }
        
        if (!hasEatenFruitJ2) {
            Point tail = snakeBodyTwo.remove(snakeBodyTwo.size() - 1); // Eliminar la última posición si no ha comido la fruta
            boardCells[tail.y][tail.x].setFill(javafx.scene.paint.Color.TRANSPARENT); // Limpiar la celda de la cola
        } else {
            hasEatenFruitJ2 = false;
        }

        // Actualizar la posición de la fruta en el tablero
        boardCells[fruitY][fruitX].setFill(javafx.scene.paint.Color.RED);

        // Incrementar la longitud de la serpiente solo si ha comido la fruta
        if (hasEatenFruitJ1) {
            snakeLength++;
        }
        
        if (hasEatenFruitJ2) {
            snakeTwoLength++;
        }
         
        if (snakeBodyTwo.stream().anyMatch(p -> p.x == snakeX && p.y == snakeY)) {
            System.out.println("Colisión de la serpiente 1 con la serpiente 2");
            gameOver();
            return;
        }
        
        if (snakeBody.stream().anyMatch(p -> p.x == snakeTwoX && p.y == snakeTwoY)) {
            System.out.println("Colisión de la serpiente 2 con la serpiente 1");
            gameOver();
            return;
        }
        
        // Actualizar visualmente el cuerpo de la serpiente en el tablero
        for (Point point : snakeBody) {
            boardCells[point.y][point.x].setFill(javafx.scene.paint.Color.GREEN);
        }
        
        for (Point point : snakeBodyTwo) {
            boardCells[point.y][point.x].setFill(javafx.scene.paint.Color.BLUE);
        }
    }
    
    private void gameOver() {
        // Detener el bucle de juego
        gameLoop.stop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/GameOver.fxml"));
            Parent root = loader.load();
    
            // Crear una nueva escena
            Scene nuevaEscena = new Scene(root);
   
            // Obtener la ventana actual (escenario)
            Stage ventana = (Stage) gameBoard.getScene().getWindow(); 

            // Establecer la nueva escena en la ventana
            ventana.setScene(nuevaEscena);
            ventana.show();

            // Mostrar la nueva escena
            ventana.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    // Método para inicializar la fruta
    private void initFruit() {
        generateFruit();
        boardCells[fruitY][fruitX].setFill(javafx.scene.paint.Color.RED); // Dibujar la fruta en el tablero
    }

    // Método para generar una nueva fruta en una posición aleatoria
    private void generateFruit() {
        do {
            fruitX = (int) (Math.random() * BOARDWIDTH);
            fruitY = (int) (Math.random() * BOARDHEIGHT);
        } while (snakeBody.stream().anyMatch(p -> p.x == fruitX && p.y == fruitY));
    }
}



