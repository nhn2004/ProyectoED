/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.vehitrade.controllers;

import ec.edu.espol.vehitrade.App;
import ec.edu.espol.vehitrade.model.DoublyCircularLinkedList;
import ec.edu.espol.vehitrade.model.DoublyNodeList;
import ec.edu.espol.vehitrade.model.SessionManager;
import ec.edu.espol.vehitrade.model.Usuario;
import ec.edu.espol.vehitrade.model.Vehiculo;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class MisVehiculosController implements Initializable {

    @FXML
    private ComboBox<String> cbx;
    
    private Usuario usuario;
    
    
    private DoublyCircularLinkedList<Vehiculo> vehiculos;
    @FXML
    private VBox carro1;
    @FXML
    private VBox carro2;
    @FXML
    private VBox carro3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        usuario=SessionManager.getInstance().getUsuarioActual();
        vehiculos = SessionManager.getInstance().getUsuarioActual().getVehiculos();
        String[] categorias = {"Auto","Moto","Camioneta","Todos"};
        
        cbx.getItems().addAll(categorias);
        mostrarVehiculos(vehiculos);
        
        
    }    
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario u) {
        
        usuario = u;
        
        vehiculos = u.getVehiculos();
        
        
    }
    
    private void mostrarVehiculos(DoublyCircularLinkedList<Vehiculo> vehiculos) {
        carro1.getChildren().clear();
        carro1.getChildren().clear();
        carro1.getChildren().clear();
        Iterator<Vehiculo> iterator = vehiculos.iterator();
        if (!iterator.hasNext()) {
            
            Alert a= new Alert(Alert.AlertType.ERROR,"No hay vehiculos por el momento, ingresa uno");
            a.show();
        } else {
            Vehiculo v;
            
            
            if(iterator.hasNext()){
                v = iterator.next();
                carro1.getChildren().add(crearVBoxVehiculo(v));
            }
            else if(iterator.hasNext()){
                v = iterator.next();
                carro2.getChildren().add(crearVBoxVehiculo(v));
            }
            else if(iterator.hasNext()){
                v = iterator.next();
                carro3.getChildren().add(crearVBoxVehiculo(v));
            }
            
            
        }
    }

//    private VBox crearVBoxVehiculo(Vehiculo v) {
//        VBox cuadro = new VBox();
//        cuadro.setSpacing(10);
//        cuadro.setAlignment(Pos.CENTER);
//        cuadro.setMinWidth(VBox.USE_PREF_SIZE);
//        cuadro.setMaxWidth(VBox.USE_PREF_SIZE);
//        cuadro.setMaxHeight(60);
//        
//        
//        
//
//        Image im = new Image(getClass().getResource("/ec/edu/espol/vehitrade/imagenes/"+v.getTipoVehiculo()+".png").toString());
//        ImageView img = new ImageView(im);
//        img.setFitHeight(200);
//        img.setFitWidth(250);
//        img.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
//            Alert a = new Alert(Alert.AlertType.INFORMATION, v.toString());
//            a.show();
//        });
//
//        Text datos = new Text("Marca: " + v.getMarca() + "\nModelo: " + v.getModelo() + "\nPlaca: " + v.getPlaca());
//        datos.setWrappingWidth(300);
//        datos.setTextAlignment(TextAlignment.CENTER);
//        if (v.getOfertas().size()>0){
//            Button aceptar = new Button(""+v.getOfertas().size()+" Ofertas");
//            aceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
//                SessionManager.getInstance().setVehiculoSeleccionado(v);
//                    
//                try {
//                    App.setRoot("ofertasVehiculos");
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            });
//            cuadro.getChildren().addAll(aceptar,img, datos);
//                
//        }
//        else
//            cuadro.getChildren().addAll(img, datos);
//        
//
//        return cuadro;
//    }
    
    private VBox crearVBoxVehiculo(Vehiculo v) {
    VBox cuadro = new VBox();
    cuadro.setSpacing(10);
    cuadro.setAlignment(Pos.CENTER);
    cuadro.setMinWidth(VBox.USE_PREF_SIZE);
    cuadro.setMaxWidth(VBox.USE_PREF_SIZE);
    cuadro.setMaxHeight(60);

    Image im = new Image(getClass().getResource("/ec/edu/espol/vehitrade/imagenes/" + v.getTipoVehiculo() + ".png").toString());
    ImageView img = new ImageView(im);
    img.setFitHeight(200);
    img.setFitWidth(250);
    img.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
        Alert a = new Alert(Alert.AlertType.INFORMATION, v.toString());
        a.show();
    });

    Text datos = new Text("Marca: " + v.getMarca() + "\nModelo: " + v.getModelo() + "\nPlaca: " + v.getPlaca());
    datos.setWrappingWidth(300);
    datos.setTextAlignment(TextAlignment.CENTER);

    // VBox para los dos botones
    HBox botonesHBox = new HBox();
    botonesHBox.setSpacing(5);
    botonesHBox.setAlignment(Pos.CENTER);

    Button verDetalleBtn = new Button("Ver Detalle");
    verDetalleBtn.setOnAction(event -> {
        // Acción a realizar al hacer clic en "Ver Detalle"
    });

    Button editarBtn = new Button("Editar");
    editarBtn.setOnAction(event -> {
        // Acción a realizar al hacer clic en "Editar"
    });

    botonesHBox.getChildren().addAll(verDetalleBtn, editarBtn);

    if (!v.getOfertas().isEmpty()) {
        Button aceptar = new Button("" + v.getOfertas().size() + " Ofertas");
        aceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
            SessionManager.getInstance().setVehiculoSeleccionado(v);

            try {
                App.setRoot("ofertasVehiculos");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cuadro.getChildren().addAll(aceptar, img, datos, botonesHBox);
    } else {
        cuadro.getChildren().addAll(img, datos, botonesHBox);
    }

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

    @FXML
    private void registrarNuevoVehiculo(MouseEvent event) {
        try {
            
            App.setRoot("registronuevoVehiculo");
        } catch (IOException ex) {
           
        }
    }

    @FXML
    private void filtrar(ActionEvent event) {
        ComboBox cb = (ComboBox)event.getSource();
        String s = (String)cb.getValue();
        DoublyCircularLinkedList<Vehiculo> veh = new DoublyCircularLinkedList<>();
        if (s.equals("Todos"))
            mostrarVehiculos(vehiculos);
        else{
        
            Iterator<Vehiculo> iterator = vehiculos.iterator();
            while(!iterator.hasNext()){
                Vehiculo v = iterator.next();
                if(v.getTipoVehiculo().equals(s))
                veh.addLast(v);
            }
        mostrarVehiculos(veh);
        }
    }

    @FXML
    private void anterior(MouseEvent event) {
    }

    @FXML
    private void siguiente(MouseEvent event) {
    }
    
}
