package org.example.practica1.Vehiculo;

public class Auto extends Vehiculo {
    public Auto(String marca, String modelo, int anio, double precio, String tipo) {
        super(marca, modelo, anio, precio, "Auto");
    }

    @Override
    public void showDetails() {
        // Detalles específicos del Auto
    }

}
