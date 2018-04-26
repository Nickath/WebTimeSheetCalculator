<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
<%@ include file="/css/main.css"%>
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
</head>
<body>
<jsp:include page="navBar.jsp" />



<div class="content">
<h1>User Information </h1>
<div class="container">
<p>Username : ${user.username} </p>
<p>Email : ${user.email} </p>
<p>Role : ${user.role.id} </p>

</div>
</div>
</body>
</html>