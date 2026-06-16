package com.example.demo.count;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class LogicTest {

    @Test
    void countTotalShouldMultiplyPriceByQuantity() {
        assertEquals(30.0, Logic.countTotal(10.0, 3), 0.0000001);
    }

    @Test
    void countTotalShouldHandleZeroQuantity() {
        assertEquals(0.0, Logic.countTotal(12.5, 0), 0.0000001);
    }

    @Test
    void shouldInstantiateLogicClass() {
        Logic logic = new Logic();
        assertNotNull(logic);
    }
}