package SOS.biblioteca.exceptions;

public class UsuarioExistsException extends RuntimeException {
    public UsuarioExistsException(String correo){
        super("Usuario con Id " + correo + "ya existe.");
    }
}
