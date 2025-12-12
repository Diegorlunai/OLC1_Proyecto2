package arbol;

import java.util.LinkedList;

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
        Object result;
        
        do {
            // 1. Ejecutar instrucciones PRIMERO
            for (Instruccion ins : instrucciones) {
                Object res = ins.ejecutar(tabla);
                
                if (res != null) {
                    if (res.equals("BREAK")) return null;
                    if (res.equals("CONTINUE")) break;
                }
            }

            // 2. Evaluar condición DESPUÉS
            result = condicion.ejecutar(tabla);
            if (!(result instanceof Boolean)) {
                System.err.println("❌ Error: Condición DO-WHILE inválida.");
                return null;
            }

        } while ((Boolean) result);
        
        return null;
    }
}