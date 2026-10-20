package prog2.parcial.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private int id;
    private boolean activo;
    private String codigoCama;
    private int piso;
    private int idPaciente;
    private String nombrePaciente;
    private String apellidoPaciente;
    private String dniPaciente;
}
