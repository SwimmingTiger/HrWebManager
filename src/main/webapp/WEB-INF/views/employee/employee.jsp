<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>员工管理</title>
	<link rel="stylesheet" href="../resources/css/main.css">
	<script src="../resources/js/main.js"></script>

</head>
<body>
<jsp:include page="../nav/adminNav.jsp"></jsp:include>
<h3>员工列表：</h3>
<table class="itemsTable">
	<tr>
		<th>编号</th>
		<th>姓名</th>
		<th>薪水</th>
		<th>工作岗位</th>
		<th>入职日期</th>
		<th>所属部门</th>
		<th> </th>
	</tr>
<!-- 使用 JSTL标签forEach循环显示员工  -->
<c:forEach var="emp" items="${empList}">
	<tr>
		<td>${emp.empID}</td>
		<td>${emp.empname}</td>
		<td>${emp.empsalary}</td>
		<td>${emp.emppost.name}</td>
		<td>${emp.empdate}</td>
		<td>${emp.empdept.name}</td>
		<td>
		<form class="toolButton" action="delete" method="post">
			<input type="hidden" name="ID" value="${emp.empID }">
			<input type="submit" onclick="return DeleteConfirm('${emp.empname}')" value="删除">
		</form>
		<form class="toolButton" action="edit" method="post">
		<input type="hidden" name="ID" value="${emp.empID }">
		<input type="submit" value="编辑">
		</form>
		</td>
	</tr>
</c:forEach>
	<tr>
<c:choose>
<c:when test="${empty editEmp}">
	<form action="add" method="post">
		<td></td>
		<td><input name="empname"></td>
		<td><input name="empsalary"></td>
		<td>
		<select name="job">
			<c:forEach var="job" items="${jobList}">
			<option value="${job.id}">${job.name}</option>
			</c:forEach>
		</select>
		</td>
		<td><input name="empdate" class="datepicker"></td>
		<td>
		<select name="dept">
			<c:forEach var="dept" items="${deptList}">
			<option value="${dept.id}">${dept.name}</option>
			</c:forEach>
		</select>
		</td>
		<td>
			<input type="submit" value="添加">
			<input type="reset" value="重置">
		</td>
	</form>
</c:when>
<c:otherwise>
	<form action="save" method="post">
		<input type="hidden" name="ID" value="${editEmp.empID }">
		<td></td>
		<td><input name="empname" value="${editEmp.empname} "></td>
		<td><input name="empsalary" value="${editEmp.empsalary }"></td>
		<td>
		<select name="job">
			<c:forEach var="job" items="${jobList}">
			<option value="${job.id}">${job.name}</option>
			</c:forEach>
		</select>
		</td>
		<td><input name="empdate" value="${editEmp.empdate}" class="datepicker"></td>
		<td>
		<select name="dept">
			<c:forEach var="dept" items="${deptList}">
			<option value="${dept.id}">${dept.name}</option>
			</c:forEach>
		</select>
		</td>
		
		<td>
			<input type="submit" value="保存">
			<input type="reset" value="重置">
		</td>
	</form>
</c:otherwise>
</c:choose>
	</tr>
</table>

<p class="success">${success}</p>
<p class="error">${error}</p>

<script>

</script>
</body>
</html>
