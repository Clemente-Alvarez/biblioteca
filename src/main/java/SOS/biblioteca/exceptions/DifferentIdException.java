package SOS.biblioteca.exceptions;

public class DifferentIdException extends RuntimeException{
    public DifferentIdException(int idLibro, int idEjemplar){
        super("El Id del libro: " + idLibro + "no coincide con el Id del ejemplar" + idEjemplar);
    }
}
