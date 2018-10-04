<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
        <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<<link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/clock.png">
<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/clock.png">
<style>
<%@ include file="/resources/css/main.css"%>
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap-filestyle.min.js"> </script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Notifications Page</title>
</head>
<body>
<jsp:include page="navBar.jsp" />
  <div class="content">
   <div class="container">
     <h1>My Notifications</h1><br><br><br>
     
     <form id="newNotification">
     <input type="text" name = "notification"/>
     <input type="button" value="Submit" onclick="sendForm();"/>
     </form>
     <div id="divID">
     
     </div>
   </div>
  </div>
 <jsp:include page="footer.jsp" /> 
</body>
<script type="text/javascript">

var source = new EventSource("/notifications");

source.addEventListener('spring', function(event){
	$('#divID').prepend('<div class="kati"> <span>Hi</span> </div>');
});

function sendForm(){
	alert('submit the form');
	$.post('newNotification', $('#newNotification').serialize());
}

</script>

</html>