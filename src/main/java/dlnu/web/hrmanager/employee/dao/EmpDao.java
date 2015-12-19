package dlnu.web.hrmanager.employee.dao;

import java.sql.Date;
import java.util.ArrayList;

import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.dept.service.DeptService;
import dlnu.web.hrmanager.employee.entity.Emp;
import dlnu.web.hrmanager.job.dao.JobDao;
import dlnu.web.hrmanager.job.entity.Job;
import dlnu.web.hrmanager.util.DBUtil;
import dlnu.web.hrmanager.util.database.DBException;
import dlnu.web.hrmanager.util.database.Result;

/***
 * 员工数据库操作类
 * 
 * @author 黄英淏
 *
 */
public class EmpDao {
	//测试方法
	public static void main(String agr[])
	{

	}

	//添加员工
	public void Add(Emp emp) throws DBException
	{

		String sql="INSERT INTO employee(name,salary,job_id,hiredate,dept_id)VALUES(?,?,?,?,?)";
		DBUtil.conn().exec(sql,emp.getEmpname(),emp.getEmpsalary(),emp.getEmppost().Get_id(),emp.getEmpdate(),emp.getEmpdept().getId());

	}

	//修改员工
	public void Mod(Emp emp) throws DBException
	{


		String sql="UPDATE employee SET name =?,salary=?,job_id=?,hiredate=?,dept_id=? WHERE id =?";
		DBUtil.conn().exec(sql,emp.getEmpname(),emp.getEmpsalary(),emp.getEmppost().Get_id(),emp.getEmpdate(),emp.getEmpdept().getId(),emp.getEmpID());

	}

	//删除员工
	public void Dele(Emp emp) throws DBException
	{

		String sql="DELETE FROM employee WHERE id=?";
		DBUtil.conn().exec(sql,emp.getEmpID());

	}

	//获得指定id的员工对象
	public Emp Inquire(int id) throws DBException
	{
		Emp emp = null;

		String sql="SELECT * FROM employee WHERE id=?";
		Result result  = DBUtil.conn().query(sql, id);
		if (result.next()) {
			emp = new Emp();
			emp.setEmpID(id);
			emp.setEmpname((String)result.fetch("name"));
			emp.setEmpsalary((Double) result.fetch("salary"));
			emp.setEmppost(JobDao.con().Inquire((Integer) result.fetch("job_id")));
			emp.setEmpdate(((Date) result.fetch("hiredate")).toString());
			emp.setEmpdept(DeptService.getInstance().getDeptById((Integer) result.fetch("dept_id")));

		}

		return emp;
	}

	//获得指定id、指定部门的员工对象
	public Emp getEmpById(int id, Dept dept) throws DBException
	{
		Emp emp = new Emp();
		emp.setEmpID(id);

		String sql="SELECT name,salary,job_id,hiredate,dept_id FROM employee WHERE id=?";
		Result result = DBUtil.conn().query(sql,id);

		if (result.next()) {

			emp.setEmpname((String)result.fetch("name"));
			emp.setEmpsalary((Double) result.fetch("salary"));
			emp.setEmppost(JobDao.con().Inquire((Integer) result.fetch("job_id")));
			emp.setEmpdate(((Date) result.fetch("hiredate")).toString());

		}

		return emp;
	}

	//获得员工对象列表
	public ArrayList<Emp> Emplist() {

		ArrayList<Emp> emps = new ArrayList<Emp>();

		try{
			String sql="SELECT * FROM employee";
			Result result =DBUtil.conn().query(sql);
			while(result.next())
			{
				Emp emp = new Emp();
				emp.setEmpID((Integer) result.fetch("id"));
				emp.setEmpname((String) result.fetch("name"));
				emp.setEmpsalary((Double) result.fetch("salary"));
				emp.setEmppost(JobDao.con().Inquire((Integer)result.fetch("job_id")));
				emp.setEmpdate(((Date) result.fetch("hiredate")).toString());
				emp.setEmpdept(DeptService.getInstance().getDeptById((Integer)result.fetch("dept_id")));

				emps.add(emp);
			}
		} catch (DBException e) {
			e.printStackTrace();
		}
		return emps;
	}
	
	//获得指定部门的员工对象列表
	public ArrayList<Emp> Emplist(Dept dept) {

		ArrayList<Emp> emps = new ArrayList<Emp>();

		try{
			String sql="SELECT * FROM employee where dept_id=?";
			Result result =DBUtil.conn().query(sql, dept.getId());
			while(result.next())
			{
				Emp emp = new Emp();
				emp.setEmpID((Integer) result.fetch("id"));
				emp.setEmpname((String) result.fetch("name"));
				emp.setEmpsalary((Double) result.fetch("salary"));
				emp.setEmppost(JobDao.con().Inquire((Integer)result.fetch("job_id")));
				emp.setEmpdate(((Date) result.fetch("hiredate")).toString());
				emp.setEmpdept(DeptService.getInstance().getDeptById((Integer)result.fetch("dept_id")));

				emps.add(emp);
			}
		} catch (DBException e) {
			e.printStackTrace();
		}
		return emps;
	}



}
