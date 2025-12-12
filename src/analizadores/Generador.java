package analizadores;

public class Generador {
    public static void main(String[] args) {
        try {
            // Ruta donde están tus archivos (src/analizadores)
            String ruta = "src/analizadores/";
            
            // Argumentos para JFlex (Scanner)
            String[] opJFlex = {ruta + "Scanner.jflex", "-d", ruta};
            jflex.Main.generate(opJFlex);

            // Argumentos para CUP (Parser)
            // -parser NombreArchivo : Define como se llamará la clase java
            String[] opCUP = {"-destdir", ruta, "-parser", "Parser", ruta + "Parser.cup"};
            java_cup.Main.main(opCUP);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}