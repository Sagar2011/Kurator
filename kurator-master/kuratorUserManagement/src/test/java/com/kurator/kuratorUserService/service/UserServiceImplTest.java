package com.kurator.kuratorUserService.service;

import com.kurator.kuratorUserService.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kurator.kuratorUserService.KuratorUserManagement;
import com.kurator.kuratorUserService.exception.UsersNotFound;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {KuratorUserManagement.class})
@TestPropertySource("/bootstrap-test.properties")
public class UserServiceImplTest {
    @Autowired
    private IUserService userService;

    private static User user;
    private static User user1;

    @BeforeAll
    public static void  testUser() {
        user = new User( "Beauty", "beauty@gmail.com", "Beauty", "Sahu");
        user1 = new User( "abc", "abc@gmail.com", "abc", "abc");
    }

    @Test
    public void createUser() throws UsersNotFound {
	  	userService.saveUser(user);
	  	assertEquals(userService.findByUserName(user.getUserName()).getEmail(), user.getEmail());
    }
	@Test
	public void findByEmail() throws UsersNotFound
	{
	    userService.saveUser(user1);
		User user2 = userService.findUserByEmail("abc@gmail.com");
		assertEquals(user1.getEmail(),user2.getEmail());
	}

}
