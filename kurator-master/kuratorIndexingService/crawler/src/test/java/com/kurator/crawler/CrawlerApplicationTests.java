package com.kurator.crawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CrawlerApplication.class)
@TestPropertySource("/bootstrap-test.properties")
public class CrawlerApplicationTests {

	@Test
	public void contextLoads() {

	}

}
