package SOS.biblioteca.exceptions;

public class LibroNotFoundException extends RuntimeException {

    public LibroNotFoundException (int id){
        super("Libro con Id " + id + " no existe.");
    }
}
