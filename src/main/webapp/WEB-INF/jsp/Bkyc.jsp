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
<title>NIDHI CMS | KYC</title>

<!-- Favicon -->
<link rel="shortcut icon" href="/assets/img/favicon.ico"
	type="image/x-icon">
<script src="/assets/js/jquery.min.js"></script>
<link href="/assets/css/font-awesome.css" rel="stylesheet">
<link href="/assets/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/assets/css/slick.css">
<link rel="stylesheet" href="/assets/css/jquery.fancybox.css" type="text/css" media="screen" />
<link id="switcher" href="/assets/css/theme-color/default-theme.css" rel="stylesheet">


<link href="/assets/css/style.css" rel="stylesheet">
<script src="/assets/js_dev/profile.js"></script>



</head>
<c:if test="${sessionScope.authtoken eq null}">
	<c:redirect url="/api/v1/fe/login"></c:redirect>
</c:if> 
<body>
<!-- //https://bbbootstrap.com/snippets/bootstrap-5-sidebar-menu-toggle-button-34132202 -->
	<!--START SCROLL TOP BUTTON -->
	<a class="scrollToTop" href="#"> <i class="fa fa-angle-up"></i>
	</a>
	<!-- END SCROLL TOP BUTTON -->

	<!-- Start header  -->
	<header id="mu-header">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-md-12">
				<div class="mu-header-area">
					<div class="row">
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
							<div class="mu-header-top-left">
								<div class="mu-top-email">
									<i class="fa fa-envelope"></i> <span>cs@nidhicms.com</span>
								</div>
								<div class="mu-top-phone">
									<i class="fa fa-phone"></i> <span>+916377201669</span>
								</div>
							</div>
						</div>
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
							<div class="mu-header-top-right">
								<nav>
								<ul class="mu-top-social-nav">
									<li><a href="#"><span class="fa fa-facebook"></span></a></li>
									<li><a href="#"><span class="fa fa-google-plus"></span></a></li>
									<li><a href="#"><span class="fa fa-youtube"></span></a></li>
									<li><a href="#"><span class="fa fa-instagram"></span></a></li>
								</ul>
								</nav>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</header>
	<!-- End header  -->
	<!-- Start menu -->
	<section id="mu-menu"> <nav class="navbar navbar-default"
		role="navigation">
	<div class="container">
		<div class="navbar-header">
			<!-- FOR MOBILE VIEW COLLAPSED BUTTON -->
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar"style="background-color: transparent;border: 0;">
				&#9776;
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<!-- LOGO -->
			<!-- TEXT BASED LOGO -->
			<!--  <a class="navbar-brand" href="index.html"><i class="fa fa-university"></i><span>ss</span></a> -->
			<!-- IMG BASED LOGO  -->
			<a class="navbar-brand" href="/api/v1/fe/index"><img
				src="/assets/img/logo.png"
				style="height: 150px; width: 300px; margin-top: -15px;" alt="logo"></a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul id="top-menu" class="nav navbar-nav navbar-right main-nav">
				<!-- <li ><a href="index">My Profile</a></li> -->
			     <li><a href="/api/v1/fe/Dashboard">Dashboard </a></li>				
				<li class="dropdown active"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Setting &#9881; <span
						class="fa fa-angle-down"></span></a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="ChangePassword?sid=${st.sid}">Change
								Password &#128273;</a></li>
						<li><a href="logout">Logout </a></li>
					</ul></li>
				<!-- <li class="active"><a href="Signup">SIGNUP</a></li> -->
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	</nav> </section>
	<!-- End menu -->
	<!-- Start search box -->
	<div id="mu-search">
		<div class="mu-search-area">
			<button class="mu-search-close">
				<span class="fa fa-close"></span>
			</button>
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<form class="mu-search-form">
							<input type="search"
								placeholder="Type Your Keyword(s) & Hit Enter">
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- End search box -->
	<!-- Page breadcrumb -->
	<section id="mu-page-breadcrumb">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="mu-page-breadcrumb-area">
					<h2>KYC</h2>
					<ol class="breadcrumb">
						<li><a href="/api/v1/fe/Dashboard">Dashboard</a></li>
						<li class="active">KYC</li>
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
								<div class="mu-contact-left">

									<div class="panel-body bio-graph-info">

										<div class="panel panel-primary">
											<div class="panel-heading" style="background-color: #60b51a;"
												align="center">
												<strong> <font	style="color: white; cursor: pointer;">Lets Get Started</font>
												</strong>
											</div>
										</div>
										<div style="margin-top: -20px;">
											<div class="panel-heading"
												style="background-color: #002066; margin: 1px 0 1px 0px; border-radius: 0px;">
												<strong> <a href="/api/v1/fe/Pkyc"> <font	style="color: white; cursor: pointer;">1. Personal KYC</font>
                                                 
												</a>
												</strong>
											</div>
										</div>
										<div class="panel-heading"
											style="background-color: #002066; margin: 1px 0 1px 0px; border-radius: 0px;">
											<strong> <a href="tobeOpr?flag=true"> <font
													style="color: white; cursor: pointer;">2. Business
														KYC </font> <img src="/assets/img/rightbluearrow.png"
													align="right"
													style="width: 7px; height: 13px; margin-right: 15px; margin-top: 6px;"
													alt="">
											</a>
											</strong>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-9">
								<div class="mu-contact-right">
									<form class="contactform" action="/api/v1/bkycupload"  method="post" enctype = "multipart/form-data">
									
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
										
										 <div class="col-lg-12">
										<strong> <font
													style="color: Blue; cursor: pointer;">Business Details</font>
												</strong>
												</div>
										
                                   <div class="col-lg-12">
                                   <div class="col-lg-6">
								        <p class="comment-form-author">
											<label for="author">Business Entity Type<span class="mandate">*</span></label>
											<input type="text" required="required" size="30" value="${bkyc.entityType }"
												name="entityType" id="entityType" >
										</p>
										</div>
										 <div class="col-lg-6">
										<p class="comment-form-author">
											<label for="author">Industry<span class="mandate">*</span></label>
											<input type="text" required="required"  value="${bkyc.industry }"
												name="industry" id="industry" >
										</p>
										</div>
										</div>
										
										<div class="col-lg-12">
                                   <div class="col-lg-6">
										<p class="comment-form-author">
											<label for="author">register Company Name<span class="mandate">*</span></label>
											<input type="text" required="required" size="30" value="${bkyc.compnayName }"
												name="compnayName" id="compnayName" >
										</p>
										</div>
                                   <div class="col-lg-6">
										<p class="comment-form-author">
											<label for="author">No.of Employees<span class="mandate">*</span></label>
											<input type="text" required="required" size="30" value="${bkyc.noOfEmp }"
												name="noOfEmp" id="noOfEmp" >
										</p>
										</div>
										</div>
										
										<div class="col-lg-12">
                                   <div class="col-lg-6">
										<p class="comment-form-author">
											<label for="author">Individual Pan<span class="mandate">*</span></label>
											<input type="text" required="required" size="30" value="${bkyc.individualPan }"
												name="individualPan" id="individualPan" >
										</p>
										</div>
										<div class="col-lg-6">
										<p class="comment-form-author">
											<label for="author">GST Number<span class="mandate">*</span></label>
											<input type="text" required="required" size="30" value="${bkyc.gstNo }"
												name="gstNo" id="gstNo" >
										</p>
										</div>
										</div>
										<div class="col-lg-12">
                                   <div class="col-lg-6">
										<p class="comment-form-author">
											<label for="author">Business Website Link<span class="mandate">*</span></label>
											<input type="text" required="required" size="30" value="${bkyc.websiteLink }"
												name="websiteLink" id="websiteLink" >
										</p>
										</div>
										<div class="col-lg-6">
										</div>
										</div>
										
										 <div class="col-lg-12">
										<strong> <font
													style="color: Blue; cursor: pointer;">Business Documents</font>
												</strong>
												</div>
										
										<div class="col-lg-12">
                                       <div class="col-lg-6">
										 <p class="comment-form-author">
											<label for="author">GST Certificate<span class="mandate">*</span></label>
											<input type="file" required="required" size="30" value=""
												name="fileUpload" id="fileUpload" >
										</p>
										<c:if test="${userDocx.docType eq 'DOCUMENT_GST'}">
										<p><font color="blue;">${userDocx.fileName }</font> <font color="green;">Already uploaded</font></p>
										</c:if>
										</div>
										 <div class="col-lg-6">
										 </div>
										 </div>
										 
										 
										 
										 
										 
										  <div class="col-lg-12">
										<strong> <font
													style="color: Blue; cursor: pointer;">Registered Address</font>
												</strong>
												</div>
										<div class="col-lg-12">
										 <p class="comment-form-author">
											<label for="author">Address Line 1<span class="mandate">*</span></label>
											<input type="text" required="required" size="30" value="${bkyc.address1 }"
												name="address1" id="address1" >
										</p>
										</div>
										
										<div class="col-lg-12">
										 <p class="comment-form-author">
											<label for="author">Address Line 2<span class="mandate">*</span></label>
											<input type="text" required="required" size="30" value="${bkyc.address2 }"
												name="address2" id="address2" >
										</p>
										</div>
										
										<div class="col-lg-12">
                                       <div class="col-lg-6">
										 <p class="comment-form-author">
											<label for="author">Pincode<span class="mandate">*</span></label>
											<input type="text" required="required" size="30" value="${bkyc.pincode }"
												name="pincode" id="pincode" >
										</p>
										</div>
										 <div class="col-lg-6">
										  <p class="comment-form-author">
											<label for="author">State<span class="mandate">*</span></label>
											<input type="text" required="required" size="30" value="${bkyc.state }"
												name="state" id="state" >
										</p>
										 </div>
										 </div>
										 
										 <div class="col-lg-12">
                                       <div class="col-lg-6">
										 <p class="comment-form-author">
											<label for="author">City<span class="mandate">*</span></label>
											<input type="text" required="required" size="30" value="${bkyc.city }"
												name="city" id="city" >
										</p>
										</div>
										 <div class="col-lg-6">
										 </div>
										 </div>
										 
										 
										
										<p class="form-submit">
											<input type="submit" value="Submit" class="btn btn-success"
												name="Submit"> <input
												
												type="button" value="Cancel" class="btn btn-info"
												name="cancel">
										</p>
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