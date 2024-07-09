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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
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
    private DoublyNodeList<Vehiculo> currentVehiculo1;
    private DoublyNodeList<Vehiculo> currentVehiculo2;
    private DoublyNodeList<Vehiculo> currentVehiculo3;

    private int n;
    @FXML
    private VBox carro1;
    @FXML
    private VBox carro2;
    @FXML
    private VBox carro3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = SessionManager.getInstance().getUsuarioActual();
        vehiculos = SessionManager.getInstance().getUsuarioActual().getVehiculos();
        n = vehiculos.size();
        System.out.println(n);
        String[] categorias = {"Auto", "Moto", "Camioneta", "Todos"};

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
        carro2.getChildren().clear();
        carro3.getChildren().clear();
        n = vehiculos.size();
        if (n == 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "No hay vehiculos por el momento, ingresa uno");
            a.show();
        } else {
            if (n == 1) {
                currentVehiculo1 = vehiculos.getLast().getNext();
                carro1.getChildren().add(crearVBoxVehiculo(currentVehiculo1.getContent()));
            }
            if (n == 2) {
                currentVehiculo1 = vehiculos.getLast().getNext();
                currentVehiculo2 = currentVehiculo1.getNext();
                carro1.getChildren().add(crearVBoxVehiculo(currentVehiculo1.getContent()));
                carro2.getChildren().add(crearVBoxVehiculo(currentVehiculo2.getContent()));
            }
            if (n >= 3) {
                currentVehiculo1 = vehiculos.getLast().getNext();
                currentVehiculo2 = currentVehiculo1.getNext();
                currentVehiculo3 = currentVehiculo2.getNext();
                carro3.getChildren().add(crearVBoxVehiculo(currentVehiculo3.getContent()));
                carro1.getChildren().add(crearVBoxVehiculo(currentVehiculo1.getContent()));
                carro2.getChildren().add(crearVBoxVehiculo(currentVehiculo2.getContent()));
            }
        }
    }

    private VBox crearVBoxVehiculo(Vehiculo v) {
        VBox cuadro = new VBox();
        cuadro.setSpacing(10);
        cuadro.setAlignment(Pos.CENTER);
        cuadro.setMinWidth(VBox.USE_PREF_SIZE);
        cuadro.setMaxWidth(VBox.USE_PREF_SIZE);
        cuadro.setMaxHeight(60);

        File file = new File("src/ec/edu/espol/vehitrade/ImagenesVehículos/" + v.getPlaca() + ".png");
        Image im = new Image(file.toURI().toString());
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

        HBox botonesHBox = new HBox();
        botonesHBox.setSpacing(5);
        botonesHBox.setAlignment(Pos.CENTER);

        Button verDetalleBtn = new Button("Ver Detalle");
        verDetalleBtn.setOnAction(event -> {
            mostrarDetalleVehiculo(v);
        });

        Button editarBtn = new Button("Editar");
        editarBtn.setOnAction(event -> {
            mostrarEditarVehiculo(v);
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

    private void mostrarDetalleVehiculo(Vehiculo v) {
        Stage detalleStage = new Stage();
        detalleStage.initModality(Modality.APPLICATION_MODAL);
        detalleStage.setTitle("Detalle del Vehículo");

        VBox detalleVBox = new VBox();
        detalleVBox.setSpacing(10);
        detalleVBox.setPadding(new Insets(20));
        detalleVBox.setAlignment(Pos.CENTER);

        Label detalleLabel = new Label(v.toString());
        detalleVBox.getChildren().add(detalleLabel);

        Scene detalleScene = new Scene(detalleVBox, 400, 300);
        detalleStage.setScene(detalleScene);
        detalleStage.showAndWait();
    }

    private void mostrarEditarVehiculo(Vehiculo v) {
        try {
            URL fxmlLocation = getClass().getResource("/ec/edu/espol/vehitrade/editarVehiculo.fxml");
            if (fxmlLocation == null) {
                System.out.println("El archivo FXML no existe en la ruta especificada.");
                return;
            }
            System.out.println("FXML Location: " + fxmlLocation);

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            EditarVehiculoController controller = loader.getController();
            controller.setVehiculo(v);
            controller.setMisVehiculosController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Actualizamos la lista de vehículos después de la edición
            mostrarVehiculos(vehiculos);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        ComboBox cb = (ComboBox) event.getSource();
        String s = (String) cb.getValue();
        DoublyCircularLinkedList<Vehiculo> veh = new DoublyCircularLinkedList<>();
        if (s.equals("Todos")) {
            mostrarVehiculos(vehiculos);
        } else {
            Iterator<Vehiculo> iterator = vehiculos.iterator();
            while (iterator.hasNext()) {
                Vehiculo v = iterator.next();
                if (v.getTipoVehiculo().equals(s)) {
                    veh.addLast(v);
                }
            }
            mostrarVehiculos(veh);
        }
    }

    private void actualizarVehiculos() {
        carro1.getChildren().clear();
        carro2.getChildren().clear();
        carro3.getChildren().clear();
        carro1.getChildren().add(crearVBoxVehiculo(currentVehiculo1.getContent()));
        carro2.getChildren().add(crearVBoxVehiculo(currentVehiculo2.getContent()));
        carro3.getChildren().add(crearVBoxVehiculo(currentVehiculo3.getContent()));
    }

    @FXML
    private void anterior(MouseEvent event) {
        if (n < 3) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "No hay suficientes vehiculos");
            a.show();
        } else {
            currentVehiculo1 = currentVehiculo1.getPrevious();
            currentVehiculo2 = currentVehiculo2.getPrevious();
            currentVehiculo3 = currentVehiculo3.getPrevious();
            actualizarVehiculos();
        }
    }

    @FXML
    private void siguiente(MouseEvent event) {
        if (n < 3) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "No hay suficientes vehiculos");
            a.show();
        } else {
            currentVehiculo1 = currentVehiculo1.getNext();
            currentVehiculo2 = currentVehiculo2.getNext();
            currentVehiculo3 = currentVehiculo3.getNext();
            actualizarVehiculos();
        }
    }
}
