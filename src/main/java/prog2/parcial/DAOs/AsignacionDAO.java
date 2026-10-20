package prog2.parcial.daos;

import prog2.parcial.config.DBConnection;
import prog2.parcial.models.Asignacion;
import prog2.parcial.models.Medico;
import prog2.parcial.models.Paciente;
import prog2.parcial.dtos.AsignacionDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AsignacionDAO {

    public void guardar(Asignacion asignacion) throws SQLException {
        String sql = "INSERT INTO asignaciones (id_medico, id_paciente, activo) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, asignacion.getMedico().getId());
            ps.setInt(2, asignacion.getPaciente().getId());
            ps.setBoolean(3, asignacion.isActivo());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    asignacion.setId(rs.getInt(1));
                }
            }
        }
    }

    public Optional<Asignacion> buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT a.id AS asignacion_id, a.activo AS asignacion_activa,
                   m.id AS medico_id, m.dni AS medico_dni,
                   m.nombre AS medico_nombre, m.apellido AS medico_apellido,
                   m.especialidad, m.activo AS medico_activo,
                   p.id AS paciente_id, p.dni AS paciente_dni,
                   p.nombre AS paciente_nombre, p.apellido AS paciente_apellido,
                   p.activo AS paciente_activo
            FROM asignaciones a
            JOIN medicos m ON a.id_medico = m.id
            JOIN pacientes p ON a.id_paciente = p.id
            WHERE a.id = ?
            """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medico medico = new Medico();
                    medico.setId(rs.getInt("medico_id"));
                    medico.setDni(rs.getString("medico_dni"));
                    medico.setNombre(rs.getString("medico_nombre"));
                    medico.setApellido(rs.getString("medico_apellido"));
                    medico.setEspecialidad(rs.getString("especialidad"));
                    medico.setActivo(rs.getBoolean("medico_activo"));

                    Paciente paciente = new Paciente();
                    paciente.setId(rs.getInt("paciente_id"));
                    paciente.setDni(rs.getString("paciente_dni"));
                    paciente.setNombre(rs.getString("paciente_nombre"));
                    paciente.setApellido(rs.getString("paciente_apellido"));
                    paciente.setActivo(rs.getBoolean("paciente_activo"));

                    Asignacion asignacion = new Asignacion();
                    asignacion.setId(rs.getInt("asignacion_id"));
                    asignacion.setMedico(medico);
                    asignacion.setPaciente(paciente);
                    asignacion.setActivo(rs.getBoolean("asignacion_activa"));

                    return Optional.of(asignacion);
                }
            }
        }

        return Optional.empty();
    }

    public List<AsignacionDTO> listar() throws SQLException {
        String sql = """
            SELECT a.id AS asignacion_id, a.activo AS asignacion_activa,
                   m.id AS medico_id, m.nombre AS medico_nombre,
                   m.apellido AS medico_apellido, m.especialidad,
                   p.id AS paciente_id, p.nombre AS paciente_nombre,
                   p.apellido AS paciente_apellido, p.dni AS paciente_dni
            FROM asignaciones a
            JOIN medicos m ON a.id_medico = m.id
            JOIN pacientes p ON a.id_paciente = p.id
            ORDER BY a.id
            """;

        List<AsignacionDTO> asignaciones = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                asignaciones.add(mapearDTO(rs));
            }
        }

        return asignaciones;
    }

    public List<AsignacionDTO> listarActivas() throws SQLException {
        String sql = """
            SELECT a.id AS asignacion_id, a.activo AS asignacion_activa,
                   m.id AS medico_id, m.nombre AS medico_nombre,
                   m.apellido AS medico_apellido, m.especialidad,
                   p.id AS paciente_id, p.nombre AS paciente_nombre,
                   p.apellido AS paciente_apellido, p.dni AS paciente_dni
            FROM asignaciones a
            JOIN medicos m ON a.id_medico = m.id
            JOIN pacientes p ON a.id_paciente = p.id
            WHERE a.activo = TRUE
            ORDER BY a.id
            """;

        List<AsignacionDTO> asignaciones = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                asignaciones.add(mapearDTO(rs));
            }
        }

        return asignaciones;
    }

    public List<AsignacionDTO> listarPorMedico(int idMedico) throws SQLException {
        String sql = """
            SELECT a.id AS asignacion_id, a.activo AS asignacion_activa,
                   m.id AS medico_id, m.nombre AS medico_nombre,
                   m.apellido AS medico_apellido, m.especialidad,
                   p.id AS paciente_id, p.nombre AS paciente_nombre,
                   p.apellido AS paciente_apellido, p.dni AS paciente_dni
            FROM asignaciones a
            JOIN medicos m ON a.id_medico = m.id
            JOIN pacientes p ON a.id_paciente = p.id
            WHERE a.id_medico = ? AND a.activo = TRUE
            ORDER BY p.apellido
            """;

        List<AsignacionDTO> asignaciones = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idMedico);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    asignaciones.add(mapearDTO(rs));
                }
            }
        }

        return asignaciones;
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "UPDATE asignaciones SET activo = FALSE WHERE id = ? AND activo = TRUE";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private AsignacionDTO mapearDTO(ResultSet rs) throws SQLException {
        AsignacionDTO dto = new AsignacionDTO();

        dto.setId(rs.getInt("asignacion_id"));
        dto.setIdMedico(rs.getInt("medico_id"));
        dto.setNombreMedico(rs.getString("medico_nombre"));
        dto.setApellidoMedico(rs.getString("medico_apellido"));
        dto.setEspecialidad(rs.getString("especialidad"));
        dto.setIdPaciente(rs.getInt("paciente_id"));
        dto.setNombrePaciente(rs.getString("paciente_nombre"));
        dto.setApellidoPaciente(rs.getString("paciente_apellido"));
        dto.setDniPaciente(rs.getString("paciente_dni"));
        dto.setActivo(rs.getBoolean("asignacion_activa"));

        return dto;
    }

    public Optional<AsignacionDTO> buscarActivaPorPaciente(int idPaciente) throws SQLException {
        String sql = """
            SELECT a.id, a.activo,
                   m.id AS id_medico, m.nombre AS nombre_medico,
                   m.apellido AS apellido_medico, m.especialidad,
                   p.id AS id_paciente, p.nombre AS nombre_paciente,
                   p.apellido AS apellido_paciente, p.dni AS dni_paciente
            FROM asignaciones a
            INNER JOIN medicos m ON a.id_medico = m.id
            INNER JOIN pacientes p ON a.id_paciente = p.id
            WHERE a.id_paciente = ? AND a.activo = TRUE
            """;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idPaciente);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    AsignacionDTO dto = new AsignacionDTO();

                    dto.setId(rs.getInt("id"));
                    dto.setIdMedico(rs.getInt("id_medico"));
                    dto.setNombreMedico(rs.getString("nombre_medico"));
                    dto.setApellidoMedico(rs.getString("apellido_medico"));
                    dto.setEspecialidad(rs.getString("especialidad"));
                    dto.setIdPaciente(rs.getInt("id_paciente"));
                    dto.setNombrePaciente(rs.getString("nombre_paciente"));
                    dto.setApellidoPaciente(rs.getString("apellido_paciente"));
                    dto.setDniPaciente(rs.getString("dni_paciente"));
                    dto.setActivo(rs.getBoolean("activo"));

                    return Optional.of(dto);
                }
            }
        }

        return Optional.empty();
    }

}
