package SOS.biblioteca.exceptions;

public class EjemplarNotAvailableException extends RuntimeException{
    public EjemplarNotAvailableException(int id){
        super("Ejemplar con Id " + id + "no esta disponible.");
    }
}
