package com.rootuss.BloodDonationCentre.exception;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = BloodDonationCentreException.class)
    public ResponseEntity<ErrorInfo> handleException(BloodDonationCentreException exception) {

        HttpStatus httpStatus = exception.getError().getHttpStatus();
        String message = exception.getError().getMessage();

        if (message != null) {
            return ResponseEntity.status(httpStatus)
                    .body(new ErrorInfo(exception.getError().getMessage()));
        } else {
            return ResponseEntity.status(httpStatus).build();
        }
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorInfo> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errors = result.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ValidationErrorInfo(errors));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorInfo> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> result = ex.getConstraintViolations();
        List<String> errors = result.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ValidationErrorInfo(errors));
    }

    @ExceptionHandler(value = TypeMismatchException.class)
    public ResponseEntity<ErrorInfo> handleTypeMismatchException(TypeMismatchException ex) {
        Class<?> requiredType = ex.getRequiredType();

        if (requiredType == LocalDate.class) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorInfo("Date is not in YYYY-MM-DD format"));
        } else if (requiredType == Integer.class || requiredType == Long.class) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorInfo("Passed number is invalid"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorInfo> handleDateTimeParseException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof DateTimeParseException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorInfo("Date is not in YYYY-MM-DD format"));
        } else if (cause instanceof JsonParseException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorInfo("Invalid JSON format"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
