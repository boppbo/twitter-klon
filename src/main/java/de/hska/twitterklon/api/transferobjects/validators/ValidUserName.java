package de.hska.twitterklon.api.transferobjects.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNameValidator.class)
public @interface ValidUserName {
    String message() default "userName.not.valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
