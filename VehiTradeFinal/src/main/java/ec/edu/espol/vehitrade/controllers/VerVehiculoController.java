/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package ec.edu.espol.vehitrade.controllers;

import ec.edu.espol.vehitrade.model.Auto;
import ec.edu.espol.vehitrade.model.Camioneta;
import ec.edu.espol.vehitrade.model.DoublyCircularLinkedList;
import ec.edu.espol.vehitrade.model.DoublyNodeList;
import ec.edu.espol.vehitrade.model.Vehiculo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class VerVehiculoController implements Initializable {

    @FXML
    private ImageView vehiculoImage;
    @FXML
    private Text vehiculoDatos;

    private DoublyCircularLinkedList<Vehiculo> vehiculosFiltrados;
    private int currentIndex;
    private DoublyNodeList<String> currentImageNode;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic if needed
    }

    public void setVehiculos(DoublyCircularLinkedList<Vehiculo> vehiculosFiltrados, int index) {
        this.vehiculosFiltrados = vehiculosFiltrados;
        this.currentIndex = index;
        mostrarDetalleVehiculo();
    }

    private void mostrarDetalleVehiculo() {
        if (vehiculosFiltrados != null && !vehiculosFiltrados.isEmpty()) {
            Vehiculo vehiculo = getVehiculoAt(currentIndex);
            if (vehiculo != null) {
                currentImageNode = vehiculo.getRutasImagenes().getLast();
                mostrarImagenVehiculo(vehiculo);
                String detalles = "Marca: " + vehiculo.getMarca() +
                        "\nModelo: " + vehiculo.getModelo() +
                        "\nPlaca: " + vehiculo.getPlaca() +
                        "\nAño: " + vehiculo.getAño() +
                        "\nRecorrido: " + vehiculo.getRecorrido() +
                        "\nColor: " + vehiculo.getColor() +
                        "\nTipo de Combustible: " + vehiculo.getTipoCosmbustible() +
                        "\nPrecio: " + vehiculo.getPrecio() +
                        "\nReparaciones: " + vehiculo.getReparaciones() +
                        "\nMantenimiento: " + vehiculo.getMantenimiento() +
                        "\nVidrios: " + (vehiculo instanceof Auto ? ((Auto) vehiculo).getVidrios() : "") +
                        "\nTransmisión: " + (vehiculo instanceof Auto ? ((Auto) vehiculo).getTransmision() : (vehiculo instanceof Camioneta ? ((Camioneta) vehiculo).getTransmision() : ""));
                if (vehiculo instanceof Camioneta) {
                    detalles += "\nTracción: " + ((Camioneta) vehiculo).getTraccion();
                }
                vehiculoDatos.setText(detalles);

                // Ajustar el tamaño de la ventana después de que la escena esté completamente cargada
                vehiculoDatos.sceneProperty().addListener((obs, oldScene, newScene) -> {
                    if (newScene != null) {
                        newScene.windowProperty().addListener((obsWindow, oldWindow, newWindow) -> {
                            if (newWindow != null) {
                                ((Stage) newWindow).sizeToScene();
                            }
                        });
                    }
                });
            }
        }
    }

    private void mostrarImagenVehiculo(Vehiculo vehiculo) {
        if (vehiculo.getRutasImagenes().isEmpty()) {
            vehiculoImage.setImage(null);
        } else if (currentImageNode != null) {
            String rutaImagen = currentImageNode.getContent();
            File file = new File(rutaImagen);
            if (file.exists()) {
                Image im = new Image(file.toURI().toString(), 400, 300, true, true); // Ajustar tamaño de imagen aquí
                vehiculoImage.setImage(im);
            } else {
                vehiculoImage.setImage(null);
            }
        }
    }

    private Vehiculo getVehiculoAt(int index) {
        if (vehiculosFiltrados == null || index < 0 || index >= vehiculosFiltrados.size()) {
            return null;
        }
        return vehiculosFiltrados.get(index);
    }

    @FXML
    private void cerrar(MouseEvent event) {
        Stage stage = (Stage) vehiculoDatos.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void anteriorImagen(MouseEvent event) {
        if (currentImageNode != null) {
            currentImageNode = currentImageNode.getPrevious();
            mostrarImagenVehiculo(getVehiculoAt(currentIndex));
        }
    }

    @FXML
    private void siguienteImagen(MouseEvent event) {
        if (currentImageNode != null) {
            currentImageNode = currentImageNode.getNext();
            mostrarImagenVehiculo(getVehiculoAt(currentIndex));
        }
    }

    @FXML
    private void editarInfo(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ec/edu/espol/vehitrade/editarVehiculo.fxml"));
            Parent root = loader.load();

            EditarVehiculoController controller = loader.getController();
            controller.setVehiculo(getVehiculoAt(currentIndex));
            controller.setMisVehiculosController(null); // Opcional: Ajustar según sea necesario

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            mostrarDetalleVehiculo();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void añadirImagen(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        File archivoSeleccionado = fileChooser.showOpenDialog(vehiculoDatos.getScene().getWindow());
        if (archivoSeleccionado != null) {
            Vehiculo vehiculo = getVehiculoAt(currentIndex);
            if (vehiculo != null) {
                String rutaImagen = "src/main/resources/ec/edu/espol/vehitrade/imagenes/" + archivoSeleccionado.getName();
                try {
                    Files.copy(archivoSeleccionado.toPath(), new File(rutaImagen).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    vehiculo.añadirImagen(rutaImagen);
                    currentImageNode = vehiculo.getRutasImagenes().getLast();
                    mostrarImagenVehiculo(vehiculo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void eliminarImagen(MouseEvent event) {
        Vehiculo vehiculo = getVehiculoAt(currentIndex);
        if (vehiculo != null && currentImageNode != null) {
            String rutaImagen = currentImageNode.getContent();
            vehiculo.eliminarImagen(rutaImagen);
            currentImageNode = vehiculo.getRutasImagenes().getLast();
            mostrarImagenVehiculo(vehiculo);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No hay imágenes para eliminar.");
            alert.show();
        }
    }
}