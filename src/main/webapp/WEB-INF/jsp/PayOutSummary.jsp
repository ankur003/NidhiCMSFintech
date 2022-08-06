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
<title>NIDHI CMS | DASHBOARD</title>

<!-- Favicon -->


</head>
<c:if test="${sessionScope.authtoken eq null}">
	<c:redirect url="/api/v1/fe/login"></c:redirect>
</c:if> 
<body>

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
					
							<div class="col-md-12">
								<div class="mu-contact-right">
									<form class="contactform">
									
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
                            
                            <div class="col-sm-12">
											<div class="col-sm-4">
												<p class="comment-form-comment">
													<label for="comment">Today Payment Amount </label> 
												</p>
												<p class="comment-form-comment">
													<label for="comment"><font color="#FFA500" size="6">${todayAmt} &#8377;</font></label> 
												</p>
											</div>
											<div class="col-sm-4">
												<p class="comment-form-comment">
													<label for="comment">Today's Payout Count </label> 
												</p>
												<p class="comment-form-comment" style="margin-left: 15%;">
													<label for="comment"><font color="green" size="6" >${todayCount}</font></label> 
												</p>
											</div>
											<div class="col-sm-4">
												<p class="comment-form-comment">
													<label for="comment">Available Fund</label>
												</p>
												<p class="comment-form-comment">
													<label for="comment"><font color="blue" size="6">${avFund} &#8377;</font></label> 
												</p>
											</div>
											<!-- <div class="col-sm-2" style="margin-top: 25px;">
												<input type="button" value="Search" class="btn btn-success"
													name="Search" onclick="javascript:findstudentlist();">
											</div> -->
										</div>
										</form>
								</div>

							

								


								

								<!-- <div class="container" style="display: none;">
									<button type="button" id="showalloted"
										data-target="#allotedmodel" data-toggle="modal">alloted</button>
								</div> -->
								

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

</body>
</html>