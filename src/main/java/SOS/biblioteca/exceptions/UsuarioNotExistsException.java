package SOS.biblioteca.exceptions;

public class UsuarioNotExistsException extends RuntimeException {
    public UsuarioNotExistsException(int id){
        super("Usuario con Id " + id + "no existe.");
    }
}
