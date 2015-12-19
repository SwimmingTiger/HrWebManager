package dlnu.web.hrmanager.login.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.login.dao.LoginDao;
import dlnu.web.hrmanager.login.entity.Signin;

public class SignTableModel extends AbstractTableModel {
	String[] n = {"工号", "姓名", "签到时间", "签退时间", "迟到", "早退"};
	List<Signin> signinList = null;
	LoginDao dao = new LoginDao();
	int month = 0;
	Dept dept = null;
	
	public SignTableModel() {
		update();
	}
	
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public void update() {
		signinList = dao.getSigninList(month, dept);
	}
	
	public int getColumnCount() {
		return n.length;
	}
	public int getRowCount() {
		return signinList.size();
	}
	public String getColumnName(int col) {
		return n[col];
	}
	public Object getValueAt(int row, int col) {
		Signin signin = signinList.get(row);
		switch (col) {
		case 0:
			return signin.getEmp_id();
		case 1:
			return signin.getEmp_name();
		case 2:
			return signin.getSignin_time().toLocaleString();
		case 3:
			if (signin.getSignout_time() != null) {
				return signin.getSignout_time().toLocaleString();
			} else {
				return "未签退";
			}
		case 4:
			if(signin.getSignin_time().getHours()>8){         //9点为上班时间
				return true;
			}
			else{
				return false;
			}
			
		case 5:
			if(signin.getSignout_time() == null || signin.getSignout_time().getHours()<17){            //5点为下班时间
				return true;
			}
			else{
				return false;
			}
			
		default:
			return null;
		}
	}
	
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
	
}
