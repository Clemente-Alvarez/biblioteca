package SOS.biblioteca.model;

import java.beans.Transient;
import java.lang.annotation.Inherited;

import javax.annotation.processing.Generated;

@Entity
@Table(name ="Libros")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Libro extends RepresentationModel<Libro> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int libro_id;
    private String titulo;
    @NotNull(message = "El isbn es obligatorio y no puede ser null")
    private String isbn;
    private String autor;
    private String edicion;
    private String editorial;
    private Boolean estado; //true si activo false en caso contrario 
}
