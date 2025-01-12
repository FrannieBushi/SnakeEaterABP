package modelo;

import java.security.MessageDigest;
import java.util.Base64;
import javafx.fxml.FXML;
import utilidades.bbdd.Bd;
import utilidades.bbdd.Gestor_conexion_POSTGRE;

public class gestorBBDD {
    
    private String nombre;
    public gestorBBDD(String nombre){
        this.nombre = nombre;
        
    }
    
    public static String encriptarPsw(String contrasena){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(contrasena.getBytes());
            return Base64.getEncoder().encodeToString(hash);   
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean comprobarContrasena(String usuario, String contrasena){
        
        Gestor_conexion_POSTGRE gestor = new Gestor_conexion_POSTGRE("snakeeaterabp", true);
        String psw = encriptarPsw(contrasena);
        String[][] pswBBDD = Bd.consultaSelect(gestor, "select contrasena from jugadores where nick = " + "'" + usuario + "'");
        
        System.out.println(psw);
        
        if (pswBBDD[0][0].equals(psw)){
            return true;
        }
      
        return false;
    }
    
    public static boolean comprobarContrasenas(String pswUno, String pswDos){
        
        String contrasenaUno = pswUno;
        String contrasenaDos = pswDos;
        
        if(contrasenaUno.equals(contrasenaDos)){
            
            return true;
        }
        return false;
    }
    
    public static boolean comprobarUsuario(String usuario){
        String [][] vec;
        Gestor_conexion_POSTGRE gestor = new Gestor_conexion_POSTGRE("snakeeaterabp", true);
        vec = Bd.consultaSelect(gestor, "select nick from jugadores");
        
        if(vec!=null){
            for(int i=0; i < vec.length; i++){
                for(int j=0; j<vec[0].length; j++ ){
                    if(vec[i][j].equals(usuario))
                        return false;  
                }
            }
        }
        
        return true;
    }

     public static boolean comprobarIdPartida(int idPartida){
        String [][] vec;
        Gestor_conexion_POSTGRE gestor = new Gestor_conexion_POSTGRE("snakeeaterabp", true);
        vec = Bd.consultaSelect(gestor, "select partida_id from partidas");
        
        if(vec!=null){
            for(int i=0; i < vec.length; i++){
                for(int j=0; j<vec[0].length; j++ ){
                    if(vec[i][j].equals(idPartida))
                        return false;  
                }
            }
        }
        
        return true;
    }
}
