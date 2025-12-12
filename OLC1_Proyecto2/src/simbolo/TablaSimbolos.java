package simbolo;

import java.util.HashMap;

public class TablaSimbolos {
    
    // Mapa ahora guarda objetos Simbolo completos
    private HashMap<String, Simbolo> tabla;

    public TablaSimbolos() {
        this.tabla = new HashMap<>();
    }
    
    // Método para obtener el mapa completo (USADO POR EL REPORTE)
    public HashMap<String, Simbolo> obtenerTodos() {
        return this.tabla;
    }
    
    // Método para guardar (MODIFICADO para inferir el tipo y guardar el objeto Simbolo)
    public void guardar(String id, Object valor) {
        String tipo;
        
        // Inferencia simplificada del tipo
        if (valor instanceof Double || valor instanceof Integer) {
            tipo = "number"; // Usamos 'number' o puedes detallar 'int' o 'double' si quieres
        } else if (valor instanceof Boolean) {
            tipo = "bool";
        } else if (valor instanceof String) {
            tipo = "string";
        } else if (valor instanceof Character) {
            tipo = "char";
        } else {
            tipo = "unknown";
        }
        
        // Creamos o actualizamos el Simbolo
        // Nota: Asumimos entorno "Global" por ahora, ya que no implementamos funciones/ámbitos
        Simbolo nuevoSimbolo = new Simbolo(id, valor, tipo, "Global"); 
        this.tabla.put(id.toLowerCase(), nuevoSimbolo);
    }

    public Object obtener(String id) {
        Simbolo s = this.tabla.get(id.toLowerCase());
        if (s != null) {
            return s.getValor();
        }
        return null;
    }
    
    // --- CLASE AUXILIAR SIMBOLO ---
    // Esta clase es la que contiene toda la información que el reporte mostrará
    public static class Simbolo {
        private String id;
        private Object valor;
        private String tipo;
        private String entorno;

        public Simbolo(String id, Object valor, String tipo, String entorno) {
            this.id = id;
            this.valor = valor;
            this.tipo = tipo;
            this.entorno = entorno;
        }

        public String getId() {
            return id;
        }

        public Object getValor() {
            return valor;
        }

        public String getTipo() {
            return tipo;
        }
        
        public String getEntorno() {
            return entorno;
        }
    }
}