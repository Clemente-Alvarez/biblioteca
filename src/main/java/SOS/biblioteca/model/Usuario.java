package SOS.biblioteca.model;

import SOS.biblioteca.model.*;
import java.lang.annotation.Inherited;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

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
    @Schema(description = "Id del empleado", required = true, example = "1")
    private Integer matricula;

    @Schema(description = "Nombre del usuario", required = true, example = "Marcos")
	@NotNull(message = "El nombre es obligatorio y no puede ser null")
    private String nombre;

    @Schema(description = "Fecha de nacimiento", required = true, example = "13-12-2004")
	@NotNull(message = "La fehca de nacimiento es obligatorio y no puede ser null")
    private String fechaNacimiento;

    @Schema(description = "Correo del usuario", required = true, example = "marcos@menudocorreo.com")
	@NotNull(message = "El correo es obligatorio y no puede ser null")
    private String correo;

    private String penalizacion;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL) // Solo mostrar si no es null
    private Set<EntityModel<Ejemplar>> listaPrestamosActuales;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL) // Solo mostrar si no es null
    private Set<EntityModel<Ejemplar>> listaPrestamosDevueltos;
    
}
