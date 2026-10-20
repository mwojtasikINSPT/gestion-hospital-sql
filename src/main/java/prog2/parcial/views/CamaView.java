package prog2.parcial.views;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import prog2.parcial.controllers.CamaController;
import prog2.parcial.models.Cama;
import prog2.parcial.models.EstadoCama;
import prog2.parcial.utils.ConsolaUtils;

public class CamaView {

    private final CamaController camaController;
    private final Scanner scanner;

    public CamaView(Scanner scanner) {
        this.camaController = new CamaController();
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion;

        do {
            ConsolaUtils.imprimirTitulo("Gestion de camas");

            ConsolaUtils.imprimirMenu(
                    "1. Registrar cama",
                    "2. Buscar cama por codigo",
                    "3. Listar camas",
                    "4. Actualizar cama",
                    "5. Eliminar cama",
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
        Cama cama = new Cama();

        cama.setCodigo(ConsolaUtils.leerTexto(scanner, "Codigo de cama"));
        cama.setPiso(ConsolaUtils.leerEntero(scanner, "Piso"));
        cama.setEstado(EstadoCama.LIBRE);

        camaController.guardar(cama);
        ConsolaUtils.imprimirMensaje("Cama registrada correctamente.");
    }

    private void buscar() throws SQLException {
        String codigo = ConsolaUtils.leerTexto(scanner, "Codigo de cama");

        mostrarCama(camaController.buscarPorCodigo(codigo));
    }

    private void listar() throws SQLException {
        List<Cama> camas = camaController.listar();

        if (camas.isEmpty()) {
            ConsolaUtils.imprimirMensaje("No hay camas registradas.");
            return;
        }

        camas.forEach(this::mostrarCama);
    }

    private void actualizar() throws SQLException {
        String codigo = ConsolaUtils.leerTexto(scanner, "Codigo de cama a actualizar");

        Cama cama = camaController.buscarPorCodigo(codigo);

        cama.setPiso(ConsolaUtils.leerEntero(scanner, "Nuevo piso"));

        camaController.actualizar(cama);
        ConsolaUtils.imprimirMensaje("Cama actualizada correctamente.");
    }

    private void eliminar() throws SQLException {
        String codigo = ConsolaUtils.leerTexto(scanner, "Codigo de cama a eliminar");

        camaController.eliminar(codigo);
        ConsolaUtils.imprimirMensaje("Cama desactivada correctamente.");
    }

    private void mostrarCama(Cama cama) {
        ConsolaUtils.imprimirMensaje(
                "\n------------------------------"
                + "\nCodigo: " + cama.getCodigo()
                + "\nPiso: " + cama.getPiso()
                + "\nEstado: " + cama.getEstado()
                + "\nActivo: " + (cama.isActivo() ? "Si" : "No")
        );
    }
}