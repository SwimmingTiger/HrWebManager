package dlnu.web.hrmanager.util;

import javax.swing.JOptionPane;

/***
 * 窗口工具类
 * 
 * @author 彭逸豪
 *
 */
public class Window {
	public static void showPlainDialog(String title, String content) {
		JOptionPane.showMessageDialog(null, content, title, JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void showInfoDialog(String title, String content) {
		JOptionPane.showMessageDialog(null, content, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showErrorDialog(String title, String content) {
		JOptionPane.showMessageDialog(null, content, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static boolean showYesNoDialog(String title, String content) {
		int chose = JOptionPane.showConfirmDialog(null, content, title, JOptionPane.YES_NO_OPTION);
		
		return chose == JOptionPane.YES_OPTION;
	}
	
	public static String showInputDialog(String message) {
		return JOptionPane.showInputDialog(message);
	}
}
