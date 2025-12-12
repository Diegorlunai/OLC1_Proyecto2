package arbol;

import java.util.LinkedList;

public class Switch extends Instruccion {

    private Instruccion expresion;
    private LinkedList<Case> listaCasos;

    public Switch(Instruccion expresion, LinkedList<Case> listaCasos, int linea, int col) {
        super(linea, col);
        this.expresion = expresion;
        this.listaCasos = listaCasos;
    }

    @Override
    public Object ejecutar(Object tabla) {
        // 1. Calculamos el valor de la variable del switch (ej: switch(x) -> calculamos x)
        Object valSwitch = expresion.ejecutar(tabla);
        
        boolean casoEncontrado = false;
        
        // 2. Recorremos todos los casos
        for (Case caso : listaCasos) {
            
            // Si ya encontramos uno previo (fall-through) o este coincide...
            if (casoEncontrado || verificarCaso(valSwitch, caso, tabla)) {
                
                casoEncontrado = true; // Activamos bandera para ejecutar este y los siguientes
                
                // Ejecutamos las instrucciones del caso
                if (caso.bloque != null) {
                    for (Instruccion ins : caso.bloque) {
                        Object res = ins.ejecutar(tabla);
                        
                        // Si encontramos un BREAK, terminamos TODO el switch
                        if (res != null && res.equals("BREAK")) {
                            return null;
                        }
                    }
                }
            }
        }
        return null;
    }

    // Método auxiliar para comparar valores
    private boolean verificarCaso(Object valSwitch, Case caso, Object tabla) {
        if (caso.esDefault) return true; // El default siempre coincide si llegamos a él
        
        Object valCaso = caso.valor.ejecutar(tabla);
        return valSwitch.equals(valCaso);
    }
}