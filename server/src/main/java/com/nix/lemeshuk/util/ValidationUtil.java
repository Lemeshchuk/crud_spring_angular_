package com.nix.lemeshuk.util;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class ValidationUtil {

    public <T> boolean isValid(T entity) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(entity);

        return constraintViolationSet.isEmpty();
    }
}
