package SOS.biblioteca.exceptions;

public class LibroExistsException extends RuntimeException {

    public LibroExistsException (String isbn){
        super("Libro con ISBN " + isbn + " ya existe.");
    }
}
