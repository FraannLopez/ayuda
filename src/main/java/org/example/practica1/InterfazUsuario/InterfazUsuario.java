package org.example.practica1.InterfazUsuario;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox; // Importación correcta
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.practica1.Gestion.ControladorAdmin;
import org.example.practica1.Gestion.GestionUsuarios;
import org.example.practica1.Gestion.GestionVehiculo;
import org.example.practica1.Gestion.VehiculoDialogo;
import org.example.practica1.Vehiculo.Vehiculo;

public class InterfazUsuario {

    private TableView<Vehiculo> vehiculoTable;  // Tabla para mostrar los vehículos
    private GestionVehiculo gestionVehiculos;   // Gestión de los vehículos
    private GestionUsuarios gestionUsuarios;   // Gestión de los usuarios
    private VBox panelPrincipal;                // Contenedor principal de la interfaz
    private Button btnVerLista;                 // Botón para ver la lista de vehículos
    private boolean listaVisible = false;       // Indicador de si la lista está visible o no

    public InterfazUsuario(GestionVehiculo gestionVehiculos, GestionUsuarios gestionUsuarios) {
        this.gestionVehiculos = gestionVehiculos;
        this.gestionUsuarios = gestionUsuarios;
        this.panelPrincipal = new VBox();
        this.panelPrincipal.setSpacing(10);


        // Verificamos que los componentes se hayan inicializado antes de agregarlos
        if (vehiculoTable != null && btnVerLista != null) {
            // Añadimos los componentes al panel principal
            panelPrincipal.getChildren().addAll(btnVerLista, vehiculoTable);
        } else {
            System.out.println("Error: vehiculoTable o btnVerLista es null");
        }
    }


    public Scene createMainScene() {

        Button btnClientes = new Button("Clientes");
        btnClientes.setOnAction(e -> {
            gestionUsuarios.cambiarModo("Clientes");
            actualizarVista();
        });

        Button btnAdmin = new Button("Admin");
        btnAdmin.setOnAction(e -> {
            gestionUsuarios.cambiarModo("Admin");
            actualizarVista();
        });

        // Botones del administrador
        // Botón para agregar un vehículo
        Button btnAgregarVehiculo = new Button("Agregar Vehículo");
        btnAgregarVehiculo.setOnAction(e -> {
            // Llamamos al cuadro de diálogo para capturar los datos del vehículo
            VehiculoDialogo.mostrarDialogoTipoVehiculo((Stage) panelPrincipal.getScene().getWindow(), gestionVehiculos.getListaVehiculos(), gestionVehiculos);
        });


        Button btnEliminarVehiculo = new Button("Eliminar Vehículo");
        btnEliminarVehiculo.setOnAction(e -> {
            // Obtener el vehículo seleccionado de la tabla
            Vehiculo vehiculoSeleccionado = vehiculoTable.getSelectionModel().getSelectedItem();

            if (vehiculoSeleccionado != null) {
                // Eliminar el vehículo seleccionado
                gestionVehiculos.eliminarVehiculo(vehiculoSeleccionado);
            } else {
                // Mostrar un mensaje si no hay vehículo seleccionado
                mostrarMensajeError("Debe seleccionar un vehículo para eliminar.");
            }
        });


        Button btnActualizarVehiculo = new Button("Actualizar Vehículo");
        btnActualizarVehiculo.setOnAction(e -> {
            // Obtener el vehículo seleccionado de la tabla
            Vehiculo vehiculoSeleccionado = vehiculoTable.getSelectionModel().getSelectedItem();

            if (vehiculoSeleccionado != null) {
                // Llamar al cuadro de diálogo para actualizar el precio del vehículo
                mostrarDialogoActualizarVehiculo(vehiculoSeleccionado);
            } else {
                // Mostrar un mensaje si no hay vehículo seleccionado
                mostrarMensajeError("Debe seleccionar un vehículo para actualizar.");
            }
        });


        // Botón para ver/ocultar la lista de vehículos
        btnVerLista = new Button("Ver Lista de Vehículos");
        btnVerLista.setOnAction(e -> {
            listaVisible = !listaVisible; // Alternar la visibilidad de la lista
            actualizarListaVehiculos();
        });

        this.vehiculoTable = gestionVehiculos.getTablaVehiculos();  // Obtener la tabla de vehículos
        VBox adminLayout = new VBox(10, btnAgregarVehiculo, btnEliminarVehiculo, btnActualizarVehiculo, btnVerLista);

        VBox layout = new VBox(20, btnClientes, btnAdmin);
        layout.setPadding(new Insets(20));

        if (gestionUsuarios.isAdmin()) {
            adminLayout.setSpacing(10);
            panelPrincipal.getChildren().clear();
            panelPrincipal.getChildren().addAll(adminLayout, vehiculoTable);  // Agregar la tabla de vehículos al layout
        } else {
            panelPrincipal.getChildren().clear();
            panelPrincipal.getChildren().add(layout);
        }

        return new Scene(panelPrincipal, 800, 600);
    }

    private void actualizarVista() {
        if (gestionUsuarios.isAdmin()) {
            actualizarListaVehiculos();
        } else {
            panelPrincipal.getChildren().clear();
            Button btnClientes = new Button("Clientes");
            btnClientes.setOnAction(e -> {
                gestionUsuarios.cambiarModo("Clientes");
                actualizarVista();
            });
            VBox layout = new VBox(20, btnClientes);
            layout.setPadding(new Insets(20));
            panelPrincipal.getChildren().add(layout);
        }
    }

    private void actualizarListaVehiculos() {
        if (listaVisible) {
            if (!panelPrincipal.getChildren().contains(vehiculoTable)) {
                panelPrincipal.getChildren().add(vehiculoTable);
            }
            btnVerLista.setText("Ocultar Lista de Vehículos");
        } else {
            panelPrincipal.getChildren().remove(vehiculoTable);
            btnVerLista.setText("Ver Lista de Vehículos");
        }
    }

    private void mostrarDialogoActualizarVehiculo(Vehiculo vehiculo) {
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.setTitle("Actualizar Vehículo");

        TextField campoPrecio = new TextField();
        campoPrecio.setPromptText("Nuevo Precio");
        campoPrecio.setText(String.valueOf(vehiculo.getPrecio())); // Mostrar el precio actual

        Button botonGuardar = new Button("Guardar");
        botonGuardar.setOnAction(e -> {
            try {
                double nuevoPrecio = Double.parseDouble(campoPrecio.getText());
                // Llamar al método para actualizar el vehículo con el nuevo precio
                gestionVehiculos.actualizarVehiculo(vehiculo, nuevoPrecio);
                dialogo.close();
            } catch (NumberFormatException ex) {
                mostrarMensajeError("Por favor, ingrese un precio válido.");
            }
        });

        VBox vboxDialogo = new VBox(15, campoPrecio, botonGuardar);
        vboxDialogo.setPadding(new Insets(15));
        vboxDialogo.setAlignment(Pos.CENTER);
        Scene escenaDialogo = new Scene(vboxDialogo, 300, 200);
        dialogo.setScene(escenaDialogo);
        dialogo.showAndWait();
    }


    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}









