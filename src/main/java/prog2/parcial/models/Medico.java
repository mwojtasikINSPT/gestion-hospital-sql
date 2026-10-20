package prog2.parcial.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medico {
    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    private String especialidad;
    private boolean activo;
}
