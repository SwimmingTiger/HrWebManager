package dlnu.web.hrmanager.job.view;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import dlnu.web.hrmanager.job.dao.JobDao;
import dlnu.web.hrmanager.job.entity.Job;

public class JobTableModel extends AbstractTableModel{
	
	ArrayList<Job> joblist;
	String name[] = {"岗位编号", "岗位名称", "岗位最低工资" ,"岗位最高工资", "岗位平均工资"};
	
	public JobTableModel(){
		update();
	}
	
	public void update(){
		joblist = JobDao.con().Display();
	}
	
	//返回某一行
	public Job getRow(int rowIndex) {
		return joblist.get(rowIndex);
	}

	public int getRowCount() {
		// TODO 自动生成的方法存根
		
		return joblist.size();
	}

	public int getColumnCount() {
		// TODO 自动生成的方法存根
		return name.length;
	}
	
	public String getColumnName(int columnIndex) {
		return name[columnIndex];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Job job = getRow(rowIndex);
		DecimalFormat doubleFormat = new DecimalFormat("#.00");
		
		switch (columnIndex) {
		case 0:
			return job.Get_id();
		case 1:
			return job.Get_name();
		case 2:
			return job.Get_salary_min();
		case 3:
			return job.Get_salary_max();
		case 4:
			if(job.Get_salary_avg() == -1){
				return "暂无员工";
			}else{
				return doubleFormat.format(job.Get_salary_avg());
		//		return job.Get_salary_avg;()
			}
		default:
			return null;
		}
		
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
	//	if (columnIndex == 1)
	//		return true;
        return false;
    }
	
}

