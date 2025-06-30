package SOS.biblioteca.exceptions;

public class DevolucionAmpliacionSimultaneaException extends RuntimeException{
    public DevolucionAmpliacionSimultaneaException(Integer id){
        super("Se esta intentando hacer amplaicion y devolucion a la vez de l prestamo " + id);
    }
}
