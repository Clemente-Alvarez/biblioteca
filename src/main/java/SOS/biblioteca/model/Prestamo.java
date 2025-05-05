package SOS.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "garaje_empleado")
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {

    @EmbeddedId // Indica GarajeEmpleadoId es la clave primaria de esta entidad
    private PrestamoId id;

    @ManyToOne // Cada instancia GarajeEmpleado va a tener un empleado
    @MapsId("ejemplarId") // Asocia la clave primaria al campo correspondiente
    @JoinColumn(name = "ejemplar_id")
    private Ejemplar ejemplar;

    @ManyToOne // Cada instancia GarajeEmpleado va a tener un garaje
    @MapsId("usuarioId") // Asocia la clave primaria al campo correspondiente
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
