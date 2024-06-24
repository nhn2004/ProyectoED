/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.vehitrade.controllers;

import ec.edu.espol.vehitrade.App;
import ec.edu.espol.vehitrade.model.DigitosInvalidos;
import ec.edu.espol.vehitrade.model.SessionManager;
import ec.edu.espol.vehitrade.model.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class IniciarSesionController implements Initializable {

    @FXML
    private TextField correo;
    @FXML
    private TextField contraseña;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void verificar(MouseEvent event) {
        try {
            Usuario u = Usuario.verificarUsuario( correo.getText(), contraseña.getText());
            SessionManager.getInstance().setUsuarioActual(u);
            try {
                App.setRoot("paginaUsuario");
            } catch (IOException ex) { }
        } catch (DigitosInvalidos ex) {
            Alert a= new Alert(Alert.AlertType.ERROR,ex.getMessage());
            a.show();
        }
    }



    @FXML
    private void regresar(MouseEvent event) {
        try {
            App.setRoot("inicio");
        } catch (IOException ex) {
           
        }
    }
}
