package dlnu.web.hrmanager.dept.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.dept.service.DeptService;
import dlnu.web.hrmanager.employee.dao.EmpDao;
import dlnu.web.hrmanager.employee.entity.Emp;
import dlnu.web.hrmanager.employee.view.EmpView;
import dlnu.web.hrmanager.util.database.DBException;

/***
 * 部门编辑对话框
 * 
 * @author 彭逸豪 with WindowBuilder
 *
 */
public class DeptEditView extends JDialog {
	
	/***
	 * 操作类型：编辑
	 */
	protected static final int OP_EDIT = 0;
	/***
	 * 操作类型：新增
	 */
	protected static final int OP_ADD = 1;
	
	/***
	 * 操作类型
	 * 
	 * 默认为新增
	 */
	protected int op = OP_ADD;
	
	/***
	 * 操作的部门对象
	 */
	protected Dept dept;

	private JPanel contentPane;
	private JTextField txtDeptId;
	private JTextField txtDeptName;
	private JTextField txtDeptAddress;
	private JTextField txtManagerId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeptEditView frame = new DeptEditView("新建部门");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	protected DeptEditView() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDeptId = new JLabel("\u90E8\u95E8\u7F16\u53F7\uFF1A");
		lblDeptId.setBounds(21, 23, 65, 15);
		contentPane.add(lblDeptId);
		
		JLabel lblDeptName = new JLabel("\u90E8\u95E8\u540D\u79F0\uFF1A");
		lblDeptName.setBounds(21, 58, 65, 15);
		contentPane.add(lblDeptName);
		
		JLabel lblDeptAddress = new JLabel("\u90E8\u95E8\u5730\u5740\uFF1A");
		lblDeptAddress.setBounds(21, 92, 65, 15);
		contentPane.add(lblDeptAddress);
		
		JLabel lblManagerId = new JLabel("\u603B\u7ECF\u7406id\uFF1A");
		lblManagerId.setBounds(186, 23, 65, 15);
		contentPane.add(lblManagerId);
		
		txtDeptId = new JTextField();
		txtDeptId.setEditable(false);
		txtDeptId.setBounds(83, 20, 93, 21);
		contentPane.add(txtDeptId);
		txtDeptId.setColumns(10);
		
		txtDeptName = new JTextField();
		txtDeptName.setBounds(83, 55, 324, 21);
		contentPane.add(txtDeptName);
		txtDeptName.setColumns(10);
		
		txtDeptAddress = new JTextField();
		txtDeptAddress.setColumns(10);
		txtDeptAddress.setBounds(83, 89, 324, 21);
		contentPane.add(txtDeptAddress);
		
		txtManagerId = new JTextField();
		txtManagerId.setColumns(10);
		txtManagerId.setBounds(250, 20, 93, 21);
		contentPane.add(txtManagerId);
		
