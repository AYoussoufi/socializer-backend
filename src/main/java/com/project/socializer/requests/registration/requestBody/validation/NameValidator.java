package com.project.socializer.requests.registration.requestBody.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName,String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.matches("^[a-zA-Z]{1,20}\\S")){
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Invalid name please use a real name.").addConstraintViolation();
        return false;
    }
}
