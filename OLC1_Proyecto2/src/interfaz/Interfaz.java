package interfaz;

import analizadores.Parser;
import analizadores.Scanner;
import analizadores.TokenLista;
import arbol.*;
import excepciones.Errores;
import java.io.StringReader;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import main.Salida;
import simbolo.TablaSimbolos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Interfaz extends JFrame {

    private JTextArea areaCodigo;
    private JTextArea areaConsola;
    
    private JButton btnCompilar;
    private JButton btnReporteErrores;
    private JButton btnReporteTabla;
    private JButton btnReporteTokens;
    private JButton btnAbrir;
    private JButton btnGuardar;
    private JButton btnGuardarComo;
    private File archivoActual; // Para recordar el archivo abierto
    
    private TablaSimbolos entornoFinal;
    
    
    // Paleta de colores profesional
    private static final Color PRIMARY_DARK = new Color(26, 35, 46);      // Azul oscuro profundo
    private static final Color SECONDARY_DARK = new Color(41, 50, 65);    // Azul grisáceo
    private static final Color ACCENT_GREEN = new Color(39, 174, 96);     // Verde profesional
    private static final Color ACCENT_BLUE = new Color(52, 152, 219);     // Azul brillante
    private static final Color ACCENT_ORANGE = new Color(230, 126, 34);   // Naranja suave
    private static final Color ACCENT_RED = new Color(231, 76, 60);       // Rojo suave
    private static final Color BG_LIGHT = new Color(236, 240, 241);       // Gris muy claro
    private static final Color TEXT_DARK = new Color(44, 62, 80);         // Texto oscuro
    private static final Color CONSOLE_BG = new Color(40, 44, 52);        // Fondo consola tipo terminal
    private static final Color CONSOLE_TEXT = new Color(171, 178, 191);   // Texto consola claro

    public Interfaz() {
        super("Compilador OLC1 - Proyecto Final 2024");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        initComponents();
    }

    private void initComponents() {
        // Panel principal con fondo claro
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(BG_LIGHT);
        setContentPane(panelPrincipal);

        // ===== HEADER CON TÍTULO =====
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(PRIMARY_DARK);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lblTitulo = new JLabel("COMPILADOR OLC1");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        
        JLabel lblSubtitulo = new JLabel("Análisis Léxico, Sintáctico y Semántico");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(189, 195, 199));
        
        JPanel panelTitulos = new JPanel(new GridLayout(2, 1, 0, 5));
        panelTitulos.setOpaque(false);
        panelTitulos.add(lblTitulo);
        panelTitulos.add(lblSubtitulo);
        
        panelHeader.add(panelTitulos, BorderLayout.WEST);
        add(panelHeader, BorderLayout.NORTH);

        // ===== TOOLBAR CON BOTONES =====
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 12));
        panelBotones.setBackground(SECONDARY_DARK);
        panelBotones.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(52, 73, 94)));

        btnCompilar = crearBotonModerno("▶ COMPILAR", ACCENT_GREEN, "Ejecutar código");
        btnReporteTokens = crearBotonModerno("📋 TOKENS", ACCENT_BLUE, "Ver tokens");
        btnReporteTabla = crearBotonModerno("💾 SÍMBOLOS", ACCENT_ORANGE, "Tabla de símbolos");
        btnReporteErrores = crearBotonModerno("⚠ ERRORES", ACCENT_RED, "Ver errores");
        btnAbrir = crearBotonModerno("📂 ABRIR", new Color(52, 152, 219), "Abrir archivo");
        btnGuardar = crearBotonModerno("💾 GUARDAR", new Color(46, 204, 113), "Guardar archivo");
        btnGuardarComo = crearBotonModerno("💾 GUARDAR COMO", new Color(26, 188, 156), "Guardar como...");
        
        btnCompilar.addActionListener(e -> compilar());
        btnReporteTokens.addActionListener(e -> generarReporteTokens());
        btnReporteTabla.addActionListener(e -> generarReporteTabla());
        btnReporteErrores.addActionListener(e -> generarReporteErrores());
        btnAbrir.addActionListener(e -> abrirArchivo());
        btnGuardar.addActionListener(e -> guardarArchivo());
        btnGuardarComo.addActionListener(e -> guardarArchivoComo());

        panelBotones.add(btnCompilar);
        panelBotones.add(Box.createHorizontalStrut(25));
        panelBotones.add(btnReporteTokens);
        panelBotones.add(btnReporteTabla);
        panelBotones.add(btnReporteErrores);
        panelBotones.add(btnAbrir);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnGuardarComo);
        panelBotones.add(Box.createHorizontalStrut(25)); // Separador
        
        add(panelBotones, BorderLayout.CENTER);

        // ===== ÁREA DE TRABAJO (SPLIT PANE) =====
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerSize(6);
        splitPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        splitPane.setBackground(BG_LIGHT);
        splitPane.setDividerLocation(420);

        // EDITOR DE CÓDIGO (Superior)
        areaCodigo = new JTextArea();
        areaCodigo.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        areaCodigo.setForeground(TEXT_DARK);
        areaCodigo.setBackground(Color.WHITE);
        areaCodigo.setCaretColor(new Color(52, 152, 219));
        areaCodigo.setSelectionColor(new Color(52, 152, 219, 80));
        areaCodigo.setLineWrap(false);
        areaCodigo.setTabSize(4);
        areaCodigo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        areaCodigo.setText("// Escribe tu código aquí...\nSTART_WITH main();\n\nvoid main() {\n    print(\"Hola Mundo\");\n}");
        
        JScrollPane scrollCodigo = new JScrollPane(areaCodigo);
        scrollCodigo.setBorder(crearBordeTitulado(" 📝 Editor de Código ", ACCENT_BLUE));
        scrollCodigo.getViewport().setBackground(Color.WHITE);

        // CONSOLA DE SALIDA (Inferior)
        areaConsola = new JTextArea();
        areaConsola.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
        areaConsola.setBackground(CONSOLE_BG);
        areaConsola.setForeground(CONSOLE_TEXT);
        areaConsola.setCaretColor(CONSOLE_TEXT);
        areaConsola.setEditable(false);
        areaConsola.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        areaConsola.setText("=== Consola lista para mostrar resultados ===");

        JScrollPane scrollConsola = new JScrollPane(areaConsola);
        scrollConsola.setBorder(crearBordeTitulado(" 🖥 Consola de Salida ", ACCENT_GREEN));
        scrollConsola.getViewport().setBackground(CONSOLE_BG);

        splitPane.setTopComponent(scrollCodigo);
        splitPane.setBottomComponent(scrollConsola);
        splitPane.setResizeWeight(0.6);
        
        // Panel contenedor para el split pane
        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(BG_LIGHT);
        contenedorCentral.add(panelBotones, BorderLayout.NORTH);
        contenedorCentral.add(splitPane, BorderLayout.CENTER);
        
        add(contenedorCentral, BorderLayout.CENTER);

        // ===== BARRA DE ESTADO =====
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelEstado.setBackground(SECONDARY_DARK);
        panelEstado.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(52, 73, 94)));
        
        JLabel lblEstado = new JLabel("✓ Listo para compilar");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblEstado.setForeground(new Color(189, 195, 199));
        panelEstado.add(lblEstado);
        
        add(panelEstado, BorderLayout.SOUTH);
    }

    private JButton crearBotonModerno(String texto, Color colorBase, String tooltip) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(colorBase.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(colorBase.brighter());
                } else {
                    g2.setColor(colorBase);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(140, 38));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setToolTipText(tooltip);
        
        return btn;
    }

    private Border crearBordeTitulado(String titulo, Color color) {
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(color, 2),
            titulo,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            color
        );
        return BorderFactory.createCompoundBorder(
            border,
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
    }

    // =======================================================
    //        LÓGICA DEL COMPILADOR (3 FASES)
    // =======================================================
    private void compilar() {
        String codigo = areaCodigo.getText();
        Salida.limpiar();
        if(Errores.listaErrores != null) Errores.listaErrores.clear();
        if(TokenLista.lista != null) TokenLista.lista.clear();
        
        areaConsola.setText("");
        areaConsola.setForeground(CONSOLE_TEXT);

        try {
            TablaSimbolos entornoGlobal = new TablaSimbolos();
            Scanner scanner = new Scanner(new StringReader(codigo));
            Parser parser = new Parser(scanner);
            parser.parse();
            
            LinkedList<Instruccion> ast = parser.AST;

            if (ast != null && (Errores.listaErrores == null || Errores.listaErrores.isEmpty())) {
                areaConsola.append("╔══════════════════════════════════════════╗\n");
                areaConsola.append("║     INICIANDO EJECUCIÓN DEL PROGRAMA     ║\n");
                areaConsola.append("╚══════════════════════════════════════════╝\n\n");

                // FASE 1: Declaración de funciones
                for (Instruccion ins : ast) {
                    if (ins instanceof Funcion) ins.ejecutar(entornoGlobal);
                }
                
                // FASE 2: Variables globales
                for (Instruccion ins : ast) {
                    if (ins instanceof Declaracion || ins instanceof DeclaracionVector || 
                        ins instanceof DeclaracionLista) {
                        ins.ejecutar(entornoGlobal);
                    }
                }
                             
                // FASE 3: Punto de entrada (StartWith o ejecución directa)
                boolean startFound = false;
                for (Instruccion ins : ast) {
                    if (ins instanceof StartWith) {
                        ins.ejecutar(entornoGlobal);
                        startFound = true;
                        break;
                    }
                }

                // Si NO hay StartWith, ejecutar instrucciones globales ejecutables
                if (!startFound) {
                    for (Instruccion ins : ast) {
                        // Ejecutar solo instrucciones ejecutables (no declaraciones ni funciones)
                        if (!(ins instanceof Declaracion) && 
                            !(ins instanceof DeclaracionVector) && 
                            !(ins instanceof DeclaracionLista) && 
                            !(ins instanceof Funcion)) {
                            ins.ejecutar(entornoGlobal);
                        }
                    }
                }
                
                this.entornoFinal = entornoGlobal;

                if (!Salida.listaSalida.isEmpty()) {
                    areaConsola.append("\n--- SALIDA DEL PROGRAMA ---\n");
                    for (String linea : Salida.listaSalida) {
                        areaConsola.append(linea + "\n");
                    }
                }
                
                areaConsola.append("\n╔══════════════════════════════════════════╗\n");
                areaConsola.append("║      ✓ COMPILACIÓN EXITOSA              ║\n");
                areaConsola.append("╚══════════════════════════════════════════╝\n");
                areaConsola.setForeground(new Color(46, 204, 113)); // Verde éxito

            } else {
                areaConsola.setForeground(ACCENT_RED);
                areaConsola.append("╔══════════════════════════════════════════╗\n");
                areaConsola.append("║      ✗ ERROR DE COMPILACIÓN             ║\n");
                areaConsola.append("╚══════════════════════════════════════════╝\n\n");
                
                if (Errores.listaErrores != null && !Errores.listaErrores.isEmpty()) {
                    areaConsola.append("Se encontraron " + Errores.listaErrores.size() + " error(es):\n\n");
                    for (Errores.ErrorDato error : Errores.listaErrores) {
                        areaConsola.append(String.format("  • [Línea %d, Col %d] %s: %s\n",
                            error.getLinea(), error.getColumna(), 
                            error.getTipo(), error.getDescripcion()));
                    }
                    areaConsola.append("\nRevisa el código y vuelve a intentar.\n");
                }
            }

        } catch (Exception e) {
            areaConsola.setForeground(ACCENT_RED);
            areaConsola.append("╔══════════════════════════════════════════╗\n");
            areaConsola.append("║      ✗ ERROR CRÍTICO                    ║\n");
            areaConsola.append("╚══════════════════════════════════════════╝\n\n");
            areaConsola.append("Excepción: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    // ==========================================
    //           REPORTES HTML
    // ==========================================
    
    private void generarReporteTokens() {
        generarHTML("ReporteTokens.html", "Reporte de Tokens", () -> {
            StringBuilder sb = new StringBuilder();
            if (TokenLista.lista == null || TokenLista.lista.isEmpty()) {
                sb.append("<div class='alert'>⚠ No hay tokens registrados. Compila primero.</div>");
            } else {
                sb.append("<div class='stats'>Total de tokens: <strong>").append(TokenLista.lista.size()).append("</strong></div>");
                sb.append("<table><thead><tr><th>Lexema</th><th>Tipo</th><th>Línea</th><th>Columna</th></tr></thead><tbody>");
                for (TokenLista.TokenDato t : TokenLista.lista) {
                    sb.append("<tr>");
                    sb.append("<td><code>").append(escapeHtml(t.getLexema())).append("</code></td>");
                    sb.append("<td><span class='badge'>").append(t.getTipo()).append("</span></td>");
                    sb.append("<td>").append(t.getLinea()).append("</td>");
                    sb.append("<td>").append(t.getColumna()).append("</td>");
                    sb.append("</tr>");
                }
                sb.append("</tbody></table>");
            }
            return sb.toString();
        });
    }

    private void generarReporteErrores() {
        generarHTML("ReporteErrores.html", "Reporte de Errores", () -> {
            StringBuilder sb = new StringBuilder();
            if (Errores.listaErrores == null || Errores.listaErrores.isEmpty()) {
                sb.append("<div class='success'>✅ ¡Excelente! No se encontraron errores.</div>");
            } else {
                sb.append("<div class='alert-error'>Se encontraron <strong>").append(Errores.listaErrores.size()).append("</strong> error(es)</div>");
                sb.append("<table><thead><tr><th>Tipo</th><th>Descripción</th><th>Línea</th><th>Columna</th></tr></thead><tbody>");
                for (Errores.ErrorDato e : Errores.listaErrores) {
                    sb.append("<tr class='error-row'>");
                    sb.append("<td><span class='badge-error'>").append(e.getTipo()).append("</span></td>");
                    sb.append("<td>").append(escapeHtml(e.getDescripcion())).append("</td>");
                    sb.append("<td>").append(e.getLinea()).append("</td>");
                    sb.append("<td>").append(e.getColumna()).append("</td>");
                    sb.append("</tr>");
                }
                sb.append("</tbody></table>");
            }
            return sb.toString();
        });
    }

    private void generarReporteTabla() {
        if (entornoFinal == null) {
            JOptionPane.showMessageDialog(this, 
                "Primero debes compilar el código para generar la tabla de símbolos.",
                "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        generarHTML("ReporteTabla.html", "Tabla de Símbolos", () -> {
            StringBuilder sb = new StringBuilder();
            HashMap<String, TablaSimbolos.Simbolo> mapa = entornoFinal.obtenerTodos();
            if (mapa.isEmpty()) {
                sb.append("<div class='alert'>⚠ La tabla de símbolos está vacía.</div>");
            } else {
                sb.append("<div class='stats'>Total de símbolos: <strong>").append(mapa.size()).append("</strong></div>");
                sb.append("<table><thead><tr><th>Identificador</th><th>Valor</th><th>Tipo</th><th>Entorno</th></tr></thead><tbody>");
                for (TablaSimbolos.Simbolo s : mapa.values()) {
                    sb.append("<tr>");
                    sb.append("<td><code><strong>").append(escapeHtml(s.getId())).append("</strong></code></td>");
                    
                    String val = (s.getValor() != null) ? s.getValor().toString() : "null";
                    if(val.contains("arbol.Funcion")) val = "<em>[Función]</em>";
                    sb.append("<td>").append(escapeHtml(val)).append("</td>");
                    
                    sb.append("<td><span class='badge'>").append(s.getTipo()).append("</span></td>");
                    sb.append("<td>").append(escapeHtml(s.getEntorno())).append("</td>");
                    sb.append("</tr>");
                }
                sb.append("</tbody></table>");
            }
            return sb.toString();
        });
    }

    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }

    private void generarHTML(String nombre, String titulo, java.util.function.Supplier<String> contenido) {
        try {
            FileWriter fw = new FileWriter(nombre);
            PrintWriter pw = new PrintWriter(fw);
            
            pw.println("<!DOCTYPE html>");
            pw.println("<html lang='es'><head>");
            pw.println("<meta charset='UTF-8'>");
            pw.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            pw.println("<title>" + titulo + "</title>");
            pw.println("<style>");
            pw.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
            pw.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 30px; min-height: 100vh; }");
            pw.println(".container { max-width: 1200px; margin: 0 auto; background: white; border-radius: 12px; box-shadow: 0 10px 40px rgba(0,0,0,0.3); overflow: hidden; }");
            pw.println(".header { background: linear-gradient(135deg, #1a232e 0%, #29323f 100%); color: white; padding: 30px; text-align: center; }");
            pw.println(".header h1 { font-size: 2.5em; margin-bottom: 8px; }");
            pw.println(".header p { color: #bdc3c7; font-size: 0.95em; }");
            pw.println(".content { padding: 30px; }");
            pw.println("table { width: 100%; border-collapse: collapse; background: white; margin-top: 20px; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }");
            pw.println("thead { background: #34495e; color: white; }");
            pw.println("th { padding: 15px; text-align: left; font-weight: 600; text-transform: uppercase; font-size: 0.85em; letter-spacing: 0.5px; }");
            pw.println("td { padding: 12px 15px; border-bottom: 1px solid #ecf0f1; color: #2c3e50; }");
            pw.println("tbody tr:hover { background: #f8f9fa; }");
            pw.println("tbody tr:last-child td { border-bottom: none; }");
            pw.println("code { background: #ecf0f1; padding: 3px 6px; border-radius: 3px; font-family: 'Courier New', monospace; color: #e74c3c; }");
            pw.println(".badge { background: #3498db; color: white; padding: 4px 10px; border-radius: 12px; font-size: 0.85em; font-weight: 600; }");
            pw.println(".badge-error { background: #e74c3c; color: white; padding: 4px 10px; border-radius: 12px; font-size: 0.85em; font-weight: 600; }");
            pw.println(".alert { background: #fff3cd; border-left: 4px solid #ffc107; padding: 15px; border-radius: 4px; color: #856404; margin: 20px 0; }");
            pw.println(".alert-error { background: #f8d7da; border-left: 4px solid #dc3545; padding: 15px; border-radius: 4px; color: #721c24; margin: 20px 0; }");
            pw.println(".success { background: #d4edda; border-left: 4px solid #28a745; padding: 15px; border-radius: 4px; color: #155724; margin: 20px 0; font-size: 1.1em; }");
            pw.println(".stats { background: #e8f4f8; padding: 12px 20px; border-radius: 6px; margin-bottom: 15px; color: #2c3e50; font-size: 1.05em; }");
            pw.println(".error-row { background: #fff5f5; }");
            pw.println("</style>");
            pw.println("</head><body>");
            pw.println("<div class='container'>");
            pw.println("<div class='header'>");
            pw.println("<h1>" + titulo + "</h1>");
            pw.println("<p>Compilador OLC1 - Proyecto Final 2024</p>");
            pw.println("</div>");
            pw.println("<div class='content'>");
            pw.println(contenido.get());
            pw.println("</div>");
            pw.println("</div>");
            pw.println("</body></html>");
            pw.close();
            
            File archivo = new File(nombre);
            if (archivo.exists()) {
                Desktop.getDesktop().open(archivo);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al generar el reporte: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos JU (*.ju)", "ju"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            archivoActual = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(archivoActual));
                areaCodigo.setText("");
                String linea;
                while ((linea = reader.readLine()) != null) {
                    areaCodigo.append(linea + "\n");
                }
                reader.close();
                setTitle("Compilador OLC1 - " + archivoActual.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al abrir el archivo: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarArchivo() {
        if (archivoActual == null) {
            guardarArchivoComo();
        } else {
            try {
                FileWriter writer = new FileWriter(archivoActual);
                writer.write(areaCodigo.getText());
                writer.close();
                JOptionPane.showMessageDialog(this, "Archivo guardado exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarArchivoComo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos JU (*.ju)", "ju"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            archivoActual = fileChooser.getSelectedFile();
            
            // Agregar extensión .ju si no la tiene
            if (!archivoActual.getName().endsWith(".ju")) {
                archivoActual = new File(archivoActual.getAbsolutePath() + ".ju");
            }
            
            try {
                FileWriter writer = new FileWriter(archivoActual);
                writer.write(areaCodigo.getText());
                writer.close();
                setTitle("Compilador OLC1 - " + archivoActual.getName());
                JOptionPane.showMessageDialog(this, "Archivo guardado exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Interfaz().setVisible(true);
        });
    }
        
    
}