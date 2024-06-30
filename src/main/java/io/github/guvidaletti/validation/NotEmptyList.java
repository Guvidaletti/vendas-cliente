package io.github.guvidaletti.validation;

import io.github.guvidaletti.validation.impl.NotEmptyListImpl;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListImpl.class)
public @interface NotEmptyList {

  String message() default "{campo.lista.vazia}";

  Class<?>[] groups() default {};

  Class<?>[] payload() default {};
}
