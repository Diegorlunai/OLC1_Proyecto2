package arbol;

public class Break extends Instruccion {

    public Break(int linea, int col) {
        super(linea, col);
    }

    @Override
    public Object ejecutar(Object tabla) {
        // Retornamos una "señal" única que el ciclo entenderá
        return "BREAK"; 
    }
}