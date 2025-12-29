package arbol;

public class Continue extends Instruccion {

    public Continue(int linea, int col) {
        super(linea, col);
    }

    @Override
    public Object ejecutar(Object tabla) {
        return "CONTINUE"; // La señal para saltar iteración
    }
}