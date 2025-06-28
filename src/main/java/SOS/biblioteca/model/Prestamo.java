package SOS.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "prestamos")
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {

    @EmbeddedId // Indica PrestamoId es la clave primaria de esta entidad
    private PrestamoId id;

    @ManyToOne // Cada instancia Prestamo va a tener un libro
    @MapsId("libroId") // Asocia la clave primaria al campo correspondiente
    @JoinColumn(name = "libro_id")
    private Libro libro;

    @ManyToOne // Cada instancia Prestamo va a tener un usuario
    @MapsId("usuarioId") // Asocia la clave primaria al campo correspondiente
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private String fechaPrestamo;
    private String fechaDevolucion;
    private boolean devuelto;
}
