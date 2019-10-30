package com.yangjie.test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yangjie.entity.User;
import com.yangjie.service.UserService;

public class Test2 {

	public static void main(String[] args) {
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-*.xml");
		context.start();
		UserService user = (UserService) context.getBean(UserService.class);
		User u1 = new User();
		u1.setId(48);
		List<User> u = user.getUserlist(null, (2-1)*10, 10);
		System.out.println(u);
	}

}
