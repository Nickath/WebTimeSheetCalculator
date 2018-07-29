<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
<%@ include file="/resources/css/main.css"%>

.notification-container {
    position: relative;
    width: 16px;
    height: 16px;
    top: 15px;
    left: 15px;
    
    i {
        color: #fff;
    }
}


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
        <li>
        <a href="Notifications">Notifications</a>
        </li>
        <li><a href="<c:url value="/homePage" />" >${user.username}</a></li>
        <li><a data-toggle="modal" href="#myModal" > Actions </a></li>
       
      </c:if>
      <c:if test="${pageContext.request.userPrincipal.name == null}">
       <li><a data-toggle="modal" href="#myLoginModal" > Login </a></li>
       <li><a data-toggle="modal" href="#myRegisterModal" > Register </a></li>
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
         <span class ="inlinehref">
          <a href="<c:url value="/homePage" />" class="inlinehref" >
          <img alt="img" style="border: 0;" src="data:image/jpeg;base64,${photoProfil}" height="60" width="70" />
          </a>
         </span>
       </li>
       <li>
       <div class="rail-select">
         <select class="form-control" id="sel1" value="-1" onChange="uploadPhoto(this.selectedIndex);">
           <option name="uploadphoto" value=""> </option>
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
    
    
   
  <!--Actions  Modal -->
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
           <li><a href="<c:url value="/subscribeMailPage" />" >Subscribe to the mail list</a></li><br>
           <li><a href="<c:url value="/downloadXMLPage" />" >Get your profil in XML form</a></li><br>
          </c:if>
          <c:if test="${ (isAdmin eq true) || user.role.id == 1 }">
             <li><a href="<c:url value="/usersPage" />" >Admin Control Panel</a></li><br>
            <li><a href="<c:url value="/userStatisticsPage" />" >Watch employees Statistics</a></li><br>
            <li><a href="<c:url value="/downloadXMLPage" />" >Get your profil in XML form</a></li><br>
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
  
  
         
  <!-- register modal -->
    <div class="modal fade" id="myRegisterModal" role="dialog">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Register Actions </h4>
        </div>
        <div class="modal-body">
          <p>Choose one of the following actions</p>
        </div>
        <div>
        <ul class="ul-nobullets">
          <li><a href="<c:url value="/registerPage" />" >Register</a></li> <br>
          <li><a href="<c:url value="/registerXMLPage" />" >Register XML</a></li> <br>
        </ul>
          <div class="modal-footer">
           <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
  </div>
  
        
  <!-- login modal -->
    <div class="modal fade" id="myLoginModal" role="dialog">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Login Actions </h4>
        </div>
        <div class="modal-body">
          <p>Choose one of the following actions</p>
        </div>
        <div>
        <ul class="ul-nobullets">
          <li><a href="<c:url value="/loginPage" />" >Login</a></li><br>
          <li><a href="<c:url value="/loginXmlPage" />" >Login via XML</a></li><br>
        </ul>
          <div class="modal-footer">
           <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
  </div>
  
</body>



<!-- ajax to upload -->

<script type="text/javascript">

$(document).keypress(function(e) { 
    if (e.keyCode == 27) { 
    	alert('Escape pressed');
        $("#myModal").fadeOut(500);
        $("#myRegisterModal").fadeOut(500);
        $("#myLoginModal").fadeOut(500);
    } 
});


let select; 
function uploadPhoto(selectedIndex){
	
	if(selectedIndex == 2){
		deletePhoto();
		return;
	}
	
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
    	select = selectedIndex;
    	return;
    }
    
    if(select == 1){
    	updatePhoto();
    }
    else if(select == 2){
    	deletePhoto();
    }
    else{
    	return;
    }

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
    	  location.reload();//if ajax was successfull, reload the page
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
      location.reload(); //if ajax was successfull, reload the page
  	  },
  	  error: function(){      
  	  alert('Error while request..');
  	  }
  	 });
}

</script>

</html>