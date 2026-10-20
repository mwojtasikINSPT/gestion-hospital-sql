package prog2.parcial.utils;


import java.util.Locale;

public final class ValidacionesUtils {

    private ValidacionesUtils() {
        // Evita instanciar la clase
    }

    // 1. Validar número mayor que cero
    public static boolean esMayorQueCero(int numero) {
        return numero > 0;
    }

    // 2. Validar texto vacío
    public static boolean esTextoVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    // 3. Normalizar texto para guardar
    public static String normalizarTextoAGuardar(String texto) {

        if (esTextoVacio(texto)) {
            return "";
        }

        texto = texto.trim()
                .replaceAll("\\s+", " ")
                .toLowerCase(Locale.ROOT);

        String[] palabras = texto.split(" ");
        StringBuilder resultado = new StringBuilder();

        for (String palabra : palabras) {
            resultado.append(palabra.substring(0, 1).toUpperCase(Locale.ROOT)).append(palabra.substring(1)).append(" ");
        }

        return resultado.toString().trim();
    }

    // 4. Normalizar texto para buscar
    public static String normalizarTextoABuscar(String texto) {

        if (esTextoVacio(texto)) {
            return "";
        }

        return texto.trim()
                .replaceAll("\\s+", " ")
                .toLowerCase(Locale.ROOT);
    }
}
