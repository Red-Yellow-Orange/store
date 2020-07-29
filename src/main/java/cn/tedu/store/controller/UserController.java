package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.util.JsonResult;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private IUserService iUserService;
	
	/**
	 * 响应正确时使用的状态码
	 */
	public static final Integer OK = 2000;
	
	// http://localhost:80/users/reg?username=controller&password=1234&gender=0&phone=13100131001&email=controller@qq.com
	@RequestMapping("reg")
	public JsonResult<Void> reg(User user) {
		iUserService.reg(user);
		return new JsonResult<>(OK);
	}
	
	// http://localhost:80/users/login?username=root&password=1234
	@RequestMapping("login")
	public JsonResult<User> login(String username, String password, HttpSession session) {
		User data = iUserService.login(username, password);
		session.setAttribute("uid", data.getUid());
		session.setAttribute("username", data.getUsername());
		return new JsonResult<>(OK, data);
	}
	
	// http://localhost:80/users/password/change?oldPassword=1234&newPassword=123
	//请求路径：/users/password/change
	//请求参数：String oldPassword, String newPassword, HttpSession session
	//请求方式：POST
	//响应结果：JsonResult<Void>
	@RequestMapping("password/change")
	public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
		//从参数session中取出登录时存入的uid和username
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		String username = session.getAttribute("username").toString();
		//调用业务对象的方法执行修改密码
		iUserService.changePassword(uid, oldPassword, newPassword, username);
		//返回
		return new JsonResult<>(OK);
	}
	
}
