package arbol;

public class Logica extends Instruccion {

    private Instruccion izquierda;
    private Instruccion derecha;
    private Operacion.Tipo_Operacion tipo;

    public Logica(Instruccion izq, Instruccion der, Operacion.Tipo_Operacion tipo, int linea, int col) {
        super(linea, col);
        this.izquierda = izq;
        this.derecha = der;
        this.tipo = tipo;
    }

    public Logica(Instruccion izq, Operacion.Tipo_Operacion tipo, int linea, int col) {
        super(linea, col);
        this.izquierda = izq;
        this.derecha = null;
        this.tipo = tipo;
    }

    @Override
    public Object ejecutar(Object tabla) {
        Object valIzq = izquierda.ejecutar(tabla);
        Object valDer = (derecha != null) ? derecha.ejecutar(tabla) : null;

        if (valIzq == null) return null;
        if (derecha != null && valDer == null) return null;

        switch (tipo) {
            case IGUALIGUAL:
                return valIzq.toString().equals(valDer.toString());
            case DIFERENTE:
                return !valIzq.toString().equals(valDer.toString());
            case MENOR:
                return getDouble(valIzq) < getDouble(valDer);
            case MAYOR:
                return getDouble(valIzq) > getDouble(valDer);
            case MENORIGUAL:
                return getDouble(valIzq) <= getDouble(valDer); // ¡Aquí estaba fallando!
            case MAYORIGUAL:
                return getDouble(valIzq) >= getDouble(valDer);
            case AND:
                return Boolean.parseBoolean(valIzq.toString()) && Boolean.parseBoolean(valDer.toString());
            case OR:
                return Boolean.parseBoolean(valIzq.toString()) || Boolean.parseBoolean(valDer.toString());
            case XOR:
                return Boolean.parseBoolean(valIzq.toString()) ^ Boolean.parseBoolean(valDer.toString());
            case NOT:
                return !Boolean.parseBoolean(valIzq.toString());
            default:
                return null;
        }
    }

    // Método auxiliar seguro para obtener números
    private double getDouble(Object val) {
        if (val instanceof Number) return ((Number) val).doubleValue();
        try {
            return Double.parseDouble(val.toString());
        } catch (NumberFormatException e) {
            System.err.println("Error numérico en Logica: " + val);
            return 0.0;
        }
    }
}