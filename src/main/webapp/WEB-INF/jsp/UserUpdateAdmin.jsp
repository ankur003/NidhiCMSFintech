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

<script type="text/javascript" src="/assets/js_dev/generic.js"></script>

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
				
				<c:if test="${msgs == null}">
				<div class="col-md-12" style="margin-top: -4%;">
					<a href="/api/v1/get-div-kyc?userUuid=${user.userUuid }&id=1"><button type="button" class="btn btn-primary">Personal KYC</button></a>
					<a href="/api/v1/get-div-kyc?userUuid=${user.userUuid }&id=2"><button type="button" class="btn btn-secondary">Business Details</button></a>
					<a href="/api/v1/get-div-kyc?userUuid=${user.userUuid }&id=3"><button type="button" class="btn btn-success">Bank Details</button></a>
					<a href="/api/v1/get-div-kyc?userUuid=${user.userUuid }&id=4"><button type="button" class="btn btn-danger">Deactivate</button></a>
					<a href="/api/v1/get-div-kyc?userUuid=${user.userUuid }&id=5"><button type="button" class="btn btn-warning">Billing Charges</button></a>
					<a href="/api/v1/get-div-kyc?userUuid=${user.userUuid }&id=6"><button type="button" class="btn btn-info">White List IP</button></a>
				
				</div>
					
						            
					
					<c:if test="${id eq 1 }">
					<div id="1" >
					<div class="mu-contact-area" >
						<div class="mu-contact-content">
							<div class="row">
								<div class="col-md-12">
									<div class="mu-contact-right">
										<form class="contactform" action="/api/v1/pkycupload-Update" enctype = "multipart/form-data"	method="post">
<input type="hidden" id="userUuid" name="userUuid" value="${user.userUuid }" >
											<div class="col-lg-12">
												<strong> <font
													style="color: Blue; cursor: pointer;">Personal KYC</font>
												</strong>
											</div>

											<div class="col-lg-12">
												<div class="col-lg-6">
													<p class="comment-form-author">
														<label for="author">Upload PAN <span
															class="mandate">*</span></label> <input type="file"
															required="required" size="30" value="" name="fileUpload"
															id="pan">
													</p>

													<c:if test="${pan ne null}">
														<img src="data:image/gif;base64,${pan.data}" height="50%"
															width="50%" />
														<p>
															<font color="blue;">${pan.fileName }</font>
														</p>
													</c:if>
												</div>
												<div class="col-lg-6">
													<p class="comment-form-author">
														<label for="author">Upload Aadhar card <span
															class="mandate">*</span></label> <input type="file"
															required="required" size="30" value="" name="fileUpload"
															id="aadhar">
													</p>
													<c:if test="${aadhar ne null}">
														<img src="data:image/gif;base64,${aadhar.data}"
															height="50%" width="50%" />
														<p>
															<font color="blue;">${aadhar.fileName }</font>
														</p>
													</c:if>
												</div>
											</div>
											
											<p class="form-submit">
											<input type="submit" value="Update" class="btn btn-success"
												name="Update"> <input type="button" value="Cancel"
												class="btn btn-info" name="cancel">
										</p>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
					</c:if>
					<!------------------------------------------------------------------->
					
					
					<c:if test="${id eq 2 }">
					<div id="2">
					<div class="mu-contact-area" >
						<div class="mu-contact-content">
							<div class="row">
								<div class="col-md-12">
									<div class="mu-contact-right">
										<form class="contactform" action="/api/v1/bkycupload-update" enctype = "multipart/form-data"	method="post">
<div class="col-lg-12">
											<strong> <font style="color: Blue; cursor: pointer;">Business
													Details</font>
											</strong>
										</div>
