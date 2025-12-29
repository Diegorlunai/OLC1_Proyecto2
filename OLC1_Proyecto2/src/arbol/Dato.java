package arbol;

public class Dato extends Instruccion {
    
    private Object valor;
    private String tipo;

    public Dato(Object valor, String tipo, int linea, int col) {
        super(linea, col);
        this.valor = valor;
        this.tipo = tipo;
    }

    @Override
    public Object ejecutar(Object tabla) {
        // Convertir el valor según su tipo
        if (tipo == null) return valor;
        
        switch (tipo.toLowerCase()) {
            case "int":
                try {
                    if (valor instanceof Integer) return valor;
                    return Integer.parseInt(valor.toString());
                } catch (Exception e) {
                    return valor;
                }
            case "double":
                try {
                    if (valor instanceof Double) return valor;
                    return Double.parseDouble(valor.toString());
                } catch (Exception e) {
                    return valor;
                }
            case "bool":
                if (valor instanceof Boolean) return valor;
                return Boolean.parseBoolean(valor.toString());
            case "char":
                if (valor instanceof Character) return valor;
                if (valor.toString().length() > 0) {
                    return valor.toString().charAt(0);
                }
                return '\0';
            default:
                return valor; // string y otros
        }
    }
}