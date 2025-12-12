package arbol;

import simbolo.TablaSimbolos;

public class AccesoVar extends Instruccion {

    private String identificador;

    public AccesoVar(String id, int linea, int col) {
        super(linea, col);
        this.identificador = id;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        
        // Buscamos la variable
        Object valor = ts.obtener(identificador);
        
        if (valor == null) {
            // Si no existe, es un error semántico (pero no detendremos la ejecución hoy)
            System.err.println("Error Semántico: La variable '" + identificador + "' no existe. (Línea: " + linea + ")");
            return null;
        }
        
        return valor;
    }
}