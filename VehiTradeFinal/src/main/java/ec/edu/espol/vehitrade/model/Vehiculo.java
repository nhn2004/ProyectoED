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
import java.util.Comparator;

public class Vehiculo implements Serializable {
    private String tipoVehiculo;
    private String placa;
    private String modelo;
    private String marca;
    private String tipoMotor;
    private int año;
    private double recorrido;
    private String color;
    private String tipoCosmbustible;
    private double precio;
    private int idUsuario;
    private DoublyCircularLinkedList<String> dirImagen =  new DoublyCircularLinkedList<>();
    private String reparaciones;
    private String mantenimiento;
    private DoublyLinkedList<Oferta> ofertas;
    
    private boolean esFavorito;
    
    public Vehiculo(){
        
    }

    public Vehiculo(String tipo,String placa, String modelo, String marca, String tipoMotor, 
            int año, double recorrido, String color, String tipoCosmbustible, 
            double precio, int idUsuario, String dirImagen, String reparaciones, String mantenimiento) {
        this.tipoVehiculo = tipo;
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.tipoMotor = tipoMotor;
        this.año = año;
        this.recorrido = recorrido;
        this.color = color;
        this.tipoCosmbustible = tipoCosmbustible;
        this.precio = precio;
        this.idUsuario=idUsuario;
        this.ofertas = new DoublyLinkedList<>(); 
        this.dirImagen.addLast(dirImagen);
        this.esFavorito=false;
        this.reparaciones = reparaciones;
        this.mantenimiento = mantenimiento;
        
    }

    public String getDirImagen() {
        return dirImagen.getLast().getContent();
    }
    
       public DoublyCircularLinkedList<String> getRutasImagenes() {
        return dirImagen;
    }

    public String getReparaciones() {
        return reparaciones;
    }

    public String getMantenimiento() {
        return mantenimiento;
    }

    public void setReparaciones(String reparaciones) {
        this.reparaciones = reparaciones;
    }

    public void setMantenimiento(String mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setTipoMotor(String tipoMotor) {
        this.tipoMotor = tipoMotor;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public void setRecorrido(double recorrido) {
        this.recorrido = recorrido;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTipoCosmbustible(String tipoCosmbustible) {
        this.tipoCosmbustible = tipoCosmbustible;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setEsFavorito(boolean esFavorito) {
        this.esFavorito = esFavorito;
    }
    


    public int getIdUsuario() {
        return idUsuario;
    }
    

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public String getTipoMotor() {
        return tipoMotor;
    }

    public int getAño() {
        return año;
    }

    public double getRecorrido() {
        return recorrido;
    }

    public String getColor() {
        return color;
    }

    public String getTipoCosmbustible() {
        return tipoCosmbustible;
    }

    public double getPrecio() {
        return precio;
    }


    public DoublyLinkedList<Oferta> getOfertas() {
        return ofertas;
    }

    public void setOfertas(DoublyLinkedList<Oferta> ofertas) {
        this.ofertas = ofertas;
    }
    
    public boolean isFavorite() {
        return esFavorito;
    }

    public void setFavorite(boolean favorite) {
        this.esFavorito = favorite;
    }
    


    public String getTipoVehiculo() {
        return tipoVehiculo;
    }
    
    public void añadirOferta(Oferta o){
        this.ofertas.addLast(o);
    }
    
    public void añadirImagen(String rutaImagen) {
        this.dirImagen.addLast(rutaImagen);
    }

    public void eliminarImagen(String rutaImagen) {
        Iterator<String> it = this.dirImagen.iterator();
        while (it.hasNext()) {
            if (it.next().equals(rutaImagen)) {
                it.remove();
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Este " + this.tipoVehiculo + " tiene:\n"
                + "Placa = " + this.placa + ",\n"
                + "Marca = " + this.marca + ",\n"
                + "Modelo = " + this.modelo + ",\n"
                + "Tipo de motor = " + this.tipoMotor + ",\n"
                + "Año = " + this.año + ",\n"
                + "Recorrido = " + this.recorrido + ",\n"
                + "Color = " + this.color + ",\n"
                + "Tipo de combustible = " + this.tipoCosmbustible + ",\n"
                + "Precio = " + this.precio + ",\n"
                + "Reparaciones = " + this.reparaciones + ",\n"
                + "Mantenimiento = " + this.mantenimiento;
    }

    
    public static void saveListSer(DoublyCircularLinkedList<Vehiculo> vehiculos) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("VehiculoSer.txt"))) {
            output.writeObject(vehiculos);
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    public static DoublyCircularLinkedList<Vehiculo> readListSer() {
        DoublyCircularLinkedList<Vehiculo> lista = new DoublyCircularLinkedList<>();
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("VehiculoSer.txt"))) {
            lista = (DoublyCircularLinkedList<Vehiculo>) input.readObject();
        } catch (IOException | ClassNotFoundException ioE) {
            ioE.printStackTrace();
        }
        return lista;
    }
    
    public void updateFile(){
        DoublyCircularLinkedList<Vehiculo> vehiculos = Vehiculo.readListSer();
        Iterator<Vehiculo> iterator = vehiculos.iterator();
        while (iterator.hasNext()) {
            Vehiculo v = iterator.next();
            if (v.getPlaca().equals(this.getPlaca())) {
                iterator.remove(); // Eliminamos el elemento actual de la lista
            }
        }
        vehiculos.addLast(this);
        Vehiculo.saveListSer(vehiculos);
        
    }
    
    public DoublyLinkedList<Oferta> vincularOfertasVehiculo(){
        DoublyLinkedList<Oferta> off = Oferta.readListSer();
        DoublyLinkedList<Oferta> fin = new DoublyLinkedList<>();
        for (Oferta o: off){
            if (o.getPlaca().equals(this.getPlaca())){
                fin.addLast(o);
            }
        }
        return fin;
    }
    
    public static void verificarPlaca(String placa) throws ObjetoExistente{
        DoublyCircularLinkedList<Vehiculo> veh = Vehiculo.readListSer();
        for (Vehiculo v:veh){
            if (v.getPlaca().equals(placa)){
                   throw new ObjetoExistente("Vehiculo ya Registrado");
            }
        } 
    }
    
    public void eliminar(){
        DoublyCircularLinkedList<Vehiculo> vehiculos = Vehiculo.readListSer();
        Iterator<Vehiculo> iterator = vehiculos.iterator();
        while (iterator.hasNext()) {
            Vehiculo v = iterator.next();
            if(v.getPlaca().equals(this.getPlaca())) {
                iterator.remove();
            }
        }
        Vehiculo.saveListSer(vehiculos);
    }
    
    public static DoublyCircularLinkedList<Vehiculo> quitarMisVehiculos(DoublyCircularLinkedList<Vehiculo> vehiculos) {
        DoublyCircularLinkedList<Vehiculo> veh = Vehiculo.readListSer();

        // Define un Comparator para comparar los vehículos por su placa
        Comparator<Vehiculo> comparator = new Comparator<Vehiculo>() {
            @Override
            public int compare(Vehiculo v1, Vehiculo v2) {
                return v1.getPlaca().compareTo(v2.getPlaca());
            }
        };

        Iterator<Vehiculo> iterator = veh.iterator();
        while (iterator.hasNext()) {
            Vehiculo v = iterator.next();
            for (Vehiculo vehhh : vehiculos) {
                if (comparator.compare(v, vehhh) == 0) {
                    iterator.remove(); // Eliminamos el elemento actual de la lista
                    break;
                }
            }
        }
        return veh;
    }

        
    
 
}