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
						<li><a href="#">Payout</a></li>
						<li class="active">Access Setting</li>
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
							<div class="col-md-3"></div>
							<div class="col-md-9">
								<div class="mu-contact-right">
									<form class="contactform" action="#" method="post">

										<c:choose>
											<c:when test="${msg!=null}">
												<p align='center'
													style="border-style: solid; border-color: green;">
													<font color="green"> ${msg} </font>
												</p>
											</c:when>
											<c:when test="${msgs!=null}">
												<p align='center' 
													style="border-style: solid; border-color: red;margin-left: -29%;">
													<font color="red" > ${msgs} </font>
												</p>
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>
										<label for="email"> </label>
                               <!--     <p class="form-submit">
											<a href="/api/v1/generateApiKey" data-target="#pwdModal"
												data-toggle="modal">
												<button type="button" class="btn btn-info">Generate</button>
											</a>
										</p> -->

										<%-- <p class="comment-form-email">
											<label for="email">API KEY<span class="mandate">*</span></label>
											<input type="text" required="required" aria-required="true"
												value="${api}" disabled="disabled" readonly="readonly">
										</p>
										<p class="comment-form-email">
											<label for="email">Secret Key <span class="mandate">*</span></label>
											<input type="text" required="required" aria-required="true"
												value="${token}" name="" disabled="disabled"
												readonly="readonly">
										</p>
										<p class="comment-form-url">
											<label for="subject">IP<span class="mandate">*</span></label>
											<input type="text" name="ip" id="ip"
												value="${userLoginDetails.whiteListIp }" disabled="disabled"
												readonly="readonly">
										</p>
										<p class="form-submit">
											<a href="/api/v1/generateApiKey">
												<button type="button" class="btn btn-info">Generate</button>
											</a>
										</p> --%>
										
									  <c:choose>
										<c:when test="${msgs==null}">
										 <a href="#" data-target="#pwdModal"
												data-toggle="modal" style="margin-left: 25%;">
												<button type="button" class="btn btn-info">Generate</button></a>
										</c:when>
										<c:otherwise>
										<a href="#"
												style="margin-left: 25%;">
												<button type="button" class="btn btn-info" disabled="disabled">Generate</button></a>
										
										</c:otherwise>
										</c:choose>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end contact content -->
		</div>
	</div>
	</section>
	<!-- End contact  -->
	<!-- lgt box start -->

	<!--modal-->
	<div id="pwdModal" class="modal fade" tabindex="-1" role="dialog"
		aria-hidden="true">
		
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h1 class="text-center">What's My Api Key?</h1>
				</div>
				<div class="modal-body">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="text-center">

									
									
									<div class="panel-body">
										<fieldset>
										<form class="contactform1" action="#" method="post">
										
										<div class="form-group" >
										<label for="email">API KEY<span class="mandate">*</span></label>
												<input class="form-control input-lg"
													placeholder="E-mail Address" name="emailorphone" disabled="disabled"
													id="emailorphone" type="text" value="${api}" >
											</div>
										
										
											<div class="form-group" id="e" >
											<label for="email">Secret Key <span class="mandate">*</span></label>
											<textarea class="form-control input-lg"  disabled="disabled"
											rows="5"> ${token}</textarea>
											</div>
											
											<div class="form-group" id="p">
											<label for="subject">IP<span class="mandate">*</span></label>
												<input class="form-control input-lg"
													placeholder="Conatct Number"  value="${userLoginDetails.whiteListIp }" disabled="disabled"
													>
											</div>
											
											<!-- <div id="btn" >
											<input type="Submit" value="Submit" class="btn btn-success" name="Submit">
											</div>	 -->
											</form>	
										</fieldset>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="col-md-12">
						<button class="btn" data-dismiss="modal" aria-hidden="true"
							id="canbtn">Cancel</button>
					</div>
				</div>
			</div>
		</div>
		
	</div>
	<!--modal-->

	<!-- Start footer -->
	<jsp:include page="footer.jsp" />
	<!-- End footer -->


</body>
</html>