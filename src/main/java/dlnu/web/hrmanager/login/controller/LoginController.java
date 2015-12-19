package dlnu.web.hrmanager.login.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dlnu.web.hrmanager.employee.dao.EmpDao;
import dlnu.web.hrmanager.employee.entity.Emp;

@Controller
public class LoginController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		EmpDao dao = new EmpDao();
		ArrayList<Emp> emps = dao.Emplist();
		
		model.addAttribute("empList", emps );
		
		return "login/login";
	}
}
