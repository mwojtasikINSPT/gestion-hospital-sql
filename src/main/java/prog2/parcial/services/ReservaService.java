package prog2.parcial.services;

import java.sql.SQLException;
import java.util.List;
import prog2.parcial.daos.CamaDAO;
import prog2.parcial.daos.PacienteDAO;
import prog2.parcial.daos.ReservaDAO;
import prog2.parcial.dtos.ReservaDTO;
import prog2.parcial.models.Cama;
import prog2.parcial.models.EstadoCama;
import prog2.parcial.models.Paciente;
import prog2.parcial.models.Reserva;

public class ReservaService {

    private final ReservaDAO reservaDAO;
    private final CamaDAO camaDAO;
    private final PacienteDAO pacienteDAO;

    public ReservaService() {
        this.reservaDAO = new ReservaDAO();
        this.camaDAO = new CamaDAO();
        this.pacienteDAO = new PacienteDAO();
    }

    public void guardar(String codigoCama, int idPaciente) throws SQLException {
        if (codigoCama == null || codigoCama.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de cama es obligatorio.");
        }

        if (idPaciente <= 0) {
            throw new IllegalArgumentException("El ID del paciente debe ser mayor a cero.");
        }

        String codigo = codigoCama.trim().toUpperCase();

        Cama cama = camaDAO.buscarPorCodigo(codigo)
                .orElseThrow(() -> new IllegalArgumentException("Cama no encontrada."));

        Paciente paciente = pacienteDAO.buscarPorId(idPaciente)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado."));

        if (!cama.isActivo()) {
            throw new IllegalArgumentException("La cama está desactivada.");
        }

        if (!paciente.isActivo()) {
            throw new IllegalArgumentException("El paciente está desactivado.");
        }

        if (cama.getEstado() != EstadoCama.LIBRE) {
            throw new IllegalArgumentException("La cama está ocupada.");
        }

        for (ReservaDTO reserva : reservaDAO.listarActivas()) {
            if (reserva.getIdPaciente() == idPaciente) {
                throw new IllegalArgumentException("El paciente ya tiene una reserva activa.");
            }
        }

        Reserva reserva = new Reserva();
        reserva.setCama(cama);
        reserva.setPaciente(paciente);
        reserva.setActivo(true);

        reservaDAO.guardar(reserva);

        cama.setEstado(EstadoCama.OCUPADA);
        camaDAO.actualizar(cama);
    }

    public Reserva buscarPorId(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a cero.");
        }

        return reservaDAO.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada."));
    }

    public List<ReservaDTO> listar() throws SQLException {
        return reservaDAO.listar();
    }

    public List<ReservaDTO> listarActivas() throws SQLException {
        return reservaDAO.listarActivas();
    }

    public void eliminar(int id) throws SQLException {
        Reserva reserva = buscarPorId(id);

        if (!reserva.isActivo()) {
            throw new IllegalArgumentException("La reserva ya está cancelada.");
        }

        reservaDAO.eliminar(id);

        Cama cama = reserva.getCama();
        cama.setEstado(EstadoCama.LIBRE);
        camaDAO.actualizar(cama);
    }

    public ReservaDTO buscarActivaPorPaciente(int idPaciente) throws SQLException {
        if (idPaciente <= 0) {
            throw new IllegalArgumentException("El ID del paciente debe ser mayor a cero.");
        }

        return reservaDAO.buscarActivaPorPaciente(idPaciente)
                .orElseThrow(() -> new IllegalArgumentException(
                "El paciente no tiene una cama asignada."
        ));
    }

}
