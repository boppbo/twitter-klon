package de.hska.twitterklon.api.transferobjects.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class ContentValidator implements ConstraintValidator<ValidContent, String> {

    @Override
    public void initialize(ValidContent validContent) {
        // do nothing
    }

    @Override
    public boolean isValid(String post, ConstraintValidatorContext constraintValidatorContext) {
        return !StringUtils.isEmpty(post) && post.length() <= 140;
    }
}