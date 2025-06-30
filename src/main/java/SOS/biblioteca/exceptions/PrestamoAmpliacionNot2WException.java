package SOS.biblioteca.exceptions;

public class PrestamoAmpliacionNot2WException extends RuntimeException{
    public PrestamoAmpliacionNot2WException(){
        super("Una ampliacion tiene que ser de dos semanas");
    }
}
