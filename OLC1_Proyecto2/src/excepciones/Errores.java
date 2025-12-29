package excepciones;

import java.util.LinkedList;

public class Errores {

    // --- ESTA ES LA CLASE QUE FALTABA (ErrorDato) ---
    // Tu interfaz necesita que esta clase sea pública y estática para poder leerla.
    public static class ErrorDato {
        private String tipo;
        private String descripcion;
        private int linea;
        private int columna;

        public ErrorDato(String tipo, String descripcion, int linea, int columna) {
            this.tipo = tipo;
            this.descripcion = descripcion;
            this.linea = linea;
            this.columna = columna;
        }

        // Getters necesarios para tus reportes HTML
        public String getTipo() { return tipo; }
        public String getDescripcion() { return descripcion; }
        public int getLinea() { return linea; }
        public int getColumna() { return columna; }
    }
    // ------------------------------------------------

    // Lista estática para guardar los errores
    public static LinkedList<ErrorDato> listaErrores = new LinkedList<>();

    // Método para agregar errores desde el Scanner o Parser
    public static void agregar(String tipo, String desc, int linea, int col) {
        listaErrores.add(new ErrorDato(tipo, desc, linea, col));
    }

    // Método para limpiar antes de compilar de nuevo
    public static void limpiar() {
        listaErrores.clear();
    }
}