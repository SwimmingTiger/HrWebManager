 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>全部职业</title>
	<link rel="stylesheet" href="../resources/css/main.css">
	<script src="../resources/js/main.js"></script>
</head>
<body>
<h3>职业列表：</h3>
<table  class="itemsTable">
	<tr>
		<th>编号</th>
		<th>岗位名称</th>		
		<th>平均工资</th>
		<th>最高工资</th>		
		<th>最低工资</th>		
	</tr>
<!-- 使用 JSTL标签forEach循环显示员工  -->
<c:forEach var="job" items="${DisplayJob}">
	<tr>
		<td>${job.id}</td>
		<td>${job.name}</td>
		<td>${-1 == job.salaryavg ? '-' : job.salaryavg}</td>
		<td>${job.salarymax}</td>
		<td>${job.salarymin}</td>
		<td>
			<form class="toolButton" action="del" method="post">
				<input type="submit"  value = "删除" onclick="return DeleteConfirm('${job.name}')">
				<input type="hidden" name="id" value = "${job.id}">					
			</form>

			<form class="toolButton" action="edit" method="post">
				<input type="submit" value="修改">
				<input type="hidden" name="编号" value = "${job.id}">
			</form>
		</td>
	</tr>
</c:forEach>

	<form action="${JobEdit==null ? 'add' : 'editSave'}" method="post">
			<td><input type="hidden" name = "编号" value="${JobEdit.id}"></td>			
			<td><input name = "岗位名称" value="${JobEdit.name}"></td>		
			<td>-</td>		
			<td><input name = "最高工资" value="${JobEdit.salarymax}"></td>		
			<td><input name = "最低工资" value="${JobEdit.salarymin}"></td>
			<td>
			<c:if test="${JobEdit == null}">
				<input type="submit" value="添加">
			</c:if>
			<c:if test="${JobEdit != null}">
				<input type="submit" value="保存">
			</c:if>
			
				<input type="reset" value="重置">
			</td>
	</form>
</table>

<p class="success">${success}</p>
<p class="error">${error}</p>
</body>
</html>
