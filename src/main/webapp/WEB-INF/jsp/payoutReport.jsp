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
<title>NIDHI CMS | ACCOUNT Statement</title>


<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
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
  
  function fnExcelReport()
  {
  	var today = new Date();
  	var date = today.getFullYear()+'_'+(today.getMonth()+1)+'_'+today.getDate()+'_';
  	var time = today.getHours() + "_" + today.getMinutes() + "_" + today.getSeconds();
      var sFileName = 'ProcessingFeeReport_'+date+time+'.xlsx';alert('s');
      let table = document.getElementsByTagName("table");alert('s1');
      TableToExcel.convert(table[0], {
          name: sFileName,
          sheet: {
            name: 'ProcessingFeeReport_ Report'
          }
      });
  }
  </script>

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
					<h2>Report</h2>
					<ol class="breadcrumb">
						<li><a href="#">Report</a></li>
						<li class="active">Payout Report</li>
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
							
										<div class="col-md-12">
								<div class="mu-contact-right">
                                  <form class="contactform"  method="post" action="/api/v1/userPayoutReport" >
                                          <div class="col-sm-12">
                                            <p class="comment-form-comment">
													<label for="comment">Transaction Details</label>
												</p>
												</div>
	                                    <input type="hidden" id="userUuid" name="userUuid" value="${userLoginDetails.userUuid}">

										<div class="col-lg-12">
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">From date<span class="mandate">*</span></label>
													<input type="text" required="required" value="${startDate}"
														name="startDate" id="datepicker" autocomplete="off">
												</p>
											</div>
											<div class="col-lg-6">
												<p class="comment-form-author">
													<label for="author">To date<span class="mandate">*</span></label>
													<input type="text" required="required" value="${endDate}"
														name="endDate" id="datepicker1" autocomplete="off">
												</p>
											</div>
										</div>

										<p class="form-submit" align="left">
											<input type="submit" value="Submit" class="btn btn-success"
												name="Submit" onclick> <input
												
												type="button" value="Cancel" class="btn btn-info"
												name="cancel">
										</p>


										<c:if test="${init }">
										 <div align="right">
                                  <!--       <a href=""> <img src="/assets/img/exl.png" style="height: 50px; width: 90px; " alt="logo"  onclick="Export();"></a>
                                          <a href="">  <img src="/assets/img/pdf.png" style="height: 40px; width: 40px; " alt="logo"></a>
 -->                                         <!--    <button class="btn btn-primary" type="submit" onclick="fnExcelReport()">Xls</button> -->
                                          </div>
										
											<table class="table" style="margin-top: 2%;">
												<thead class="thead-light" style="background-color: gray;">
													<tr>
														<th scope="col">#</th>
														<th scope="col">Date</th>
														<th scope="col">UniqueId</th>
														<!-- <th scope="col">Aggr Id</th>
														<th scope="col">Aggr Name</th> -->
														<th scope="col">IFSC</th>
														<th scope="col">Amount</th>
														<th scope="col">Fee</th>
														<th scope="col">Currency</th>
														<th scope="col">Txn Type</th>
														<th scope="col">Payee name</th>
														<th scope="col">M_Id</th>
														<th scope="col">Status</th>
														<th scope="col">UTR</th>
														<th scope="col">Res.</th>
														
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
															<%-- <td>${us.aggrId}</td>
															<td>${us.aggrName}</td> --%>
															<td>${us.ifsc}</td>
															<td>${us.amount}</td>
															<td>${us.fee}</td>
															 <td>${us.currency}</td> 
															<td>${us.txnType}</td>
															<td>${us.payeeName}</td>
															<td>${us.merchantId}</td>
															<td>${us.status}</td>
															<td>${us.utrNumber}</td>
															<td>${us.response}</td>
															
															
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



	<!-- Start footer -->
	<jsp:include page="footer.jsp" />
	<!-- End footer -->


</body>
</html>