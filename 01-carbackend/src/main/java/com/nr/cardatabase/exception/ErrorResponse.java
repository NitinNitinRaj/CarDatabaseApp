package com.nr.cardatabase.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {

  @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime date;

  private List<String> message;

  public ErrorResponse(List<String> message) {
    this.message = message;
    this.date = LocalDateTime.now();
  }
}
