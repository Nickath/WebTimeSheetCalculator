<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- bootstrap stuff -->
<head>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap-filestyle.min.js"> </script>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
<jsp:include page="navBar.jsp" />
<!-- bootstrap form -->
<div class="content">

<div class="container">
<h1> Create an account </h1><br><br><br>

    <form:form class="form-horizontal" action="registerAttempt" modelAttribute="registerForm" method="POST" enctype="multipart/form-data">
      <div class="form-group">
        <form:label path = "username" class="col-sm-2 control-label">Username:</form:label>
        <div class="col-sm-4">
          <form:input path="username" class="form-control inputstl" name="username" id="username" placeholder="Username"/>
        </div>
        <form:errors path = "username" cssClass = "error" /> <div class="error"> ${error} </div>
      </div>
      <div class="form-group">
        <form:label path="email" class="col-sm-2 control-label">Email:</form:label>
        <div class="col-sm-4">
          <form:input path="email" class="form-control inputstl" name="email" id="email" placeholder="Email"/>
        </div>
        <form:errors path = "email" cssClass = "error" />
      </div>
      
       <div class="form-group">
        <form:label path="password" class="col-sm-2 control-label">Password:</form:label>
        <div class="col-sm-4">
          <form:password path="password" class="form-control inputstl" name="password" id="password" placeholder=""/>
        </div>
        <form:errors path = "password" cssClass = "error" />
      </div>
 
      
      

      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-4">
          <button type="submit" class="btn btn-lg btn-block btn-primary"  >Register</button>
        </div>
      </div>
      
      
      
    </form:form>
   </div> 
   
   
  </div>

<jsp:include page="footer.jsp" />
</body>
</html>