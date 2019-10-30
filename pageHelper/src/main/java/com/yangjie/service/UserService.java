package com.yangjie.service;


import java.util.List;

import com.yangjie.entity.User;

public interface UserService {

	 public void add();
	 
	 public boolean show();

	int insert(User user); 
	
	public User getUser(int id);
	
	List<User> getUserlist(User user, Integer pageNum, Integer pageSize);
}
