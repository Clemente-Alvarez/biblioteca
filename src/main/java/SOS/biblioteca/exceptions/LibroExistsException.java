package SOS.biblioteca.exceptions;

public class LibroExistsException extends RuntimeException {

    public LibroExistsException (String isbn){
        super("Libro con Id " + isbn + "ya existe.");
    }
}
