package SOS.biblioteca.exceptions;

public class UserWithPenaltyException extends RuntimeException{
    public UserWithPenaltyException(int id){
        super("Usuario con id " +id +" tiene una penalización por entregar con retraso");
    }
}
