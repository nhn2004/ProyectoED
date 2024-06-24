/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.vehitrade.controllers;

import ec.edu.espol.vehitrade.App;
import ec.edu.espol.vehitrade.model.SessionManager;
import ec.edu.espol.vehitrade.model.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class PaginaUsuarioController implements Initializable {

    private Usuario usuario;
    @FXML
    private Text nombre;
    @FXML
    private Text apellido;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = SessionManager.getInstance().getUsuarioActual();
        nombre.setText(usuario.getNombre());
        apellido.setText(usuario.getApellidos());
    }  
    
    



    public Usuario getUsuario() {
        return usuario;
    }
    

    @FXML
    private void cerrarSesion(MouseEvent event) {
        usuario.updateFile();
        SessionManager.getInstance().cerrarSesion();
        try {
            
            App.setRoot("iniciarSesion");
        } catch (IOException ex) {
            ex.printStackTrace();
           
        }
    }

    @FXML
    private void misVehiculos(MouseEvent event) {
        try {
            App.setRoot("misVehiculos");
        } catch (IOException ex) {
           
        }
        
    }

    @FXML
    private void comprar(MouseEvent event) {
        Stage stage = (Stage) nombre.getScene().getWindow();
        stage.setHeight(620);
        try {
            App.setRoot("comprarVehiculo");
        } catch (IOException ex) {
           
        }
    }

    @FXML
    private void misOfertas(MouseEvent event) {
        try {
            App.setRoot("misOfertas");
        } catch (IOException ex) {
           
        }
    }  

    @FXML
    private void mostrarUsuario(MouseEvent event) {
    }
}
