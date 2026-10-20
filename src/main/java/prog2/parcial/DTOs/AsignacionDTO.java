package prog2.parcial.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionDTO {

    private int id;
    private boolean activo;
    private int idMedico;
    private String nombreMedico;
    private String apellidoMedico;
    private String especialidad;
    private int idPaciente;
    private String nombrePaciente;
    private String apellidoPaciente;
    private String dniPaciente;
}
