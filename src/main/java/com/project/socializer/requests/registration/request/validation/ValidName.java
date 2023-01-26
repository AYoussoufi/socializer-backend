package com.project.socializer.requests.registration.request.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NameValidator.class)
public @interface ValidName {

    String message() default "Invalid name please use a real name.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
