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
						<li class="active">Manage client</li>
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
							<!-- <div class="col-md-3">
								
							</div> -->
							<div class="col-md-12">
								<div class="mu-contact-right">
									<form class="contactform" action="/api/v1/user" method="post">

										<div class="col-lg-6">
											<p class="comment-form-author">
												<label for="author">Search client<span
													class="mandate">*</span></label> <input type="text"
													required="required" value="" name="fullName" id="client">
											</p>
										</div>
										<br>
										<div class="col-lg-6">
											<p class="form-submit">
												<input type="button" value="search" class="btn btn-success"
													name="Submit"> <input type="button" value="Cancel"
													class="btn btn-info" name="cancel">
											</p>
										</div>


										<!-- -----------------personal------------------------------ -->
										<div class="col-lg-12">
											<strong> <font style="color: Blue; cursor: pointer;">Personal
													KYC</font>
											</strong>
										</div>
										<p class="comment-form-author">
											<label for="author">Upload Personal PAN <span
												class="mandate">*</span></label> <input type="file"
												required="required" size="30" value="" name="fileUpload"
												id="pan">
										</p>


										<c:if test="${userDoc.docType eq 'DOCUMENT_PAN'}">
											<img src="data:image/gif;base64,${userDoc.data}" height="30%"
												width="30%" />
											<p>
												<font color="blue;">${userDoc.fileName }</font> <font
													color="green;">Already uploaded</font>
											</p>
										</c:if>

										<p class="comment-form-author">
											<label for="author">Upload Aadhar card <span
												class="mandate">*</span></label> <input type="file"
												required="required" size="30" value="" name="fileUpload"
												id="aadhar">
										</p>
										<c:if test="${userDocs.docType eq 'DOCUMENT_AADHAR'}">
											<img src="data:image/gif;base64,${userDocs.data}"
												height="30%" width="30%" />
											<p>
												<font color="blue;">${userDocs.fileName }</font> <font
													color="green;">Already uploaded</font>
											</p>
										</c:if>

										<!-- -----------------personal------------------------------ -->
										<!-- -----------------Business------------------------------ -->

										<div class="col-lg-12">
											<strong> <font style="color: Blue; cursor: pointer;">Business
													Details</font>
											</strong>
										</div>

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Business Entity Type<span
														class="mandate">*</span></label> <input type="text"
														required="required" size="30" value="${bkyc.entityType }"
														name="entityType" id="entityType">
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Industry<span class="mandate">*</span></label>
													<input type="text" required="required"
														value="${bkyc.industry }" name="industry" id="industry">
												</p>
											</div>
										</div>

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">register Company Name<span
														class="mandate">*</span></label> <input type="text"
														required="required" size="30" value="${bkyc.compnayName }"
														name="compnayName" id="compnayName">
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">No.of Employees<span
														class="mandate">*</span></label> <input type="text"
														required="required" size="30" value="${bkyc.noOfEmp }"
														name="noOfEmp" id="noOfEmp">
												</p>
											</div>
										</div>

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Individual Pan<span
														class="mandate">*</span></label> <input type="text"
														required="required" size="30"
														value="${bkyc.individualPan }" name="individualPan"
														id="individualPan">
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">GST Number<span class="mandate">*</span></label>
													<input type="text" required="required" size="30"
														value="${bkyc.gstNo }" name="gstNo" id="gstNo">
												</p>
											</div>
										</div>
										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Business Website Link<span
														class="mandate">*</span></label> <input type="text"
														required="required" size="30" value="${bkyc.websiteLink }"
														name="websiteLink" id="websiteLink">
												</p>
											</div>
											<div class="col-lg-6"></div>
										</div>

										<div class="col-lg-12">
											<strong> <font style="color: Blue; cursor: pointer;">Business
													Documents</font>
											</strong>
										</div>

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">GST Certificate<span
														class="mandate">*</span></label> <input type="file"
														required="required" size="30" value="" name="fileUpload"
														id="fileUpload">
												</p>
												<c:if test="${userDocx.docType eq 'DOCUMENT_GST'}">
													<p>
														<font color="blue;">${userDocx.fileName }</font> <font
															color="green;">Already uploaded</font>
													</p>
												</c:if>
											</div>
											<div class="col-lg-6"></div>
										</div>





										<div class="col-lg-12">
											<strong> <font style="color: Blue; cursor: pointer;">Registered
													Address</font>
											</strong>
										</div>
										<div class="col-lg-12">
											<p class="comment-form-author">
												<label for="author">Address Line 1<span
													class="mandate">*</span></label> <input type="text"
													required="required" size="30" value="${bkyc.address1 }"
													name="address1" id="address1">
											</p>
										</div>

										<div class="col-lg-12">
											<p class="comment-form-author">
												<label for="author">Address Line 2<span
													class="mandate">*</span></label> <input type="text"
													required="required" size="30" value="${bkyc.address2 }"
													name="address2" id="address2">
											</p>
										</div>

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Pincode<span class="mandate">*</span></label>
													<input type="text" required="required" size="30"
														value="${bkyc.pincode }" name="pincode" id="pincode">
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">State<span class="mandate">*</span></label>
													<input type="text" required="required" size="30"
														value="${bkyc.state }" name="state" id="state">
												</p>
											</div>
										</div>

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">City<span class="mandate">*</span></label>
													<input type="text" required="required" size="30"
														value="${bkyc.city }" name="city" id="city">
												</p>
											</div>
											<div class="col-lg-6"></div>
										</div>

										<div class="col-lg-12">
										
											<div class="col-lg-6">
												<p class="comment-form-comment">
													<label for="comment">Deactivate Account  <input type="checkbox" required="required" size="30"
														value="" name="city" id="city"></label>
													<textarea name="comment" cols="25" rows="8" placeholder="Type Your reason"
														aria-required="true" required="required"></textarea>
												</p>
											</div>
											<div class="col-lg-6"></div>
										</div>




										<p class="form-submit">
											<input type="submit" value="Update" class="btn btn-success"
												name="Submit"> <input type="button" value="Cancel"
												class="btn btn-info" name="cancel">
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
	<div id="allotedmodel" class="modal fade" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">�</button>
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