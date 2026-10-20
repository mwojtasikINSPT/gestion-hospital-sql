package prog2.parcial.controllers;

import java.sql.SQLException;
import java.util.List;
import prog2.parcial.models.Paciente;
import prog2.parcial.services.PacienteService;

public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController() {
        this.pacienteService = new PacienteService();
    }

    public void guardar(Paciente paciente) throws SQLException {
        pacienteService.guardar(paciente);
    }

    public Paciente buscarPorId(int id) throws SQLException {
        return pacienteService.buscarPorId(id);
    }

    public List<Paciente> listar() throws SQLException {
        return pacienteService.listar();
    }

    public void actualizar(Paciente paciente) throws SQLException {
        pacienteService.actualizar(paciente);
    }

    public void eliminar(int id) throws SQLException {
        pacienteService.eliminar(id);
    }
}