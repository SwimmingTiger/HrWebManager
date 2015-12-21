<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>编辑部门</title>
	<link rel="stylesheet" href="../resources/css/main.css">
	<script src="../resources/js/main.js"></script>
</head>
<body>
<table class="itemsTable">
	<tr>
		<th>编号</th>
		<th>名称</th>
		<th>地址</th>
		<th>总经理</th>
		<th>操作</th>
	</tr>
	<tr>
	<form action="edit" method="post">
		<input type="hidden" name="id" value="${dept.id}">
		<td>${dept.id}</td>
		<td><input class="name" name="name" value="${dept.name}"></td>
		<td><input class="address" name="address" value="${dept.address}"></td>
		<td>
			<select name="manager">
			<c:forEach var="manager" items="${managerList}">
				<option value="${manager.empID}" ${manager.empID == dept.manager.empID ? 'selected' : ''}>
					${manager.empname}
				</option>
			</c:forEach>
			<c:if test="${empty managerList}">
				<option value="0">-</option>
			</c:if>
			</select>
		</td>
		<td>
			<input type="submit" name="save" value="保存">
			<input type="button" value="关闭" onclick="CloseWindow()">
		</td>
	</form>
	</tr>
</table>

<p class="success">${success}</p>
<p class="error">${error}</p>
</body>
</html>
