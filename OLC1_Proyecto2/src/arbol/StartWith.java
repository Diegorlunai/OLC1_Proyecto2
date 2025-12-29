package arbol;

import java.util.LinkedList;
import simbolo.TablaSimbolos;

public class StartWith extends Instruccion {
    
    private String nombreFuncion;

    public StartWith(String nombreFuncion, int linea, int col) {
        super(linea, col);
        this.nombreFuncion = nombreFuncion;
    }

    @Override
    public Object ejecutar(Object tabla) {
        // 1. Crear lista de argumentos vacía
        LinkedList<Instruccion> argumentosVacios = new LinkedList<>();
        
        // 2. Llamar al constructor de LlamadaFuncion
        // CORRECCIÓN: Usamos 'this.columna' (porque en Instruccion.java se llama 'columna')
        LlamadaFuncion llamada = new LlamadaFuncion(
            nombreFuncion, 
            argumentosVacios, 
            this.linea, 
            this.columna  // <--- ¡AQUÍ ESTABA EL ERROR! (Decía super.col)
        );
        
        return llamada.ejecutar(tabla);
    }
}