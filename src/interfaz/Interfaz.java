package interfaz;

import analizadores.Parser;
import analizadores.Scanner;
import arbol.Instruccion;
import java.io.StringReader;
import java.util.LinkedList;
import javax.swing.*;
import java.awt.*;
import main.Salida;
import simbolo.TablaSimbolos;
import excepciones.Errores; 

public class Interfaz extends JFrame {

    private JTextArea areaCodigo;
    private JTextArea areaConsola;
    private JButton btnCompilar;
    private JButton btnReporte; 
    private JButton btnReporteTabla; // Nuevo botón
    
    // Para guardar la memoria después de la ejecución
    private TablaSimbolos entornoFinal; 

    public Interfaz() {
        super("Mi Compilador - Fase 2");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

        // --- 1. PANEL SUPERIOR (BOTONES) ---
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Más espacio entre botones
        
        // Botón Compilar
        btnCompilar = new JButton("▶ Compilar");
        btnCompilar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCompilar.setBackground(new Color(50, 205, 50)); 
        btnCompilar.setForeground(Color.WHITE);
        btnCompilar.setPreferredSize(new Dimension(150, 35));
        btnCompilar.addActionListener(e -> compilar());
        
        // Botón Reporte de Errores
        btnReporte = new JButton("📄 Ver Errores");
        btnReporte.setFont(new Font("Arial", Font.BOLD, 14));
        btnReporte.setBackground(new Color(255, 69, 0)); 
        btnReporte.setForeground(Color.WHITE);
        btnReporte.setPreferredSize(new Dimension(150, 35));
        btnReporte.addActionListener(e -> generarReporteErrores());
        
        // Botón Reporte Tabla de Símbolos
        btnReporteTabla = new JButton("💾 Ver Tabla Símbolos");
        btnReporteTabla.setFont(new Font("Arial", Font.BOLD, 14));
        btnReporteTabla.setBackground(new Color(60, 179, 113)); 
        btnReporteTabla.setForeground(Color.WHITE);
        btnReporteTabla.setPreferredSize(new Dimension(200, 35));
        btnReporteTabla.addActionListener(e -> {
            if(entornoFinal != null) {
                generarReporteTabla(entornoFinal);
            } else {
                JOptionPane.showMessageDialog(this, "Debe compilar el código primero para generar la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        panelBotones.add(btnCompilar);
        panelBotones.add(btnReporte);
        panelBotones.add(btnReporteTabla);
        add(panelBotones, BorderLayout.NORTH);

        // --- 2. PANEL CENTRAL (CÓDIGO Y CONSOLA) ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        // Área de Código (Arriba)
        areaCodigo = new JTextArea();
        areaCodigo.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scrollCodigo = new JScrollPane(areaCodigo);
        scrollCodigo.setBorder(BorderFactory.createTitledBorder("Editor de Código"));
        
        // Área de Consola (Abajo)
        areaConsola = new JTextArea();
        areaConsola.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaConsola.setBackground(Color.BLACK);
        areaConsola.setForeground(Color.GREEN);
        areaConsola.setEditable(false);
        JScrollPane scrollConsola = new JScrollPane(areaConsola);
        scrollConsola.setBorder(BorderFactory.createTitledBorder("Consola de Salida"));

        splitPane.setTopComponent(scrollCodigo);
        splitPane.setBottomComponent(scrollConsola);
        splitPane.setDividerLocation(450); // Mayor tamaño para el código
        
        add(splitPane, BorderLayout.CENTER);
    }

    private void compilar() {
        String codigo = areaCodigo.getText();
        
        // 1. Limpiar consolas y listas antes de compilar
        Salida.limpiar(); 
        Errores.limpiar(); 
        areaConsola.setText("");

        try {
            // 2. Ejecutar análisis
            TablaSimbolos entornoGlobal = new TablaSimbolos();
            Scanner scanner = new Scanner(new StringReader(codigo));
            Parser parser = new Parser(scanner);
            
            parser.parse();
            
            // 3. Ejecutar AST solo si no hubo errores sintácticos/léxicos FATALES
            if (Errores.listaErrores.isEmpty()) { 
                LinkedList<Instruccion> ast = parser.AST;
                if (ast != null) {
                    for (Instruccion ins : ast) {
                        if (ins != null) ins.ejecutar(entornoGlobal);
                    }
                }
            } else {
                 areaConsola.append("❌ NO SE EJECUTÓ: Se encontraron errores léxicos o sintácticos.\n");
            }
            
            // 4. Guardar el entorno final (CRÍTICO para el reporte de símbolos)
            this.entornoFinal = entornoGlobal; 
            
            // 5. Mostrar salida y errores no fatales
            for (String linea : Salida.listaSalida) {
                areaConsola.append(linea + "\n");
            }
            areaConsola.append("\n--- Ejecución Finalizada ---");
            
            if (!Errores.listaErrores.isEmpty()) {
                areaConsola.append("\n\n⚠️ Se encontraron " + Errores.listaErrores.size() + " errores. Ver reporte HTML.");
            }


        } catch (Exception e) {
            areaConsola.append("❌ Error Grave: El análisis falló completamente. " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Método para generar el HTML de errores
    private void generarReporteErrores() {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("ReporteErrores.html");
            writer.write("<html><head><style>table{border-collapse: collapse; width: 80%; margin: 20px auto;} th, td {text-align: left; padding: 10px;} tr:nth-child(even){background-color: #f2f2f2} th {background-color: #d32f2f; color: white;}</style></head><body>");
            writer.write("<h1>Reporte de Errores Léxicos y Sintácticos</h1>");
            
            if (Errores.listaErrores.isEmpty()) {
                writer.write("<p>✅ No se encontraron errores léxicos ni sintácticos.</p>");
            } else {
                writer.write("<table border='1'><tr><th>Tipo</th><th>Descripcion</th><th>Linea</th><th>Columna</th></tr>");
                
                for (excepciones.Errores err : excepciones.Errores.listaErrores) {
                    writer.write("<tr>");
                    writer.write("<td>" + err.tipo + "</td>");
                    writer.write("<td>" + err.descripcion + "</td>");
                    writer.write("<td>" + err.linea + "</td>");
                    writer.write("<td>" + err.columna + "</td>");
                    writer.write("</tr>");
                }
                writer.write("</table>");
            }
            
            writer.write("</body></html>");
            writer.close();
            
            JOptionPane.showMessageDialog(this, "¡Reporte generado con éxito! (ReporteErrores.html)");
            java.awt.Desktop.getDesktop().open(new java.io.File("ReporteErrores.html"));
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar reporte: " + e.getMessage());
        }
    }
    
    // Método para generar el HTML de la Tabla de Símbolos
    private void generarReporteTabla(TablaSimbolos ts) {
        try {
            // Obtenemos todos los símbolos
            // Nota: Aquí se asume que TablaSimbolos.java tiene la clase interna Simbolo y el método obtenerTodos()
            java.util.HashMap<String, simbolo.TablaSimbolos.Simbolo> simbolos = ts.obtenerTodos(); 
            
            java.io.FileWriter writer = new java.io.FileWriter("ReporteTablaSimbolos.html");
            writer.write("<html><head><style>table{border-collapse: collapse; width: 80%; margin: 20px auto;} th, td {text-align: left; padding: 10px;} tr:nth-child(even){background-color: #f2f2f2} th {background-color: #4CAF50; color: white;}</style></head><body>");
            writer.write("<h1>Reporte de Tabla de Símbolos Final</h1>");
            
            if (simbolos.isEmpty()) {
                writer.write("<p>La Tabla de Símbolos está vacía. Compile código con declaraciones de variables para llenar la tabla.</p>");
            } else {
                writer.write("<table border='1'><tr><th>ID</th><th>Valor</th><th>Tipo</th><th>Entorno</th></tr>");
                
                for (java.util.Map.Entry<String, simbolo.TablaSimbolos.Simbolo> entry : simbolos.entrySet()) {
                    simbolo.TablaSimbolos.Simbolo s = entry.getValue();
                    writer.write("<tr>");
                    writer.write("<td>" + s.getId() + "</td>");
                    writer.write("<td>" + s.getValor() + "</td>");
                    writer.write("<td>" + s.getTipo() + "</td>");
                    writer.write("<td>" + s.getEntorno() + "</td>");
                    writer.write("</tr>");
                }
                writer.write("</table>");
            }
            
            writer.write("</body></html>");
            writer.close();
            
            JOptionPane.showMessageDialog(this, "¡Reporte de Tabla de Símbolos generado con éxito! (ReporteTablaSimbolos.html)");
            java.awt.Desktop.getDesktop().open(new java.io.File("ReporteTablaSimbolos.html"));
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar reporte de tabla: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interfaz().setVisible(true);
        });
    }
}