		JButton btnSave = new JButton("\u4FDD\u5B58");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSave();
			}
		});
		btnSave.setBounds(108, 136, 93, 23);
		contentPane.add(btnSave);
		
		JButton btnCacel = new JButton("\u53D6\u6D88");
		btnCacel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCacel.setBounds(221, 136, 93, 23);
		contentPane.add(btnCacel);
		
		JButton btnSelectManager = new JButton("\u9009\u62E9");
		btnSelectManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ent) {
				doSelectManager();
			}
		});
		btnSelectManager.setBounds(351, 19, 65, 23);
		contentPane.add(btnSelectManager);
	}
	
	/***
	 * 修改部门
	 * @param title
	 * @param dept
	 */
	public DeptEditView(String title, Dept dept) {
		this(); //调用无参构造方法
		this.setTitle(title);
		this.dept = dept;
		txtDeptId.setText(new Integer(dept.getId()).toString());
		txtDeptName.setText(dept.getName());
		txtDeptAddress.setText(dept.getAddress());
		txtManagerId.setText(new Integer(dept.getManager().getEmpID()).toString());
		this.op = OP_EDIT;
	}
	
	/***
	 * 新建部门
	 * @param title
	 */
	public DeptEditView(String title) {
		this(); //调用无参构造方法
		this.setTitle(title);
		dept = new Dept();
		
		try {
			int id = DeptService.getInstance().getNextId();
			dept.setId(id);
			txtDeptId.setText(new Integer(id).toString());
		} catch (DBException e) {
			e.printStackTrace();
			txtDeptId.setText("自动生成");
		}
		
		this.op = OP_ADD;
	}
	
	protected void doSave() {
		switch (this.op) {
		case OP_ADD:
			doAdd();
			break;
		case OP_EDIT:
			doEdit();
			break;
		default:
			dlnu.web.hrmanager.util.Window.showErrorDialog("未知选项", "内部数据错误，请重新打开本窗口！");
			break;
		}
	}
	
	protected class FieldNotComplete extends Exception {
		FieldNotComplete(String msg) {
			super(msg);
		}
	}
	
	protected class ExecuteFailed extends Exception {
		ExecuteFailed(String msg) {
			super(msg);
		}
	}
	
	protected void doAdd() {
		try {
			
			if (txtDeptName.getText().equals("")) {
				throw new FieldNotComplete("部门名称不能为空");
			}
		
			dept.setName(txtDeptName.getText());
		
			/*if (txtManagerId.getText().equals("")) {
				throw new FieldNotComplete("总经理id不能为空");
			}*/
		
			int manager_id;
			
			try {
				manager_id = new Integer(txtManagerId.getText());
			} catch (Exception e) {
				manager_id = 0;
			}
			
			Emp manager = new EmpDao().getEmpById(manager_id, dept);
			
			/*if (manager.getEmpname() == null) {
				throw new FieldNotComplete("id为" + manager_id + "的总经理不存在");
			}*/
			
			dept.setManager(manager);
			
			dept.setAddress(txtDeptAddress.getText());
			
			if (DeptService.getInstance().addDept(dept)) {
				dlnu.web.hrmanager.util.Window.showPlainDialog("添加成功", "添加成功");
				dispose();
			} else {
				throw new ExecuteFailed("请稍候再试");
			}
			
		} catch (DBException e) {
			e.printStackTrace();
			dlnu.web.hrmanager.util.Window.showErrorDialog("数据库错误", e.getMessage());
		} catch (FieldNotComplete e) {
			dlnu.web.hrmanager.util.Window.showInfoDialog("填写不完整", e.getMessage());
		} catch (ExecuteFailed e) {
			e.printStackTrace();
			dlnu.web.hrmanager.util.Window.showErrorDialog("添加失败", e.getMessage());
		}
	}
	
	protected void doEdit() {
		try {
			
			if (txtDeptName.getText().equals("")) {
				throw new FieldNotComplete("部门名称不能为空");
			}
		
			dept.setName(txtDeptName.getText());
		
			/*if (txtManagerId.getText().equals("")) {
				throw new FieldNotComplete("总经理id不能为空");
			}*/
		
			int manager_id;
			
			try {
				manager_id = new Integer(txtManagerId.getText());
			} catch (Exception e) {
				manager_id = 0;
			}
			
			Emp manager = new EmpDao().getEmpById(manager_id, dept);
			
			/*if (manager.getEmpname() == null) {
				throw new FieldNotComplete("id为" + manager_id + "的总经理不存在");
			}*/
			
			dept.setManager(manager);
			
			dept.setAddress(txtDeptAddress.getText());
			
			if (DeptService.getInstance().editDept(dept)) {
				dlnu.web.hrmanager.util.Window.showPlainDialog("修改成功", "修改成功");
				dispose();
			} else {
				throw new ExecuteFailed("请稍候再试");
			}
			
		} catch (DBException e) {
			e.printStackTrace();
			dlnu.web.hrmanager.util.Window.showErrorDialog("数据库错误", e.getMessage());
		} catch (FieldNotComplete e) {
			dlnu.web.hrmanager.util.Window.showInfoDialog("填写不完整", e.getMessage());
		} catch (ExecuteFailed e) {
			e.printStackTrace();
			dlnu.web.hrmanager.util.Window.showErrorDialog("修改失败", e.getMessage());
		}
	}
	
	/***
	 * 执行选择总经理
	 */
	protected void doSelectManager() {
		ManagerSelectView managerView = new ManagerSelectView(dept, this);
		managerView.setLocationRelativeTo(this);
		managerView.setModal(true);
		managerView.setVisible(true);
	}
	
	/***
	 * 设置总经理
	 */
	public void setManager(Emp manager) {
		dept.setManager(manager);
		
		if (manager != null) {
			txtManagerId.setText(new Integer(manager.getEmpID()).toString());
		}
	}
}
