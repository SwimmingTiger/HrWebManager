package dlnu.web.hrmanager.dept.entity;

import dlnu.web.hrmanager.employee.entity.Emp;

/***
 * 部门实体
 * 
 * @author 彭逸豪
 *
 */
public class Dept {
	//部门编号
	protected int id = 0;
	//部门名称
	protected String name = null;
	//部门地址
	protected String address = null;
	//部门经理
	protected Emp manager = null;
	//部门最高工资
	protected double salary_max = 0;
	//部门平均工资
	protected double salary_avg = 0;
	//部门最低工资
	protected double salary_min = 0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Emp getManager() {
		return manager;
	}
	public void setManager(Emp manager) {
		this.manager = manager;
	}
	public double getSalary_max() {
		return salary_max;
	}
	public void setSalary_max(double salary_max) {
		this.salary_max = salary_max;
	}
	public double getSalary_avg() {
		return salary_avg;
	}
	public void setSalary_avg(double salary_avg) {
		this.salary_avg = salary_avg;
	}
	public double getSalary_min() {
		return salary_min;
	}
	public void setSalary_min(double salary_min) {
		this.salary_min = salary_min;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.getClass() == Dept.class && ((Dept)obj).getId() == this.getId();
	}
}
