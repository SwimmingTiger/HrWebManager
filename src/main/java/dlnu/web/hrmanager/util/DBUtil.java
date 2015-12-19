package dlnu.web.hrmanager.util;

import dlnu.web.hrmanager.util.database.*;

public class DBUtil {
	protected static Database db = null;
	protected static String dbHost = "localhost";
	protected static String dbUser = "hrmanager";
	protected static String dbPass = "hrmanager";
	protected static String dbName = "hrmanager";
	
	public static Database conn() throws DBException {
		if (db == null) {
			db = new Mysql(dbHost, dbName, dbUser, dbPass);
		}
		
		return db;
	}
	
	/***
	 * 关闭数据库连接
	 * 
	 * 立即关闭数据库连接并释放占用的JDBC资源。
	 * 
	 * 注意：不要直接在conn()方法返回的Database对象上调用close()，
	 * 这样做将导致以后从conn()方法返回的Database对象不可用（均为已被关闭的对象）。
	 * 如果确实要提前关闭数据库连接，请调用本方法。（对结果集对象调用close()不受影响。）
	 */
	public static void close() {
		if (db != null) {
			db.close();
			db = null;
		}
	}
}
