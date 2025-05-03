package SOS.biblioteca.model;

import java.beans.Transient;
import java.lang.annotation.Inherited;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;
import lombok.*;

@Entity
@Table(name ="Libros")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Libro extends RepresentationModel<Libro> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int libroId;
    @NotNull(message = "El titulo es obligatorio y no puede ser null")
    private String titulo;
    @NotNull(message = "El isbn es obligatorio y no puede ser null")
    private String isbn;
    private String autor;
    private String edicion;
    private String editorial;
    private String estado; 
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL) // Solo mostrar si no es null
    private Set<EntityModel<Ejemplar>> listaEjemplares;

}
