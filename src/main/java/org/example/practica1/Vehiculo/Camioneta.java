package org.example.practica1.Vehiculo;

public class Camioneta extends Vehiculo {
    public Camioneta(String marca, String modelo, int anio, double precio, String tipo) {
        super(marca, modelo, anio, precio, "Camioneta");
    }

    @Override
    public void showDetails() {
        // Detalles específicos de la Camioneta
    }
}
