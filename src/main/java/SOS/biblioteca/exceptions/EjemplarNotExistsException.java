package SOS.biblioteca.exceptions;

public class EjemplarNotExistsException extends RuntimeException {
    public EjemplarNotExistsException(int id){
        super("Ejemplar con Id " + id + "no existe.");
    }
    
}
