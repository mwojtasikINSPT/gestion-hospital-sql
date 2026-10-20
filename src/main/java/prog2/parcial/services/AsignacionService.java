package prog2.parcial.services;

import java.sql.SQLException;
import java.util.List;
import prog2.parcial.daos.AsignacionDAO;
import prog2.parcial.daos.MedicoDAO;
import prog2.parcial.daos.PacienteDAO;
import prog2.parcial.dtos.AsignacionDTO;
import prog2.parcial.models.Asignacion;
import prog2.parcial.models.Medico;
import prog2.parcial.models.Paciente;

public class AsignacionService {

    private final AsignacionDAO asignacionDAO;
    private final MedicoDAO medicoDAO;
    private final PacienteDAO pacienteDAO;

    public AsignacionService() {
        this.asignacionDAO = new AsignacionDAO();
        this.medicoDAO = new MedicoDAO();
        this.pacienteDAO = new PacienteDAO();
    }

    public void guardar(int idMedico, int idPaciente) throws SQLException {
        if (idMedico <= 0 || idPaciente <= 0) {
            throw new IllegalArgumentException("Los ID deben ser mayores a cero.");
        }

        Medico medico = medicoDAO.buscarPorId(idMedico)
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado."));

        Paciente paciente = pacienteDAO.buscarPorId(idPaciente)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado."));

        if (!medico.isActivo()) {
            throw new IllegalArgumentException("El médico está desactivado.");
        }

        if (!paciente.isActivo()) {
            throw new IllegalArgumentException("El paciente está desactivado.");
        }

        for (AsignacionDTO asignacion : asignacionDAO.listarActivas()) {
            if (asignacion.getIdPaciente() == idPaciente) {
                throw new IllegalArgumentException("El paciente ya tiene un médico asignado.");
            }
        }

        Asignacion asignacion = new Asignacion();
        asignacion.setMedico(medico);
        asignacion.setPaciente(paciente);
        asignacion.setActivo(true);

        asignacionDAO.guardar(asignacion);
    }

    public Asignacion buscarPorId(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a cero.");
        }

        return asignacionDAO.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Asignación no encontrada."));
    }

    public List<AsignacionDTO> listar() throws SQLException {
        return asignacionDAO.listar();
    }

    public List<AsignacionDTO> listarActivas() throws SQLException {
        return asignacionDAO.listarActivas();
    }

    public List<AsignacionDTO> listarPorMedico(int idMedico) throws SQLException {
        if (idMedico <= 0) {
            throw new IllegalArgumentException("El ID del médico debe ser mayor a cero.");
        }

        return asignacionDAO.listarPorMedico(idMedico);
    }

    public void eliminar(int id) throws SQLException {
        Asignacion asignacion = buscarPorId(id);

        if (!asignacion.isActivo()) {
            throw new IllegalArgumentException("La asignación ya está cancelada.");
        }

        asignacionDAO.eliminar(id);
    }

    public void cambiarMedico(int idAsignacion, int idNuevoMedico) throws SQLException {
        Asignacion asignacion = buscarPorId(idAsignacion);

        if (!asignacion.isActivo()) {
            throw new IllegalArgumentException("La asignación está cancelada.");
        }

        if (asignacion.getMedico().getId() == idNuevoMedico) {
            throw new IllegalArgumentException("El paciente ya tiene asignado ese médico.");
        }

        Medico nuevoMedico = medicoDAO.buscarPorId(idNuevoMedico)
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado."));

        if (!nuevoMedico.isActivo()) {
            throw new IllegalArgumentException("El nuevo médico está desactivado.");
        }

        asignacionDAO.eliminar(idAsignacion);

        Asignacion nuevaAsignacion = new Asignacion();
        nuevaAsignacion.setMedico(nuevoMedico);
        nuevaAsignacion.setPaciente(asignacion.getPaciente());
        nuevaAsignacion.setActivo(true);

        asignacionDAO.guardar(nuevaAsignacion);
    }

    public AsignacionDTO buscarActivaPorPaciente(int idPaciente) throws SQLException {
        if (idPaciente <= 0) {
            throw new IllegalArgumentException("El ID del paciente debe ser mayor a cero.");
        }

        return asignacionDAO.buscarActivaPorPaciente(idPaciente)
                .orElseThrow(() -> new IllegalArgumentException(
                "El paciente no tiene un medico asignado."
        ));
    }
}
