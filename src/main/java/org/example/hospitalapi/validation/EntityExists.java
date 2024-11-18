package org.example.hospitalapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EntityExistsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityExists {
    String message() default "A entidade não existe"; // Mensagem padrão

    Class<?>[] groups() default {}; // Grupos de validação (opcional)

    Class<? extends Payload>[] payload() default {}; // Payloads adicionais (opcional)

    Class<?> entity(); // Tipo da entidade a ser verificada

    String fieldName() default "id"; // Nome do campo usado na verificação (padrão: "id")

}
