<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập</title>
</head>
<body>
	<h2>Form Đăng nhập</h2>

	<c:if test="${not empty error}">
		<p style="color: red;">${error}</p>
	</c:if>
	<c:if test="${param.error == 'need_login'}">
		<p style="color: red;">Vui lòng đăng nhập để tiếp tục!</p>
	</c:if>
	<c:if test="${param.message == 'logged_out'}">
		<p style="color: green;">Bạn đã đăng xuất thành công!</p>
	</c:if>

	<form action="<c:url value='/login'/>" method="post">
		<label for="username">Username:</label><br/>
		<input type="text" id="username" name="username" value="${savedUsername}" required /><br/><br/>

		<label for="password">Password:</label><br/>
		<input type="password" id="password" name="password" required /><br/><br/>

		<input type="checkbox" id="remember" name="remember" ${isRemember ? "checked" : ""} />
		<label for="remember">Remember me</label><br/><br/>

		<input type="submit" value="Login" />
	</form>
</body>
</html>
