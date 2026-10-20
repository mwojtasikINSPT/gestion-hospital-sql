package prog2.parcial.services;

import java.sql.SQLException;
import java.util.List;
import prog2.parcial.daos.PacienteDAO;
import prog2.parcial.models.Paciente;
import prog2.parcial.utils.ValidacionesUtils;

public class PacienteService {

    private final PacienteDAO pacienteDAO;

    public PacienteService() {
        this.pacienteDAO = new PacienteDAO();
    }

    public void guardar(Paciente paciente) throws SQLException {
        validarPaciente(paciente);

        paciente.setDni(paciente.getDni().trim());
        paciente.setNombre(ValidacionesUtils.normalizarTextoAGuardar(paciente.getNombre()));
        paciente.setApellido(ValidacionesUtils.normalizarTextoAGuardar(paciente.getApellido()));
        paciente.setActivo(true);

        pacienteDAO.guardar(paciente);
    }

    public Paciente buscarPorId(int id) throws SQLException {
    if (id <= 0) {
        throw new IllegalArgumentException("El ID debe ser mayor a cero.");
    }

    return pacienteDAO.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado."));
}

    public List<Paciente> listar() throws SQLException {
        return pacienteDAO.listar();
    }

    public void actualizar(Paciente paciente) throws SQLException {
        if (paciente == null || paciente.getId() <= 0) {
            throw new IllegalArgumentException("Paciente inválido.");
        }

        validarPaciente(paciente);

        paciente.setDni(paciente.getDni().trim());
        paciente.setNombre(ValidacionesUtils.normalizarTextoAGuardar(paciente.getNombre()));
        paciente.setApellido(ValidacionesUtils.normalizarTextoAGuardar(paciente.getApellido()));

        pacienteDAO.actualizar(paciente);
    }

    public void eliminar(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a cero.");
        }

        pacienteDAO.eliminar(id);
    }

    private void validarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede ser nulo.");
        }

        if (ValidacionesUtils.esTextoVacio(paciente.getDni())
                || ValidacionesUtils.esTextoVacio(paciente.getNombre())
                || ValidacionesUtils.esTextoVacio(paciente.getApellido())) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }
    }
}