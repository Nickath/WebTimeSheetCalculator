<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- bootstrap stuff -->
<style>
<%@ include file="/css/main.css"%>
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap-filestyle.min.js"> </script>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>


<body>
<jsp:include page="navBar.jsp" />

<!-- bootstrap form -->
<div class="content">

<div class="container">
<h1> Login Form</h1><br><br><br>

  <!-- /login?error=true -->
     <c:if test="${param.error == 'true'}">
         <div style="color:red;margin:10px 0px;">
          
                Login Failed!!!<br />
                Reason :  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                 
         </div>
    </c:if>

   <form class="form-horizontal" name='f' action="${pageContext.request.contextPath}/j_spring_security_check" method='POST'>
      <div class="form-group">
        <label class="col-sm-2 control-label">Username:</label>
        <div class="col-sm-4">
          <input class="form-control inputstl" name="username" id="username" placeholder="Enter username"/>
        </div>
        <label cssClass = "error" />
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Password:</label>
        <div class="col-sm-4">
          <input type= "password" class="form-control inputstl" name="password" id="password"/>
        </div>
        <label cssClass = "error" />
      </div>
      
      <br><div class="error"> ${invalidCreds} </div>
 

     <div class="form-group">
        <div class="col-sm-offset-2 col-sm-4">
          <button type="submit" class="btn btn-lg btn-block btn-primary"  >Login</button>
        </div>
      </div>
  </form>
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
   </div> 
   
   
  </div>

<!-- end of bootstrap form -->

<!--  js bootstrap -->

<script>
			 $('#file').filestyle({
				buttonName : 'btn-primary',
                buttonText : ' Upload an Image',
                iconName : 'glyphicon glyphicon-upload'
			}); 
</script>   



<!-- end of js bootstrap -->





<script type="text/javascript">
$(document).ready(function () {
$('#file').change(function() {
   
     if($(this).val()){
    	 $('#imgValid').removeClass('hidden');
    	 $('#fileerror').addClass('hidden');
    	  alert($(this).val());
     }
     else{
    	 $('#imgValid').addClass('hidden');
    	 $('#fileerror').removeClass('hidden');
    	  alert('Path is empty');
     }
});


$('#eraseInput').click(function() {
	
	document.getElementById("file").value = "";
	 $('#imgValid').addClass('hidden');
	 alert('Path is empty');
});




});
</script>



</body>

 
</html>