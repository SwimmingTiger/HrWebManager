<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>
</head>
<body>
<h1>
	Hello no Login User!  
</h1>
喵呜！
<!-- 使用EL表达式显示日期  -->
<P>  The time on the server is ${serverTime}. </P>

<h3>emps:</h3>
<table>
	<tr>
		<th>姓名</th>
		<th>薪水</th>
	</tr>
<!-- 使用 JSTL标签forEach循环显示员工  -->
<c:forEach var="emp" items="${empList}">
	<tr>
		<td>${emp.empname}</td>
		<td>${emp.empsalary}</td>
	</tr>
</c:forEach>
</table>
</body>
</html>
