/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.vehitrade.controllers;

import ec.edu.espol.vehitrade.App;
import ec.edu.espol.vehitrade.model.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class InicioController implements Initializable {


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void registro(MouseEvent event) {
        try {
            App.setRoot("registro");
        } catch (IOException ex) {
         
        }
    }

    @FXML
    private void iniciarSesion(MouseEvent event) {
        ArrayList<Usuario> lista= Usuario.readListSer();
        System.out.println(lista);
        try {
            App.setRoot("iniciarSesion");
        } catch (IOException ex) {
           
        }
    }  
}
