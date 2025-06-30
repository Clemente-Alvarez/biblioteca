package SOS.biblioteca.exceptions;

public class PrestamoTimeLimitExceededException extends RuntimeException{
    public PrestamoTimeLimitExceededException(int id){
        super("Usuario con id " +id +"  ha devuelto tarde un ejemplar");
    }
}
