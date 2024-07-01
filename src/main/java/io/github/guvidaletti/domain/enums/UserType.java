package io.github.guvidaletti.domain.enums;


public enum UserType {
  ADMIN("ADMIN"), USER("USER"), STUDENT("STUDENT");

  private final String type;

  UserType(String string) {
    type = string;
  }

  @Override
  public String toString() {
    return type;
  }
}
