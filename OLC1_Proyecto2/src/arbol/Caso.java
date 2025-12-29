package arbol;

import java.util.LinkedList;

// IMPORTANTE: La clase debe llamarse 'Caso' para coincidir con tu Parser
public class Caso extends Instruccion {
    
    public Instruccion valor; // El valor a comparar
    public LinkedList<Instruccion> bloque; // Las instrucciones
    public boolean esDefault;

    // Constructor que coincide con lo que pide el Parser (valor, instrucciones, linea, col)
    public Caso(Instruccion valor, LinkedList<Instruccion> bloque, int linea, int col) {
        super(linea, col);
        this.valor = valor;
        this.bloque = bloque;
        this.esDefault = (valor == null);
    }

    @Override
    public Object ejecutar(Object tabla) {
        return this; // Solo retornamos el objeto para que el Switch lo use
    }
}