package arbol;

public class Casteo extends Instruccion {

    private String tipoDestino; 
    private Instruccion expresion;

    public Casteo(String tipoDestino, Instruccion expresion, int linea, int col) {
        super(linea, col);
        this.tipoDestino = tipoDestino;
        this.expresion = expresion;
    }

    @Override
    public Object ejecutar(Object tabla) {
        Object valor = expresion.ejecutar(tabla);
        if (valor == null) return null;

        try {
            switch (tipoDestino.toLowerCase()) {
                case "int":
                    if (valor instanceof Character) return (int) ((Character) valor);
                    if (valor instanceof String && valor.toString().length() == 1) {
                        // Si es "A", devolvemos 65 (ASCII)
                        return (int) valor.toString().charAt(0);
                    }
                    if (valor instanceof Double) return ((Double) valor).intValue();
                    return Integer.parseInt(valor.toString());

                case "double":
                    if (valor instanceof Character) return (double) ((Character) valor);
                    if (valor instanceof String && valor.toString().length() == 1) {
                        return (double) valor.toString().charAt(0); // ASCII a Double
                    }
                    if (valor instanceof Integer) return ((Integer) valor).doubleValue();
                    return Double.parseDouble(valor.toString());

                case "string":
                    return valor.toString(); 

                case "char":
                    // (char) 65 -> 'A'
                    if (valor instanceof Number) {
                        return (char) ((Number) valor).intValue(); 
                    }
                    return valor.toString().charAt(0);
                
                default:
                    System.err.println("❌ Error: Tipo desconocido " + tipoDestino);
                    return null;
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error en Casteo: " + e.getMessage());
            return null;
        }
    }
}