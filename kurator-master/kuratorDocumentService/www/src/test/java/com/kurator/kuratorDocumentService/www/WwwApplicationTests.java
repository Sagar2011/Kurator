package com.kurator.kuratorDocumentService.www;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.*;

@SpringBootTest
@TestPropertySource("/bootstrap-test.properties")
class WwwApplicationTests {

	@Test
	public void WwwApplicationTest(){

		int expectedName=1;
		assertEquals(expectedName, 1);
	}

}
