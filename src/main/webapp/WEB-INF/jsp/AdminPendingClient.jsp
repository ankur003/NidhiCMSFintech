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
						<li class="active">Pending Client</li>
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



<!-- 
<button type="button" id="showalloted"	data-target="#allotedmodel" data-toggle="modal">alloted</button>

 -->
										<c:if test="${init }">
											<table class="table table-striped">
												<thead class="thead-dark">
													<tr>
														<th scope="col">#</th>
														<th scope="col">Full Name</th>
														<th scope="col">Email</th>
														<th scope="col">Mobile</th>
														<th scope="col">DOB</th>
														<th scope="col">PAN</th>
														<th scope="col">AADHAR</th>
														<th scope="col">GST</th>
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
															<td><fmt:parseDate value="${ul.dob}"
																	pattern="yyyy-MM-dd" var="disbDate" /> <fmt:formatDate
																	value="${disbDate}" pattern="dd-MM-yyyy" /></td>
															<%-- <td><a href="/api/v1/get-user-docs?userUuid=${ul.userUuid}&docType=PAN" id="showalloted"	
															                           data-target="#allotedmodel" data-toggle="modal">PAN</a></td>
															<td><a href="/api/v1/get-user-docs?userUuid=${ul.userUuid}&docType=AADHAR" id="showalloted"	
															                           data-target="#allotedmodel" data-toggle="modal">AADHAR</a></td>
															<td><a href="/api/v1/get-user-docs?userUuid=${ul.userUuid}&docType=GST" id="showalloted"	
															                           data-target="#allotedmodel" data-toggle="modal">GST</a></td> --%>
													
 
 	                                                        <td><a href="/api/v1/get-user-docs?userUuid=${ul.userUuid}&docType=PAN" target="_blank"><font color="blue">PAN</font></a></td>
															<td><a href="/api/v1/get-user-docs?userUuid=${ul.userUuid}&docType=AADHAR" target="_blank"><font color="blue">AADHAR</font></a></td>
															<td><a href="/api/v1/get-user-docs?userUuid=${ul.userUuid}&docType=GST" target="_blank"><font color="blue">GST</font></a></td>
 	
														
															<td>
															 <c:choose>
															<c:when test="${ul.kycStatus eq 'PENDING'}"> 
															 <a href="/api/v1/kyc-auth?userUuid=${ul.userUuid}&kycResponse=true">
															<input type="Button" value="Approve" class="btn btn-success" name="Approve"></a>
														 	</c:when>
														 	<c:otherwise>
														 	 <a href="#">
															<input type="Button" value="Approve" class="btn btn-success" name="Approve" disabled="disabled"></a>
														 	</c:otherwise>
														 	</c:choose> 
														 	 
															 <c:choose>
														<c:when test="${ul.kycStatus eq 'VERIFIED'}"> 
											            <a href="/api/v1/kyc-auth?userUuid=${ul.userUuid}&kycResponse=false">
											            <input type="button" value="Reject" class="btn btn-danger"	name="reject"></a>
											         </c:when>
											           <c:otherwise>
											            <a href="#">
											            <input type="button" value="Reject" class="btn btn-danger"	name="reject" disabled="disabled"></a>
															 </c:otherwise>
															</c:choose> 
											            
											            </td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>

										
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
					<h1 class="text-center">Document</h1>
				</div>
				<div class="modal-body">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="panel-body">
							
							
							<jsp:include page="docsView.jsp" />
							
							
							
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
	<!-- end footer bottom --> </footer>
<!-- jQuery library -->
   <script src="/assets/js/jquery.min.js"></script> 
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