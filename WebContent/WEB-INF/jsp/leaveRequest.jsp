<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
        <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/clock.png">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/clock.png">
        <title>Leave Request form</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
        <link href='https://fonts.googleapis.com/css?family=Lato:300,400,700' rel='stylesheet' type='text/css'>
        <link href='custom.css' rel='stylesheet' type='text/css'>
    </head>
<body>
<jsp:include page="navBar.jsp" />
        <div class="container">

            <div class="row">

                <div class="col-lg-8 col-lg-offset-2">

                    <h1>Leave Request Form</h1>
                    <p class="lead">You can use the following form to make a leave request</p>

                    <form:form id="leaveRequestFormID" method="post" modelAttribute="leaveRequestForm" action="leaveRequestAttempt">
                        <div class="messages"></div>
                        <div class="controls">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="fromDate">From *</label>
                                         <form:input path="fromDate" type="date" name="fromDate" required="required" data-error="Valid date is required"/>
                                        <div class="help-block with-errors"></div>
                                        <form:errors path = "fromDate" cssClass = "error" />
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="toDate">To *</label>
                                         <form:input path="toDate" type="date" name="toDate" required="required" data-error="Valid date is required"/>
                                        <div class="help-block with-errors"></div>
                                        <form:errors path = "toDate" cssClass = "error" />
                                    </div>
                                </div>
                            </div>
                           <div class="row">
                                <div class="col-md-5">
                                    <div class="form-group">
                                        <label for="recipients">Recipient(s)</label>
                                         <select name="recipients" id="optionsRecID" class="dropdown-select">
                                           <option selected="true" disabled="disabled">Choose Recipient(s)</option>   
                                         </select>
                                        <div class="help-block with-errors"></div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="recipientsTable">Selected Recipients</label>
                                        <table id="tbSelectedID">
                                        
                                        
                                        </table>
                                        <div class="help-block with-errors"></div>
                                        <form:input path="recipients" type="text" name="recipients" id="recipID" style="visibility: hidden;"/>
                                        <div class="help-block with-errors"></div>
                                        <form:errors path = "recipients" cssClass = "error" />
                                    </div>
                                </div>
                                
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label for="form_message">Message *</label>
                                        <textarea id="form_message" name="message" class="form-control" placeholder="Message for me *" rows="4" required="required" data-error="Please,leave us a message."></textarea>
                                        <div class="help-block with-errors"></div>
                                         <form:errors path = "message" cssClass = "error" />
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <button type="submit" id="submitButtonID" class="btn btn-success btn-send">Send Request</button>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <p class="text-muted"><strong>*</strong> These fields are required. Contact form template by <a href="https://bootstrapious.com/p/how-to-build-a-working-bootstrap-contact-form" target="_blank">Bootstrapious</a>.</p>
                                </div>
                            </div>
                        </div>
                    </form:form>
                    <div class="success">
                    
                    <span class="success">${success}</span>
                    
                    </div>
                </div><!-- /.8 -->
            </div> <!-- /.row-->
        </div> <!-- /.container-->
        <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.min.js" integrity="sha256-dHf/YjH1A4tewEsKUSmNnV05DDbfGN3g7NMq86xgGh8=" crossorigin="anonymous"></script>
        <script src="resources/js/contactform/contact-2.js"></script>
 <jsp:include page="footer.jsp" /> 
</body>


<script type="text/javascript">

var recip = [];

//when the page is rendered, fill the options with the recipients
$( document ).ready(function() {
	var json = ${usersJson};
	addRecipientOptions(json);
});

//on change of the select box, add the value selected to the list on the right first and then delete from the list
$('select[name="recipients"]').change(function() {
	  addRecipient(this.value,$(this).children(":selected").attr("id"));
	  removeRecipientFromOptions();
});


function addRecipient(selectedRecipient,selectedRecipientId){
 $.ajax({
  type: "POST",
  url: "http://localhost:8080/WebTimeSheetCalculator/addRecipient", //URL to access the controller
  cache: false,    
  data: { recipient: selectedRecipient }, // parameters to send (the value of the month, using the id)
  success: function(response){
  json = JSON.parse(response);
  $('#tbSelectedID').append('<span style="display:block;white-space: pre;" id="'+selectedRecipientId+'"> <label for="recipient">' +selectedRecipient+ ' </label> <a href="#" onclick="deleteFromList(&quot;'+selectedRecipient+'&quot;,'+selectedRecipientId+');" style="position:absolute;right:50px;"><i class="material-icons">delete</i></a></span>');
  recip.push(selectedRecipientId);  $('#recipID').val(recip);},
  error: function(){      
  alert('Error while request..');
  }
 });
}


//add recipients in the list
function addRecipientOptions(json){
	 $(json).each(function(i, j) {
		 $('select[name="recipients"]').append('<option value="'+j.username+'('+j.email+')" id="'+j.id+'">'+j.username+'('+j.email+')</option>');
	 });
}

function removeRecipientFromOptions(){
	  $("#optionsRecID :selected").remove();
	  $("#optionsRecID").val($("#optionsRecID option:first").val());
}

function deleteFromList(selectedRecipient,selectedRecipientId){
	$('span#'+selectedRecipientId+'').remove();
	removeByItem(recip,selectedRecipientId);
	addOptionToSelect(selectedRecipient,selectedRecipientId);
}

function addOptionToSelect(selectedRecipient,selectedRecipientId){
	$('select[name="recipients"]').append('<option value="'+selectedRecipient+'" id="'+selectedRecipientId+'">'+selectedRecipient+'</option>');
}

function removeByItem(recip, removeid){
	var index = recip.indexOf(removeid.toString());
	if (index > -1) {
	    recip.splice(index, 1);
	    $('#recipID').val(recip);
	}
}



</script>


</html>