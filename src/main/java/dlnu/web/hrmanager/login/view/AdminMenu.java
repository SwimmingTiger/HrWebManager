package dlnu.web.hrmanager.login.view;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import dlnu.web.hrmanager.dept.view.DeptTableView;
import dlnu.web.hrmanager.employee.view.EmpView;
import dlnu.web.hrmanager.job.view.JobView;

public class AdminMenu extends JFrame {
	
	JButton JEmp=new JButton("员工管理");
	JButton JDept=new JButton("部门管理");
	JButton JJob=new JButton("岗位管理");
	JButton JSign=new JButton("查看员工签到");
	JButton JChangePW=new JButton("修改密码");
	JFrame jframe=new JFrame();
	
	public AdminMenu(){
		setTitle("\u7BA1\u7406\u5458\u83DC\u5355");
		getContentPane().setLayout(null);
		JEmp.setBounds(58, 21, 271, 43);
		getContentPane().add(JEmp);
		JDept.setBounds(58, 74, 271, 43);
		getContentPane().add(JDept);
		JJob.setBounds(58, 124, 271, 43);
		getContentPane().add(JJob);
		JSign.setBounds(58, 174, 271, 43);
		getContentPane().add(JSign);
		JChangePW.setBounds(58, 224, 271, 43);
		getContentPane().add(JChangePW);
		this.setVisible(true);
		this.setBounds(100, 200, 400,327);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//为员工管理按钮添加监听
		this.JEmp.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				EmpView emp=new EmpView();
			}
			
		});
		
		this.JSign.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				SignInfo SignView=new SignInfo();
			}
			
		});
		
		this.JChangePW.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				ChangePwGui ChanggePw=new ChangePwGui();
			}
			
		});
		
		//为部门管理添加监听
		this.JDept.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				DeptTableView DeptTable=new DeptTableView();
				
			}
			
		});
		
		//为岗位管理添加监听
		this.JJob.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				JobView view = new JobView();
				view.setVisible(true);
			}
			
		});
	}
}
