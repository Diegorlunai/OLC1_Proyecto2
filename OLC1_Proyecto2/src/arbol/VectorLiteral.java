package arbol;

import java.util.LinkedList;
import simbolo.TablaSimbolos;

public class VectorLiteral extends Instruccion {
    private LinkedList<Instruccion> valores;

    public VectorLiteral(LinkedList<Instruccion> valores, int linea, int col) {
        super(linea, col);
        this.valores = valores;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        Object[] arreglo = new Object[valores.size()];
        for(int i=0; i<valores.size(); i++){
            arreglo[i] = valores.get(i).ejecutar(ts);
        }
        return arreglo;
    }
}