package com.yangjie.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yangjie.entity.SysMenuEntity;
import com.yangjie.entity.User;
import com.yangjie.entity.meet;
import com.yangjie.service.UserService;
import com.yangjie.util.AcenterApi;
import com.yangjie.util.MD5Util;
import com.yangjie.util.PageUtils;
import com.yangjie.util.R;

@RestController
public class UserController {

	 @Autowired
	 private UserService userService;
	
	 @RequestMapping(value = "/hello", produces = "text/html; charset=utf-8")
	 public ModelAndView show() {
		 userService.getUser(2);
		 ModelAndView view = new ModelAndView("WEB-INF/page/index");
		 return view;
	 }
	
	 @RequestMapping(value = "/insert")
	 public ModelAndView add() {
		 User u = new User();
		 Map<String, String> m = new HashMap<String, String>();
		 u.setId(2);
		 u.setName("123");
		 int num = userService.insert(u);
		 if (num > 0) {
		 m.put("msg", "添加成功");
		 }
		 ModelAndView view = new ModelAndView("index", m);
		 return view;
	 }

	/**
	 *  登录
	 * @param mcu
	 * @param username
	 * @param password
	 * @param request
	 * @param respone
	 * @param httpSession
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public R login(String mcu, String username, String password,HttpServletRequest request,HttpServletResponse respone,HttpSession httpSession) throws Exception {
		AcenterApi api = new AcenterApi();
		if(username.equals("") || password.equals("") )
		{
			return R.error(-4, "用户名密码不能为空");
		}
		String r = api.Login(mcu, username, MD5Util.MD5((password)));
		if(r.equals("1"))
		{
			request.getSession().setAttribute("mcu", mcu);
			request.getSession().setAttribute("user", username);
			return R.ok();
		}
		else if(r.equals("-2") ||r.equals("-3"))
		{
			return R.error(-2, "用户名密码不正确");
		}
		else if(r.equals("-1"))
		{
			return R.error(-1, "服务器请求超时");
		}
		return R.ok();
	}

	/**
	 * 菜单
	 * 
	 * @param mcu
	 * @param username
	 * @param password
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/user/menu", method = RequestMethod.GET)
	public R menu(String mcu, String username, String password, HttpServletRequest request,
			HttpServletResponse respone) {
		List<SysMenuEntity> menuList = new ArrayList<SysMenuEntity>();
		SysMenuEntity s = new SysMenuEntity();
		s.setName("会议列表");
		s.setUrl("page/meet.jsp");
		s.setType(1);
		menuList.add(s);
		SysMenuEntity s1 = new SysMenuEntity();
		s1.setName("网络设置");
		s1.setUrl("page/5.html");
		s1.setType(1);
		menuList.add(s1);
		return R.ok().put("menuList", menuList);
	}

	/**
	 * 用户
	 * 
	 * @param mcu
	 * @param username
	 * @param password
	 * @param request
	 * @param respone
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/user/getuser", method = RequestMethod.GET)
	public R getuser(String mcu, String username, String password, HttpServletRequest request,
			HttpServletResponse respone, HttpSession httpSession) {
		return R.ok().put("user", httpSession.getAttribute("user"));
	}

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public R list(String mcu, String username, String password, HttpServletRequest request, HttpServletResponse respone,
			HttpSession httpSession, String page, String mname,String limit) throws Exception {
		AcenterApi api = new AcenterApi();
		PageUtils pages = api.listPermRoom(request.getSession().getAttribute("mcu").toString(),page,request.getSession().getAttribute("user").toString(),limit,mname);
		return R.ok().put("page", pages);
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletResponse respone, HttpServletRequest request) {
		Enumeration em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		ModelAndView view = new ModelAndView("login");
		return view;
	}

	@RequestMapping(value = "/uesr/joinmeet", method = RequestMethod.GET)
	public ModelAndView joinmeet(HttpServletResponse respone, HttpServletRequest request, String rid) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("rid", rid);
		ModelAndView view = new ModelAndView("page/joinmeet",m);
		return view;
	}
}
