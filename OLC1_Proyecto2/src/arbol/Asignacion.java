package arbol;

import simbolo.TablaSimbolos;

public class Asignacion extends Instruccion {

    private String id;
    private Instruccion expresion;

    public Asignacion(String id, Instruccion expresion, int linea, int col) {
        super(linea, col);
        this.id = id;
        this.expresion = expresion;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        
        // 1. Calculamos el nuevo valor
        Object valor = expresion.ejecutar(ts);
        
        // 2. CORRECCIÓN: Usamos 'asignar' para que busque la variable global
        // en lugar de 'guardar', que crearía una nueva local tapando la global.
        ts.asignar(id, valor);
        
        return null;
    }
}