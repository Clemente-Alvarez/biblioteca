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
    ErrorMessage usuarioExistsHandler(LibroExistsException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    
}
