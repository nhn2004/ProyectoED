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
import ec.edu.espol.vehitrade.model.Utilitaria;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class ComprarVehiculoController implements Initializable {

    @FXML
    private ComboBox<String> cbx;
    @FXML
    private CheckBox faño;
    @FXML
    private CheckBox frecorrido;
    @FXML
    private CheckBox fprecio;

    private DoublyCircularLinkedList<Vehiculo> vehiculos;
    private DoublyNodeList<Vehiculo> currentVehiculo1;
    private DoublyNodeList<Vehiculo> currentVehiculo2;
    private DoublyNodeList<Vehiculo> currentVehiculo3;
    private int n;
    private double maxAno;
    private double minAno;
    private double maxRecorrido;
    private double minRecorrido;
    private double maxPrecio;
    private double minPrecio;
    private String tipoVehiculoSeleccionado;

    private Usuario usuario;
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
        usuario = SessionManager.getInstance().getUsuarioActual();
        vehiculos = Vehiculo.quitarMisVehiculos(SessionManager.getInstance().getUsuarioActual().getVehiculos());
        // TODO
        faño.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                mostrarDialogoRango("Año", minAno, maxAno, (min, max) -> {
                    minAno = min;
                    maxAno = max;
                });
            }
        });

        frecorrido.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                mostrarDialogoRango("Recorrido", minRecorrido, maxRecorrido, (min, max) -> {
                    minRecorrido = min;
                    maxRecorrido = max;
                });
            }
        });

        fprecio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                mostrarDialogoRango("Precio", minPrecio, maxPrecio, (min, max) -> {
                    minPrecio = min;
                    maxPrecio = max;
                });
            }
        });
        String[] categorias = {"Auto", "Moto", "Camioneta", "Todos"};

        cbx.getItems().addAll(categorias);
        mostrarVehiculos(vehiculos);
    }

    @FXML
    private void cerrarSesion(MouseEvent event) {
        Stage stage = (Stage) cbx.getScene().getWindow();
        stage.setHeight(550);
        usuario.updateFile();
        SessionManager.getInstance().cerrarSesion();

        try {

            App.setRoot("iniciarSesion");
        } catch (IOException ex) {

        }
    }

    @FXML
    private void regresar(MouseEvent event) {
        Stage stage = (Stage) cbx.getScene().getWindow();
        stage.setHeight(550);
        try {

            App.setRoot("paginaUsuario");
        } catch (IOException ex) {

        }

    }

    @FXML
    private void filtrar(ActionEvent event) {
        ComboBox cb = (ComboBox) event.getSource();
        tipoVehiculoSeleccionado = (String) cb.getValue();
    }

    @FXML
    private void filtrarFinal(MouseEvent event) {
        DoublyCircularLinkedList<Vehiculo> veh = vehiculos;
        if (cbx.getValue() != null && !cbx.getValue().equals("Todos")) {
            veh = Utilitaria.filtrarTipoVehiculo(veh, tipoVehiculoSeleccionado);
        } else if (faño.isSelected()) {
            veh = Utilitaria.filtrarAño(veh, minAno, maxAno);
        } else if (frecorrido.isSelected()) {
            veh = Utilitaria.filtrarRecorrido(veh, minRecorrido, maxRecorrido);
        } else if (fprecio.isSelected()) {
            veh = Utilitaria.filtrarPrecio(veh, minPrecio, maxPrecio);
        }
        mostrarVehiculos(veh);
    }

    private void mostrarDialogoRango(String titulo, double valorMinimoActual, double valorMaximoActual, BiConsumer<Double, Double> callback) {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Filtrar por " + titulo);
        dialog.setHeaderText("Ingrese el rango para " + titulo);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType);

        TextField minTextField = new TextField();
        minTextField.setPromptText(String.valueOf(valorMinimoActual));
        TextField maxTextField = new TextField();
        maxTextField.setPromptText(String.valueOf(valorMaximoActual));

        GridPane grid = new GridPane();
        grid.add(new Label("Min:"), 0, 0);
        grid.add(minTextField, 1, 0);
        grid.add(new Label("Max:"), 0, 1);
        grid.add(maxTextField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> new Pair<>(minTextField.getText(), maxTextField.getText()));

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            try {
                double min = Double.parseDouble(pair.getKey());
                double max = Double.parseDouble(pair.getValue());
                callback.accept(min, max);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Valores inválidos");
                alert.show();
            }
        });
    }

    private void mostrarVehiculos(DoublyCircularLinkedList<Vehiculo> vehiculos) {
        carro1.getChildren().clear();
        carro2.getChildren().clear();
        carro3.getChildren().clear();
        n = vehiculos.size();
        if (n == 0) {

            Alert a = new Alert(Alert.AlertType.INFORMATION, "No hay vehiculos con esas caracteristicas por el momento");
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

        Image im = new Image(getClass().getResource("/ec/edu/espol/vehitrade/imagenes/" + v.getTipoVehiculo() + ".png").toString());
        ImageView img = new ImageView(im);
        img.setFitHeight(200);
        img.setFitWidth(250);

        Text datos = new Text("Marca: " + v.getMarca() + "\nModelo: " + v.getModelo() + "\nPlaca: " + v.getPlaca());
        datos.setWrappingWidth(300);
        datos.setTextAlignment(TextAlignment.CENTER);

        Button aceptar = new Button("Seleccionar");
        aceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
            SessionManager.getInstance().setVehiculoSeleccionado(v);
            Stage stage = (Stage) cbx.getScene().getWindow();
            stage.setHeight(620);
            try {
                App.setRoot("generarOferta");
            } catch (IOException ex) {
            }
        });

        cuadro.getChildren().addAll(img, datos, aceptar);

        return cuadro;
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
        //ab
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
        //ab
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
