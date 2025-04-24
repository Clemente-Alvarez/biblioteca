package SOS.biblioteca.exceptions;

public class UsuarioExistsException extends RuntimeException {
    public UsuarioExistsException(int id){
        super("Usuario con Id " + id + "ya existe.");
    }
}
