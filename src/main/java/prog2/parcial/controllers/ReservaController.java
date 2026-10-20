package prog2.parcial.controllers;

import java.sql.SQLException;
import java.util.List;
import prog2.parcial.dtos.ReservaDTO;
import prog2.parcial.models.Reserva;
import prog2.parcial.services.ReservaService;

public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController() {
        this.reservaService = new ReservaService();
    }

    public void guardar(String codigoCama, int idPaciente) throws SQLException {
        reservaService.guardar(codigoCama, idPaciente);
    }

    public Reserva buscarPorId(int id) throws SQLException {
        return reservaService.buscarPorId(id);
    }

    public List<ReservaDTO> listar() throws SQLException {
        return reservaService.listar();
    }

    public List<ReservaDTO> listarActivas() throws SQLException {
        return reservaService.listarActivas();
    }

    public void eliminar(int id) throws SQLException {
        reservaService.eliminar(id);
    }

    public ReservaDTO buscarActivaPorPaciente(int idPaciente) throws SQLException {
        return reservaService.buscarActivaPorPaciente(idPaciente);
    }
}
