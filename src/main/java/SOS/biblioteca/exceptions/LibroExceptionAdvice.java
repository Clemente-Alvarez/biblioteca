package SOS.biblioteca.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LibroExceptionAdvice {
    @ExceptionHandler(LibroNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage libroNotFoundHandler(LibroNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(LibroExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorMessage libroExistsHandler(LibroExistsException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(LibroIdNotNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage libroIdNotNullHandler(LibroIdNotNullException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(EjemplaresDisponiblesMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage ejemplaresDisponiblesMismatchHandler(EjemplaresDisponiblesMismatchException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(DisponiblesInsuficientesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage disponiblesInsuficientesMismatchHandler(DisponiblesInsuficientesException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(EjemplarPrestadoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage ejemplarPrestadoHandler(EjemplarPrestadoException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    
}
