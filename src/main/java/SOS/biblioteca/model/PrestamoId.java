package SOS.biblioteca.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoId  {
    @NotNull(message = "El empleadoId es obligatorio y no puede ser null")
    private Integer libroId;
    @NotNull(message = "El empleadoId es obligatorio y no puede ser null")
    private Integer usuarioId;
    @Column(insertable=false,updatable=false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy")
    private LocalDate fechaInicio;
    @Column(insertable=false,updatable=false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy")
    private LocalDate fechaFin;
    @Column(insertable=false,updatable=false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy")
    private LocalDate fechaDevolucion;
    @Column(insertable=false,updatable=false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy")
    private LocalDate fechaPedidoAmpliacion;
}