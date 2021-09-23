/**
 * 
 */
package com.nidhi.cms.controller.fe;

import static com.nidhi.cms.constants.JwtConstants.AUTH_TOKEN;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nidhi.cms.constants.ApiConstants;
import com.nidhi.cms.controller.LoginController;
import com.nidhi.cms.controller.OtpController;
import com.nidhi.cms.controller.UserController;
import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.UserDoc;
import com.nidhi.cms.modal.request.LoginRequestModal;
import com.nidhi.cms.modal.request.UserBusinessKycRequestModal;
import com.nidhi.cms.modal.request.UserCreateModal;
import com.nidhi.cms.modal.request.VerifyOtpRequestModal;
import com.nidhi.cms.modal.response.UserDetailModal;



/**
 * @author Devendra Gread
 *
 */

@RestController
@RequestMapping(value = ApiConstants.API_VERSION)
public class UserControllerFe {

	@Autowired
	UserController userController;

	@Autowired
	OtpController otpController;

	@Autowired
	LoginController loginController;

	@PostMapping(value = "/user")
	public ModelAndView userSave(@Valid @ModelAttribute UserCreateModal userCreateModal, Model model,
			HttpServletRequest request) {
		String respose = userController.userSignUp(userCreateModal);
		model.addAttribute("msg", respose);
		if (respose != null) {
			return new ModelAndView("VerifyOtp");
		}
		return null;
	}

	@PostMapping(value = "/otp/verify")
	public ModelAndView userSave(@Valid @ModelAttribute VerifyOtpRequestModal verifyOtpRequestModal, Model model,
			HttpServletRequest request) {
		String respose = otpController.verifyOTP(verifyOtpRequestModal);
		model.addAttribute("msg", respose);
		if (respose.equalsIgnoreCase("Either email or mobile OTP is incorrect, please try again."))
			return new ModelAndView("login");
		if (respose.equalsIgnoreCase("Otp already verified, please login"))
			return new ModelAndView("login");
		if (respose.equalsIgnoreCase("Otp expired, please signUp again."))
			return new ModelAndView("Signup");
		if (respose.equalsIgnoreCase("Otp verified, please proceed with login."))
			return new ModelAndView("login");

		return null;
	}

	@PostMapping(value = "/login")
	public ModelAndView login(@Valid @ModelAttribute LoginRequestModal loginRequestModal, Model model,
			HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			String authtoken = loginController.login(loginRequestModal);
			session.getServletContext().setAttribute(AUTH_TOKEN, authtoken);
			session.setAttribute(AUTH_TOKEN, authtoken);
			UserDetailModal userLoginDetails=userController.getUserDetail();
			model.addAttribute("userLoginDetails",userLoginDetails);
			
			
			session.setAttribute("userLoginDetails",userLoginDetails);
			
			UserDoc userDoc=userController.getUserDoc(DocType.DOCUMENT_PAN);
			UserDoc userDocs=userController.getUserDoc(DocType.DOCUMENT_AADHAR);
			UserDoc userDocx=userController.getUserDoc(DocType.DOCUMENT_GST);
			
			session.setAttribute("userDoc",userDoc);
			session.setAttribute("userDocs",userDocs);
			session.setAttribute("userDocx",userDocx);
			
			userLoginDetails.getRoles().forEach(roles->
			System.out.println(roles.getName().name()));
			
			if (authtoken != null) {
				return new ModelAndView("Dashboard");
			}
		} catch (Exception e) {
			model.addAttribute("msg", "Either email or Password is incorrect, please try again.");
			return new ModelAndView("login");
		}
		return null;
	}
	
	
	@PostMapping(value = "/pkycupload")
	public ModelAndView pkyc(Model model,
			HttpServletRequest request,@RequestParam MultipartFile[] fileUpload)  {
		try {
			if(fileUpload[0]!=null)
			userController.saveOrUpdateUserDoc(fileUpload[0], DocType.DOCUMENT_PAN);
			if(fileUpload[1]!=null)
			userController.saveOrUpdateUserDoc(fileUpload[1], DocType.DOCUMENT_AADHAR);
			model.addAttribute("msg", "Pan & Aadhar card successfully uploaded");
			return new ModelAndView("Bkyc");
			
		} catch (Exception e) {
			return new ModelAndView("Pkyc");
		}
	}
	
	@PostMapping(value = "/bkycupload")
	public ModelAndView bkyc(Model model,
			HttpServletRequest request,@RequestParam MultipartFile fileUpload,@ModelAttribute UserBusinessKycRequestModal userBusinessKycRequestModal) 
	{
		try {
			if(fileUpload!=null)
			userController.saveOrUpdateUserDoc(fileUpload, DocType.DOCUMENT_GST);
			
			userController.saveOrUpdateUserBusnessKyc(userBusinessKycRequestModal);
			
			model.addAttribute("msg","Business Details Succesfully Uploaded");
			return new ModelAndView("Dashboard");
			
		} catch (Exception e) {
			return new ModelAndView("Pkyc");
		}
	}
}
