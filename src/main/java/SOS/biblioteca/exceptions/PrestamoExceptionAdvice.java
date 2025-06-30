package SOS.biblioteca.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PrestamoExceptionAdvice {
  @ExceptionHandler(PrestamoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage prestamoNotFoundHandler(PrestamoNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PrestamoLibroIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage prestamoLibroIdHandler(PrestamoLibroIdException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PrestamoUsuarioIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage prestamoUsuarioIdHandler(PrestamoUsuarioIdException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PrestamoFechaInicioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage prestamoFechaInicioHandler(PrestamoFechaInicioException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PrestamoYaDevueltoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorMessage prestamoYaDevueltoHandler(PrestamoYaDevueltoException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(DevolucionAmpliacionSimultaneaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage prestamoDevAmplHandler(DevolucionAmpliacionSimultaneaException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PedidoAmpliacionSinFechaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage prestamoAmplSinFechaHandler(PedidoAmpliacionSinFechaException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PrestamoTimeLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage prestamoTimeLimitExceededHandler(PrestamoTimeLimitExceededException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PrestamoAmpliacionNot2WException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage prestamoAmplNot2WHandler(PrestamoAmpliacionNot2WException ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
