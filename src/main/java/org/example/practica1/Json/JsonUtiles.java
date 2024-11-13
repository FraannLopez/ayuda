package org.example.practica1.Json;

import org.example.practica1.Vehiculo.Auto;
import org.example.practica1.Vehiculo.Camioneta;
import org.example.practica1.Vehiculo.Moto;
import org.example.practica1.Vehiculo.Vehiculo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class JsonUtiles {

    // Método para guardar la lista de vehículos en un archivo JSON
    public static void guardarVehiculosEnArchivo(List<Vehiculo> vehiculos, String nombreArchivo) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (Vehiculo vehiculo : vehiculos) {
            JSONObject jsonVehiculo = new JSONObject();
            jsonVehiculo.put("marca", vehiculo.getMarca());
            jsonVehiculo.put("modelo", vehiculo.getModelo());
            jsonVehiculo.put("anio", vehiculo.getAnio());
            jsonVehiculo.put("precio", vehiculo.getPrecio());
            jsonVehiculo.put("tipo", vehiculo.getTipoVehiculo()); // Agregar tipo al JSON
            jsonArray.put(jsonVehiculo);
        }

        // Guardar el JSON con indentación para hacerlo más legible
        try (FileWriter archivo = new FileWriter(nombreArchivo)) {
            archivo.write(jsonArray.toString(4)); // Añadir indentación para mejor legibilidad
        }
    }

    // Método para cargar los vehículos desde un archivo JSON
    public static List<Vehiculo> cargarVehiculosDesdeArchivo(String nombreArchivo) throws IOException {
        List<Vehiculo> listaVehiculos = new ArrayList<>();
        Path path = Paths.get(nombreArchivo);

        // Verificar si el archivo existe
        if (!Files.exists(path)) {
            System.out.println("El archivo " + nombreArchivo + " no existe. Creando archivo vacío.");
            guardarVehiculosEnArchivo(new ArrayList<>(), nombreArchivo);  // Crear un archivo vacío
            return new ArrayList<>(); // Retorna una lista vacía
        }

        try {
            // Leer el archivo JSON
            String contenido = new String(Files.readAllBytes(path));
            JSONArray jsonArray = new JSONArray(contenido);

            // Procesar cada objeto JSON y crear los vehículos
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonVehiculo = jsonArray.getJSONObject(i);
                String marca = jsonVehiculo.getString("marca");
                String modelo = jsonVehiculo.getString("modelo");
                int anio = jsonVehiculo.getInt("anio");
                double precio = jsonVehiculo.getDouble("precio");
                String tipo = jsonVehiculo.getString("tipo");

                Vehiculo vehiculo;
                switch (tipo) {
                    case "Auto":
                        vehiculo = new Auto(marca, modelo, anio, precio, tipo);
                        break;
                    case "Camioneta":
                        vehiculo = new Camioneta(marca, modelo, anio, precio, tipo);
                        break;
                    case "Moto":
                        vehiculo = new Moto(marca, modelo, anio, precio, tipo);
                        break;
                    default:
                        throw new IllegalArgumentException("Tipo de vehículo desconocido: " + tipo);
                }

                listaVehiculos.add(vehiculo);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error al leer el archivo JSON", e);
        }
        return listaVehiculos;
    }
}



