<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register User via XML</title>
</head>
<body>
<jsp:include page="navBar.jsp" />
<div class="content">
<div class="container">

<h1>Register User via XML</h1><br><br><br>

<form class="form-horizontal" name='f' action="registerXMLAttempt" method='POST' enctype="multipart/form-data">
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
             <span class="buttonText"> Register with XML</span>
            </label>
           </span>
         </div>
        </div>
       </div>
      
 
    </div>
    <br>
   <div class="error"> ${error} </div>
   <div class="success"> ${success} </div>
   <br>
   <p class="forgot-pass">
    <span class="forget-pass link" tabindex="0">
    <a href="<c:url value="/forgotPassword" />"> Forgot your password?  </a>
    
    </span>
   </p>

  </form>
  
  
  

</div>

</div>

<jsp:include page="footer.jsp" />
</body>
</html>