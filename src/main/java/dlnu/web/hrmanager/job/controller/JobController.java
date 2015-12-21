package dlnu.web.hrmanager.job.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import org.springframework.web.bind.annotation.RequestParam;

import dlnu.web.hrmanager.job.dao.JobDao;
import dlnu.web.hrmanager.job.entity.Job;
import dlnu.web.hrmanager.util.DBUtil;
import dlnu.web.hrmanager.util.database.DBException;

@Controller
public class JobController {

	@RequestMapping(value = "/job/*", method = RequestMethod.GET)
	public String joblist(Locale locale, Model model) {
		
		ArrayList<Job> joblist = JobDao.con().Display();
		model.addAttribute("DisplayJob", joblist);
		
		return "job/jobs";
	}
	
	@RequestMapping(value = "/job/add", method = RequestMethod.POST)
	public String addjob(Locale locale, Model model,
			@RequestParam(value="岗位名称") String jobName,
			@RequestParam(value="最高工资") double jobMax,
			@RequestParam(value="最低工资") double jobMin) {
		
		try {
			Job job = new Job();
			
			job.id = DBUtil.conn().getNextId("job");
			job.name = jobName;
			job.salary_max = jobMax;
			job.salary_min = jobMin;
			
			JobDao.con().Add(job);
		} catch (DBException e) {
			model.addAttribute("error", e.getMessage());
		}

		return joblist(locale, model);
	}	
	
	@RequestMapping(value = "/job/del", method = RequestMethod.POST)
	public String delPost(Locale locale, Model model,
			@RequestParam(value="id") int DeleteId) {
		
		/***************** 删除 *****************/
		try {
			if (JobDao.con().Check_id_forDelete(DeleteId)){
				throw new Exception("此岗位有员工，请先删除员工");
			}
			
			JobDao.con().Delete(DeleteId);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		
		return joblist(locale, model);
	}
	
	@RequestMapping(value = "/job/edit", method = RequestMethod.POST)
	public String editPost(Locale locale, Model model,
			@RequestParam(value="编号") int EditId) {

		/***************** 修改 *****************/
		try {
			Job job = JobDao.con().Inquire(EditId);
			model.addAttribute("JobEdit", job);			
			
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		
		return joblist(locale, model);
	}
	
	@RequestMapping(value = "/job/editSave", method = RequestMethod.POST)
	public String editSave(Locale locale, Model model,
			@RequestParam(value="编号") int EditId,
			@RequestParam(value="岗位名称") String EditName,
			@RequestParam(value="最高工资") double EditSalary_max,
			@RequestParam(value="最低工资") double EditSalary_min) {

		/***************** 修改 *****************/
		try {
			Job job = new Job();
			job.id = EditId;
			job.name = EditName;
			job.salary_max = EditSalary_max;
			job.salary_min = EditSalary_min;		
			JobDao.con().Change(job);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		
		return joblist(locale, model);
	}
}
