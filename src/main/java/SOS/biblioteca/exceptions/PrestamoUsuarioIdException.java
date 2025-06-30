package SOS.biblioteca.exceptions;

public class PrestamoUsuarioIdException extends RuntimeException {
    public PrestamoUsuarioIdException(){
        super("No se puede modificar el usuarioId de un prestamo");
    }
}
