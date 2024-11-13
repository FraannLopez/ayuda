package org.example.practica1.Gestion;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.Modality;
import org.example.practica1.Vehiculo.Vehiculo;
import eu.hansolo.toolbox.observables.ObservableList;

public class ControladorAdmin {

    private final GestionVehiculo gestionVehiculo;
    private BorderPane panelPrincipal;  // Usamos BorderPane

    public ControladorAdmin(GestionVehiculo gestionVehiculo) {
        this.gestionVehiculo = gestionVehiculo;
        this.panelPrincipal = new BorderPane();  // Usamos BorderPane
    }

    public void inicializarInterfazAdmin() {
        TableView<Vehiculo> tablaVehiculo = crearTablaVehiculo();
        Button btnVerLista = new Button("Ver Lista");

        // Verificación de que la tabla y el botón no sean nulos
        if (tablaVehiculo == null || btnVerLista == null) {
            throw new NullPointerException("vehiculoTable o btnVerLista no están correctamente inicializados.");
        }

        // Acción del botón para ver la lista de vehículos
        btnVerLista.setOnAction(e -> verListaVehiculos());

        // Botones para acciones de administración
        Button btnAgregar = new Button("Agregar Vehículo");
        btnAgregar.setOnAction(e -> mostrarDialogoTipoVehiculo());

        Button btnEliminar = new Button("Eliminar Vehículo");
        btnEliminar.setOnAction(e -> mostrarDialogoEliminarVehiculo());

        Button btnActualizar = new Button("Actualizar Vehículo");
        btnActualizar.setOnAction(e -> mostrarDialogoActualizarVehiculo());

        // Colocamos los botones en la zona de la izquierda
        VBox vboxAdmin = new VBox(10, btnAgregar, btnEliminar, btnActualizar);
        panelPrincipal.setLeft(vboxAdmin);

        // Colocamos la tabla en el centro
        panelPrincipal.setCenter(tablaVehiculo);
    }

    private TableView<Vehiculo> crearTablaVehiculo() {
        // Crear la tabla de vehículos
        TableView<Vehiculo> tablaVehiculo = new TableView<>();

        // Crear las columnas de la tabla
        TableColumn<Vehiculo, String> columnaMarca = new TableColumn<>("Marca");
        columnaMarca.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarca()));

        TableColumn<Vehiculo, String> columnaModelo = new TableColumn<>("Modelo");
        columnaModelo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelo()));

        TableColumn<Vehiculo, Integer> columnaAnio = new TableColumn<>("Año");
        columnaAnio.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAnio()).asObject());

        TableColumn<Vehiculo, Double> columnaPrecio = new TableColumn<>("Precio");
        columnaPrecio.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrecio()).asObject());

        // Agregar las columnas a la tabla
        tablaVehiculo.getColumns().add(columnaMarca);
        tablaVehiculo.getColumns().add(columnaModelo);
        tablaVehiculo.getColumns().add(columnaAnio);
        tablaVehiculo.getColumns().add(columnaPrecio);

        // Agregar los vehículos a la tabla
        tablaVehiculo.setItems(gestionVehiculo.getListaVehiculos());

        return  tablaVehiculo;
    }

    // Métodos de mostrarDialogoTipoVehiculo, eliminarVehiculo, mostrarDialogoActualizarVehiculo se mantienen igual

    // Cambia todas las referencias a ObservableList a la versión correcta de JavaFX
    public void mostrarDialogoTipoVehiculo() {

        VehiculoDialogo.mostrarDialogoTipoVehiculo((Stage) panelPrincipal.getScene().getWindow(), gestionVehiculo.getListaVehiculos(), gestionVehiculo);
    }

    public void mostrarDialogoEliminarVehiculo() {
        VehiculoDialogo.mostrarDialogoEliminarVehiculo((Stage) panelPrincipal.getScene().getWindow(), gestionVehiculo.getListaVehiculos(), gestionVehiculo);
    }

    public void mostrarDialogoActualizarVehiculo() {
        VehiculoDialogo.mostrarDialogoActualizarVehiculo((Stage) panelPrincipal.getScene().getWindow(), gestionVehiculo.getListaVehiculos(), gestionVehiculo);
    }

    private void verListaVehiculos() {
        // Acción cuando el botón Ver Lista es presionado
        // Por ejemplo, podrías actualizar la tabla de vehículos si hay algún cambio
        gestionVehiculo.getTablaVehiculos().refresh();  // Este método actualizará la vista de la tabla si es necesario
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}





