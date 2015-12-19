package dlnu.web.hrmanager.login.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import dlnu.web.hrmanager.login.dao.LoginDao;
import dlnu.web.hrmanager.util.DBUtil;
import dlnu.web.hrmanager.util.Window;
import dlnu.web.hrmanager.util.database.DBException;

class EmpMenu extends JFrame{
	JButton JSignin1=new JButton("签到");
	JButton JSignout=new JButton("签退");
	JButton Jchangepw=new JButton("修改密码");
	JFrame Jframe=new JFrame("签到系统");
	int sign_id = 0;
	int empId = 0;
	LoginDao dao = new LoginDao();

	public static void main(String args[]){
		EmpMenu Emp=new EmpMenu();
	}

	public EmpMenu(){
		setTitle("\u5458\u5DE5\u83DC\u5355");
		getContentPane().setLayout(null);
		JSignout.setBounds(44, 27, 253, 55);
		getContentPane().add(JSignout);
		JSignin1.setBounds(44, 92, 253, 55);
		getContentPane().add(JSignin1);
		Jchangepw.setBounds(44, 157, 253, 55);
		getContentPane().add(Jchangepw);
		this.setVisible(true);
		this.setBounds(300, 300, 357, 278);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		try {
			empId = dao.getEmpId(Login.username,Login.password);
			sign_id = dao.getLastSigninId(empId);
		} catch (DBException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		}


		this.Jchangepw.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				ChangePwGui ChangePw=new ChangePwGui();
			}
		});	

		this.JSignin1.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){ 
				long Date=0;
				try {
					if(sign_id == 0){
						sign_id = dao.empSignin(empId);
						Window.showInfoDialog("", "签到成功");
					}
					else{
						Window.showInfoDialog("", "你已经签到！");
					}
				}

				catch (DBException e1) {
					Window.showErrorDialog("数据库错误", e1.getMessage());
				}
			}
		});

		this.JSignout.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){

				try {

					if (sign_id == 0) {
						Window.showInfoDialog("", "请先签到");
						return;
					}

					boolean ok = dao.empSignout(sign_id);
					
					if (ok) {
						Window.showInfoDialog("", "签退成功");
					} else {
						Window.showInfoDialog("", "您已经签退");
					}

				} catch (DBException e1) {
					Window.showErrorDialog("数据库错误", e1.getMessage());
				}

			}
		});
	}
}
