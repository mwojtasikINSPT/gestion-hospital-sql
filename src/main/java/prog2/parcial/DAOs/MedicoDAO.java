package prog2.parcial.daos;

import prog2.parcial.models.Medico;
import prog2.parcial.config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicoDAO {

    public void guardar(Medico medico) throws SQLException {
        String sql = "INSERT INTO medicos (dni, nombre, apellido, especialidad, activo) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, medico.getDni());
            ps.setString(2, medico.getNombre());
            ps.setString(3, medico.getApellido());
            ps.setString(4, medico.getEspecialidad());
            ps.setBoolean(5, medico.isActivo());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    medico.setId(rs.getInt(1));
                }
            }
        }
    }

    public Optional<Medico> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM medicos WHERE id = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearMedico(rs));
                }
            }
        }

        return Optional.empty();
    }

    public List<Medico> listar() throws SQLException {
        String sql = "SELECT * FROM medicos";
        List<Medico> medicos = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                medicos.add(mapearMedico(rs));
            }
        }

        return medicos;
    }

    public boolean actualizar(Medico medico) throws SQLException {
        String sql = "UPDATE medicos SET dni = ?, nombre = ?, apellido = ?, especialidad = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, medico.getDni());
            ps.setString(2, medico.getNombre());
            ps.setString(3, medico.getApellido());
            ps.setString(4, medico.getEspecialidad());
            ps.setInt(5, medico.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "UPDATE medicos SET activo = FALSE WHERE id = ? AND activo = TRUE";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Medico mapearMedico(ResultSet rs) throws SQLException {
        return new Medico(
                rs.getInt("id"),
                rs.getString("dni"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("especialidad"),
                rs.getBoolean("activo")
        );
    }

}
