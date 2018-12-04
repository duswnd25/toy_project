<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title>CAT VOTE</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/public/css/login.css">
</head>

<body>
	<form class="login-form" method="post"
		action="/catvote/auth?auth_type=login">
		<p class="login-text">
			<span class="fa-stack fa-lg"> <i
				class="fa fa-circle fa-stack-2x"></i> <i
				class="fa fa-lock fa-stack-1x"></i>
			</span>
		</p>
		<input type="text" class="login-username" autofocus name="ID"
			required /> <input type="password" class="login-password"
			placeholder="Password" name="PW" /> <input type="submit"
			name="Login" value="Login" required class="login-submit" />
	</form>
	<div class="underlay-photo"></div>
	<div class="underlay-black"></div>
</body>

</html>
