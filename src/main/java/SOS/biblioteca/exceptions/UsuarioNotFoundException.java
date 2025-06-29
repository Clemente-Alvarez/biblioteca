package SOS.biblioteca.exceptions;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(int id){
        super("Usuario con Id " + id + " no existe.");
    }
}
