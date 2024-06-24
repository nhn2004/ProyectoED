/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.vehitrade.model;

/**
 *
 * @author nicol
 */
public class Auto extends Vehiculo {
    private final String vidrios;
    private final String transmision;

    public Auto(String tipo, String placa, String modelo, String marca, String tipoMotor, 
            int año, double recorrido, String color, String tipoCosmbustible, 
            double precio, int idVendedor,String vidrios, String transmision) {
        super(tipo, placa, modelo, marca, tipoMotor, año, recorrido, color, tipoCosmbustible, precio,idVendedor);
        this.vidrios = vidrios;
        this.transmision = transmision;
    }

    public String getVidrios() {
        return vidrios;
    }

    public String getTransmision() {
        return transmision;
    }
    
    @Override
    public String toString(){
        return (super.toString()+",\n"
                + "Vidrios = "+this.vidrios+",\n"
                + "Transmisión = "+this.transmision);
    }
}
