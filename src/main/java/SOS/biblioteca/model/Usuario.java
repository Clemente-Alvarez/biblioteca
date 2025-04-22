package SOS.biblioteca.model;

import javax.annotation.processing.Generated;

@Entity
@Table(name ="Usuarios")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int matricula;
    private String nombre;
    private String fecha_naciemnto;
    private String correo;
    private String penalizacion;
    
}
