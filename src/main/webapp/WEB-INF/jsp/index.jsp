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
<title>NIDHI CMS | Home</title>

<!-- Favicon -->
<link rel="shortcut icon" href="/assets/img/favicon.ico"	type="image/x-icon">
<script src="/assets/js/jquery.min.js"></script>
<link href="/assets/css/font-awesome.css" rel="stylesheet">
<link href="/assets/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/assets/css/slick.css">
<link rel="stylesheet" href="/assets/css/jquery.fancybox.css"	type="text/css" media="screen" />
<link id="switcher" href="/assets/css/theme-color/default-theme.css" rel="stylesheet">


<link href="/assets/css/style.css" rel="stylesheet">
<script src="/assets/js_dev/profile.js"></script>


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
		<div class="navbar-header" >
			<!-- FOR MOBILE VIEW COLLAPSED BUTTON -->
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar" style="background-color: transparent;border: 0;">
				<b>&#9776;</b>
				<span class="sr-only">Toggle navigation</span> <span
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
				<li class="active"><a href="#">HOME</a></li>
				<!-- <li><a href="aboutus">ABOUT US</a></li> -->
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
	<!-- Start Slider -->
	<section id="mu-slider"> <!-- Start single slider item -->
	<div class="mu-slider-single">
		<div class="mu-slider-img">
			<figure> <img src="/assets/img/slider/2.jpg" alt="img">
			</figure>
		</div>
		<div class="mu-slider-content">
			<h2>Grow Your Business With Nidhi Fintech</h2>
			<span></span>
			<h3>made smarter for your business</h3>
			<h4></h4>
			<!--   <a href="#" class="mu-read-more-btn">Read More</a> -->
		</div>
	</div>
	
	<!-- Start single slider item --> <!-- Start single slider item -->
	<div class="mu-slider-single">
		<div class="mu-slider-img">
			<figure> <img src="/assets/img/slider/3.jpg" alt="img">
			</figure>
		</div>
	</div>
	
	<div class="mu-slider-single">
		<div class="mu-slider-img">
			<figure> <img src="/assets/img/slider/4.jpg" alt="img">
			</figure>
		</div>
	</div>
	<!-- Start single slider item --> </section>
	<!-- End Slider -->
	<!-- Start service  -->
	<section id="mu-service">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-md-12">
				<div class="mu-service-area">
					<!-- Start single service -->
					<div class="mu-service-single">
						<span class="fa fa-book"></span>
						<h3>Business Services</h3>
					</div>
					<!-- Start single service -->
					<!-- Start single service -->
					<div class="mu-service-single">
						<span class="fa fa-users"></span>
						<h3>Finance Business</h3>
					</div>
					<!-- Start single service -->
					<!-- Start single service -->
					<div class="mu-service-single">
						<span class="fa fa-table"></span>
						<h3>CMS Services</h3>
					</div>
					<!-- Start single service -->
				</div>
			</div>
		</div>
	</div>
	</section>
	<!-- End service  -->


	<!-- Start features section -->
	<section id="mu-features">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-md-12">
				<div class="mu-features-area">
					<!-- Start Title -->
					<div class="mu-title">
						<h2>Our Features</h2>
					</div>
					<!-- End Title -->
					<!-- Start features content -->
					<div class="mu-features-content" >
						<div class="row">
							<div class="col-lg-4 col-md-4 col-sm-6">
								<div class="mu-single-feature" >
									<span class="fa fa-inr"></span>
									<h4>Lowest Fees</h4>
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6">
								<div class="mu-single-feature">
									<span class="fa fa-money"></span>
									<h4>Transparency in cost</h4>
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6">
								<div class="mu-single-feature">
									<span class="fa fa-lock"></span>
									<h4>Confidential</h4>
								</div>
							</div>

							<div class="col-lg-4 col-md-4 col-sm-6">
								<div class="mu-single-feature">
									<span class="fa fa-align-justify"></span>
									<h4>Quality Work</h4>
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6">
								<div class="mu-single-feature">
									<span class="fa fa-smile-o"></span>
									<h4>Satisfaction Guaranteed</h4>
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-sm-6">
								<div class="mu-single-feature">
									<span class="fa fa-user-plus"></span>
									<h4>Best CA/CS Consultant</h4>
								</div>
							</div>
						</div>
					</div>
					<!-- End features content -->
				</div>
			</div>
		</div>
	</div>
	</section>
	<!-- End features section -->

	<!-- Start latest course section -->
	
	<!-- End latest course section -->

	<!-- Start our teacher -->

	<!-- End our teacher -->


	<div class="icon-bar f_right" style="align: right">
		<a href="#" class="facebook"><i class="fa fa-facebook"></i></a> 
		<a	href="#" class="google"><i class="fa fa-google"></i></a> 
			<a href="#"	class="linkedin"><i class="fa fa-linkedin"></i></a> 
			 <a href="#" class="youtube"><i class="fa fa-youtube"></i></a> 
			 <a href="#" class="instagram"><i class="fa fa-instagram"></i></a>
	</div>


	<!-- Start testimonial -->
	<section id="mu-testimonial">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="mu-testimonial-area">
					<div id="mu-testimonial-slide" class="mu-testimonial-content">
						<!-- start testimonial single item -->
						<div class="mu-testimonial-item">
							<div class="mu-testimonial-quote">
								<blockquote>
									<p>
										  Really nice service and co-operative staff. 
									 Nidhi Fin Tech doing very good and hia staff is 
									   really nice.. keep it up sir..
									</p>
								</blockquote>
							</div>
							<div class="mu-testimonial-img">
								<img src="/assets/img/customer/customer.png" alt="img"
									height="100" width=100>
							</div>
							<div class="mu-testimonial-info">
								<h4>Deepak Gupta</h4>
								<span>Happy Customer</span>
							</div>
						</div>
						<!-- end testimonial single item -->
						<!-- start testimonial single item -->
						<div class="mu-testimonial-item">
							<div class="mu-testimonial-quote">
								<blockquote>
									<p> Good communication and friendly accessable Work provided by the Nidhi Fin Tech </p>
								</blockquote>
							</div>
							<div class="mu-testimonial-img">
								<img src="/assets/img/customer/customer.png" alt="img"
									height="100" width=100>
							</div>
							<div class="mu-testimonial-info">
								<h4>Anil Singh</h4>
								<span>Happy Customer</span>
							</div>
						</div>
						<!-- end testimonial single item -->
					</div>
				</div>
			</div>
		</div>
	</div>
	</section>
	<!-- End testimonial -->

	<!-- Start from blog -->

	<!-- End from blog -->

	<!-- Start footer -->
	<jsp:include page="footer.jsp" />
	<!-- End footer -->

	<!-- jQuery library -->
	<!-- <script src="/assets/js/jquery.min.js"></script> -->
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