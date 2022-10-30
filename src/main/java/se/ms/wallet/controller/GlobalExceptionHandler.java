package se.ms.wallet.controller;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import se.ms.wallet.enums.TransactionType;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Object> handleHttpServerErrorExceptions(final HttpServerErrorException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getStatusText());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex) {
        final List<String> errors = ex.getConstraintViolations().stream().map(violation ->
                violation.getPropertyPath().toString() + ": " + violation.getMessage()).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<Object> handleConversionFailedException(final ConversionFailedException ex) {
        if (TransactionType.class.getName().equals(ex.getTargetType().getName())) {
            final String msg = String.format("Invalid transaction type! Valid transaction types are: %s and %s",
                    TransactionType.CREDIT, TransactionType.DEBIT);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
