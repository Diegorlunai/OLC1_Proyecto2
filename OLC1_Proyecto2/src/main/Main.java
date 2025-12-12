package main;

import analizadores.Scanner;
import analizadores.Parser;
import arbol.Instruccion;
import simbolo.TablaSimbolos; 
import java.io.StringReader;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        try {
            // --- PRUEBA DE CASTEOS (Conversión de tipos) ---
            String entrada = 
                "var decimal : double = 18.9;\n" +             
                "// Convertimos el 18.9 a entero (deberia ser 18)\n" +
                "var entero : int = (int) decimal;\n" +  
                
                "print(\"Original: \" + decimal);\n" +
                "print(\"Casteado a int: \" + entero);\n" +
                
                "// Convertimos codigo ASCII 70 a caracter\n" +
                "var letra : char = (char) 70;\n" + 
                "print(\"Codigo 70 es letra: \" + letra);";

            System.out.println("--- INICIANDO COMPILACIÓN ---");
            
            TablaSimbolos entornoGlobal = new TablaSimbolos();
            Scanner scanner = new Scanner(new StringReader(entrada));
            Parser parser = new Parser(scanner);
            parser.parse(); 
            
            LinkedList<Instruccion> ast = parser.AST;
            
            if (ast != null) {
                System.out.println("--- EJECUTANDO CÓDIGO ---");
                for (Instruccion ins : ast) {
                    if(ins != null) ins.ejecutar(entornoGlobal); 
                }
            } else {
                System.out.println("❌ Error: No se pudo generar el árbol.");
            }

        } catch (Exception e) {
            System.out.println("❌ Ocurrió un error grave:");
            e.printStackTrace();
        }
    }
}