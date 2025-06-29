package SOS.biblioteca.exceptions;

public class EjemplarNotAvailableException extends RuntimeException{
    public EjemplarNotAvailableException(int id){
        super("Libro con Id " + id + "no tiene ejemplares disponibles.");
    }
}
