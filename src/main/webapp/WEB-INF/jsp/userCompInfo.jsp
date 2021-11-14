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
					<div class="mu-contact-content" >
						<div class="row">
							<div class="col-md-12">
								<div class="mu-contact-right">
									<form class="contactform" action="/api/v1/get-user-search" method="post">

									
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
														name="entityType" id="entityType" disabled="disabled">
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Industry<span class="mandate">*</span></label>
													<input type="text" required="required"
														value="${bkyc.industry }" name="industry" id="industry" disabled="disabled">
												</p>
											</div>
										</div>

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">register Company Name<span
														class="mandate">*</span></label> <input type="text"
														required="required" size="30" value="${bkyc.compnayName }"
														name="compnayName" id="compnayName" disabled="disabled">
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">No.of Employees<span
														class="mandate">*</span></label> <input type="text"
														required="required" size="30" value="${bkyc.noOfEmp }"
														name="noOfEmp" id="noOfEmp" disabled="disabled">
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
														id="individualPan" disabled="disabled">
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">GST Number<span class="mandate">*</span></label>
													<input type="text" required="required" size="30"
														value="${bkyc.gstNo }" name="gstNo" id="gstNo" disabled="disabled">
												</p>
											</div>
										</div>
										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Business Website Link<span
														class="mandate">*</span></label> <input type="text"
														required="required" size="30" value="${bkyc.websiteLink }"
														name="websiteLink" id="websiteLink" disabled="disabled">
												</p>
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
													name="address1" id="address1" disabled="disabled">
											</p>
										</div>

										<div class="col-lg-12">
											<p class="comment-form-author">
												<label for="author">Address Line 2<span
													class="mandate">*</span></label> <input type="text"
													required="required" size="30" value="${bkyc.address2 }"
													name="address2" id="address2" disabled="disabled">
											</p>
										</div>

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Pincode<span class="mandate">*</span></label>
													<input type="text" required="required" size="30"
														value="${bkyc.pincode }" name="pincode" id="pincode" disabled="disabled">
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">State<span class="mandate">*</span></label>
													<input type="text" required="required" size="30"
														value="${bkyc.state }" name="state" id="state" disabled="disabled">
												</p>
											</div>
										</div>

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">City<span class="mandate">*</span></label>
													<input type="text" required="required" size="30"
														value="${bkyc.city }" name="city" id="city" disabled="disabled">
												</p>
											</div>
											<div class="col-lg-6"></div>
										</div>

										<!-- <div class="col-lg-12">
										
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
 -->
									<div class="col-lg-12">
											<strong> <font style="color: Blue; cursor: pointer;">Bank Details</font>
											</strong>
										</div>

											
										<input type="hidden"
														required="required"  maxlength="100"
														value="${bank.userBankDetailsId}" name="userBankDetailsId"
														id="userBankDetailsId">

										

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Bank Account Holder Name<span
														class="mandate">*</span></label> <input type="text"
														required="required"  maxlength="100"
														value="${bank.bankAccHolderName }" name="bankAccHolderName"
														id="bankAccHolderName" disabled="disabled">
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Bank Name<span class="mandate">*</span></label>
													<input type="text" required="required" maxlength="55"
														value="${bank.bankName}" name="bankName" id="bankName" disabled="disabled">
												</p>
											</div>
										</div>
										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Account Number<span
														class="mandate">*</span></label> <input type="text"
														required="required" size="20" value="${bank.accountNumber }"
														name="accountNumber" id="accountNumber" disabled="disabled">
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">IFSC<span
														class="mandate">*</span></label> <input type="text"
														required="required" size="10" value="${bank.ifsc }"
														name="ifsc" id="ifsc" disabled="disabled">
												</p>
											</div>
										</div>


									<!-- 	<p class="form-submit">
											<input type="submit" value="Update" class="btn btn-success"
												name="Submit"> <input type="button" value="Cancel"
												class="btn btn-info" name="cancel">
										</p> -->
										<!-- -----------------personal------------------------------ -->
										
										<div class="col-lg-12">
											<strong> <font style="color: Blue; cursor: pointer;">Personal
													KYC</font>
											</strong>
										</div>

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Uploaded PAN </label> 
												</p>

												<c:if test="${pan ne null}">
													<img src="data:image/gif;base64,${pan.data}" height="70%"
														width="70%" />
													<p>
														<font color="blue;">${pan.fileName }</font>
													</p>
												</c:if>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Uploaded Aadhar card </label> 
												</p>
												<c:if test="${aadhar ne null}">
													<img src="data:image/gif;base64,${aadhar.data}"
														height="70%" width="70%" />
													<p>
														<font color="blue;">${aadhar.fileName }</font>
													</p>
												</c:if>
											</div>
											
											<div class="col-lg-12">
											<strong> <font style="color: Blue; cursor: pointer;">Business
													Documents</font>
											</strong>
										</div>
												<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">GST Certificate</label> 
												</p>
												
												<c:if test="${gst ne null}">
													<img src="data:image/gif;base64,${gst.data}"
														height="70%" width="70%" />
													<p>
														<font color="blue;">${gst.fileName }</font>
													</p>
												</c:if>
												
											</div>
											<div class="col-lg-6"></div>
										</div>
										</div>
										<!-- -----------------personal------------------------------ -->
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				
			</div>
		</div>
	</div>
	</section>


	<!-- Start footer -->
	<jsp:include page="footer.jsp" />

</body>
</html>