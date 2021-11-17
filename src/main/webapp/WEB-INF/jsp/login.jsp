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
<title>NIDHI CMS | Login</title>

<!-- Favicon -->
<link rel="shortcut icon" href="/assets/img/favicon.ico"
	type="image/x-icon">
<script src="/assets/js/jquery.min.js"></script>
<link href="/assets/css/font-awesome.css" rel="stylesheet">
<link href="/assets/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/assets/css/slick.css">
<link rel="stylesheet" href="/assets/css/jquery.fancybox.css"
	type="text/css" media="screen" />
<link id="switcher" href="/assets/css/theme-color/default-theme.css"
	rel="stylesheet">

<script type="text/javascript" src="/assets/js_dev/generic.js"></script>

<link href="/assets/css/style.css" rel="stylesheet">
<script src="/assets/js_dev/profile.js"></script>

<script type="text/javascript">

function yesnoCheck() { 
    if (document.getElementById('Email').checked) {
        document.getElementById('e').style.display = '';
        document.getElementById('emailorphone').value = "EMAIL";
        document.getElementById('p').style.display = 'none';
        document.getElementById('btn').style.display = '';
    } else {
        document.getElementById('e').style.display = 'none';
        document.getElementById('p').style.display = '';
        document.getElementById('emailorphone').value = "PHONE";
        document.getElementById('btn').style.display = '';
    }

}

function passwordmatch()
{
	var valid=true;
	var npassword=document.getElementById('npassword').value;
	var cpassword=document.getElementById('cpassword').value;
	
	if(npassword!=cpassword)
		{
		document.getElementById("password_error").style.display = "block";
		document.getElementById("password_error").innerHTML = "password and confirm password not match";
		valid=false;
		}
	return valid;
}


</script>

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
			<a class="navbar-brand" href="/api/v1/fe/index"><img
				src="/assets/img/logo.png"
				style="height: 80px; width: 300px; margin-top: 0px;" alt="logo"></a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul id="top-menu" class="nav navbar-nav navbar-right main-nav">
				<li><a href="/api/v1/fe/index">HOME</a></li>
				<!-- <li><a href="aboutus">ABOUT US</a></li> -->
				<li><a href="contactus">CONTACT US</a></li>
				<li class="active"><a href="#">LOGIN</a></li>
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
					<h2>Login</h2>
					<ol class="breadcrumb">
						<li><a href="#">Home</a></li>
						<li class="active">Login</li>
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
						<h2>Login</h2>
						</div> -->
					<!-- end title -->
					<!-- start contact content -->
					<div class="mu-contact-content" style="margin-top: -2%">
						<div class="row">
							<div class="col-md-3">
								<div class="mu-contact-left">

									<!-- <img src="assets/img/login1.png" height="100%" width="100%"	alt="img" > -->

								</div>
							</div>
							
							<c:if test="${otp eq null }">
							
							<div class="col-md-6">
								<div class="mu-contact-right">
									<form class="contactform" action="/api/v1/login" method="post">



										<c:choose>
											<c:when test="${msg!=null}">
												<p align='center'
													style="border-style: solid; border-color: green;">
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
										<label id="fpass" style="display: none; color: red;"></label>
										<p class="comment-form-email">
											<label for="email">Email/Contact Number <span
												class="mandate">*</span></label> <input type="text"
												required="required" aria-required="true" value=""
												name="username" id="username" maxlength="250">
										</p>
										<p class="comment-form-url">
											<label for="subject">Password<span class="mandate">*</span></label>
											<input type="password" name="password" aria-required="true"
												pattern="^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d][A-Za-z\d!@#$%^&*()_+]{7,19}$" 
												title="Must contain at least one number and one uppercase and lowercase letter,one special  
												and at least 8 or more characters"> <a href="#" data-target="#pwdModal"
												data-toggle="modal" style="margin-left: 75%;">Forgot
												Password?
												</button>
											</a>

										</p>

                                      <input type="hidden" name="otpflag" value="no">

										<p class="form-submit">
											<input type="submit" value="Login" class="btn btn-success"
												name="submit"> <input type="reset" value="Reset"
												class="btn btn-warning" name="reset"> <input
												type="button" value="Cancel" class="btn btn-info"
												name="cancel">
										</p>
									</form>
									<a href="/api/v1/fe/Signup">If not register? <font color="blue"><b>Register
												Here</b></font></a>


								</div>
							</div>
							</c:if>
							
							
							
							<!-- verify OTP------------------------------------------------------ -->
							<c:if test="${otp ne null }">
							
							<div class="col-md-6">
								<div class="mu-contact-right">
									<form class="contactform" action="/api/v1/login" method="post" onsubmit="javascript:return passwordmatch();">



										<c:choose>
											<c:when test="${msg!=null}">
												<p align='center'
													style="border-style: solid; border-color: green;">
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
											
											<c:if test="${byemail ne null }">
											<p class="comment-form-email">
											<label for="email">Enter id<span
												class="mandate">*</span></label> <input type="text"
												required="required" aria-required="true" value="${byemail}"
												name="byemail" id="byemail" readonly="readonly">
										</p>
										</c:if>
										<c:if test="${byphone ne null }">
										<p class="comment-form-email">
											<label for="email">Contact Number<span
												class="mandate">*</span></label> <input type="text"
												required="required" aria-required="true" readonly="readonly"
												name="byphone" id="byphone" maxlength="10" minlength="10" value="${byphone}">
										</p>
											</c:if>
										
										<p class="comment-form-email">
											<label for="email">Enter OTP<span
												class="mandate">*</span></label> <input type="text"
												required="required" aria-required="true" value=""
												name="emailphOtp" id="emailphOtp" maxlength="6" minlength="6"
													onkeypress="javascript: return onlyNumbers(event);">
										</p>
										<p class="comment-form-email">
											<label for="email">Enter New Password<span
												class="mandate">*</span></label> <input type="text"
												required="required" aria-required="true" value=""
												name="npassword" id="npassword" 
												pattern="^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d][A-Za-z\d!@#$%^&*()_+]{7,19}$" 
												title="Must contain at least one number and one uppercase and lowercase letter,one special  
												and at least 8 or more characters"   onclick="hideMessage('password_error');">
										</p>
										<p class="comment-form-email">
											<label for="email">Enter Confirm Password<span
												class="mandate">*</span></label> <input type="text"
												required="required" aria-required="true" value=""
												name="cpassword" id="cpassword" 
                                                pattern="^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d][A-Za-z\d!@#$%^&*()_+]{7,19}$" 
												title="Must contain at least one number and one uppercase and lowercase letter,one special  
												and at least 8 or more characters"  onclick="hideMessage('password_error');">
												<label	id="password_error" style="display: none; color: red;"></label>
										</p>
                                     
                                     

										
                                      <input type="hidden" name="otpflag" value="verifytp">

										<p class="form-submit">
											<input type="submit" value="Verify" class="btn btn-success"
												name="submit"> <input type="reset" value="Reset"
												class="btn btn-warning" name="reset"> <input
												type="button" value="Cancel" class="btn btn-info"
												name="cancel">
										</p>
									</form>


								</div>
							</div>
							</c:if>
							
							
							
							
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
	<!-- <div class="container">
  <a href="#" data-target="#pwdModal" data-toggle="modal">Forgot my password</a>
