package arbol;

import java.util.LinkedList;
import simbolo.TablaSimbolos;

public class Funcion extends Instruccion {

    private String tipo;
    private String nombre;
    private LinkedList<Instruccion> parametros;
    private LinkedList<Instruccion> instrucciones;

    public Funcion(String tipo, String nombre, LinkedList<Instruccion> parametros, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(linea, col);
        this.tipo = tipo;
        this.nombre = nombre;
        this.parametros = parametros;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object ejecutar(Object tabla) {
        // En la declaración, lo único que hacemos es guardar la función en la tabla de símbolos
        // para poder usarla después. No ejecutamos las instrucciones aquí.
        
        TablaSimbolos ts = (TablaSimbolos) tabla;
        
        // Validar que no exista ya
        if (ts.obtener(nombre) != null) {
            System.err.println("❌ Error Semántico: La función '" + nombre + "' ya existe. Línea: " + linea);
            return null;
        }
        
        // Nos guardamos a nosotros mismos (this) como valor del símbolo
        // El tipo es "funcion" para diferenciarlo de variables normales
        ts.guardar(nombre, this);
        
        return null; 
    }

    // Getters para que LlamadaFuncion pueda acceder a la info
    public String getTipo() { return tipo; }
    public LinkedList<Instruccion> getParametros() { return parametros; }
    public LinkedList<Instruccion> getInstrucciones() { return instrucciones; }
    public String getNombre() { return nombre; }
}