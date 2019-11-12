package com.jsbg.concurrentserver.logging;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputValidatorTest {
    private static InputValidator inputValidator;

    @BeforeEach
    void setUp() {
        inputValidator = new InputValidator();
    }

    @Test
    void tooShortTest() {
        Assertions.assertFalse(inputValidator.isValidInput("12345678"));
    }

    @Test
    void tooLongTest() {
        Assertions.assertFalse(inputValidator.isValidInput("1234567890"));
    }

    @Test
    void notIntegerTest() {
        Assertions.assertFalse(inputValidator.isValidInput("ABCDEFGHI"));
    }

    @Test
    void isValidTest() {
        Assertions.assertTrue(inputValidator.isValidInput("918273645"));
    }

}