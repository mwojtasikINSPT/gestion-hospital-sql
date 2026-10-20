package prog2.parcial.views;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import prog2.parcial.controllers.MedicoController;
import prog2.parcial.models.Medico;
import prog2.parcial.utils.ConsolaUtils;

public class MedicoView {

    private final MedicoController medicoController;
    private final Scanner scanner;

    public MedicoView(Scanner scanner) {
        this.medicoController = new MedicoController();
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion;

        do {
            ConsolaUtils.imprimirTitulo("Gestion de medicos");

            ConsolaUtils.imprimirMenu(
                    "1. Registrar medico",
                    "2. Buscar medico por ID",
                    "3. Listar medicos",
                    "4. Actualizar medico",
                    "5. Eliminar medico",
                    "0. Volver"
            );

            opcion = ConsolaUtils.leerEntero(scanner, "Opcion");

            try {
                switch (opcion) {
                    case 1 -> registrar();
                    case 2 -> buscar();
                    case 3 -> listar();
                    case 4 -> actualizar();
                    case 5 -> eliminar();
                    case 0 -> ConsolaUtils.imprimirMensaje("Volviendo al menu principal...");
                    default -> ConsolaUtils.imprimirError("Opcion invalida.");
                }
            } catch (SQLException | IllegalArgumentException e) {
                ConsolaUtils.imprimirError(e.getMessage());
            }

        } while (opcion != 0);
    }

    private void registrar() throws SQLException {
        Medico medico = new Medico();

        medico.setDni(ConsolaUtils.leerTexto(scanner, "DNI"));
        medico.setNombre(ConsolaUtils.leerTexto(scanner, "Nombre"));
        medico.setApellido(ConsolaUtils.leerTexto(scanner, "Apellido"));
        medico.setEspecialidad(ConsolaUtils.leerTexto(scanner, "Especialidad"));

        medicoController.guardar(medico);
        ConsolaUtils.imprimirMensaje("Medico registrado correctamente.");
    }

    private void buscar() throws SQLException {
        int id = ConsolaUtils.leerEntero(scanner, "ID del medico");

        mostrarMedico(medicoController.buscarPorId(id));
    }

    private void listar() throws SQLException {
        List<Medico> medicos = medicoController.listar();

        if (medicos.isEmpty()) {
            ConsolaUtils.imprimirMensaje("No hay medicos registrados.");
            return;
        }

        medicos.forEach(this::mostrarMedico);
    }

    private void actualizar() throws SQLException {
        int id = ConsolaUtils.leerEntero(scanner, "ID del medico a actualizar");

        Medico medico = medicoController.buscarPorId(id);

        medico.setDni(ConsolaUtils.leerTexto(scanner, "Nuevo DNI"));
        medico.setNombre(ConsolaUtils.leerTexto(scanner, "Nuevo nombre"));
        medico.setApellido(ConsolaUtils.leerTexto(scanner, "Nuevo apellido"));
        medico.setEspecialidad(ConsolaUtils.leerTexto(scanner, "Nueva especialidad"));

        medicoController.actualizar(medico);
        ConsolaUtils.imprimirMensaje("Medico actualizado correctamente.");
    }

    private void eliminar() throws SQLException {
        int id = ConsolaUtils.leerEntero(scanner, "ID del medico a eliminar");

        medicoController.eliminar(id);
        ConsolaUtils.imprimirMensaje("Medico desactivado correctamente.");
    }

    private void mostrarMedico(Medico medico) {
        ConsolaUtils.imprimirMensaje(
                "\n------------------------------"
                + "\nID: " + medico.getId()
                + "\nDNI: " + medico.getDni()
                + "\nNombre: " + medico.getNombre()
                + "\nApellido: " + medico.getApellido()
                + "\nEspecialidad: " + medico.getEspecialidad()
                + "\nActivo: " + (medico.isActivo() ? "Si" : "No")
        );
    }
}