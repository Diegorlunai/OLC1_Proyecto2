package arbol;

public class Operacion {
    
    // El Enum debe estar DENTRO de la clase
    public enum Tipo_Operacion {
        SUMA, RESTA, MULTIPLICACION, DIVISION, POTENCIA, MODULO, NEGACION,
        IGUALIGUAL, DIFERENTE, MENOR, MENORIGUAL, MAYOR, MAYORIGUAL,
        AND, OR, NOT, XOR
    }
    
    private Tipo_Operacion tipo;
    
    // Constructor vacío o con tipo, según necesites. 
    // El Parser usa 'new Operacion(Tipo)' en algunas versiones, o lo usa estático.
    // Para cumplir con 'new Operacion(Tipo)', agregamos esto:
    public Operacion(Tipo_Operacion tipo) {
        this.tipo = tipo;
    }
    
    public Tipo_Operacion getTipo() {
        return this.tipo;
    }
}