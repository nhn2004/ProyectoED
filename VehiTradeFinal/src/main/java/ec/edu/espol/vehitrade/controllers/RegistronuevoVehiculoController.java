/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.vehitrade.controllers;

import ec.edu.espol.vehitrade.App;
import ec.edu.espol.vehitrade.model.Auto;
import ec.edu.espol.vehitrade.model.Camioneta;
import ec.edu.espol.vehitrade.model.DoublyCircularLinkedList;
import ec.edu.espol.vehitrade.model.ObjetoExistente;
import ec.edu.espol.vehitrade.model.SessionManager;
import ec.edu.espol.vehitrade.model.Usuario;
import ec.edu.espol.vehitrade.model.Vehiculo;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 */public class RegistronuevoVehiculoController implements Initializable {

    @FXML
    private ComboBox<String> tipoVehiculo;
    @FXML
    private VBox registro1;
    @FXML
    private VBox registro2;
    @FXML
    private VBox general;
    @FXML
    private VBox registroPrecio;
    private TextField placa;
    private TextField marca;
    private TextField modelo;
    private TextField color;
    private TextField año;
    private TextField recorrido;
    private TextField tipoMotor;
    private TextField tipoCombustible;
    private TextField precio;
    private TextField vidrios;
    private TextField transmision;
    private TextField traccion;
    private TextField reparaciones;
    private TextField mantenimiento;
    private Usuario usuario;
    private String valor;
    private DoublyCircularLinkedList<String> dirImagen = new DoublyCircularLinkedList<>();
    private Text imagenSeleccionada;  // Texto para mostrar el nombre del archivo seleccionado
    private Button btnSeleccionarImagen;  // Botón para seleccionar la imagen

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = SessionManager.getInstance().getUsuarioActual();
        String[] categorias = {"Auto", "Moto", "Camioneta"};
        tipoVehiculo.getItems().addAll(categorias);
    }

    @FXML
    private void filtrar(ActionEvent event) {
        registro1.getChildren().clear();
        registro2.getChildren().clear();
        registroPrecio.getChildren().clear();
        ComboBox cb = (ComboBox) event.getSource();
        String s = (String) cb.getValue();
        valor = s;

        if (s.equals("Auto")) {
            Stage stage = (Stage) tipoVehiculo.getScene().getWindow();
            stage.setHeight(725);
            registroAuto();
        } else if (s.equals("Moto")) {
            Stage stage = (Stage) tipoVehiculo.getScene().getWindow();
            stage.setHeight(600);
            registroMoto();
        } else if (s.equals("Camioneta")) {
            Stage stage = (Stage) tipoVehiculo.getScene().getWindow();
            stage.setHeight(850);
            registroCamioneta();
        }
    }

    private void registroMoto() {
        Text Placa = new Text("Placa:");
        Placa.setFont(new Font(16));
        VBox.setMargin(Placa, new Insets(20, 0, 0, 0));
        TextField placa = new TextField();
        VBox.setMargin(placa, new Insets(5, 0, 0, 0));

        Text Color = new Text("Color:");
        Color.setFont(new Font(16));
        VBox.setMargin(Color, new Insets(20, 0, 0, 0));
        TextField color = new TextField();
        VBox.setMargin(color, new Insets(5, 0, 0, 0));

        Text Modelo = new Text("Modelo:");
        Modelo.setFont(new Font(16));
        VBox.setMargin(Modelo, new Insets(20, 0, 0, 0));
        TextField modelo = new TextField();
        VBox.setMargin(modelo, new Insets(5, 0, 0, 0));

        Text Marca = new Text("Marca:");
        Marca.setFont(new Font(16));
        VBox.setMargin(Marca, new Insets(20, 0, 0, 0));
        TextField marca = new TextField();
        VBox.setMargin(marca, new Insets(5, 0, 0, 0));

        Text Año = new Text("Año:");
        Año.setFont(new Font(16));
        VBox.setMargin(Año, new Insets(20, 0, 0, 0));
        TextField año = new TextField();
        VBox.setMargin(año, new Insets(5, 0, 0, 0));

        Text Recorrido = new Text("Recorrido:");
        Recorrido.setFont(new Font(16));
        VBox.setMargin(Recorrido, new Insets(20, 0, 0, 0));
        TextField recorrido = new TextField();
        VBox.setMargin(recorrido, new Insets(5, 0, 0, 0));

        Text TipoMotor = new Text("Tipo Motor:");
        TipoMotor.setFont(new Font(16));
        VBox.setMargin(TipoMotor, new Insets(20, 0, 0, 0));
        TextField tipoMotor = new TextField();
        VBox.setMargin(tipoMotor, new Insets(5, 0, 0, 0));

        Text TipoCombustible = new Text("Tipo Combustible:");
        TipoCombustible.setFont(new Font(16));
        VBox.setMargin(TipoCombustible, new Insets(20, 0, 0, 0));
        TextField tipoCombustible = new TextField();
        VBox.setMargin(tipoCombustible, new Insets(5, 0, 0, 0));

        Text Precio = new Text("Precio:");
        Precio.setFont(new Font(16));
        VBox.setMargin(Precio, new Insets(20, 0, 0, 0));
        TextField precio = new TextField();
        VBox.setMargin(precio, new Insets(5, 0, 0, 0));
        registroPrecio.getChildren().addAll(Precio, precio);
        this.precio = precio;

        Text Reparaciones = new Text("Reparaciones:");
        Reparaciones.setFont(new Font(16));
        VBox.setMargin(Reparaciones, new Insets(20, 0, 0, 0));
        TextField reparaciones = new TextField();
        VBox.setMargin(reparaciones, new Insets(5, 0, 0, 0));
        registroPrecio.getChildren().addAll(Reparaciones, reparaciones);
        this.reparaciones = reparaciones;

        Text Mantenimiento = new Text("Mantenimiento:");
        Mantenimiento.setFont(new Font(16));
        VBox.setMargin(Mantenimiento, new Insets(20, 0, 0, 0));
        TextField mantenimiento = new TextField();
        VBox.setMargin(mantenimiento, new Insets(5, 0, 0, 0));
        registroPrecio.getChildren().addAll(Mantenimiento, mantenimiento);
        this.mantenimiento = mantenimiento;

        registro1.getChildren().addAll(Placa, placa, Modelo, modelo, Año, año, TipoMotor, tipoMotor);
        registro2.getChildren().addAll(Color, color, Marca, marca, Recorrido, recorrido, TipoCombustible, tipoCombustible);
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.año = año;
        this.recorrido = recorrido;
        this.tipoMotor = tipoMotor;
        this.tipoCombustible = tipoCombustible;

        // Añadir botón para seleccionar imagen
        btnSeleccionarImagen = new Button("Seleccionar Imagen");
        btnSeleccionarImagen.setOnAction(e -> seleccionarImagen());
        VBox.setMargin(btnSeleccionarImagen, new Insets(20, 0, 0, 0));
        registroPrecio.getChildren().add(btnSeleccionarImagen);

        // Texto para mostrar el nombre del archivo seleccionado
        imagenSeleccionada = new Text("");
        VBox.setMargin(imagenSeleccionada, new Insets(5, 0, 0, 0));
        registroPrecio.getChildren().add(imagenSeleccionada);
    }

    public void registroAuto() {
        registroMoto();
        Text Vidrios = new Text("Vidrios:");
        Vidrios.setFont(new Font(16));
        VBox.setMargin(Vidrios, new Insets(20, 0, 0, 0));
        TextField vidrios = new TextField();
        VBox.setMargin(vidrios, new Insets(5, 0, 0, 0));

        Text Transmision = new Text("Transmision:");
        Transmision.setFont(new Font(16));
        VBox.setMargin(Transmision, new Insets(20, 0, 0, 0));
        TextField transmision = new TextField();
        VBox.setMargin(transmision, new Insets(5, 0, 0, 0));
        registro1.getChildren().addAll(Vidrios, vidrios);
        registro2.getChildren().addAll(Transmision, transmision);
        this.vidrios = vidrios;
        this.transmision = transmision;
    }

    public void registroCamioneta() {
        registroAuto();
        Text Traccion = new Text("Tracción:");
        Traccion.setFont(new Font(16));
        VBox.setMargin(Traccion, new Insets(20, 0, 0, 0));
        TextField traccion = new TextField();
        VBox.setMargin(traccion, new Insets(5, 0, 0, 0));
        registro1.getChildren().addAll(Traccion, traccion);
        this.traccion = traccion;
    }

    public void registrarVehiculo(Vehiculo v) {
        usuario.añadirVehiculo(v);
        v.updateFile();
    }

    @FXML
    public void regresar(MouseEvent event) {
        Stage stage = (Stage) tipoVehiculo.getScene().getWindow();
        stage.setHeight(550);
        try {
            App.setRoot("misVehiculos");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void ant() {
        Stage stage = (Stage) tipoVehiculo.getScene().getWindow();
        stage.setHeight(550);
        try {
            App.setRoot("misVehiculos");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void aceptar(MouseEvent event) {
        try {
            Vehiculo.verificarPlaca(placa.getText());
            String s = valor;
            if ((placa.getText().equals("")) || (modelo.getText().equals("")) || (marca.getText().equals("")) || (color.getText().equals("")) || (año.getText().equals("")) || (recorrido.getText().equals("")) || (tipoCombustible.getText().equals(""))) {
                Alert a = new Alert(Alert.AlertType.ERROR, "No escrito nada");
                a.show();
            } else {
                try {
                    double recorridodouble = Double.parseDouble(this.recorrido.getText());
                    int añoint = Integer.parseInt(this.año.getText());
                    double preciodouble = Double.parseDouble(this.precio.getText());
                    if (dirImagen.isEmpty()) {
                        switch (s) {
                            case "Moto":
                                dirImagen.addLast("\\Users\\nicol\\ProyectoED\\VehiTradeFinal\\src\\main\\resources\\ec\\edu\\espol\\vehitrade\\imagenes\\Moto.png");
                                break;
                            case "Auto":
                                dirImagen.addLast("\\Users\\nicol\\ProyectoED\\VehiTradeFinal\\src\\main\\resources\\ec\\edu\\espol\\vehitrade\\imagenes\\Auto.png");
                                break;
                            case "Camioneta":
                                dirImagen.addLast("\\Users\\nicol\\ProyectoED\\VehiTradeFinal\\src\\main\\resources\\ec\\edu\\espol\\vehitrade\\imagenes\\Camioneta.png");
                                break;
                        }
                    }
                    switch (s) {
                        case "Moto": {
                            Vehiculo v = new Vehiculo(s, placa.getText(), modelo.getText(), marca.getText(), tipoMotor.getText(), añoint, recorridodouble, color.getText(), tipoCombustible.getText(), preciodouble, usuario.getId(), dirImagen.getLast().getContent(), reparaciones.getText(), mantenimiento.getText());
                            registrarVehiculo(v);
                            ant();
                            break;
                        }
                        case "Auto": {
                            Vehiculo v = new Auto(s, placa.getText(), modelo.getText(), marca.getText(), tipoMotor.getText(), añoint, recorridodouble, color.getText(), tipoCombustible.getText(), preciodouble, usuario.getId(), vidrios.getText(), transmision.getText(), dirImagen.getLast().getContent(), reparaciones.getText(), mantenimiento.getText());
                            registrarVehiculo(v);
                            ant();
                            break;
                        }
                        case "Camioneta": {
                            Vehiculo v = new Camioneta(s, placa.getText(), modelo.getText(), marca.getText(), tipoMotor.getText(), añoint, recorridodouble, color.getText(), tipoCombustible.getText(), preciodouble, usuario.getId(), traccion.getText(), vidrios.getText(), transmision.getText(), dirImagen.getLast().getContent(), reparaciones.getText(), mantenimiento.getText());
                            registrarVehiculo(v);
                            ant();
                            break;
                        }
                        default:
                            break;
                    }

                } catch (NumberFormatException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Caracteres inválidos\n (Año, Recorrido o Precio no son numeros)");
                    a.show();
                    this.precio.setText("");
                    this.año.setText("");
                    this.recorrido.setText("");
                }
            }
        } catch (ObjetoExistente ex) {
            this.placa.setText("");
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }

    }

    private void seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) tipoVehiculo.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                // Copiar la imagen seleccionada a la carpeta del proyecto
                File destDir = new File("src/main/resources/ec/edu/espol/vehitrade/imagenes");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                String destFileName = "vehiculo_" + placa.getText() + "_" + file.getName();
                File destFile = new File(destDir, destFileName);

                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Añadir la dirección de la imagen a la lista de imágenes del vehículo
                dirImagen.addLast(destFile.getPath());

                // Mostrar el nombre del archivo seleccionado
                imagenSeleccionada.setText("Imagen seleccionada: " + file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}