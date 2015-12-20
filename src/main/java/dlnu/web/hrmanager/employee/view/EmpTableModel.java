package dlnu.web.hrmanager.employee.view;

import java.sql.Date;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import dlnu.web.hrmanager.dept.service.DeptService;
import dlnu.web.hrmanager.employee.dao.EmpDao;
import dlnu.web.hrmanager.employee.entity.Emp;
import dlnu.web.hrmanager.job.dao.JobDao;
import dlnu.web.hrmanager.job.entity.Job;
import dlnu.web.hrmanager.util.DBUtil;
import dlnu.web.hrmanager.util.database.DBException;
import dlnu.web.hrmanager.util.database.Result;

public class EmpTableModel extends AbstractTableModel{

	String title[]={"员工编号","员工姓名","员工工资","员工岗位","入职日期","部门"};
	ArrayList<Emp> emps;

	public EmpTableModel() {
		EmpDao dao = new EmpDao();
		emps=dao.Emplist();
	}
	public void Update() {
		EmpDao dao = new EmpDao();
		emps=dao.Emplist();
	}

	public String getColumnName(int column){  
		return title[column];
	} 
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return title.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub

		return emps.size();
	}



	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return emps.get(rowIndex).getEmpID();
		case 1:
			return emps.get(rowIndex).getEmpname();
		case 2:
			return emps.get(rowIndex).getEmpsalary();
		case 3:
			return emps.get(rowIndex).getEmppost().getName();
		case 4:
			return emps.get(rowIndex).getEmpdate();
		case 5:
			return emps.get(rowIndex).getEmpdept().getName();

		default:
			return null;
		}

	}

}
