package arbol;

public class Logica extends Instruccion {

    private Instruccion izquierda;
    private Instruccion derecha;
    private Operacion operacion;

    public Logica(Instruccion izq, Instruccion der, Operacion op, int linea, int col) {
        super(linea, col);
        this.izquierda = izq;
        this.derecha = der;
        this.operacion = op;
    }

    public Logica(Instruccion izq, Operacion op, int linea, int col) {
        super(linea, col);
        this.izquierda = izq;
        this.derecha = null;
        this.operacion = op;
    }

    @Override
    public Object ejecutar(Object tabla) {
        Object resIzq = izquierda.ejecutar(tabla);
        Object resDer = (derecha != null) ? derecha.ejecutar(tabla) : null;

        // Nota: Aquí asumimos que comparamos números (Doubles) para simplificar.
        // En un futuro deberías validar si son Strings o Booleanos antes de castear.
        
        switch (operacion) {
            // --- RELACIONALES ---
            case MAYOR:
                return Double.parseDouble(resIzq.toString()) > Double.parseDouble(resDer.toString());
            case MENOR:
                return Double.parseDouble(resIzq.toString()) < Double.parseDouble(resDer.toString());
            case MAYORIGUAL:
                return Double.parseDouble(resIzq.toString()) >= Double.parseDouble(resDer.toString());
            case MENORIGUAL:
                return Double.parseDouble(resIzq.toString()) <= Double.parseDouble(resDer.toString());
            case IGUALIGUAL:
                // Para igualdad usamos .equals porque sirve para Strings y Números
                return resIzq.toString().equals(resDer.toString());
            case DIFERENTE:
                return !resIzq.toString().equals(resDer.toString());

            // --- LÓGICAS ---
            case AND:
                return Boolean.parseBoolean(resIzq.toString()) && Boolean.parseBoolean(resDer.toString());
            case OR:
                return Boolean.parseBoolean(resIzq.toString()) || Boolean.parseBoolean(resDer.toString());
            case NOT:
                return !Boolean.parseBoolean(resIzq.toString());
            case XOR:
                return Boolean.parseBoolean(resIzq.toString()) ^ Boolean.parseBoolean(resDer.toString());

            default:
                return null;
        }
    }
}