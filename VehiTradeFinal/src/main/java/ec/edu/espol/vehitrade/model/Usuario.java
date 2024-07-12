/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.vehitrade.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.Alert;


/**
 *
 * @author HP
 */
public class Usuario implements Serializable {
    private final int id;
    private String nombre;
    private String apellidos;
    private String organizacion;
    private String correoElectronico;
    private String clave;
    private DoublyLinkedList<Oferta> ofertas;
    private DoublyCircularLinkedList<Vehiculo> vehiculos;
    private String dirImagen; 
    private DoublyCircularLinkedList<Vehiculo> favoritos;
    
    public Usuario(String nombre, String apellidos, String organizacion, String correoElectronico, String clave) {
        this.id = Usuario.nextId();
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.organizacion = organizacion;
        this.correoElectronico = correoElectronico;
        this.clave = clave;
        this.ofertas = new DoublyLinkedList<>();
        this.vehiculos = new DoublyCircularLinkedList<>();
        this.dirImagen = "C:\\Users\\nicol\\ProyectoED\\VehiTradeFinal\\src\\main\\resources\\ec\\edu\\espol\\vehitrade\\imagenes\\usuario.png";
        this.favoritos = new DoublyCircularLinkedList<>();
        
    }

    public String getDirImagen() {
        return dirImagen;
    }

    public void setDirImagen(String dirImagen) {
        this.dirImagen = dirImagen;
    }

    public int getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }
    
    public void añadirVehiculo(Vehiculo v){
        this.vehiculos.addLast(v);
    }
    
    public void añadirOferta(Oferta o){
        this.ofertas.addLast(o);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public DoublyLinkedList<Oferta> getOfertas() {
        return ofertas;
    }

    public void setOfertas(DoublyLinkedList<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

    public DoublyCircularLinkedList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(DoublyCircularLinkedList<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }
    
    

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    @Override
    public String toString(){
        return this.id+"|"+this.nombre+"|"+this.apellidos+"|"+this.organizacion+"|"+this.correoElectronico+"|"+this.clave;
    }
    
    public String datos(){
        return this.nombre+"\n"+this.apellidos+"\n"+this.organizacion+"\n"+this.correoElectronico;
    }

    
    public static void saveListSer(ArrayList<Usuario> lista){
        try(ObjectOutputStream output= new ObjectOutputStream(new FileOutputStream("UsuarioSer.txt"))){
            output.writeObject(lista);
        } catch(IOException ioE){
        }
    }
    @SuppressWarnings("unchecked")
    public static ArrayList<Usuario> readListSer(){
        ArrayList<Usuario> lista= new ArrayList<>();
        try(ObjectInputStream input= new ObjectInputStream(new FileInputStream("UsuarioSer.txt"));){
            lista = (ArrayList<Usuario>)input.readObject();
        } catch(IOException | ClassNotFoundException ioE){
            
        }
        return lista;
    }
    public static Usuario verificarUsuario(String correo,String contraseña) throws DigitosInvalidos{
        ArrayList<Usuario> lista = Usuario.readListSer();
        
        for (Usuario u:lista){
            System.out.println(u);
            if ((u.getCorreoElectronico().equals(correo))&&(u.getClave().equals(contraseña))){
                u.setVehiculos(u.vincularVehiculoUsuario());
                u.setOfertas(u.vincularOfertaUsuario());
                return u;   
            }
        }
        throw new DigitosInvalidos("Credenciales Incorrectas");
    }
    
    public static void verificarCorreo(String correo) throws ObjetoExistente{
        ArrayList<Usuario> lista = Usuario.readListSer();
        for (Usuario u:lista){
            if ((u.getCorreoElectronico().equals(correo))){
                   throw new ObjetoExistente("Correo ya Existente");
            }
        } 
    }
    
    public static int nextId(){
        int n = Usuario.readListSer().size();
        
        return n+1;
    }

    public DoublyCircularLinkedList<Vehiculo> getFavoritos() {
        return favoritos;
    }
    
    public void addFavorito(Vehiculo v) {
    if (!favoritos.contains(v)) {
        favoritos.addLast(v);
    }
}

public void removeFavorito(Vehiculo v) {
    Iterator<Vehiculo> iterator = favoritos.iterator();
    while (iterator.hasNext()) {
        Vehiculo favorito = iterator.next();
        if (favorito.equals(v)) {
            iterator.remove();
            return;
        }
    }
}
    
    public DoublyLinkedList<Oferta> vincularOfertaUsuario(){
        DoublyLinkedList<Oferta> off = Oferta.readListSer();
        DoublyLinkedList<Oferta> fin = new DoublyLinkedList<>();
        for (Oferta o: off){
            if (o.getIdUsuario()== this.getId()){
                fin.addLast(o);
            }
        }
        return fin;
    }
    
    public DoublyCircularLinkedList<Vehiculo> vincularVehiculoUsuario(){
        DoublyCircularLinkedList<Vehiculo> veh = Vehiculo.readListSer();
        DoublyCircularLinkedList<Vehiculo> fin = new DoublyCircularLinkedList<>();
        Iterator<Vehiculo> iterator = veh.iterator();
        while (iterator.hasNext()) {
            Vehiculo v = iterator.next();
            if (v.getIdUsuario() == this.getId()){
                v.setOfertas(v.vincularOfertasVehiculo());
                fin.addLast(v);
            }
        }
        return fin;
    }
    
    public void updateFile(){
        ArrayList<Usuario> usuarios = Usuario.readListSer();
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario u = iterator.next();
            if (u.getId() == this.getId()) {
                iterator.remove(); // Eliminamos el elemento actual de la lista
            }
        }
        usuarios.add(this);
        System.out.println(usuarios);
        Usuario.saveListSer(usuarios);
    }

    public void eliminarVehiculo(Vehiculo v){
        Iterator<Vehiculo> iterator = this.vehiculos.iterator();
        while (iterator.hasNext()) {
            Vehiculo v1 = iterator.next();
            if (v.getPlaca().equals(v1.getPlaca()) ) {
                    iterator.remove(); // Eliminamos el elemento actual de la lista
            }   
        }
    }
    
    public void eliminarOferta(Oferta o){
        Iterator<Oferta> iterator = this.ofertas.iterator();
        while (iterator.hasNext()) {
            Oferta o1 = iterator.next();
            if (o.equals(o1) ) {
                    iterator.remove(); // Eliminamos el elemento actual de la lista
            }   
        }
        
    }
    
    
    
}
