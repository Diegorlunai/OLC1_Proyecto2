package arbol;

import simbolo.TablaSimbolos;

public class FindVector extends Instruccion {
    private String id;
    private Instruccion valorBuscado;

    public FindVector(String id, Instruccion valorBuscado, int linea, int col) {
        super(linea, col);
        this.id = id;
        this.valorBuscado = valorBuscado;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        Object vector = ts.obtener(id);
        
        if (vector == null) {
            System.err.println("⌦ Error: El vector '" + id + "' no existe.");
            return Boolean.FALSE;  // ← CAMBIO: Boolean en lugar de false
        }
        
        if (!(vector instanceof Object[])) {
            System.err.println("⌦ Error: '" + id + "' no es un vector.");
            return Boolean.FALSE;  // ← CAMBIO: Boolean en lugar de false
        }
        
        Object[] arr = (Object[]) vector;
        Object valorABuscar = valorBuscado.ejecutar(ts);
        
        if (valorABuscar == null) {
            return Boolean.FALSE;
        }
        
        // Buscar en el vector
        for (Object elemento : arr) {
            if (elemento != null) {
                // Comparación numérica inteligente
                if (elemento instanceof Number && valorABuscar instanceof Number) {
                    if (((Number)elemento).doubleValue() == ((Number)valorABuscar).doubleValue()) {
                        return Boolean.TRUE;  // ← CAMBIO: Boolean en lugar de true
                    }
                } else if (elemento.toString().equals(valorABuscar.toString())) {
                    return Boolean.TRUE;  // ← CAMBIO: Boolean en lugar de true
                }
            }
        }
        
        return Boolean.FALSE;  // ← CAMBIO: Boolean en lugar de false
    }
}