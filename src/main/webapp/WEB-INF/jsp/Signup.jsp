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
<title>NIDHI CMS | Sign Up</title>
<!-- Favicon -->
<link rel="shortcut icon" href="/assets/img/favicon.ico" type="image/x-icon">
<script src="/assets/js/jquery.min.js"></script>
<link href="/assets/css/font-awesome.css" rel="stylesheet">
<link href="/assets/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/assets/css/slick.css">
<link rel="stylesheet" href="/assets/css/jquery.fancybox.css" type="text/css" media="screen" />
<link id="switcher" href="/assets/css/theme-color/default-theme.css" rel="stylesheet">
<link href="/assets/css/style.css" rel="stylesheet">
<script src="/assets/js_dev/profile.js"></script>
<script type="text/javascript" src="/assets/js_dev/generic.js"></script>


</head>
<body>

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
				aria-controls="navbar"
				style="background-color: transparent; border: 0;">
				<b>&#9776;</b> <span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<!-- LOGO -->
			<!-- TEXT BASED LOGO -->
			<!--  <a class="navbar-brand" href="index.html"><i class="fa fa-university"></i><span>ss</span></a> -->
			<!-- IMG BASED LOGO  -->
			<a class="navbar-brand" href="index"><img
				src="/assets/img/logo.png"
				style="height: 80px; width: 300px; margin-top: -15px;" alt="logo"></a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul id="top-menu" class="nav navbar-nav navbar-right main-nav">
				<li class="active"><a href="#">HOME</a></li>
			<!-- 	<li><a href="aboutus">ABOUT US</a></li> -->
				<li><a href="contactus">CONTACT US</a></li>
				<li><a href="/api/v1/fe/login">LOGIN</a></li>
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
					<h2>Signup</h2>
					<ol class="breadcrumb">
						<li><a href="#">Home</a></li>
						<li class="active">signup</li>
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
							<div class="col-md-6">
								<div class="mu-contact-left" style="border: 1px solid;">
										
											<img src="/assets/img/blog/sideimg.jpg" alt="" height="50%" 	width="100%">
									
								</div>
							</div>
							<div class="col-md-6">
								<div class="mu-contact-right">
									<form class="contactform" action="/api/v1/user" method="post">


										<c:choose>
											<c:when test="${msg!=null}">
												<p align='center'
													style="border-style: solid; border-color: red;">
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

										<p class="comment-form-author">
											<label for="author">Full Name <span class="mandate">*</span></label>
											<input type="text" required="required" size="30" value=""
												name="fullName" id="fullName" maxlength="50">
										</p>
										<p class="comment-form-email">
											<label for="email">Email <span class="mandate">*</span></label>
											<input type="email" required="required" aria-required="true"
												value="" name="userEmail" id="userEmail" maxlength="250">
										</p>
										
										<p class="comment-form-comment">
											<label for="comment">Contact Number<span
												class="mandate">*</span></label> <input type="text"
												required="required" aria-required="true" value="" minlength="10"
												name="mobileNumber" id="mobileNumber" maxlength="10"
												onkeypress="javascript: return onlyNumbers(event);">
										</p>
										<p class="comment-form-url">
											<label for="subject">Password<span class="mandate">*</span></label>
											<input type="password" name="password" id="password" minlentg="3"
												aria-required="true" required="required" 
												pattern="^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d][A-Za-z\d!@#$%^&*()_+]{7,19}$" 
												title="Must contain at least one number and one uppercase and lowercase letter,one special  
												and at least 8 or more characters">
										</p>
										<p class="comment-form-url">
											<label for="subject">Referral Code</label>
											<input type="text" id="referralCode" name="referralCode" equired="required"
												maxlength="30">
										</p>
										<p class="form-submit">
											<input type="submit" value="Signup" class="btn btn-success"
												name="signup"> <input type="reset" value="Reset"
												class="btn btn-warning" name="reset"> <input
												type="button" value="Cancel" class="btn btn-info"
												name="cancel">
										</p>
									</form>

								</div>
							</div>
						</div>
					</div>
					<!-- end contact content -->
				</div>
			</div>
		</div>
	</div>
	<%
		session.removeAttribute("student");
	%> </section>
	<!-- End contact  -->


	<!-- Start footer -->
	<jsp:include page="footer.jsp" />
	<!-- End footer -->






	<!-- jQuery library -->
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