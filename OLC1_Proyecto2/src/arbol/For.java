package arbol;

import java.util.LinkedList;

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
        // 1. Inicializar
        asignacion.ejecutar(tabla);

        // 2. Validar condición
        Object condRes = condicion.ejecutar(tabla);
        if (!(condRes instanceof Boolean)) {
            System.err.println("❌ Error: Condición de FOR inválida.");
            return null;
        }

        // 3. Ciclo
        while ((Boolean) condRes) {
            
            // A. Ejecutar instrucciones internas
            for (Instruccion ins : bloque) {
                Object resultado = ins.ejecutar(tabla);
                
                if (resultado != null) {
                    if (resultado.equals("BREAK")) {
                        return null; // Salir del FOR completo
                    }
                    if (resultado.equals("CONTINUE")) {
                        break; // Saltar al paso B (Actualización)
                    }
                }
            }

            // B. Actualizar (i++)
            actualizacion.ejecutar(tabla);

            // C. Re-evaluar condición
            condRes = condicion.ejecutar(tabla);
            if (!(condRes instanceof Boolean)) return null;
        }

        return null;
    }
}