package dlnu.web.hrmanager.dept.service;

import java.util.List;

import dlnu.web.hrmanager.dept.dao.DeptDao;
import dlnu.web.hrmanager.dept.entity.Dept;
import dlnu.web.hrmanager.util.DBUtil;
import dlnu.web.hrmanager.util.database.DBException;

/***
 * 部门服务类
 * 
 * 单例类，通过getInstance()方法获取本类的唯一实例。
 * 
 * @author 老虎会游泳
 *
 */
public class DeptService {
	protected DeptDao dao;
	protected static DeptService instance = null;
	
	protected DeptService() throws DBException {
		dao = new DeptDao();
	}
	
	/***
	 * 获取唯一的DeptService实例
	 * 
	 * @throws DBException
	 */
	public static DeptService getInstance() throws DBException {
		if (instance == null) {
			instance = new DeptService();
		}
		
		return instance;
	}
	
	public Dept getDeptById(int id) throws DBException {
		return dao.getDeptById(id);
	}
	
	public List<Dept> getDeptList() throws DBException {
		return dao.getDeptList();
	}
	
	/***
	 * 按条件查找部门
	 * 
	 * @param cond
	 * @return
	 * @throws DBException
	 * @see DeptDAO.getDeptList(Dept cond)
	 */
	public List<Dept> getDeptList(Dept cond) throws DBException {
		return dao.getDeptList(cond);
	}
	
	/***
	 * 获取下一个（即将插入的）部门id
	 * 
	 * @return
	 * @throws DBException
	 */
	public int getNextId() throws DBException {
		return dao.getNextId();
	}
	
	public boolean addDept(Dept dept) throws DBException {
		return dao.addDept(dept);
	}
	
	public boolean editDept(Dept dept) throws DBException {
		return dao.editDept(dept);
	}
	
	public boolean delDept(Dept dept) throws DBException {
		return dao.delDept(dept);
	}
	
	public int empCount(Dept dept) {
		return dao.empCount(dept);
	}
}
