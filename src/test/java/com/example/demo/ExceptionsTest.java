package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ExceptionsTest {

    @Test
    void handlerShouldReturnExceptionViewName() {
        Exceptions exceptions = new Exceptions();

        String result = exceptions.handler();

        assertNotNull(result);
        assertEquals("exception", result);
    }
}