package SOS.biblioteca.exceptions;

public class PrestamoYaDevueltoException extends RuntimeException{
    public PrestamoYaDevueltoException(Integer id){
        super("El prestamo con id: " + id + " ya ha sido devuelto");
    }
}
