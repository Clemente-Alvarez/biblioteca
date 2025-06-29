package SOS.biblioteca.model;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "prestamos")
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo extends RepresentationModel<Prestamo>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne // Cada instancia Prestamo va a tener un libro
    @JoinColumn(name = "libro_id")
    private Libro libro;

    @ManyToOne // Cada instancia Prestamo va a tener un usuario
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaPrestamo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaDevolucion;
    private Boolean devuelto;
}
