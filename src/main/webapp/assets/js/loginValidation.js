$(document).ready(function() {


	$("#email_error").hide();
	$("#password_error").hide();


	var email_error = false;
	var password_error = false;

	$("#emailid_from_login").focusout(function() {
		// $("#email_error").hide();
		$("#emailid_from_login").css("border", "1px solid #ced4da");
		check_email();
	});
	$("#emailid_from_login").keyup(function() {
		check_email();
	});
	$("#emailid_from_login").focus(function() {
		check_email();
	});

	$("#password_from_login").focusout(function() {
		// $("#password_error").hide();
		$("#pwd").css("border", "1px solid #ced4da");
		check_password();
	});
	$("#password_from_login").keyup(function() {
		check_password();
	});
	$("#password_from_login").focus(function() {
		check_password();
	});


	function check_email() {
		var pattern = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
		var email = $("#emailid_from_login").val();
		if (email.length == 0) {
			$("#email_error").html("Email can not be empty!!").css("color", "red");
			$("#email_error").show();
			$("#emailid_from_login").css("border-bottom", "4px solid #F90A0A");
			email_error = true;
		} else if (!pattern.test(email)) {
			$("#email_error").html("Invalid Email!!").css("color", "red");
			$("#email_error").show();
			$("#emailid_from_login").css("border-bottom", "4px solid #F90A0A");
			email_error = true;
		} else {
			$("#email_error").hide();
			$("#emailid_from_login").css("border-bottom", "4px solid #34F458");
		}

	}
	function check_password() {
		var password_length = $("#password_from_login").val().length;
		if (password_length == 0) {
			$("#password_error").html("Password can not be empty!!").css("color", "red");
			$("#password_error").show();
			$("#password_from_login").css("border-bottom", "4px solid #F90A0A");
			password_error = true;
		} else {
			$("#password_error").hide();
			$("#password_from_login").css("border-bottom", "4px solid #34F458");
		}
	}


	$("#login_form").submit(function() {
		email_error = false;
		password_error = false;

		check_email();
		check_password();


		if (email_error === false && password_error === false) {
			alert("Login successful..");
			return true;
		} else {
			alert("Please Fill the form Correctly");
			return false;
		}

	});


});