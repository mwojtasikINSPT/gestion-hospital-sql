package prog2.parcial.views;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import prog2.parcial.controllers.AsignacionController;
import prog2.parcial.dtos.AsignacionDTO;
import prog2.parcial.models.Asignacion;
import prog2.parcial.utils.ConsolaUtils;

public class AsignacionView {

    private final AsignacionController asignacionController;
    private final Scanner scanner;

    public AsignacionView(Scanner scanner) {
        this.asignacionController = new AsignacionController();
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion;

        do {
            ConsolaUtils.imprimirTitulo("Gestion de asignaciones");

            ConsolaUtils.imprimirMenu(
                    "1. Registrar asignacion",
                    "2. Buscar asignacion por ID",
                    "3. Listar todas las asignaciones",
                    "4. Listar asignaciones activas",
                    "5. Listar asignaciones por medico",
                    "6. Cambiar medico de una asignacion",
                    "7. Cancelar asignacion",
                    "8. Consultar medico asignado a un paciente",
                    "0. Volver"
            );

            opcion = ConsolaUtils.leerEntero(scanner, "Opcion");

            try {
                switch (opcion) {
                    case 1 ->
                        registrar();
                    case 2 ->
                        buscar();
                    case 3 ->
                        listar();
                    case 4 ->
                        listarActivas();
                    case 5 ->
                        listarPorMedico();
                    case 6 ->
                        cambiarMedico();
                    case 7 ->
                        eliminar();
                    case 8 ->
                        consultarMedicoPorPaciente();
                    case 0 ->
                        ConsolaUtils.imprimirMensaje("Volviendo al menu principal...");
                    default ->
                        ConsolaUtils.imprimirError("Opcion invalida.");
                }
            } catch (SQLException | IllegalArgumentException e) {
                ConsolaUtils.imprimirError(e.getMessage());
            }

        } while (opcion != 0);
    }

    private void registrar() throws SQLException {
        int idMedico = ConsolaUtils.leerEntero(scanner, "ID del medico");
        int idPaciente = ConsolaUtils.leerEntero(scanner, "ID del paciente");

        asignacionController.guardar(idMedico, idPaciente);
        ConsolaUtils.imprimirMensaje("Asignacion registrada correctamente.");
    }

    private void buscar() throws SQLException {
        int id = ConsolaUtils.leerEntero(scanner, "ID de la asignacion");

        mostrarAsignacion(asignacionController.buscarPorId(id));
    }

    private void listar() throws SQLException {
        List<AsignacionDTO> asignaciones = asignacionController.listar();

        if (asignaciones.isEmpty()) {
            ConsolaUtils.imprimirMensaje("No hay asignaciones registradas.");
            return;
        }

        asignaciones.forEach(this::mostrarAsignacionDTO);
    }

    private void listarActivas() throws SQLException {
        List<AsignacionDTO> asignaciones = asignacionController.listarActivas();

        if (asignaciones.isEmpty()) {
            ConsolaUtils.imprimirMensaje("No hay asignaciones activas.");
            return;
        }

        asignaciones.forEach(this::mostrarAsignacionDTO);
    }

    private void listarPorMedico() throws SQLException {
        int idMedico = ConsolaUtils.leerEntero(scanner, "ID del medico");

        List<AsignacionDTO> asignaciones = asignacionController.listarPorMedico(idMedico);

        if (asignaciones.isEmpty()) {
            ConsolaUtils.imprimirMensaje("El medico no tiene pacientes asignados.");
            return;
        }

        asignaciones.forEach(this::mostrarAsignacionDTO);
    }

    private void cambiarMedico() throws SQLException {
        int idAsignacion = ConsolaUtils.leerEntero(scanner, "ID de la asignacion");
        int idNuevoMedico = ConsolaUtils.leerEntero(scanner, "ID del nuevo medico");

        asignacionController.cambiarMedico(idAsignacion, idNuevoMedico);
        ConsolaUtils.imprimirMensaje("Medico cambiado correctamente.");
    }

    private void eliminar() throws SQLException {
        int id = ConsolaUtils.leerEntero(scanner, "ID de la asignacion a cancelar");

        asignacionController.eliminar(id);
        ConsolaUtils.imprimirMensaje("Asignacion cancelada correctamente.");
    }

    private void mostrarAsignacion(Asignacion asignacion) {
        ConsolaUtils.imprimirMensaje(
                "\n------------------------------"
                + "\nID: " + asignacion.getId()
                + "\nMedico: " + asignacion.getMedico().getNombre()
                + " " + asignacion.getMedico().getApellido()
                + "\nEspecialidad: " + asignacion.getMedico().getEspecialidad()
                + "\nPaciente: " + asignacion.getPaciente().getNombre()
                + " " + asignacion.getPaciente().getApellido()
                + "\nDNI: " + asignacion.getPaciente().getDni()
                + "\nActivo: " + (asignacion.isActivo() ? "Si" : "No")
        );
    }

    private void mostrarAsignacionDTO(AsignacionDTO asignacion) {
        ConsolaUtils.imprimirMensaje(
                "\n------------------------------"
                + "\nID: " + asignacion.getId()
                + "\nMedico ID: " + asignacion.getIdMedico()
                + "\nMedico: " + asignacion.getNombreMedico()
                + " " + asignacion.getApellidoMedico()
                + "\nEspecialidad: " + asignacion.getEspecialidad()
                + "\nPaciente ID: " + asignacion.getIdPaciente()
                + "\nPaciente: " + asignacion.getNombrePaciente()
                + " " + asignacion.getApellidoPaciente()
                + "\nDNI: " + asignacion.getDniPaciente()
                + "\nActivo: " + (asignacion.isActivo() ? "Si" : "No")
        );
    }

    private void consultarMedicoPorPaciente() throws SQLException {
        int idPaciente = ConsolaUtils.leerEntero(scanner, "ID del paciente");

        AsignacionDTO asignacion = asignacionController.buscarActivaPorPaciente(idPaciente);

        mostrarAsignacionDTO(asignacion);
    }
}
