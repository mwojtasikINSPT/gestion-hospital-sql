package prog2.parcial.daos;

import prog2.parcial.models.Cama;
import prog2.parcial.models.EstadoCama;
import prog2.parcial.config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CamaDAO {

    public void guardar(Cama cama) throws SQLException {
        String sql = "INSERT INTO camas (codigo, piso, estado) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cama.getCodigo());
            ps.setInt(2, cama.getPiso());
            ps.setString(3, cama.getEstado().name());

            ps.executeUpdate();
        }
    }

    public Optional<Cama> buscarPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT * FROM camas WHERE codigo = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearCama(rs));
                }
            }
        }

        return Optional.empty();
    }

    public List<Cama> listar() throws SQLException {
        String sql = "SELECT * FROM camas";
        List<Cama> camas = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                camas.add(mapearCama(rs));
            }
        }

        return camas;
    }

    public boolean actualizar(Cama cama) throws SQLException {
        String sql = "UPDATE camas SET piso = ?, estado = ? WHERE codigo = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cama.getPiso());
            ps.setString(2, cama.getEstado().name());
            ps.setString(3, cama.getCodigo());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(String codigo) throws SQLException {
        String sql = "DELETE FROM camas WHERE codigo = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        }
    }

    private Cama mapearCama(ResultSet rs) throws SQLException {
        return new Cama(
                rs.getString("codigo"),
                rs.getInt("piso"),
                EstadoCama.valueOf(rs.getString("estado")),
                rs.getBoolean("activo")
        );
    }

}
