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
<title>NIDHI CMS | ADMIN DASHBOARD</title>

<!-- Favicon -->

</head>
<c:if test="${sessionScope.userLoginDetails eq null}">
	<c:redirect url="/api/v1/fe/login"></c:redirect>
</c:if> 
<body>
<jsp:include page="adminmenu.jsp" />

	<!-- Page breadcrumb -->
	<section id="mu-page-breadcrumb">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="mu-page-breadcrumb-area">
					<h2>My Dashboard</h2>
					<ol class="breadcrumb">
						<li><a href="#">Client Onboarding </a></li>
						<li class="active">Create</li>
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
							<div class="col-md-3">
								
							</div>
							<div class="col-md-9">
								<div class="mu-contact-right">
									<form class="contactform" action="/api/v1/userbyAdmin" method="post">


										<c:choose>
											<c:when test="${msg!=null}">
												<p align='center'
													style="border-style: solid; border-color: Green;">
													<font color="green"> ${msg} </font>
												</p>
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>

									 <input type="hidden" id="" name="" value="USER"> 
										<p class="comment-form-author">
											<label for="author">Full Name <span class="mandate">*</span></label>
											<input type="text" required="required" size="30" value=""
												name="fullName" id="fullName" maxlength="50">
										</p>
										<p class="comment-form-email">
											<label for="email">Email <span class="mandate">*</span></label>
											<input type="email" required="required" aria-required="true"
												value="" name="userEmail" id="userEmail" maxlength="250">
										</p>
										
										<p class="comment-form-comment">
											<label for="comment">Contact Number<span
												class="mandate">*</span></label> <input type="text"
												required="required" aria-required="true" value=""
												name="mobileNumber" id="mobileNumber" maxlength="10">
										</p>
										<p class="comment-form-url">
											<label for="subject">Password<span class="mandate">*</span></label>
											<input type="password" name="password" id="password" minlentg="3"
												aria-required="true" required="required" maxlength="10">
										</p>
										<p class="form-submit">
											<input type="submit" value="Add" class="btn btn-success"
												name="signup"> <input type="reset" value="Reset"
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
	<div id="allotedmodel" class="modal fade" tabindex="-1" role="dialog"	aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h1 class="text-center">Alloted Batch Timeings</h1>
				</div>
				<div class="modal-body">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="text-center" id="showallotedtiming"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="col-md-12">
						<button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Start footer -->
	<jsp:include page="footer.jsp" />
	

</body>
</html>