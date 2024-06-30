package io.github.guvidaletti.validation.impl;

import io.github.guvidaletti.validation.NotEmptyList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NotEmptyListImpl implements ConstraintValidator<NotEmptyList, List> {
  @Override
  public void initialize(NotEmptyList constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
    return list != null && !list.isEmpty();
  }
}
