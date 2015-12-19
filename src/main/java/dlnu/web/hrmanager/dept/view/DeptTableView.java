package dlnu.web.hrmanager.dept.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.dept.model.DeptTableModel;
import dlnu.web.hrmanager.dept.service.DeptService;
import dlnu.web.hrmanager.util.Window;
import dlnu.web.hrmanager.util.database.DBException;

/***
 * 部门表格窗口
 * 
 * @author 彭逸豪  with WindowBuilder
 */
public class DeptTableView extends JFrame {

	private JPanel contentPane;
	private JSplitPane splitPane;
	private JTable tblDept;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	//部门表的数据模型
	private DeptTableModel deptTableModel;
	private JButton btnFind;
	private JButton btnRefresh;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeptTableView frame = new DeptTableView();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/***
	 * 刷新表格数据（按条件查找）
	 */
	public void updateTable(Dept cond) {
		try {
			deptTableModel.update(cond);
		} catch (DBException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		tblDept.updateUI();
	}
	
	/***
	 * 刷新表格数据
	 */
	public void updateTable() {
		updateTable(null);
	}

	/**
	 * Create the frame.
	 */
	public DeptTableView() {
		setTitle("\u90E8\u95E8\u5217\u8868");
		//实例化数据模型
		try {
			deptTableModel = new DeptTableModel();
		} catch (DBException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "数据库错误", JOptionPane.ERROR_MESSAGE);
		}
		addComponentListener(new ComponentAdapter() {
			@Override
			//窗口大小改变事件
			public void componentResized(ComponentEvent e) {
				//自动改变分割条位置
				splitPane.setDividerLocation(getHeight()-88);
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 578, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		//窗口居中
		setLocationRelativeTo(null);
		
		splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(50);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		tblDept = new JTable();
		tblDept.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblDept.setFont(new Font("宋体", Font.PLAIN, 12));
		tblDept.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblDept);
		//设置部门表的数据模型
		tblDept.setModel(deptTableModel);
		
		JPanel pnlToolBar = new JPanel();
		splitPane.setRightComponent(pnlToolBar);
		pnlToolBar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		//设置分割条大小
		splitPane.setDividerSize(5);
		
		btnAdd = new JButton("\u6DFB\u52A0");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ent) {
				doAddDept();
			}
		});
		pnlToolBar.add(btnAdd);
		
		btnEdit = new JButton("\u4FEE\u6539");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ent) {
				doEditDept();
			}
		});
		pnlToolBar.add(btnEdit);
		
		btnDelete = new JButton("\u5220\u9664");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ent) {
				doDelDept();
			}
		});
		pnlToolBar.add(btnDelete);
		
		btnFind = new JButton("\u67E5\u627E");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ent) {
				doFindDept();
			}
		});
		pnlToolBar.add(btnFind);
		
		btnRefresh = new JButton("\u5237\u65B0");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ent) {
				doRefresh();
			}
		});
		pnlToolBar.add(btnRefresh);
		
		this.setVisible(true);
	}
	
	/***
	 * 执行添加部门操作
	 */
	protected void doAddDept() {
		DeptEditView editDept = new DeptEditView("添加部门");
		//子窗口关闭后刷新本窗口中表格
		editDept.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				updateTable();
			}
		});
		editDept.setLocationRelativeTo(this);
		editDept.setModal(true);
		editDept.setVisible(true);
	}

	/***
	 * 执行修改部门操作
	 */
	protected void doEditDept() {
		int selectedRow = tblDept.getSelectedRow();
		
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "请选择要修改的部门", "未选择部门", JOptionPane.INFORMATION_MESSAGE);
		} else {
			Dept dept = deptTableModel.getDept(selectedRow);
			DeptEditView editDept = new DeptEditView("修改部门", dept);
			//子窗口关闭后刷新本窗口中表格
			editDept.addWindowListener(new WindowAdapter() {
				@Override
				public void windowDeactivated(WindowEvent e) {
					updateTable();
				}
			});
			editDept.setLocationRelativeTo(this);
			editDept.setModal(true);
			editDept.setVisible(true);
		}
	}
	
	/***
	 * 执行删除部门操作
	 */
	protected void doDelDept() {
		int selectedRow = tblDept.getSelectedRow();
		
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "请选择要删除的部门", "未选择部门", JOptionPane.INFORMATION_MESSAGE);
		} else {
			Dept dept = deptTableModel.getDept(selectedRow);
			
			if (Window.showYesNoDialog("是否删除", "您确定要删除“" + dept.getName() + "”吗？")) {
				try {
					if (DeptService.getInstance().empCount(dept) > 0) {
						throw new Exception("请先删除所有员工或转移至其他部门");
					}
					
					DeptService.getInstance().delDept(dept);
					Window.showInfoDialog("删除成功", "部门已删除");
					updateTable();
					
				} catch (DBException e) {
					Window.showErrorDialog("删除失败", "数据库错误" + e.getMessage());
				} catch (Exception e) {
					Window.showErrorDialog("删除失败", e.getMessage());
				}
				
				
			}
		}
	}
	
	/***
	 * 执行查找部门操作
	 */
	protected void doFindDept() {
		DeptFindView findView = new DeptFindView(this);
		findView.setLocationRelativeTo(this);
		findView.setModal(true);
		findView.setVisible(true);
	}
	
	/***
	 * 执行刷新操作
	 */
	protected void doRefresh() {
		updateTable();
	}
}
