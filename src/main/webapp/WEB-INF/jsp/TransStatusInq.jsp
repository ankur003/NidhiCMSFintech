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


<script type="text/javascript">
  function copyUuid(uuid)
  {
       document.getElementById("userUuid").value = uuid;
       document.getElementById("tab").style.display = "";
  }

  	
  function validateUpi()
  {
  	var upiVirtualAddress=document.getElementById("upiVirtualAddress").value;	
  	var userUuid=document.getElementById("userUuid").value;	
  	var adminUuid=document.getElementById("adminUuid").value;	
         
      var dataemployeeid = {"userUuid":userUuid,"adminUuid" : adminUuid,"upiVirtualAddress":upiVirtualAddress};
      $.ajax({
          type : "POST",
          url : "/api/v1/validateUpi",
          data : dataemployeeid,
          success : function(data) {
        	
        	  console.log(data);
        	  var obj = JSON.parse(data);
        	 
        	  var response=obj.found;
        	  if(response=='YES')
        		  {
        		document.getElementById("upiadd").disabled = false;
        		 document.getElementById("upifail").style.display = 'block';
        		  }
        	  else 
        		  {
        		document.getElementById("upiadd").disabled = true;
        		 document.getElementById("upiok").style.display = 'block';
        		  }
        	
      	},
  		error : function(e) {
  			alert('Error: ' + e);
  		}
  	});
      
  	
  }

  function hideMessage(divId){
  	document.getElementById(divId).style.display="none";
  }
  </script>
</head>
<c:if test="${sessionScope.userLoginDetails eq null}">
	<c:redirect url="/api/v1/fe/login"></c:redirect>
</c:if>
<body>
	<jsp:include page="adminmenu.jsp" />
	<!-- Page breadcrumb -->
	<section id="mu-page-breadcrumb">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="mu-page-breadcrumb-area">
					<h2>My Dashboard</h2>
					<ol class="breadcrumb">
						<li><a href="#">Upi</a></li>
						<li class="active">Transaction Status inquiry</li>
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
					<div class="mu-contact-content" style="margin-top: -2%">
						<div class="row">
							<div class="col-md-12">
								<div class="mu-contact-right">
									<form class="contactform" action="/api/v1/checkTrnsStatusInq" method="post">

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
										
									<input type="hidden" id="userUuid" name="userUuid" value="${userLoginDetails.userUuid}">
										
                                     
                                        <input type="hidden" name="adminUuid" value="${userLoginDetails.userUuid}">
									<div class="col-lg-12">
									<div class="col-lg-6">
										<p class="comment-form-url">
											<label for="subject">Choose<span class="mandate">*</span></label><br>
											<select id="typeTrans" name="typeTrans" style="height: 32px;width: 400px;">
  											<option value="Cust_Ref_No">Customer ref number</option>
  											<option value="npci_Tran_Id;">NPCI trans Id</option>
											</select>
										</p>
										</div>
										<div class="col-lg-6">
										<p class="comment-form-url">
											<label for="subject">Transaction Id<span class="mandate">*</span></label>
											<input type="text" name="transid" id="transid"
												minlength="1" aria-required="true" required="required"
												maxlength="100" 
												>
										</p>
										</div>
										</div>		
												
												
												<div class="col-lg-12">
											<p class="form-submit">
											<input type="submit" value="Check" class="btn btn-success" id="upiadd" 
												name="Submit"> <input type="button" value="Cancel"
												class="btn btn-info" name="cancel">
										</p>
										</div>
										</form>
										
										<c:if test="{init}">
										
										
										${upitransresp}
										
										
										
										
										
										</c:if>
										
										
										
										
										
										
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- end contact content -->
				
			
			</div>
		</div>
	</div>
	</section>


	<!-- Start footer -->
	<jsp:include page="footer.jsp" />
	<!-- End footer -->


</body>
</html>