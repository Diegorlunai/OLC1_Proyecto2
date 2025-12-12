package arbol;

public class Primitivo extends Instruccion {
    
    private Object valor;

    public Primitivo(Object valor, int linea, int col) {
        super(linea, col);
        this.valor = valor;
    }

    @Override
    public Object ejecutar(Object tabla) {
        return this.valor;
    }
}