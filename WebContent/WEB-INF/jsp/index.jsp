<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- bootstrap stuff -->
<head>

 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-filestyle.min.js"> </script>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

<style>
.inputstl { 
    padding: 9px; 
    border: solid 1px #173955; 
    outline: 0; 
    background: -webkit-gradient(linear, left top, left 25, from(#FFFFFF), color-stop(4%, #AACCE8), to(#FFFFFF)); 
    background: -moz-linear-gradient(top, #FFFFFF, #AACCE8 1px, #FFFFFF 25px); 
    box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px; 
    -moz-box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px; 
    -webkit-box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px; 

    } 
   /*  to get the form in the center */
    .content {
    max-width: 500px;
    margin: auto;
    background: white;
    padding: 10px;
}
   
</style>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TimeSheet Calculator</title>



<!-- bootstrap form -->
<div class="content">

<div class="container">
<h1> Welcome to the TimeSheet Calculator</h1><br><br><br>

    <form:form class="form-horizontal" action="calculate" method="POST" enctype="multipart/form-data">
      <div class="form-group">
        <label for="desiredMean" class="col-sm-2 control-label">Desired Mean:</label>
        <div class="col-sm-4">
          <input type="text" class="form-control inputstl" name="desiredMean" id="desiredMean" placeholder="Enter Your Desired Mean">
        </div>
      </div>
      <div class="form-group">
        <label for="pendingDays" class="col-sm-2 control-label">Pending Days:</label>
        <div class="col-sm-4">
          <input type="text" class="form-control inputstl" name="pendingDays" id="pendingDays" placeholder="Enter Your pending Working Days">
        </div>
      </div>
 

      <div class="form-group">
       <label for="file" class="col-sm-2 control-label">Select a File to upload:</label>
        <div class="col-sm-5">
          <input type="file" class="inputstl" id="file" name="file" tabindex="-1" style="position: absolute; clip: rect(0px 0px 0px 0px);">
          <div class="bootstrap-filestyle input-group"><input type="text" class="form-control " placeholder="" disabled="" >
           <span class="group-span-filestyle input-group-btn" tabindex="0">
            <label for="file" class="btn btn-primary ">
             <span class="icon-span-filestyle glyphicon glyphicon-upload">
             </span>
             <span class="buttonText"> Upload the xlsx</span>
            </label>
           </span>
          </div>
        </div>
      </div>
      
      

      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-4">
          <button type="submit" class="btn btn-lg btn-block btn-primary">Submit form</button>
        </div>
      </div>
    </form:form>
   </div> 
  </div>

<!-- end of bootstrap form -->

<!--  js bootstrap -->

<script>
			 $('#file').filestyle({
				buttonName : 'btn-primary',
                buttonText : ' Upload an Image',
                iconName : 'glyphicon glyphicon-upload'
			}); 
</script>   



<!-- end of js bootstrap -->



















</body>

 
</html>