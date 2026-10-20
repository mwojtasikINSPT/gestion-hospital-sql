package prog2.parcial.daos;

import prog2.parcial.config.DBConnection;
import prog2.parcial.models.Reserva;
import prog2.parcial.models.Cama;
import prog2.parcial.models.Paciente;
import prog2.parcial.models.EstadoCama;
import prog2.parcial.dtos.ReservaDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservaDAO {

    public void guardar(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reservas (codigo_cama, id_paciente, activo) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, reserva.getCama().getCodigo());
            ps.setInt(2, reserva.getPaciente().getId());
            ps.setBoolean(3, reserva.isActivo());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    reserva.setId(rs.getInt(1));
                }
            }
        }
    }

    public Optional<Reserva> buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT r.id AS reserva_id, r.activo AS reserva_activo,
                   c.codigo, c.piso, c.estado, c.activo AS cama_activa,
                   p.id AS paciente_id, p.dni, p.nombre, p.apellido,
                   p.activo AS paciente_activo
            FROM reservas r
            JOIN camas c ON r.codigo_cama = c.codigo
            JOIN pacientes p ON r.id_paciente = p.id
            WHERE r.id = ?
            """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cama cama = new Cama(
                            rs.getString("codigo"),
                            rs.getInt("piso"),
                            EstadoCama.valueOf(rs.getString("estado")),
                            rs.getBoolean("cama_activa")
                    );

                    Paciente paciente = new Paciente(
                            rs.getInt("paciente_id"),
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getBoolean("paciente_activo")
                    );

                    return Optional.of(new Reserva(
                            rs.getInt("reserva_id"),
                            cama,
                            paciente,
                            rs.getBoolean("reserva_activo")
                    ));
                }
            }
        }

        return Optional.empty();
    }

    public List<ReservaDTO> listar() throws SQLException {
        String sql = """
            SELECT r.id AS reserva_id, r.activo AS reserva_activo,
                   c.codigo, c.piso,
                   p.id AS paciente_id, p.nombre, p.apellido, p.dni
            FROM reservas r
            JOIN camas c ON r.codigo_cama = c.codigo
            JOIN pacientes p ON r.id_paciente = p.id
            ORDER BY r.id
            """;

        List<ReservaDTO> reservas = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReservaDTO dto = new ReservaDTO();

                dto.setId(rs.getInt("reserva_id"));
                dto.setCodigoCama(rs.getString("codigo"));
                dto.setPiso(rs.getInt("piso"));
                dto.setIdPaciente(rs.getInt("paciente_id"));
                dto.setNombrePaciente(rs.getString("nombre"));
                dto.setApellidoPaciente(rs.getString("apellido"));
                dto.setDniPaciente(rs.getString("dni"));
                dto.setActivo(rs.getBoolean("reserva_activo"));

                reservas.add(dto);
            }

        }

        return reservas;
    }

    public boolean actualizar(Reserva reserva) throws SQLException {
        String sql = """
            UPDATE reservas
            SET codigo_cama = ?, id_paciente = ?
            WHERE id = ? AND activo = TRUE
            """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reserva.getCama().getCodigo());
            ps.setInt(2, reserva.getPaciente().getId());
            ps.setInt(3, reserva.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "UPDATE reservas SET activo = FALSE WHERE id = ? AND activo = TRUE";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<ReservaDTO> listarActivas() throws SQLException {
        String sql = """
            SELECT r.id AS reserva_id, r.activo AS reserva_activo,
                   c.codigo, c.piso,
                   p.id AS paciente_id, p.nombre, p.apellido, p.dni
            FROM reservas r
            JOIN camas c ON r.codigo_cama = c.codigo
            JOIN pacientes p ON r.id_paciente = p.id
            WHERE r.activo = TRUE
            ORDER BY r.id
            """;

        List<ReservaDTO> reservas = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReservaDTO dto = new ReservaDTO();

                dto.setId(rs.getInt("reserva_id"));
                dto.setCodigoCama(rs.getString("codigo"));
                dto.setPiso(rs.getInt("piso"));
                dto.setIdPaciente(rs.getInt("paciente_id"));
                dto.setNombrePaciente(rs.getString("nombre"));
                dto.setApellidoPaciente(rs.getString("apellido"));
                dto.setDniPaciente(rs.getString("dni"));
                dto.setActivo(rs.getBoolean("reserva_activo"));

                reservas.add(dto);
            }

        }

        return reservas;
    }

    //Paciente con su cama asignada
    public Optional<ReservaDTO> buscarActivaPorPaciente(int idPaciente) throws SQLException {
        String sql = """
            SELECT r.id, r.activo, c.codigo, c.piso,
                   p.id AS id_paciente, p.nombre, p.apellido, p.dni
            FROM reservas r
            INNER JOIN camas c ON r.codigo_cama = c.codigo
            INNER JOIN pacientes p ON r.id_paciente = p.id
            WHERE r.id_paciente = ? AND r.activo = TRUE
            """;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idPaciente);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    ReservaDTO dto = new ReservaDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setCodigoCama(rs.getString("codigo"));
                    dto.setPiso(rs.getInt("piso"));
                    dto.setIdPaciente(rs.getInt("id_paciente"));
                    dto.setNombrePaciente(rs.getString("nombre"));
                    dto.setApellidoPaciente(rs.getString("apellido"));
                    dto.setDniPaciente(rs.getString("dni"));
                    dto.setActivo(rs.getBoolean("activo"));

                    return Optional.of(dto);
                }
            }
        }

        return Optional.empty();
    }
}
