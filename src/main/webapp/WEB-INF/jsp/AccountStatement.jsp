<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>NIDHI CMS | ACCOUNT Statement</title>
 <link rel="stylesheet"	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 
  <script>
  $( function() {
    $( "#datepicker" ).datepicker({
    	 changeMonth: true,
         changeYear: true,
         dateFormat: 'dd-mm-yy',
         endDate: "today",
         maxDate: "today"
    });
  } );
  
  $( function() {
	    $( "#datepicker1" ).datepicker({
	    	 changeMonth: true,
	         changeYear: true,
	         dateFormat: 'dd-mm-yy',
	         endDate: "today",
	         maxDate: "today"
	    });
	  } );
  </script>

</head>
<c:if test="${sessionScope.authtoken eq null}">
	<c:redirect url="/api/v1/fe/login"></c:redirect>
</c:if> 
<body >
<jsp:include page="usermenu.jsp" />
	<!-- Page breadcrumb -->
	<section id="mu-page-breadcrumb">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="mu-page-breadcrumb-area">
					<h2>My Dashboard</h2>
					<ol class="breadcrumb">
						<li><a href="#">Home</a></li>
						<li class="active">Dashboard</li>
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
                                  <form class="contactform"  method="post" action="/api/v1/userTransactionOnDates" >
                                          <div class="col-sm-12">
                                            <p class="comment-form-comment">
													<label for="comment">Transaction Details</label>
												</p>
												</div>
	                                    <input type="hidden" id="userUuid" name="userUuid" value="${userLoginDetails.userUuid}">

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">From date<span class="mandate">*</span></label>
													<input type="text" required="required" value="${startDate}"
														name="startDate" id="datepicker" autocomplete="off">
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">To date<span class="mandate">*</span></label>
													<input type="text" required="required" value="${endDate}"
														name="endDate" id="datepicker1" autocomplete="off">
												</p>
											</div>
										</div>

										<p class="form-submit" align="left">
											<input type="submit" value="Submit" class="btn btn-success"
												name="Submit" onclick> <input
												
												type="button" value="Cancel" class="btn btn-info"
												name="cancel">
										</p>


										<c:if test="${init }">
											<table class="table" style="margin-top: 2%;">
												<thead class="thead-light" style="background-color: gray;">
													<tr>
														<th scope="col">#</th>
														<th scope="col">Date</th>
														<th scope="col">Unique Id</th>
														<th scope="col">Description</th>
													    <th scope="col">Dr</th>
														<th scope="col">Cr</th>
														<th scope="col">Balance</th>
														
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
																<td>${us.uniqueId}</td>		
															<td><c:if test="${us.isFeeTx eq true}">Charges </c:if>${us.utrNumber}/${us.payeeName}/
															<c:if test="${us.txnType eq 'RTG'}">RTGS</c:if>
																<c:if test="${us.txnType eq 'IFS'}">IMPS</c:if>
																	<c:if test="${us.txnType eq 'RGS'}">NEFT</c:if>
															</td>		
															  <c:choose><c:when test="${us.txType eq 'Dr.'}"><td>${us.amount}</td> 	<c:set var="totalDr" value="${totalDr+us.amount}" /></c:when><c:otherwise><td>0.0</td></c:otherwise></c:choose>											
															<c:choose><c:when test="${us.txType eq 'Cr.'}"><td>${us.amount}</td>     <c:set var="totalCr" value="${totalCr+us.amount}" /></c:when><c:otherwise><td>0.0</td></c:otherwise></c:choose>	      
															<td>${us.amt}</td>
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
	<div id="allotedmodel" class="modal fade" tabindex="-1" role="dialog"
		aria-hidden="true">
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

	

</body>
</html>