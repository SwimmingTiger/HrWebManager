package dlnu.web.hrmanager.login.entity;

import java.util.Date;

/***
 * 签到记录对象
 * 
 * @author 孙大鹏
 *
 */
public class Signin {
	protected int id; //签到记录id
	protected int emp_id; //员工id
	protected String emp_name; //员工姓名
	protected Date signin_time; //签到时间
	protected Date signout_time; //签退时间
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public Date getSignin_time() {
		return signin_time;
	}
	public void setSignin_time(Date signin_time) {
		this.signin_time = signin_time;
	}
	public Date getSignout_time() {
		return signout_time;
	}
	public void setSignout_time(Date signout_time) {
		this.signout_time = signout_time;
	}
	
}
