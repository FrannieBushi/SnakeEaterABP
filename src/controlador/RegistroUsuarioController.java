/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Base64;
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
import javafx.stage.Stage;
import utilidades.bbdd.Bd;
import utilidades.bbdd.Gestor_conexion_POSTGRE;
import modelo.gestorBBDD;

/**
 * FXML Controller class
 *
 * @author minak
 */
public class RegistroUsuarioController implements Initializable {
    @FXML
    private TextField UsrIntroducido;
    
    @FXML
    private PasswordField PswIntroducido;
    
    @FXML
    private PasswordField PswIntroducidoDos;
    
    @FXML
    private Label Alerta;
    
    @FXML
    private void registrarUsuario(ActionEvent event){
        
        String usuario = UsrIntroducido.getText();
        String contrasena = PswIntroducido.getText();
        String contrasenaDos = PswIntroducidoDos.getText();
        String[][] consultaSiExisteUsuario;
        boolean verifContrasena = gestorBBDD.comprobarContrasenas(contrasena, contrasenaDos);
        
        if(usuario.isEmpty()){
            Alerta.setText("Introduce usuario");
            Alerta.setVisible(true);
        }
        
        else if(contrasena.isEmpty()){
            Alerta.setText("Introduce contraseña");
            Alerta.setVisible(true);   
        }
        
        else if(!usuario.isEmpty() && !contrasena.isEmpty() && verifContrasena){
            
            Gestor_conexion_POSTGRE gestor = new Gestor_conexion_POSTGRE("snakeeaterabp", true);
            
            if(gestorBBDD.comprobarUsuario(usuario)){
                Bd.consultaModificacion(gestor, "insert into jugadores(nick, contrasena, nivel) values('"+ usuario + "','" + gestorBBDD.encriptarPsw(contrasena) +"', 0);");
                Alerta.setText("Cuenta creada con éxito");
                Alerta.setVisible(true);
            }
            
            else{
                Alerta.setText("Ya existe este usuario");
                Alerta.setVisible(true);
            }
        }
        
        else{
            Alerta.setText("No coinciden las contrasenas");
            Alerta.setVisible(true);
        }
    }
    
    @FXML
    private void iniciarSesion(ActionEvent event){
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
