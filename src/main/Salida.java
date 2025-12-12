package main;

import java.util.ArrayList;

public class Salida {
    // Aquí guardaremos todo lo que el compilador quiera imprimir
    public static ArrayList<String> listaSalida = new ArrayList<>();

    public static void limpiar() {
        listaSalida.clear();
    }

    public static void imprimir(String texto) {
        listaSalida.add(texto);
        // También lo mostramos en consola de NetBeans por si acaso
        System.out.println(texto);
    }
}