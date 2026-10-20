
package prog2.parcial.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import prog2.parcial.models.EstadoCama;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CamaDTO {

    private String codigo;
    private int piso;
    private EstadoCama estado;
    private boolean activo;
}

