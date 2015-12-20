package dlnu.web.hrmanager.dept.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.employee.dao.EmpDao;
import dlnu.web.hrmanager.employee.entity.Emp;

public class ManagerTableModel extends AbstractTableModel{

	String title[]={"员工编号","员工姓名","员工岗位"};
	ArrayList<Emp> emps;
	Dept dept;

	public ManagerTableModel(Dept dept) {
		this.dept = dept;
		EmpDao dao = new EmpDao();
		emps=dao.Emplist(dept);
	}
	public void Update() {
		EmpDao dao = new EmpDao();
		emps=dao.Emplist(dept);
	}
	
	public Emp getRow(int rowIndex) {
		return emps.get(rowIndex);
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
			return emps.get(rowIndex).getEmppost().getName();

		default:
			return null;
		}

	}

}
