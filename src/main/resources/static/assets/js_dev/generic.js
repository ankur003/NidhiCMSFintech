function onlyNumbers(e) {

	var keynum="";
	
	//var numcheck  
	// For Internet Explorer  
	if (window.event) {
		keynum = e.keyCode;
	}
	// For Netscape/Firefox/Opera  
	else if (e.which) {
		keynum = e.which;
	}
	/*keychar = String.fromCharCode(keynum); 
	alert(keychar);*/
	/*var e = event || evt; // for trans-browser compatibility
	var charCode = e.which || e.keyCode;*/

	if (keynum > 31 && (keynum < 48 || keynum > 57)) {
		return false;
	} else {
		return true;
	}
}
function onlyNumbersDot(e){
	var keynum="";
	
	//var numcheck  
	// For Internet Explorer  
	if (window.event) {
		keynum = e.keyCode;
	}
	// For Netscape/Firefox/Opera  
	else if (e.which) {
		keynum = e.which;
	}
	/*keychar = String.fromCharCode(keynum); 
	alert(keychar);*/
	/*var e = event || evt; // for trans-browser compatibility
	var charCode = e.which || e.keyCode;*/

	if ((keynum  <48 || keynum > 58) && keynum != 46 && keynum!=8) {
		return false;
	} else {
		return true;
	}
}

function onlySpaceDotChars(e) {
	/*
		if (keynum==37 ||keynum == 8|| keynum== 9||keynum == 32||keynum==46 ||(keynum >= 65 && keynum <= 90)
				|| (keynum >= 97 && keynum <= 122)) {
			return true;
		} else {
			return false;
		}*/
		var keynum="";
		
		if (window.event) {
			keynum = e.keyCode;
		}
		else if (e.which) {
			keynum = e.which;
		}
		if ((keynum < 65 || keynum > 90) && (keynum < 97 || keynum > 122) && keynum!=8 && keynum != 9 && keynum != 32 &&( keynum < 45 || keynum >46) && keynum!='') {
			return false;
		} else {
			return true;
		}

	}
function onlySpaceCommChars(e) {
	/*
		if (keynum==37 ||keynum == 8|| keynum== 9||keynum == 32||keynum==46 ||(keynum >= 65 && keynum <= 90)
				|| (keynum >= 97 && keynum <= 122)) {
			return true;
		} else {
			return false;
		}*/
		var keynum="";
		
		if (window.event) {
			keynum = e.keyCode;
		}
		else if (e.which) {
			keynum = e.which;
		}
		if ((keynum < 65 || keynum > 90) && (keynum < 97 || keynum > 122) && (keynum != 44) && keynum!=8 && keynum != 9 && keynum != 32 && keynum!='') {
			return false;
		} else {
			return true;
		}

	}

function restictSpace(e) {

	var keynum="";
	// For Internet Explorer  
	if (window.event) {
		keynum = e.keyCode;
	}
	// For Netscape/Firefox/Opera  
	else if (e.which) {
		keynum = e.which;
	}
	if (keynum ==32 ) {
		return false;
	} else {
		return true;
	}
}
function onlyalphaNumbers(e) {

	keynum="";
	
	//var numcheck  
	// For Internet Explorer  
	if (window.event) {
		keynum = e.keyCode;
	}
	// For Netscape/Firefox/Opera  
	else if (e.which) {
		keynum = e.which;
	}
	/*keychar = String.fromCharCode(keynum); 
	alert(keychar);*/
	/*var e = event || evt; // for trans-browser compatibility
	var charCode = e.which || e.keyCode;*/
	if (keynum > 31 && (keynum < 48 || keynum > 57)
			&& (keynum < 65 || keynum > 90) && (keynum < 97 || keynum > 122))
		return false;
	else {
		return true;
	}
}
/*function onlySpaceDotChars(e) {


	if (keynum==37 ||keynum == 8|| keynum== 9||keynum == 32||keynum==46 ||(keynum >= 65 && keynum <= 90)
			|| (keynum >= 97 && keynum <= 122)) {
		return true;
	} else {
		return false;
	}
	var keynum="";
	
	if (window.event) {
		keynum = e.keyCode;
	}
	else if (e.which) {
		keynum = e.which;
	}
	if ((keynum < 65 || keynum > 90) && (keynum < 97 || keynum > 122) && (keynum <37 || keynum>38 )&& keynum!=8 && keynum != 9 && keynum != 32 &&( keynum < 45 || keynum >46) ) {
		return false;
	} else {
		return true;
	}

}*/
function restrictSpaceWithoutData(e,id) {

	var keynum="";
	
	if (window.event) {
		keynum = e.keyCode;
	}
	else if (e.which) {
		keynum = e.which;
	}
	if(document.getElementById(id).value.trim() == '' && keynum == 32){
		return false;
	}else{ 
		return true;
		}
	
	}
function onlyChars(e) {

	var keynum="";
	
	//var numcheck  
	// For Internet Explorer  
	if (window.event) {
		keynum = e.keyCode;
	}
	// For Netscape/Firefox/Opera  
	else if (e.which) {
		keynum = e.which;
	}
	
	if (keynum > 31 && (keynum < 65 || keynum > 90)
			&& (keynum < 97 || keynum > 122)) {
		{
			
		return false;
		}
	} else {

		return true;
	}

}

