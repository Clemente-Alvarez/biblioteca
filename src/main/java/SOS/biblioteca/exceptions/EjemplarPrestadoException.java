package SOS.biblioteca.exceptions;

public class EjemplarPrestadoException extends RuntimeException {
    public EjemplarPrestadoException(int id){
        super("Libro con Id " + id + " tiene un ejemplar que esta siendo prestado");
    }
}
