package dlnu.web.hrmanager.util.database;

import java.sql.*;

public class PreResult extends Result {
	protected PreparedStatement stmt = null;
	
	public PreResult(PreparedStatement stmt, ResultSet rs) {
		super(stmt, rs);
		this.stmt = stmt;
	}
	
	public PreResult(PreparedStatement stmt) {
		super(stmt, null);
		this.stmt = stmt;
	}
	
	public boolean bind(int index, Object var) {
		try {
			stmt.setObject(index, var);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public int exec() throws DBException {
		try {
			int count = 0;
			count = stmt.executeUpdate();
			
			return count;
		} catch (SQLException ex) {
			throw new DBException("执行查询失败：" + ex.getMessage());
		}
	}
	
	public void query() throws DBException {
		try {
			rs = stmt.executeQuery();
			
		} catch (SQLException ex) {
			throw new DBException("执行查询失败：" + ex.getMessage());
		}
	}
	
	public Object fetch(int index) throws DBException {
		if (rs == null) {
			throw new DBException("请先执行query()方法");
		}
		
		return super.fetch(index);
	}
	
	public Object fetch(String index) throws DBException {
		if (rs == null) {
			throw new DBException("请先执行query()方法");
		}
		
		return super.fetch(index);
	}
	
	public boolean next() throws DBException {
		if (rs == null) {
			throw new DBException("请先执行query()方法");
		}
		
		return super.next();
	}
	
	public int getLastId() {
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
}
