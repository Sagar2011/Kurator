package com.kurator.kuratorDocumentService.firewall;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@TestPropertySource("/bootstrap-test.properties")
class FirewallApplicationTests {

	@Test
	public void FirewallApplicationTest(){

		int expectedName=1;
		assertEquals(expectedName, 1);
	}

}
