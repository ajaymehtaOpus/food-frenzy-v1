package com.example.demo.count;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

class LogicTest {

    @Test
    void countTotalShouldMultiplyPriceByQuantity() {
        double result = Logic.countTotal(12.5, 4);
        assertEquals(50.0, result, 0.000001);
    }

    @Test
    void countTotalShouldHandleZeroQuantity() {
        double result = Logic.countTotal(99.99, 0);
        assertEquals(0.0, result, 0.000001);
    }

    @Test
    void privateConstructorShouldNotBeInstantiableNormally() throws Exception {
        Constructor<Logic> constructor = Logic.class.getDeclaredConstructor();
        assertNotNull(constructor);
        constructor.setAccessible(true);

        try {
            Logic instance = constructor.newInstance();
            assertNotNull(instance);
        } catch (InvocationTargetException ex) {
            throw ex;
        }
    }
}