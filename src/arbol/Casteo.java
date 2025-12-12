package arbol;

public class Casteo extends Instruccion {

    private String tipoDestino; // "int", "double", "string", "char"
    private Instruccion expresion;

    public Casteo(String tipoDestino, Instruccion expresion, int linea, int col) {
        super(linea, col);
        this.tipoDestino = tipoDestino;
        this.expresion = expresion;
    }

    @Override
    public Object ejecutar(Object tabla) {
        // 1. Obtenemos el valor original
        Object valor = expresion.ejecutar(tabla);
        
        if (valor == null) return null;

        try {
            switch (tipoDestino.toLowerCase()) {
                case "int":
                    if (valor instanceof Double) return ((Double) valor).intValue(); // 5.5 -> 5
                    if (valor instanceof Integer) return (int) valor;
                    if (valor instanceof Character) return (int) ((Character) valor);
                    return Integer.parseInt(valor.toString());

                case "double":
                    if (valor instanceof Double) return valor;
                    if (valor instanceof Integer) return ((Integer) valor).doubleValue(); // 5 -> 5.0
                    return Double.parseDouble(valor.toString());

                case "string":
                    return valor.toString(); 

                case "char":
                    // Convertir ASCII a Char: (char) 70 -> 'F'
                    if (valor instanceof Number) {
                        return (char) ((Number) valor).intValue(); 
                    }
                    return valor.toString().charAt(0);
                
                default:
                    System.err.println("❌ Error: Tipo de casteo no reconocido: " + tipoDestino);
                    return null;
            }
        } catch (Exception e) {
            System.err.println("❌ Error Semántico: No se puede castear el valor '" + valor + "' a " + tipoDestino);
            return null;
        }
    }
}