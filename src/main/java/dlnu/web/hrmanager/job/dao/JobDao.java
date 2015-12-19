package dlnu.web.hrmanager.job.dao;

import java.util.ArrayList;

import dlnu.web.hrmanager.job.entity.Job;
import dlnu.web.hrmanager.util.DBUtil;
import dlnu.web.hrmanager.util.Window;
import dlnu.web.hrmanager.util.database.DBException;
import dlnu.web.hrmanager.util.database.PreResult;
import dlnu.web.hrmanager.util.database.Result;

public class JobDao {

	private static JobDao db = new JobDao();
	public static JobDao con(){
		return db;
	}
	
	public int Add(Job job){
		
		try {
			String sql = "INSERT INTO job(id, name, salary_min, salary_max) VALUES(?,?,?,?)";
			DBUtil.conn().exec(sql, job.id, job.name, job.salary_min, job.salary_max);
		} catch (DBException e) {
			// TODO 自动生成的 catch 块
		e.printStackTrace();
		}
		
		return 0;
	}
	
	public void Change(Job job) throws Exception{
		
	
		String sql = "UPDATE job SET name = ? , salary_min = ?, salary_max = ? WHERE id = ?";
		int i = DBUtil.conn().exec(sql, job.name, job.salary_min, job.salary_max, job.id);
		
	}	
	
	public void Delete(int id) throws Exception{
		
		int i = 0;
		String sql = "DELETE FROM job WHERE id = ?";
		DBUtil.conn().exec(sql, id);
	}
	
	public Job Inquire(int id) throws DBException{
		
		Job job = new Job();
		job.id = id;
		
		String sql = "SELECT name, salary_min,  salary_max FROM job WHERE id = ?";
		Result result = DBUtil.conn().query(sql, id);
		if (result.next()){	
			job.name = (String)result.fetch("name");
			job.salary_min = (Double) result.fetch("salary_min");
			job.salary_max = (Double) result.fetch("salary_max");
		}
		
		return job;
	}
	
	public ArrayList<Job> Display(){
		ArrayList<Job> arraylist = new ArrayList<Job>();
		
		try{
			String sql = "SELECT * FROM job";
			String sql2 = "SELECT AVG(salary) FROM employee WHERE job_id = ?";
			Result result = DBUtil.conn().query(sql);
			while(result.next()){
				Job job = new Job();
				job.id = (Integer) result.fetch("id");				 //	System.out.println(job.id);System.out.println("#");
				job.name = (String) result.fetch("name");			 //	System.out.print(job.name);System.out.println("#");
				job.salary_min = (Double) result.fetch("salary_min");//	System.out.print(job.salary_min);System.out.println("#");
				job.salary_max = (Double) result.fetch("salary_max");//	System.out.print(job.salary_max);System.out.println("#");
				Result result_sal = DBUtil.conn().query(sql2, job.id);
				if(result_sal.next()){
					Object avg = result_sal.fetch(1);
					if(avg != null){
						job.salary_avg = (Double)avg;
					}
					else{
						job.salary_avg = -1;	//表示暂无员工
					}
				}
				arraylist.add(job);
			}
			
		}catch(DBException e){
			e.printStackTrace();
			arraylist = null;
		}
		
		return arraylist;
	}
	
	//检查id是否已经存在
	public boolean Check_id(int id) throws DBException{
		
		Window window = new Window();
		String sql = "SELECT id FROM job WHERE id = ?";
		Result result = DBUtil.conn().query(sql, id);
				
		if(result.next()){
			return true;	//存在
		}else{
			return false;	//不存在
		}
	}
	
	public boolean Check_id_forDelete(int id) throws DBException{
		
		String sql = "SELECT id FROM employee WHERE job_id = ?";
		Result result = DBUtil.conn().query(sql, id);
		
		if (result.next()){
			return true;	//有人返回true
		}else{
			return false;	//无人返回false
		}
	}
}
