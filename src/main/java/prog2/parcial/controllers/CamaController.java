package prog2.parcial.controllers;

import java.sql.SQLException;
import java.util.List;
import prog2.parcial.models.Cama;
import prog2.parcial.services.CamaService;

public class CamaController {

    private final CamaService camaService;

    public CamaController() {
        this.camaService = new CamaService();
    }

    public void guardar(Cama cama) throws SQLException {
        camaService.guardar(cama);
    }

    public Cama buscarPorCodigo(String codigo) throws SQLException {
        return camaService.buscarPorCodigo(codigo);
    }

    public List<Cama> listar() throws SQLException {
        return camaService.listar();
    }

    public void actualizar(Cama cama) throws SQLException {
        camaService.actualizar(cama);
    }

    public void eliminar(String codigo) throws SQLException {
        camaService.eliminar(codigo);
    }
}