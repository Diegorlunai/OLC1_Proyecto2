package arbol;

// Esta clase es el "padre" de todas tus instrucciones.
// Es abstracta porque no sirve para crear objetos por sí misma, 
// sino para que otras hereden de ella.
public abstract class Instruccion {
    
    // Guardamos la fila y columna para reportar errores si fallan
    public int linea;
    public int columna;

    public Instruccion(int linea, int columna) {
        this.linea = linea;
        this.columna = columna;
    }

    // Este método es obligatorio para quien herede de aquí.
    // "ejecutar" significa: realizar la acción (imprimir, sumar, declarar, etc.)
    public abstract Object ejecutar(Object tablaSimbolos); 
}