<input type="hidden" id="userUuid" name="userUuid" value="${user.userUuid }" >
										<div class="col-lg-12">
												<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Business Entity Type<span
														class="mandate">*</span></label> <Select name="entityType"
														id="entityType" class="col-lg-12" style="height: 40px;">
														<option value=${bkyc.entityType} Selected="selected">${bkyc.entityType}</option>
														<option value="Partnership">Partnership</option>
														<option value="Sole Proprietership">Sole Proprietership</option>
														<option value="Public / Private Limited Company">Public	/ Private Limited Company</option>
														<option value="Trust / NGO / Societies">Trust /	NGO / Societies</option>
														<option value="Company yet to register">Company	yet to register</option>
													</Select>

													<%-- <input type="text" required="required" size="30" value="${bkyc.entityType }"
												name="entityType" id="entityType" > --%>
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Industry<span class="mandate">*</span></label>
													<Select name="industry" id="industry" class="col-lg-12"
														style="height: 40px;">
														<option value=${bkyc.industry} Selected="selected">${bkyc.industry}</option>
														<option value="Agriculture">Agriculture</option>
														<option value="Architect">Architect</option>
														<option value="Automobile">Automobile</option>
														<option value="Builder">Builder</option>
														<option value="Consultancy">Consultancy</option>
														<option value="Art">Art</option>
														<option value="Crowd Funding">Crowd Funding</option>
														<option value="Dealer">Dealer</option>
														<option value="Distributors">Distributors</option>
														<option value="Drop Shipping">Drop Shipping</option>
														<option value="E-Comm">E-Comm</option>
														<option value="Education">Education</option>
														<option value="Electronic/Hardware">Electronic/Hardware</option>
														<option value="Engineering Services">Engineering
															Services</option>
														<option value="Entertainment">Entertainment</option>
														<option value="Event Management">Event Management</option>
														<option value="Financial Services">Financial
															Services</option>
														<option value="Food and Beverages">Food and
															Beverages</option>
														<option value="Freelancer">Freelancer</option>
														<option value="Gambling/Casino">Gambling/Casino</option>
														<option value="Health">Health</option>
														<option value="Hospitality">Hospitality</option>
														<option value="IT / Software">IT / Software</option>
														<option value="Import / Export">Import / Export</option>
														<option value="Insurance">Insurance</option>
														<option value="Insurance">Insurance</option>
														<option value="Live Stock">Live Stock</option>
														<option value="Manpower / HR">Manpower / HR</option>
														<option value="Manufacturer">Manufacturer</option>
														<option value="Marketing Agency">Marketing Agency</option>
														<option value="Media / Advt">Media / Advt</option>
														<option value="Miscellaneous">Miscellaneous</option>
														<option value="Mobile / Computer Accessories">Mobile
															/ Computer Accessories</option>
														<option value="Multi Level Marketing">Multi Level
															Marketing</option>
														<option value="NGO">NGO</option>
														<option value="Online Services">Online Services</option>
														<option value="Pet Shop">Pet Shop</option>
														<option value="Photography / Studio">Photography
															/ Studio</option>
														<option value="Printing">Printing</option>
														<option value="Provisional Store">Provisional
															Store</option>
														<option value="Real Estate">Real Estate</option>
														<option value="Retailer / Supplier">Retailer /	Supplier</option>
														<option value="Saloon / Lifestyle">Saloon /	Lifestyle</option>
														<option value="Tobacco">Tobacco</option>
														<option value="Tours And Travels">Tours And	Travels</option>
														<option value="Trading">Trading</option>
														<option value="Transportation / Logistics">Transportation / Logistics</option>
														<option value="Unlicensed Finance Services">Unlicensed	Finance Services</option>
														<option value="Wholesaler">Wholesaler</option>
														<option value="Wine Shop">Wine Shop</option>

													</Select>
													<%-- <input type="text" required="required"  value="${bkyc.industry }"
												name="industry" id="industry" > --%>
												</p>
											</div>
										</div>

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">register Company Name<span
														class="mandate">*</span></label> <input type="text"
														required="required" size="30" value="${bkyc.compnayName }"
														name="compnayName" id="compnayName"  >
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">No.of Employees<span
														class="mandate">*</span></label> <Select name="noOfEmp"
														id="noOfEmp" class="col-lg-12" style="height: 35px;">
														<option value=${bkyc.noOfEmp} Selected="selected">${bkyc.noOfEmp}</option>
														<option value="5-20">5-20</option>
														<option value="20-50">20-50</option>
														<option value="50-100">50-100</option>
														<option value="100+">100+</option>
													</Select>
													<%-- <input type="text" required="required" size="30" value="${bkyc.noOfEmp }"
												name="noOfEmp" id="noOfEmp" > --%>
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
														id="individualPan"  >
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">GST Number<span class="mandate">*</span></label>
													<input type="text" required="required" size="30"
														value="${bkyc.gstNo }" name="gstNo" id="gstNo"  >
												</p>
											</div>
										</div>
										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Business Website Link<span
														class="mandate">*</span></label> <input type="text"
														required="required" size="30" value="${bkyc.websiteLink }"
														name="websiteLink" id="websiteLink"  >
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
												
												<c:if test="${gst ne null}">
													<img src="data:image/gif;base64,${gst.data}"
														height="50%" width="50%" />
													<p>
														<font color="blue;">${gst.fileName }</font>
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
													name="address1" id="address1"  >
											</p>
										</div>

										<div class="col-lg-12">
											<p class="comment-form-author">
												<label for="author">Address Line 2<span
													class="mandate">*</span></label> <input type="text"
													required="required" size="30" value="${bkyc.address2 }"
													name="address2" id="address2"  >
											</p>
										</div>

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Pincode<span class="mandate">*</span></label>
													<input type="text" required="required" size="30"
														value="${bkyc.pincode }" name="pincode" id="pincode"  >
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">State<span class="mandate">*</span></label>
													<input type="text" required="required" size="30"
														value="${bkyc.state }" name="state" id="state" >
												</p>
											</div>
										</div>

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">City<span class="mandate">*</span></label>
													<input type="text" required="required" size="30"
														value="${bkyc.city }" name="city" id="city" >
												</p>
											</div>
											<div class="col-lg-6"></div>
										</div>
											
											<p class="form-submit">
											<input type="submit" value="Update" class="btn btn-success"
												name="Update"> <input type="button" value="Cancel"
												class="btn btn-info" name="cancel">
										</p>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					</div>
					</c:if>
					<!-- ------------------------------------------------------------  -->
					<c:if test="${id eq 3 }">
					<div id="3">
					<div class="mu-contact-area" >
						<div class="mu-contact-content">
							<div class="row">
								<div class="col-md-12">
									<div class="mu-contact-right">
										<form class="contactform" action="/api/v1/user-bank-account-update"	 enctype = "multipart/form-data" method="post">
                                   <input type="hidden" id="userUuid" name="userUuid" value="${user.userUuid }" >
											<div class="col-lg-12">
											<strong> <font style="color: Blue; cursor: pointer;">Bank Details</font>
											</strong>
										</div>

										<c:if test="${bank.userBankDetailsId ne null}">
										<input type="hidden" required="required"  maxlength="100" value="${bank.userBankDetailsId}" name="userBankDetailsId"
														id="userBankDetailsId">

										</c:if>	

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Bank Account Holder Name<span
														class="mandate">*</span></label> <input type="text"
														required="required"  maxlength="100"
														value="${bank.bankAccHolderName}" name="bankAccHolderName"
														id="bankAccHolderName" ">
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Bank Name<span class="mandate">*</span></label>
													<input type="text" required="required" maxlength="55"
														value="${bank.bankName}" name="bankName" id="bankName" >
												</p>
											</div>
										</div>
										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Account Number<span
														class="mandate">*</span></label> <input type="text"
														required="required" size="20" value="${bank.accountNumber }"
														name="accountNumber" id="accountNumber">
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">IFSC<span
														class="mandate">*</span></label> <input type="text"
														required="required" size="10" value="${bank.ifsc }"
														name="ifsc" id="ifsc">
												</p>
											</div>
										</div> 
											<p class="form-submit">
											<input type="submit" value="Update" class="btn btn-success"
												name="Update"> <input type="button" value="Cancel"
												class="btn btn-info" name="cancel">
										</p>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					</div>
					</c:if>
					<!-- ------------------------------------------------------------ -->
					<c:if test="${id eq 4 }">
					<div id="4">
					<div class="mu-contact-area" >
						<div class="mu-contact-content">
							<div class="row">
								<div class="col-md-12">
									<div class="mu-contact-right">
										<form class="contactform" action="/api/v1/deactivateUser"	method="post">
                                     <input type="hidden" id="userUuid" name="userUuid" value="${user.userUuid }" >
											<div class="col-lg-6">
												<p class="comment-form-comment">
													<label for="comment">Deactivate Account 
													
													<p><input type="radio" name="acstatus"
													 <c:if test="${user.isActive}">checked="checked"</c:if> 
													value="active"> Active</input></p>
                                                    <p><input type="radio" name="acstatus" value="deactive"
                                                    <c:if test="${!user.isActive}">checked="checked"</c:if>> Deactive</input></p>
													
													
													<textarea name="reason" cols="25" rows="8" placeholder="Type Your reason"
														aria-required="true" required="required">
														 <c:if test="${!user.isActive}">
														${user.deactivateReason}
														</c:if>
														</textarea>
												</p>
											</div>
												<div class="col-lg-6"><br></div>
												<div class="col-lg-12">
											<p class="form-submit">
											<input type="submit" value="Update" class="btn btn-success"
												name="Update"> <input type="button" value="Cancel"
												class="btn btn-info" name="cancel">
										</p>
										</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					</div>
					</c:if>
					<!-- ------------------------------------------------------------- -->
					<c:if test="${id eq 5 }">
					<div id="5">
					<div class="mu-contact-area" >
						<div class="mu-contact-content">
							<div class="row">
								<div class="col-md-12">
									<div class="mu-contact-right">
										<form class="contactform" action="/api/v1/add-Payment-mode"	method="post">
										<input type="hidden" id="userUuid" name="userUuid" value="${user.userUuid }" >
                                          <div class="col-lg-9">
                                              <p class="comment-form-author">
													<label for="author">Product<span
														class="mandate">*</span></label><br>
														<Select id="product" style="width:65%;height: 40px;">
														<option value="payout">payout</option>
															<!-- <option value="CMS">CMS</option> -->
														</Select>
												</p>
												</div>
		
											
											
												
												<div class="col-lg-12">
												<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">Set Charges<span
														class="mandate">*</span></label><br>
                                                 <input type="hidden"  required="required" maxlength="5" name="RGS" id="RTG" value="RTG" >
                                                <input type="Text"  required="required" maxlength="5" name="RTGS" id="RTGS" value="RTGS" readonly="readonly">   
                                          
												</p>
												</div>
												<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">Bill Charge<span
														class="mandate">*</span></label><br>
                                                        <Select name="billChargeType" style="width:100%;height: 36px;">
														<option value="Flat">Flat</option>
														<option value="Percentage">Percentage</option>
														</Select>
                                          
												</p>
												</div>
												<div class="col-lg-3" style="margin-top: 2%;margin-left: -2%">
												<label for="author"></label>
												 <input type="text"  value="${RTGfeePercent}"
														required="required" maxlength="5" name="RTGfeePercent" id="RTGfeePercent" 
														onkeypress="javascript: return onlyNumbers(event);">
												</div>
												<div class="col-lg-3" style="margin-top: 3%;">
												  <input type="radio" name="rtgsstatus" value="Active" <c:if test="${rtgsstatus}">checked="checked"</c:if>> Active</input>
												  <input type="radio" name="rtgsstatus" value="Deactive" <c:if test="${!rtgsstatus}">checked="checked"</c:if>> Deactive</input>
												</div>
												</div>
												
												
													<div class="col-lg-12">
												<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">Set Charges<span
														class="mandate">*</span></label><br>
					<input type="hidden"  required="required" maxlength="5" name="IFS" id="IFS" value="IFS" >
                      <input type="Text"  required="required" maxlength="5" name="IMPS" id="IMPS" value="IMPS" readonly="readonly">                          
												</p>
												</div>
												<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">Bill Charge<span
														class="mandate">*</span></label><br>
                                                        <Select name="billChargeType2" style="width:100%;height: 36px;">
														<option value="Flat">Flat</option>
														<option value="Percentage">Percentage</option>
														</Select>
                                          
												</p>
												</div>
												<div class="col-lg-3" style="margin-top: 2%;margin-left: -2%">
												<label for="author"></label>
												 <input type="text"  required="required" maxlength="5" name="IFSfeePercent" id="IFSfeePercent" 
													value="${IFSfeePercent}"	onkeypress="javascript: return onlyNumbers(event);">
												</div>
												<div class="col-lg-3" style="margin-top: 3%;">
												  <input type="radio" name="impsstatus" value="Active"  <c:if test="${impsstatus}">checked="checked"</c:if>> Active</input>
												  <input type="radio" name="impsstatus" value="Deactive"  <c:if test="${!impsstatus}">checked="checked"</c:if>> Deactive</input>
												</div>
												</div>
												
												<div class="col-lg-12">
												<div class="col-lg-3">
												<p class="comment-form-author">
												<label for="author">Set Charges<span class="mandate">*</span></label><br>
												<input type="hidden"  required="required" maxlength="5" name="RGS" id="RGS" value="RGS" >
                                                <input type="Text"  required="required" maxlength="5" name="NEFT" id="NEFT" value="NEFT" readonly="readonly">   
												</p>
												</div>
												<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">Bill Charge<span
														class="mandate">*</span></label><br>
                                                        <Select name="billChargeType2" style="width:100%;height: 36px;">
														<option value="Flat">Flat</option>
														<option value="Percentage">Percentage</option>
														</Select>
                                          
												</p>
												</div>
												<div class="col-lg-3" style="margin-top: 2%;margin-left: -2%">
												<label for="author"></label>
												 <input type="text"  required="required" maxlength="5" name="RGSfeePercent" id="RGSfeePercent" 
												 value="${RGSfeePercent}" onkeypress="javascript: return onlyNumbers(event);">
												</div>
												<div class="col-lg-3" style="margin-top: 3%;">
												  <input type="radio" name="neftstatus" value="Active"  <c:if test="${neftstatus}">checked="checked"</c:if>> Active</input>
												  <input type="radio" name="neftstatus" value="Deactive"  <c:if test="${!neftstatus}">checked="checked"</c:if>> Deactive</input>
												</div>
												</div>
												
											
											<div class="col-lg-12">
											<p class="form-submit">
											<input type="submit" value="Add" class="btn btn-success"
												name="Update"> <input type="button" value="Cancel"
												class="btn btn-info" name="cancel">
										</p>
										</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					</div>
					</c:if>
					<!-- ---------------------------------------------------------------- -->
					
						<!-- ------------------------------------------------------------ -->
					<c:if test="${id eq 6 }">
					<div id="4">
					<div class="mu-contact-area" >
						<div class="mu-contact-content">
							<div class="row">
								<div class="col-md-12">
									<div class="mu-contact-right">
										<form class="contactform" action="/api/v1/admin-whitelist-update"	method="post">
                                     <input type="hidden" id="userUuid" name="userUuid" value="${user.userUuid }" >
												<div class="col-lg-6">
										<p class="comment-form-url">
											<label for="subject">Enter IP<span class="mandate">*</span></label>
											<input type="text" name="ip" id="ip"
												minlength="3" aria-required="true" required="required"
												maxlength="50" value="${user.whiteListIp}">
										</p>
										</div>
												<div class="col-lg-6"><br></div>
												<div class="col-lg-12">
											<p class="form-submit">
											<input type="submit" value="Update" class="btn btn-success"
												name="Update"> <input type="button" value="Cancel"
												class="btn btn-info" name="cancel">
										</p>
										</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					</div>
					</c:if>
					<!-- ------------------------------------------------------------- -->
			</c:if>
		</div>
	</div>
	</section>


	<!-- Start footer -->
	<jsp:include page="footer.jsp" />
	<!-- End footer -->


</body>
</html>