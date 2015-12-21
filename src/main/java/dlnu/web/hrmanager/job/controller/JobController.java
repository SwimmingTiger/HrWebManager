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

	@RequestMapping(value = "/job/joblist", method = RequestMethod.GET)
	public String joblist(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		return joblist(locale, model);
	}	
	
	@RequestMapping(value = "/job/del", method = RequestMethod.POST)
	public String delPost(Locale locale, Model model,
			@RequestParam(value="id") int DeleteId) {
		
		/***************** 删除 *****************/
		try {
			JobDao.con().Delete(DeleteId);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return joblist(locale, model);
	}
	
	@RequestMapping(value = "/job/edit", method = RequestMethod.POST)
	public String editPost(Locale locale, Model model,
			@RequestParam(value="id") int EditId) {

		/***************** 修改 *****************/
		try {
			Job job = JobDao.con().Inquire(EditId);
			
			
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return joblist(locale, model);
	}
}
