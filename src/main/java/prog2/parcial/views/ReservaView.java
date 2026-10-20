package prog2.parcial.views;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import prog2.parcial.controllers.ReservaController;
import prog2.parcial.dtos.ReservaDTO;
import prog2.parcial.models.Reserva;
import prog2.parcial.utils.ConsolaUtils;

public class ReservaView {

    private final ReservaController reservaController;
    private final Scanner scanner;

    public ReservaView(Scanner scanner) {
        this.reservaController = new ReservaController();
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion;

        do {
            ConsolaUtils.imprimirTitulo("Gestion de reservas");

            ConsolaUtils.imprimirMenu(
                    "1. Registrar reserva",
                    "2. Buscar reserva por ID",
                    "3. Listar todas las reservas",
                    "4. Listar reservas activas",
                    "5. Cancelar reserva",
                    "6. Consultar cama asignada a un paciente",
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
                        eliminar();
                    case 6 ->
                        consultarCamaPorPaciente();
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
        String codigoCama = ConsolaUtils.leerTexto(scanner, "Codigo de cama");
        int idPaciente = ConsolaUtils.leerEntero(scanner, "ID del paciente");

        reservaController.guardar(codigoCama, idPaciente);
        ConsolaUtils.imprimirMensaje("Reserva registrada correctamente.");
    }

    private void buscar() throws SQLException {
        int id = ConsolaUtils.leerEntero(scanner, "ID de la reserva");

        Reserva reserva = reservaController.buscarPorId(id);
        mostrarReserva(reserva);
    }

    private void listar() throws SQLException {
        List<ReservaDTO> reservas = reservaController.listar();

        if (reservas.isEmpty()) {
            ConsolaUtils.imprimirMensaje("No hay reservas registradas.");
            return;
        }

        reservas.forEach(this::mostrarReservaDTO);
    }

    private void listarActivas() throws SQLException {
        List<ReservaDTO> reservas = reservaController.listarActivas();

        if (reservas.isEmpty()) {
            ConsolaUtils.imprimirMensaje("No hay reservas activas.");
            return;
        }

        reservas.forEach(this::mostrarReservaDTO);
    }

    private void eliminar() throws SQLException {
        int id = ConsolaUtils.leerEntero(scanner, "ID de la reserva a cancelar");

        reservaController.eliminar(id);
        ConsolaUtils.imprimirMensaje("Reserva cancelada correctamente.");
    }

    private void mostrarReserva(Reserva reserva) {
        ConsolaUtils.imprimirMensaje(
                "\n------------------------------"
                + "\nID: " + reserva.getId()
                + "\nCama: " + reserva.getCama().getCodigo()
                + "\nPiso: " + reserva.getCama().getPiso()
                + "\nPaciente: " + reserva.getPaciente().getNombre()
                + " " + reserva.getPaciente().getApellido()
                + "\nDNI: " + reserva.getPaciente().getDni()
                + "\nActivo: " + (reserva.isActivo() ? "Si" : "No")
        );
    }

    private void mostrarReservaDTO(ReservaDTO reserva) {
        ConsolaUtils.imprimirMensaje(
                "\n------------------------------"
                + "\nID: " + reserva.getId()
                + "\nCama: " + reserva.getCodigoCama()
                + "\nPiso: " + reserva.getPiso()
                + "\nPaciente ID: " + reserva.getIdPaciente()
                + "\nPaciente: " + reserva.getNombrePaciente()
                + " " + reserva.getApellidoPaciente()
                + "\nDNI: " + reserva.getDniPaciente()
                + "\nActivo: " + (reserva.isActivo() ? "Si" : "No")
        );
    }

    private void consultarCamaPorPaciente() throws SQLException {
        int idPaciente = ConsolaUtils.leerEntero(scanner, "ID del paciente");

        ReservaDTO reserva = reservaController.buscarActivaPorPaciente(idPaciente);

        mostrarReservaDTO(reserva);
    }
}
