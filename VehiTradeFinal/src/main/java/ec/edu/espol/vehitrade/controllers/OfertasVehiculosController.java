/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.vehitrade.controllers;

import ec.edu.espol.vehitrade.App;
import ec.edu.espol.vehitrade.model.Oferta;
import ec.edu.espol.vehitrade.model.SessionManager;
import ec.edu.espol.vehitrade.model.Usuario;
import ec.edu.espol.vehitrade.model.Vehiculo;
import ec.edu.espol.vehitrade.model.DoublyLinkedList;
import ec.edu.espol.vehitrade.model.Utilitaria;
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
public class OfertasVehiculosController implements Initializable {

    @FXML
    private Text textoPlaca;
    @FXML
    private Text textoCantOfertas;
    @FXML
    private Text textoPrecio;
    @FXML
    private HBox hoff;
    
    private Usuario usuario;
    private Vehiculo vehiculo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = SessionManager.getInstance().getUsuarioActual();
        vehiculo = SessionManager.getInstance().getVehiculoSeleccionado();
        mostrarOfertas(vehiculo.getOfertas());
        textoPlaca.setText(vehiculo.getPlaca());
        textoCantOfertas.setText("Tiene "+vehiculo.getOfertas().size()+" Ofertas");
        textoPrecio.setText(""+vehiculo.getPrecio());
        
        
    }    
    
    private void mostrarOfertas(DoublyLinkedList<Oferta> ofertas) {
        hoff.getChildren().clear();
        int i = 1;
        Iterator<Oferta> iterator = ofertas.iterator();
        while (iterator.hasNext()) {
            Oferta o = iterator.next();
            hoff.getChildren().add(crearVBoxOferta(o,i));
            i++;
            
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
        Text precio = new Text("Precio sugerido: " + o.getPrecio());
        precio.setWrappingWidth(300);
        precio.setTextAlignment(TextAlignment.CENTER);

        
        
        Button aceptar = new Button("Seleccionar");
                aceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Enviando correo electrónico. Espere, por favor.");
                    a.show();
                    try{
                        String mensaje= "Hola soy "+usuario.getNombre()+",\n"+"Es un placer hacer negocios contigo, mi "+vehiculo.getMarca()+" "+vehiculo.getModelo()+" pronto será tuyo, enviame un correo a "+usuario.getCorreoElectronico()+" para hablar";
                    
                        String asunto= "¡"+usuario.getNombre()+" acepto tu oferta!";
                        Utilitaria.enviarCorreo(o.getCorreoElectronicoComprador(), asunto, mensaje);
                        o.eliminar();
                        usuario.eliminarVehiculo(vehiculo);
                        vehiculo.eliminar();
                    
                    
                        App.setRoot("misVehiculos");
                    } catch (IOException ex) {
                    }
                });

        cuadro.getChildren().addAll(titulo, precio,aceptar);

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
            App.setRoot("misVehiculos");
        } catch (IOException ex) {
           
        }
    }
    
}
