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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelo.gestorBBDD;

public class MenuInicioController implements Initializable {
    @FXML
    private Label NombreJugador;
    String usuario;
    
    public void setNombreJugador(String nombreUsuario) {
        usuario = nombreUsuario;
        NombreJugador.setText(nombreUsuario);
    }
    
    @FXML
     private void cerrarSesion(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/InicioSesion.fxml"));
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
     
     @FXML
    private void unJugador(ActionEvent event){
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/GameBoard.fxml"));
            Parent root = loader.load();
            GameBoardController GameBoardController = loader.getController();
            GameBoardController.setUsuario(usuario);

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
    
    @FXML
    private void dosJugadores(ActionEvent event){
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/TwoPlayerGameBoard.fxml"));
            Parent root = loader.load();
            //GameBoardController GameBoardController = loader.getController();
            //GameBoardController.setUsuario(usuario);

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
     
      @FXML
     private void registroPartidas(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/RegistroPartidas.fxml"));
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
