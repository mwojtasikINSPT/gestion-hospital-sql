package prog2.parcial.services;

import java.sql.SQLException;
import java.util.List;
import prog2.parcial.daos.MedicoDAO;
import prog2.parcial.models.Medico;
import prog2.parcial.utils.ValidacionesUtils;

public class MedicoService {

    private final MedicoDAO medicoDAO;

    public MedicoService() {
        this.medicoDAO = new MedicoDAO();
    }

    public void guardar(Medico medico) throws SQLException {
        validarMedico(medico);

        medico.setDni(medico.getDni().trim());
        medico.setNombre(ValidacionesUtils.normalizarTextoAGuardar(medico.getNombre()));
        medico.setApellido(ValidacionesUtils.normalizarTextoAGuardar(medico.getApellido()));
        medico.setEspecialidad(ValidacionesUtils.normalizarTextoAGuardar(medico.getEspecialidad()));
        medico.setActivo(true);

        medicoDAO.guardar(medico);
    }

    public Medico buscarPorId(int id) throws SQLException {
    if (id <= 0) {
        throw new IllegalArgumentException("El ID debe ser mayor a cero.");
    }

    return medicoDAO.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado."));
}

    public List<Medico> listar() throws SQLException {
        return medicoDAO.listar();
    }

    public void actualizar(Medico medico) throws SQLException {
        if (medico == null || medico.getId() <= 0) {
            throw new IllegalArgumentException("Médico inválido.");
        }

        validarMedico(medico);

        medico.setDni(medico.getDni().trim());
        medico.setNombre(ValidacionesUtils.normalizarTextoAGuardar(medico.getNombre()));
        medico.setApellido(ValidacionesUtils.normalizarTextoAGuardar(medico.getApellido()));
        medico.setEspecialidad(ValidacionesUtils.normalizarTextoAGuardar(medico.getEspecialidad()));

        medicoDAO.actualizar(medico);
    }

    public void eliminar(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a cero.");
        }

        medicoDAO.eliminar(id);
    }

    private void validarMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("El médico no puede ser nulo.");
        }

        if (ValidacionesUtils.esTextoVacio(medico.getDni())
                || ValidacionesUtils.esTextoVacio(medico.getNombre())
                || ValidacionesUtils.esTextoVacio(medico.getApellido())
                || ValidacionesUtils.esTextoVacio(medico.getEspecialidad())) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }
    }
}