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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import modelo.gestorBBDD;
import snakeeaterabp.SnakeEaterABP;

/**
 *
 * @author minak
 */
public class InicioSesionController implements Initializable {
   
    @FXML
    private TextField UsrIntroducido;
    
    @FXML
    private PasswordField PswIntroducido;
    
    @FXML
    private Label alerta;
     
    @FXML
    private void bloquearEspacios(KeyEvent event){
        
        Object evt = event.getSource();
        
        if(evt.equals(UsrIntroducido)){
            
            if(event.getCharacter().equals(" ")){
                event.consume();
            }
        }
        
        else if(evt.equals(PswIntroducido)){
            
            if(event.getCharacter().equals(" ")){
                event.consume();
            }
        }
    }
    
    @FXML
    private void iniciarSesion(ActionEvent event){
        
        String usuario = UsrIntroducido.getText();
        String contrasena = PswIntroducido.getText();
        
        if(gestorBBDD.comprobarUsuario(usuario) == false){
            if(gestorBBDD.comprobarContrasena(usuario, contrasena)){
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
                } 
                catch (IOException e) {
                    e.printStackTrace(); // Manejo básico de excepciones, puedes cambiarlo según tu necesidad
                }
            }
            
            else{
                
                alerta.setText("Contraseña incorrecta");
                alerta.setVisible(true);
            }    
        }
        
        else{
            
            alerta.setText("Usuario incorrecto");
            alerta.setVisible(true);
        }
    }
        
        
    @FXML
    public static void muteMusic(ActionEvent event){
    
    }
    
    @FXML
    private void registrarUsuario(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/RegistroUsuario.fxml"));
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }
        
    
}
