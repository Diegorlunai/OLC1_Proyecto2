package arbol;

import main.Salida; // Importamos la nueva clase

public class Impresion extends Instruccion {

    private Instruccion expresion;

    public Impresion(Instruccion expresion, int linea, int col) {
        super(linea, col);
        this.expresion = expresion;
    }

    @Override
    public Object ejecutar(Object tabla) {
        Object resultado = expresion.ejecutar(tabla);
        
        // EN LUGAR DE System.out.println, USAMOS NUESTRA CLASE
        if (resultado != null) {
            Salida.imprimir(resultado.toString());
        } else {
            Salida.imprimir("null");
        }
        
        return null;
    }
}