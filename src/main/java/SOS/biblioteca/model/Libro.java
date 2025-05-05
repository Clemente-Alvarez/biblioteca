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

    @Schema(description = "Autor del libro", required = false, example = "Leonardo DiCaprio")
    private String autor;

    @Schema(description = " Edición del libro", required = false, example = "Primera edición")
    private String edicion;

    @Schema(description = "Editorial del libro", required = false, example = "Casablanca")
    private String editorial;

    //@Transient
    //@JsonInclude(JsonInclude.Include.NON_NULL) // Solo mostrar si no es null
    //private Set<EntityModel<Ejemplar>> listaEjemplares;

}
