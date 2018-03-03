<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TimeSheet Calculator</title>
</head>
<body>
<h1> Welcome to the TimeSheet Calculator</h1>


<form:form action="calculate" method="POST" enctype="multipart/form-data">  
Select File: <input type="file" name="file"/>  
<input type="submit" value="Upload File"/>  
<table>
<tr>
<td> Desired Average Mean : <input type="text" name="desiredMean">  </td>
</tr>
<tr>
<td> Number of rest working days <input type="text" name="pendingDays">  </td>
</tr>
</table>



</form:form>  

</body>

 
</html>