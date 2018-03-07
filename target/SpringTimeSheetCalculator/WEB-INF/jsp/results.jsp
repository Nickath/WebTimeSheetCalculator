<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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

</head>
<body>

<div class="content">

<div class="container">
<p> Working days: ${timesheet.workingDays} </p>
<p> Total Mean Average: ${timesheet.mean} </p>
<p> Mean Average coming time: ${timesheet.insertMean} </p>
<p> Mean Average leaving time: ${timesheet.exitMean} </p>
<p> The mean you can have till the end of the month is: ${timesheet.restAverage}</p>
</div>
</div>

</body>
</html>