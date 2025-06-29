package SOS.biblioteca.exceptions;

public class PrestamoTimeLimitExceededException extends RuntimeException{
    public PrestamoTimeLimitExceededException(int id){
        super("Usuario con matricula " +id +"  ha devuelto tarde un ejemplar");
    }
}
