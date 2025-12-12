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
        // 1. Convertimos el objeto tabla a nuestra clase TablaSimbolos
        TablaSimbolos ts = (TablaSimbolos) tabla;
        
        // 2. Calculamos el valor de la expresión (ej: 10 + 5)
        Object valor = expresion.ejecutar(ts);
        
        // 3. (OPCIONAL) Aquí validaríamos tipos. Ej: Si tipo es "int" y valor es "hola", dar error.
        // Por ahora lo guardamos directo para avanzar.
        
        // 4. Guardamos en la tabla de símbolos
        ts.guardar(identificador, valor);
        
        System.out.println("Variable guardada: " + identificador + " = " + valor);
        return null;
    }
}