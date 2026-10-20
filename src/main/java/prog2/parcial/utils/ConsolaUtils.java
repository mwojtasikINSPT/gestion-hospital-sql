package prog2.parcial.utils;

import java.util.Scanner;

public class ConsolaUtils {

    public static void imprimirTitulo(String titulo) {
        System.out.println("\n===== " + titulo.toUpperCase() + " =====");
    }

    public static void imprimirMenu(String... opciones) {
        for (String opcion : opciones) {
            System.out.println(opcion);
        }
    }

    public static void imprimirMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public static void imprimirError(String mensaje) {
        System.out.println("Error: " + mensaje);
    }

    public static void solicitarDato(String mensaje) {
        System.out.print(mensaje + ": ");
    }

    public static String leerTexto(Scanner scanner, String mensaje) {
        solicitarDato(mensaje);
        return scanner.nextLine();
    }

    public static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            solicitarDato(mensaje);
            String entrada = scanner.nextLine().trim();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                imprimirError("Debe ingresar un número válido.");
            }
        }
    }
}
