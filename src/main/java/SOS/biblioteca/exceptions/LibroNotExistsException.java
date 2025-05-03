package SOS.biblioteca.exceptions;

public class LibroNotExistsException extends RuntimeException {

    public LibroNotExistsException (int id){
        super("Libro con Id " + id + "no existe.");
    }
}
