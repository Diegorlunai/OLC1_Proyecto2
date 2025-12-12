package arbol;

import main.Salida;

public class Impresion extends Instruccion {

    private Instruccion expresion;

    public Impresion(Instruccion expresion, int linea, int col) {
        super(linea, col);
        this.expresion = expresion;
    }

    @Override
    public Object ejecutar(Object tabla) {
        Object resultado = expresion.ejecutar(tabla);
        
        if (resultado != null) {
            // Lógica para quitar el .0 si es un entero visualmente
            if (resultado instanceof Double) {
                double num = (Double) resultado;
                if (num % 1 == 0) {
                    Salida.imprimir(String.valueOf((int) num)); // Imprime "100"
                } else {
                    Salida.imprimir(String.valueOf(num)); // Imprime "10.5"
                }
            } else {
                Salida.imprimir(resultado.toString());
            }
        } else {
            Salida.imprimir("null");
        }
        
        return null;
    }
}