package arbol;

import java.util.LinkedList;
import simbolo.TablaSimbolos;

public class DeclaracionVector extends Instruccion {

    private String id;
    private String tipo;
    private int dimensiones; // 1 o 2
    private LinkedList<Instruccion> valores; // Lista de expresiones para inicializar

    public DeclaracionVector(String id, String tipo, int dimensiones, LinkedList<Instruccion> valores, int linea, int col) {
        super(linea, col);
        this.id = id;
        this.tipo = tipo;
        this.dimensiones = dimensiones;
        this.valores = valores;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;

        // 1. Validar si ya existe
        if (ts.obtenerEnActual(id) != null) {
            System.err.println("❌ Error: La variable '" + id + "' ya existe.");
            return null;
        }

        // 2. Crear el arreglo
        Object[] arreglo = new Object[valores.size()];
        
        // 3. Llenar el arreglo evaluando las expresiones
        for (int i = 0; i < valores.size(); i++) {
            Object val = valores.get(i).ejecutar(ts);
            arreglo[i] = val;
        }

        // 4. Guardar en la tabla de símbolos
        // El tipo guardado será "int[]" o "char[][]", etc.
        String tipoArreglo = tipo;
        for(int k=0; k<dimensiones; k++) tipoArreglo += "[]";
        
        ts.guardar(id, arreglo); 
        // Nota: En un compilador estricto, aquí validarías que todos los valores sean del 'tipo' correcto.
        
        return null;
    }
}