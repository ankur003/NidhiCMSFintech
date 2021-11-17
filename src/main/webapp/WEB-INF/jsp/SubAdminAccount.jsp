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
						<li><a href="#">Subadmin</a></li>
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
									<form class="contactform" action="/api/v1/subadmin-add" method="post">


										<c:choose>
											<c:when test="${msg!=null}">
												<p align='center'
													style="border-style: solid; border-color: green;">
													<font color="green"> ${msg} </font>
												</p>
											</c:when>
											<c:when test="${msgs!=null}">
												<p align='center'
													style="border-style: solid; border-color: red;">
													<font color="red"> ${msgs} </font>
												</p>
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>
                                      <input type="hidden" id="" name="" value="SUBADMIN"> 
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
												aria-required="true" 
												pattern="^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d][A-Za-z\d!@#$%^&*()_+]{7,19}$" 
												title="Must contain at least one number and one uppercase and lowercase letter,one special  
												and at least 8 or more characters"
												required="required" >
										</p>
										
										
										 
											<table class="table table-striped">
												<thead class="thead-dark">
													<tr>
														<th scope="col">#</th>
														<th scope="col">Privilege Name </th>
														<th scope="col">Action</th>
													</tr>
												</thead>
												<tbody>
												
													<c:forEach items="${privilegeList}" var="ul" varStatus="counter">
														<tr>
															<th scope="row">${counter.count}</th>
															<td>${ul.privilegeName}</td>
 															<td> 
 															<label class="checkbox-inline"><input type="checkbox" name="privilageNames"
 															 value="${ul.privilegeName}"></label>
 															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										
										
										
										<!-- <div>
										<label class="checkbox-inline"><input type="checkbox" value="">Onboarding</label>
										<label class="checkbox-inline"><input type="checkbox" value="">Product Featuring</label>
										<label class="checkbox-inline"><input type="checkbox" value="">SubAdmin</label>
										<label class="checkbox-inline"><input type="checkbox" value="">Report</label>
										</div> -->
										<br>
										<p class="form-submit">
											<input type="submit" value="Create" class="btn btn-success"
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
	<!-- End footer -->

	<!-- jQuery library -->
	<!-- <script src="assets/js/jquery.min.js"></script> -->
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="/assets/js/bootstrap.js"></script>
	<!-- Slick slider -->
	<script type="text/javascript" src="/assets/js/slick.js"></script>
	<!-- Counter -->
	<script type="text/javascript" src="/assets/js/waypoints.js"></script>
	<script type="text/javascript" src="/assets/js/jquery.counterup.js"></script>
	<!-- Mixit slider -->
	<script type="text/javascript" src="/assets/js/jquery.mixitup.js"></script>
	<!-- Add fancyBox -->
	<script type="text/javascript" src="/assets/js/jquery.fancybox.pack.js"></script>

	<!-- Custom js -->
	<script src="/assets/js/custom.js"></script>

</body>
</html>