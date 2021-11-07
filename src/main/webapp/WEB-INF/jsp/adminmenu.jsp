<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<link rel="shortcut icon" href="/assets/img/favicon.ico" type="image/x-icon">
<!-- 
 <script src="/assets/js/jquery.min.js"></script>  -->
<link href="/assets/css/font-awesome.css" rel="stylesheet">
<link href="/assets/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/assets/css/slick.css">
<link rel="stylesheet" href="/assets/css/jquery.fancybox.css" type="text/css" media="screen" />
<link id="switcher" href="/assets/css/theme-color/default-theme.css" rel="stylesheet">


<link href="/assets/css/style.css" rel="stylesheet">
<script src="/assets/js_dev/profile.js"></script>




 <link rel="stylesheet" type="text/css" href="/assets/css/dev1.css">
    <link href='https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css' rel='stylesheet'>
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     

  
  
</head>
<body>
 <div class="sidebar close">
  <!--   <div class="logo-details">
      <i class='bx bxl-c-plus-plus'></i>
      <span class="logo_name">CodingLab</span>
    </div> -->
    <ul class="nav-links">
    <!--   <li>
        <a href="#">
          <i class='bx bx-grid-alt' ></i>
          <span class="link_name">Dashboard</span>
        </a>
        <ul class="sub-menu blank">
          <li><a class="link_name" href="#">Category</a></li>
        </ul>
      </li> -->
      <li>
        <div class="iocn-link">
          <a href="#">
            <i class='bx bx-grid-alt' ></i>
            <span class="link_name"> Onboarding </span>
          </a>
          <i class='bx bxs-chevron-down arrow' ></i>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="#">Onboarding </a></li>
          <li><a href="/api/v1/fe/AdminCreateNew">Create New</a></li>
          <li><a href="/api/v1/get-all-user">Pending client</a></li>
          <li><a href="/api/v1/fe/AdminmanageClint">Manage Client</a></li>
        </ul>
      </li>
     <!--  <li>
        <div class="iocn-link">
          <a href="#">
            <i class='bx bx-book-alt' ></i>
            <span class="link_name">Posts</span>
          </a>
          <i class='bx bxs-chevron-down arrow' ></i>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="#">Posts</a></li>
          <li><a href="#">Web Design</a></li>
          <li><a href="#">Login Form</a></li>
          <li><a href="#">Card Design</a></li>
        </ul>
      </li> -->
      <li>
        <a href="#">
          <i class='bx bx-pie-chart-alt-2' ></i>
          <span class="link_name">Product Featuring</span>
        </a>
        <ul class="sub-menu blank">
          <li><a class="link_name" href="/api/v1/fe/AdminProductFeaturing">Product Featuring</a></li>
        </ul>
      </li>
<!--       <li>
        <a href="#">
          <i class='bx bx-line-chart' ></i>
          <span class="link_name">Chart</span>
        </a>
        <ul class="sub-menu blank">
          <li><a class="link_name" href="#">Chart</a></li>
        </ul>
      </li> -->
     <!--  <li>
        <div class="iocn-link">
          <a href="#">
            <i class='bx bx-plug' ></i>
            <span class="link_name">Plugins</span>
          </a>
          <i class='bx bxs-chevron-down arrow' ></i>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="#">Plugins</a></li>
          <li><a href="#">UI Face</a></li>
          <li><a href="#">Pigments</a></li>
          <li><a href="#">Box Icons</a></li>
        </ul>
      </li> -->
  <!--     <li>
        <a href="#">
          <i class='bx bx-compass' ></i>
          <span class="link_name"></span>
        </a>
        <ul class="sub-menu">
           <li><a class="link_name" href="/api/v1/fe/SubAdminAccount">Create</a></li>
          <li><a href="#">Update</a></li>
        </ul>
      </li> -->
      <li>
       <div class="iocn-link">
          <a href="#">
            <i class='bx bx-compass' ></i>
            <span class="link_name"> SubAdmin</span>
          </a>
          <i class='bx bxs-chevron-down arrow' ></i>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="#"> SubAdmin </a></li>
          <li><a href="/api/v1/fe/SubAdminAccount">Create</a></li>
          <li><a href="/api/v1/fe/SubAdminAccountUpdate">Update</a></li>
        </ul>
      </li>
        <li>
        <div class="iocn-link">
          <a href="#">
            <i class='bx bx-book-alt' ></i>
            <span class="link_name">Report</span>
          </a>
          <i class='bx bxs-chevron-down arrow' ></i>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="#">Report</a></li>
          <li><a href="/api/v1/fe/AdminTransactionReport">Transaction report</a></li>
          <li><a href="/api/v1/fe/AdminBankACverification">Bank A/c Verification</a></li>
          <li><a href="/api/v1/fe/AdminBillingReport">Billing / Charges Report</a></li>
          <li><a href="/api/v1/fe/AdminTransactionInqReport">Transaction inquiry</a></li>
          <li><a href="/api/v1/fe/AdminACStatement">Account Statement</a></li>
        </ul>
      </li>
      
 <!--      <li>
        <a href="#">
          <i class='bx bx-history'></i>
          <span class="link_name">History</span>
        </a>
        <ul class="sub-menu blank">
          <li><a class="link_name" href="#">History</a></li>
        </ul>
      </li> -->
      
      
        <li>
        <div class="iocn-link">
          <a href="#">
            <i class='bx bx-cog' ></i>
            <span class="link_name">Setting</span>
          </a>
          <i class='bx bxs-chevron-down arrow' ></i>
        </div>
        <ul class="sub-menu">
           <li><a class="link_name" href="/api/v1/get-loggedin-info?userUuid=${userLoginDetails.userUuid}&type=a">Setting</a></li>
          <li><a href="/api/v1/fe/AdminWhiteListpage">White Listing</a></li>
           <li><a href="/api/v1/get-privilegeList?userUuid=${userLoginDetails.userUuid}">Add Privilege</a></li>
          
        </ul>
      </li>
      
      <li>
  </li>
</ul>
  </div>
  <section class="home-section">
    <div class="home-content">
      <i class='bx bx-menu' ></i>
    </div>
  </section>
  
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
				style="height: 80px; width: 300px; margin-top: 0px;" alt="logo"></a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
				<ul id="top-menu" class="nav navbar-nav navbar-right main-nav">
				<li><a href="#">Hi <b>${userLoginDetails.fullName}</b></a></li>
				
					
				<li class="dropdown active"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Setting &#9881; <spanclass="fa fa-angle-down"></span></a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="logout">Logout </a></li>
					</ul></li>
					
					
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	</nav> </section>
  
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
  <script src="/assets/js/dev.js"></script>
</body>
</html>