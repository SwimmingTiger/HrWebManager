<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>登录 - 企业员工管理系统</title>
	<link rel="stylesheet" href="../resources/css/main.css">
	<script src="../resources/js/main.js"></script>
</head>
<body>
<form action="login" method="post">
	<p>用户名：<input name="username"></p>
	<p>密　码：<input name="password" type="password"></p>
	<p>类　型：<select name="loginType">
		<option value="2">员工</option>
		<option value="1">管理员</option>
	</select></p>
	<p><input type="submit" value="登录"></p>
</form>
<p class="success">${success}</p>
<p class="error">${error}</p>
</body>
</html>