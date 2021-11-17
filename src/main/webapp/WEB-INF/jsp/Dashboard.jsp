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


<!-- 
<script>
function openNav() {
  document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
  document.getElementById("mySidenav").style.width = "0";
}
</script>
 -->
</head>
<c:if test="${sessionScope.authtoken eq null}">
	<c:redirect url="/api/v1/fe/login"></c:redirect>
</c:if> 
<body>

<jsp:include page="usermenu.jsp" />
<!-- <div id="mySidenav" class="sidenav">
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
  <a href="#"> <b>Payout</b>
  <img src="/assets/img/rightbluearrow.png"	align="right" style="width: 7px; height: 13px; margin-right: 15px; margin-top: 6px;" alt="">
  </a>
  <a href="/api/v1/fe/PayOutSummary">1. Summary</a>
  <a href="/api/v1/fe/AccountStatement">2. Account Statement</a>
  <a href="#">3. Report</a> 
      <a href="/api/v1/fe/payoutReport" style="margin-left: 25px"> a. Payout</a> 
      <a href="/api/v1/fe/bankAcVerifyreport" style="margin-left: 25px">b. Bank A/c Verification</a> 
  <a href="/api/v1/fe/AccessSetting">4. Access Setting</a>
  <a href="/api/v1/fe/FundAccount">5. Fund Account</a>
  <a href="/api/v1/fe/Setting">6. Setting</a>
</div>

			 -->

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
													style="border-style: solid; border-color: green; margin-top: -20px;">
													<font color="green"> ${msg} </font>
												</p>
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>
                            
                                 <div class="col-sm-12" style="margin-left: 25%;margin-top: 0%;">
								 <img src="/assets/img/wc.gif"	  alt="">		
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