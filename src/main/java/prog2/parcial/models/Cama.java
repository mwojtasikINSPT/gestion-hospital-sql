package prog2.parcial.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cama {
    private String codigo;
    private int piso;
    private EstadoCama estado;
    private boolean activo;
}
