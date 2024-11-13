package org.example.practica1.Gestion;

import eu.hansolo.toolbox.observables.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import org.example.practica1.Vehiculo.Auto;
import org.example.practica1.Vehiculo.Camioneta;
import org.example.practica1.Vehiculo.Moto;
import org.example.practica1.Vehiculo.Vehiculo;

import java.awt.*;

public class VehiculoDialogo {

    // Método estático para mostrar el diálogo del tipo de vehículo
    public static void mostrarDialogoTipoVehiculo(Stage stage, ObservableList<Vehiculo> listaVehiculos, GestionVehiculo gestionVehiculo) {
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.setTitle("Seleccionar Tipo de Vehículo");

        ComboBox<String> comboTipoVehiculo = new ComboBox<>();
        comboTipoVehiculo.getItems().addAll("Auto", "Moto", "Camioneta");

        Button botonSiguiente = new Button("Siguiente");
        botonSiguiente.setOnAction(e -> {
            String tipoSeleccionado = comboTipoVehiculo.getValue();
            if (tipoSeleccionado != null) {
                dialogo.close();
                mostrarDialogoAgregarVehiculo(tipoSeleccionado, stage, listaVehiculos, gestionVehiculo);
            }
        });

        VBox vboxDialogo = new VBox(15, new Label("Selecciona el tipo de vehículo:"), comboTipoVehiculo, botonSiguiente);
        vboxDialogo.setAlignment(Pos.CENTER);
        vboxDialogo.setPadding(new Insets(10));
        Scene escenaDialogo = new Scene(vboxDialogo, 300, 200);
        dialogo.setScene(escenaDialogo);
        dialogo.showAndWait();
    }

    // Método estático para mostrar el diálogo de agregar vehículo
    public static void mostrarDialogoAgregarVehiculo(String tipo, Stage stage, ObservableList<Vehiculo> listaVehiculos, GestionVehiculo gestionVehiculo) {
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.setTitle("Agregar " + tipo);

        TextField campoMarca = new TextField();
        campoMarca.setPromptText("Marca");

        TextField campoModelo = new TextField();
        campoModelo.setPromptText("Modelo");

        TextField campoAnio = new TextField();
        campoAnio.setPromptText("Año");

        TextField campoPrecio = new TextField();
        campoPrecio.setPromptText("Precio");

        Button botonGuardar = new Button("Guardar");
        botonGuardar.setOnAction(e -> {
            String marca = campoMarca.getText();
            String modelo = campoModelo.getText();
            int anio = Integer.parseInt(campoAnio.getText());
            double precio = Double.parseDouble(campoPrecio.getText());

            // Crear un vehículo dependiendo del tipo seleccionado
            Vehiculo vehiculo = null;
            if (tipo.equals("Auto")) {
                vehiculo = new Auto(marca, modelo, anio, precio, tipo);
            } else if (tipo.equals("Moto")) {
                vehiculo = new Moto(marca, modelo, anio, precio, tipo);
            }else if (tipo.equals("Camioneta")){
                vehiculo = new Camioneta(marca, modelo, anio, precio, tipo);
            }

            // Verifica si el vehículo fue creado correctamente
            if (vehiculo != null) {
                gestionVehiculo.agregarVehiculo(vehiculo);
                dialogo.close();
            }
        });

        VBox vboxDialogo = new VBox(15, campoMarca, campoModelo, campoAnio, campoPrecio, botonGuardar);
        vboxDialogo.setAlignment(Pos.CENTER);
        vboxDialogo.setPadding(new Insets(10));
        Scene escenaDialogo = new Scene(vboxDialogo, 300, 250);
        dialogo.setScene(escenaDialogo);
        dialogo.showAndWait();
    }

    //Metodo para eliminar un vehiculo
    public static void mostrarDialogoEliminarVehiculo(Stage stage, ObservableList<Vehiculo> listaVehiculos, GestionVehiculo gestionVehiculo) {
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.setTitle("Eliminar Vehículo");

        // Crear ComboBox para seleccionar el vehículo
        ComboBox<Vehiculo> comboVehiculos = new ComboBox<>();
        comboVehiculos.getItems().addAll(listaVehiculos);

        Button botonEliminar = new Button("Eliminar");
        botonEliminar.setOnAction(e -> {
            Vehiculo vehiculoSeleccionado = comboVehiculos.getValue();
            if (vehiculoSeleccionado != null) {
                // Eliminar vehículo de la lista
                gestionVehiculo.eliminarVehiculo(vehiculoSeleccionado);
                dialogo.close();
                mostrarAlerta("Éxito.\n.El vehículo ha sido eliminado correctamente.");
            } else {
                mostrarAlerta("Error.\n. Debes seleccionar un vehículo para eliminar.");
            }
        });

        // Layout del diálogo
        VBox vboxDialogo = new VBox(15, new Label("Selecciona un vehículo para eliminar:"), comboVehiculos, botonEliminar);
        vboxDialogo.setAlignment(Pos.CENTER);
        vboxDialogo.setPadding(new Insets(10));
        Scene escenaDialogo = new Scene(vboxDialogo, 300, 200);
        dialogo.setScene(escenaDialogo);
        dialogo.showAndWait();
    }

    // Método para actualizar un vehículo
    public static void mostrarDialogoActualizarVehiculo(Stage stage, ObservableList<Vehiculo> listaVehiculos, GestionVehiculo gestionVehiculo) {
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.setTitle("Actualizar Vehículo");

        // Crear ComboBox para seleccionar el vehículo
        ComboBox<Vehiculo> comboVehiculos = new ComboBox<>();
        comboVehiculos.getItems().addAll(listaVehiculos);

        // Campo para ingresar el nuevo precio
        TextField campoNuevoPrecio = new TextField();
        campoNuevoPrecio.setPromptText("Nuevo Precio");

        Button botonActualizar = new Button("Actualizar");
        botonActualizar.setOnAction(e -> {
            Vehiculo vehiculoSeleccionado = comboVehiculos.getValue();
            if (vehiculoSeleccionado != null) {
                try {
                    double nuevoPrecio = Double.parseDouble(campoNuevoPrecio.getText());
                    // Actualizar el precio del vehículo
                    gestionVehiculo.actualizarVehiculo(vehiculoSeleccionado, nuevoPrecio);
                    dialogo.close();
                    mostrarAlerta("Éxito.\n.El precio del vehículo ha sido actualizado.");
                } catch (NumberFormatException ex) {
                    mostrarAlerta("Error\n.El precio ingresado no es válido.");
                }
            } else {
                mostrarAlerta("Error\n.Debes seleccionar un vehículo para actualizar.");
            }
        });

        // Layout del diálogo
        VBox vboxDialogo = new VBox(15, new Label("Selecciona un vehículo para actualizar:"), comboVehiculos, new Label("Nuevo Precio:"), campoNuevoPrecio, botonActualizar);
        vboxDialogo.setAlignment(Pos.CENTER);
        vboxDialogo.setPadding(new Insets(10));
        Scene escenaDialogo = new Scene(vboxDialogo, 300, 250);
        dialogo.setScene(escenaDialogo);
        dialogo.showAndWait();
    }

    //Metodo para mostrar alertas de validacion
    private static void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}





