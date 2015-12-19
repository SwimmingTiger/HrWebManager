package dlnu.web.hrmanager.login.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.login.entity.Signin;
import dlnu.web.hrmanager.login.view.Login;
import dlnu.web.hrmanager.util.DBUtil;
import dlnu.web.hrmanager.util.Window;
import dlnu.web.hrmanager.util.database.DBException;
import dlnu.web.hrmanager.util.database.PreResult;
import dlnu.web.hrmanager.util.database.Result;

/***
 * 登录模块数据库操作类
 * 
 * @author 孙大鹏
 */
public class LoginDao {
	/***
	 * 获得签到记录列表
	 * 
	 * @param month 指定月份（0为全部）
	 * @param dept 指定部门（null为全部）
	 * @return 符合指定条件的签到记录列表
	 */
	public List<Signin> getSigninList(int month, Dept dept) {
		List<Signin> signinList = new ArrayList<Signin>();
		List<Object> argList = new ArrayList<Object>();
		Signin signin = null;

		String sql = "SELECT signin.id,emp_id,name,signin_time,signout_time FROM signin,employee WHERE signin.emp_id=employee.id";

		if (month != 0) {
			sql = sql + " AND month(signin_time) = ?";
			argList.add(month);
		}

		if (dept != null) {
			sql = sql + " AND emp_id IN (SELECT id FROM employee WHERE dept_id = ?)";
			argList.add(dept.getId());
		}

		try {
			PreResult prt = DBUtil.conn().prepare(sql);

			for (int i=0; i<argList.size(); i++) {
				prt.bind(i+1, argList.get(i));
			}

			prt.query();

			while (prt.next()) {
				signin = new Signin();
				signin.setId((Integer)prt.fetch("id"));
				signin.setEmp_id((Integer)prt.fetch("emp_id"));
				signin.setEmp_name((String)prt.fetch("name"));
				signin.setSignin_time((Timestamp) prt.fetch("signin_time"));
				signin.setSignout_time((Timestamp) prt.fetch("signout_time"));
				signinList.add(signin);
			}

		} catch (DBException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		return signinList;
	}

	/***
	 * 修改密码
	 * 
	 * @param loginType 登录类型（1普通员工，2管理员）
	 * @param username 用户名
	 * @param newpassword 新密码
	 * @return 修改是否成功（true成功，false失败）
	 * @throws DBException 数据库异常
	 */
	public boolean ChangePw(int loginType, String username, String newpassword) throws DBException{
		int effectedLine = 0;
		
		if(loginType==1){
			effectedLine = DBUtil.conn().exec("Update admin set password=? where username=?",username, newpassword);
		} else if(loginType==2){
			effectedLine = DBUtil.conn().exec("Update employee set password=? where name=?", username, newpassword);
		}
		
		return effectedLine > 0;
	}

	/***
	 * 用户登录
	 * 
	 * @param loginType 登录类型（（1普通员工，2管理员））
	 * @param username 用户名
	 * @param password 密码
	 * @return 登录是否成功（true成功，false失败）
	 * @throws DBException 数据库异常
	 */
	public boolean Login(int loginType, String username, String password) throws DBException {
		boolean status = false;

		if(loginType == 1) {
			PreResult prt = DBUtil.conn().query("SELECT * FROM admin WHERE username=? AND password=?", username, password);
			if (prt.next()) {
				status = true;
			}
		} else if (loginType == 2) {
			PreResult prt = DBUtil.conn().query("SELECT * FROM employee WHERE name=? AND password=?", username, password);
			if (prt.next()) {
				status = true;
			}
		}


		return status;
	}
	
	/***
	 * 得到上次签到的Id
	 * 
	 * @param empId 员工id
	 * @return 今日签到的id。若今日未签到，返回0.
	 * @throws DBException 数据库异常
	 */
	public int getLastSigninId(int empId) throws DBException {
		int id = 0;
		
		Result prt = DBUtil.conn().query("SELECT id from signin WHERE emp_id=? and datediff(now(),signin_time)=0", empId);
		
		if(prt.next()){
			try {
				id = (int)prt.fetch("id");
			} catch (NullPointerException e) {
				id = 0;
			}
		}
		
		return id;
	}
	
	/***
	 * 根据员工名和密码得到员工id
	 * 
	 * @param empName 员工名
	 * @param password 密码
	 * @return 该员工的id。若未找到，返回0.
	 * @throws DBException 数据库异常
	 */
	public int getEmpId(String empName, String password) throws DBException {
		int empId = 0;
		PreResult prt = DBUtil.conn().query("select id from employee where name=? AND password=?", empName, password);
		
		if (prt.next()) {
			empId=(Integer)prt.fetch("id");
		}
		
		return empId;
	}
	
	/***
	 * 员工签到
	 * 
	 * @param empId 员工id
	 * @return 签到记录id（若今日已签到，则签到失败，返回0）
	 * @throws DBException 数据库异常
	 */
	public int empSignin(int empId) throws DBException {
		int sign_id = 0;
		
		//未找到本日的签到记录
		if (getLastSigninId(empId) == 0) {
			PreResult insertPrt = DBUtil.conn().prepare("insert into signin(emp_id,signin_time) values(?,now())", empId);
			insertPrt.exec();
			sign_id = insertPrt.getLastId();
		}
		
		return sign_id;
	}

	/***
	 * 员工签退
	 * 
	 * @param sign_id 签到记录id
	 * @return 签退是否成功（true成功，false失败）
	 * @throws DBException 数据库异常
	 */
	public boolean empSignout(int sign_id) throws DBException {
		int effectLine = DBUtil.conn().exec("update signin set signout_time=now() where id=? and signout_time is null",sign_id);
		
		if (effectLine > 0) {
			return true;
		} else {
			return false;
		}
	}
}
