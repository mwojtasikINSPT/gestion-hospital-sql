package prog2.parcial.controllers;

import java.sql.SQLException;
import java.util.List;
import prog2.parcial.dtos.AsignacionDTO;
import prog2.parcial.models.Asignacion;
import prog2.parcial.services.AsignacionService;

public class AsignacionController {

    private final AsignacionService asignacionService;

    public AsignacionController() {
        this.asignacionService = new AsignacionService();
    }

    public void guardar(int idMedico, int idPaciente) throws SQLException {
        asignacionService.guardar(idMedico, idPaciente);
    }

    public Asignacion buscarPorId(int id) throws SQLException {
        return asignacionService.buscarPorId(id);
    }

    public List<AsignacionDTO> listar() throws SQLException {
        return asignacionService.listar();
    }

    public List<AsignacionDTO> listarActivas() throws SQLException {
        return asignacionService.listarActivas();
    }

    public List<AsignacionDTO> listarPorMedico(int idMedico) throws SQLException {
        return asignacionService.listarPorMedico(idMedico);
    }

    public void eliminar(int id) throws SQLException {
        asignacionService.eliminar(id);
    }

    public void cambiarMedico(int idAsignacion, int idNuevoMedico) throws SQLException {
        asignacionService.cambiarMedico(idAsignacion, idNuevoMedico);
    }

    public AsignacionDTO buscarActivaPorPaciente(int idPaciente) throws SQLException {
        return asignacionService.buscarActivaPorPaciente(idPaciente);
    }
}
