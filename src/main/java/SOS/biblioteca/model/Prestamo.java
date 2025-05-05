package SOS.biblioteca.model;

import java.beans.Transient;
import java.lang.annotation.Inherited;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Id del préstamo", required = true, example = "1")
    private int prestamoId;

    @Schema(description = "Fecha de inicio del préstamo", required = false, example = "12-01-2005")
    private String fechaInicio;

    @Schema(description = "Fecha de devolución del préstamo", required = false, example = "26-01-2005")
    private String fechaDevolucion;

    @Schema(description = "Autor del libro", required = false, example = "true")
    private Boolean devuelto;
}
