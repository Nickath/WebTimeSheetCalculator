<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>HTTP Status 403 - Access is denied</h1>
	 ${user.username}
	<c:choose>
		<c:when test="${pageContext.request.userPrincipal.name == null}">
		  <h2>${msg}</h2>
		</c:when>
		<c:otherwise>
		  <h2>Username : ${pageContext.request.userPrincipal.name} <br/>
                    ${message}</h2>
		</c:otherwise>
	</c:choose>
	
	<br>
	<h3>Go to<a href="<c:url value="/homePage" />"> Home Page </a></h3>

</body>
</html>