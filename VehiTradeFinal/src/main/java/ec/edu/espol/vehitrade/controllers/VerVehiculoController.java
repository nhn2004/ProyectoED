/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.vehitrade.controllers;

import ec.edu.espol.vehitrade.model.DoublyCircularLinkedList;
import ec.edu.espol.vehitrade.model.DoublyNodeList;
import ec.edu.espol.vehitrade.model.Vehiculo;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author abrahamcedeno
 */
public class VerVehiculoController implements Initializable {

    @FXML
    private ImageView vehiculoImage;
    @FXML
    private Text vehiculoDatos;

    private DoublyCircularLinkedList<Vehiculo> vehiculosFiltrados;
    private int currentIndex;

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
                File file = new File("src/ec/edu/espol/vehitrade/ImagenesVehículos/" + vehiculo.getPlaca() + ".png");
                Image im = new Image(file.toURI().toString());
                vehiculoImage.setImage(im);

                vehiculoDatos.setText(
                    "Marca: " + vehiculo.getMarca() +
                    "\nModelo: " + vehiculo.getModelo() +
                    "\nPlaca: " + vehiculo.getPlaca() +
                    "\nAño: " + vehiculo.getAño() +
                    "\nRecorrido: " + vehiculo.getRecorrido() +
                    "\nColor: " + vehiculo.getColor() +
                    "\nTipo de Combustible: " + vehiculo.getTipoCosmbustible() +
                    "\nPrecio: " + vehiculo.getPrecio()
                );
            }
        }
    }

    private Vehiculo getVehiculoAt(int index) {
        if (vehiculosFiltrados == null || index < 0 || index >= vehiculosFiltrados.size()) {
            return null;
        }
        DoublyNodeList<Vehiculo> node = vehiculosFiltrados.getLast().getNext();
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        return node.getContent();
    }

    @FXML
    private void cerrar(MouseEvent event) {
        Stage stage = (Stage) vehiculoDatos.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void anterior(MouseEvent event) {
        if (currentIndex > 0) {
            currentIndex--;
            mostrarDetalleVehiculo();
        }
    }

    @FXML
    private void siguiente(MouseEvent event) {

        if (currentIndex < vehiculosFiltrados.size() - 1) {
            currentIndex++;

            mostrarDetalleVehiculo();
        }
    }
}
