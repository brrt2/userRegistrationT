package com.task.userRegistration.model;

import com.task.userRegistration.model.User;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class UserValidationTest {

    private static final String CORRECT_PASSWORD = "January1";
    private static final String CORRECT_USERNAME = "bartek";

    private static ValidatorFactory factory;
    private static Validator validator;
    private User user;
    private Set<ConstraintViolation<User>> constraintViolations;

    @BeforeClass
    public static void setUpTestClass() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterClass
    public static void close() {
        factory.close();
    }

    @BeforeMethod
    public void setUpEachTest() {

        user = new User();

    }

    @DataProvider
    public Object [] provideIncorrectPasswords() {
        return new Object[] {
                "abc",
                "",
                "-",
                " "

        };
    }

    @DataProvider
    public Object [] provideIncorrectUsernames() {
        return new Object[] {
                "@user",
                "username@#$",
                "-sample-",
                "Tom&Jerry"

        };
    }

    @DataProvider
    public Object [][] provideCorrectPasswordsAndUsernames() {
        return new Object[][]{
                {"bartek","Administrator1"},
                {"sampleUser","January1"},
                {"sampleUser2","February2"},
                {"sampleUser3","September9"},
                {"sampleUser4","December12"},

        };
    }

    @DataProvider
    public Object [][] provideIncorrectPasswordsAndUsernames() {
        return new Object[][]{
                {"bartek@","letmein"},
                {"sampleUser!","password"},
                {"Bonnie&Clyde","February"},
                {"JohnWayne12!!","september9"},
                {"*sampleUser*","december"},

        };
    }

    @Test(dataProvider = "provideIncorrectPasswords")
    public void shouldFindOneConstraintValidationWhenPasswordIsIncorrect(String password) {

        // Arrange
        user.setUsername(CORRECT_USERNAME);
        user.setPassword(password);

        // Act
        constraintViolations = validator.validate(user);

        // Assert
        Assert.assertEquals(constraintViolations.size(), 1);
    }

    @Test(dataProvider = "provideIncorrectUsernames")
    public void shouldFindOneConstraintValidationWhenIncorrectUsernameIsProvided(String username) {

        // Arrange
        user.setUsername(username);
        user.setPassword(CORRECT_PASSWORD);

        // Act
        constraintViolations = validator.validate(user);

        // Assert
        Assert.assertEquals(constraintViolations.size(), 1);
    }

    @Test(dataProvider = "provideIncorrectPasswordsAndUsernames")
    public void shouldSignalTwoConstraintValidationsWhenIncorrectUsernameAndPasswordAreProvided(String username,
                                                                                                    String password) {
        // Arrange
        user.setUsername(username);
        user.setPassword(password);

        // Act
        constraintViolations = validator.validate(user);

        // Assert
        Assert.assertEquals(constraintViolations.size(), 2);
    }

    @Test(dataProvider = "provideCorrectPasswordsAndUsernames")
    public void shouldFindNoConstraintValidationsWhenPasswordAndUsernameAreCorrect(String username, String password ) {

       // Arrange
        user.setUsername(username);
        user.setPassword(password);

        // Act
        constraintViolations = validator.validate(user);

        // Assert
        Assert.assertEquals(constraintViolations.size(), 0);
    }

}