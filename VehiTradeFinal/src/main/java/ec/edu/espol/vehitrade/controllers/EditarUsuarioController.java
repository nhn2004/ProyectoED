/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.vehitrade.controllers;

import ec.edu.espol.vehitrade.App;
import ec.edu.espol.vehitrade.model.SessionManager;
import ec.edu.espol.vehitrade.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class EditarUsuarioController implements Initializable {

    private Usuario usuario;
    @FXML
    private TextField cajaUno; // Nombre
    @FXML
    private TextField cajaDos; // Apellido
    @FXML
    private PasswordField cajaTres; // Contraseña
    @FXML
    private ImageView imagePerfil;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = SessionManager.getInstance().getUsuarioActual();
        cajaUno.setText(usuario.getNombre());
        cajaDos.setText(usuario.getApellidos());
        cajaTres.setText(usuario.getClave());
        
        // Cargar la imagen del usuario
        Image image = new Image("file:" + usuario.getDirImagen(),false);
        imagePerfil.setImage(image);
        imagePerfil.setPreserveRatio(false);
        imagePerfil.setFitHeight(150);
        imagePerfil.setFitWidth(150);
        double radius = imagePerfil.getFitWidth() / 2;
        Circle clip = new Circle(radius, radius, radius);
        imagePerfil.setClip(clip);
    }

    @FXML
    private void cambiarImagen(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) imagePerfil.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            try {
                // Copiar la imagen seleccionada a la carpeta del proyecto
                File destDir = new File("src/main/resources/ec/edu/espol/vehitrade/imagenes");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                String destFileName = "usuario_" + usuario.getId() + "_" + file.getName();
                File destFile = new File(destDir, destFileName);
                
                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
                // Actualizar la dirección de la imagen del usuario
                usuario.setDirImagen(destFile.getPath());
                
                // Cargar la nueva imagen
                Image image = new Image("file:" + destFile.getPath(), false);
                imagePerfil.setImage(image);
                imagePerfil.setPreserveRatio(false);
                imagePerfil.setFitHeight(150);
                imagePerfil.setFitWidth(150);
                
                // Guardar los cambios del usuario
                usuario.updateFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void guardarCambios(MouseEvent event) {
        
        try {
            usuario.setNombre(cajaUno.getText());
            usuario.setApellidos(cajaDos.getText());
            usuario.setClave(cajaTres.getText());
            usuario.updateFile();Stage stage = (Stage) cajaUno.getScene().getWindow();
            stage.setWidth(800); 
            stage.setHeight(600);
            
            App.setRoot("paginaUsuario");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void regresar(MouseEvent event) {
        try {
            Stage stage = (Stage) cajaUno.getScene().getWindow();
            stage.setWidth(800); 
            stage.setHeight(600);
            App.setRoot("paginaUsuario");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void cajaTres(ActionEvent event) {
    }
}  

