package SOS.biblioteca.exceptions;

public class EjemplaresDisponiblesMismatchException extends RuntimeException{
    public EjemplaresDisponiblesMismatchException(){
        super("El numero de ejemplares y disponibles deben ser iguales al crear un libro");
    }
}
