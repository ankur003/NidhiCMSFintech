/**
 * 
 */
package com.nidhi.cms.controller.fe;

import static com.nidhi.cms.constants.JwtConstants.AUTH_TOKEN;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nidhi.cms.constants.ApiConstants;
import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.controller.LoginController;
import com.nidhi.cms.controller.OtpController;
import com.nidhi.cms.controller.UserController;
import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.Role;
import com.nidhi.cms.domain.SystemPrivilege;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserAccountStatement;
import com.nidhi.cms.domain.UserBankDetails;
import com.nidhi.cms.domain.UserDoc;
import com.nidhi.cms.domain.UserPaymentMode;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.modal.request.LoginRequestModal;
import com.nidhi.cms.modal.request.SubAdminCreateModal;
import com.nidhi.cms.modal.request.UserAccountActivateModal;
import com.nidhi.cms.modal.request.UserBankModal;
import com.nidhi.cms.modal.request.UserBusinessKycRequestModal;
import com.nidhi.cms.modal.request.UserCreateModal;
import com.nidhi.cms.modal.request.UserPaymentModeModal;
import com.nidhi.cms.modal.request.UserPaymentModeModalReqModal;
import com.nidhi.cms.modal.request.UserRequestFilterModel;
import com.nidhi.cms.modal.request.UserUpdateModal;
import com.nidhi.cms.modal.request.VerifyOtpRequestModal;
import com.nidhi.cms.modal.response.UserBusinessKycModal;
import com.nidhi.cms.service.UserService;

/**
 * @author Devendra Gread
 *
 */

@RestController
@RequestMapping(value = ApiConstants.API_VERSION)
public class UserControllerFe {

	@Autowired
	private UserController userController;

	@Autowired
	private OtpController otpController;

	@Autowired
	private LoginController loginController;
	
	@Autowired
	private UserService userservice;

	@PostMapping(value = "/user")
	public ModelAndView userSave(@Valid @ModelAttribute UserCreateModal userCreateModal, Model model,
			HttpServletRequest request) {
		userCreateModal.setIsCreatedByAdmin(false);
		String respose = userController.userSignUp(userCreateModal);
		model.addAttribute("msg", respose);
		if (respose != null && respose.equalsIgnoreCase("Either Email or Mobile already Exist.")) {
			return new ModelAndView("Signup");
		} else {
			return new ModelAndView("VerifyOtp");

		}

	}

	@PostMapping(value = "/otp/verify")
	public ModelAndView userSave(@Valid @ModelAttribute VerifyOtpRequestModal verifyOtpRequestModal, Model model,
			HttpServletRequest request) {
		String respose = otpController.verifyOTP(verifyOtpRequestModal);
		
		if (respose.equalsIgnoreCase("Either email or mobile OTP is incorrect, please try again."))
			{
			model.addAttribute("msgs", respose);
			return new ModelAndView("VerifyOtp");
			}
		if (respose.equalsIgnoreCase("Otp already verified, please login"))
			{model.addAttribute("msg", respose);
			return new ModelAndView("login");}
		if (respose.equalsIgnoreCase("Otp expired, please signUp again."))
			{model.addAttribute("msgs", respose);
			return new ModelAndView("Signup");}
		if (respose.equalsIgnoreCase("Otp verified, please proceed with login."))
			{model.addAttribute("msg", respose);
			return new ModelAndView("login");
			
			}

		return null;
	}

