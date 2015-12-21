<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>部门列表</title>
	<link rel="stylesheet" href="../resources/css/main.css">
	<script src="../resources/js/main.js"></script>
</head>
<body>
<h3>部门列表：</h3>
<table class="itemsTable">
	<tr>
		<th>编号</th>
		<th>名称</th>
		<th>地址</th>
		<th>总经理</th>
		<th>最高工资</th>
		<th>平均工资</th>
		<th>最低工资</th>
		<th>操作</th>
	</tr>
<!-- 使用 JSTL标签forEach循环显示部门  -->
<c:forEach var="dept" items="${deptList}">
	<tr>
		<td>${dept.id}</td>
		<td>${dept.name}</td>
		<td>${dept.address}</td>
		<td>${dept.manager.empname}</td>
		<td>${dept.salary_max}</td>
		<td>${dept.salary_avg}</td>
		<td>${dept.salary_min}</td>
		<td>
		<form action="delete" method="post">
			<input type="hidden" name="id" value="${dept.id}">
			<input type="submit" onclick="return DeleteConfirm('${dept.name}')" value="删除">
		</form>
		</td>
	</tr>
</c:forEach>
	<tr>
	<form action="add" method="post">
		<td>${nextId}</td>
		<td><input class="name" name="name"></td>
		<td><input class="address" name="address"></td>
		<td>-</td>
		<td>-</td>
		<td>-</td>
		<td>-</td>
		<td><input type="submit" name="save" value="保存"></td>
	</form>
	</tr>
</table>

<p class="success">${success}</p>
<p class="error">${error}</p>
</body>
</html>
