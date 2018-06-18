<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Successfull Registration</title>
</head>
<body>
<jsp:include page="navBar.jsp" />
<div class="content">
 <div class="container">
  <h2>You account with username <i>${userForm.username} </i> was successfully created !</h2><br><br><br>
  <h3>Check your email for the account confirmation link and then </h3><br>
  <p>Go to   <a href="<c:url value="/loginPage" />" >Login Page</a></p>
 </div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>