</div> -->

	<!--modal-->
	<div id="pwdModal" class="modal fade" tabindex="-1" role="dialog"
		aria-hidden="true">
		
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h1 class="text-center">What's My Password?</h1>
				</div>
				<div class="modal-body">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="text-center">

									<p>Please Choose Any One</p>
									
									<div class="form-check">
  <input class="form-check-input" type="radio" name="PhoneOrEmail" id="Email"  onclick="javascript:yesnoCheck();">
  <label class="form-check-label" for="flexRadioDefault2">
    By Email
  </label>
</div>
<div class="form-check">
  <input class="form-check-input" type="radio" name="PhoneOrEmail" id="Phone" onclick="javascript:yesnoCheck();">
  <label class="form-check-label" for="flexRadioDefault2">
    By Phone
  </label>
</div>
									
									<div class="panel-body">
										<fieldset>
										<form class="contactform1" action="/api/v1/login" method="post">
										
										<input type="hidden" name="otpflag" value="yes">
										<div class="form-group" id="pe" style="display: none;">
												<input class="form-control input-lg"
													placeholder="E-mail Address" name="emailorphone"
													id="emailorphone" type="text" >
											</div>
										
										
											<div class="form-group" id="e" style="display: none;">
												<input class="form-control input-lg"
													placeholder="E-mail Address" name="byemail" 
													id="byemail" type="text">
											</div>
											
											<div class="form-group" id="p" style="display: none;">
												<input class="form-control input-lg"
													placeholder="Conatct Number" maxlength="10"
													id="byphone" type="text" name="byphone" onkeypress="javascript: return onlyNumbers(event);">
											</div>
											
											<div id="btn" style="display: none;">
											<input type="Submit" value="Submit" class="btn btn-success" name="Submit">
											<!-- <input class="btn btn-lg btn-primary btn-block"
												value="Ok" type="Submit"> -->
											</div>	
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
	<!-- lgt box end -->

	<!-- Start footer -->
	<jsp:include page="footer.jsp" />
	<!-- End footer -->





</body>
</html>