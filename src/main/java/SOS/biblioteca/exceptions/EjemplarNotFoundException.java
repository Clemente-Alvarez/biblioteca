package SOS.biblioteca.exceptions;

public class EjemplarNotFoundException extends RuntimeException {
    public EjemplarNotFoundException(int id){
        super("Ejemplar con Id " + id + "no existe.");
    }
    
}
