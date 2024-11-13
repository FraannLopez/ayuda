package org.example.practica1.Vehiculo;

public abstract class Vehiculo {
    private String marca;
    private String modelo;
    private int anio;
    private double precio;
    private String tipoVehiculo;
    private static int idCounter = 1;
    private int id;

    // Constructor
    public Vehiculo(String marca, String modelo, int anio, double precio, String tipoVehiculo) {
        this.id = idCounter++;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.tipoVehiculo = tipoVehiculo;
    }

    // Getters y Setters
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public int getId() {
        return id;
    }

    public abstract void showDetails(); // Método abstracto, cada tipo de vehículo lo implementará.

    @Override
    public String toString() {
        return " " + tipoVehiculo + " " + marca + " " + modelo + " año: " + anio + " precio: " + precio;
    }
}
