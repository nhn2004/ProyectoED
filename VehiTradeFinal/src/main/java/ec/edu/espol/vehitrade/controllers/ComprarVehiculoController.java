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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    @FXML
    private VBox carro1;
    @FXML
    private VBox carro2;
    @FXML
    private VBox carro3;

    private DoublyCircularLinkedList<Vehiculo> vehiculos;
    private DoublyCircularLinkedList<Vehiculo> vehiculosFavoritos;
    private DoublyNodeList<Vehiculo> currentVehiculo1;
    private DoublyNodeList<Vehiculo> currentVehiculo2;
    private DoublyNodeList<Vehiculo> currentVehiculo3;
    private int n;
    private double maxAno = Double.MAX_VALUE;
    private double minAno = Double.MIN_VALUE;
    private double maxRecorrido = Double.MAX_VALUE;
    private double minRecorrido = Double.MIN_VALUE;
    private double maxPrecio = Double.MAX_VALUE;
    private double minPrecio = Double.MIN_VALUE;
    private String tipoVehiculoSeleccionado;
    private boolean mostrandoFavoritos;

    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = SessionManager.getInstance().getUsuarioActual();
        System.out.println("Usuario actual: " + usuario.getId());

        vehiculos = Vehiculo.quitarMisVehiculos(usuario.getVehiculos());
        System.out.println("Vehículos después de quitar los del usuario:");

        for (Vehiculo v : vehiculos) {
            System.out.println(v.getPlaca());
        }

        vehiculosFavoritos = usuario.getFavoritos();
        mostrandoFavoritos = false;

        // Validar favoritos y actualizar estado de los vehículos
        validarFavoritos();

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


    private void validarFavoritos() {
        if (vehiculosFavoritos != null && vehiculosFavoritos.size() > 0) {
            Iterator<Vehiculo> itVehiculos = vehiculos.iterator();
            
            while (itVehiculos.hasNext()) {
                Vehiculo vehiculo = itVehiculos.next();
                Iterator<Vehiculo> itFavoritos = vehiculosFavoritos.iterator();
                while (itFavoritos.hasNext()) {
                    Vehiculo favorito = itFavoritos.next();
                    if (vehiculo.getPlaca().equals(favorito.getPlaca())) {
                        vehiculo.setFavorite(true);
                    }
                }
            }
        }
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
            ex.printStackTrace();
        }
    }

    @FXML
    private void regresar(MouseEvent event) {
        Stage stage = (Stage) cbx.getScene().getWindow();
        stage.setHeight(550);
        try {
            App.setRoot("paginaUsuario");
        } catch (IOException ex) {
            ex.printStackTrace();
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
        }
        if (faño.isSelected()) {
            veh = Utilitaria.filtrarAño(veh, minAno, maxAno);
        }
        if (frecorrido.isSelected()) {
            veh = Utilitaria.filtrarRecorrido(veh, minRecorrido, maxRecorrido);
        }
        if (fprecio.isSelected()) {
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
            Alert a = new Alert(Alert.AlertType.INFORMATION, "No hay vehículos con esas características por el momento");
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

        Image im = new Image("file:" + v.getDirImagen());
        ImageView img = new ImageView(im);

        img.setFitWidth(100);
        img.setPreserveRatio(true);

        Text datos = new Text("Marca: " + v.getMarca() + "\nModelo: " + v.getModelo() + "\nPlaca: " + v.getPlaca());
        datos.setWrappingWidth(100);
        datos.setTextAlignment(TextAlignment.CENTER);

        HBox botonesHBox = new HBox();
        botonesHBox.setSpacing(10);
        botonesHBox.setAlignment(Pos.CENTER);

        Button seleccionarBtn = new Button("Más info");

        seleccionarBtn.setOnAction(event -> {
            SessionManager.getInstance().setVehiculoSeleccionado(v);
            Stage stage = (Stage) cbx.getScene().getWindow();
            stage.setHeight(620);
            try {
                App.setRoot("generarOferta");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Button favoritoBtn = new Button();
        favoritoBtn.setStyle("-fx-background-color: white;");
        updateFavoriteButton(favoritoBtn, v.isFavorite());
        favoritoBtn.setOnAction(event -> {
            v.setFavorite(!v.isFavorite());
            if (v.isFavorite()) {
                usuario.addFavorito(v);
            } else {
                usuario.removeFavorito(v);
            }
            updateFavoriteButton(favoritoBtn, v.isFavorite());
        });

        botonesHBox.getChildren().addAll(seleccionarBtn, favoritoBtn);

        cuadro.getChildren().addAll(img, datos, botonesHBox);

        // Validar si el vehículo es favorito y actualizar el botón
        if (v.isFavorite()) {
            updateFavoriteButton(favoritoBtn, true);
        }

        return cuadro;
    }

    private void updateFavoriteButton(Button button, boolean isFavorite) {
        String imagePath = isFavorite ? "/ec/edu/espol/vehitrade/imagenes/heart_filled.png" : "/ec/edu/espol/vehitrade/imagenes/heart.png";
        ImageView heart = new ImageView(new Image(getClass().getResource(imagePath).toString()));
        heart.setFitWidth(20);
        heart.setPreserveRatio(true);
        button.setGraphic(heart);
    }

    private void actualizarVehiculos() {
        carro1.getChildren().clear();
        carro2.getChildren().clear();
        carro3.getChildren().clear();
        if (currentVehiculo1 != null) {
            carro1.getChildren().add(crearVBoxVehiculo(currentVehiculo1.getContent()));
        }
        if (currentVehiculo2 != null) {
            carro2.getChildren().add(crearVBoxVehiculo(currentVehiculo2.getContent()));
        }
        if (currentVehiculo3 != null) {
            carro3.getChildren().add(crearVBoxVehiculo(currentVehiculo3.getContent()));
        }
    }

    @FXML
    private void anterior(MouseEvent event) {
        if (n < 3) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "No hay suficientes vehículos");
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

    @FXML
    private void mostrarFavoritos(MouseEvent event) {
        mostrarVehiculos(vehiculosFavoritos);
    }

    @FXML
    private void MostrarTodos(MouseEvent event) {
        mostrarVehiculos(vehiculos);
    }
}