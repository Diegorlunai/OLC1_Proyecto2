package main; // <--- CONFIRMA QUE ESTÁ EN EL PAQUETE MAIN

import java.util.LinkedList;

public class Salida {
    
    public static LinkedList<String> listaSalida = new LinkedList<>();

    public static void limpiar() {
        listaSalida.clear();
    }

    public static void agregar(String texto) {
        listaSalida.add(texto);
    }

    public static void agregarSinSalto(String texto) {
        listaSalida.add(texto);
    }
    
    // Método de compatibilidad (por si alguna clase vieja llama a "imprimir")
    public static void imprimir(String texto) {
        listaSalida.add(texto);
    }
}