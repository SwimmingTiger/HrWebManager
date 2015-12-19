package dlnu.web.hrmanager.employee.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.dept.service.DeptService;
import dlnu.web.hrmanager.employee.dao.EmpDao;
import dlnu.web.hrmanager.employee.entity.Emp;
import dlnu.web.hrmanager.job.dao.JobDao;
import dlnu.web.hrmanager.job.entity.Job;
import dlnu.web.hrmanager.util.DBUtil;
import dlnu.web.hrmanager.util.database.DBException;

import java.awt.Color;

import javax.swing.ListSelectionModel;

import java.awt.Component;
import java.util.List;
import java.awt.Rectangle;
import java.awt.Button;
import java.awt.Checkbox;

public class EmpView extends JFrame{

	JFrame Jframe=new JFrame("员工管理");
	//员工表格
	JTable tblemp = new JTable();
	//表格模型
	EmpTableModel etm;

	//实例化带滚动的表格
	JScrollPane pnlemp= new JScrollPane(tblemp);
	JPanel p = new JPanel();

	//员工标签
	JLabel lblEmpNo = new JLabel("员工编号：");
	JLabel lblEmpName = new JLabel("员工姓名：");
	JLabel lblEmpSalary = new JLabel("员工工资：");
	JLabel lblEmpPost = new JLabel("员工岗位编号：");
	JLabel lblEmpDate = new JLabel("入职日期：");
	JLabel lblEmpDept = new JLabel("\u90E8\u95E8\uFF1A");
	JTextField txtEmpNo = new JTextField();
	JTextField txtEmpName = new JTextField();
	JTextField txtEmpSalary = new JTextField();
	JTextField txtEmpDate = new JTextField();
	JButton butAdd = new JButton("添加");
	JButton butMod = new JButton("修改");
	JButton butDel = new JButton("删除");
	JButton butQue = new JButton("查询");
	JButton butSave = new JButton("保存");
	JPanel centerPane = new JPanel();
	protected String oper;
	private JTextField txtstate;

	//部门下拉框
	JComboBox ComDept = new JComboBox();
	//员工下拉框
	JComboBox ComJob = new JComboBox();
	//部门列表
	List<Dept> depts;
	//员工列表
	List<Job> jobs;

	//下拉框内容分配
	public void SelectSign()
	{
		try {
			depts = DeptService.getInstance().getDeptList();
			for (int i=0; i<depts.size(); i++) {
				ComDept.addItem(depts.get(i));
			}
		}
		catch(Exception e) {

		}
		
		try{
			jobs = JobDao.con().Display();
			for (int i=0; i<jobs.size(); i++) {
				ComJob.addItem(jobs.get(i));
			}
		}
		catch(Exception e) {

		}

		ComDept.setBounds(new Rectangle(371, 372, 84, 27));
		ComJob.setBounds(new Rectangle(97, 372, 84, 27));
	}

	//主要布局
	public EmpView()
	{
		//获取页面容器
		Container con = Jframe.getContentPane();



		etm = new EmpTableModel();
		centerPane.setLayout(null);
		butSave.setBounds(485, 409, 77, 23);
		centerPane.add(butSave);
		butSave.setForeground(new Color(51, 204, 102));
		con.add(centerPane,BorderLayout.CENTER);
		tblemp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblemp.setModel(etm);  //与表格模型建立联系
		pnlemp.setForeground(Color.BLACK);
		pnlemp.setBounds(10, 10, 564, 270);
		centerPane.add(pnlemp);
		pnlemp.setPreferredSize(new Dimension(500, 300));
		lblEmpNo.setBounds(10, 293, 77, 21);
		centerPane.add(lblEmpNo);
		SelectSign();
		setTextEnable(false);
		AddListenr();
		Jframe.setSize(600, 481);
		Jframe.setVisible(true);
		//窗口居中
		Jframe.setLocationRelativeTo(null);

	}
	/**
	 * 设置文字框不可以编辑添加组件
	 * */
	public void setTextEnable(boolean enabled)
	{
		txtEmpNo.setBounds(97, 290, 196, 27);
		centerPane.add(txtEmpNo);
		this.txtEmpNo.setEditable(enabled);
		lblEmpName.setBounds(10, 337, 77, 15);
		centerPane.add(lblEmpName);
		txtEmpName.setBounds(97, 331, 196, 27);
		centerPane.add(txtEmpName);
		this.txtEmpName.setEditable(enabled);
		lblEmpSalary.setBounds(307, 296, 77, 15);
		centerPane.add(lblEmpSalary);
		txtEmpSalary.setBounds(371, 291, 202, 27);
		centerPane.add(txtEmpSalary);
		this.txtEmpSalary.setEditable(enabled);
		lblEmpPost.setBounds(10, 376, 91, 18);
		centerPane.add(lblEmpPost);
		lblEmpDate.setBounds(307, 337, 91, 15);
		centerPane.add(lblEmpDate);
		txtEmpDate.setBounds(371, 331, 203, 27);
		centerPane.add(txtEmpDate);
		this.txtEmpDate.setEditable(enabled);
		lblEmpDept.setBounds(332, 378, 84, 15);
		centerPane.add(lblEmpDept);


		centerPane.add(ComDept);
		centerPane.add(ComJob);
	}


