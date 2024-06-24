/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.vehitrade.controllers;

import ec.edu.espol.vehitrade.App;
import ec.edu.espol.vehitrade.model.DoublyLinkedList;
import ec.edu.espol.vehitrade.model.Oferta;
import ec.edu.espol.vehitrade.model.SessionManager;
import ec.edu.espol.vehitrade.model.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class MisOfertasController implements Initializable {

    @FXML
    private HBox hoff;
    
    private Usuario usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        usuario = SessionManager.getInstance().getUsuarioActual();
        mostrarOfertas(usuario.getOfertas());
    }    
    
    private void mostrarOfertas(DoublyLinkedList<Oferta> ofertas) {
        hoff.getChildren().clear();
        Iterator<Oferta> iterator = ofertas.iterator();
    
        if (iterator.hasNext()) {
            Text t = new Text("No hay vehiculos :(");
            t.setWrappingWidth(500);
            t.setTextAlignment(TextAlignment.LEFT);
            hoff.getChildren().add(t);
        } else {
            int i = 1;
            while(!iterator.hasNext()){   
                Oferta o = iterator.next();
                hoff.getChildren().add(crearVBoxOferta(o,i));
                i++;
            }
        }
        
        
    }

    private VBox crearVBoxOferta(Oferta o, int i) {
        VBox cuadro = new VBox();
        cuadro.setSpacing(10);
        cuadro.setAlignment(Pos.CENTER);
        cuadro.setMinWidth(VBox.USE_PREF_SIZE);
        cuadro.setMaxWidth(VBox.USE_PREF_SIZE);
        cuadro.setMaxHeight(60);

        Text titulo = new Text("Oferta: " + i);
        titulo.setWrappingWidth(300);
        titulo.setTextAlignment(TextAlignment.CENTER);
        Text marca = new Text("Marca: " + o.getMarca());
        marca.setWrappingWidth(300);
        marca.setTextAlignment(TextAlignment.CENTER);
        Text modelo = new Text("Modelo: " + o.getModelo());
        modelo.setWrappingWidth(300);
        modelo.setTextAlignment(TextAlignment.CENTER);
        
        Text precio = new Text("Precio sugerido: " + o.getPrecio());
        precio.setWrappingWidth(300);
        precio.setTextAlignment(TextAlignment.CENTER);

        
        
        Button eliminar = new Button("Eliminar");
                eliminar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
                    usuario.eliminarOferta(o);
                    o.eliminar();
                    Alert a = new Alert(Alert.AlertType.INFORMATION,"Se elimino la oferta");
                });

        cuadro.getChildren().addAll(titulo,marca,modelo, precio,eliminar);

        return cuadro;
    }

    @FXML
    private void cerrarSesion(MouseEvent event) {
        usuario.updateFile();
        SessionManager.getInstance().cerrarSesion();
        try {  
            App.setRoot("iniciarSesion");
        } catch (IOException ex) {
           
        }
    }

    @FXML
    private void regresar(MouseEvent event) {
        try {
            App.setRoot("paginaUsuario");
        } catch (IOException ex) { 
        }
    }
    
}
