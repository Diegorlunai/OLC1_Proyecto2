package arbol;

import java.util.LinkedList;
import simbolo.TablaSimbolos;

public class LlamadaFuncion extends Instruccion {
    private String nombre;
    private LinkedList<Instruccion> argumentos;

    public LlamadaFuncion(String nombre, LinkedList<Instruccion> argumentos, int linea, int col) {
        super(linea, col);
        this.nombre = nombre;
        this.argumentos = argumentos;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        
        Object objetoFuncion = ts.obtener(nombre);
        if (objetoFuncion == null) return null;
        
        Funcion funcion = (Funcion) objetoFuncion;
        LinkedList<Instruccion> parametros = funcion.getParametros();

        if (parametros.size() != argumentos.size()) {
            System.err.println("Error: Cantidad de parámetros incorrecta.");
            return null;
        }
        
        // --- OPTIMIZACIÓN: Usar Entorno Global como padre ---
        // Asegúrate de que TablaSimbolos tenga el método getEntornoGlobal() que te di antes.
        TablaSimbolos tablaLocal = new TablaSimbolos(ts.getEntornoGlobal()); 
        // --------------------------------------------------

        for (int i = 0; i < parametros.size(); i++) {
            Declaracion decl = (Declaracion) parametros.get(i);
            Object valorArg = argumentos.get(i).ejecutar(ts);
            tablaLocal.guardar(decl.getId(), valorArg);
        }

        for (Instruccion ins : funcion.getInstrucciones()) {
            Object res = ins.ejecutar(tablaLocal);
            if (res != null) {
                if (funcion.getTipo().equalsIgnoreCase("void")) return null;
                return res;
            }
        }
        return null;
    }
}