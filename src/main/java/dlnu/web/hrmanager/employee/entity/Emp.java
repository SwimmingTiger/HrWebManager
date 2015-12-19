package dlnu.web.hrmanager.employee.entity;

import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.job.entity.Job;

/***
 * 员工实体
 * 
 * @author 黄英淏
 *
 */
public class Emp {
	private int empID; //员工id
	private String empname; //员工名
	private double empsalary; //工资
	private Job emppost; //工作岗位
	private String empdate; //入职日期
	private Dept empdept; //所属部门
	
	public int getEmpID() {
		return empID;
	}
	public void setEmpID(int empID) {
		this.empID = empID;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public double getEmpsalary() {
		return empsalary;
	}
	public void setEmpsalary(double empsalary) {
		this.empsalary = empsalary;
	}
	public Job getEmppost() {
		return emppost;
	}
	public void setEmppost(Job emppost) {
		this.emppost = emppost;
	}
	public String getEmpdate() {
		return empdate;
	}
	public void setEmpdate(String empdate) {
		this.empdate = empdate;
	}
	public Dept getEmpdept() {
		return empdept;
	}
	public void setEmpdept(Dept empdept) {
		this.empdept = empdept;
	}


}
