
@Entity
@Table(name ="Ejemplares")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ejemplar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ejemplar_id;
    private String estado; // disponible, prestado o baja
}
