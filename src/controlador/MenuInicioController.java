/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author minak
 */
public class MenuInicioController implements Initializable {
    
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
            e.printStackTrace(); // Manejo básico de excepciones, puedes cambiarlo según tu necesidad
        }
    }
     
     @FXML
     private void unJugador(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/GameBoard.fxml"));
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
            e.printStackTrace(); // Manejo básico de excepciones, puedes cambiarlo según tu necesidad
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
            e.printStackTrace(); // Manejo básico de excepciones, puedes cambiarlo según tu necesidad
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
