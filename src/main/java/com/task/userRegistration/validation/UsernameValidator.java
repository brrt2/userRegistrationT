package com.task.userRegistration.validation;

import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;


public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {



    @Override
    public void initialize(ValidUsername validUsername) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        Properties props = new Properties();

        props.put("ALLOWED_MATCH","No special characters are allowed ! ");
        props.put("TOO_SHORT","From Program must be at least %1$s characters in length.");
        props.put("INSUFFICIENT_UPPERCASE","It must contain at least %1$s uppercase characters.");
        props.put("INSUFFICIENT_LOWERCASE","It must contain at least %1$s lowercase characters.");

        MessageResolver resolver = new PropertiesMessageResolver(props);

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(5,100),
                new AllowedRegexRule("^[A-Za-z0-9]+$")));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                validator.getMessages(result).get(0))
                .addConstraintViolation();

        return false;
    }
}
