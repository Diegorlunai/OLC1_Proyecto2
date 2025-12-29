package arbol;
import java.util.LinkedList;
import simbolo.TablaSimbolos;

public class DeclaracionLista extends Instruccion {
    private String id;
    private String tipo;

    public DeclaracionLista(String id, String tipo, int linea, int col) {
        super(linea, col);
        this.id = id;
        this.tipo = tipo;
    }

    @Override
    public Object ejecutar(Object tabla) {
        // Creamos una LinkedList de Java real para manejar la lista dinámica
        ((TablaSimbolos)tabla).guardar(id, new LinkedList<Object>());
        return null;
    }
}