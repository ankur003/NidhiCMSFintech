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
       document.getElementById("subadmindiv").style.display = "";
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
					<div class="mu-contact-content" style="margin-top: -2%">
						<div class="row">
							<div class="col-md-12">
								<div class="mu-contact-right">
									<form class="contactform" action="/api/v1/get-subadmin" method="post">
                                 	<input type="hidden" id="userUuid" name="userUuid" value="${userLoginDetails.userUuid}">
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
 															
															<td><a href="/api/v1/get-userDetails?userUuid=${ul.userUuid}"> 
															<input type="Button" value="Select" class="btn btn-success" name="Approve"
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
			
			
			
			
			
			
			
			
			
			
			
				<div class="mu-contact-area">
					<!-- start title -->
					<!-- <div class="mu-title">
						<h2>Register Here</h2>
						</div> -->
					<!-- end title -->
					<!-- start contact content -->
					
					
					<c:if test="${user ne null }">
					
					<div class="mu-contact-content" id="subadmindiv" >
						<div class="row">
							<div class="col-md-3">
								
							</div>
							<div class="col-md-9">
								<div class="mu-contact-right">
									<form class="contactform" action="/api/v1/subadminUpdate" method="post">


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
										

                                   	<input type="hidden" id="userUuid" name="userUuid" value="${userLoginDetails.userUuid}">
                                       <input type=hidden id="userId" name="userId"  value="${user.userId }"  >
										<p class="comment-form-author">
											<label for="author">Full Name <span class="mandate">*</span></label>
											<input type="text" required="required" size="30" value="${user.fullName }"
												name="fullName" id="fullName" maxlength="50">
										</p>
										<p class="comment-form-email">
											<label for="email">Email <span class="mandate">*</span></label>
											<input type="email" required="required" aria-required="true"
												value="${user.userEmail }" name="userEmail" id="userEmail" maxlength="250">
										</p>
										
										<p class="comment-form-comment">
											<label for="comment">Contact Number<span
												class="mandate">*</span></label> <input type="text"
												required="required" aria-required="true" value="${user.mobileNumber }"
												name="mobileNumber" id="mobileNumber" maxlength="10">
										</p>
										
										<p class="comment-form-url">
											<label for="subject">Password</label>
											<input type="password" name="password" id="password" minlength="3"
												aria-required="true" maxlength="10">
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
												
												<c:set var = "theString" value = "${plist}"/>
													<c:forEach items="${listprivlege}" var="ul" varStatus="counter">
													
														<tr>
															<th scope="row">${counter.count}</th>
															<td>${ul.privilegeName}</td>
															<td><label class="checkbox-inline">
															<input type="checkbox" name="privilageNames"
															 <c:if test="${fn:contains(theString,ul.privilegeName)}">checked </c:if>
															 			value="${ul.privilegeName}"></label></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
															
										<br>
										<p class="form-submit">
											<input type="submit" value="Update" class="btn btn-success"
												name="signup">
												 <input
												type="button" value="Cancel" class="btn btn-info"
												name="cancel">
										</p>
										
										
										
										
										
									</form>

								</div>
							</div>
						</div>
					</div>
					</c:if>
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