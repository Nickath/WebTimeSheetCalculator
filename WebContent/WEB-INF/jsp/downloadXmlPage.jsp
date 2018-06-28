<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Download profil in XML</title>
</head>
<body>
<jsp:include page="navBar.jsp" />
<div class="content">
<div class="container">
<h1>Download profil in XML</h1><br><br><br>
<a href="<c:url value="/downloadXMLAttempt" />" >Get your profil in XML form</a><br>

</div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>