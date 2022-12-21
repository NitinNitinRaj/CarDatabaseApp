package com.nr.cardatabase.exception;

public class EntityNotFoundExceptionHandler extends RuntimeException {

  public EntityNotFoundExceptionHandler(String id, Class<?> entity) {
    super(
      "The " +
      entity.getSimpleName().toLowerCase() +
      " with id '" +
      id +
      "' does not exist in our records."
    );
  }
}
