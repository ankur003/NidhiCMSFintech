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
<body >
<jsp:include page="adminmenu.jsp" />
	<!-- Page breadcrumb -->
	<section id="mu-page-breadcrumb">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="mu-page-breadcrumb-area">
					<h2>My Dashboard</h2>
					<ol class="breadcrumb">
						<li><a href="#">Report</a></li>
						<li class="active">Transaction inquiry  </li>
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
							<div class="col-md-12">
								<div class="mu-contact-right">
									<form class="contactform"  method="post" action="/api/v1/findByUnique">

                                    <input type="hidden" id="userUuid" name="userUuid" value="${userLoginDetails.userUuid}">

 										<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">Unique Number<span class="mandate">*</span></label>
													<input type="text" required="required" value="${uniqueNum}"
														name="uniqueNum" id="uniqueNum" autocomplete="off">
												</p>
											</div>
											<br>
											<div class="col-lg-6">
										    <p class="form-submit" >
											<input type="submit" value="Submit" class="btn btn-success"
												name="Submit"> <input
												
												type="button" value="Cancel" class="btn btn-info"
												name="cancel">
										</p>
										</div>



										<c:if test="${init }">
											<table class="table" style="margin-top: 2%;">
												<thead class="thead-light" style="background-color: gray;">
													<tr>
														<th scope="col">#</th>
														<th scope="col">Date</th>
														<th scope="col">Tx Type</th>
														<th scope="col">Amount</th>
													    <th scope="col">Fee</th>
														<th scope="col">Currency</th>
														<th scope="col">Transaction</th>
														<th scope="col">Status</th>
														<th scope="col">Txn Status</th>
														
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${trans}" var="us"
														varStatus="counter">
														<tr>
															<th scope="row">${counter.count}</th>
															<td><fmt:parseDate value="${us.txDate}"
																	pattern="yyyy-MM-dd" var="disbDate" /> <fmt:formatDate
																	value="${disbDate}" pattern="dd-MM-yyyy" /></td>
															<td>${us.txType}</td>
															<td>${us.amount}</td>
															<td>${us.fee}</td>
															 <td>${us.currency}</td> 
															<td>${us.txnType}</td>
															<td>${us.status}</td>
															<td> <input	type="button" value="call" class="btn btn-info"	name="Call"></td>
															
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