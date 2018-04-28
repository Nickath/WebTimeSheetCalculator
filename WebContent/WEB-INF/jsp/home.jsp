<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page session="true"%>
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

<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_USER')">

<div class="content">
 <h1>User Information </h1>
<div class="container">

<c:if test="${pageContext.request.userPrincipal.name != null}">
        <h2>
            Welcome : ${user.username} | <a
                href="<c:url value="/j_spring_security_logout" />"> Logout</a>
        </h2>
</c:if>
<p>Username : ${user.username} </p>
<p>Email : ${user.email} </p>
<p>Role :
 <c:choose>
            <c:when test="${user.role.id == 2 }">
                User 
            </c:when>
            <c:otherwise>
                Admin
            </c:otherwise>
        </c:choose>
</p>

</div>
</div>

</sec:authorize>
</body>
</html>