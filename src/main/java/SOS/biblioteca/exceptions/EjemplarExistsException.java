package SOS.biblioteca.exceptions;

public class EjemplarExistsException extends RuntimeException {
    public EjemplarExistsException(int id){
        super("Ejemplar con Id " + id + "ya existe.");
    }
    
}
