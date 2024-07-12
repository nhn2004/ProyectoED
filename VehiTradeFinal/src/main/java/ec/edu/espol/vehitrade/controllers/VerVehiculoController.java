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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VerVehiculoController implements Initializable {

    @FXML
    private ImageView vehiculoImage;
    @FXML
    private Text vehiculoDatos;

    private DoublyCircularLinkedList<Vehiculo> vehiculosFiltrados;
    private int currentIndex;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("VerVehiculoController inicializado.");
    }

    public void setVehiculos(DoublyCircularLinkedList<Vehiculo> vehiculosFiltrados, int index) {
        this.vehiculosFiltrados = vehiculosFiltrados;
        this.currentIndex = index;
        mostrarDetalleVehiculo();
    }

    private void mostrarDetalleVehiculo() {
        if (vehiculosFiltrados != null && !vehiculosFiltrados.isEmpty()) {
            Vehiculo vehiculo = vehiculosFiltrados.get(currentIndex);
            System.out.println("Mostrando detalles del vehículo: " + vehiculo);

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
        } else {
            System.out.println("Lista de vehículos filtrados es nula o está vacía.");
        }
    }

    @FXML
    private void cerrar(MouseEvent event) {
        Stage stage = (Stage) vehiculoDatos.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void anterior(MouseEvent event) {
<<<<<<< HEAD
        if (vehiculosFiltrados != null && vehiculosFiltrados.size() > 1) {
=======
        if (vehiculosFiltrados != null && !vehiculosFiltrados.isEmpty()) {
>>>>>>> 66a899dca045c5e5985842bb865df401bfcb06c3
            currentIndex = (currentIndex - 1 + vehiculosFiltrados.size()) % vehiculosFiltrados.size();
            mostrarDetalleVehiculo();
        } else {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "No hay suficientes vehiculos");
            a.show();
        }
    }

    @FXML
    private void siguiente(MouseEvent event) {
<<<<<<< HEAD
        if (vehiculosFiltrados != null && vehiculosFiltrados.size() > 1) {
=======
        if (vehiculosFiltrados != null && !vehiculosFiltrados.isEmpty()) {
>>>>>>> 66a899dca045c5e5985842bb865df401bfcb06c3
            currentIndex = (currentIndex + 1) % vehiculosFiltrados.size();
            mostrarDetalleVehiculo();
        } else {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "No hay suficientes vehiculos");
            a.show();
        }
    }
}
