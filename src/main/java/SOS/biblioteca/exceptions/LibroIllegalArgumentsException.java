package SOS.biblioteca.exceptions;

public class LibroIllegalArgumentsException extends RuntimeException{
    public LibroIllegalArgumentsException (){
        super("El libro pasado tiene argumentos invalidos");
    }
}
