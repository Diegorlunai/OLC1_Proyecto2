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
        
        // 1. Calculamos el nuevo valor (ej: contador + 1)
        Object valor = expresion.ejecutar(ts);
        
        // 2. Verificamos si la variable existe (opcional pero recomendado)
        if (ts.obtener(id) != null) {
            // 3. Sobrescribimos el valor en la tabla
            ts.guardar(id, valor);
        } else {
            System.err.println("❌ Error Semántico: La variable '" + id + "' no ha sido declarada. Línea: " + linea);
        }
        
        return null;
    }
}