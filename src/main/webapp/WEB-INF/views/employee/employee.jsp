<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>Employee</title>
</head>
<body>

<table>
	<tr>
		<th>ID</th>
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
		<form action="delete" method="post">
			<input type="hidden" name="ID" value="${emp.empID }">
			<input type="submit" value="删除">
		</form>
		</td>
		<td>
		<form action="edit" method="post">
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
		<td><input name="empdate"></td>
		<td>
		<select name="dept">
			<c:forEach var="dept" items="${deptList}">
			<option value="${dept.id}">${dept.name}</option>
			</c:forEach>
		</select>
		</td>
		<td><input type="submit" value="插入"></td>
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
		<td><input name="empdate" value="${editEmp.empdate }"></td>
		<td>
		<select name="dept">
			<c:forEach var="dept" items="${deptList}">
			<option value="${dept.id}">${dept.name}</option>
			</c:forEach>
		</select>
		</td>
		
		<td><input type="submit" value="保存"></td>
	</form>
</c:otherwise>
</c:choose>
	</tr>
</table>

</body>
</html>
