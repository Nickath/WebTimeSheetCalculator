<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
<title>Subscribe To Mail</title>
</head>
<body>
<jsp:include page="navBar.jsp" />

<div class="content">
<div class="container" style="width:1000px;margin-left:-70px;">

      
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-4">
         <c:if test="${usersubscription != null}">
         <input type="checkbox" name="subscribed" id = "checkboxID" value="subscribed" checked onChange="ajaxCheckBox()">
         Subscribed since ${usersubscription.subscription_date}
         </c:if> 
      <c:if test="${usersubscription == null}">
      <input type="checkbox" name="subscribed" id = "checkboxID" value="subscribed" onChange="ajaxCheckBox()">
       You are not subscribed to the mail list
     </c:if> 
        <br>
      </div>
      </div>
      

</div>
</div>

<jsp:include page="footer.jsp" />
</body>



<script type="text/javascript">

function ajaxCheckBox(){
    if($("#checkboxID").is(":checked")){
    	var r = confirm("Are you sure you want to subscribe to mail list?");
    }
    else{
    	var r = confirm("Are you sure you want to unsubscribe?");
    }
	
	if (r == true) {
	} else {
		if($("#checkboxID").is(":checked")){
		   $("#checkboxID").prop("checked", false);
		}
		else {
			$("#checkboxID").prop("checked", true);
		}
	    return;
	}
	
	
 $.ajax({
  type: "POST",
  url: "http://localhost:8080/WebTimeSheetCalculator/subsribeAction", //URL to access the controller
  cache: false,    
  data: { subscribed: $("#checkboxID").is(":checked") }, // parameters to send (the value of the month)
  success: function(response){
  location.reload();//if ajax was successfull, reload the page
  },
  error: function(){      
   alert('Error while request..');
  }
 });
}
</script>

</html>