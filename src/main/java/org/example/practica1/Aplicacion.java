package org.example.practica1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.practica1.Gestion.ControladorAdmin;
import org.example.practica1.Gestion.GestionUsuarios;
import org.example.practica1.Gestion.GestionVehiculo;
import org.example.practica1.InterfazUsuario.InterfazUsuario;
import org.example.practica1.Json.JsonUtiles;
import org.example.practica1.Vehiculo.Vehiculo;

import java.io.IOException;
import java.util.List;

public class Aplicacion extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Cargar lista de vehículos desde archivo
        ObservableList<Vehiculo> vehiculoList = FXCollections.observableArrayList(JsonUtiles.cargarVehiculosDesdeArchivo("vehiculos.json"));

        GestionVehiculo gestionVehiculos = new GestionVehiculo(vehiculoList);
        GestionUsuarios gestionUsuarios = new GestionUsuarios();

        // Crear interfaz de usuario
        InterfazUsuario interfaz = new InterfazUsuario(gestionVehiculos, gestionUsuarios);
        interfaz.createMainScene();  // Asegúrate de que este método esté implementado correctamente

        // Crear ventana de inicio para seleccionar el modo
        elegirModoAcceso(primaryStage, gestionVehiculos, gestionUsuarios);

        // Configurar ventana principal
        primaryStage.setTitle("Gestión de Vehículos");
        primaryStage.show();
    }

    // Método para mostrar el cuadro de diálogo de selección de modo
    private void elegirModoAcceso(Stage primaryStage, GestionVehiculo gestionVehiculos, GestionUsuarios gestionUsuarios) {
        // Crear un cuadro de diálogo para elegir el modo de acceso
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Modo de Acceso");
        alert.setHeaderText("Bienvenido a la Gestión de Vehículos");
        alert.setContentText("Por favor, seleccione el modo de acceso:");

        // Botones de selección de modo
        ButtonType btnAdmin = new ButtonType("Administrador", ButtonBar.ButtonData.YES);
        ButtonType btnCliente = new ButtonType("Cliente", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(btnAdmin, btnCliente);

        // Estilizar los botones
        Button btnAdminBtn = (Button) alert.getDialogPane().lookupButton(btnAdmin);
        btnAdminBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        Button btnClienteBtn = (Button) alert.getDialogPane().lookupButton(btnCliente);
        btnClienteBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        // Acción al seleccionar el modo
        alert.showAndWait().ifPresent(response -> {
            if (response == btnAdmin) {
                gestionUsuarios.cambiarModo("Admin");
                inicializarInterfazModo(primaryStage, gestionVehiculos, gestionUsuarios, "Admin");
            } else if (response == btnCliente) {
                gestionUsuarios.cambiarModo("Cliente");
                inicializarInterfazModo(primaryStage, gestionVehiculos, gestionUsuarios, "Cliente");
            }
        });
    }

    // Método que inicializa la interfaz según el modo seleccionado
    private void inicializarInterfazModo(Stage primaryStage, GestionVehiculo gestionVehiculos, GestionUsuarios gestionUsuarios, String modo) {
        // Verificación de modo válido
        if (!modo.equals("Admin") && !modo.equals("Cliente")) {
            throw new IllegalArgumentException("Modo no válido: " + modo);
        }

        // Crear la interfaz de usuario con los parámetros correspondientes
        InterfazUsuario interfaz = new InterfazUsuario(gestionVehiculos, gestionUsuarios);
        Scene mainScene = interfaz.createMainScene();

        // Cambiar la escena en el Stage principal
        primaryStage.setScene(mainScene);

        // Asegurarse de que la escena se ha configurado correctamente antes de mostrarla
        if (primaryStage.getScene() == null) {
            throw new NullPointerException("La escena no se configuró correctamente.");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}




