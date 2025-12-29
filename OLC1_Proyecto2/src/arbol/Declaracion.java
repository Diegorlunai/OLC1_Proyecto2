package arbol;

import simbolo.TablaSimbolos;

public class Declaracion extends Instruccion {

    private String id;
    private String tipo;
    private Instruccion valor; // Puede ser null si es solo "var x: int;"

    public Declaracion(String id, String tipo, Instruccion valor, int linea, int col) {
        super(linea, col);
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
    }

    @Override
    public Object ejecutar(Object tabla) {
        TablaSimbolos ts = (TablaSimbolos) tabla;

        // 1. Verificar si la variable ya existe en el entorno ACTUAL
        // (Para evitar declarar dos veces la misma variable en la misma función)
        if (ts.obtenerEnActual(id) != null) {
            System.err.println("❌ Error Semántico: La variable '" + id + "' ya existe en este entorno. Línea: " + linea);
            return null;
        }

        // 2. Calcular el valor
        Object valorInterpretado = null;
        
        if (valor != null) {
            // Caso: var x: int = 10;
            valorInterpretado = valor.ejecutar(tabla);
            
            // Si hubo error al calcular el valor, detenemos
            if (valorInterpretado == null) {
                return null;
            }
            
            // (Opcional) Aquí podrías validar que el tipo del valor coincida con la declaración
            // if (tipo.equals("int") && !(valorInterpretado instanceof Integer)) ...
            
        } else {
            // Caso: var x: int; (Sin valor inicial)
            // Asignamos valor por defecto según el tipo
            switch (tipo.toLowerCase()) {
                case "int": valorInterpretado = 0; break;
                case "double": valorInterpretado = 0.0; break;
                case "bool": valorInterpretado = false; break;
                case "string": valorInterpretado = ""; break;
                case "char": valorInterpretado = '\u0000'; break;
                default: valorInterpretado = null;
            }
        }

        // 3. Guardar la variable en la Tabla de Símbolos
        ts.guardar(id, valorInterpretado);
        
        return null;
    }

    // --- MÉTODOS GETTERS (NECESARIOS PARA LlamadaFuncion.java) ---
    public String getId() {
        return this.id;
    }

    public String getTipo() {
        return this.tipo;
    }
}