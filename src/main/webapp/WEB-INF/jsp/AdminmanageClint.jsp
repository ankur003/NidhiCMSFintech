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
 															<%-- <td><a href="/api/v1/kyc-auth?userUuid=${ul.userUuid}&kycResponse=true">
															<input type="Button" value="Select" class="btn btn-success" name="Approve"></a></td> --%>
															<td><a href="/api/v1/get-div-kyc?userUuid=${ul.userUuid}&id=1" target="_blank"><input type="Button" value="Select" class="btn btn-success" name="Approve"
 															onclick="javascript:copyUuid('${ul.userUuid}')"></a></td>
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
				<!-- end contact content -->
				
				<c:if test="${user ne null }">
				
				<div class="col-md-12" >
					<a href = "javascript:;" onclick = "this.href='/api/v1/get-kyc-data?userUuid=' + document.getElementById('userUuid').value"
					><button type="button" class="btn btn-primary">Personal KYC</button></a>
					<a href = "javascript:;" onclick = "this.href='/api/v1/get-kyc-data?userUuid=' + document.getElementById('userUuid').value">
					<button type="button" class="btn btn-secondary">Business Details</button></a>
					<a href = "javascript:;" onclick = "this.href='/api/v1/get-kyc-data?userUuid=' + document.getElementById('userUuid').value">
					<button type="button" class="btn btn-success">Bank Details</button></a>
					<a href = "javascript:;" onclick = "this.href='/api/v1/get-kyc-data?userUuid=' + document.getElementById('userUuid').value">
					<button type="button" class="btn btn-danger">Deactivate</button></a>
					<a href = "javascript:;" onclick = "this.href='/api/v1/get-kyc-data?userUuid=' + document.getElementById('userUuid').value">
					<button type="button" class="btn btn-warning">Billing Charges</button></a>
				</div>
					
					</c:if>
					<input type="hidden" id="userUuid" name="userUuid" >
			
			</div>
		</div>
	</div>
	</section>


	<!-- Start footer -->
	<jsp:include page="footer.jsp" />
	<!-- End footer -->


</body>
</html>