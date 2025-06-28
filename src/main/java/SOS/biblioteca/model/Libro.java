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
    private Integer id;

    @Schema(description = "Titulo del libro", required = true, example = "Las aventuras de Don pepito")
    @NotNull(message = "El titulo es obligatorio y no puede ser null ni estar vacío")
    private String titulo;

    @Schema(description = "isbn", required = true, example = "Las aventuras de Don pepito")
    @NotNull(message = "El isbn es obligatorio y no puede ser null ni estar vacío")
    private String isbn;

    @Schema(description = "Autor del libro", required = false, example = "Leonardo DiCaprio")
    private String autor;

    @Schema(description = " Edición del libro", required = false, example = "Primera edición")
    private String edicion;

    @Schema(description = "Editorial del libro", required = false, example = "Casablanca")
    private String editorial;

    @Schema(description = "cantidad de ejemplares", required = true, example = "3")
    @NotNull(message = "La cantidad de ejemplares es obligatorio y no puede ser null")
    private Integer ejemplares;

    @Schema(description = "ejemplares disponibles", required = false, example = "2")
    private Integer disponibles;
    

    //@Transient
    //@JsonInclude(JsonInclude.Include.NON_NULL) // Solo mostrar si no es null
    //private Set<EntityModel<Ejemplar>> listaEjemplares;

}
