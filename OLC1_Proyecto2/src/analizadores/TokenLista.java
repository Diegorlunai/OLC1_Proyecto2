package analizadores;

import java.util.LinkedList;

public class TokenLista {
    
    // Inner class to store token info
    public static class TokenDato {
        // We keep fields public if you use them elsewhere, but add Getters for the Interface
        public Tokens tipo;
        public String lexema;
        public int linea;
        public int columna;

        public TokenDato(Tokens tipo, String lexema, int linea, int columna) {
            this.tipo = tipo;
            this.lexema = lexema;
            this.linea = linea;
            this.columna = columna;
        }

        // --- GETTERS REQUIRED BY THE INTERFACE ---
        public String getLexema() { return lexema; }
        public String getTipo() { return tipo.toString(); } // Convert Enum to String
        public int getLinea() { return linea; }
        public int getColumna() { return columna; }
    }

    // Static list to store found tokens
    public static LinkedList<TokenDato> lista = new LinkedList<>();

    // Method to add a token to the list
    public static void guardar(Tokens tipo, String lexema, int linea, int col) {
        lista.add(new TokenDato(tipo, lexema, linea, col));
    }

    // Method to clear the list before a new compilation
    public static void limpiar() {
        lista.clear();
    }
}