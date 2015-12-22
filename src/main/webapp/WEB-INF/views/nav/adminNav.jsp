<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>

<h4>欢迎，${username}，这是管理员面板。<a href="../login/exit">退出</a></h4>
<nav>
	<a href="../admin/main">首页</a>
	-
	<a href="../employee/list">员工管理</a>
	-
	<a href="../job/list">岗位管理</a>
	-
	<a href="../dept/list">部门管理</a>
</nav>