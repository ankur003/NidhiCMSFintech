/**
 * 
 */
package com.nidhi.cms.controller.fe;

import static com.nidhi.cms.constants.JwtConstants.AUTH_TOKEN;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.controller.LoginController;
import com.nidhi.cms.controller.OtpController;
import com.nidhi.cms.controller.UserController;
import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.Role;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserDoc;
import com.nidhi.cms.modal.request.LoginRequestModal;
import com.nidhi.cms.modal.request.UserBusinessKycRequestModal;
import com.nidhi.cms.modal.request.UserCreateModal;
import com.nidhi.cms.modal.request.VerifyOtpRequestModal;

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
		if (respose != null && respose.equalsIgnoreCase("username : user already exist by mobile or email.")) {
			return new ModelAndView("Signup");
		}
		else
		{	return new ModelAndView("VerifyOtp");
		
		}
		
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
			User userLoginDetails = userController.getUserDetail();
			model.addAttribute("userLoginDetails", userLoginDetails);

			session.setAttribute("userLoginDetails", userLoginDetails);

			UserDoc userDoc = userController.getUserDoc(DocType.DOCUMENT_PAN);
			UserDoc userDocs = userController.getUserDoc(DocType.DOCUMENT_AADHAR);
			UserDoc userDocx = userController.getUserDoc(DocType.DOCUMENT_GST);

			session.setAttribute("userDoc", userDoc);
			session.setAttribute("userDocs", userDocs);
			session.setAttribute("userDocx", userDocx);
			String roleName = StringUtils.EMPTY;
			for (Role roles : userLoginDetails.getRoles()) {
				roleName = roles.getName().name();
			}

			List<UserDoc> getUserAllKyc=userController.getUserAllKyc();
			
			if(!getUserAllKyc.isEmpty() && getUserAllKyc.size()==3 || getUserAllKyc.size()>3)
			{
				session.setAttribute("kyc", "Done");
			}else
			{
				session.setAttribute("kyc", "Pending");
			}
			
			if (authtoken != null && roleName.equals(RoleEum.ADMIN.name())) {
				return new ModelAndView("AdminDashboard");
			} else {
				return new ModelAndView("Dashboard");
			}
		} catch (Exception e) {
			model.addAttribute("msg", "Either email or Password is incorrect, please try again.");
			e.printStackTrace();
			System.out.println(e);
			return new ModelAndView("login");
		}
	}

	@PostMapping(value = "/pkycupload")
	public ModelAndView pkyc(Model model, HttpServletRequest request, @RequestParam MultipartFile[] fileUpload) {
		try {
			HttpSession session = request.getSession();
			if (fileUpload[0] != null) {
				userController.saveOrUpdateUserDoc(fileUpload[0], DocType.DOCUMENT_PAN);
			}
			if (fileUpload[1] != null) {
				userController.saveOrUpdateUserDoc(fileUpload[1], DocType.DOCUMENT_AADHAR);
			}
			model.addAttribute("msg", "Pan & Aadhar card successfully uploaded");
			
			List<UserDoc> getUserAllKyc=userController.getUserAllKyc();
			if(getUserAllKyc.size()==3 || getUserAllKyc.size()>3)
			{
				session.setAttribute("kyc", "Done");
			}else
			{
				session.setAttribute("kyc", "Pending");
			}
			
			return new ModelAndView("Bkyc");
		} catch (Exception e) {
			return new ModelAndView("Pkyc");
		}
	}

	@PostMapping(value = "/bkycupload")
	public ModelAndView bkyc(Model model, HttpServletRequest request, @RequestParam MultipartFile fileUpload,
			@ModelAttribute UserBusinessKycRequestModal userBusinessKycRequestModal) {
		try {
			if (fileUpload != null) {
				userController.saveOrUpdateUserDoc(fileUpload, DocType.DOCUMENT_GST);
			}
			HttpSession session = request.getSession();
			
			userController.saveOrUpdateUserBusnessKyc(userBusinessKycRequestModal);
			
			UserDoc userDoc = userController.getUserDoc(DocType.DOCUMENT_PAN);
			UserDoc userDocs = userController.getUserDoc(DocType.DOCUMENT_AADHAR);
			UserDoc userDocx = userController.getUserDoc(DocType.DOCUMENT_GST);

			session.setAttribute("userDoc", userDoc);
			session.setAttribute("userDocs", userDocs);
			session.setAttribute("userDocx", userDocx);
			
			List<UserDoc> getUserAllKyc=userController.getUserAllKyc();
			if(getUserAllKyc.size()==3 || getUserAllKyc.size()>3)
			{
				session.setAttribute("kyc", "Done");
			}else
			{
				session.setAttribute("kyc", "Pending");
			}
			
			model.addAttribute("msg", "Business Details Succesfully Uploaded");
			return new ModelAndView("Dashboard");
		} catch (Exception e) {
			return new ModelAndView("Pkyc");
		}
	}
	
	
	@PostMapping(value = "/updateEmailpass")
	public ModelAndView updateEmailpass( Model model,
			HttpServletRequest request) {
		String email=request.getParameter("userEmail");
		String password=request.getParameter("password");
		String respose = userController.changeEmailOrPassword(email,password);
		model.addAttribute("msg", respose);
		if (respose != null && respose.equalsIgnoreCase("Email Or Password changed")) {
			return new ModelAndView("Setting");
		}
		else
		{	return new ModelAndView("Setting");
		
		}
		
	}
}
