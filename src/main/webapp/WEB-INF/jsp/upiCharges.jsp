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
       document.getElementById("tab").style.display = "";
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
						<li><a href="#">Upi</a></li>
						<li class="active">UPI Valiadtion</li>
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
									<form class="contactform" action="/api/v1/get-user-search" method="post">

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
										
										<input type="hidden" id="userUuid" name="userUuid" value="${user.userUuid }" >
										
<input type="hidden" name="adminUuid" value="${userLoginDetails.userUuid}">
                                          <div class="col-lg-9">
                                              <p class="comment-form-author">
													<label for="author">Product<span
														class="mandate">*</span></label><br>
														<Select id="product" style="width:65%;height: 40px;">
														<option value="UPI">UPI</option>
														</Select>
												</p>
												</div>
		
											
											
												
												<div class="col-lg-12">
												<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">Set Charges<span
														class="mandate">*</span></label><br>
                                                <input type="Text"  required="required" maxlength="5" name="Debit" id="Debit" value="Debit" readonly="readonly">   
                                          
												</p>
												</div>
												<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">Bill Charge<span
														class="mandate">*</span></label><br>
                                                        <Select name="billChargeType" style="width:100%;height: 36px;">
														<option <c:if test="${billChargeType eq 'FLAT'}">Selected</c:if>   value="FLAT">Flat</option>
														<option <c:if test="${billChargeType eq 'PERCENTAGE'}">Selected</c:if> value="PERCENTAGE">Percentage</option>
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
                      <input type="Text"  required="required" maxlength="5" name="Credit" id="Credit" value="Credit" readonly="readonly">                          
												</p>
												</div>
												<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">Bill Charge<span
														class="mandate">*</span></label><br>
                                                        <Select name="billChargeType1" style="width:100%;height: 36px;">
														<option <c:if test="${billChargeType1 eq 'FLAT'}">Selected</c:if>  value="FLAT">Flat</option>
														<option <c:if test="${billChargeType1 eq 'PERCENTAGE'}">Selected</c:if> value="PERCENTAGE">Percentage</option>
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
				<!-- end contact content -->
				
			
			</div>
		</div>
	</div>
	</section>


	<!-- Start footer -->
	<jsp:include page="footer.jsp" />
	<!-- End footer -->


</body>
</html>