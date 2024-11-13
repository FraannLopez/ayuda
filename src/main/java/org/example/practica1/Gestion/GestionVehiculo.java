package org.example.practica1.Gestion;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import org.example.practica1.Json.JsonUtiles;
import org.example.practica1.Vehiculo.Vehiculo;

import java.io.IOException;
import java.util.ArrayList;

public class GestionVehiculo {
    private ObservableList<Vehiculo> listaVehiculos;  // La lista de vehículos
    private TableView<Vehiculo> tablaVehiculos;       // La tabla que mostrará los vehículos
    private boolean cambiosPendientes = false;

    public GestionVehiculo() {
        listaVehiculos = FXCollections.observableArrayList();  // Inicializar la lista
        tablaVehiculos = new TableView<>();
    }

    public GestionVehiculo(ObservableList<Vehiculo> listaVehiculos) {
        this.listaVehiculos = listaVehiculos;
    }

    public TableView<Vehiculo> getTablaVehiculos() {
        return tablaVehiculos;
    }

    // Método para guardar cambios si hay modificaciones
    public void guardarVehiculosSiHayCambios() {
        if (cambiosPendientes) {
            try {
                JsonUtiles.guardarVehiculosEnArchivo(new ArrayList<>(listaVehiculos), "vehiculos.json");
                cambiosPendientes = false; // Resetea la bandera después de guardar
            } catch (IOException e) {
                mostrarAlerta("Error", "No se pudo guardar el archivo de vehículos: " + e.getMessage());
            }
        }
    }

    // Agregar un nuevo vehículo
    public void agregarVehiculo(Vehiculo vehiculo) {
        listaVehiculos.add(vehiculo);
        cambiosPendientes = true;
        guardarVehiculosSiHayCambios();
    }

    // Eliminar un vehículo
    public void eliminarVehiculo(Vehiculo vehiculo) {
        listaVehiculos.remove(vehiculo);
        cambiosPendientes = true;
        guardarVehiculosSiHayCambios();
    }

    // Actualizar un vehículo
    public void actualizarVehiculo(Vehiculo vehiculo, double nuevoPrecio) {
        vehiculo.setPrecio(nuevoPrecio);
        cambiosPendientes = true;
        guardarVehiculosSiHayCambios();
    }

    // Méetodo para mostrar Alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public ObservableList<Vehiculo> getListaVehiculos() {
        return listaVehiculos;
    }

}






