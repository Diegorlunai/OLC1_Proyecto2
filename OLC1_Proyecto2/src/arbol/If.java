package arbol;

import java.util.LinkedList;
import simbolo.TablaSimbolos;

public class If extends Instruccion {

    private Instruccion condicion;
    private LinkedList<Instruccion> sentenciasTrue;
    private LinkedList<Instruccion> sentenciasFalse;

    // Constructor completo
    public If(Instruccion condicion, LinkedList<Instruccion> sentenciasTrue, LinkedList<Instruccion> sentenciasFalse, int linea, int col) {
        super(linea, col);
        this.condicion = condicion;
        this.sentenciasTrue = sentenciasTrue;
        this.sentenciasFalse = sentenciasFalse;
    }

    // Constructor para IF dentro de ELSE (else if)
    public If(Instruccion condicion, LinkedList<Instruccion> sentenciasTrue, LinkedList<Instruccion> sentenciasFalse) {
        super(0, 0); // Línea/Col no importan tanto en recursión interna
        this.condicion = condicion;
        this.sentenciasTrue = sentenciasTrue;
        this.sentenciasFalse = sentenciasFalse;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        
        Object cond = condicion.ejecutar(ts);
        if (cond == null) return null; // Error en condición

        if (Boolean.parseBoolean(cond.toString())) {
            // --- BLOQUE TRUE ---
            TablaSimbolos tablaLocal = new TablaSimbolos(ts);
            for (Instruccion ins : sentenciasTrue) {
                Object res = ins.ejecutar(tablaLocal);
                // ¡IMPORTANTE! Si una instrucción devuelve algo (Return), lo propagamos y DETENEMOS el If.
                if (res != null) return res; 
            }
        } else {
            // --- BLOQUE FALSE ---
            if (sentenciasFalse != null) {
                TablaSimbolos tablaLocal = new TablaSimbolos(ts);
                for (Instruccion ins : sentenciasFalse) {
                    Object res = ins.ejecutar(tablaLocal);
                    // ¡IMPORTANTE! Propagar retorno también aquí
                    if (res != null) return res; 
                }
            }
        }
        
        return null; // Si no hubo return, seguimos normal
    }
}