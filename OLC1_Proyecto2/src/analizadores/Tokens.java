package analizadores;

public enum Tokens {
    // --- TIPOS DE DATOS ---
    INT,
    DOUBLE,
    BOOL,
    CHAR,
    STRING,
    VOID,       // Nuevo Fase 2 (para funciones)
    
    // --- DECLARACIÓN ---
    VAR,
    
    // --- SENTENCIAS DE CONTROL ---
    IF,
    ELSE,
    SWITCH,
    CASE,
    DEFAULT,
    
    // --- CICLOS ---
    WHILE,
    DO,
    FOR,
    
    // --- TRANSFERENCIA ---
    BREAK,
    CONTINUE,
    RETURN,     // Nuevo Fase 2
    
    // --- VALORES BOOLEANOS ---
    TRUE,
    FALSE,
    
    // --- PALABRAS RESERVADAS ESPECIALES ---
    START_WITH, // Nuevo Fase 2 (Requisito de entrada)
    NEW,        // Nuevo Fase 2 (Instancias)
    
    // --- FUNCIONES NATIVAS ---
    PRINT,
    PRINTLN,    // Nuevo Fase 2 (Aparece en los archivos .ju)
    LENGTH,     // Nuevo Fase 2 (Tamaño de arreglos/cadenas)
    ROUND,      // Nuevo Fase 2 (Redondear)
    TOSTRING,   // Nuevo Fase 2 (Convertir a cadena)
    FIND,       // Nuevo Fase 2 (Buscar en listas)
    
    // --- MANEJO DE LISTAS DINÁMICAS ---
    LIST,       // Nuevo Fase 2
    APPEND,     // Nuevo Fase 2
    REMOVE,     // Nuevo Fase 2
    
    // --- OPERADORES ARITMÉTICOS ---
    SUMA,       // +
    RESTA,      // -
    MULT,       // *
    DIV,        // /
    POT,        // **
    MOD,        // %
    
    // --- OPERADORES RELACIONALES ---
    IGUAL,      // ==
    DIF,        // !=
    MENOR,      // <
    MENORIGUAL, // <=
    MAYOR,      // >
    MAYORIGUAL, // >=
    
    // --- OPERADORES LÓGICOS ---
    AND,        // &&
    OR,         // ||
    NOT,        // !
    XOR,        // ^
    
    // --- ASIGNACIÓN E INCREMENTO ---
    ASIGN,      // =
    INCREMENTO, // ++
    DECREMENTO, // --
    
    // --- SÍMBOLOS Y AGRUPACIÓN ---
    PAR_A,      // (
    PAR_C,      // )
    LLAVE_A,    // {
    LLAVE_C,    // }
    COR_IZQ,    // [  (Crucial para vectores)
    COR_DER,    // ]  (Crucial para vectores)
    PUNTO_COMA, // ;
    DOS_PUNTOS, // :
    COMA,       // ,
    PUNTO,      // .  (Para acceso a métodos .append, .length)
    
    // --- LITERALES (VALORES) ---
    ID,         // Identificadores (nombres de variables/funciones)
    ENTERO,     // Números enteros
    DECIMAL,    // Números decimales
    LIT_CHAR,   // Caracteres 'a'
    LIT_STRING, // Cadenas "hola"
    
    // --- OTROS ---
    ERROR
}