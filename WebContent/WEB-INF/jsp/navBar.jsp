<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
<%@ include file="/css/main.css"%>
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap-filestyle.min.js"> </script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>navbar</title>
</head>
<body>

 <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
      <c:if test="${not empty loggedInUser}">
       <li><a href="#" >${user.username}</a></li>
       <li><a data-toggle="modal" href="#myModal" > Actions </a></li>
       
      </c:if>
      <c:if test="${empty loggedInUser}">
       <li><a href="<c:url value="/loginPage" />" >Login</a></li>
       <li><a href="<c:url value="/registerPage" />" >Register</a></li>
     </c:if>
      
        
        <li><a href="#">About</a></li>
      <c:if test="${not empty loggedInUser}">
       <li><a href="<c:url value="/logout" />" >Logout</a></li>
       
     </c:if>
      </ul>
    </div>
    
    
   
  <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Actions </h4>
        </div>
        <div class="modal-body">
          <p>Choose one of the following actions</p>
        </div>
        <div>
        <ul class="ul-nobullets">
          <c:if test="${ (isAdmin eq false) || user.role.id == 2 }">
           <li><a href="<c:url value="/calculatePage" />" >Calculate this month average</a></li><br>
           <li><a href="<c:url value="/loadTimeSheetsPage" />" >Load TimeSheets for your months</a></li><br>
           <li><a href="<c:url value="/userStatisticsPage" />" >Watch my Statistics for all months</a></li><br>
           <li><a href="<c:url value="/subscribeMail" />" >Subscribe to the mail list</a></li><br>
           
          </c:if>
          <c:if test="${ (isAdmin eq true) || user.role.id == 1 }">
            <li><a href="<c:url value="/userStatisticsPage" />" >Watch employees Statistics</a></li><br>
            <li><a href="<c:url value="/deleteUserPage" />" >Delete a user</a></li><br>
          </c:if>
        </ul>
          <div class="modal-footer">
           <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- end of Modal -->
    
    
</body>
</html>