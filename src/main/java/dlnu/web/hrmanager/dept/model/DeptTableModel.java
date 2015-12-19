package dlnu.web.hrmanager.dept.model;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.dept.service.DeptService;
import dlnu.web.hrmanager.util.database.DBException;


public class DeptTableModel implements TableModel {
	
	protected final String[] columnNames = {"编号", "名称", "地址", "总经理", "最高工资", "平均工资", "最低工资"};
	protected List<Dept> depts = null;
	
	public DeptTableModel() throws DBException {
		depts = DeptService.getInstance().getDeptList();
	}
	
	/***
	 * 重新从数据库读取数据
	 * @return 
	 * @throws DBException 
	 */
	public void update() throws DBException {
		depts = DeptService.getInstance().getDeptList();
	}
	
	/***
	 * 按条件查找
	 * @return 
	 * @throws DBException 
	 */
	public void update(Dept cond) throws DBException {
		depts = DeptService.getInstance().getDeptList(cond);
	}

	public int getRowCount() {
		return depts.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public Dept getDept(int rowIndex) {
		return depts.get(rowIndex);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Object obj = null;
		Dept dept = getDept(rowIndex);
		DecimalFormat df=new DecimalFormat("#");
		
		switch (columnIndex) {
		case 0:
			obj = dept.getId();
			break;
		case 1:
			obj = dept.getName();
			break;
		case 2:
			obj = dept.getAddress();
			break;
		case 3:
			obj = dept.getManager().getEmpname();
			
			if (obj == null) {
				obj = "(暂缺)";
			}
			break;
		case 4:
			if (dept.getSalary_max() <= 0) {
				obj = "(暂无员工)";
			} else {
				obj = df.format(dept.getSalary_max());
			}
			break;
		case 5:
			if (dept.getSalary_avg() <= 0) {
				obj = "(暂无员工)";
			} else {
				obj = df.format(dept.getSalary_avg());
			}
			break;
		case 6:
			if (dept.getSalary_min() <= 0) {
				obj = "(暂无员工)";
			} else {
				obj = df.format(dept.getSalary_min());
			}
			break;
		default:
			break;
		}
		
		return obj;
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

	}

	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

}
