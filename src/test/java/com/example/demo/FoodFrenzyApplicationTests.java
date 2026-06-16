package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

class FoodFrenzyApplicationTests {

	@Test
	void applicationClassShouldBeInstantiable() {
		FoodFrenzyApplication application = new FoodFrenzyApplication();
		assertNotNull(application);
	}

	@Test
	void mainShouldDelegateToSpringApplicationBuilderWithoutThrowing() {
		assertDoesNotThrow(() -> {
			SpringApplicationBuilder builder = new SpringApplicationBuilder(FoodFrenzyApplication.class);
			assertNotNull(builder);
		});
	}
}