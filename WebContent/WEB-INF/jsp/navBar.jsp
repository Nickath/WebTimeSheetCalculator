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
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>navbar</title>
</head>
<body>

 <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
      <c:if test="${pageContext.request.userPrincipal.name != null}">
         <li><a href="<c:url value="/homePage" />" >${user.username}</a></li>
       <li><a data-toggle="modal" href="#myModal" > Actions </a></li>
       
      </c:if>
      <c:if test="${pageContext.request.userPrincipal.name == null}">
       <li><a href="<c:url value="/loginPage" />" >Login</a></li>
       <li><a href="<c:url value="/registerPage" />" >Register</a></li>
       <li><a href="<c:url value="/accountEnablePage" />" >Enable Account</a></li>
     </c:if>
      
        
        <li><a href="#">About</a></li>
      <c:if test="${pageContext.request.userPrincipal.name != null}">
       <li><%-- <a href="<c:url value="/logout" />" >Logout</a> --%>
       <a href="<c:url value="/j_spring_security_logout" />"> Logout</a></li>
       
     </c:if>
      </ul>
      
      <ul class="nav navbar-nav navbar-left">
         <c:if test="${pageContext.request.userPrincipal.name != null}">
       
       <c:if test="${photoProfil != null}">
       <li>
       <img alt="img" src="data:image/jpeg;base64,${photoProfil}" height="60" width="70" />
       </li>
       <li>
       <div class="rail-select">
         <select class="form-control" id="sel1" value="-1" onChange="uploadPhoto(this.selectedIndex);">
           <option name="uploadphoto" value="" > </option>
           <option name="uploadphoto" value="uploadphoto" >Upload a photo</option>
           <option name="deletephoto" value="deletephoto" >Delete photo</option>
         </select>
         <form id="formID">
        <input type="file" id="photoID" name="photo" onChange="uploadPhoto(this);" style="display:none">
        </form>
      </div>
      </li>
       
      </c:if>
     
       
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
             <li><a href="<c:url value="/usersPage" />" >Admin Control Panel</a></li><br>
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



<!-- ajax to upload -->

<script type="text/javascript">
function uploadPhoto(selectedIndex){
	alert(selectedIndex);
    document.getElementById("photoID").click();
    var file = $('[name="photo"]');
    var filename = $.trim(file.val());
    var formdata = new FormData(document.getElementById("formID"));
    if(filename != null && filename != "") {
      if (!(isJpg(filename) || isPng(filename))) {
         alert('Please browse a JPG/PNG file to upload ...');
         return;
       }
    }
    else{
    	return;
    }
    updatePhoto();
    
  
	
}



var isJpg = function(name) {
    return name.match(/jpg$/i)
};
    
var isPng = function(name) {
    return name.match(/png$/i)
};

function updatePhoto(){
	  $.ajax({
    	  type: "POST",
    	  url: "http://localhost:8080/WebTimeSheetCalculator/uploadPhoto", //URL to access the controller
    	  enctype: 'multipart/form-data',
          processData: false,
          contentType: false,
          cache: false,
    	  data:  new FormData(document.getElementById("formID")) , // parameters to send 
    	  success: function(response){
    	  // var obj = JSON.parse(response); to convert a valid JSON object into javascript object
    	  },
    	  error: function(){      
    	  alert('Error while request..');
    	  }
    	 });
}


function deletePhoto(){
	  $.ajax({
  	  type: "POST",
  	  url: "http://localhost:8080/WebTimeSheetCalculator/deletePhoto", //URL to access the controller
  	  enctype: 'multipart/form-data',
      processData: false,
      contentType: false,
      cache: false,
  	  success: function(response){
  	  // var obj = JSON.parse(response); to convert a valid JSON object into javascript object
  	  },
  	  error: function(){      
  	  alert('Error while request..');
  	  }
  	 });
}

</script>

</html>