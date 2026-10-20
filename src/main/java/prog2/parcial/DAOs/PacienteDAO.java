package prog2.parcial.daos;

import prog2.parcial.models.Paciente;
import prog2.parcial.config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacienteDAO {

    public void guardar(Paciente paciente) throws SQLException {
        String sql = "INSERT INTO pacientes (dni, nombre, apellido, activo) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, paciente.getDni());
            ps.setString(2, paciente.getNombre());
            ps.setString(3, paciente.getApellido());
            ps.setBoolean(4, paciente.isActivo());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    paciente.setId(rs.getInt(1));
                }
            }
        }
    }

    public Optional<Paciente> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM pacientes WHERE id = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearPaciente(rs));
                }
            }
        }

        return Optional.empty();
    }

    public List<Paciente> listar() throws SQLException {
        String sql = "SELECT * FROM pacientes";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pacientes.add(mapearPaciente(rs));
            }
        }

        return pacientes;
    }

    public boolean actualizar(Paciente paciente) throws SQLException {
        String sql = "UPDATE pacientes SET dni = ?, nombre = ?, apellido = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, paciente.getDni());
            ps.setString(2, paciente.getNombre());
            ps.setString(3, paciente.getApellido());
            ps.setInt(4, paciente.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "UPDATE pacientes SET activo = FALSE WHERE id = ? AND activo = TRUE";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Paciente mapearPaciente(ResultSet rs) throws SQLException {
        return new Paciente(
                rs.getInt("id"),
                rs.getString("dni"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getBoolean("activo")
        );
    }

}
