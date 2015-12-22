package dlnu.web.hrmanager.login.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dlnu.web.hrmanager.employee.dao.EmpDao;
import dlnu.web.hrmanager.employee.entity.Emp;
import dlnu.web.hrmanager.login.dao.LoginDao;
import dlnu.web.hrmanager.util.database.DBException;

@Controller
public class LoginController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void DefaultPage(Locale locale, Model model,
			HttpServletResponse response) {
		try {
			response.sendRedirect("login/login");
		} catch (IOException e) {
			// ignore
		}
	}

	@RequestMapping(value = "/login/*", method = RequestMethod.GET)
	public String loginGet(Locale locale, Model model) {
		
		return "login/login";
	}
	
	@RequestMapping(value = "/login/exit", method = RequestMethod.GET)
	public String loginExit(Locale locale, Model model,
			HttpSession session, HttpServletResponse response) {

		session.removeAttribute("loginType");
		session.removeAttribute("username");
		
		try {
			response.sendRedirect("login");
		} catch (IOException e) {
			//ignore
		}
		return "login/login";
	}
	
	@RequestMapping(value = "/login/*", method = RequestMethod.POST)
	public String loginPost(Locale locale, Model model,
			HttpSession session, HttpServletResponse response,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("loginType") int loginType) {

		boolean isLogin = false;
		
		try {
			LoginDao dao = new LoginDao();
			isLogin = dao.Login(loginType, username, password);
			
			if (isLogin) {
				session.setAttribute("loginType", loginType);
				session.setAttribute("username", username);
			} else {
				throw new Exception("用户名、密码或登录类型错误");
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		
		if (isLogin) {
			if (loginType == 1) {
				try {
					response.sendRedirect("../admin/main");
				} catch (IOException e) {
					// ignore
				}
				
				return "nav/adminMain";
			} else {
				try {
					response.sendRedirect("../user/main");
				} catch (IOException e) {
					// ignore
				}
				
				return "nav/userMain";
			}
		} else {
			return "login/login";
		}
	}
	
	@RequestMapping(value = "/admin/main", method = RequestMethod.GET)
	public String adminMain(Locale locale, Model model) {

		return "nav/adminMain";
	}
	
	@RequestMapping(value = "/user/main", method = RequestMethod.GET)
	public String userMain(Locale locale, Model model) {

		return "nav/userMain";
	}
}
