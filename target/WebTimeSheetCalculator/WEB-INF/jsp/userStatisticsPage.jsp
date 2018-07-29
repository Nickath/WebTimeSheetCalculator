<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
<title>${user.username} Statistics Page</title>
</head>
<body>
<jsp:include page="navBar.jsp" />
<div class="container" style="margin-left:420px; width:1280px !important;">
<div class="table-responsive">
<table class="table">
<thead>
      <tr>
        <th>Month</th>
        <th>Mean</th>
        <th>Average Coming</th>
        <th>Average Leaving</th>
        <th>Working Days</th>
        <th>Desired Mean</th>
        <th>Last Update</th>
      </tr>
    </thead>
<c:forEach items="${timesheetList}" var="element"> 

<tbody>
  <tr>
    <td>${element.month.month}</td>
    <td><c:if test="${empty element.insertMean}"> null </c:if>${element.insertMean}</td>
    <td><c:if test="${empty element.exitMean}"> null </c:if>${element.exitMean}</td>
    <td><c:if test="${empty element.mean}"> null </c:if>${element.mean}</td>
    <td><c:if test="${empty element.workingDays}"> null </c:if>${element.workingDays}</td>
    <td><c:if test="${empty element.desiredMean}"> null </c:if>${element.desiredMean}</td>
    <td><c:if test="${empty element.lastUpdate}"> null </c:if>${element.lastUpdate}</td>
    <td><a href="<c:url value='/deleteMonth/${element.month.id}' />" >Delete</a></td>
    <td><a href="<c:url value='/downloadMonth/${element.month.id}' />" >Download in .xls form 
    <img src="${pageContext.request.contextPath}/resources/images/xls.gif"/> 
    </a></td>
  </tr>

</tbody>

  <br>
</c:forEach>
</table>
</div>
<p class="pdftitle">
    <form id="statisticsform" action="downloadPdf" method="POST">
      <input type="hidden" id="hiddenform" name="hiddenform"> </input>
      <a href="#" onclick="formSubmit();">Download statistics in .pdf form
       <img src="${pageContext.request.contextPath}/resources/images/pdficon.png"/> 
     </a>
    </form>
</p>
</div>
<jsp:include page="footer.jsp" />
</body>




<!-- ajax to get the dom of the form -->

<!-- <script type="text/javascript">
function ajaxGetForm(){

 var htmlString = $('.container').html();

 $.ajax({
  type: "GET",
  url: "http://localhost:8080/WebTimeSheetCalculator/downloadPdf", //URL to access the controller
  cache: false,    
  data: { form: htmlString }, // parameters to send (the value of the month, using the id)
  success: function(response){

  alert('PDF successfully generated');
  
  },
  error: function(){      
   alert('Error while request..');
  }
 });
}
</script> -->

<script type="text/javascript">
$( document ).ready(function() {
   $('#hiddenform').val($('.container').html());
});

function formSubmit()    
{    
     document.getElementById('statisticsform').submit();    
} 

</script>

</html>