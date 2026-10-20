package prog2.parcial.views;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import prog2.parcial.controllers.PacienteController;
import prog2.parcial.models.Paciente;
import prog2.parcial.utils.ConsolaUtils;

public class PacienteView {

    private final PacienteController pacienteController;
    private final Scanner scanner;

    public PacienteView(Scanner scanner) {
        this.pacienteController = new PacienteController();
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion;

        do {
            ConsolaUtils.imprimirTitulo("Gestion de pacientes");

            ConsolaUtils.imprimirMenu(
                    "1. Registrar paciente",
                    "2. Buscar paciente por ID",
                    "3. Listar pacientes",
                    "4. Actualizar paciente",
                    "5. Eliminar paciente",
                    "0. Volver"
            );

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1 ->
                        registrar();
                    case 2 ->
                        buscar();
                    case 3 ->
                        listar();
                    case 4 ->
                        actualizar();
                    case 5 ->
                        eliminar();
                    case 0 ->
                        System.out.println("Volviendo al menu principal...");
                    default ->
                        System.out.println("Opcion invalida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero valido.");
                opcion = -1;
            } catch (SQLException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                opcion = -1;
            }

        } while (opcion != 0);
    }

    private void registrar() throws SQLException {
        Paciente paciente = new Paciente();

        paciente.setDni(ConsolaUtils.leerTexto(scanner, "DNI"));
        paciente.setNombre(ConsolaUtils.leerTexto(scanner, "Nombre"));
        paciente.setApellido(ConsolaUtils.leerTexto(scanner, "Apellido"));

        pacienteController.guardar(paciente);
        ConsolaUtils.imprimirMensaje("Paciente registrado correctamente.");
    }

    private void buscar() throws SQLException {
        int id = ConsolaUtils.leerEntero(scanner, "ID del paciente");

        mostrarPaciente(pacienteController.buscarPorId(id));
    }

    private void listar() throws SQLException {
        List<Paciente> pacientes = pacienteController.listar();

        if (pacientes.isEmpty()) {
            ConsolaUtils.imprimirMensaje("No hay pacientes registrados.");
            return;
        }

        pacientes.forEach(this::mostrarPaciente);
    }

    private void actualizar() throws SQLException {
        int id = ConsolaUtils.leerEntero(scanner, "ID del paciente a actualizar");

        Paciente paciente = pacienteController.buscarPorId(id);

        paciente.setDni(ConsolaUtils.leerTexto(scanner, "Nuevo DNI"));
        paciente.setNombre(ConsolaUtils.leerTexto(scanner, "Nuevo nombre"));
        paciente.setApellido(ConsolaUtils.leerTexto(scanner, "Nuevo apellido"));

        pacienteController.actualizar(paciente);
        ConsolaUtils.imprimirMensaje("Paciente actualizado correctamente.");
    }

    private void eliminar() throws SQLException {
        int id = ConsolaUtils.leerEntero(scanner, "ID del paciente a eliminar");

        pacienteController.eliminar(id);
        ConsolaUtils.imprimirMensaje("Paciente desactivado correctamente.");
    }

    private void mostrarPaciente(Paciente paciente) {
        ConsolaUtils.imprimirMensaje(
                "\n------------------------------"
                + "\nID: " + paciente.getId()
                + "\nDNI: " + paciente.getDni()
                + "\nNombre: " + paciente.getNombre()
                + "\nApellido: " + paciente.getApellido()
                + "\nActivo: " + (paciente.isActivo() ? "Si" : "No")
        );
    }
}
