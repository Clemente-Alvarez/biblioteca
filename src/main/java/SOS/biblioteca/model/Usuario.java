package SOS.biblioteca.model;

import SOS.biblioteca.model.*;
import java.lang.annotation.Inherited;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Entity
@Table(name ="Usuarios")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Usuario extends RepresentationModel<Usuario>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Nombre del usuario", required = true, example = "2217")
	@NotBlank(message = "La matricula es obligatoria y no puede ser null ni vacio")
    private String matricula;

    @Schema(description = "Nombre del usuario", required = true, example = "Marcos")
	@NotBlank(message = "El nombre es obligatorio y no puede ser null ni vacio")
    private String nombre;

    @Schema(description = "Fecha de nacimiento", required = true, example = "13-12-2004")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaNacimiento;

    @Schema(description = "Correo del usuario", required = true, example = "marcos@menudocorreo.com")
	@NotBlank(message = "El correo es obligatorio y no puede ser null ni vacio")
    private String correo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate penalizacion;
    
}
