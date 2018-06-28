<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/clock.png">
<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/clock.png">
<!-- Hide this line for IE (needed for Firefox and others) -->
<![if !IE]>
<link rel="icon" href="http://yourwebsite/images/favicon.png" type="image/x-icon" />
<![endif]>
<!-- This is needed for IE -->
<link rel="shortcut icon" href="http://yourwebsite/images/favicon.ico" type="image/ico" />
<!-- bootstrap stuff -->
<style>
<%@ include file="/resources/css/main.css"%>
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap-filestyle.min.js"> </script>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login XML Page</title>
</head>


<body>
<jsp:include page="navBar.jsp" />

<!-- bootstrap form -->
<div class="content">

<div class="container">
<h1>XML Login Form</h1><br><br><br>



   <form class="form-horizontal" name='f' action="loginXMLAttempt" method='POST' enctype="multipart/form-data">
      <div class="form-group">
      <label for="file" class="col-sm-2 control-label">Upload:</label>
        <div class="col-sm-2">
      <%--   name="months[${status.index}].month" --%>
         <input  type="file" class="inputstl" id="file" name="file" tabindex="-1" style="position: absolute; clip: rect(0px 0px 0px 0px);" onchange="this.form.submit()"/>
          <div class="bootstrap-filestyle input-group">
          <input type="hidden" class="form-control " placeholder="" disabled="" >
           <span class="group-span-filestyle input-group-btn" tabindex="0">
            <label for="file" class="btn btn-primary ">
             <span class="icon-span-filestyle glyphicon glyphicon-upload">
             </span>
             <span class="buttonText"> Login with XML</span>
            </label>
           </span>
         </div>
        </div>
       </div>
      
      
   <div class="error">

          <!-- /login?error=true -->
     <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
     <br>
      <font color="red">
        Your login attempt was not successful due to: <br/>
        <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
      </font>
     </c:if>
     
    </div>
    <br>

   <p class="forgot-pass">
    <span class="forget-pass link" tabindex="0">
    <a href="<c:url value="/forgotPassword" />"> Forgot your password?  </a>
    
    </span>
   </p>
   
   <br><br>
   
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

<jsp:include page="footer.jsp" />

</body>

 
</html>