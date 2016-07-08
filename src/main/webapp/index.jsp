<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' href='webjars/jquery-ui-themes/1.11.4/smoothness/jquery-ui.min.css'>
<script src="webjars/jquery/3.0.0/jquery.min.js"></script>
<script src="webjars/jquery-ui/1.11.4/jquery-ui.min.js"></script>

<script type="text/javascript">
	$(document).ready(
			function() {
				
				$(':input','#edit_form')
				 .not(':button, :submit, :reset, :hidden')
				 .val('');				
				
				$.get("${pageContext.request.contextPath}/rest/person/all",
						function(data, status) {
							var trHTML = '';
							$.each(data, function(i, item) {
								trHTML += "<tr id='" + item.uuid + "'><td>" + item.uuid + '</td><td>'
										+ item.firstName + '</td><td>'
										+ item.middleName + '</td><td>'
										+ item.lastName + '</td><td>'
										+ item.birthday + '</td><tr>'
							});
							$("#persons tbody").append(trHTML);
							$("#persons tr").on('click', function() {
								var uuid = $(this).find('td')
								.eq(0).html();
								var firstName = $(this).find('td')
										.eq(1).html();
								var middleName = $(this).find('td').eq(
										2).html();
								var lastName = $(this).find('td').eq(3)
										.html();
								var birthday = $(this).find('td').eq(4)
								.html();
								
								$("#edit_form input[name='uuid']")
								.val(uuid);
								$("#edit_form input[name='firstName']")
										.val(firstName);
								$("#edit_form input[name='middleName']")
								.val(middleName);
								$("#edit_form input[name='lastName']")
								.val(lastName);
								$("#edit_form input[name='birthday']")
								.val(birthday);
							});
				});
	
		        $('#edit_form').submit(function(event) {
		        	  event.preventDefault();							 
					  var form = $(this);					  
					  var uuid = form.find("input[name='uuid']").val();					  
					  
					  var data = {};
					  form.find('input').each(function(i) {
						  var input = $(this); 
					      data[input.attr("name")] = input.val();
					      delete data["undefined"]; 
					  });
					  										
					    $.ajax({
						    url: "${pageContext.request.contextPath}/rest/person/" + uuid,
					        type: "PUT",
					        data: JSON.stringify(data),
							dataType: 'json', 
						    contentType: 'application/json; charset=UTF-8',
					        success: function(data, textStatus, jqXHR) 
					        {					        	
					        	var row = $("tr#"+data.uuid);
					            row.find('td').eq(0).html(data.uuid);
					            row.find('td').eq(1).html(data.firstName);
					            row.find('td').eq(2).html(data.middleName);
					            row.find('td').eq(3).html(data.lastName);
					            row.find('td').eq(4).html(data.birthday);
					        },
					        error: function(jqXHR, textStatus, errorThrown) 
					        {
					            alert('Error!');      
					        }
					   }); 								  
				});				        		     
						       							       		        		       
				$("#birthday").datepicker({
					dateFormat: "dd.mm.yy",
					changeMonth:true,
		            changeYear:true,
		           });
				$("#birthday").datepicker('setDate', $(this).val());			
				$("#birthday").datepicker();
				
				
				$('#add_new').on('click', function(event) {
					  var form = $("#edit_form");					  
					  
					  var data = {};
					  form.find('input').each(function(i) {
						  var input = $(this); 
					      data[input.attr("name")] = input.val();
					      delete data["uuid"];
					      delete data["undefined"]; 
					  });
					  							
					    $.ajax({
						    url: "${pageContext.request.contextPath}/rest/person/",
					        type: "POST",
					        data: JSON.stringify(data),
							dataType: 'json', 
						    contentType: 'application/json; charset=UTF-8',
					        success: function(data, textStatus, jqXHR) 
					        {					        	
					        	$("#persons tr").last().after("<tr id='" + data.uuid + "'><td>"					        	
					            + data.uuid + "</td><td>" + data.firstName + "</td><td>" + data.middleName
					            + "</td><td>" + data.lastName + "</td><td>" + data.birthday + "</td></tr>");
					    		
					        	// todo: move to single function
					        	$("#persons tr").on('click', function() {
									var uuid = $(this).find('td')
									.eq(0).html();
									var firstName = $(this).find('td')
											.eq(1).html();
									var middleName = $(this).find('td').eq(
											2).html();
									var lastName = $(this).find('td').eq(3)
											.html();
									var birthday = $(this).find('td').eq(4)
									.html();
									
									$("#edit_form input[name='uuid']")
									.val(uuid);
									$("#edit_form input[name='firstName']")
											.val(firstName);
									$("#edit_form input[name='middleName']")
									.val(middleName);
									$("#edit_form input[name='lastName']")
									.val(lastName);
									$("#edit_form input[name='birthday']")
									.val(birthday);
								});
					        	
					        },
					        error: function(jqXHR, textStatus, errorThrown) 
					        {
					            alert('Error!');      
					        }
					   }); 								  
				});	
				
			});
</script>

<style>
table#persons {
	width: 100%;
	border: 1px solid black;
	border-collapse: collapse;
}

#persons tr:hover {
	background-color: #ccc;
}

#persons th {
	background-color: #fff;
}

#persons th, #persons td {
	padding: 3px 5px;
}

#persons td:hover {
	cursor: pointer;
}
</style>
<title>Persons</title>
</head>

<body>
	<h1 id="banner">Persons Info</h1>
	<hr />
	<table id="persons" border="1">
		<thead>
			<tr>
				<th>UID</th>
				<th>First Name</th>
				<th>Middle Name</th>
				<th>Last Name</th>
				<th>Birthday</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	<br>
	<div id="edit_form">
		<form action="javascript:void(null);">
			<input type="hidden" name="uuid"> First Name:<br> <input
				type="text" name="firstName"><br> Middle Name:<br>
			<input type="text" name="middleName"><br> Last Name:<br>
			<input type="text" name="lastName"><br> Birthday:<br>
			<input type="text" name="birthday" id="birthday"><br><br>
			<button type="submit">Save</button>
			<button type="button" id="add_new">Add New</button>
			<button type="reset">Clear</button>
		</form>
	</div>
</body>
</html>