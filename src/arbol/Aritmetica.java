package arbol;

// Importamos la tabla de símbolos (aunque no la usemos todavía en sumas simples)
import simbolo.TablaSimbolos; 

public class Aritmetica extends Instruccion {

    private Instruccion izquierda;
    private Instruccion derecha;
    private Operacion operacion;

    // Constructor para operaciones de dos lados (Suma, Resta...)
    public Aritmetica(Instruccion izq, Instruccion der, Operacion op, int linea, int col) {
        super(linea, col);
        this.izquierda = izq;
        this.derecha = der;
        this.operacion = op;
    }
    
    // Constructor para operaciones de un lado (Negación: -5)
    public Aritmetica(Instruccion izq, Operacion op, int linea, int col) {
        super(linea, col);
        this.izquierda = izq;
        this.derecha = null;
        this.operacion = op;
    }

    @Override
    public Object ejecutar(Object tabla) {
        // 1. Obtenemos los valores de los hijos
        Object valIzq = izquierda.ejecutar(tabla);
        Object valDer = (derecha != null) ? derecha.ejecutar(tabla) : null;

        // 2. Operamos según el tipo
        switch (operacion) {
            case SUMA:
                try {
                    // Intentamos sumar como números (Doubles)
                    return Double.parseDouble(valIzq.toString()) + Double.parseDouble(valDer.toString());
                } catch (Exception e) {
                    // Si falla, es texto: concatenamos
                    return valIzq.toString() + valDer.toString();
                }

            case RESTA:
                return Double.parseDouble(valIzq.toString()) - Double.parseDouble(valDer.toString());

            case MULTIPLICACION:
                return Double.parseDouble(valIzq.toString()) * Double.parseDouble(valDer.toString());

            case DIVISION:
                return Double.parseDouble(valIzq.toString()) / Double.parseDouble(valDer.toString());
            
            case POTENCIA:
                 return Math.pow(Double.parseDouble(valIzq.toString()), Double.parseDouble(valDer.toString()));

            case MODULO:
                return Double.parseDouble(valIzq.toString()) % Double.parseDouble(valDer.toString());

            case NEGACION:
                return Double.parseDouble(valIzq.toString()) * -1;

            default:
                return null;
        }
    }
}