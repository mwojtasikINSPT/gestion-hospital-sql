package prog2.parcial.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//Relación Paciente ↔ Cama (1:1)

public class Reserva {
    private int id;
    private Cama cama;
    private Paciente paciente;
    private boolean activo;
}

