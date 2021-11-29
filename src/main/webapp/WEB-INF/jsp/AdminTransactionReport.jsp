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
<title>NIDHI CMS | ADMIN DASHBOARD</title>

<link rel="stylesheet"	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
  $( function() {
    $( "#datepicker" ).datepicker({
    	 changeMonth: true,
         changeYear: true,
         dateFormat: 'dd-mm-yy',
         endDate: "today",
         maxDate: "today"
    });
  } );
  
  $( function() {
	    $( "#datepicker1" ).datepicker({
	    	 changeMonth: true,
	         changeYear: true,
	         dateFormat: 'dd-mm-yy',
	         endDate: "today",
	         maxDate: "today"
	    });
	  } );
  
  

  function getmgrname()
  {
         var emp_rmanager=document.getElementById("firstname").value;
         var dataemployeeid = {
                 "emp_rmanager" : emp_rmanager
          };
         $.ajax({
             type : "POST",
             url : "/api/v1/getUserNameByMarchantIds",
             data : dataemployeeid,
             success : function(data) {
         $( function() {
      	    var availableTags=new Array();
  			for(var i=0;i<data.length;i++)
  				{
  				availableTags=data.split(",");
  	         	} 
      	    $( "#firstname" ).autocomplete({
      	      source: availableTags
      	    });
      	  } );   
         	},
  		error : function(e) {
  			alert('Error: ' + e);
  		}
  	});
  	}
         

  </script>
</head>
<c:if test="${sessionScope.userLoginDetails eq null}">
	<c:redirect url="/api/v1/fe/login"></c:redirect>
</c:if> 
<body>
<jsp:include page="adminmenu.jsp" />
	<a class="scrollToTop" href="#"> <i class="fa fa-angle-up"></i>
	</a>
	<section id="mu-page-breadcrumb">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="mu-page-breadcrumb-area">
					<h2>My Dashboard</h2>
					<ol class="breadcrumb">
						<li><a href="#">Report</a></li>
						<li class="active">Transaction Report</li>
					</ol>
				</div>
			</div>
		</div>
	</div>
	</section>
	<!-- Start contact  -->
	<section id="mu-contact">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="mu-contact-area">
					<!-- start title -->
					
					
					
					
					<div class="mu-contact-content" style="margin-top: -2%">
						<div class="row">
							
								<div class="col-md-12">
								<div class="mu-contact-right">
									<form class="contactform" method="post" action="/api/v1/findbyMerchantId">


										<div class="col-lg-12">
										<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">Client Name<span class="mandate">*</span></label>
													<input type="text" required="required" value="${clientname}"
														name="clientname" id="firstname" oninput='getmgrname()'>
												</p>
											</div>
											<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">From date<span class="mandate">*</span></label>
													<input type="text" required="required" value="${startDate}"
														name="startDate" id="datepicker" autocomplete="off" >
												</p>
											</div>
											<div class="col-lg-3">
												<p class="comment-form-author">
													<label for="author">To date<span class="mandate">*</span></label>
													<input type="text" required="required" value="${endDate}"
														name="endDate" id="datepicker1" autocomplete="off">
												</p>
											</div>
										</div>

										<p class="form-submit" align="left">
											<input type="submit" value="Submit" class="btn btn-success"
												name="Submit"> <input
												
												type="button" value="Cancel" class="btn btn-info"
												name="cancel">
										</p>



											<c:if test="${init }">
											<table class="table" style="margin-top: 2%;">
												<thead class="thead-light" style="background-color: gray;">
													<tr>
														<th scope="col">#</th>
														<th scope="col">Date</th>
														<th scope="col">UniqueId</th>
														<th scope="col">Amount</th>
													    <th scope="col">Fee</th>
														<th scope="col">Currency</th>
														<th scope="col">Type</th>
														<th scope="col">Status</th>
														
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${trans}" var="us"
														varStatus="counter">
														<tr>
															<th scope="row">${counter.count}</th>
															<td><fmt:parseDate value="${us.txDate}"
																	pattern="yyyy-MM-dd" var="disbDate" /> <fmt:formatDate
																	value="${disbDate}" pattern="dd-MM-yyyy" /></td>
															<td>${us.uniqueId}</td>
															<td>${us.amount}</td>
															<td>${us.fee}</td>
															 <td>${us.currency}</td> 
															<td>${us.txType}</td>
															<td>${us.status}</td>
															
															
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>


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

	

</body>
</html>