	/**
	 * 设置监视器
	 */
	public void AddListenr()
	{

		butAdd.setBounds(137, 409, 77, 23);
		centerPane.add(butAdd);
		butMod.setBounds(224, 409, 77, 23);
		centerPane.add(butMod);
		butDel.setBounds(311, 409, 77, 23);
		centerPane.add(butDel);
		butQue.setBounds(398, 409, 77, 23);
		centerPane.add(butQue);

		JLabel label = new JLabel("\u72B6\u6001\uFF1A");
		label.setBounds(33, 413, 54, 15);
		centerPane.add(label);

		//状态框
		txtstate = new JTextField("无");
		txtstate.setForeground(new Color(255, 0, 0));  //设置状态框字体颜色
		txtstate.setEditable(false);
		txtstate.setBounds(71, 410, 42, 21);
		centerPane.add(txtstate);
		txtstate.setColumns(10);

		butSave.addActionListener(new ActionListener(){

			EmpDao dao =new EmpDao();
			@Override

			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if("add".equals(oper))
				{

					Emp emp1 = new Emp();
					try {
						emp1 = Tool1(1);    //读取文本框的信息

						if(emp1!=null)
						{
							dao.Add(emp1);
							etm.Update();		//刷新表格
							tblemp.updateUI();
							Tool2();
							setTextEnable(false);
							txtstate.setText("无");
						}

					} catch (Exception e1) {
	
						dlnu.web.hrmanager.util.Window.showErrorDialog("添加失败", e1.getMessage());
						txtstate.setText("添加");
					}

				}

				else if("mod".equals(oper))
				{



					try {
						Emp emp2 = new Emp();
						emp2 = Tool1(1);

						if(emp2!=null)
						{
							dao.Mod(emp2);
							etm.Update();
							tblemp.updateUI();
							Tool2();
							
							
						}
					} catch (Exception e1) {
						dlnu.web.hrmanager.util.Window.showErrorDialog("修改失败", e1.getMessage());
						txtstate.setText("修改");

					}

				}
				else
				{
					dlnu.web.hrmanager.util.Window.showErrorDialog("提示", "无效操作");
				}

			}


		});

