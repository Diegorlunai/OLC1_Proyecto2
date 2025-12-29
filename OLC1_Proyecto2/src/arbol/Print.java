package arbol;

import main.Salida; // <--- CORRECCIÓN: Apuntamos al paquete 'main' donde está tu archivo

public class Print extends Instruccion {

    private Instruccion expresion;
    private boolean salto; 

    public Print(Instruccion expresion, boolean salto, int linea, int col) {
        super(linea, col);
        this.expresion = expresion;
        this.salto = salto;
    }

    @Override
    public Object ejecutar(Object tabla) {
        Object val = expresion.ejecutar(tabla);
        
        if (val != null) {
            if (salto) {
                Salida.agregar(val.toString());
            } else {
                Salida.agregarSinSalto(val.toString());
            }
        }
        return null;
    }
}