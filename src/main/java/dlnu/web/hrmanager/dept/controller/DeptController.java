package dlnu.web.hrmanager.dept.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import dlnu.web.hrmanager.util.database.DBException;

@Controller
public class DeptController {

	/**
	 * 列出部门
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dept/*", method = RequestMethod.GET)
	public String DeptList(Locale locale, Model model) {
		List<Dept> depts = null;
		int nextId = 1;
		
		try {
			DeptService dao = DeptService.getInstance();
			depts = dao.getDeptList();
			nextId = dao.getNextId();
		} catch (DBException e) {
			model.addAttribute("error", e.getMessage());
		}
		
		model.addAttribute("deptList", depts);
		model.addAttribute("nextId", nextId);
		
		return "dept/list";
	}
	
	/**
	 * 添加部门
	 * @param locale
	 * @param model
	 * @param name
	 * @param address
	 * @return
	 */
	@RequestMapping(value = "/dept/add", method = RequestMethod.POST)
	public String DeptAdd(Locale locale, Model model,
			@RequestParam("name") String name,
			@RequestParam("address") String address) {
		try {
			if ("".equals(name)) {
				throw new DeptException("部门名称不能为空");
			}
			
			Dept dept = new Dept();
			dept.setName(name);
			dept.setAddress(address);
			dept.setManager(new Emp());

			boolean success = DeptService.getInstance().addDept(dept);
			
			if (success) {
				model.addAttribute("success", "添加成功");
			} else {
				throw new DeptException("添加失败");
			}
			
		} catch (DBException | DeptException e) {
			model.addAttribute("error", e.getMessage());
		}
		
		return DeptList(locale, model);
	}
	
	/**
	 * 删除部门
	 * @param locale
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/dept/delete", method = RequestMethod.POST)
	public String DeptDelete(Locale locale, Model model,
			@RequestParam("id") int id) {
		try {
			Dept dept = new Dept();
			dept.setId(id);
			
			if (DeptService.getInstance().empCount(dept) > 0) {
				throw new DeptException("请先删除所有员工或转移至其他部门");
			}
			
			boolean success = DeptService.getInstance().delDept(dept);
			
			if (success) {
				model.addAttribute("success", "删除成功");
			} else {
				throw new DeptException("删除失败");
			}
		} catch (DBException | DeptException e) {
			model.addAttribute("error", e.getMessage());
		}
		
		return DeptList(locale, model);
	}
	
	@RequestMapping(value = "/dept/edit", method = RequestMethod.GET)
	public String DeptEditGet(Locale locale, Model model,
			@RequestParam("id") int id) {
		try {
			Dept dept = DeptService.getInstance().getDeptById(id);
			
			if (dept.getName() == null) {
				throw new DeptException("指定的部门不存在");
			}
			
			model.addAttribute("dept", dept);
			
			EmpDao empDao = new EmpDao();
			List<Emp> managerList = empDao.Emplist(dept);
			model.addAttribute("managerList", managerList);
		} catch (DBException | DeptException e) {
			model.addAttribute("error", e.getMessage());
			return "dept/editError";
		}
		
		return "dept/edit";
	}
	
	@RequestMapping(value = "/dept/edit", method = RequestMethod.POST)
	public String DeptEditPost(Locale locale, Model model,
			@RequestParam("id") int id,
			@RequestParam("name") String name,
			@RequestParam("address") String address,
			@RequestParam("manager") int managerID) {
		try {
			Dept dept = DeptService.getInstance().getDeptById(id);
			model.addAttribute("dept", dept);
			
			if (dept.getName() == null) {
				throw new DeptException("指定的部门不存在");
			}
			
			dept.setName(name);
			dept.setAddress(address);
			
			Emp manager = (new EmpDao()).Inquire(managerID);
			
			if (manager == null) {
				manager = new Emp();
				manager.setEmpID(managerID);
			}
			
			dept.setManager(manager);
			
			DeptService.getInstance().editDept(dept);
			
		} catch (DBException | DeptException e) {
			model.addAttribute("error", e.getMessage());
			return "dept/editError";
		}
		
		return "dept/editSuccess";
	}
	
	private class DeptException extends Exception {
		public DeptException(String msg) {
			super(msg);
		}
	}
}
