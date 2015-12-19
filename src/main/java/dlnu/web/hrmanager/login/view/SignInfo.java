package dlnu.web.hrmanager.login.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.dept.service.DeptService;
import dlnu.web.hrmanager.login.model.SignTableModel;
import dlnu.web.hrmanager.util.database.DBException;

public class SignInfo {
	private JComboBox ComDept;
	private JComboBox ComMouth;
	List<Dept> depts;
	private JTable t;
	public SignInfo(){
		JFrame f = new JFrame();
		SignTableModel mt=new SignTableModel();
		f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));
		JPanel Panel = new JPanel();
		JLabel JMouth=new JLabel("月份");
		JLabel JDept=new JLabel("部门");
		ComMouth=new JComboBox();
		ComMouth.addItem("全部");
		for(int i=1;i<13;i++){
			ComMouth.addItem(i);
		}
			
		ComDept=new JComboBox();
		ComDept.addItem("全部");
		try {
			depts = DeptService.getInstance().getDeptList();
			
			for (int i=0; i<depts.size(); i++) {
				ComDept.addItem(depts.get(i));
			}
		} catch (DBException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	
		ComMouth.setSelectedItem("全部");
		ComDept.setSelectedItem("全部");
		Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Panel.add(JMouth);
		Panel.add(ComMouth);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 30, 0, 0));
		Panel.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Panel.add(JDept);
		Panel.add(ComDept);
		t=new JTable(mt);
		JScrollPane s = new JScrollPane(t);
		
		//让表格中的员工工号居中对齐
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		t.setDefaultRenderer(Integer.class, r);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(0);
		splitPane.setEnabled(false);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		f.getContentPane().add(splitPane);
		
		splitPane.setLeftComponent(Panel);
		splitPane.setRightComponent(s);
		
		
		f.setTitle("查看员工签到表");
		f.pack();
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		f.setSize(750, 400);
		
		ComDept.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					if (e.getItem().getClass() == String.class) {
						mt.setDept(null);
					} else {
						mt.setDept((Dept)e.getItem());
					}
					
					mt.update();
					t.updateUI();
				}
			}
		});
		
		ComMouth.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {

				if(e.getStateChange()==ItemEvent.SELECTED){
					int EmpId=0;
					if (e.getItem().getClass() == String.class) {
						mt.setMonth(0);
					} 
					else {
						int month= (int) e.getItem();
						mt.setMonth(month);
					}

					mt.update();
					t.updateUI();
				}
			}
	});

}
	
		public static void main(String args[]) {

			new SignInfo();
		}
	}


