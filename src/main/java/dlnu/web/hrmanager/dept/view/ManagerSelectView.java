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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.dept.model.ManagerTableModel;
import dlnu.web.hrmanager.dept.service.DeptService;
import dlnu.web.hrmanager.util.Window;
import dlnu.web.hrmanager.util.database.DBException;

public class ManagerSelectView extends JDialog {

	private JPanel contentPane;
	private JSplitPane splitPane;
	private JTable tblManager;
	private JButton btnComfirm;
	//部门表的数据模型
	private ManagerTableModel managerTableModel;
	private JButton btncancel;
	private JButton btnRefresh;
	private Dept dept;
	private DeptEditView editView;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dept dept = new Dept();
					dept.setId(1);
					ManagerSelectView frame = new ManagerSelectView(dept, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManagerSelectView(Dept dept, DeptEditView editView) {
		this.dept = dept;
		this.editView = editView;
		setTitle("\u9009\u62E9\u603B\u7ECF\u7406");
		managerTableModel = new ManagerTableModel(dept);
		addComponentListener(new ComponentAdapter() {
			@Override
			//窗口大小改变事件
			public void componentResized(ComponentEvent e) {
				//自动改变分割条位置
				splitPane.setDividerLocation(getHeight()-88);
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 322, 424);
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
		
		tblManager = new JTable();
		tblManager.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblManager.setFont(new Font("宋体", Font.PLAIN, 12));
		tblManager.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblManager);
		//设置部门表的数据模型
		tblManager.setModel(managerTableModel);
		
		JPanel pnlToolBar = new JPanel();
		splitPane.setRightComponent(pnlToolBar);
		pnlToolBar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		//设置分割条大小
		splitPane.setDividerSize(5);
		
		btnComfirm = new JButton("\u786E\u5B9A");
		btnComfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ent) {
				doComfirm();
			}
		});
		pnlToolBar.add(btnComfirm);
		
		btncancel = new JButton("\u53D6\u6D88");
		btncancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ent) {
				doCancel();
			}
		});
		pnlToolBar.add(btncancel);
		
		btnRefresh = new JButton("\u5237\u65B0");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ent) {
				doRefresh();
			}
		});
		pnlToolBar.add(btnRefresh);
	}
	
	protected void doCancel() {
		dispose();
	}
	
	protected void doRefresh() {
		managerTableModel.Update();
		tblManager.updateUI();
	}
	
	protected void doComfirm() {
		int selected = tblManager.getSelectedRow();
		
		if (selected != -1) {
			editView.setManager(managerTableModel.getRow(selected));
		}
		
		dispose();
	}

}
