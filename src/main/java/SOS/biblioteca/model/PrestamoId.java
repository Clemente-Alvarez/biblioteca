package SOS.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable // Indica que esta clase no es una entidad, sino que se incrusta en otra entidad
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoId {
    @NotNull(message = "El empleadoId es obligatorio y no puede ser null")
    private int ejemplarId;
    private int usuarioId;
    private String fechaPrestamo;
    @Column(insertable=false,updatable=false)private String fechaDevolucion;
    private String fechaPedidoAmpliacion;
}