package com.kuangshen;

import com.kuangshen.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootShiroApplicationTests {

	@Autowired
	UserService userService;
	@Test
	void contextLoads() {
		System.out.println(userService.queryByName("ccj"));

	}

}
