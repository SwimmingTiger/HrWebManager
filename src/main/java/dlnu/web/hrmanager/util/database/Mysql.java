package dlnu.web.hrmanager.util.database;

import java.math.BigInteger;

public class Mysql extends Database {
	protected static String driver = "com.mysql.jdbc.Driver"; //Mysql驱动类名

	public Mysql(String host, String database, String username, String password) throws DBException {
		super(driver, "jdbc:mysql://" + host + "/" + database + "?useUnicode=true&characterEncoding=UTF-8&user=" + username + "&password=" + password);
	}

	/***
	 * 获取表中下一个自增值
	 * 
	 * @param tableName
	 * @return
	 */
	public int getNextId(String tableName) throws DBException {
		String sql = "select AUTO_INCREMENT from INFORMATION_SCHEMA.TABLES where TABLE_NAME=?";
		Result rt = this.query(sql, tableName);
		
		if (!rt.next()) {
			throw new DBException("表“" + tableName + "”不存在或不含自增字段");
		}
		
		BigInteger id = (BigInteger) rt.fetch(1);
		return id.intValue();
	}
}
