<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
<%@ include file="/resources/css/main.css"%>
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Results</title>


<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap-filestyle.min.js"> </script>


    <meta name="viewport" content="width=device-width, initial-scale=1.0">


</head>
<body>
<jsp:include page="navBar.jsp" />
<div class="content">

<div class="container">


<p class="results"> Working days: </p> ${timesheet.workingDays}
<p class="results"> Total Mean Average: </p> ${timesheet.mean} 
<p class="results"> Mean Average coming time:</p> ${timesheet.insertMean} 
<p class="results"> Mean Average leaving time:</p> ${timesheet.exitMean} 
<p class="results"> The mean you can have till the end of the month is: </p>${timesheet.restAverage} <br><br>
<p> If you want to recalculate using another mean average expected fill in the input below and submit <br>
 <form:form class="form-horizontal" action="recalculate" modelAttribute="timeSheetForm" method="POST">
 <div class="form-group">
        <form:label path = "desiredMean" class="col-sm-2 control-label">Desired Mean:</form:label>
        <div class="col-sm-4">
          <form:input path="desiredMean" class="form-control inputstl" name="desiredMean" id="desiredMean" placeholder="Enter Your Desired Mean"/>
        </div>
        <form:errors path = "desiredMean" cssClass = "error" />
      </div>

 <div class="form-group">
        <div class="col-sm-offset-2 col-sm-4">
          <button type="submit" class="btn btn-lg btn-block btn-primary" >Recalculate</button>
        </div>
      </div>
</form:form>



</div>
</div>

</body>
</html>