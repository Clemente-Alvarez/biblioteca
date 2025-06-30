package SOS.biblioteca.exceptions;

public class PrestamoLibroIdException extends RuntimeException {
    public PrestamoLibroIdException(){
        super("No se puede modificar el libroId de un prestamo");
    }
}
