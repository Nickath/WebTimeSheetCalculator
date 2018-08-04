<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
        <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Contact Form Tutorial by Bootstrapious.com</title>
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

                    <form id="contact-form" method="post" action="/leaveRequestAttempt">
                        <div class="messages"></div>
                        <div class="controls">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="fromDate">From *</label>
                                         <input type="date" name="dateFrom" required="required" data-error="Valid date is required">
                                        <div class="help-block with-errors"></div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="toDate">To *</label>
                                         <input type="date" name="dateTo" required="required" data-error="Valid date is required">
                                        <div class="help-block with-errors"></div>
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
                                    </div>
                                </div>
                                
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label for="form_message">Message *</label>
                                        <textarea id="form_message" name="message" class="form-control" placeholder="Message for me *" rows="4" required="required" data-error="Please,leave us a message."></textarea>
                                        <div class="help-block with-errors"></div>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <input type="submit" class="btn btn-success btn-send" value="Send message">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <p class="text-muted"><strong>*</strong> These fields are required. Contact form template by <a href="https://bootstrapious.com/p/how-to-build-a-working-bootstrap-contact-form" target="_blank">Bootstrapious</a>.</p>
                                </div>
                            </div>
                        </div>
                    </form>
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

//when the page is rendered, fill the options with the recipients
$( document ).ready(function() {
	var json = ${usersJson};
	addRecipientOptions(json);
});
	

//on change of the select box, add the value selected to the list on the right first and then delete from the list
$('select[name="recipients"]').change(function() {
	  addRecipient(this.value);
	  removeRecipientFromOptions();
});


function addRecipient(selectedRecipient){
 $.ajax({
  type: "POST",
  url: "http://localhost:8080/WebTimeSheetCalculator/addRecipient", //URL to access the controller
  cache: false,    
  data: { recipient: selectedRecipient }, // parameters to send (the value of the month, using the id)
  success: function(response){
  json = JSON.parse(response);
  $('#tbSelectedID').append('<span style="display:block;white-space: pre;" id="'+selectedRecipient+'id"> <label for="recipient">' +selectedRecipient+ ' </label> <a href="#" onclick="deleteFromList(&quot;'+selectedRecipient+'&quot;);" style="position:absolute;right:50px;"><i class="material-icons">delete</i></a></span>');
  },
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

function deleteFromList(selectedRecipient){
	document.getElementById(selectedRecipient+'id').remove();
	addOptionToSelect(selectedRecipient);
}

function addOptionToSelect(selectedRecipient){
	$('select[name="recipients"]').append('<option value="'+selectedRecipient+'" id="">'+selectedRecipient+'</option>');
}


</script>


</html>