/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.vehitrade.model;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import javafx.scene.control.Alert;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import javax.mail.*;
//import javax.mail.internet.*;

/**
 *
 * @author Dom
 */
public class Utilitaria {
    private static final String CORREO_ENVIO = "nhncevallos@gmail.com";
    private static final String CONTRASEÑA = "olbapqxbumuxutep";

    public static Usuario buscarUsuario(String correoElectronico){
        ArrayList<Usuario> lU= Usuario.readListSer();
      
        for (Usuario u:lU){
            if (u.getCorreoElectronico().equals(correoElectronico)){
                u.vincularOfertaUsuario();
                u.vincularVehiculoUsuario();
                return u;
            }
        }
        return null;
    }

    public static void eliminarOferta( Oferta o) {
        DoublyLinkedList<Oferta> ofertas = Oferta.readListSer();
        Iterator<Oferta> iterator = ofertas.iterator();
        while (iterator.hasNext()) {
            Oferta o1 = iterator.next();
            if(o1.equals(o)) {
                iterator.remove();
            }
        }
        Oferta.saveListSer(ofertas);
    }
    
    public static void eliminarVehiculo( Vehiculo v) {
        DoublyCircularLinkedList<Vehiculo> vehiculos = Vehiculo.readListSer();
        Iterator<Vehiculo> iterator = vehiculos.iterator();
        while (iterator.hasNext()) {
            Vehiculo v1 = iterator.next();
            if (v.getPlaca().equals(v1.getPlaca()) ) {
                    iterator.remove(); // Eliminamos el elemento actual de la lista
            }   
        }
        Vehiculo.saveListSer(vehiculos);
    }
    
    
    
    
    
    public static DoublyCircularLinkedList<Vehiculo> filtrarAño(DoublyCircularLinkedList<Vehiculo> vehiculos, double añoInicio, double añoFin){
        DoublyCircularLinkedList<Vehiculo> vehFiltrados = new DoublyCircularLinkedList<>();
        Iterator<Vehiculo> iterator = vehiculos.iterator();
        while(!iterator.hasNext()){
            Vehiculo veh = iterator.next();
            if(veh.getAño()<=añoFin && veh.getAño()>=añoInicio )
                vehFiltrados.addLast(veh);
        }
        return vehFiltrados;
    }
    
    public static DoublyCircularLinkedList<Vehiculo> filtrarRecorrido(DoublyCircularLinkedList<Vehiculo> vehiculos, double recorridoInicio, double recorridoFin){
        DoublyCircularLinkedList<Vehiculo> vehFiltrados = new DoublyCircularLinkedList<>();
        Iterator<Vehiculo> iterator = vehiculos.iterator();
        while(!iterator.hasNext()){
            Vehiculo veh = iterator.next();
            if(veh.getRecorrido()<=recorridoFin && veh.getRecorrido()>=recorridoInicio )
                vehFiltrados.addLast(veh);     
        }
        return vehFiltrados;
    }
    
    public static DoublyCircularLinkedList<Vehiculo> filtrarPrecio(DoublyCircularLinkedList<Vehiculo> vehiculos, double precioInicio, double precioFin){
        DoublyCircularLinkedList<Vehiculo> vehFiltrados = new DoublyCircularLinkedList<>();
        Iterator<Vehiculo> iterator = vehiculos.iterator();
        while(!iterator.hasNext()){
            Vehiculo veh = iterator.next();
            if(veh.getPrecio()<=precioFin && veh.getPrecio()>=precioInicio )
                vehFiltrados.addLast(veh);     
        }
        return vehFiltrados;
    }
    
    public static DoublyCircularLinkedList<Vehiculo> filtrarTipoVehiculo(DoublyCircularLinkedList<Vehiculo> vehiculos, String tipo){
        DoublyCircularLinkedList<Vehiculo> veh = new DoublyCircularLinkedList<>();
        if (tipo.equals("Todos"))
            return vehiculos;

        else{
            Iterator<Vehiculo> iterator = vehiculos.iterator();
            while(iterator.hasNext()){
                Vehiculo v = iterator.next();
                if(v.getTipoVehiculo().equals(tipo))
                veh.addLast(v);
            }
            
        }
        return veh;
    }

    public static void enviarCorreo(String correoDestino, String asunto, String mensaje){
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.starttls.enable", "true");
        

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(CORREO_ENVIO, CONTRASEÑA);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(CORREO_ENVIO));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(correoDestino)
            );
            message.setSubject(asunto);
            message.setText(mensaje);

            Transport.send(message);
            Alert a = new Alert(Alert.AlertType.INFORMATION,"Oferta aceptada correctamente!\nSu vehiculo sera vendido");
            a.show();

            

        } catch (MessagingException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Error al aceptar Oferta");
            a.show();
        } 
    }
}
