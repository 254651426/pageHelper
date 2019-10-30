package com.yangjie.dao;

import java.util.List;

import com.github.pagehelper.PageRowBounds;
import com.yangjie.entity.User;

public interface UserDao {

	public boolean show();

	public int insert(User user);

	public User getUser(int id);

	public List<User> selectByUser(User user, PageRowBounds pageRowBounds);
}
