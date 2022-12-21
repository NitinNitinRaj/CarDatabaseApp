package com.nr.cardatabase;

import com.nr.cardatabase.exception.EntityNotFoundExceptionHandler;
import com.nr.cardatabase.exception.ErrorResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler
  extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EntityNotFoundExceptionHandler.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(
    EntityNotFoundExceptionHandler ex
  ) {
    return new ResponseEntity<>(
      new ErrorResponse(List.of(ex.getMessage())),
      HttpStatus.NOT_FOUND
    );
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ResponseEntity<ErrorResponse> handleDataAccessException(
    EmptyResultDataAccessException ex
  ) {
    return new ResponseEntity<>(
      new ErrorResponse(List.of("Cannot delete non-existing resource")),
      HttpStatus.NOT_FOUND
    );
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
    DataIntegrityViolationException ex
  ) {
    return new ResponseEntity<>(
      new ErrorResponse(
        List.of("Data Integrity Violation: we cannot process your request.")
      ),
      HttpStatus.BAD_REQUEST
    );
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    List<String> errorMessage = ex
      .getBindingResult()
      .getAllErrors()
      .stream()
      .map(err -> err.getDefaultMessage())
      .collect(Collectors.toList());
    return new ResponseEntity<>(
      new ErrorResponse(errorMessage),
      HttpStatus.BAD_REQUEST
    );
  }
}
