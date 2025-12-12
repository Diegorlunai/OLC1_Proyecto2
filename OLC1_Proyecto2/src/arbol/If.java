package arbol;

import java.util.LinkedList;

public class If extends Instruccion {

    private Instruccion condicion;
    private LinkedList<Instruccion> sentenciasIf;
    private LinkedList<Instruccion> sentenciasElse;

    public If(Instruccion condicion, LinkedList<Instruccion> sentenciasIf, LinkedList<Instruccion> sentenciasElse, int linea, int col) {
        super(linea, col);
        this.condicion = condicion;
        this.sentenciasIf = sentenciasIf;
        this.sentenciasElse = sentenciasElse;
    }

    @Override
    public Object ejecutar(Object tabla) {
        Object resultadoCondicion = condicion.ejecutar(tabla);

        if (resultadoCondicion instanceof Boolean) {
            
            if ((Boolean) resultadoCondicion) {
                // --- CAMINO VERDADERO ---
                for (Instruccion ins : sentenciasIf) {
                    Object res = ins.ejecutar(tabla);
                    
                    // CORRECCIÓN: Ahora propagamos BREAK y CONTINUE
                    if (res != null) {
                        if (res.equals("BREAK")) return "BREAK";
                        if (res.equals("CONTINUE")) return "CONTINUE";
                    }
                }
            } else {
                // --- CAMINO FALSO (ELSE) ---
                if (sentenciasElse != null) {
                    for (Instruccion ins : sentenciasElse) {
                        Object res = ins.ejecutar(tabla);
                        
                        // CORRECCIÓN AQUÍ TAMBIÉN
                        if (res != null) {
                            if (res.equals("BREAK")) return "BREAK";
                            if (res.equals("CONTINUE")) return "CONTINUE";
                        }
                    }
                }
            }
            
        } else {
            System.err.println("❌ Error Semántico: La condición del IF debe ser booleana. Línea: " + linea);
        }
        
        return null;
    }
}