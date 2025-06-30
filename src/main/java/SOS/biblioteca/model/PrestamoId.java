package SOS.biblioteca.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoId  {
    @NotNull(message = "El libroId es obligatorio y no puede ser null")
    private Integer libroId;
    @NotNull(message = "El usuarioId es obligatorio y no puede ser null")
    private Integer usuarioId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull(message = "ELa fecha de inicio de prestamo es obligatorio y no puede ser null")
    private LocalDate fechaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaDevolucion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaPedidoAmpliacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaAmpliacion;
}