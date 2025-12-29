package arbol;
import java.util.LinkedList;
import simbolo.TablaSimbolos;

public class MetodoLista extends Instruccion {
    private String id;
    private String metodo; // "append", "remove", "find"
    private Instruccion valor;

    public MetodoLista(String id, String metodo, Instruccion valor, int linea, int col) {
        super(linea, col);
        this.id = id;
        this.metodo = metodo;
        this.valor = valor;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        LinkedList<Object> lista = (LinkedList<Object>) ts.obtener(id);
        Object val = valor.ejecutar(tabla);
        
        if (metodo.equalsIgnoreCase("append")) {
            lista.add(val);
        } else if (metodo.equalsIgnoreCase("remove")) {
            int index = Integer.parseInt(val.toString());
            return lista.remove(index);
        } else if (metodo.equalsIgnoreCase("find")) {
            return lista.contains(val);
        }
        return null;
    }
}