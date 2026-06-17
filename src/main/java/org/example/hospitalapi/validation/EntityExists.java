package org.example.hospitalapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = EntityExistsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityExists {

  String message() default "A entidade não existe";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  Class<?> entity();

  String fieldName() default "id";

}
