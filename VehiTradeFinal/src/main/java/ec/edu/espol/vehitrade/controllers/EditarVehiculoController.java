/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.vehitrade.controllers;

import ec.edu.espol.vehitrade.model.Vehiculo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarVehiculoController {

    @FXML
    private TextField placa;
    @FXML
    private TextField marca;
    @FXML
    private TextField modelo;
    @FXML
    private TextField color;
    @FXML
    private TextField año;
    @FXML
    private TextField recorrido;
    @FXML
    private TextField tipoMotor;
    @FXML
    private TextField tipoCombustible;
    @FXML
    private TextField precio;

    private Vehiculo vehiculo;
    private MisVehiculosController misVehiculosController;

    @FXML
    private void initialize() {
        // Inicialización si es necesaria
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        cargarDatosVehiculo();
    }

    public void setMisVehiculosController(MisVehiculosController controller) {
        this.misVehiculosController = controller;
    }

    private void cargarDatosVehiculo() {
        placa.setText(vehiculo.getPlaca());
        marca.setText(vehiculo.getMarca());
        modelo.setText(vehiculo.getModelo());
        color.setText(vehiculo.getColor());
        año.setText(String.valueOf(vehiculo.getAño()));
        recorrido.setText(String.valueOf(vehiculo.getRecorrido()));
        tipoMotor.setText(vehiculo.getTipoMotor());
        tipoCombustible.setText(vehiculo.getTipoCosmbustible());
        precio.setText(String.valueOf(vehiculo.getPrecio()));
    }

    @FXML
    private void guardarCambios() {
        try {
            vehiculo.setPlaca(placa.getText());
            vehiculo.setMarca(marca.getText());
            vehiculo.setModelo(modelo.getText());
            vehiculo.setColor(color.getText());
            vehiculo.setAño(Integer.parseInt(año.getText()));
            vehiculo.setRecorrido(Double.parseDouble(recorrido.getText()));
            vehiculo.setTipoMotor(tipoMotor.getText());
            vehiculo.setTipoCosmbustible(tipoCombustible.getText());
            vehiculo.setPrecio(Double.parseDouble(precio.getText()));
            vehiculo.updateFile();

            Stage stage = (Stage) placa.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error en los datos ingresados.");
            alert.show();
        }
    }
}
