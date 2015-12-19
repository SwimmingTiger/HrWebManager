package dlnu.web.hrmanager.login.view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dlnu.web.hrmanager.login.dao.LoginDao;
import dlnu.web.hrmanager.util.DBUtil;
import dlnu.web.hrmanager.util.Window;
import dlnu.web.hrmanager.util.database.DBException;
import dlnu.web.hrmanager.util.database.PreResult;

public class Login  extends JFrame{
	protected static String username = null;
	protected static String password = null;
	protected static int k=2;
	protected String Selected;
	
	JLabel JLogname=new JLabel("登录名:");
	JLabel JLogpassword=new JLabel("登录密码:");
	JLabel Jrole=new JLabel("角色:");
	JComboBox dtype=new JComboBox();
	JTextField TexName=new JTextField(10);
	JPasswordField TexPassword=new JPasswordField(10);
	JButton BuLogin=new JButton("登录");
	JButton BuCancle=new JButton("取消");
	JFrame Jframe=new JFrame("管理系统");
	
	

	public static String getUsername() {
		return username;
	}

	public static int getUserType() {
		return k;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Login windows=new Login(); 
	}

		public Login(){
			setTitle("企业信息管理系统");
			dtype.setBounds(122, 112, 113, 30);
			dtype.addItem("管理员");
			dtype.addItem("普通员工");
			dtype.setSelectedItem("普通员工");
			this.setBounds(300, 200, 332, 282);
			this.setVisible(true);
			getContentPane().setLayout(null);
			JLogname.setBounds(25, 22, 70, 30);
			getContentPane().add(JLogname);
			TexName.setBounds(112, 23, 147, 30);
			getContentPane().add(TexName);
			JLogpassword.setBounds(25, 62, 113, 37);
			getContentPane().add(JLogpassword);
			TexPassword.setBounds(112, 66, 147, 30);
			getContentPane().add(TexPassword);
			Jrole.setBounds(25, 109, 95, 37);
			getContentPane().add(Jrole);
			getContentPane().add(dtype);
			BuLogin.setBounds(58, 172, 80, 37);
			getContentPane().add(BuLogin);
			BuCancle.setBounds(179, 172, 80, 37);
			getContentPane().add(BuCancle);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setLocationRelativeTo(null);
			
			//取消按钮添加监听
			this.BuCancle.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					boolean result=Window.showYesNoDialog("", "确定退出管理系统");
					if(result==true){
						System.exit(0);
					}
				}
			});
			
			//下拉框添加监听
			this.dtype.addItemListener(new ItemListener(){

				@Override
				public void itemStateChanged(ItemEvent e) {
					
					if(e.getStateChange()==ItemEvent.SELECTED){
						 Selected=e.getItem().toString();
						 if(Selected.equals("管理员")){
							 k=1;
						 }
						 if(Selected.equals("普通员工")){
							 k=2;
						 }
					}
				}
				
			});
			
			//登录按钮添加监听
			this.BuLogin.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent ent){
					try {
						password = new String(TexPassword.getPassword());
						LoginDao dao = new LoginDao();
						if(Login.getUserType()==1){
							if (dao.Login(getUserType(), TexName.getText(), password)) {
								//System.out.println("登陆成功");
								Login.username = TexName.getText();
								AdminMenu login=new AdminMenu();
								dispose();
							} else {
								Window.showInfoDialog("", "登陆失败");
							}
						} else if(Login.getUserType()==2){
							if (dao.Login(getUserType(), TexName.getText(), password)) {
								//System.out.println("登陆成功");
								Login.username = TexName.getText();
								EmpMenu Emp=new EmpMenu();
								dispose();
							} else {
								Window.showInfoDialog("", "登陆失败");
							}
						}
						
					} catch (DBException e) {
						Window.showErrorDialog("数据库错误", e.getMessage());
					}
				}
			});
			
		}	
}


	


