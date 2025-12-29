package arbol;

import simbolo.TablaSimbolos;

public class Return extends Instruccion {
    private Instruccion expresion;

    public Return(Instruccion expresion, int linea, int col) {
        super(linea, col);
        this.expresion = expresion;
    }

    @Override
    public Object ejecutar(Object tabla) {
        // Si hay expresión (return 5;), la evaluamos y devolvemos el valor.
        if (expresion != null) {
            return expresion.ejecutar((TablaSimbolos) tabla);
        }
        // Si es return vacío (return;), devolvemos un objeto bandera (o string vacío)
        return ""; 
    }
}