		butQue.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
				Queview();
			}

		});

		butDel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				int ifadd=JOptionPane.showConfirmDialog(null, "您确认要删除数据么?", "",JOptionPane.YES_NO_OPTION); 

				if(ifadd==JOptionPane.YES_OPTION){

					try {
						Emp emp = new Emp();
						EmpDao dao = new EmpDao();
						emp = Tool1(0);

						dao.Dele(emp);
						etm.Update();
						tblemp.updateUI();

					} catch (Exception e1) {
						// TODO 自动生成的 catch 块
						dlnu.web.hrmanager.util.Window.showErrorDialog("提示", "删除失败");
					} 



				}

			}

		});

		butMod.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				// TODO Auto-generated method stub
				setTextEnable(true);
				txtEmpNo.setEnabled(false);
				txtstate.setText("修改");


				EmpDao dao = new EmpDao();

				Emp emp = new Emp();

				try {
					emp=dao.Inquire(new Integer(txtEmpNo.getText()));
					if (emp==null)
					{
						dlnu.web.hrmanager.util.Window.showErrorDialog("提示", "无法修改当前编号的员工");
						Tool2();
					}
				} catch (NumberFormatException | DBException e1) {
					// TODO 自动生成的 catch 块
				//	e1.printStackTrace();                //忽略该异常
				}

				oper="mod";
				//	txtEmpNo.setEditable(false);
			}

		});

		butAdd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				Tool2();
				setTextEnable(true);
				txtEmpNo.setEditable(false);
				txtstate.setText("添加");
				try {
					txtEmpNo.setText(new Integer(DBUtil.conn().getNextId("employee")).toString());
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				oper="add";
			}});

		tblemp.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e)
			{
				int row=tblemp.getSelectedRow();
				txtEmpNo.setText(((Integer)etm.emps.get(row).getEmpID()).toString());
				txtEmpName.setText(etm.emps.get(row).getEmpname());
				txtEmpSalary.setText(((Double)etm.emps.get(row).getEmpsalary()).toString());

				Job job = etm.emps.get(row).getEmppost();
				
				ComJob.setSelectedItem(job);
				//设置选中的岗位
				txtEmpDate.setText(etm.emps.get(row).getEmpdate());

				Dept dept = etm.emps.get(row).getEmpdept();
				//设置选中的部门
				ComDept.setSelectedItem(dept);

			}

		});


	}


	private Emp Tool1(int i) throws Exception {

		Emp emp = new Emp();
		emp.setEmpID(new Integer(txtEmpNo.getText()));
		emp.setEmpname(txtEmpName.getText());
		emp.setEmpsalary(new Double(txtEmpSalary.getText()));
		emp.setEmppost((Job)ComJob.getSelectedItem());
		emp.setEmpdate(txtEmpDate.getText());
		emp.setEmpdept((Dept)ComDept.getSelectedItem());


		if(i == 1)
		{
		if (new Double(txtEmpSalary.getText())>emp.getEmppost().Get_salary_max()||new Double(txtEmpSalary.getText())<emp.getEmppost().Get_salary_min())
		{
			dlnu.web.hrmanager.util.Window.showErrorDialog("工资不在范围内", "工资范围为"+emp.getEmppost().Get_salary_min()+"到"+emp.getEmppost().Get_salary_max()+"元");
			emp=null;
		}
		}
		return emp;
		// TODO 自动生成的方法存根

	}

	public void Tool2()
	{
		txtEmpNo.setText(null);
		txtEmpName.setText(null);
		txtEmpSalary.setText(null);
		txtEmpDate.setText(null);

	}

	public void Queview()
	{
		JLabel lblque = new JLabel("员工编号查询：");
		final JTextField txtque = new JTextField(15);
		JButton btnOK = new JButton("确定");

		final JFrame que = new JFrame();
		Container con1 = que.getContentPane();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();		
		Box baseBox=Box.createVerticalBox();
		con1.add(baseBox);
		p1.setLayout(new FlowLayout());
		p1.add(lblque);
		p1.add(txtque);
		p2.add(btnOK);
		baseBox.add(p1);
		baseBox.add(p2);
		que.setSize(250, 140);
		que.setVisible(true);
		//窗口居中
		que.setLocationRelativeTo(null);

		btnOK.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根

				EmpDao dao =new EmpDao();
				Emp emp = new Emp();

				try {
					int i=new Integer(txtque.getText());
					emp=dao.Inquire(i);
					txtEmpNo.setText(((Integer)emp.getEmpID()).toString());
					txtEmpName.setText(emp.getEmpname());
					txtEmpSalary.setText(((Double)emp.getEmpsalary()).toString());
					txtEmpDate.setText(emp.getEmpdate());

					que.dispose();				//关闭窗口
				} catch (Exception e1) {
					dlnu.web.hrmanager.util.Window.showErrorDialog("提示", "查询失败");
				}

			}

		});

	}

	public static void main(String args[]) {
		new EmpView();
	}


}
