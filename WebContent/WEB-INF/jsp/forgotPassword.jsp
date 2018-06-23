<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot Password </title>
</head>
<body>
<jsp:include page="navBar.jsp" />

<div class="content">
 <div class="container">
   <h1>Change Password Request</h1><br><br><br>
    <form class="form-horizontal" name='f' action="changePassRequest" method='POST'>
      <div class="form-group">
        <label class="col-sm-2 control-label">Email:</label>
        <div class="col-sm-4">
          <input class="form-control inputstl" name="email" id="email" placeholder="Enter email" />
        </div>
        <div id="result"></div>
      </div>

      
       <div class="form-group">
        <div class="col-sm-offset-2 col-sm-4">
          <button type="submit" class="btn btn-lg btn-block btn-primary"  >Send Email</button>
        </div>
      </div>
      
      <br>
      <div class="error">${notexistingmail}</div>
      <div class="success">${success}</div>
     
  </form>
 </div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>