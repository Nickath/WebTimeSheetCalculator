<%--
  Created by IntelliJ IDEA.
  User: NICK
  Date: 10/9/2018
  Time: 8:20 PM
  To change this template use File | Settings | File Templates.
--%>
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
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-filestyle.min.js"> </script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Notifications</title>
</head>
<body>
<jsp:include page="navBar.jsp" />

<div class="content">
    <div class="container" style="width:1000px;">

        <div class="notification">
            <div class="row">
                <div class="columnLeft">
                    <i class="material-icons">notifications</i>
                </div>
               <form name="leaveRequestReply" action="/leaveRequestReply${notification.id}" method="post">
                <div class="columnRight">
                    <p>Notification Type: ${notification.notificationType}</p>
                    <c:if test="${notification.notificationType == 'LEAVE_REQUEST'}">
                    <p>From: ${notification.fromDate} </p>
                    <p>To: ${notification.toDate}</p>
                    </c:if>
                    <p>Description: ${notification.description}</p>
                    <p>Referrer User: ${notification.referreruser.username}</p>
                    <p>Date: ${notification.date}</p>
                    <p>Status:
                    <c:if test="${notification.active eq true}">
                     Active
                    </c:if>
                    <c:if test="${notification.active ne true}">
                     Inactive
                    </c:if>
                    </p>
                </br>
                <c:if test="${(notification.notificationType.name().toString() == 'LEAVE_REQUEST') || (notification.notificationType.name().toString() eq 'LEAVE_REQUEST')}">
                    <button type="button" value="accept" name="accept" class="btn btn-success">Accept</button>
                    &nbsp;&nbsp;
                    <button type="button" value="reject" name="reject" class="btn btn-danger">Reject</button>
                </c:if>
                </div>
               </form>
            </div>
        </div>

    </div>
</div>

<jsp:include page="footer.jsp" />
</body>


</html>
