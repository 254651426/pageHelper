package com.yangjie.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;
import com.yangjie.dao.UserDao;
import com.yangjie.entity.User;
import com.yangjie.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	// 注入以后就可以直接使用sqlsession
	@Autowired
	private SqlSessionTemplate sqlsession;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private UserDao userDao;

	@Override
	public int insert(User user) {
		return userDao.insert(user);
	}

	@Override
	@Transactional
	public void add() {
		int num = jdbcTemplate.update("insert into student(name) values('212')");
		if (num > 0) {
			System.out.println("添加成功");
		}
		// int s = 1 / 0;
	}

	@Override
	public boolean show() {
		int count = jdbcTemplate.queryForObject("select count(id) from student where name='212'", Integer.class);
		if (count > 0) {
			return false;
		}
		return true;
	}

	@Override
	public User getUser(int id) {
		// UserDao u = sqlsession.getMapper(UserDao.class);
		// u.getUser(id);
		// u.getUser(id);
		// return sqlsession.selectOne("com.yangjie.dao.UserDao.getUser", 2);
		return userDao.getUser(id);
	}

	@Override
	public List<User> getUserlist(User user, Integer pageNum, Integer pageSize) {
		PageRowBounds pageRowBounds = new PageRowBounds(pageNum, pageSize);
		List<User> UserList = userDao.selectByUser(user, pageRowBounds);
		System.out.println(pageRowBounds.getTotal());
		Page<User> page = (Page<User>) UserList;
		System.out.println(page);
		return UserList;
	}

}
