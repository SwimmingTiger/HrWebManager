<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>出错啦！</title>
	<link rel="stylesheet" href="../resources/css/main.css">
	<script src="../resources/js/main.js"></script>
</head>
<body>
<p class="error">${error}</p>
<p><input type="button" value="关闭" onclick="CloseWindow()"></p>
</body>
</html>
