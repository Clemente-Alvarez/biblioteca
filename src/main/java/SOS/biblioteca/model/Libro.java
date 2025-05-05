package SOS.biblioteca.model;

import java.beans.Transient;
import java.lang.annotation.Inherited;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.hateoas.RepresentationModel;
import io.swagger.v3.oas.annotations.media.Schema;
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
    private int id;

    @Schema(description = "Titulo del libro", required = true, example = "Las aventuras de Don pepito")
    @NotNull(message = "El titulo es obligatorio y no puede ser null")
    private String titulo;

    @Schema(description = "isbn", required = true, example = "Las aventuras de Don pepito")
    @NotNull(message = "El isbn es obligatorio y no puede ser null")
    private String isbn;

    @Schema(description = "Titulo del libro", required = false, example = "Las aventuras de Don pepito")
    private String autor;

    private String edicion;
    private String editorial;
    private String estado; 

    //@Transient
    //@JsonInclude(JsonInclude.Include.NON_NULL) // Solo mostrar si no es null
    //private Set<EntityModel<Ejemplar>> listaEjemplares;

}
