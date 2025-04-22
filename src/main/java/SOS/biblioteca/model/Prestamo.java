package SOS.biblioteca.model;
@Entity
@Table(name ="Prestamos")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Prestamo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Prestamo_id;
    private String fecha_inicio;
    private String echa_debolucion;
    private Boolean debuelto;
}
