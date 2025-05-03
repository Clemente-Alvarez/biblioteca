package SOS.biblioteca.exceptions;

public class PrestamoNotExistsException extends RuntimeException {
    public PrestamoNotExistsException(int id){
        super("Prestamo con Id " + id + "no existe.");
    }
}
