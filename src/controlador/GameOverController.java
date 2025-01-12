package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class GameOverController implements Initializable {

    String JugadorUno;
    String JugadorDos;
    int puntuacionUno;
    int puntuacionDos;
    int idPartida;
    
    @FXML
    private Label IdPartida;
    
    @FXML
    private Label PuntuacionJ1;
    
    @FXML
    private Label PuntuacionJ2;
    
    @FXML
    private Button Salir;
    
    @FXML
    private Button EnviarComentario;
    
    @FXML
    private TextArea ComentarioPartida;
    
    @FXML
    private void btnSalir(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MenuInicio.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena
            Scene nuevaEscena = new Scene(root);

            // Obtener la ventana actual (escenario)
            Stage ventana = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Establecer la nueva escena en la ventana
            ventana.setScene(nuevaEscena);

            // Mostrar la nueva escena
            ventana.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setNombreJugadorUno(String nombreUsuario) {
        JugadorUno = nombreUsuario;
    }
    
    public void setNombreJugadorDos(String nombreUsuario) {
        JugadorDos = nombreUsuario;
    }
    
    public void setPuntuacion(int puntuacion) {
        puntuacionUno = puntuacion;
    }
    
    public void setPuntuacionDos(int puntuacion) {
        puntuacionDos = puntuacion;
    }
    
    public void setIdPartida(int id) {
        idPartida = id;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IdPartida.setText("ID PARTIDA: " + idPartida);
        PuntuacionJ1.setText("PUNTUACIÓN J1: " + puntuacionUno);
    }    
    
}
