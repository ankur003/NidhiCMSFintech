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
<title>SS Coaching Centre | About Us</title>

<!-- Favicon -->
<link rel="shortcut icon" href="assets/img/favicon.ico"
	type="image/x-icon">

<!-- Font awesome -->
<link href="assets/css/font-awesome.css" rel="stylesheet">
<!-- Bootstrap -->
<link href="assets/css/bootstrap.css" rel="stylesheet">
<!-- Slick slider -->
<link rel="stylesheet" type="text/css" href="assets/css/slick.css">
<!-- Fancybox slider -->
<link rel="stylesheet" href="assets/css/jquery.fancybox.css"
	type="text/css" media="screen" />
<!-- Theme color -->
<link id="switcher" href="assets/css/theme-color/default-theme.css"
	rel="stylesheet">

<!-- Main style sheet -->
<link href="assets/css/style.css" rel="stylesheet">


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
	<header id="mu-header">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-md-12">
				<div class="mu-header-area">
					<div class="row">
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
							<div class="mu-header-top-left">
							<div class="mu-top-email">
									<i class="fa fa-envelope"></i> <span>info@sscoachingcentre.com</span>
								</div>
								<div class="mu-top-phone">
									<i class="fa fa-phone"></i> <span>+91-9810196745</span>
								</div>
							</div>
						</div>
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
							<div class="mu-header-top-right">
								<nav>
								<ul class="mu-top-social-nav">
								<li><a href="https://www.facebook.com/ssjanakpuri/"><span class="fa fa-facebook"></span></a></li>
									<li><a href="#"><span class="fa fa-google-plus"></span></a></li>
									<li><a href="https://youtube.com/user/sscoachingcentre"><span class="fa fa-youtube"></span></a></li>
									<li><a href="https://instagram.com/centresscoaching?igshid=fynw2icu0uwc"><span class="fa fa-instagram"></span></a></li>
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
				aria-controls="navbar" style="background-color: transparent;border: 0;">
				&#9776;
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<!-- LOGO -->
			<!-- TEXT BASED LOGO -->
			<!--  <a class="navbar-brand" href="index.html"><i class="fa fa-university"></i><span>ss</span></a> -->
			<!-- IMG BASED LOGO  -->
		<a class="navbar-brand" href="index"><img
				src="assets/img/logo.png"
				style="height: 150px; width: 140px; margin-top: -19px;" alt="logo"></a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul id="top-menu" class="nav navbar-nav navbar-right main-nav">
				<li ><a href="index">HOME</a></li>
				<li><a href="courses">COURSES</a></li>
				<li><a href="teachers">TEACHERS</a></li>
				<li class="active"><a href="aboutus">ABOUT US</a></li>
				<li ><a href="contactus">CONTACT US</a></li>
				<li><a href="login">LOGIN</a></li>
				<!-- <li><a href="Signup">SIGNUP</a></li> -->
				<!-- <li><a href="404.html">404 Page</a></li>               
            <li><a href="#" id="mu-search-icon"><i class="fa fa-search"></i></a></li> -->
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
					<h2>About Us</h2>
					<ol class="breadcrumb">
						<li><a href="#">Home</a></li>
						<li class="active">About Us</li>
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
						<h2>Who we Are?</h2>
						</div>
					<!-- end title -->
					<!-- start contact content -->
					<div class="mu-contact-content">
						<div class="row">
							<div class="col-md-8">
									
									<form class="contactform">
									<p style="text-align: justify;">At a S.S. Coaching Centre, the child benefits from a curriculum that has been synchronized with the C.B.S.E. Board System and is updated to include the latest continuous and comprehensive evaluation. From enrollment to exams and student personality development, our students are mentored by teachers with a proven track record, who have been hired by our educational board after an exhaustive screening and selection process.</p>
									<p style="text-align: justify;">SCCC prides itself in having best teachers and faculty of the highest caliber, as we believe they form the backbone of our learning center.Our centers are equipped with best infrastructure with clean and well lit classrooms running on optimum batch size to ensure attention to the needs of each child. Within the classroom, learning is simplified by Technology-Aided, Teaching as well as having study material and study notes of CBSE syllabus in an easy method collated by teachers who have demonstrated years of subject expertise.<b>Our best coaching classes</b> involves a perfect mix of Classroom lectures and Problem-solving concept of <b style="color: #23A38F">Accounts, Economics and business studies for commerce stream students with exhaustive study material and assignments.</b></p>
									<p style="text-align: justify;">Many trust on the saga of amazing results it has produced over the decades. SSCC has emerged as a brand that has provided a competitive, transparent, disciplined, and result oriented environment to the students at large. Our success story is directly associated with the success of thousands of students who have only cleared the most exams but also grabbed the top ranks.</p>
									<p>We have a team of <b style="color: #23A38F">best and highly qualified teachers for Accounts, Economics and business studies who gives the classes</b> for XI & XII Commerce stream, B. Com (P) 1st to 3rd years, B. Com (H) 1st to 3rd years student.</p>
	
									</form>
							</div>
							<div class="col-md-4">
								<div class="mu-contact-right">
								 <img src="assets/img/logo.jpeg" height="100%" width="100%"	alt="img" > 
								
								</div>
								</div>
						</div>
						
						
						
						<section id="mu-our-teacher">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="mu-our-teacher-area">
					<!-- begain title -->
					<div class="mu-title">
						<h2>Reviews</h2>
					</div>
					<!-- end title -->
					<!-- begain our teacher content -->
					<div class="mu-our-teacher-content">
						<div class="row">
							<div class="col-lg-3 col-md-3  col-sm-6">
								<div class="mu-our-teacher-single">
									<figure class="mu-our-teacher-img">
										<div class="embed-responsive embed-responsive-16by9">
											<video controls> <source src="assets/img/students/review1.mp4"
												type="video/mp4"> Sorry, your browser doesn't
											support embedded videos. </video>

										</div>
									 </figure>
								
								</div>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-6">
								<div class="mu-our-teacher-single">
									<figure class="mu-our-teacher-img"> <div class="embed-responsive embed-responsive-16by9">
											<video controls> <source src="assets/img/students/review2.mp4"
												type="video/mp4"> Sorry, your browser doesn't
											support embedded videos. </video>
										</div>
										 </figure>
								</div>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-6">
								<div class="mu-our-teacher-single">
									<figure class="mu-our-teacher-img"> 
									<div class="embed-responsive embed-responsive-16by9">
											<video controls> <source src="assets/img/students/review3.mp4"
												type="video/mp4"> Sorry, your browser doesn't
											support embedded videos. </video>

										</div>
									</figure>
									
								</div>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-6">
								<div class="mu-our-teacher-single">
									<figure class="mu-our-teacher-img"><div class="embed-responsive embed-responsive-16by9">
											<video controls> <source src="assets/img/students/review4.mp4"
												type="video/mp4"> Sorry, your browser doesn't
											support embedded videos. </video>

										</div>
									</figure>
									
								</div>
							</div>
						</div>
					</div>
					<!-- End our teacher content -->
				</div>
			</div>
		</div>
	</div>
	</section>
	<!-- End our teacher -->
						
						
						
						
						
						
						
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
	<script src="assets/js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="assets/js/bootstrap.js"></script>
	<!-- Slick slider -->
	<script type="text/javascript" src="assets/js/slick.js"></script>
	<!-- Counter -->
	<script type="text/javascript" src="assets/js/waypoints.js"></script>
	<script type="text/javascript" src="assets/js/jquery.counterup.js"></script>
	<!-- Mixit slider -->
	<script type="text/javascript" src="assets/js/jquery.mixitup.js"></script>
	<!-- Add fancyBox -->
	<script type="text/javascript" src="assets/js/jquery.fancybox.pack.js"></script>

	<!-- Custom js -->
	<script src="assets/js/custom.js"></script>

</body>
</html>