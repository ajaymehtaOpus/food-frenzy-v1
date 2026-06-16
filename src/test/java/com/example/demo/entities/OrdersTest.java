package com.example.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

class OrdersTest {

	@Test
	void shouldSetAndGetAllFields() {
		Orders orders = new Orders();
		Date orderDate = new Date(123456789L);
		User user = new User();

		orders.setoId(10);
		orders.setoName("Laptop");
		orders.setoPrice(999.99);
		orders.setoQuantity(2);
		orders.setOrderDate(orderDate);
		orders.setTotalAmmout(1999.98);
		orders.setUser(user);

		assertEquals(10, orders.getoId());
		assertEquals("Laptop", orders.getoName());
		assertEquals(999.99, orders.getoPrice());
		assertEquals(2, orders.getoQuantity());
		assertEquals(orderDate, orders.getOrderDate());
		assertEquals(1999.98, orders.getTotalAmmout());
		assertEquals(user, orders.getUser());
	}

	@Test
	void shouldFormatToStringWithAllFields() {
		Orders orders = new Orders();
		Date orderDate = new Date(123456789L);
		User user = new User();

		orders.setoId(7);
		orders.setoName("Phone");
		orders.setoPrice(499.5);
		orders.setoQuantity(3);
		orders.setOrderDate(orderDate);
		orders.setTotalAmmout(1498.5);
		orders.setUser(user);

		String result = orders.toString();

		assertNotNull(result);
		assertEquals("Orders [oId=7, oName=Phone, oPrice=499.5, oQuantity=3, orderDate=" + orderDate
				+ ", totalAmmout=1498.5, user=" + user + "]", result);
	}

	@Test
	void shouldCreateDefaultInstance() {
		Orders orders = new Orders();

		assertNotNull(orders);
	}
}