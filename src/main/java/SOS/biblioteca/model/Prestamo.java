package SOS.biblioteca.model;

import java.beans.Transient;
import java.lang.annotation.Inherited;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;
import lombok.*;

@Entity
@Table(name ="Prestamos")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Prestamo extends RepresentationModel<Prestamo>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prestamoId;
    private String fechaInicio;
    private String fechaDevolucion;
    private Boolean devuelto;
}
