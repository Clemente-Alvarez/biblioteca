package SOS.biblioteca.exceptions;

public class PrestamoExistsException extends RuntimeException {
    public PrestamoExistsException(int id){
        super("Prestamo con Id " + id + " ya existe.");
    }
}
