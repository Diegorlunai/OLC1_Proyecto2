package arbol;

import java.util.LinkedList;

public class Case {
    public Instruccion valor; // El valor a comparar (ej: case 1:)
    public LinkedList<Instruccion> bloque; // Las instrucciones de ese caso
    public boolean esDefault; // Para saber si es el 'default:'

    public Case(Instruccion valor, LinkedList<Instruccion> bloque, boolean esDefault) {
        this.valor = valor;
        this.bloque = bloque;
        this.esDefault = esDefault;
    }
}