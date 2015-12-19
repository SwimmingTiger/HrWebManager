package dlnu.web.hrmanager.login.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dlnu.web.hrmanager.login.dao.LoginDao;
import dlnu.web.hrmanager.util.Window;
import dlnu.web.hrmanager.util.database.DBException;

class ChangePwGui extends JFrame{
	
	protected static String Password = null;
	
	JLabel NewPassword=new JLabel("新密码");
	JLabel ConfirmNP=new JLabel("新密码确认");
	JTextField TNewPassword=new JTextField(15);
	JTextField TConfirmNP=new JTextField(15);
	JButton Confirm=new JButton("确认");
	JButton Concle=new JButton("取消");
	JFrame jframe=new JFrame();
	
	public static String getPassword() {
		return Password;
	}
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		ChangePwGui window=new ChangePwGui();
	}
	
	public ChangePwGui(){
		setTitle("\u4FEE\u6539\u5BC6\u7801");
		getContentPane().setLayout(null);
		NewPassword.setBounds(23, 28, 75, 30);
		getContentPane().add(NewPassword);
		TNewPassword.setBounds(124, 25, 134, 38);
		getContentPane().add(TNewPassword);
		ConfirmNP.setBounds(23, 76, 75, 30);
		getContentPane().add(ConfirmNP);
		TConfirmNP.setBounds(124, 73, 134, 38);
		getContentPane().add(TConfirmNP);
		Confirm.setBounds(23, 143, 98, 38);
		getContentPane().add(Confirm);
		Concle.setBounds(148, 143, 98, 38);
		getContentPane().add(Concle);
		this.setVisible(true);
		this.setBounds(300, 300, 300, 239);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		
		this.Confirm.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ent){
			if( (TNewPassword.getText()).equals(TConfirmNP.getText())){
				Password=TNewPassword.getText();
				try {
					LoginDao dao = new LoginDao();
					boolean ok = false;
					
					if(Login.getUserType()==1){
						ok = dao.ChangePw(Login.getUserType(), TNewPassword.getText(), Login.username);
						
					} else if(Login.getUserType()==2){
						ok = dao.ChangePw(Login.getUserType(), TNewPassword.getText(), Login.username);
					}
					
					if (ok) {
						Window.showInfoDialog("", "修改成功");
					} else {
						Window.showInfoDialog("", "修改失败");
					}
				} catch (DBException e) {
					// TODO 自动生成的 catch 块
					Window.showInfoDialog("数据库错误", e.getMessage());
				}
			}
			else{
				Window.showInfoDialog("", "两次输入密码不一样");
			}
			}
		});
		
		this.Concle.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				dispose();
			}
		});
		
		}
}