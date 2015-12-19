package dlnu.web.hrmanager.job.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.print.attribute.IntegerSyntax;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dlnu.web.hrmanager.job.dao.JobDao;
import dlnu.web.hrmanager.job.entity.Job;
import dlnu.web.hrmanager.util.DBUtil;
import dlnu.web.hrmanager.util.Window;
import dlnu.web.hrmanager.util.database.DBException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JobView extends JFrame{
	
	//窗体上、中、下、背景
	JPanel northpanel = new JPanel();
	JPanel centerpanel = new JPanel();
	JPanel centerpanelleft = new JPanel();
	JPanel centerpanelright = new JPanel();
	JPanel centerpanel1 = new JPanel();
	JPanel centerpanel2 = new JPanel();
	JPanel centerpanel3 = new JPanel();
	JPanel centerpanel4 = new JPanel();
	JPanel southpanel = new JPanel();
	JPanel backgroudpanel = new JPanel();
	
	
	//岗位标签
	JLabel lab_jno = new JLabel("岗位编号        ");
	JLabel lab_jna = new JLabel("岗位名称        ");
	JLabel sal_min = new JLabel("岗位最低工资");
	JLabel sal_avg = new JLabel("岗位最高工资");
	//岗位文本框
	JTextField text_jno = new JTextField(15);
	JTextField text_jna = new JTextField(15);
	JTextField text_sal_min = new JTextField(15);
	JTextField text_sal_max = new JTextField(15);

	
	JButton button_add = new JButton("添加");
	JButton button_change = new JButton("修改");
	JButton button_delete = new JButton("删除");
	JButton button_inquiry = new JButton("查询");

	
	//布局
	GridLayout glayout_this = new GridLayout();
	GridLayout glayout_north = new GridLayout();
	BoxLayout blayout_backgroud = new BoxLayout(backgroudpanel, BoxLayout.Y_AXIS);
	BoxLayout blayout_center = new  BoxLayout(centerpanel, BoxLayout.Y_AXIS);
	BoxLayout blayout_centerleft = new BoxLayout(centerpanelleft, BoxLayout.Y_AXIS);
	BoxLayout blayout_centerright = new BoxLayout(centerpanelright, BoxLayout.Y_AXIS);
	FlowLayout flayout_south = new FlowLayout();
	FlowLayout flayout_center1 = new FlowLayout();
	FlowLayout flayout_center2 = new FlowLayout();
	FlowLayout flayout_center3 = new FlowLayout();
	FlowLayout flayout_center4 = new FlowLayout();
	private JTable table;
	JobTableModel model = new JobTableModel();
	
	public JobView() {

		text_jno.setEditable(false);
		ArrayList<Job> joblist = JobDao.con().Display();
		Object name[] = {"岗位编号", "岗位名称", "岗位最低工资" ,"岗位最高工资","岗位平均工资"};
		Object a[][] = new Object[joblist.size()][4];
		for(Integer i = 0; i < joblist.size(); i++){
			a[i][0] = joblist.get(i).id;
			a[i][1] = joblist.get(i).name;
			a[i][2] = joblist.get(i).salary_min;
			a[i][3] = joblist.get(i).salary_max;
		}
		//创建装table，构建table模型，并将rable装入容器jobTablePane中
		table = new JTable(a, name);
		table.setModel(model);
		table.setRowHeight(20);

		JScrollPane jobTablePane = new JScrollPane(table);
		
		//this容器设置布局，添加组件
		getContentPane().setLayout(glayout_this);
		backgroudpanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(backgroudpanel);
		
		//background容器设置布局，添加组件
		backgroudpanel.setLayout(blayout_backgroud);
		backgroudpanel.add(northpanel);
		backgroudpanel.add(centerpanel);
		backgroudpanel.add(southpanel);
		
		//northpanel容器设置布局，添加组件
		northpanel.setLayout(glayout_north);
		northpanel.add(jobTablePane);
		
		//centerpanel,以及容器内的centerpanel、2、3、4 容器设置布局，添加组件
		centerpanel.setLayout(blayout_center);
		centerpanel.add(centerpanelleft);
		centerpanel.add(centerpanelright);
		centerpanel.setSize(200, 100);
		
		centerpanelleft.add(centerpanel1);
		centerpanel1.setLayout(flayout_center1);
		centerpanel1.add(lab_jno);
		centerpanel1.add(text_jno);
		
		centerpanelleft.add(centerpanel2);
		centerpanel2.setLayout(flayout_center2);
		centerpanel2.add(lab_jna);
		centerpanel2.add(text_jna);
		
		centerpanelright.add(centerpanel3);
		centerpanel3.setLayout(flayout_center3);
		centerpanel3.add(sal_min);
		centerpanel3.add(text_sal_min);
		
		centerpanelright.add(centerpanel4);
		centerpanel4.setLayout(flayout_center4);
		centerpanel4.add(sal_avg);	
		centerpanel4.add(text_sal_max);
		
		//southpanel容器设置布局，添加组件
		southpanel.setLayout(flayout_south);
		southpanel.add(button_add);		
		southpanel.add(button_change);
		southpanel.add(button_delete);
		southpanel.add(button_inquiry);

		
		button_add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				try {
					Job job = MakeJob_forAdd();
					if (job != null){
						JobDao.con().Add(job);
						table.updateUI();
						model.update();
					}
				}catch (DBException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		button_change.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				try{
					Job job = MakeJob_forChange();
					if (job != null){	
						JobDao.con().Change(job);
						table.updateUI();
						model.update();
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
		button_delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				Window window = new Window();
	
				try {
					
					if(Check_forDelete()){
						int id = Integer.valueOf(text_jno.getText());
						JobDao.con().Delete(id);
						table.updateUI();
						model.update();		
					}
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
									
				}
			}
		});
		button_inquiry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				Window window = new Window();

				Job job;
				try {
					text_jno.setText(JOptionPane.showInputDialog("员工编号"));
					if (Check_forInquire()){
						int id = Integer.valueOf(text_jno.getText());
						job = JobDao.con().Inquire(id);
						text_jna.setText(job.name);
						text_sal_min.setText(String.valueOf(job.salary_min));
						text_sal_max.setText(String.valueOf(job.salary_max));
					}
				}catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}	
		});
		table.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e){
				int row = table.getSelectedRow();
				text_jno.setText(String.valueOf(model.joblist.get(row).Get_id()));
				text_jna.setText(model.joblist.get(row).Get_name());
				text_sal_min.setText(String.valueOf(model.joblist.get(row).Get_salary_min()));
				text_sal_max.setText(String.valueOf(model.joblist.get(row).Get_salary_max()));
			}			
		});
		
		this.setSize(608, 347);
		this.setLocationRelativeTo(null);
		this.setTitle("工作岗位");
		
	}
	//创建Job类的对象并赋值，检查此对象的数据
	public Job MakeJob_forAdd() throws DBException{
		
		Window window = new Window();
		Job job = new Job();
		
		job.id =  DBUtil.conn().getNextId("job");
		
		//检查是否输入岗位名称
		job.name = text_jna.getText();
		if(this.Check_forSame()){
			boolean x = Window.showYesNoDialog("注意", "岗位名称已存在，确定添加？");
			if(x == false){
				return job = null;
			}
		}
		if(!Check_jna(job.name)){
			window.showErrorDialog("错误", "请输入岗位名称！");
			return job = null;
		}

		//检查岗位工资
		if (!Check_min_max()){
			text_sal_min.setText("");
			text_sal_max.setText("");
			return job = null;
		}else{
			job.salary_min = Double.valueOf(text_sal_min.getText());
			job.salary_max = Double.valueOf(text_sal_max.getText());
		}
		
		return job;
	}
	//创建Job类的对象并赋值，检查此对象的数据
	public Job MakeJob_forChange() throws DBException{
		
		Job job = new Job();
		Window window = new Window();
		
		//检查id
		//检查输入的id类型是否正确
		if(Check_id_forRight()){
			job.id =  Integer.valueOf(text_jno.getText());
		}
		else{
			window.showErrorDialog("错误", "请选择要修改的岗位");
			return job = null;
		}
		
		//检查得到的id是否存在
		if (Check_id_forExist(job.id)){
			//id存在
			job.id = Integer.valueOf(text_jno.getText());
		}else{
			//id不存在
			window.showErrorDialog("错误", "id不存在，请重新输入！");
			text_jno.setText("");
			return job = null;
		}
		
		//检查是否输入岗位名称
		job.name = text_jna.getText();
			//判断是否同名，是否添加
		Job job1 = JobDao.con().Inquire(Integer.valueOf(text_jno.getText()));
		
	//	System.out.println("00000");
		if(this.Check_SName_forChange()){
			//是它本身
			if(job1.salary_min == Double.valueOf(text_sal_min.getText()) && job1.salary_max == Double.valueOf(text_sal_max.getText())){
				//工资没有修改
				return job = null;
			}else{
				//工资修改
			}
		}else{//不是它本身
			//有无重复岗位名称
			if(this.Check_forSame()){
				boolean x = Window.showYesNoDialog("注意", "岗位名称已存在，确定修改？");
				if(x == false){
				//	System.out.println("333000");
					return job = null;
				}
			}
			
			/*
			System.out.println("11000");
			if(job1.salary_min == Double.valueOf(text_sal_min.getText()) && job1.salary_max == Double.valueOf(text_sal_max.getText())){				
				System.out.println("22200");
				boolean x = Window.showYesNoDialog("注意", "岗位名称已存在，确定修改？");
				if(x == false){
					System.out.println("333000");
					return job = null;
				}
			}
			*/
		}
		//检查岗位名称是否为空
		if(!Check_jna(job.name)){
			window.showErrorDialog("错误", "请输入岗位名称！");
			return job = null;
		}
		
		//检查岗位工资
		if (Check_min_max()){
			job.salary_min = Double.valueOf(text_sal_min.getText());
			job.salary_max = Double.valueOf(text_sal_max.getText());
		}else {
			text_sal_min.setText("");
			text_sal_max.setText("");
			return job = null;
		}
		
		return job;
	}
	//检查删除
	public boolean Check_forDelete() throws DBException{
		
		Window window = new Window();
	
		//判断是否选择要删除的岗位，否返回false
		if(!Check_id_forRight()){
			window.showErrorDialog("错误", "请选择要删除的岗位");
			text_jno.setText("");
			return false;
		}
	
		//检查有无此工作岗位
		int id = Integer.valueOf(text_jno.getText());
		if (!Check_id_forExist(id)){
			//此岗位不存在
			window.showErrorDialog("错误", "没有此工作岗位，请重新输入id！");
			text_jno.setText("");
			return false;
		}else{
			//此岗位存在
			//检查此岗位是否有员工，有返回true，无返回false
			if (JobDao.con().Check_id_forDelete(id)){
				//有员工
				window.showErrorDialog("错误", "此岗位有员工，请先删除员工");
				text_jno.setText("");
				return false;
			}else{
				//没有员工
				return true;
			}
		}
	}
	//检查查询
	public boolean Check_forInquire() throws DBException{
		
		Window window = new Window();
		//输入为空则返回false
		if(text_jno.getText().equals("")){
			return false;
		}
		//检查输入类型是否正确
		if(!Check_id_forRight()){
			window.showErrorDialog("错误", "请输入数字");
			text_jno.setText("");
			return false;
		}
		
		//检查有无此工作岗位
		int id = Integer.valueOf(text_jno.getText());
		if (Check_id_forExist(id)){
			return true;
		}else {
			window.showErrorDialog("错误", "此岗位不存在，请重新输入id！");
			text_jno.setText("");
			return false;
		}
	}
	//检查工资
	public boolean Check_min_max(){
		
		Window window = new Window();
		double min = 0;
		double max = 0;
		//判断输入的工资类型是否正确
		try{
			min = Double.valueOf(text_sal_min.getText());
			max = Double.valueOf(text_sal_max.getText());
		}catch(NumberFormatException e){
			window.showErrorDialog("错误", "请在岗位工资栏输入数字");
			//输入类型错误
			return false;
		}
		
		//判断最低工资是否低于最高工资
		if(min < max){
			return true;
		}else{
			window.showErrorDialog("错误", "请重新输入岗位工资！");
			//输入工资大小错误
			return false;
		}
	}
	
	//检查id是否存在
	public boolean Check_id_forExist(int id) throws DBException{
		
		if (JobDao.con().Check_id(id)){
			//存在
			return true;
		}else {
			//不存在
			return false;
		}
	}
	//检查id输入类型是否正确
	public boolean Check_id_forRight(){
		
		int id = 0;
		
		try{
			id = Integer.valueOf(text_jno.getText());
		}catch(NumberFormatException e){
			//类型错误
			return false;
		}
		
		return true;
	}
	//检查岗位名称是否为空
	public boolean Check_jna(String jname){
		
		if(jname.equals("")){
			//空
			return false;
		}else{
			//非空
			return true;
		}
	}
	//检查岗位名称是否同名，同名返回true，否则返回false
	public boolean Check_forSame(){
		
		String jname = text_jna.getText();
	//	int id = Integer.valueOf(text_jno.getText());
		ArrayList<Job> list = JobDao.con().Display();
		
		for(int i = 0; i < list.size(); i++){
			
			if(jname.equals(list.get(i).name)){
				return true;
			}
		}
		
		return false;
	}
	//判断是否修改它本身（不变）
	public boolean Check_SName_forChange() throws DBException{
		
		String jname = text_jna.getText();
		int id = Integer.valueOf(text_jno.getText());
		Job job = JobDao.con().Inquire(id);
		
		if(jname.equals(job.name)){
			return true; //是
		}else{
			return false;//否
		}
	}

	public static void main(String[] args){

		JobView view = new JobView();
		view.setVisible(true);

		System.out.println("this is a test");
		
	}	
}
