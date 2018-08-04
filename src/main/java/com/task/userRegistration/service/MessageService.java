package com.task.userRegistration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;

@Service("messageService")
public class MessageService {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key, Object... parameters) {
        final Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, parameters, locale);
    }

    public void addConstraintViolation(ConstraintValidatorContext context, String key, Object... parameters) {
        if (key.startsWith("{") && key.endsWith("}")) {
            key = key.substring(1, key.length() - 1);
        }
        String message = getMessage(key, parameters);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    public void addDefaultConstraintViolation(ConstraintValidatorContext context, Object... parameters) {
        String defaultTemplate = context.getDefaultConstraintMessageTemplate();
        addConstraintViolation(context, defaultTemplate, parameters);
    }

}