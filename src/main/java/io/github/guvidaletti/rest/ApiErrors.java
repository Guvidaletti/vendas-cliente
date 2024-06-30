package io.github.guvidaletti.rest;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.util.Collections;
import java.util.List;


public class ApiErrors {

  @Getter
  private List<String> errors;

  public ApiErrors(String message) {
    this.errors = Collections.singletonList(message);
  }

  public ApiErrors(List<String> errors) {
    this.errors = errors;
  }
}
