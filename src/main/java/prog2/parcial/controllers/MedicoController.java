package prog2.parcial.controllers;

import java.sql.SQLException;
import java.util.List;
import prog2.parcial.models.Medico;
import prog2.parcial.services.MedicoService;

public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController() {
        this.medicoService = new MedicoService();
    }

    public void guardar(Medico medico) throws SQLException {
        medicoService.guardar(medico);
    }

    public Medico buscarPorId(int id) throws SQLException {
        return medicoService.buscarPorId(id);
    }

    public List<Medico> listar() throws SQLException {
        return medicoService.listar();
    }

    public void actualizar(Medico medico) throws SQLException {
        medicoService.actualizar(medico);
    }

    public void eliminar(int id) throws SQLException {
        medicoService.eliminar(id);
    }
}