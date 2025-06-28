package SOS.biblioteca.exceptions;

public class LibroExistsException extends RuntimeException {

    public LibroExistsException (int isbn){
        super("Libro con Id " + isbn + "ya existe.");
    }
}
