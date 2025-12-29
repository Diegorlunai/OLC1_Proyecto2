package main;

import analizadores.Scanner;
import analizadores.Parser;
import arbol.*; // Importar todas las clases del árbol
import simbolo.TablaSimbolos; 
import java.io.StringReader;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        try {
            // TU ENTRADA DE PRUEBA
            String entrada = 
                "// ---------------- VARIABLES GLOBALES ----------------\n" +
                "var nota: double = 0.0;\n" +
                "\n" +
                "START_WITH main();\n" +
                "\n" +
                "// ---------------- METODO PRINCIPAL ----------------\n" +
                "void main(){\n" +
                "    print(\"----------------- CALIFICACION ARCHIVO 1 -----------------\\n\");\n" +
                "    // ... (resto del código para abreviar en la prueba visual) ...\n" +
                "    var var1: int = 20;\n" +
                "    if (var1 != 20) { print(\"Manejo de ambitos erroneo :(\"); } else { print(\"Manejo de ambitos correcto :D\"); nota = nota + 0.5; }\n" +
                "    declaracion();\n" +
                "    var miVariable: string = \":D\";\n" +
                "    ambitos();\n" +
                "    var resultado: int;\n" +
                "    resultado = mayor(10, 25);\n" +
                "    if (resultado == 25) { print(\"Funcion mayor funciona correctamente\"); nota = nota + 1.0; } else { print(\"Error en funcion mayor\"); }\n" +
                "    var notaFinal: double;\n" +
                "    notaFinal = nota * 10;\n" +
                "    print(\"\\nNota final archivo 1: \" + notaFinal + \" / 25\");\n" +
                "}\n" +
                "void declaracion(){ print(\"========= Metodo Declaracion =========\"); var num1: int; var cadena1: string = \"Si sale compi en vacas\"; var cadena2: string = \"No sale compi2 en vacas\"; var cadena3: string = \"No sale compi2 en vacas\"; var cadena4: string = \"Si sale compi1 en vacas\"; if (cadena1 == cadena4 ^ cadena2 == cadena3) { print(\"Declaracion Correcta\"); nota = nota + 1.0; } else { print(\"Declaracion de variables erronea :(\"); } }\n" +
                "void ambitos(){ print(\"========= Metodo Ambitos =========\"); var miVariable: string = \"Verificando ambitos\"; if (miVariable == \"Verificando ambitos\") { print(\"Entornos correctos :D\"); nota = nota + 0.5; } else { print(\"Los entornos estan mal :(\"); } }\n" +
                "int mayor(int a, int b){ if (a > b) { return a; } return b; }";

            System.out.println("--- INICIANDO COMPILACIÓN ---");
            main.Salida.limpiar(); 
            
            TablaSimbolos entornoGlobal = new TablaSimbolos();
            Scanner scanner = new Scanner(new StringReader(entrada));
            Parser parser = new Parser(scanner);
            parser.parse(); 
            
            LinkedList<Instruccion> ast = parser.AST;
            
            if (ast != null) {
                System.out.println("--- AST GENERADO CORRECTAMENTE ---");
                
                // PASO 1: Registrar Funciones
                System.out.println("[PASO 1] Registrando funciones...");
                for (Instruccion ins : ast) {
                    if (ins instanceof Funcion) {
                        // Casteamos para imprimir el nombre y verificar que lo detecta
                        Funcion f = (Funcion) ins; 
                        // Nota: Si tu clase Funcion no tiene getNombre publico, comenta la linea de abajo
                        // System.out.println("   -> Función encontrada: " + f.getNombre()); 
                        
                        ins.ejecutar(entornoGlobal);
                    }
                }

                // PASO 2: Variables Globales
                System.out.println("[PASO 2] Declarando globales...");
                for (Instruccion ins : ast) {
                    if (ins instanceof Declaracion || ins instanceof DeclaracionVector || ins instanceof DeclaracionLista) {
                        ins.ejecutar(entornoGlobal);
                    }
                }

                // PASO 3: Ejecutar StartWith
                System.out.println("[PASO 3] Buscando StartWith...");
                boolean startFound = false;
                for (Instruccion ins : ast) {
                    if (ins instanceof StartWith) {
                        System.out.println("   -> StartWith encontrado. Ejecutando...");
                        ins.ejecutar(entornoGlobal);
                        startFound = true;
                        break; 
                    }
                }
                
                if (!startFound) {
                    System.out.println("⚠️ ALERTA: No se encontró instrucción START_WITH en el AST.");
                }
                
                // PASO 4: Imprimir resultados
                System.out.println("--- SALIDA DEL PROGRAMA ---");
                if (main.Salida.listaSalida.isEmpty()) {
                    System.out.println("(La lista de salida está vacía)");
                }
                for (String linea : main.Salida.listaSalida) {
                    System.out.println(linea);
                }
                
            } else {
                System.out.println("❌ Error: No se pudo generar el árbol (AST es null).");
            }

        } catch (Exception e) {
            System.out.println("❌ EXCEPCIÓN FATAL:");
            e.printStackTrace();
        }
    }
}