package arbol;

import simbolo.TablaSimbolos;
import java.util.LinkedList;

public class AccesoVector extends Instruccion {

    private String id;
    private Instruccion indice1;
    private Instruccion indice2;

    public AccesoVector(String id, Instruccion idx1, Instruccion idx2, int linea, int col) {
        super(linea, col);
        this.id = id;
        this.indice1 = idx1;
        this.indice2 = idx2;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        Object valor = ts.obtener(id);

        if (valor == null) {
            System.err.println("⌦ Error: El vector/lista '" + id + "' no existe. Línea: " + linea);
            return null;
        }
        
        // --- MANEJO DE LISTAS (LinkedList) ---
        if (valor instanceof LinkedList) {
            LinkedList lista = (LinkedList) valor;
            
            Object idx1Val = indice1.ejecutar(ts);
            int i = obtenerEntero(idx1Val);
            
            if (i == -1 && !esEnteroValido(idx1Val)) {
                System.err.println("⌦ Error: El índice debe ser numérico. Línea: " + linea);
                return null;
            }
            
            if (i < 0 || i >= lista.size()) {
                System.err.println("⌦ Error: Índice " + i + " fuera de límites en lista. Línea: " + linea);
                return null;
            }
            
            return lista.get(i);
        }
        
        // --- MANEJO DE VECTORES (Object[]) ---
        if (!(valor instanceof Object[])) {
            System.err.println("⌦ Error: '" + id + "' no es un vector ni lista. Línea: " + linea);
            return null;
        }

        Object[] arreglo = (Object[]) valor;

        // --- ÍNDICE 1 (Con conversión segura) ---
        Object idx1Val = indice1.ejecutar(ts);
        int i = obtenerEntero(idx1Val);
        
        if (i == -1 && !esEnteroValido(idx1Val)) {
            System.err.println("⌦ Error: El índice 1 debe ser numérico. Línea: " + linea);
            return null;
        }

        if (i < 0 || i >= arreglo.length) {
            System.err.println("⌦ Error: Índice " + i + " fuera de límites. Línea: " + linea);
            return null;
        }

        Object resultado = arreglo[i];

        // --- ÍNDICE 2 (Matriz) ---
        if (indice2 != null) {
            if (resultado instanceof Object[]) {
                Object[] arregloInterno = (Object[]) resultado;
                Object idx2Val = indice2.ejecutar(ts);
                int j = obtenerEntero(idx2Val);
                
                if (j == -1 && !esEnteroValido(idx2Val)) {
                    System.err.println("⌦ Error: El índice 2 debe ser numérico. Línea: " + linea);
                    return null;
                }
                
                if (j < 0 || j >= arregloInterno.length) {
                    System.err.println("⌦ Error: Índice 2 (" + j + ") fuera de límites. Línea: " + linea);
                    return null;
                }
                return arregloInterno[j];
            } else {
                System.err.println("⌦ Error: Intento de acceso 2D en vector 1D. Línea: " + linea);
                return null;
            }
        }

        return resultado;
    }
    
    // Auxiliar para convertir Double (5.0) a Int (5)
    private int obtenerEntero(Object val) {
        if (val instanceof Integer) return (int) val;
        if (val instanceof Double) return ((Double) val).intValue();
        return -1;
    }
    
    private boolean esEnteroValido(Object val) {
        return (val instanceof Integer) || (val instanceof Double);
    }
}