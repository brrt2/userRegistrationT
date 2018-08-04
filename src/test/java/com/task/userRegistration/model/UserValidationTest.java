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

        user.setUsername("bartek");
        user.setPassword(password);
        constraintViolations = validator.validate(user);

        Assert.assertEquals(constraintViolations.size(), 1);
        Assert.assertNotEquals(2, constraintViolations.size());
    }

    @Test(dataProvider = "provideIncorrectUsernames")
    public void shouldFindOneConstraintValidationWhenIncorrectUsernameIsProvided(String username) {

        user.setUsername(username);
        user.setPassword("January1");
        constraintViolations = validator.validate(user);

        Assert.assertEquals(constraintViolations.size(), 1);
        Assert.assertNotEquals(2, constraintViolations.size());
    }

    @Test(dataProvider = "provideIncorrectPasswordsAndUsernames")
    public void shouldSignalTwoConstraintValidationsWhenIncorrectUsernameAndPasswordAreProvided(String username,
                                                                                                String password) {
        user.setUsername(username);
        user.setPassword(password);
        constraintViolations = validator.validate(user);

        Assert.assertEquals(constraintViolations.size(), 2);
        Assert.assertNotEquals(1, constraintViolations.size());
    }

    @Test(dataProvider = "provideCorrectPasswordsAndUsernames")
    public void shouldFindNoConstraintValidationsWhenPasswordAndUsernameAreCorrect(String username, String password ) {
        user.setUsername(username);
        user.setPassword(password);
        constraintViolations = validator.validate(user);

        Assert.assertEquals(constraintViolations.size(), 0);
        Assert.assertNotEquals(1, constraintViolations.size());
    }

}