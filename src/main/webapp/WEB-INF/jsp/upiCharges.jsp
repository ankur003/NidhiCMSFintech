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

<script type="text/javascript">
  function copyUuid(uuid)
  {
       document.getElementById("userUuid").value = uuid;
       document.getElementById("onb").style.display = "";
  }
  function validform()
  {
	var valid = true;

		if (document.getElementById("gstcflag").value == 'Y') {
			if (document.getElementById("gstnnum").value == '') {
				document.getElementById("gstnnum_error").style.display = "block";
				document.getElementById("gstnnum_error").innerHTML = "Please Provide Merchant GSTIN Number";
				valid = false;
			}
		}
		return valid;
	}
  function hideMessage(divId){
		document.getElementById(divId).style.display="none";
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
						<li><a href="#">UPI </a></li>
						<li class="active">Merchant Set Charges</li>
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
							<div class="col-md-12">
								<div class="mu-contact-right">
								
								
									<form class="contactform" action="/api/v1/get-user-forSetCharge" method="post">
<input type="hidden" id="adminUuid" name="adminUuid" value="${userLoginDetails.userUuid}">
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
										
									<input type="hidden" id="userUuid" name="userUuid" value="${userLoginDetails.userUuid}">
										
									<div class="col-lg-6">
											<p class="comment-form-author">
												<label for="author">Merchant Id</label> <input type="text"
													 value="${merchantId}" name="merchantId" id="merchantId">
											</p>
										</div>
										<div class="col-lg-6">
											<p class="comment-form-author">
												<label for="author">Pan Card
												</label> <input type="text"
													value="${pancard}" name="pancard" id="pancard">
											</p>
										</div>
										<br>
										<div class="col-lg-6">
											<p class="comment-form-author">
												<label for="author">Email</label> <input type="text"
													 value="${userEmail}" name="userEmail" id="userEmail">
											</p>
										</div>
										<div class="col-lg-6">
											<p class="comment-form-author">
												<label for="author">Contact Number</label> <input type="text"
													 value="${contactNumber}" name="contactNumber" id="contactNumber">
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
															
															
															<td><a href="/api/v1/get-UserChargeInfo?userUuid=${ul.userUuid}&adminUuid=${userLoginDetails.userUuid}" >
															<input type="Button" value="Select" class="btn btn-success" name="Approve"	
															></a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
										<!-- -----------------personal------------------------------ -->
									</form>
								
								
								<c:if test="${upiCharge}">
								
									<form class="contactform" action="/api/v1/add-Payment-modes"
										method="post">

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

										<input type="hidden" id="userUuid" name="userUuid"	value="${user.userUuid }"> 
										<input type="hidden" id="adminUuid" name="adminUuid" value="${userLoginDetails.userUuid}">
										<div class="col-lg-9">
											<p class="comment-form-author">
												<label for="author">Product<span class="mandate">*</span></label><br>
												<Select id="product" style="width: 65%; height: 40px;">
													<option value="UPI">UPI</option>
												</Select>
											</p>
										</div>




										<div class="col-lg-12">
											<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">Set Charges<span
														class="mandate">*</span></label><br> <input type="Text"
														required="required" maxlength="5" name="Debit" id="Debit"
														value="Debit" readonly="readonly">

												</p>
											</div>
											<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">Bill Charge<span
														class="mandate">*</span></label><br> <Select
														name="billChargeType" style="width: 100%; height: 36px;">
														<option
															<c:if test="${billChargeType eq 'FLAT'}">Selected</c:if>
															value="FLAT">Flat</option>
														<option
															<c:if test="${billChargeType eq 'PERCENTAGE'}">Selected</c:if>
															value="PERCENTAGE">Percentage</option>
													</Select>

												</p>
											</div>
											<div class="col-lg-3"
												style="margin-top: 2%; margin-left: -2%">
												<label for="author"></label> <input type="text"
													value="${debitfeePercent}" required="required" maxlength="5"
													name="debitfeePercent" id="debitfeePercent"
													onkeypress="javascript: return onlyNumbers(event);">
											</div>
											<div class="col-lg-3" style="margin-top: 3%;">
												<input type="radio" name="debitstatus" value="Active"
													<c:if test="${debitstatus}">checked="checked"</c:if>>
												Active</input> <input type="radio" name="debitstatus"
													value="Deactive"
													<c:if test="${!debitstatus}">checked="checked"</c:if>>
												Deactive</input>
											</div>
										</div>




										<div class="col-lg-12">
											<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">Set Charges<span
														class="mandate">*</span></label><br> <input type="Text"
														required="required" maxlength="5" name="Credit"
														id="Credit" value="Credit" readonly="readonly">
												</p>
											</div>
											<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">Bill Charge<span
														class="mandate">*</span></label><br> <Select
														name="billChargeType1" style="width: 100%; height: 36px;">
														<option
															<c:if test="${billChargeType1 eq 'FLAT'}">Selected</c:if>
															value="FLAT">Flat</option>
														<option
															<c:if test="${billChargeType1 eq 'PERCENTAGE'}">Selected</c:if>
															value="PERCENTAGE">Percentage</option>
													</Select>

												</p>
											</div>
											<div class="col-lg-3"
												style="margin-top: 2%; margin-left: -2%">
												<label for="author"></label> <input type="text"
													required="required" maxlength="5" name="CreditfeePercent"
													id="CreditfeePercent" value="${CreditfeePercent}"
													onkeypress="javascript: return onlyNumbers(event);">
											</div>
											<div class="col-lg-3" style="margin-top: 3%;">
												<input type="radio" name="creditstatus" value="Active"
													<c:if test="${creditstatus}">checked="checked"</c:if>>
												Active</input> <input type="radio" name="creditstatus"
													value="Deactive"
													<c:if test="${!creditstatus}">checked="checked"</c:if>>
												Deactive</input>
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
									</c:if>

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