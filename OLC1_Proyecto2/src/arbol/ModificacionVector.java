package arbol;

import simbolo.TablaSimbolos;

public class ModificacionVector extends Instruccion {

    private String id;
    private Instruccion indice1;
    private Instruccion indice2;
    private Instruccion valor;

    public ModificacionVector(String id, Instruccion idx1, Instruccion idx2, Instruccion valor, int linea, int col) {
        super(linea, col);
        this.id = id;
        this.indice1 = idx1;
        this.indice2 = idx2;
        this.valor = valor;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        Object simboloArray = ts.obtener(id);

        if (simboloArray == null || !(simboloArray instanceof Object[])) {
            System.err.println("❌ Error: No se puede asignar a '" + id + "' (no es vector o no existe).");
            return null;
        }

        Object[] arreglo = (Object[]) simboloArray;
        
        // Evaluar índice 1
        Object idx1Val = indice1.ejecutar(ts);
        if (!(idx1Val instanceof Integer)) return null;
        int i = (int) idx1Val;

        if (i < 0 || i >= arreglo.length) {
            System.err.println("❌ Error: Índice fuera de rango.");
            return null;
        }

        // Evaluar el valor a asignar
        Object nuevoValor = valor.ejecutar(ts);

        // --- ASIGNACIÓN 1 DIMENSIÓN ---
        if (indice2 == null) {
            arreglo[i] = nuevoValor;
        } 
        // --- ASIGNACIÓN 2 DIMENSIONES ---
        else {
            if (arreglo[i] instanceof Object[]) {
                Object[] fila = (Object[]) arreglo[i];
                Object idx2Val = indice2.ejecutar(ts);
                if (!(idx2Val instanceof Integer)) return null;
                int j = (int) idx2Val;

                if (j < 0 || j >= fila.length) {
                    System.err.println("❌ Error: Índice 2 fuera de rango.");
                    return null;
                }
                fila[j] = nuevoValor;
            }
        }

        return null;
    }
}