package arbol;

import simbolo.TablaSimbolos; 

public class Aritmetica extends Instruccion {

    private Instruccion izquierda;
    private Instruccion derecha;
    private Operacion operacion;

    // Constructor binario (Suma, Resta...)
    public Aritmetica(Instruccion izq, Instruccion der, Operacion op, int linea, int col) {
        super(linea, col);
        this.izquierda = izq;
        this.derecha = der;
        this.operacion = op;
    }
    
    // Constructor unario (Negación: -5)
    public Aritmetica(Instruccion izq, Operacion op, int linea, int col) {
        super(linea, col);
        this.izquierda = izq;
        this.derecha = null;
        this.operacion = op;
    }

    @Override
    public Object ejecutar(Object tabla) {
        Object valIzq = izquierda.ejecutar(tabla);
        Object valDer = (derecha != null) ? derecha.ejecutar(tabla) : null;

        // --- BLINDAJE CONTRA NULOS (Vital para evitar crashes) ---
        if (valIzq == null) {
            System.err.println("⚠️ Error Aritmético: Operando izquierdo nulo en línea " + linea);
            return null; // Retornamos null para no romper el programa
        }
        if (operacion != Operacion.NEGACION && valDer == null) {
            System.err.println("⚠️ Error Aritmético: Operando derecho nulo en línea " + linea);
            return null;
        }
        // ---------------------------------------------------------

        switch (operacion) {
            case SUMA:
                try {
                    // Intentamos sumar números
                    return Double.parseDouble(valIzq.toString()) + Double.parseDouble(valDer.toString());
                } catch (Exception e) {
                    // Si falla, concatenamos cadenas
                    return valIzq.toString() + valDer.toString();
                }

            case RESTA:
                return Double.parseDouble(valIzq.toString()) - Double.parseDouble(valDer.toString());

            case MULTIPLICACION:
                return Double.parseDouble(valIzq.toString()) * Double.parseDouble(valDer.toString());

            case DIVISION:
                double div = Double.parseDouble(valDer.toString());
                if (div == 0) {
                    System.err.println("❌ Error: División entre cero en línea " + linea);
                    return 0.0;
                }
                return Double.parseDouble(valIzq.toString()) / div;
            
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