function onlyNumSpecChar(e) {

	var keynum="";
	
	//var numcheck  
	// For Internet Explorer  
	if (window.event) {
		keynum = e.keyCode;
	}
	// For Netscape/Firefox/Opera  
	else if (e.which) {
		keynum = e.which;
	}

	if (keynum > 31 && (keynum < 48 || keynum > 57)
			&& (keynum < 65 || keynum > 90) && (keynum < 97 || keynum > 122)
			&& keynum != 32 && keynum != 35 && keynum != 58 && keynum != 38
			&& keynum != 40 && keynum != 41 && keynum != 46 && keynum != 64
			&& keynum != 45 && keynum != 91 && keynum != 93 && keynum != 95
			&& keynum != 123 && keynum != 125 && keynum != 44 && keynum != 13) {

		return false;
	} else {
		return true;
	}
}

function onlyDotChar(e) {

	var keynum="";
	
	//var numcheck  
	// For Internet Explorer  
	if (window.event) {
		keynum = e.keyCode;
	}
	// For Netscape/Firefox/Opera  
	else if (e.which) {
		keynum = e.which;
	}

	if (keynum > 31 && (keynum < 65 || keynum > 90) && (keynum != 46) &&(keynum!=32)
			&& (keynum < 97 || keynum > 122)) {
		return false;
	} else {
		return true;
	}
}

function blockBackspace(e) {
	var keynum="";
	//var numcheck  
	// For Internet Explorer  
	if (window.event) {
		keynum = e.keyCode;
	}
	// For Netscape/Firefox/Opera  
	else if (e.which) {
		keynum = e.which;
	}
	if (keynum == 8) {
		return false;
	} else {
		return true;
	}
}

function restict_commercial(e) {

	keynum="";
	
	if (window.event) {
		keynum = e.keyCode;
	}
	else if (e.which) {
		keynum = e.which;
	}
	if (keynum == 64)
		return false;
	else {
		return true;
	}
}

function onlyChars_spc(e) {

	var keynum="";
	
	//var numcheck  
	// For Internet Explorer  
	if (window.event) {
		keynum = e.keyCode;
	}
	// For Netscape/Firefox/Opera  
	else if (e.which) {
		keynum = e.which;
	}
	
	if (keynum > 31 &&  (keynum < 65 || keynum > 90)&&(keynum != 32)
			&& (keynum < 97 || keynum > 122)) {
		{
			
		return false;
		}
	} else {

		return true;
	}

}

function only_char_coma(e) {

	var keynum="";
	//var numcheck  
	// For Internet Explorer  
	if (window.event) {
		keynum = e.keyCode;
	}
	// For Netscape/Firefox/Opera  
	else if (e.which) {
		keynum = e.which;
	}
	
	if (keynum > 31 &&(keynum != 44)&&(keynum != 32) &&(keynum < 65 || keynum > 90)
			&& (keynum < 97 || keynum > 122 )) {
		{
		
		return false;
		}
	} else {

		return true;
	}

}

function only_num_coma_dot_char(e) {

	var keynum="";
	//var numcheck  
	// For Internet Explorer  
	if (window.event) {
		keynum = e.keyCode;
	}
	// For Netscape/Firefox/Opera  
	else if (e.which) {
		keynum = e.which;
	}
	
	if (keynum > 31 && (keynum < 48 || keynum > 57) &&(keynum != 44)&&(keynum != 32)  &&(keynum != 46)&&(keynum < 65 || keynum > 90)
			&& (keynum < 97 || keynum > 122))
	{
		{
		
		return false;
		}
	} else {

		return true;
	}

}

/*Only cahr and comma allowed*/

function onlyChars_comma(e) {
	var keynum="";
	
	//var numcheck  
	// For Internet Explorer  
	if (window.event) {
		keynum = e.keyCode;
	}
	// For Netscape/Firefox/Opera  
	else if (e.which) {
		keynum = e.which;
	}
	
	if (keynum > 31 && (keynum < 48 || keynum > 57) && (keynum < 65 || keynum > 90)&&(keynum != 46)&&(keynum != 95)
			&& (keynum < 97 || keynum > 122)) {
		{
			
		return false;
		}
	} else {

		return true;
	}

}

function hideMessage(divId){
	document.getElementById(divId).style.display="none";
}

function Filevalidation()
{ var valid=true;

var allowedFiles = [".png",".jpg",".jpeg"];
var fileUpload = document.getElementById("imagess");
var regex = new RegExp("([a-zA-Z0-9\s_\\.\-:])+(" + allowedFiles.join('|') + ")$");

    var sizeofupload=fileUpload.files[0].size;
	 if(sizeofupload>1048576)
     {
     	alert('upload file less then 1 MB');
     	 valid=false;
     }
	 
	 else{
         if (!regex.test(fileUpload.value.toLowerCase()))
         {
        	 alert('Please Upload png , jpg or jpeg file only');
      	  valid=false; 
   	  fileUpload.value = "";
         }
   else{
       }
   }
	 
	 return valid;
}
