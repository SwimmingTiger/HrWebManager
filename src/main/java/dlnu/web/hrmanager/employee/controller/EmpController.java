package dlnu.web.hrmanager.employee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dlnu.web.hrmanager.dept.dao.DeptDao;
import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.dept.service.DeptService;
import dlnu.web.hrmanager.employee.dao.EmpDao;
import dlnu.web.hrmanager.employee.entity.Emp;
import dlnu.web.hrmanager.job.dao.JobDao;
import dlnu.web.hrmanager.job.entity.Job;
import dlnu.web.hrmanager.util.DBUtil;
import dlnu.web.hrmanager.util.database.DBException;

@Controller
public class EmpController {
	@RequestMapping(value = "/employee/*", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		try {
				EmpDao dao = new EmpDao();
				JobDao job = new JobDao();
				ArrayList<Emp> emps = dao.Emplist();
				ArrayList<Job> jobs = job.Display();
				List<Dept> depts = DeptService.getInstance().getDeptList();
		
				model.addAttribute("empList", emps);
				model.addAttribute("jobList", jobs);
				model.addAttribute("deptList", depts);
				
		
		} catch (DBException e) {
			model.addAttribute("error", e.getMessage());
		}
		
		return "employee/employee";
	}
	
	@RequestMapping(value = "/employee/add", method = RequestMethod.POST)
	public String add(Locale locale, Model model,
			@RequestParam(value="empname") String empname,
			@RequestParam(value="empsalary") Double empsalary,
			@RequestParam(value="job") int emppost,
			@RequestParam(value="empdate") String empdate,
			@RequestParam(value="dept") int empdept) {
		EmpDao dao = new EmpDao();
		JobDao job = new JobDao();
		
		
		
		try {
			Emp emp1 = new Emp();
			emp1.setEmpID(new Integer(DBUtil.conn().getNextId("employee")));
			emp1.setEmpname(empname);
			emp1.setEmpsalary(empsalary);
			emp1.setEmppost(job.Inquire(emppost));
			emp1.setEmpdate(empdate);
			emp1.setEmpdept(DeptService.getInstance().getDeptById(empdept));
			
			dao.Add(emp1);
			
		} catch (DBException e) {
			model.addAttribute("error", e.getMessage());
		}
		
		return home(locale, model);
	}
	
	@RequestMapping(value = "/employee/delete", method = RequestMethod.POST)
	public String delete(Locale locale, Model model,
			@RequestParam(value="ID") int empID) {
		JobDao job = new JobDao();
		try {
			Emp emp1 = new Emp();
			emp1.setEmpID(empID);
			
			EmpDao dao2 = new EmpDao();
			dao2.Dele(emp1);
		} catch (DBException e) {
			model.addAttribute("error", e.getMessage());
		}
		
		
		return home(locale, model);
	}
	
	@RequestMapping(value = "/employee/edit", method = RequestMethod.POST)
	public String edit(Locale locale, Model model,
			@RequestParam(value="ID") int empID) {
		EmpDao dao = new EmpDao();
		JobDao job = new JobDao();
		try {
			Emp editEmp = dao.Inquire(empID);
			model.addAttribute("editEmp", editEmp);
		} catch (DBException e) {
			model.addAttribute("error", e.getMessage());
		}
		
		
		return home(locale, model);
	}
	
	@RequestMapping(value = "/employee/save", method = RequestMethod.POST)
	public String save(Locale locale, Model model,
		@RequestParam(value="ID") int empID,
		@RequestParam(value="empname") String empname,
		@RequestParam(value="empsalary") Double empsalary,
		@RequestParam(value="job") int emppost,
		@RequestParam(value="empdate") String empdate,
		@RequestParam(value="dept") int empdept) {
		EmpDao dao = new EmpDao();
		JobDao job = new JobDao();

		try {
			
			Emp editEmp = new Emp();
			editEmp.setEmpID(empID);
			editEmp.setEmpname(empname);
			editEmp.setEmpsalary(empsalary);
			editEmp.setEmppost(job.Inquire(emppost));
			editEmp.setEmpdate(empdate);
			editEmp.setEmpdept(DeptService.getInstance().getDeptById(empdept));
			dao.Mod(editEmp);

		} catch (DBException e) {
			model.addAttribute("error", e.getMessage());
		}
		
		
		return home(locale, model);
	}
}
