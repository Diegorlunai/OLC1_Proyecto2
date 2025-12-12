package arbol;

public enum Operacion {
    // Aritméticas
    SUMA,
    RESTA,
    MULTIPLICACION,
    DIVISION,
    POTENCIA,
    MODULO,
    NEGACION, // Menos unario
    
    // Relacionales
    MAYOR,
    MENOR,
    MAYORIGUAL,
    MENORIGUAL,
    IGUALIGUAL, // Ojo: verifica que se llame así en Logica.java
    DIFERENTE,
    
    // Lógicas
    AND,
    OR,
    NOT,
    XOR
}