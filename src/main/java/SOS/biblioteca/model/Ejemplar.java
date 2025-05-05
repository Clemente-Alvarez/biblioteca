package SOS.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name ="Ejemplar")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ejemplar extends RepresentationModel<Ejemplar>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id del ejemplar", required = false, example = "23")
    private int id;

    @Schema(description = "Id del libro al que corresponde el ejemplar", required = true, example = "Vuelta al mundo en 80 días")
    private int libroId;

    @Schema(description = "Estado del ejemplar", required = false, example = "prestado")
    private String estado; // disponible, prestado o baja

    
}
