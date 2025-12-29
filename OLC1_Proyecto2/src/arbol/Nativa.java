package arbol;

import simbolo.TablaSimbolos;

public class Nativa extends Instruccion {

    private Instruccion expresion;
    private String funcion; // "length", "round", "toString"

    public Nativa(Instruccion expresion, String funcion, int linea, int col) {
        super(linea, col);
        this.expresion = expresion;
        this.funcion = funcion;
    }

    @Override
    public Object ejecutar(Object tabla) {
        Object valor = expresion.ejecutar(tabla);
        
        if (valor == null) return null;

        switch (funcion.toLowerCase()) {
            case "length":            
                if (valor instanceof Object[]) {
                    return ((Object[]) valor).length;
                } else if (valor instanceof String) {
                    return ((String) valor).length();
                } else if (valor instanceof java.util.LinkedList) {
                    return ((java.util.LinkedList) valor).size();
                } else {
                    System.err.println("⌦ Error: 'length' solo aplica a vectores o cadenas.");
                    return 0;
                }   

            case "round":
                if (valor instanceof Double) {
                    return (int) Math.round((Double) valor);
                } else if (valor instanceof Integer) {
                    return valor;
                }
                return null;

            case "tostring":
                return String.valueOf(valor);

            default:
                return null;
        }
    }
}