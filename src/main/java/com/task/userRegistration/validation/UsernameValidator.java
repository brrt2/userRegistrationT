package com.task.userRegistration.validation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.passay.*;
import org.springframework.context.annotation.PropertySource;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;



@PropertySource("classpath:messages.properties")
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    Logger logger = LogManager.getLogger(UsernameValidator.class);

    @Override
    public void initialize(ValidUsername validUsername) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        Properties props = new Properties();
        try {
            props.load(new FileInputStream("messages.properties"));
        } catch (IOException e) {
            logger.error("Exception occured : ",e);
        }
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


//        context.buildConstraintViolationWithTemplate(
//                Joiner.on("\n").join(validator.getMessages(result)))
//                .addConstraintViolation();
        return false;
    }
}
