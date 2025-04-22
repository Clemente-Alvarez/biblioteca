package SOS.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
