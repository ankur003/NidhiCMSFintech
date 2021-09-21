//var contextPath="/sscoachingcentre";
function passwordchk()
{
var valid=true;
if(document.getElementById("newpassword").value != document.getElementById("cpassword").value)
{
	 document.getElementById("nomatch").style.display="block";
	 document.getElementById("nomatch").innerHTML="New Password and confirm password not match.";
	 valid=false;
}
return valid;
}

function checkemail()
{
	var semail=document.getElementById("semail").value;
	var datas = {"semail" : semail};
	$.ajax({
		type : "POST",
		url :  contextPath +"/checkemail",
		data : datas,
		success : function(data) {
			document.getElementById("emailcount").value=data;
			if(data>0)
				{
			 valid=false;
				}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
function checkph()
{
	var scontact=document.getElementById("scontact").value;
	var datas = {"scontact" : scontact};
	$.ajax({
		type : "POST",
		url :  contextPath +"/checkcontact",
		data : datas,
		success : function(data) {
			document.getElementById("phcount").value=data;
			if(data>0)
				{
			 valid=false;
				}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}


function validProfessionalProfile()
{
	var valid=true;
	if(document.getElementById("sid").value.trim()!= "0" && document.getElementById("sid").value.trim()!=""){
		if(document.getElementById("emailcount").value.trim()!=""){
		
		if(document.getElementById("sid").value.trim()!=document.getElementById("emailcount").value.trim()){
		document.getElementById('emailerror').style.display = "block";
		document.getElementById('emailerror').innerHTML = "Email is Already Registered.";
		valid=false;
		document.getElementById("semail").focus();
		}
		}
	}	
	if(document.getElementById("sid").value.trim()!= "0" && document.getElementById("sid").value.trim()!=""){
		if(document.getElementById("phcount").value.trim()!=""){
		
		if(document.getElementById("sid").value.trim()!=document.getElementById("phcount").value.trim()){
		document.getElementById('pherror').style.display = "block";
		document.getElementById('pherror').innerHTML = "Contact number is Already Registered.";
		valid=false;
		document.getElementById("scontact").focus();
		}
		}
	}	
	return valid;
}

function finddetails()
{
	var standard=document.getElementById("standard").value.trim();
	var datas = {"standard" : standard};
	$.ajax({
		type : "POST",
		url :  contextPath +"/coursetb",
		data : datas,
		success : function(data) {
			if(data!=null)
				{
			document.getElementById("courselistdiv").style.display = "";
			document.getElementById("courselistdiv").innerHTML = data;
				}
			else{
				alert("na");
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
	
}


function findstudentlist()
{
	var sfirstname=document.getElementById("sfirstname").value.trim();
	var slastname=document.getElementById("slastname").value.trim();
	var scontact=document.getElementById("scontact").value.trim();
	var datas = {"sfirstname" : sfirstname,"slastname":slastname,"scontact":scontact};
	$.ajax({
		type : "POST",
		url :  contextPath +"/getDataofStudent",
		data : datas,
		success : function(data) {
			if(data!=null)
				{
			document.getElementById("courselistdiv").style.display = "";
			document.getElementById("courselistdiv").innerHTML = data;
				}
			else{
				alert("na");
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});

}

function allot(sid,standard)
{
	var datas = {"standard" : standard};
	$.ajax({
		type : "POST",
		url :  contextPath +"/coursebyStandard",
		data : datas,
		success : function(data) {
			if(data!=null)
				{
				document.getElementById("studentid").value = sid;
			document.getElementById("allotmentdiv").style.display = "";
			document.getElementById("allotmentdiv").innerHTML = data;
				}
			else{
				alert("na");
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});

}

function allotbatch(cid,standard)
{
	var sid=document.getElementById("studentid").value;
	var datas = {"cid" : cid,"sid":sid,"standard":standard};
	$.ajax({
		type : "POST",
		url :  contextPath +"/allocatebatches",
		data : datas,
		success : function(data) {
			if(data!=null)
				{
				alert("Batch Has been alloted.");
				document.getElementById("studentid").value = sid;
			document.getElementById("allotmentdiv").style.display = "";
			document.getElementById("allotmentdiv").innerHTML = data;
				}
			else{
				alert("na");
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});

}

function deallotbatch(cid,standard)
{
	var sid=document.getElementById("studentid").value;
	var datas = {"cid" : cid,"sid":sid,"standard":standard};
	$.ajax({
		type : "POST",
		url :  contextPath +"/deallocatebatches",
		data : datas,
		success : function(data) {
			if(data!=null)
				{
			
				alert("Student Has been removed from batch");
				
				
				document.getElementById("studentid").value = sid;
			document.getElementById("allotmentdiv").style.display = "";
			document.getElementById("allotmentdiv").innerHTML = data;
				}
			else{
				alert("na");
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});

}

function Checkalloted(sid,standard)
{
	var datas = {"sid":sid};
	$.ajax({
		type : "POST",
		url :  contextPath +"/checkallotedbatch",
		data : datas,
		success : function(data) {
			if(data!=null)
				{
				 document.getElementById("showalloted").click();
				 document.getElementById("studentid").value = sid;		
			document.getElementById("showallotedtiming").style.display = "";
			document.getElementById("showallotedtiming").innerHTML = data;
				}
			else{
				alert("na");
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}

function getListofcourses()
{
	var standard=document.getElementById("standard").value.trim();
	var datas = {"standard" : standard};
	$.ajax({
		type : "POST",
		url :  contextPath +"/listofcoursesforaddnum",
		data : datas,
		success : function(data) {
			if(data!=null)
				{
				
				document.getElementById("batchdivbtn").click();
			document.getElementById("batchtimelist").style.display = "";
			document.getElementById("batchtimelist").innerHTML = data;
				}
			else{
				alert("na");
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});	
}


function getDataofStudentbycid(cid)
{
	
	var datas = {"cid" : cid};
	$.ajax({
		type : "POST",
		url :  contextPath +"/getListofStudentfornumber",
		data : datas,
		success : function(data) {
			if(data!=null)
				{
				 document.getElementById("cid").value = cid;		
				 document.getElementById("studnetListForAdd").style.display = "";
				 document.getElementById("studnetListForAdd").innerHTML = data;
				}
			else{
				alert("na");
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}


function AddNumber(sid,aid)
{
	var cid= document.getElementById("cid").value;
	var subjectnumber= document.getElementById("subjectnumber"+aid).value;
	
	var datas = {"sid":sid,"cid" : cid,"subjectnumber":subjectnumber,"aid":aid};
	$.ajax({
		type : "POST",
		url :  contextPath +"/addSubjectNumber",
		data : datas,
		success : function(data) {
			
			if(data!=null)
				{
				 document.getElementById("added").style.display="block";
				 document.getElementById("added").innerHTML=subjectnumber+" Has been added";
				 document.getElementById("cid").value = cid;		
				 document.getElementById("studnetListForAdd").style.display = "";
				 document.getElementById("studnetListForAdd").innerHTML = data;
				}
			else{
				alert("na");
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}

function showobtainmarks(sid)
{
	var datas = {"sid":sid};
	$.ajax({
		type : "POST",
		url :  contextPath +"/getSubjectnumbySid",
		data : datas,
		success : function(data) {
			
			if(data!=null)
				{
				document.getElementById("shownumberbtn").click();
				 document.getElementById("showobtainmarks").style.display = "";
				 document.getElementById("showobtainmarks").innerHTML = data;
				}
			else{
				alert("na");
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}

function sendpassword()
{
	var emailorph=document.getElementById("contactoremail").value;
	var datas = {"emailorph":emailorph};
	$.ajax({
		type : "POST",
		url :  contextPath +"/forgetpassword",
		data : datas,
		success : function(data) {
			
			if(data!=null)
				{
				
				document.getElementById("canbtn").click();
				document.getElementById('fpass').style.display = "block";
				document.getElementById('fpass').innerHTML = "Your Password has been sent on your email. kindly check";
				}
			else{
				alert("na");
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});

}
 function getNoticboard()
 {
			$.ajax({
			type : "POST",
			url :  contextPath +"/showNotic",
			success : function(data) {
				
				if(data!=null)
					{
					 document.getElementById("notices").style.display = "";
					 document.getElementById("notices").innerHTML = data;
					}
				else{
					alert("na");
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	 
 }

