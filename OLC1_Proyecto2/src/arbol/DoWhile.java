package arbol;

import java.util.LinkedList;
import simbolo.TablaSimbolos;

public class DoWhile extends Instruccion {

    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;

    public DoWhile(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(linea, col);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        Object result;
        
        do {
            // ✅ CAMBIO: Crear NUEVO entorno para cada iteración
            TablaSimbolos entornoIteracion = new TablaSimbolos(ts);
            
            // 1. Ejecutar instrucciones PRIMERO
            for (Instruccion ins : instrucciones) {
                Object res = ins.ejecutar(entornoIteracion); // ✅ Usar entorno local
                
                if (res != null) {
                    if (res.equals("BREAK")) return null;
                    if (res.equals("CONTINUE")) break;
                }
            }

            // 2. Evaluar condición DESPUÉS (en el entorno PADRE)
            result = condicion.ejecutar(ts);
            if (!(result instanceof Boolean)) {
                System.err.println("❌ Error: Condición DO-WHILE inválida.");
                return null;
            }

        } while ((Boolean) result);
        
        return null;
    }
}   