package dlnu.web.hrmanager.util.database;

import java.sql.*;

public class Result {
	protected Statement stmt = null;
	protected ResultSet rs = null;
	
	public Result(Statement stmt, ResultSet rs) {
		this.stmt =stmt;
		this.rs = rs;
	}
	
	/***
	 * 关闭结果集
	 * 
	 * 立即关闭结果集和语句对象，释放占用的JDBC资源。
	 * 
	 * 注意：结果集关闭后不可重新打开，意即不能再通过此结果集对象获取查询结果。
	 */
	public void close()
	{
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				//忽略异常
			}
		}
		
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				//忽略异常
			}
		}
	}
	
	public Object fetch(int index) throws DBException {
		try {
			if (rs == null) {
				throw new DBException("结果集为空");
			}
			
			return rs.getObject(index);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public Object fetch(String index) throws DBException {
		try {
			if (rs == null) {
				throw new DBException("结果集为空");
			}
			
			return rs.getObject(index);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public boolean next() throws DBException {
		try {
			if (rs == null) {
				throw new DBException("结果集为空");
			}
			
			return rs.next();
		} catch (SQLException e) {
			return false;
		}
	}
}
