package prog2.parcial;

import java.util.Scanner;
import prog2.parcial.utils.ConsolaUtils;
import prog2.parcial.views.PacienteView;
import prog2.parcial.views.MedicoView;
import prog2.parcial.views.CamaView;
import prog2.parcial.views.ReservaView;
import prog2.parcial.views.AsignacionView;

public class Main {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            PacienteView pacienteView = new PacienteView(scanner);
            MedicoView medicoView = new MedicoView(scanner);
            CamaView camaView = new CamaView(scanner);
            ReservaView reservaView = new ReservaView(scanner);
            AsignacionView asignacionView = new AsignacionView(scanner);

            int opcion;

            do {
                ConsolaUtils.imprimirTitulo("Sistema de gestion hospitalaria");

                ConsolaUtils.imprimirMenu(
                        "1. Gestion de pacientes",
                        "2. Gestion de medicos",
                        "3. Gestion de camas",
                        "4. Gestion de reservas",
                        "5. Gestion de asignaciones",
                        "0. Salir"
                );

                opcion = ConsolaUtils.leerEntero(scanner, "Opcion");

                switch (opcion) {
                    case 1 -> pacienteView.mostrarMenu();
                    case 2 -> medicoView.mostrarMenu();
                    case 3 -> camaView.mostrarMenu();
                    case 4 -> reservaView.mostrarMenu();
                    case 5 -> asignacionView.mostrarMenu();
                    case 0 -> ConsolaUtils.imprimirMensaje("Saliendo del sistema...");
                    default -> ConsolaUtils.imprimirError("Opcion invalida.");
                }

            } while (opcion != 0);
        }
    }
}