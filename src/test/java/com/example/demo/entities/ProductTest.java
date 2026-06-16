package com.example.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ProductTest {

	@Test
	void shouldSetAndGetAllFields() {
		Product product = new Product();

		product.setPid(101);
		product.setPname("Laptop");
		product.setPprice(999.99);
		product.setPdescription("High-end gaming laptop");

		assertEquals(101, product.getPid());
		assertEquals("Laptop", product.getPname());
		assertEquals(999.99, product.getPprice());
		assertEquals("High-end gaming laptop", product.getPdescription());
	}

	@Test
	void shouldReturnFormattedToString() {
		Product product = new Product();
		product.setPid(7);
		product.setPname("Phone");
		product.setPprice(499.5);
		product.setPdescription("Smartphone");

		String expected = "Product [pid=7, pname=Phone, pprice=499.5, pdescription=Smartphone]";

		assertEquals(expected, product.toString());
	}

	@Test
	void shouldCreateNonNullInstance() {
		Product product = new Product();

		assertNotNull(product);
	}
}