package de.hska.twitterklon.api.transferobjects.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameValidator implements ConstraintValidator<ValidUserName, String> {

    @Override
    public void initialize(ValidUserName validUserName) {
        // nothing
    }

    @Override
    public boolean isValid(String userName, ConstraintValidatorContext context) {
        return userName != null && userName.matches("[A-Za-z0-9$.+!*'(),_-]+");
    }
}
