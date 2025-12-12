package excepciones;

import java.util.ArrayList;

public class Errores {
    // Aquí guardamos todos los errores encontrados
    public static ArrayList<Errores> listaErrores = new ArrayList<>();

    public String tipo;
    public String descripcion;
    public int linea;
    public int columna;

    public Errores(String tipo, String descripcion, int linea, int columna) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.linea = linea;
        this.columna = columna;
    }

    public static void agregar(String tipo, String desc, int linea, int col) {
        listaErrores.add(new Errores(tipo, desc, linea, col));
    }
    
    public static void limpiar() {
        listaErrores.clear();
    }
}