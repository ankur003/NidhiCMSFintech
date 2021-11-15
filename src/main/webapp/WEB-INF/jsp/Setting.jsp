<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>NIDHI CMS | DASHBOARD</title>

<link rel="stylesheet"	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    $( "#datepicker" ).datepicker({
    	 changeMonth: true,
         changeYear: true,
         dateFormat: 'dd-mm-yy',
         endDate: "today",
         maxDate: "today"
    });
  } );
  
  $( function() {
	    $( "#datepicker1" ).datepicker({
	    	 changeMonth: true,
	         changeYear: true,
	         dateFormat: 'dd-mm-yy',
	         endDate: "today",
	         maxDate: "today"
	    });
	  } );
  </script>



</head>
<c:if test="${sessionScope.authtoken eq null}">
	<c:redirect url="/api/v1/fe/login"></c:redirect>
</c:if>
<body>
	<jsp:include page="usermenu.jsp" />
	<!-- Page breadcrumb -->
	<section id="mu-page-breadcrumb">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="mu-page-breadcrumb-area">
					<h2>My Dashboard</h2>
					<ol class="breadcrumb">
						<li><a href="#">Home</a></li>
						<li class="active">Dashboard</li>
					</ol>
				</div>
			</div>
		</div>
	</div>
	</section>
	<!-- End breadcrumb -->

	<!-- Start contact  -->
	<section id="mu-contact">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="mu-contact-area">
					<!-- start title -->
					<!-- <div class="mu-title">
						<h2>Register Here</h2>
						</div> -->
					<!-- end title -->
					<!-- start contact content -->




					<div class="mu-contact-content" style="margin-top: -2%">
						<div class="row">
							<div class="col-md-3"></div>
							<div class="col-md-9">
								<div class="mu-contact-right">
									<form class="contactform" action="/api/v1/updateEmailpass"
										method="post">

										<c:choose>
											<c:when test="${msg!=null}">
												<p align='center'
													style="border-style: solid; border-color: green;">
													<font color="green"> ${msg} </font>
												</p>
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>

										<input type="hidden" value="usr" name="utype">
										 <p class="comment-form-email">
											<label for="email">First Name <span class="mandate">*</span></label>
											<input type="text" required="required" aria-required="true"
												value="${user.firstName}" name="firstName" id="firstName" maxlength="250">
										</p>
										<p class="comment-form-email">
											<label for="email">Last Name <span class="mandate">*</span></label>
											<input type="text" required="required" aria-required="true"
												value="${user.lastName}" name="lastName" id="lastName" maxlength="250">
										</p>
										<p class="comment-form-email">
											<label for="email">Full Name <span class="mandate">*</span></label>
											<input type="text" required="required" aria-required="true"
												value="${user.fullName}" name="fullName" id="fullName" maxlength="250">
										</p>
										<p class="comment-form-author">
													<label for="author">DOB<span class="mandate">*</span></label>
													<input type="text"  value="${user.dob}"
														name="dob" id="datepicker" autocomplete="off">
												</p>
										<p class="comment-form-email">
											<label for="email">Email <span class="mandate">*</span></label>
											<input type="email" required="required" aria-required="true"
												value="${user.userEmail}" name="userEmail" id="userEmail" maxlength="250">
										</p>

										<p class="comment-form-url">
											<label for="subject">Password</label>
											<input type="password" name="password" id="password"
												minlentg="3" aria-required="true" pattern="^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d][A-Za-z\d!@#$%^&*()_+]{7,19}$" 
												title="Must contain at least one number and one uppercase and lowercase letter,one special  
												and at least 8 or more characters"
												>
										</p>
										 <p class="comment-form-email">
											<label for="email">IP </label>
											<input type="email" required="required" aria-required="true" disabled="disabled"
												value="${user.whiteListIp}" name="whiteListIp" id="whiteListIp" maxlength="250">
										</p>
										<p class="form-submit">
											<input type="submit" value="Update" class="btn btn-success"
												name="updte"> <input type="reset" value="Reset"
												class="btn btn-warning" name="reset"> <input
												type="button" value="Cancel" class="btn btn-info"
												name="cancel">
										</p>






									</form>
								</div>










							</div>
						</div>
					</div>
				</div>
				<!-- end contact content -->
			</div>
		</div>
	</div>
	</div>
	</section>
	<!-- End contact  -->
	<!-- lgt box start -->


	<!--modal-->


	<!-- Start footer -->
	<jsp:include page="footer.jsp" />
	<!-- End footer -->

</body>
</html>