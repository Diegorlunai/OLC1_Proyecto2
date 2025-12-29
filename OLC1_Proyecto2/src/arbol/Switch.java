package arbol;

import java.util.LinkedList;
import simbolo.TablaSimbolos;

public class Switch extends Instruccion {

    private Instruccion expresion;
    private LinkedList<Instruccion> listaCasos;

    public Switch(Instruccion expresion, LinkedList<Instruccion> listaCasos, int linea, int col) {
        super(linea, col);
        this.expresion = expresion;
        this.listaCasos = listaCasos;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        Object valSwitch = expresion.ejecutar(ts);
        
        boolean encontrado = false;
        boolean ejecutarTodo = false; // Para fall-through
        
        for (Instruccion ins : listaCasos) {
            Caso caso = (Caso) ins;
            
            // Si ya encontramos un caso, ejecutamos todo (fall-through)
            if (ejecutarTodo || caso.esDefault) {
                if (caso.bloque != null) {
                    for (Instruccion i : caso.bloque) {
                        Object res = i.ejecutar(ts);
                        
                        // Si hay BREAK, salimos del switch completo
                        if (res != null && res.equals("BREAK")) {
                            return null;
                        }
                        
                        // Si hay RETURN, lo propagamos
                        if (res != null && !res.equals("BREAK") && !res.equals("CONTINUE")) {
                            return res;
                        }
                    }
                }
                encontrado = true;
                continue; // Seguir ejecutando los siguientes casos (fall-through)
            }
            
            // Verificar si el valor coincide
            if (!encontrado && caso.valor != null) {
                Object valorCaso = caso.valor.ejecutar(ts);
                if (valSwitch != null && valSwitch.toString().equals(valorCaso.toString())) {
                    encontrado = true;
                    ejecutarTodo = true; // Activar fall-through
                    
                    // Ejecutar este caso
                    if (caso.bloque != null) {
                        for (Instruccion i : caso.bloque) {
                            Object res = i.ejecutar(ts);
                            
                            if (res != null && res.equals("BREAK")) {
                                return null;
                            }
                            
                            if (res != null && !res.equals("BREAK") && !res.equals("CONTINUE")) {
                                return res;
                            }
                        }
                    }
                }
            }
        }
        
        return null;
    }
}