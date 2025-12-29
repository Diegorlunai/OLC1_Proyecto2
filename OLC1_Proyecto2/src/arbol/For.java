package arbol;

import java.util.LinkedList;
import simbolo.TablaSimbolos;

public class For extends Instruccion {

    private Instruccion asignacion;
    private Instruccion condicion;
    private Instruccion actualizacion;
    private LinkedList<Instruccion> bloque;

    public For(Instruccion asignacion, Instruccion condicion, Instruccion actualizacion, LinkedList<Instruccion> bloque, int linea, int col) {
        super(linea, col);
        this.asignacion = asignacion;
        this.condicion = condicion;
        this.actualizacion = actualizacion;
        this.bloque = bloque;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        
        // Crear entorno local para el FOR
        TablaSimbolos entornoFor = new TablaSimbolos(ts);
        
        // 1. Inicializar
        asignacion.ejecutar(entornoFor);

        // 2. Validar condición
        Object condRes = condicion.ejecutar(entornoFor);
        if (!(condRes instanceof Boolean)) {
            System.err.println("⌦ Error: Condición de FOR inválida.");
            return null;
        }

        // 3. Ciclo
        while ((Boolean) condRes) {
            
            // A. Ejecutar instrucciones internas en UN NUEVO entorno por cada iteración
            TablaSimbolos entornoIteracion = new TablaSimbolos(entornoFor);
            for (Instruccion ins : bloque) {
                Object resultado = ins.ejecutar(entornoIteracion);
                
                if (resultado != null) {
                    if (resultado.equals("BREAK")) {
                        return null; // Salir del FOR completo
                    }
                    if (resultado.equals("CONTINUE")) {
                        break; // Saltar al paso B (Actualización)
                    }
                    // Si es un return, propagarlo
                    if (!resultado.equals("BREAK") && !resultado.equals("CONTINUE")) {
                        return resultado;
                    }
                }
            }

            // B. Actualizar (i++) en el entorno del FOR
            actualizacion.ejecutar(entornoFor);

            // C. Re-evaluar condición
            condRes = condicion.ejecutar(entornoFor);
            if (!(condRes instanceof Boolean)) return null;
        }

        return null;
    }
}