package prog2.parcial.services;

import java.sql.SQLException;
import java.util.List;
import prog2.parcial.daos.CamaDAO;
import prog2.parcial.models.Cama;
import prog2.parcial.models.EstadoCama;

public class CamaService {

    private final CamaDAO camaDAO;

    public CamaService() {
        this.camaDAO = new CamaDAO();
    }

    public void guardar(Cama cama) throws SQLException {
        validarCama(cama);

        cama.setCodigo(cama.getCodigo().trim().toUpperCase());
        cama.setEstado(EstadoCama.LIBRE);
        cama.setActivo(true);

        camaDAO.guardar(cama);
    }

    public Cama buscarPorCodigo(String codigo) throws SQLException {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de la cama es obligatorio.");
        }

        return camaDAO.buscarPorCodigo(codigo.trim().toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException("Cama no encontrada."));
    }

    public List<Cama> listar() throws SQLException {
        return camaDAO.listar();
    }

    public void actualizar(Cama cama) throws SQLException {
        validarCama(cama);

        cama.setCodigo(cama.getCodigo().trim().toUpperCase());

        camaDAO.actualizar(cama);
    }

    public void eliminar(String codigo) throws SQLException {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de la cama es obligatorio.");
        }

        Cama cama = buscarPorCodigo(codigo);

        if (cama.getEstado() == EstadoCama.OCUPADA) {
            throw new IllegalArgumentException("No se puede desactivar una cama ocupada.");
        }

        camaDAO.eliminar(codigo.trim().toUpperCase());
    }

    private void validarCama(Cama cama) {
        if (cama == null) {
            throw new IllegalArgumentException("La cama no puede ser nula.");
        }

        if (cama.getCodigo() == null || cama.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("El código de la cama es obligatorio.");
        }

        if (cama.getPiso() <= 0) {
            throw new IllegalArgumentException("El piso debe ser mayor a cero.");
        }

        if (cama.getEstado() == null) {
            throw new IllegalArgumentException("El estado de la cama es obligatorio.");
        }
    }
}