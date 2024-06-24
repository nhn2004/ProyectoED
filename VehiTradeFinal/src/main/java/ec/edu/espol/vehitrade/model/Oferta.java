/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.vehitrade.model;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author HP
 */

public class Oferta implements Serializable{
    private double precio;
    private int idUsuario;
    private String placa;
    private String correoElectronicoComprador;
    private String marca;
    private String modelo;

    public Oferta(){

    }
    
    public Oferta(double p,int idU, String placa, String c, String marca, String modelo){
        this.precio=p;
        this.idUsuario=idU;
        this.placa = placa;
        this.correoElectronicoComprador = c;
        this.marca = marca;
        this.modelo = modelo;
    }


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
    public double getPrecio() {
        return precio;
    }

    public String getCorreoElectronicoComprador() {
        return correoElectronicoComprador;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }
    
    
    

    

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    
    
    public static void saveListSer(DoublyLinkedList<Oferta> ofertas){
        try(ObjectOutputStream output= new ObjectOutputStream(new FileOutputStream("OfertaSer.txt"));){
            output.writeObject(ofertas);
        } catch(IOException ioE){
        }
    }
    
    
    public static DoublyLinkedList<Oferta> readListSer(){
        DoublyLinkedList<Oferta> lista= new DoublyLinkedList<>();
        try(ObjectInputStream input= new ObjectInputStream(new FileInputStream("OfertaSer.txt"));){
            lista = (DoublyLinkedList<Oferta>)input.readObject();
        } catch(IOException ioE){
            
        } catch (ClassNotFoundException cE){
            
        }
        return lista;
    }
    
    public void updateFile(){
        DoublyLinkedList<Oferta> ofertas = Oferta.readListSer();
        ofertas.addLast(this);
        Oferta.saveListSer(ofertas);
        
    }
    
    public void eliminar(){
        DoublyLinkedList<Oferta> ofertas = Oferta.readListSer();
        Iterator<Oferta> iterator = ofertas.iterator();
        while (iterator.hasNext()) {
            Oferta o = iterator.next();
            if(o.getPlaca().equals(this.getPlaca())) {
                iterator.remove();
            }
        }
        Oferta.saveListSer(ofertas);
    }
    
    
}