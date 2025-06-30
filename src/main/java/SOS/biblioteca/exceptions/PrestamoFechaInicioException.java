package SOS.biblioteca.exceptions;

public class PrestamoFechaInicioException extends RuntimeException {
    public PrestamoFechaInicioException(){
        super("No se puede modificar la fecha inicio de un prestamo");
    }
}
    

