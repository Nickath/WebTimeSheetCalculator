<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
<div class="container">
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
    <td><a href="<c:url value='/downloadMonth/${element.month.id}' />" >Download in .xls form</a></td>
  </tr>
</tbody>

  <br>
</c:forEach>
</table>
</div>
</div>
</body>
</html>