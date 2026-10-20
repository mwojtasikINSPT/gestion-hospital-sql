package prog2.parcial.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//Relación Médico → Pacientes (1:N)
public class Asignacion {

    private int id;
    private boolean activo;
    private Medico medico;
    private Paciente paciente;
}
