package arbol;

import simbolo.TablaSimbolos;

public class Declaracion extends Instruccion {

    private String identificador;
    private String tipo;
    private Instruccion expresion;

    public Declaracion(String id, String tipo, Instruccion expresion, int linea, int col) {
        super(linea, col);
        this.identificador = id;
        this.tipo = tipo;
        this.expresion = expresion;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        Object valor = null;

        // Si hay expresión, la ejecutamos
        if (expresion != null) {
            valor = expresion.ejecutar(ts);
        } else {
            // VALORES POR DEFECTO (Si solo se declara: var x: int;)
            switch (tipo.toLowerCase()) {
                case "int": valor = 0; break;
                case "double": valor = 0.0; break;
                case "bool": valor = false; break;
                case "string": valor = ""; break;
                case "char": valor = '\u0000'; break; // Caracter nulo
                default: valor = null;
            }
        }

        // Guardamos en la tabla
        ts.guardar(identificador, valor);
        return null;
    }
}