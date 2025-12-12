package analizadores;
import java_cup.runtime.Symbol;
import excepciones.Errores; // IMPORTANTE para guardar errores

%%

%class Scanner
%public
%line
%column
%cup
%ignorecase

%{
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }
    private Symbol symbol(int type) {
        return new Symbol(type, yyline+1, yycolumn+1);
    }
%}

// Expresiones Regulares
ENTERO  = [0-9]+
DECIMAL = [0-9]+\.[0-9]+
ID      = [a-zA-Z][a-zA-Z0-9_]*
CADENA  = \"([^\\\"]|\\.)*\"
CHAR    = \'([^\\\']|\\.)\'
ESPACIO = [ \t\r\n\f]+
COMENTARIO_LINEA = "//".*
COMENTARIO_BLOQUE = "/*" [^]*? "*/"

%%

<YYINITIAL> {
    // Palabras Reservadas
    "var"       { return symbol(sym.VAR); }
    "int"       { return symbol(sym.INT); }
    "double"    { return symbol(sym.DOUBLE); }
    "bool"      { return symbol(sym.BOOL); }
    "char"      { return symbol(sym.CHAR); }
    "string"    { return symbol(sym.STRING); }
    "if"        { return symbol(sym.IF); }
    "else"      { return symbol(sym.ELSE); }
    "switch"    { return symbol(sym.SWITCH); }
    "case"      { return symbol(sym.CASE); }
    "default"   { return symbol(sym.DEFAULT); }
    "break"     { return symbol(sym.BREAK); }
    "while"     { return symbol(sym.WHILE); }
    "for"       { return symbol(sym.FOR); }
    "do"        { return symbol(sym.DO); }
    "print"     { return symbol(sym.PRINT); }
    "true"      { return symbol(sym.TRUE); }
    "false"     { return symbol(sym.FALSE); }
    "new"       { return symbol(sym.NEW); }
    "list"      { return symbol(sym.LIST); }
    "append"    { return symbol(sym.APPEND); }
    "return"    { return symbol(sym.RETURN); }
    "continue"  { return symbol(sym.CONTINUE); }

    // Operadores
    "++"        { return symbol(sym.MASMAS); }
    "--"        { return symbol(sym.MENOSMENOS); }
    "+"         { return symbol(sym.MAS); }
    "-"         { return symbol(sym.MENOS); }
    "**"        { return symbol(sym.POTENCIA); }
    "*"         { return symbol(sym.POR); }
    "/"         { return symbol(sym.DIV); }
    "%"         { return symbol(sym.MOD); }
    "("         { return symbol(sym.PAR_A); }
    ")"         { return symbol(sym.PAR_C); }
    "{"         { return symbol(sym.LLAVE_A); }
    "}"         { return symbol(sym.LLAVE_C); }
    "["         { return symbol(sym.COR_IZQ); }
    "]"         { return symbol(sym.COR_DER); }
    ";"         { return symbol(sym.PTCOMA); }
    ":"         { return symbol(sym.DOSPTOS); }
    "="         { return symbol(sym.IGUAL); }
    ","         { return symbol(sym.COMA); }
    "."         { return symbol(sym.PUNTO); }
    
    // Relacionales y Lógicos
    "=="        { return symbol(sym.IGUALIGUAL); }
    "!="        { return symbol(sym.DIFERENTE); }
    "<="        { return symbol(sym.MENORIGUAL); }
    ">="        { return symbol(sym.MAYORIGUAL); }
    "<"         { return symbol(sym.MENOR); }
    ">"         { return symbol(sym.MAYOR); }
    "||"        { return symbol(sym.OR); }
    "&&"        { return symbol(sym.AND); }
    "!"         { return symbol(sym.NOT); }
    "^"         { return symbol(sym.XOR); }

    // Valores
    {ENTERO}    { return symbol(sym.ENTERO, yytext()); }
    {DECIMAL}   { return symbol(sym.DECIMAL, yytext()); }
    {ID}        { return symbol(sym.ID, yytext()); }
    {CADENA}    { String val = yytext(); return symbol(sym.LIT_STRING, val.substring(1, val.length()-1)); }
    {CHAR}      { String val = yytext(); return symbol(sym.LIT_CHAR, val.substring(1, val.length()-1)); }

    {ESPACIO}           { /* Ignorar */ }
    {COMENTARIO_LINEA}  { /* Ignorar */ }
    {COMENTARIO_BLOQUE} { /* Ignorar */ }
}

// REGLA DE ERROR LÉXICO
[^] { 
    // Guardamos el error en la lista global
    Errores.agregar("Lexico", "Caracter no reconocido: " + yytext(), yyline+1, yycolumn+1);
    System.err.println("Error léxico: " + yytext() + " en línea " + (yyline+1));
}