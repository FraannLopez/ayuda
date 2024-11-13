package org.example.practica1.Gestion;

public class GestionUsuarios {

    private boolean esAdmin;

    public void cambiarModo(String modo) {
        if (modo.equals("Admin")) {
            esAdmin = true;
        } else {
            esAdmin = false;
        }
        // Aquí podrías cambiar la interfaz o hacer cualquier ajuste necesario
    }

    public boolean isAdmin() {
        return esAdmin;
    }
}

