package SOS.biblioteca.exceptions;

public class PrestamoNotFoundException extends RuntimeException {
    public PrestamoNotFoundException(int id){
        super("Prestamo con Id " + id + " no existe.");
    }
}
