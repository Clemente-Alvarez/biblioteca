package SOS.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioActivity extends RepresentationModel<UsuarioActivity>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String matricula;
    private String nombre;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaNacimiento;
    private String correo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate penalizacion;
    
    @OneToMany
    private List<Prestamo> listaPrestamosActuales;
    @OneToMany
    private List<Prestamo> listaPrestamosDevueltos;
}
