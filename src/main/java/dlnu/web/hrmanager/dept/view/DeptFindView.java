package dlnu.web.hrmanager.dept.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.employee.entity.Emp;
import dlnu.web.hrmanager.util.Window;

/***
 * 部门查找对话框
 * 
 * @author 彭逸豪 with WindowBuilder
 *
 */
public class DeptFindView extends JDialog {
	private JTextField txtKeyWord;
	private DeptTableView deptView = null;
	private JComboBox cbxFindType;
	private JButton btnReset;
	
	public DeptFindView(DeptTableView deptView) {
		setResizable(false);
		this.deptView = deptView;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("\u67E5\u627E\u90E8\u95E8");
		setSize(465, 74);
		getContentPane().setLayout(null);
		
		cbxFindType = new JComboBox();
		cbxFindType.setModel(new DefaultComboBoxModel(new String[] {"部门编号","部门名称","部门地址","经理姓名"}));
		cbxFindType.setSelectedIndex(1);
		cbxFindType.setBounds(10, 10, 87, 21);
		getContentPane().add(cbxFindType);
		
		txtKeyWord = new JTextField();
		txtKeyWord.setBounds(107, 10, 167, 21);
		getContentPane().add(txtKeyWord);
		txtKeyWord.setColumns(10);
		
		JButton btnFind = new JButton("\u67E5\u627E");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ent) {
				doFind();
			}
		});
		btnFind.setBounds(284, 9, 72, 23);
		getContentPane().add(btnFind);
		
		btnReset = new JButton("\u91CD\u7F6E");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doReset();
			}
		});
		btnReset.setBounds(366, 9, 72, 23);
		getContentPane().add(btnReset);
	}

	public static void main(String[] args) {
		DeptFindView deptFindView = new DeptFindView(null);
		deptFindView.setVisible(true);
	}
	
	/***
	 * 执行查找
	 */
	private void doFind() {
		try {
			Dept cond = new Dept();
			String findType = (String) cbxFindType.getSelectedItem();

			// Java 7 新特性: switch中对String的支持
			switch (findType) {
			case "部门编号":
				cond.setId(new Integer(txtKeyWord.getText()));
				break;
			case "部门名称":
				cond.setName(txtKeyWord.getText());
				break;
			case "部门地址":
				cond.setAddress(txtKeyWord.getText());
				break;
			case "经理姓名":
				Emp manager = new Emp();
				manager.setEmpname(txtKeyWord.getText());
				cond.setManager(manager);
				break;
			}

			deptView.updateTable(cond);
			
		} catch (NumberFormatException e) {
			Window.showInfoDialog("请输入数字", "部门编号应为一个整数");
		}
	}
	
	/***
	 * 执行重置
	 */
	private void doReset() {
		deptView.updateTable();
	}
}
