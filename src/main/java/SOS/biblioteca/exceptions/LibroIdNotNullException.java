package SOS.biblioteca.exceptions;

public class LibroIdNotNullException extends RuntimeException{
    public LibroIdNotNullException (){
        super("El campo id no se debe proporcionar en el cuerpo del POST");
    }
}
