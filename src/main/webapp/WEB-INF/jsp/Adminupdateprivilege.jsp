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
  function showadp()
  {
       document.getElementById("addp").style.display = "";
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
						<li><a href="#">Setting </a></li>
						<li class="active">White List</li>
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
									<form class="contactform" action="/api/v1/admin-update-privilege" method="post">


	                                      <c:choose>
											<c:when test="${msg!=null}">
												<p align='center'
													style="border-style: solid; border-color: green;">
													<font color="green"> ${msg} </font>
												</p>
											</c:when>
											<c:otherwise>
											<c:if test="${msgs!=null}">
											<p align='center'
													style="border-style: solid; border-color: red;">
													<font color="green"> ${msgs} </font>
												</p>
												</c:if>
											</c:otherwise>
										</c:choose>
										
										<div class="col-lg-6" style="display: none;">
											<input type="button" value="Add" class="btn btn-success" name="Add Privilege" onclick="showadp();"> 
										</div>
										
										
										
									<div id="addp" >	
									
									<input type="hidden" value="${systemPrivilege.systemPrivilegeId}" name="systemPrivilegeId" id="systemPrivilegeId">
									<input type="hidden" id="adminUuid" name="adminUuid" value="${userLoginDetails.userUuid}">
									    <div class="col-lg-12">
											<p class="comment-form-author">
												<label for="author">Privilege Name</label> 
												<input type="text"	 value="${systemPrivilege.privilegeName}" name="privilegeName"
												 readonly id="privilegeName">
											</p>
										</div>
										  <div class="col-lg-12">
											<p class="comment-form-author">
												<label for="author">New Privilege Name</label> 
												<input type="text"	  name="nprivilegeName" id="nprivilegeName">
											</p>
										</div>
										<br>
                                        <div class="col-lg-6">
											<p class="form-submit">
												<input type="submit" value="Update" class="btn btn-success"
													name="Submit"> <input type="button" value="Cancel"
													class="btn btn-info" name="cancel">
											</p>
										</div>
										
									</div>	

                                     
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
	</section>


	<!-- Start footer -->
	<jsp:include page="footer.jsp" />
	
</body>
</html>