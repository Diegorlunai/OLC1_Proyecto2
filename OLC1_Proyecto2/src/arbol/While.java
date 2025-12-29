package arbol;

import java.util.LinkedList;
import simbolo.TablaSimbolos;

public class While extends Instruccion {

    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;

    public While(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(linea, col);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        
        // 1. Primera evaluación
        Object result = condicion.ejecutar(ts);
        
        if (!(result instanceof Boolean)) {
            System.err.println("❌ Error: La condición del WHILE debe ser booleana.");
            return null;
        }

        // 2. Ciclo
        while ((Boolean) result) {
            
            // ✅ CAMBIO: Crear NUEVO entorno para cada iteración
            TablaSimbolos entornoIteracion = new TablaSimbolos(ts);
            
            // Ejecutar instrucciones internas
            for (Instruccion ins : instrucciones) {
                Object resInstruccion = ins.ejecutar(entornoIteracion); // ✅ Usar entorno local
                
                if (resInstruccion != null) {
                    if (resInstruccion.equals("BREAK")) {
                        return null; // Romper ciclo
                    }
                    if (resInstruccion.equals("CONTINUE")) {
                        break; // Romper el 'for' de instrucciones para ir a la siguiente vuelta del 'while'
                    }
                }
            }

            // 3. Re-evaluar condición (en el entorno PADRE)
            result = condicion.ejecutar(ts);
            if (!(result instanceof Boolean)) return null;
        }
        
        return null;
    }
}