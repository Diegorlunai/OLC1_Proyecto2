package arbol;

public class Aritmetica extends Instruccion {
    private Instruccion izquierda;
    private Instruccion derecha;
    private Operacion.Tipo_Operacion tipo;

    public Aritmetica(Instruccion izq, Instruccion der, Operacion.Tipo_Operacion tipo, int linea, int col) {
        super(linea, col);
        this.izquierda = izq;
        this.derecha = der;
        this.tipo = tipo;
    }
    
    public Aritmetica(Instruccion izq, Operacion.Tipo_Operacion tipo, int linea, int col) {
        super(linea, col);
        this.izquierda = izq;
        this.derecha = null;
        this.tipo = tipo;
    }

    @Override
    public Object ejecutar(Object tabla) {
        Object valIzq = izquierda.ejecutar(tabla);
        Object valDer = (derecha != null) ? derecha.ejecutar(tabla) : null;

        // Si falla la izquierda, abortamos
        if (valIzq == null) return null;

        switch (tipo) {
            case SUMA:
                try {
                    // Intenta suma aritmética
                    double res = getDouble(valIzq) + getDouble(valDer);
                    if (res % 1 == 0) return (int) res; 
                    return res;
                } catch (Exception e) {
                    // Si falla, es concatenación. MANEJO SEGURO DE NULLS
                    String s1 = valIzq.toString();
                    String s2 = (valDer != null) ? valDer.toString() : "null";
                    return s1 + s2; 
                }
            case RESTA:
                if (valDer == null) return null; // Protección
                double r1 = getDouble(valIzq) - getDouble(valDer);
                if (r1 % 1 == 0) return (int) r1; return r1;
            case MULTIPLICACION:
                if (valDer == null) return null;
                double r2 = getDouble(valIzq) * getDouble(valDer);
                if (r2 % 1 == 0) return (int) r2; return r2;
            case DIVISION:
                if (valDer == null) return null;
                double valD = getDouble(valDer);
                if (valD == 0) {
                    System.err.println("Error: División por cero.");
                    return null;
                }
                double r3 = getDouble(valIzq) / valD;
                if (r3 % 1 == 0) return (int) r3; return r3;
            case MODULO:
                if (valDer == null) return null;
                double r4 = getDouble(valIzq) % getDouble(valDer);
                if (r4 % 1 == 0) return (int) r4; return r4;
            case POTENCIA:
                if (valDer == null) return null;
                double r5 = Math.pow(getDouble(valIzq), getDouble(valDer));
                if (r5 % 1 == 0) return (int) r5; return r5;
            case NEGACION:
                double r6 = getDouble(valIzq) * -1;
                if (r6 % 1 == 0) return (int) r6; return r6;
            default: return null;
        }
    }
    
    private double getDouble(Object val) {
        if (val == null) return 0.0; // Protección extra
        if (val instanceof Number) return ((Number) val).doubleValue();
        try {
            return Double.parseDouble(val.toString());
        } catch(Exception e) {
            throw e; // Lanzar para que lo capture el catch de SUMA (Concatenación)
        }
    }
}