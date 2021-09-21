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
<title>NIDHI CMS | Contact Us</title>
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



<!-- Google Fonts -->
<link href='https://fonts.googleapis.com/css?family=Montserrat:400,700'
	rel='stylesheet' type='text/css'>
<link
	href='https://fonts.googleapis.com/css?family=Roboto:400,400italic,300,300italic,500,700'
	rel='stylesheet' type='text/css'>


</head>
<body>

	<!--START SCROLL TOP BUTTON -->
	<a class="scrollToTop" href="#"> <i class="fa fa-angle-up"></i>
	</a>
	<!-- END SCROLL TOP BUTTON -->

	<!-- Start header  -->
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
				style="height: 150px; width: 300px; margin-top: -15px;" alt="logo"></a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul id="top-menu" class="nav navbar-nav navbar-right main-nav">
				<li ><a href="/api/v1/fe/index">HOME</a></li>
				<!-- <li ><a href="aboutus">ABOUT US</a></li> -->
				<li class="active"><a href="#">CONTACT US</a></li>
				<li><a href="/api/v1/fe/login">LOGIN</a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	</nav>
	
	 </section>
	
	
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
					<h2>Contact</h2>
					<ol class="breadcrumb">
						<li><a href="#">Home</a></li>
						<li class="active">Contact</li>
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
					<div class="mu-title">
						<h2>Get in Touch</h2>
						</div>
					<!-- end title -->
					<!-- start contact content -->
					<div class="mu-contact-content">
						<div class="row">
							<div class="col-md-6">
								<div class="mu-contact-left">
									<form class="contactform" action="getintouch" method="post">
									<c:choose>
    				<c:when test="${message!=null}">
    				<p align='center' style="border-style: solid; border-color: green;" >  <font color="green"> 
    				  ${message} </font></p>
    				</c:when>
   				     <c:otherwise>
   				   </c:otherwise>
					</c:choose> 
										<p class="comment-form-author">
											<label for="author">Name <span class="required">*</span></label>
											<input type="text" required="required" size="30" value=""
												name="name" id="email">
										</p>
										<p class="comment-form-email">
											<label for="email">Email <span class="required">*</span></label>
											<input type="email" required="required" aria-required="true"
												value="" name="email" id="email">
										</p>
										<p class="comment-form-author">
											<label for="author">Contact <span class="required">*</span></label>
											<input type="text" required="required" maxlength="15" value=""
												name="contact" id="contact">
										</p>
										<p class="comment-form-url">
											<label for="subject">Subject<span class="required">*</span></label>
											 <input type="text"
												name="subject" id="subject" required="required" >
										</p>
										<p class="comment-form-comment">
											<label for="comment">Message</label>
											<textarea required="required" aria-required="true" rows="8"
												cols="45" name="message" id="message"></textarea>
										</p>
										<p class="form-submit">
											<input type="submit" value="Send Message" class="mu-post-btn"
												name="submit">
										</p>
									</form>
								</div>
							</div>
							<div class="col-md-6">
								<div class="mu-contact-right">
								<p>Email: cs@nidhicms.com</p><br>
<p>Phone: +916377201669</p><br>
<p>Address: Opp. SBI Bank Near Anaj Mandi, Bansur Alwar Rajasthan 301402</p><br>
						<!-- <iframe src="https://www.google.com/maps?q=S.S.%20Coaching%20Centre%2C%20Block%20VB%2C%20Virender%20Nagar%2C%20Janakpuri%2C%20Delhi%2C%20110058&z=14&t=&ie=UTF8&output=embed" width="600" height="450"
						width="100%" height="450" frameborder="0" style="border: 0" allowfullscreen></iframe>
 -->										</div>
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


	<!-- Start footer -->
<jsp:include page="footer.jsp" />
	<!-- End footer -->






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