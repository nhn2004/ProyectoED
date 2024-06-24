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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class GenerarOfertaController implements Initializable {

    @FXML
    private Text info;
    @FXML
    private ImageView imagen;
    private Vehiculo vehiculo;
    private Usuario usuario;
    @FXML
    private HBox precioAct;
    @FXML
    private HBox precioOff;
    @FXML
    private VBox hboton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = SessionManager.getInstance().getUsuarioActual();
        vehiculo = SessionManager.getInstance().getVehiculoSeleccionado();
        Image im = new Image(getClass().getResource("/ec/edu/espol/vehitrade/imagenes/"+vehiculo.getTipoVehiculo()+".png").toString());
        imagen.setImage(im);
        info.setText(vehiculo.toString());
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
            App.setRoot("comprarVehiculo");
        } catch (IOException ex) { 
        }
    }

    @FXML
    private void seleccionar(MouseEvent event) {
        Text precioActual = new Text("Precio Actual: $"+vehiculo.getPrecio());
        precioActual.setFont(new Font(16));
        Text tnuevoPrecio = new Text("Nuevo Precio:");
        tnuevoPrecio.setFont(new Font(16));
        TextField precioNuevo = new TextField();
        HBox.setMargin(precioNuevo, new Insets(0, 0, 0, 10));
        precioAct.getChildren().add(precioActual);
        precioOff.getChildren().addAll(tnuevoPrecio,precioNuevo);
        Button aceptar = new Button("Aceptar");
                aceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
                    
                    if(!precioNuevo.getText().equals("")){
                        try{
                            double preciodouble = Double.parseDouble(precioNuevo.getText());
                            Oferta oferta = new Oferta(preciodouble,usuario.getId(),vehiculo.getPlaca(),usuario.getCorreoElectronico(),vehiculo.getMarca(),vehiculo.getModelo());
                            oferta.updateFile();
                            usuario.añadirOferta(oferta);
                            vehiculo.añadirOferta(oferta);
                            vehiculo.updateFile();
                        }
                        catch(NumberFormatException e){
                            Alert a = new Alert(Alert.AlertType.ERROR,"Precio no válido");
                            a.show();
                            precioNuevo.setText("");

                        }
                        try {
                            
                            App.setRoot("comprarVehiculo");
                        } catch (IOException ex) {
                        }
                    }
                    else{
                        Alert a = new Alert(Alert.AlertType.ERROR,"Ingrese un valor");
                        a.show(); 
                    }
                    
                });
        hboton.getChildren().add(aceptar);
        
        
        
        
    }
    
}
