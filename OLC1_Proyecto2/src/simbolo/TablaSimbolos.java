package simbolo;

import java.util.HashMap;

public class TablaSimbolos {

    public TablaSimbolos anterior; 
    private HashMap<String, Simbolo> tabla; 

    // Constructor Global
    public TablaSimbolos() {
        this.tabla = new HashMap<>();
        this.anterior = null;
    }

    // Constructor Local
    public TablaSimbolos(TablaSimbolos anterior) {
        this.tabla = new HashMap<>();
        this.anterior = anterior;
    }

    public void guardar(String id, Object valor) {
        String tipo = "unknown";
        if (valor != null) {
            if (valor instanceof Integer) tipo = "int";
            else if (valor instanceof Double) tipo = "double";
            else if (valor instanceof Boolean) tipo = "bool";
            else if (valor instanceof String) tipo = "string";
            else if (valor.getClass().isArray()) tipo = "array";
        }
        
        Simbolo nuevoSimbolo = new Simbolo(id, valor, tipo, (anterior == null ? "Global" : "Local")); 
        this.tabla.put(id.toLowerCase(), nuevoSimbolo);
    }

    public void asignar(String id, Object valor) {
        id = id.toLowerCase();
        Simbolo s = this.tabla.get(id);
        if (s != null) {
            s.setValor(valor);
            return;
        }
        if (this.anterior != null) {
            this.anterior.asignar(id, valor);
            return;
        }
        System.err.println("❌ Error: Variable '" + id + "' no encontrada.");
    }

    public Object obtener(String id) {
        // 1. Buscar en actual
        Simbolo s = this.tabla.get(id.toLowerCase());
        if (s != null) return s.getValor();
        
        // 2. Buscar en padre (CORRECCIÓN CRÍTICA: USAR 'anterior', NUNCA 'this')
        if (this.anterior != null) {
            return this.anterior.obtener(id); 
        }
        return null;
    }
    
    // Método auxiliar para obtener el entorno raíz (Global)
    // Esto es vital para evitar StackOverflow en recursividad profunda
    public TablaSimbolos getEntornoGlobal() {
        TablaSimbolos actual = this;
        while (actual.anterior != null) {
            actual = actual.anterior;
        }
        return actual;
    }
    
    public Object obtenerEnActual(String id) {
        Simbolo s = this.tabla.get(id.toLowerCase());
        if (s != null) return s.getValor();
        return null;
    }

    public HashMap<String, Simbolo> obtenerTodos() { return this.tabla; }

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
        public void setValor(Object valor) { this.valor = valor; }
        public String getId() { return id; }
        public Object getValor() { return valor; }
        public String getTipo() { return tipo; }
        public String getEntorno() { return entorno; }
    }
}