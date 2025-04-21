import java.beans.Transient;
import java.lang.annotation.Inherited;

import javax.annotation.processing.Generated;

@Entity
@Table(name ="garajes")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Libro extends RepresentationModel<Libro> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int libro_id;
    @NotNull(message = "El nombre es obligatorio y no puede ser null")
    private String titulo;
    private String isbn;
    private String autor;   
}
