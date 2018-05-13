<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
<%@ include file="/css/main.css"%>
</style>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
    <td>${element.insertMean}</td>
    <td>${element.exitMean}</td>
    <td>${element.mean}</td>
    <td>${element.desiredMean}</td>
    <td>${element.workingDays}</td>
    <td>${element.lastUpdate}</td>
  </tr>
</tbody>

  <br>
</c:forEach>
</table>
</div>
</div>
</body>
</html>