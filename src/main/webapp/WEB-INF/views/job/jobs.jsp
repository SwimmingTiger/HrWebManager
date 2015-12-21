 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>全部职业</title>
	<link rel="stylesheet" href="../resources/css/main.css">
</head>
<body>
<h1>
	Hello no Login User!  
</h1>
喵呜！fdfdsxfdtdfgtdtgtddrt
<!-- 使用EL表达式显示日期  -->
<P>  The time on the server is ${serverTime}. </P>

<h3>jobs:</h3>
<table  class="itemsTable">
	<tr>
		<th>编号</th>
		<th>岗位名称</th>		
		<th>平均工资</th>
		<th>最大工资</th>		
		<th>最小工资</th>		
	</tr>
<!-- 使用 JSTL标签forEach循环显示员工  -->
<c:forEach var="job" items="${DisplayJob}">
	<tr>
		<td>${job.id}</td>
		<td>${job.name}</td>
		<td>${job.salaryavg}</td>
		<td>${job.salarymax}</td>
		<td>${job.salarymin}</td>
		<td>
			<form action="del" method="post">
				<input type="submit"  value = "删除">
				<input type="hidden" name="id" value = "${job.id}">					
			</form>

			<form action="edit" method="post">
				<input type="submit" value="修改">
				<input type="hidden" name="id" value = "${job.id}">
			</form>
		</td>
	</tr>
</c:forEach>

	<form action="add" method="post">
			<td><input name = "编号"></td>
			<td><input name = "岗位名称"></td>
			<td><input name = "平均工资"></td>
			<td><input name = "最高工资"></td>
			<td><input name = "最低工资"></td>
			<td><input type="submit" value="添加"></td>
	</form>
</table>
</body>
</html>
