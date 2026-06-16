package com.example.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void defaultConstructorAndSettersAndGettersShouldWork() {
        User user = new User();
        assertNotNull(user);

        user.setU_id(42);
        user.setUname("John Doe");
        user.setUemail("john@example.com");
        user.setUpassword("secret");
        user.setUnumber(9876543210L);

        assertEquals(42, user.getU_id());
        assertEquals("John Doe", user.getUname());
        assertEquals("john@example.com", user.getUemail());
        assertEquals("secret", user.getUpassword());
        assertEquals(9876543210L, user.getUnumber());
    }

    @Test
    void constructorWithEmailAndPasswordShouldSetFields() {
        User user = new User("alice@example.com", "password123");

        assertNotNull(user);
        assertEquals("alice@example.com", user.getUemail());
        assertEquals("password123", user.getUpassword());
    }

    @Test
    void toStringShouldIncludeAllAssignedValues() {
        User user = new User();
        user.setU_id(7);
        user.setUname("Alice");
        user.setUemail("alice@example.com");
        user.setUpassword("pwd");
        user.setUnumber(12345L);

        List<Orders> orders = new ArrayList<>();
        user.setOrders(orders);

        String result = user.toString();

        assertNotNull(result);
        assertEquals("User [u_id=7, uname=Alice, uemail=alice@example.com, upassword=pwd, unumber=12345, orders=[]]", result);
    }

    @Test
    void ordersGetterAndSetterShouldWorkWithNullAndNonNullValues() {
        User user = new User();

        assertEquals(null, user.getOrders());

        List<Orders> orders = new ArrayList<>();
        user.setOrders(orders);

        assertEquals(orders, user.getOrders());
    }
}