package dlnu.web.hrmanager.util.database;

import java.sql.*;

public abstract class Database {
	protected Connection conn = null; //数据库连接
	protected int lastId = -1; //插入语句产生的自增id
	
	public Database(String driver, String dbUrl) throws DBException {
		conn(driver, dbUrl);
	}
	
	/***
	 * 关闭数据库连接
	 * 
	 * 立即关闭数据库连接，释放占用的JDBC资源。
	 * 
	 * 注意：连接关闭后不可重新打开，意即不能再通过此Database对象执行SQL语句。
	 */
	public void close()
	{
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				//忽略异常
			}
		}
	}
	
	protected void conn(String driver, String dbUrl) throws DBException {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl);
			
		} catch (ClassNotFoundException ex) {
			//驱动未找到
			throw new DBException("JDBC驱动程序 " + driver + " 未找到！");
		} catch (SQLException ex) {
			throw new DBException("连接数据库失败：" + ex.getMessage());
		}
	}
	
	public int exec(String sql) throws DBException {
		try {
			int count = 0;
			
			Statement stmt = conn.createStatement();
			count = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			lastId = getLastId(stmt);
			stmt.close();
			
			return count;
			
		} catch (SQLException ex) {
			throw new DBException("执行查询失败：" + ex.getMessage());
		}
	}
	
	public int exec(String sql, Object... args) throws DBException {
		PreResult prt = prepare(sql, args);
		int effectLine = prt.exec();
		lastId = prt.getLastId();
		prt.close();
		return effectLine;
	}
	
	protected int getLastId(Statement stmt) {
		int lastId = -1;
		
		try {
			ResultSet rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				lastId = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//忽略异常
		}
		
		return lastId;
	}
	
	public Result query(String sql) throws DBException {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			Result result = new Result(stmt, rs);
			
			return result;
		} catch (SQLException ex) {
			throw new DBException("执行查询失败：" + ex.getMessage());
		}
	}
	
	public PreResult query(String sql, Object... args) throws DBException {
		PreResult prt = prepare(sql, args);
		prt.query();
		return prt;
	}
	
	public PreResult prepare(String sql, Object... args) throws DBException {
		try {
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			PreResult result = new PreResult(stmt);
			
			for (int i=0; i<args.length; i++) {
				result.bind(i+1, args[i]);
			}
			
			return result;
		} catch (SQLException ex) {
			throw new DBException("执行查询失败：" + ex.getMessage());
		}
	}

	/***
	 * 获取表中下一个自增值
	 * 
	 * @param tableName
	 * @return
	 */
	public abstract int getNextId(String tableName) throws DBException;
	
	/***
	 * 获取上次插入的自增id
	 * 
	 * @param tableName
	 * @return
	 */
	public int getLastId() {
		return lastId;
	}
}