	@PostMapping(value = "/login")
	public ModelAndView login(@Valid @ModelAttribute LoginRequestModal loginRequestModal, Model model,
			HttpServletRequest request) {
		try {
			
			User usr=userservice.getUserByUserEmailOrMobileNumber(loginRequestModal.getUsername(), loginRequestModal.getUsername());
			if(usr!=null && BooleanUtils.isTrue( usr.getIsUserCreatedByAdmin()) && BooleanUtils.isFalse(usr.getIsUserVerified()))
			{
				return new ModelAndView("VerifyOtp");
			}
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
			UserBusinessKycModal bkyc= userController.getUserBusnessKyc();
			session.setAttribute("bkyc", bkyc);
			UserBankDetails bank= userController.getUserBankDetails(userLoginDetails.getUserUuid());
					session.setAttribute("bank", bank);
					
			String roleName = StringUtils.EMPTY;
			for (Role roles : userLoginDetails.getRoles()) {
				roleName = roles.getName().name();
			}

			List<UserDoc> getUserAllKyc = userController.getUserAllKyc();

//			if (!getUserAllKyc.isEmpty() && getUserAllKyc.size() == 3 || getUserAllKyc.size() > 3) {
//				session.setAttribute("kyc", "Done");
//			} else {
//				session.setAttribute("kyc", "Pending");
//			}

			if (authtoken != null && roleName.equals(RoleEum.ADMIN.name())) {
				return new ModelAndView("AdminDashboard");
			} else {
				return new ModelAndView("Dashboard");
			}
			
		} catch (Exception e) {
			model.addAttribute("msgs", "Either email or Password is incorrect, please try again.");
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

			List<UserDoc> getUserAllKyc = userController.getUserAllKyc();
			if (getUserAllKyc.size() == 3 || getUserAllKyc.size() > 3) {
				session.setAttribute("kyc", "Done");
			} else {
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

//			List<UserDoc> getUserAllKyc = userController.getUserAllKyc();
//			if (getUserAllKyc.size() == 3 || getUserAllKyc.size() > 3) {
//				session.setAttribute("kyc", "Done");
//			} else {
//				session.setAttribute("kyc", "Pending");
//			}

			model.addAttribute("msg", "Business Details Succesfully Uploaded");
			return new ModelAndView("UserBank");
		} catch (Exception e) {
			return new ModelAndView("Pkyc");
		}
	}

	//bank 
	@PostMapping(value = "/user-bank-account")
	public ModelAndView saveOrUpdateUserBankDetail(Model model,@ModelAttribute UserBankModal userBankModal) {
		UserBankDetails bank=userController.saveOrUpdateUserBankDetails(userBankModal);
		if(bank!=null)
		{
			model.addAttribute("msg", "Bank Details Succesfully Uploaded");
			return new ModelAndView("Dashboard");
		}
		else
		{
			model.addAttribute("msg", "Business Details Not Uploaded");
			return new ModelAndView("Dashboard");
		}
		
	}
	
	
	@PostMapping(value = "/updateEmailpass")
	public ModelAndView updateEmailpass(Model model, HttpServletRequest request) {
		User users=null;
		String email = request.getParameter("userEmail");
		String password = request.getParameter("password");
		String respose = userController.changeEmailOrPassword(email, password);
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String fullName = request.getParameter("fullName");
		String dob=request.getParameter("dob");
		
		UserUpdateModal userUpdateModal=new UserUpdateModal();
		userUpdateModal.setFirstName(firstName);
		userUpdateModal.setLastName(lastName);
		userUpdateModal.setFullName(fullName);
		userUpdateModal.setDob(dob);
			users=userController.updateUserDetails(userUpdateModal);
		model.addAttribute("msg", "Information Updated");
		model.addAttribute("user",users);
		if(request.getParameter("utype").equalsIgnoreCase("adm"))
       {
    	   return new ModelAndView("AdminSetting");
	   }
		else {
			return new ModelAndView("Setting");
		}

	}

	@PostMapping(value = "/get-user-account-statement")
	public ModelAndView getUserAccountStatementService(Model model, HttpServletRequest request) {
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		List<UserAccountStatement> userAccountStatement = userController.getUserAccountStatementService(fromDate,
				toDate);
		if (!CollectionUtils.isEmpty(userAccountStatement)) {
			model.addAttribute("fromDate", fromDate);
			model.addAttribute("toDate", toDate);
			model.addAttribute("userAccountStatement", userAccountStatement);
			model.addAttribute("init", true);
		} else {
			model.addAttribute("init", false);
		}
		return new ModelAndView("AccountStatement");
	}

	@GetMapping(value = "/user-wallet")
	public ModelAndView getUserWalletDetails(@RequestParam("userUuid") String userUuid, Model model) {
		UserWallet userWallet = userController.getUserWallet(userUuid);
		if (null != userWallet) {
			model.addAttribute("userWallet", userWallet);
			return new ModelAndView("FundAccount");
		}
		return new ModelAndView("FundAccount");
	}

	@GetMapping(value = "/get-all-user")
	public ModelAndView getAllClint(Model model, HttpServletRequest request) {
		UserRequestFilterModel userRequestFilterModel = new UserRequestFilterModel();
		userRequestFilterModel.setPage(1);
		userRequestFilterModel.setLimit(Integer.MAX_VALUE);
		userRequestFilterModel.setIsAdmin(false);
		userRequestFilterModel.setIsSubAdmin(false);
		Map<String, Object> users = userController.getAllUser(userRequestFilterModel);
		if (users != null) {
			model.addAttribute("userList", users.get("data"));
			model.addAttribute("init", true);
			return new ModelAndView("AdminPendingClient");
		} else {
			model.addAttribute("init", false);
		}
		return new ModelAndView("AdminPendingClient");
	}

	@GetMapping(value = "/get-user-docs")
	public ModelAndView getUserDocs(@RequestParam("userUuid") String userUuid, @RequestParam("docType") String docType,
			Model model, HttpServletRequest request, HttpSession session) {
		UserDoc doc = null;

		if (docType.equalsIgnoreCase("PAN")) {
			doc = userController.getUserDocbyUserId(DocType.DOCUMENT_PAN, userUuid);
		}
		if (docType.equalsIgnoreCase("AADHAR")) {
			doc = userController.getUserDocbyUserId(DocType.DOCUMENT_AADHAR, userUuid);
		}
		if (docType.equalsIgnoreCase("GST")) {
			doc = userController.getUserDocbyUserId(DocType.DOCUMENT_GST, userUuid);
		}

		if (doc != null)
			session.setAttribute("doc", doc.getData());
		else
			session.setAttribute("doc", null);
		return new ModelAndView("docsView");
	}

	@GetMapping(value = "/kyc-auth")
	public ModelAndView approveOrRejectKyc(@RequestParam("userUuid") String userUuid,
			@RequestParam("kycResponse") Boolean kycResponse, Model model, HttpServletRequest request) {
		userController.approveOrDisApproveKyc(userUuid, kycResponse, DocType.DOCUMENT_PAN);
		userController.approveOrDisApproveKyc(userUuid, kycResponse, DocType.DOCUMENT_AADHAR);
		userController.approveOrDisApproveKyc(userUuid, kycResponse, DocType.DOCUMENT_GST);

		UserRequestFilterModel userRequestFilterModel = new UserRequestFilterModel();
		userRequestFilterModel.setPage(1);
		userRequestFilterModel.setLimit(Integer.MAX_VALUE);
		userRequestFilterModel.setIsAdmin(false);
		userRequestFilterModel.setIsSubAdmin(false);
		
		Map<String, Object> users = userController.getAllUser(userRequestFilterModel);
		if (users != null) {
			User user=userservice.getUserByUserUuid(userUuid);
			model.addAttribute("msg", user.getFullName() ==null ? "record has been verified" : user.getFullName() + " has been verified");
			model.addAttribute("userList", users.get("data"));
			model.addAttribute("init", true);
			return new ModelAndView("AdminPendingClient");
		} else {
			model.addAttribute("init", false);
		}
		return new ModelAndView("AdminPendingClient");
	}

	@PostMapping(value = "/userbyAdmin")
	public ModelAndView userSavebyadmin(@Valid @ModelAttribute UserCreateModal userCreateModal, Model model,
			HttpServletRequest request) {
		userCreateModal.setIsCreatedByAdmin(true);
		String respose = userController.userSignUp(userCreateModal);
		model.addAttribute("msg", respose);
		if (respose != null && respose.equalsIgnoreCase("username : user already exist by mobile or email.")) {
			return new ModelAndView("AdminCreateNew");
		} else {
			model.addAttribute("msg", "user has been created and verified");
			return new ModelAndView("AdminCreateNew");

		}

	}

	@PostMapping(value = "/get-user-search")
	public ModelAndView getAllClintss(Model model, HttpServletRequest request) {
		UserRequestFilterModel userRequestFilterModel = new UserRequestFilterModel();
		userRequestFilterModel.setPage(1);
		
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String userEmail = request.getParameter("userEmail");
		String username = request.getParameter("username");

	    userRequestFilterModel.setFirstName(firstName);
	    userRequestFilterModel.setLastName(lastName);
	    userRequestFilterModel.setUserEmail(userEmail);
	    userRequestFilterModel.setUsername(username);
		userRequestFilterModel.setLimit(Integer.MAX_VALUE);
		userRequestFilterModel.setIsAdmin(false);
		userRequestFilterModel.setIsSubAdmin(false);
		Map<String, Object> users = userController.getAllUser(userRequestFilterModel);
		if (users != null) {
			model.addAttribute("userList", users.get("data"));
			model.addAttribute("init", true);
			
			model.addAttribute("firstName", firstName);
			model.addAttribute("lastName", lastName);
			model.addAttribute("userEmail", userEmail);
			model.addAttribute("username", username);
			return new ModelAndView("AdminmanageClint");
		} else {
			model.addAttribute("init", false);
		}
		return new ModelAndView("AdminmanageClint");
	}

	@PostMapping(value = "/get-user-whitesearch")
	public ModelAndView getWhitlist(Model model, HttpServletRequest request) {
		UserRequestFilterModel userRequestFilterModel = new UserRequestFilterModel();
		userRequestFilterModel.setPage(1);
		
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String userEmail = request.getParameter("userEmail");
		String username = request.getParameter("username");

	    userRequestFilterModel.setFirstName(firstName);
	    userRequestFilterModel.setLastName(lastName);
	    userRequestFilterModel.setUserEmail(userEmail);
	    userRequestFilterModel.setUsername(username);
		userRequestFilterModel.setLimit(Integer.MAX_VALUE);
		userRequestFilterModel.setIsAdmin(false);
		userRequestFilterModel.setIsSubAdmin(false);
		Map<String, Object> users = userController.getAllUser(userRequestFilterModel);
		if (users != null) {
			model.addAttribute("userList", users.get("data"));
			model.addAttribute("init", true);
			
			model.addAttribute("firstName", firstName);
			model.addAttribute("lastName", lastName);
			model.addAttribute("userEmail", userEmail);
			model.addAttribute("username", username);
			return new ModelAndView("AdminWhiteListpage");
		} else {
			model.addAttribute("init", false);
		}
		return new ModelAndView("AdminWhiteListpage");
	}
	
	@PostMapping(value = "/get-user-productFeature")
	public ModelAndView productFeaturesearch(Model model, HttpServletRequest request) {
		UserRequestFilterModel userRequestFilterModel = new UserRequestFilterModel();
		userRequestFilterModel.setPage(1);
		
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String userEmail = request.getParameter("userEmail");
		String username = request.getParameter("username");

	    userRequestFilterModel.setFirstName(firstName);
	    userRequestFilterModel.setLastName(lastName);
	    userRequestFilterModel.setUserEmail(userEmail);
	    userRequestFilterModel.setUsername(username);
		userRequestFilterModel.setLimit(Integer.MAX_VALUE);
		userRequestFilterModel.setIsAdmin(false);
		userRequestFilterModel.setIsSubAdmin(false);
		Map<String, Object> users = userController.getAllUser(userRequestFilterModel);
		if (users != null) {
			model.addAttribute("userList", users.get("data"));
			model.addAttribute("init", true);
			
			model.addAttribute("firstName", firstName);
			model.addAttribute("lastName", lastName);
			model.addAttribute("userEmail", userEmail);
			model.addAttribute("username", username);
			return new ModelAndView("AdminProductFeaturing");
		} else {
			model.addAttribute("init", false);
		}
		return new ModelAndView("AdminProductFeaturing");
	}
	
	
	@PostMapping(value = "/user-payment-mode")
	public ModelAndView userPaymentModes(@ModelAttribute UserPaymentModeModal userPaymentModeModal,Model model, HttpServletRequest request) {
		     userController.userPaymentMode(userPaymentModeModal);
		     model.addAttribute("msg", "payment Mode has been added");
		return new ModelAndView("AdminProductFeaturing");
	}
	
	@PostMapping(value = "/admin-whitelist-add")
	public ModelAndView adminwhitelistAdd(Model model, HttpServletRequest request) {
		String userUuid = request.getParameter("userUuid");
		String ip = request.getParameter("ip");
		boolean flag = false;
		try {
			flag = userController.apiWhiteListing(userUuid, ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (flag) {
			model.addAttribute("msg", "IP has been added");
		} else
			model.addAttribute("msgs", "IP didn't add. ");
		return new ModelAndView("AdminWhiteListpage");
	}
	
	@GetMapping(value = "/get-loggedin-info")
	public ModelAndView loggedIninfo(@RequestParam("userUuid") String userUuid,@RequestParam("type") String type,
			Model model, HttpServletRequest request, HttpSession session) {
		User user = userservice.getUserByUserUuid(userUuid);
		model.addAttribute("user",user);
		if(type.equalsIgnoreCase("a"))
		return new ModelAndView("AdminSetting");
		else
			return new ModelAndView("setting");
	}
	
	
	@GetMapping(value = "/get-privilegeList")
	public ModelAndView getListOfPrivilege(@RequestParam("userUuid") String userUuid,Model model, HttpServletRequest request, HttpSession session) {
		User user = userservice.getUserByUserUuid(userUuid);
		model.addAttribute("user",user);
		List<SystemPrivilege> list= userController.getSystemPrivlegeList();
		model.addAttribute("privilegeList",list);
		model.addAttribute("init",list.size());
		return new ModelAndView("AdminAddPrivilege");
	}
	
	
	@PostMapping(value = "/admin-add-privilege")
	public ModelAndView addAccessPrivilegesIntoSystem(Model model, HttpServletRequest request) {
		String privilegeName = request.getParameter("privilegeName");
		SystemPrivilege systemPrivilege = null;
			systemPrivilege = userController.addAccessPrivilegesIntoSystem(privilegeName);
			List<SystemPrivilege> list= userController.getSystemPrivlegeList();
			model.addAttribute("privilegeList",list);
			model.addAttribute("init",list.size());
		if (systemPrivilege!=null) {
			model.addAttribute("msg", "System Privilege has been added");
		} else
			model.addAttribute("msgs", "Privilege Name didn't add. ");
		return new ModelAndView("AdminAddPrivilege");
	}
	
//	deleteAccessPrivilegesIntoSystem
	@PostMapping(value = "/admin-update-privilege")
	public ModelAndView updateAccessPrivilegesIntoSystem(Model model, HttpServletRequest request) {
		String privilegeName = request.getParameter("privilegeName");
		String nprivilegeName = request.getParameter("nprivilegeName");
		SystemPrivilege systemPrivilege = null;
			systemPrivilege = userController.updateAccessPrivilegesIntoSystem(privilegeName,nprivilegeName);
			List<SystemPrivilege> list= userController.getSystemPrivlegeList();
			model.addAttribute("privilegeList",list);
			model.addAttribute("init",list.size());
		if (systemPrivilege!=null) {
			model.addAttribute("msg", "System Privilege has been Updated");
		} else
			model.addAttribute("msgs", "Privilege Name didn't Update. ");
		return new ModelAndView("AdminAddPrivilege");
	}

	
	@GetMapping(value = "/deletePrivlege")
	public ModelAndView deleteAccessPrivilegesIntoSystem(@RequestParam("privilegeName") String privilegeName,Model model, HttpServletRequest request, HttpSession session) {
		userController.deleteAccessPrivilegesIntoSystem(privilegeName);
		List<SystemPrivilege> list= userController.getSystemPrivlegeList();
		model.addAttribute("privilegeList",list);
		model.addAttribute("init",list.size());
		return new ModelAndView("AdminAddPrivilege");
	}
	
	@GetMapping(value = "/getPriviligebyid")
	public ModelAndView getPriviligebyid(@RequestParam("id") Long id,Model model, HttpServletRequest request, HttpSession session) {
		SystemPrivilege systemPrivilege = userController.getPriviligebyid(id);
		model.addAttribute("systemPrivilege",systemPrivilege);
		return new ModelAndView("Adminupdateprivilege");
	}
	
	@PostMapping(value = "/subadmin-add")
	public ModelAndView userSubadmindmin(@Valid @ModelAttribute SubAdminCreateModal subAdminCreateModal, Model model,
			HttpServletRequest request) {
			model.addAttribute("msg", "Subadmin has been created");
			User createSubAdmin=userController.createSubAdmin(subAdminCreateModal);
			List<SystemPrivilege> list= userController.getSystemPrivlegeList();
			model.addAttribute("privilegeList",list);
			return new ModelAndView("SubAdminAccount");

		

	}
	
	
	
	@PostMapping(value = "/get-subadmin")
	public ModelAndView getSubadmin(Model model, HttpServletRequest request) {
		UserRequestFilterModel userRequestFilterModel = new UserRequestFilterModel();
		userRequestFilterModel.setPage(1);
		
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String userEmail = request.getParameter("userEmail");
		String username = request.getParameter("username");

	    userRequestFilterModel.setFirstName(firstName);
	    userRequestFilterModel.setLastName(lastName);
	    userRequestFilterModel.setUserEmail(userEmail);
	    userRequestFilterModel.setUsername(username);
	    userRequestFilterModel.setIsSubAdmin(true);
		userRequestFilterModel.setLimit(Integer.MAX_VALUE);
		Map<String, Object> users = userController.getAllUser(userRequestFilterModel);
		if (users != null) {
			model.addAttribute("userList", users.get("data"));
			model.addAttribute("init", true);
			
			model.addAttribute("firstName", firstName);
			model.addAttribute("lastName", lastName);
			model.addAttribute("userEmail", userEmail);
			model.addAttribute("username", username);
			return new ModelAndView("SubAdminAccountUpdate");
		} else {
			model.addAttribute("init", false);
		}
		return new ModelAndView("SubAdminAccountUpdate");
	}
	
	
	@GetMapping(value = "/get-userDetails")
	public ModelAndView userDetails(@RequestParam("userUuid") String userUuid,Model model, HttpServletRequest request, HttpSession session) {
		User user = userservice.getUserByUserUuid(userUuid);
		String[] userPrivilages = user.getPrivilageNames().split(",");
		model.addAttribute("privilegeList",userPrivilages);
		model.addAttribute("user",user);
		List<SystemPrivilege> list= userController.getSystemPrivlegeList();
		model.addAttribute("listprivlege",list);
		return new ModelAndView("SubAdminAccountUpdate");
	}
	
	@PostMapping(value = "/subadminUpdate")
	public ModelAndView subadminUpdate(Model model, HttpServletRequest request) {
		User users=null;
		String email = request.getParameter("userEmail");
		String password = request.getParameter("password");
		String respose = userController.changeEmailOrPassword(email, password);
		String mobileNumber = request.getParameter("mobileNumber");
		String fullName = request.getParameter("fullName");
		String[] privilageNames = request.getParameterValues("privilageNames");
		
		UserUpdateModal userUpdateModal=new UserUpdateModal();
		userUpdateModal.setFullName(fullName);
		userUpdateModal.setUserPrivileges(privilageNames);
		User user = userservice.getUserByUserUuid(request.getParameter("userUuid"));
			users=userController.updateSubadminuser(user,userUpdateModal);
		model.addAttribute("msg", "Information Updated");
		model.addAttribute("user",users);
		String[] userPrivilages = user.getPrivilageNames().split(",");
		model.addAttribute("privilegeList",userPrivilages);
		model.addAttribute("user",user);
		List<SystemPrivilege> list= userController.getSystemPrivlegeList();
		model.addAttribute("listprivlege",list);
		return new ModelAndView("SubAdminAccountUpdate");

	}
	
	
	
	@GetMapping(value = "/get-kyc-data")
	public ModelAndView kycData(@RequestParam("userUuid") String userUuid,Model model, HttpServletRequest request)
	{
		User user = userservice.getUserByUserUuid(userUuid);
			UserDoc	pan = userController.getUserDocbyUserId(DocType.DOCUMENT_PAN, userUuid);
			UserDoc	aadhar = userController.getUserDocbyUserId(DocType.DOCUMENT_AADHAR, userUuid);
		UserDoc	gst = userController.getUserDocbyUserId(DocType.DOCUMENT_GST, userUuid);
		UserBankDetails bank= userController.getUserBankDetails(userUuid);	
		UserBusinessKycModal business=userController.getUserBusnessKybyid(userUuid);
		
		model.addAttribute("pan",pan);
		model.addAttribute("aadhar",aadhar);
		model.addAttribute("gst",gst);
		model.addAttribute("bank",bank);
		model.addAttribute("bkyc",business);
		model.addAttribute("user",user);
		
		if(pan==null && aadhar==null && gst==null && bank==null && business==null)
		{
			model.addAttribute("msgs","No data found against "+ user.getFullName());
			return new ModelAndView("AdminPendingClient");
		}
		
		return new ModelAndView("userCompInfo");
	}
	
	@GetMapping(value = "/get-div-kyc")
	public ModelAndView kycDivData(@RequestParam("userUuid") String userUuid,@RequestParam("id") int id,Model model, HttpServletRequest request)
	{
		
		User user = userservice.getUserByUserUuid(userUuid);
		UserDoc	pan = userController.getUserDocbyUserId(DocType.DOCUMENT_PAN, userUuid);
		UserDoc	aadhar = userController.getUserDocbyUserId(DocType.DOCUMENT_AADHAR, userUuid);
		UserDoc	gst = userController.getUserDocbyUserId(DocType.DOCUMENT_GST, userUuid);
		UserBankDetails bank= userController.getUserBankDetails(userUuid);	
		UserBusinessKycModal business=userController.getUserBusnessKybyid(userUuid);
		model.addAttribute("id",id);
		model.addAttribute("pan",pan);
		model.addAttribute("aadhar",aadhar);
		model.addAttribute("gst",gst);
		model.addAttribute("bank",bank);
		model.addAttribute("bkyc",business);
		model.addAttribute("user",user);
		List<UserPaymentMode> paylist=userController.getUserAllPaymentModeDetails(userUuid);
		model.addAttribute("paylist",paylist);
		
		for (UserPaymentMode userPaymentMode : paylist) 
		{
			if(userPaymentMode.getPaymentMode()==PaymentMode.RTG)
			{
				model.addAttribute("RTGfeePercent",userPaymentMode.getFeePercent());
				model.addAttribute("rtgsstatus",userPaymentMode.getIsActive());
			}
			if(userPaymentMode.getPaymentMode()==PaymentMode.RGS)
			{
				model.addAttribute("RGSfeePercent",userPaymentMode.getFeePercent());
				model.addAttribute("neftstatus",userPaymentMode.getIsActive());
			}
			if(userPaymentMode.getPaymentMode()==PaymentMode.IFS)
			{
				model.addAttribute("IFSfeePercent",userPaymentMode.getFeePercent());
				model.addAttribute("impsstatus",userPaymentMode.getIsActive());
			}
		}
		
		
		if(pan==null && aadhar==null && gst==null && bank==null && business==null)
		{
			model.addAttribute("msgs","No data found");
			return new ModelAndView("UserUpdateAdmin");
		}
		
		return new ModelAndView("UserUpdateAdmin");
	}

	
	@PostMapping(value = "/pkycupload-Update")
	public ModelAndView pkycUpdate(Model model, HttpServletRequest request, @RequestParam MultipartFile[] fileUpload) {
		try {
			String userUuid=request.getParameter("userUuid");
			User user = userservice.getUserByUserUuid(userUuid);
			if (fileUpload[0] != null) {
				userController.saveOrUpdateUserDoc2(fileUpload[0], DocType.DOCUMENT_PAN,user);
			}
			if (fileUpload[1] != null) {
				userController.saveOrUpdateUserDoc2(fileUpload[1], DocType.DOCUMENT_AADHAR,user);
			}
			model.addAttribute("msg", "Pan & Aadhar card successfully Updated");
			model.addAttribute("id",1);
			
			UserDoc	pan = userController.getUserDocbyUserId(DocType.DOCUMENT_PAN, userUuid);
			UserDoc	aadhar = userController.getUserDocbyUserId(DocType.DOCUMENT_AADHAR, userUuid);
			UserDoc	gst = userController.getUserDocbyUserId(DocType.DOCUMENT_GST, userUuid);
			UserBankDetails bank= userController.getUserBankDetails(userUuid);	
			UserBusinessKycModal business=userController.getUserBusnessKybyid(userUuid);
			model.addAttribute("pan",pan);
			model.addAttribute("aadhar",aadhar);
			model.addAttribute("gst",gst);
			model.addAttribute("bank",bank);
			model.addAttribute("bkyc",business);
			model.addAttribute("user",user);
			return new ModelAndView("UserUpdateAdmin");
		} catch (Exception e) {
			
		}
		return null;
	}

	@PostMapping(value = "/bkycupload-update")
	public ModelAndView bkycUpdate(Model model, HttpServletRequest request, @RequestParam MultipartFile fileUpload,
			@ModelAttribute UserBusinessKycRequestModal userBusinessKycRequestModal) {
		try {
			String userUuid=request.getParameter("userUuid");
			User user = userservice.getUserByUserUuid(userUuid);
			if (fileUpload != null) {
				userController.saveOrUpdateUserDoc2(fileUpload, DocType.DOCUMENT_GST,user);
			}

			userController.saveOrUpdateUserBusnessKyc2(userBusinessKycRequestModal,user);
			model.addAttribute("msg", "Business Details Succesfully Update");
			model.addAttribute("id",2);
			
			UserDoc	pan = userController.getUserDocbyUserId(DocType.DOCUMENT_PAN, userUuid);
			UserDoc	aadhar = userController.getUserDocbyUserId(DocType.DOCUMENT_AADHAR, userUuid);
			UserDoc	gst = userController.getUserDocbyUserId(DocType.DOCUMENT_GST, userUuid);
			UserBankDetails bank= userController.getUserBankDetails(userUuid);	
			UserBusinessKycModal business=userController.getUserBusnessKybyid(userUuid);
			model.addAttribute("pan",pan);
			model.addAttribute("aadhar",aadhar);
			model.addAttribute("gst",gst);
			model.addAttribute("bank",bank);
			model.addAttribute("bkyc",business);
			model.addAttribute("user",user);
			return new ModelAndView("UserUpdateAdmin");
		} catch (Exception e) {
			return new ModelAndView("UserUpdateAdmin");
		}
	}

	//bank 
	@PostMapping(value = "/user-bank-account-update")
	public ModelAndView UpdateUserBankDetail(Model model,HttpServletRequest request,@ModelAttribute UserBankModal userBankModal) {
		String userUuid=request.getParameter("userUuid");
		User user = userservice.getUserByUserUuid(userUuid);
		UserBankDetails banks=userController.saveOrUpdateUserBankDetails2(userBankModal,user);
		if(banks!=null)
		{
			model.addAttribute("msg", "Bank Details Succesfully Updated");
			model.addAttribute("id",3);
			
			UserDoc	pan = userController.getUserDocbyUserId(DocType.DOCUMENT_PAN, userUuid);
			UserDoc	aadhar = userController.getUserDocbyUserId(DocType.DOCUMENT_AADHAR, userUuid);
			UserDoc	gst = userController.getUserDocbyUserId(DocType.DOCUMENT_GST, userUuid);
			UserBankDetails bank= userController.getUserBankDetails(userUuid);	
			UserBusinessKycModal business=userController.getUserBusnessKybyid(userUuid);
			model.addAttribute("pan",pan);
			model.addAttribute("aadhar",aadhar);
			model.addAttribute("gst",gst);
			model.addAttribute("bank",bank);
			model.addAttribute("bkyc",business);
			model.addAttribute("user",user);
			return new ModelAndView("UserUpdateAdmin");
		}
		else
		{
			model.addAttribute("msgs", "Bank Details Not Uploaded");
			return new ModelAndView("UserUpdateAdmin");
		}
		
	}
	
	@PostMapping(value = "/deactivateUser")
	public ModelAndView userActivateOrDeactivate2(Model model,HttpServletRequest request,@ModelAttribute UserAccountActivateModal userAccountActivateModal) {
		String userUuid=request.getParameter("userUuid");
		User user = userservice.getUserByUserUuid(userUuid);
		String reason=request.getParameter("reason");
	    String status=request.getParameter("acstatus");	
		boolean flag=false;
	    
	    if(status.equalsIgnoreCase("active"))
	    	flag=true;
	    
	    
		userController.userActivateOrDeactivate2(reason, flag, user);
		
		model.addAttribute("msg", "User Has been deactivated");
		model.addAttribute("id",4);
		
		UserDoc	pan = userController.getUserDocbyUserId(DocType.DOCUMENT_PAN, userUuid);
		UserDoc	aadhar = userController.getUserDocbyUserId(DocType.DOCUMENT_AADHAR, userUuid);
		UserDoc	gst = userController.getUserDocbyUserId(DocType.DOCUMENT_GST, userUuid);
		UserBankDetails bank= userController.getUserBankDetails(userUuid);	
		UserBusinessKycModal business=userController.getUserBusnessKybyid(userUuid);
		model.addAttribute("pan",pan);
		model.addAttribute("aadhar",aadhar);
		model.addAttribute("gst",gst);
		model.addAttribute("bank",bank);
		model.addAttribute("bkyc",business);
		model.addAttribute("user",user);
		return new ModelAndView("UserUpdateAdmin");
	}
	
	@PostMapping(value = "/add-Payment-mode")
	public ModelAndView  addpaymentmode(Model model,HttpServletRequest request,@ModelAttribute UserPaymentModeModalReqModal userPaymentModeModalReqModal) {
		String userUuid=request.getParameter("userUuid");
		userPaymentModeModalReqModal.setUserUuid(userUuid);
		
		String RTG=request.getParameter("RTG");
		String RTGfeePercent=request.getParameter("RTGfeePercent");
		String rtgsstatus=request.getParameter("rtgsstatus");
		boolean flag=false;
		if(rtgsstatus.equalsIgnoreCase("Active"))flag=true;
		userPaymentModeModalReqModal.setPaymentMode(PaymentMode.RTG);
		userPaymentModeModalReqModal.setFeePercent(Double.valueOf(RTGfeePercent));
		userPaymentModeModalReqModal.setActive(flag);
		 userController.saveOrUpdateUserPaymentMode(userPaymentModeModalReqModal);
		
		String IFS=request.getParameter("IFS");
		String IFSfeePercent=request.getParameter("IFSfeePercent");
		String impsstatus=request.getParameter("impsstatus");
		boolean flag1=false;
		if(impsstatus.equalsIgnoreCase("Active"))flag1=true;
		userPaymentModeModalReqModal.setPaymentMode(PaymentMode.IFS);
		userPaymentModeModalReqModal.setFeePercent(Double.valueOf(IFSfeePercent));
		userPaymentModeModalReqModal.setActive(flag1);
		 userController.saveOrUpdateUserPaymentMode(userPaymentModeModalReqModal);
		
		String RGS=request.getParameter("RGS");
		String RGSfeePercent=request.getParameter("RGSfeePercent");
		String neftstatus=request.getParameter("neftstatus");
		
		boolean flag2=false;
		if(neftstatus.equalsIgnoreCase("Active"))flag2=true;
		userPaymentModeModalReqModal.setPaymentMode(PaymentMode.RGS);
		userPaymentModeModalReqModal.setFeePercent(Double.valueOf(RGSfeePercent));
		userPaymentModeModalReqModal.setActive(flag2);
		 userController.saveOrUpdateUserPaymentMode(userPaymentModeModalReqModal);
		 
		 
		 model.addAttribute("msg", "Payment mode Has been updated");
			model.addAttribute("id",5);
			
			UserDoc	pan = userController.getUserDocbyUserId(DocType.DOCUMENT_PAN, userUuid);
			UserDoc	aadhar = userController.getUserDocbyUserId(DocType.DOCUMENT_AADHAR, userUuid);
			UserDoc	gst = userController.getUserDocbyUserId(DocType.DOCUMENT_GST, userUuid);
			UserBankDetails bank= userController.getUserBankDetails(userUuid);	
			UserBusinessKycModal business=userController.getUserBusnessKybyid(userUuid);
			model.addAttribute("pan",pan);
			model.addAttribute("aadhar",aadhar);
			model.addAttribute("gst",gst);
			model.addAttribute("bank",bank);
			model.addAttribute("bkyc",business);
			model.addAttribute("user",userservice.getUserByUserUuid(userUuid));
			List<UserPaymentMode> paylist=userController.getUserAllPaymentModeDetails(userUuid);
			model.addAttribute("paylist",paylist);
			
			for (UserPaymentMode userPaymentMode : paylist) 
			{
				if(userPaymentMode.getPaymentMode()==PaymentMode.RTG)
				{
					model.addAttribute("RTGfeePercent",userPaymentMode.getFeePercent());
					model.addAttribute("rtgsstatus",userPaymentMode.getIsActive());
				}
				if(userPaymentMode.getPaymentMode()==PaymentMode.RGS)
				{
					model.addAttribute("RGSfeePercent",userPaymentMode.getFeePercent());
					model.addAttribute("neftstatus",userPaymentMode.getIsActive());
				}
				if(userPaymentMode.getPaymentMode()==PaymentMode.IFS)
				{
					model.addAttribute("IFSfeePercent",userPaymentMode.getFeePercent());
					model.addAttribute("impsstatus",userPaymentMode.getIsActive());
				}
			}
			return new ModelAndView("UserUpdateAdmin");
		
	}
}
