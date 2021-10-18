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


<script type="text/javascript">
  function copyUuid(uuid)
  {
       document.getElementById("userUuid").value = uuid;
  }
</script>
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
						<li><a href="#">Home</a></li>
						<li class="active">Product Featuring</li>
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
					<div class="mu-contact-content" style="margin-top: -2%">
						<div class="row">
							<div class="col-md-12">
								<div class="mu-contact-right">
									<form class="contactform" action="/api/v1/get-user-productFeature" method="post">

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

									<div class="col-lg-6">
											<p class="comment-form-author">
												<label for="author">First Name</label> <input type="text"
													 value="${firstName}" name="firstName" id="client">
											</p>
										</div>
										<div class="col-lg-6">
											<p class="comment-form-author">
												<label for="author">Last name
												</label> <input type="text"
													value="${lastName}" name="lastName" id="client">
											</p>
										</div>
										<br>
										<div class="col-lg-6">
											<p class="comment-form-author">
												<label for="author">Email</label> <input type="text"
													 value="${userEmail}" name="userEmail" id="client">
											</p>
										</div>
										<div class="col-lg-6">
											<p class="comment-form-author">
												<label for="author">Contact Number</label> <input type="text"
													 value="${username}" name="username" id="client">
											</p>
										</div>

                                        <div class="col-lg-12">
											<p class="form-submit">
												<input type="submit" value="search" class="btn btn-success"
													name="Submit"> <input type="button" value="Cancel"
													class="btn btn-info" name="cancel">
											</p>
										</div>

                                        <c:if test="${init }">
											<table class="table table-striped">
												<thead class="thead-dark">
													<tr>
														<th scope="col">#</th>
														<th scope="col">Full Name</th>
														<th scope="col">Email</th>
														<th scope="col">Mobile</th>
														<th scope="col">Action</th>
													</tr>
												</thead>
												<tbody>
												
													<c:forEach items="${userList}" var="ul"		varStatus="counter">
														<tr>
															<th scope="row">${counter.count}</th>
															<td>${ul.fullName}</td>
															<td>${ul.userEmail}</td>
															<td>${ul.mobileNumber}</td>
 															<td><input type="Button" value="Select" class="btn btn-success" name="Approve"
 															onclick="javascript:copyUuid('${ul.userUuid}')"></td>
															
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
										<!-- -----------------personal------------------------------ -->
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
				<div class="mu-contact-area">

					<div class="mu-contact-content" >
						<div class="row">
							<!-- <div class="col-md-3">
								
								
								
							</div> -->
							<div class="col-md-12">
								<div class="mu-contact-right">
									<form class="contactform" action="/api/v1/user-payment-mode" method="post">
										<div class="col-lg-12">
										
										<input type="hidden" id="userUuid" name="userUuid" >
										
											<p class="comment-form-author">
												<label for="author">Product List<span
													class="mandate">*</span></label> <br> <Select
													required="required" name="paymentMode" id="paymentMode"
													class="col-lg-3">
													<option value="UPI">UPI</option>
													<option value="NEFT">NEFT</option>
													<option value="IMPS">IMPS</option>
												</Select>
											</p>
										</div>
										<div class="col-lg-12"><br></div>
										<div class="col-lg-12">
											<p class="form-submit">
												<input type="submit" value="Submit" class="btn btn-success"
													name="Submit"> <input type="button" value="Cancel"
													class="btn btn-info" name="cancel">
											</p>

										</div>
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