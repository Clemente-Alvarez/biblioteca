package SOS.biblioteca.exceptions;

public class LibroExistsException extends RuntimeException {

    public LibroExistsException (int id){
        super("Libro con Id " + id + "ya existe.");
    }
}
