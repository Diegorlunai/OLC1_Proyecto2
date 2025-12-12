package arbol;

import simbolo.TablaSimbolos;

public class Incremento extends Instruccion {

    private String id;
    private boolean esIncremento; // true = ++, false = --

    public Incremento(String id, boolean esIncremento, int linea, int col) {
        super(linea, col);
        this.id = id;
        this.esIncremento = esIncremento;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;
        
        // 1. Obtener el valor actual
        Object valor = ts.obtener(id);
        
        if (valor == null) {
            System.err.println("❌ Error: Variable '" + id + "' no encontrada.");
            return null;
        }

        // 2. Operar (Asumimos Double por ahora)
        try {
            double valorNum = Double.parseDouble(valor.toString());
            
            if (esIncremento) {
                valorNum = valorNum + 1;
            } else {
                valorNum = valorNum - 1;
            }
            
            // 3. Guardar el nuevo valor
            ts.guardar(id, valorNum);
            
        } catch (NumberFormatException e) {
            System.err.println("❌ Error: No se puede incrementar/decrementar la variable '" + id + "' porque no es número.");
        }
        
        return null;
    }
}