package dlnu.web.hrmanager.dept.dao;

import java.util.ArrayList;
import java.util.List;

import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.employee.dao.EmpDao;
import dlnu.web.hrmanager.util.DBUtil;
import dlnu.web.hrmanager.util.database.DBException;
import dlnu.web.hrmanager.util.database.Database;
import dlnu.web.hrmanager.util.database.PreResult;
import dlnu.web.hrmanager.util.database.Result;

/***
 * 部门数据库操作类
 * 
 * @author 彭逸豪
 *
 */
public class DeptDao {
	//数据库对象
	protected Database db;
	
	//构造方法
	public DeptDao() throws DBException {
		//获取数据库连接
		db = DBUtil.conn();
	}
	
	//按id获取部门
	public Dept getDeptById(int id) throws DBException {
		Dept dept = new Dept();
		dept.setId(id);
		
		String sql = "SELECT name,address,manager_id FROM dept WHERE id=?";
		PreResult prt = DBUtil.conn().prepare(sql, id);
		prt.query();
		EmpDao empDao = new EmpDao();
		
		if (prt.next()) {
			dept.setName((String)prt.fetch("name"));
			dept.setAddress((String)prt.fetch("address"));
			dept.setManager(empDao.getEmpById((Integer)prt.fetch("manager_id"), dept));
		}
		
		return dept;
	}
	
	/***
	 * 按条件查找部门
	 * 
	 * @param cond 条件（用到的条件包括id、name、address和manager.getName()，任选，可空）
	 * @return 满足条件中所有属性的结果（字符串用 LIKE %内容% 模糊匹配）
	 * @throws DBException
	 */
	public List<Dept> getDeptList(Dept cond) throws DBException {
		List<Dept> depts = new ArrayList<Dept>();
		Dept dept = null;
		String sql = "SELECT id,name,address,manager_id FROM dept WHERE 1";
		List<Object> args = new ArrayList<Object>();
		
		if (cond != null) {
			if (cond.getId() != 0) {
				sql += " AND id=?";
				args.add(cond.getId());
			}
			
			if (cond.getName() != null) {
				sql += " AND name LIKE ?";
				args.add("%" + cond.getName() + "%");
			}
			
			if (cond.getAddress() != null) {
				sql += " AND address LIKE ?";
				args.add("%" + cond.getAddress() + "%");
			}
			
			if (cond.getManager() != null && cond.getManager().getEmpname() != null) {
				sql += " AND manager_id in (SELECT id FROM employee WHERE name LIKE ?)";
				args.add("%" + cond.getManager().getEmpname() + "%");
			}
		}
		
		PreResult prt = DBUtil.conn().prepare(sql);
		
		for (int i=0; i<args.size(); i++) {
			prt.bind(i+1, args.get(i));
		}
		
		prt.query();
		
		while (prt.next()) {
			dept = new Dept();
			dept.setId((Integer)prt.fetch("id"));
			dept.setName((String)prt.fetch("name"));
			dept.setAddress((String)prt.fetch("address"));
			EmpDao empDao = new EmpDao();
			dept.setManager(empDao.getEmpById((Integer)prt.fetch("manager_id"), dept));
			deptSalaryCount(dept);
			depts.add(dept);
		}
		
		return depts;
	}
	
	//获取全部部门列表
	public List<Dept> getDeptList() throws DBException {
		return getDeptList(null);
	}
	
	/***
	 * 统计部门最高、平均、最低工资
	 * 
	 * 统计结果保存在dept对象的属性中
	 * 
	 * @param dept
	 * @throws DBException 
	 */
	public void deptSalaryCount(Dept dept) throws DBException {
		String sql = "SELECT MAX(salary),AVG(salary),MIN(salary) FROM employee WHERE dept_id = ?";
		Result rs = db.query(sql, dept.getId());
		
		if (rs.next()) {
			Double max = (Double) rs.fetch(1);
			Double avg = (Double) rs.fetch(2);
			Double min = (Double) rs.fetch(3);
			
			dept.setSalary_max(max!=null ? max : 0);
			dept.setSalary_avg(avg!=null ? avg : 0);
			dept.setSalary_min(min!=null ? min : 0);
		}
	}
	
	/***
	 * 获取下一个（即将插入的）部门id
	 * 
	 * @return
	 * @throws DBException
	 */
	public int getNextId() throws DBException {
		return db.getNextId("dept");
	}
	
	//添加部门
	public boolean addDept(Dept dept) throws DBException {
		int effectLine = 0;
		
		String sql = "INSERT INTO dept(name, address, manager_id) VALUES(?, ?, ?)";
		effectLine = db.exec(sql, dept.getName(), dept.getAddress(), dept.getManager().getEmpID());
		
		return effectLine > 0;
	}
	
	//编辑部门
	public boolean editDept(Dept dept) throws DBException {
		int effectLine = 0;
		
		String sql = "UPDATE dept SET name=?, address=?, manager_id=? WHERE id=?";
		effectLine = db.exec(sql, dept.getName(), dept.getAddress(), dept.getManager().getEmpID(), dept.getId());
		
		return effectLine > 0;
	}
	
	//删除部门
	public boolean delDept(Dept dept) throws DBException {
		int effectLine = 0;
		
		String sql = "DELETE FROM dept WHERE id=?";
		effectLine = db.exec(sql, dept.getId());
		
		return effectLine > 0;
	}
	
	//部门员工统计
	public int empCount(Dept dept) {
		int count = 0;
		
		try {
			String sql = "SELECT count(*) FROM employee WHERE dept_id = ?";
			Result rs = db.query(sql, dept.getId());
			
			if (rs.next()) {
				count = ((Long) rs.fetch(1)).intValue();
			}
		} catch (DBException e) {
			//忽略异常
		}
		
		return count;
	}
}
