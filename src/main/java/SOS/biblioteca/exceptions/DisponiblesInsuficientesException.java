package SOS.biblioteca.exceptions;

public class DisponiblesInsuficientesException extends RuntimeException{
    public DisponiblesInsuficientesException (){
        super("No se pueden quitar mas ejemplares de los disponibles");
    }
}
