package SOS.biblioteca.exceptions;

public class PedidoAmpliacionSinFechaException extends RuntimeException{
    public PedidoAmpliacionSinFechaException(Integer id){
        super("Ampliacion sin especificar fecha en prestamo con id: " + id);
    }
}
