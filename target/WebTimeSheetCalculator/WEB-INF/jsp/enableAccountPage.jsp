<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
        <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- bootstrap stuff -->
<head>
<style>
<%@ include file="/resources/css/main.css"%>
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap-filestyle.min.js"> </script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Enable Account Page</title>
</head>


<body>
<jsp:include page="navBar.jsp" />

<!-- bootstrap form -->
<div class="content">

<div class="container">
<h1>Search User Account</h1><br><br><br>



   <form class="form-horizontal" name='f' action="activateUser" method='POST'>
      <div class="form-group">
        <label class="col-sm-2 control-label">Username:</label>
        <div class="col-sm-4">
          <input class="form-control inputstl" name="username" id="username" placeholder="Enter username"
          onkeyup="ajaxSearchUser();" />
        </div>
        <div id="result"></div>
      </div>
      <div class="form-group hidden" id="divpasswordId">
        <label class="col-sm-2 control-label">Password:</label>
        <div class="col-sm-4">
          <input type= "password" class="form-control inputstl" name="password" id="passwordid"/>
        </div>
        <label cssClass = "errorpass" />
      </div>
      <div class="form-group hidden" id="divconfirmpasswordId">
        <label class="col-sm-2 control-label">Confirm Password:</label>
        <div class="col-sm-4">
          <input type= "password" class="form-control inputstl" name="passwordconfirm" id="passwordconfirmid"/>
        </div>
        <label cssClass = "errorpass" />
      </div>
      
       <div class="form-group">
        <div class="col-sm-offset-2 col-sm-4">
          <button type="submit" class="btn btn-lg btn-block btn-primary"  >Enable User</button>
        </div>
      </div>
      
      <br>
      <div class="error">${passworderror}</div>
      <div class="success">${success}</div>
     
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

<!-- ajax to get results for the date -->
function ajaxSearchUser(){

 $.ajax({
  type: "POST",
  url: "http://localhost:8080/WebTimeSheetCalculator/searchAccount", //URL to access the controller
  cache: false,    
  data: { username: $("#username").val() }, // parameters to send (the value of the month)
  success: function(response){
   $('#result').html("");
  // var obj = JSON.parse(response); to convert a valid JSON object into javascript object
   var user = response;
   if(response != null && response !=""){
	   $('#result').html('<font color="green">' + response + '</font></br>');
	   $('#divpasswordId').removeClass('hidden');
	   $('#divconfirmpasswordId').removeClass('hidden');
   }
   else{
	   $('#result').html('<font color="red"> Nothing found ... </font></br>');
	   $('#divpasswordId').addClass('hidden');
	   $('#divconfirmpasswordId').addClass('hidden');
   }
  
  },
  error: function(){      
   alert('Error while request..');
  }
 });
}
</script>


<jsp:include page="footer.jsp" />

</body>

 
</html>