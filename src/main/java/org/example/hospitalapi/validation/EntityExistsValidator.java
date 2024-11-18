package org.example.hospitalapi.validation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EntityExistsValidator implements ConstraintValidator<EntityExists, Object> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entityClass;
    private String fieldName;

    @Override
    public void initialize(EntityExists constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Deixe que @NotNull cuide disso, se necessário
        }

        String query = String.format("SELECT COUNT(e) > 0 FROM %s e WHERE e.%s = :value",
                entityClass.getSimpleName(),
                fieldName);

        Boolean exists = entityManager.createQuery(query, Boolean.class)
                .setParameter("value", value)
                .getSingleResult();

        return exists;
    }
}
