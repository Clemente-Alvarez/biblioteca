package SOS.biblioteca.exceptions;

public class LibroExistsException extends RuntimeException {

    public LibroExistsException (String s){
        super("Libro con titulo " + s + "ya existe.");
    }
}
