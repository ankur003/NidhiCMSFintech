/**
 * 
 */
package com.nidhi.cms.controller.fe;

import static com.nidhi.cms.constants.JwtConstants.AUTH_TOKEN;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nidhi.cms.constants.ApiConstants;
import com.nidhi.cms.constants.EmailTemplateConstants;
import com.nidhi.cms.constants.enums.ForgotPassType;
import com.nidhi.cms.constants.enums.KycStatus;
import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.constants.enums.PaymentModeFeeType;
import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.controller.LoginController;
import com.nidhi.cms.controller.OtpController;
import com.nidhi.cms.controller.UserController;
import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.Role;
import com.nidhi.cms.domain.SystemPrivilege;
import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserAccountStatement;
import com.nidhi.cms.domain.UserBankDetails;
import com.nidhi.cms.domain.UserDoc;
import com.nidhi.cms.domain.UserPaymentMode;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.domain.email.MailRequest;
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
import com.nidhi.cms.service.OtpService;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.service.email.EmailService;
import com.nidhi.cms.utils.Utility;

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
	private OtpService otpService;

	@Autowired
	private LoginController loginController;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private EmailService emailService;
	
	private static final String VERIFY_OTP = "VerifyOtp";
	private static final String OTP_UUID = "otpUuid";
	private static final String LOGIN = "login";
	
	@PostMapping(value = "/user")
	public ModelAndView userSave(@Valid @ModelAttribute UserCreateModal userCreateModal, Model model,
			HttpServletRequest request) throws Exception {
		userCreateModal.setIsCreatedByAdmin(false);
		String respose = userController.userSignUp(userCreateModal, model);
		model.addAttribute("msg", respose);
		if (respose != null && respose.equalsIgnoreCase("Either Email or Mobile already Exist.")) {
			return new ModelAndView("Signup");
		} else {
			return new ModelAndView(VERIFY_OTP);
		}

	}

	@PostMapping(value = "/otp/verify")
	public ModelAndView userSave(@Valid @ModelAttribute VerifyOtpRequestModal verifyOtpRequestModal, Model model,
			HttpServletRequest request) {
		String respose = otpController.verifyOTP(verifyOtpRequestModal);
		model.addAttribute(OTP_UUID, verifyOtpRequestModal.getOtpUuid());
		if (respose.equalsIgnoreCase("Either email or mobile OTP is incorrect, please try again."))
			{
			model.addAttribute("msgs", respose);
			return new ModelAndView(VERIFY_OTP);
			}
		if (respose.equalsIgnoreCase("Otp already verified, please login"))
			{model.addAttribute("msg", respose);
			return new ModelAndView(LOGIN);}
		if (respose.equalsIgnoreCase("Otp expired, please signUp again."))
			{model.addAttribute("msgs", respose);
			return new ModelAndView("Signup");}
		if (respose.equalsIgnoreCase("Otp verified, please proceed with login."))
			{model.addAttribute("msg", respose);
			return new ModelAndView(LOGIN);
			
			}

		return null;
	}

	
	
	
	@PostMapping(value = "/login")
	public ModelAndView login(@Valid @ModelAttribute LoginRequestModal loginRequestModal, Model model,
			HttpServletRequest request) throws Exception  {
		
		if(loginRequestModal.getOtpflag().equalsIgnoreCase("no"))
		{
		
		try {
			User userLoginDetails=userservice.getUserByUserEmailOrMobileNumber(loginRequestModal.getUsername(), loginRequestModal.getUsername());
			if(userLoginDetails!=null && BooleanUtils.isTrue( userLoginDetails.getIsUserCreatedByAdmin()) && BooleanUtils.isFalse(userLoginDetails.getIsUserVerified()))
			{
				String otpUuid = otpService.sendingOtpToUserCreatedByAdmin(userLoginDetails);
				if (otpUuid == null) {
					model.addAttribute("msgs", "OTP already sent, If you have lost the OTP, Please login after 30 min");
					return new ModelAndView(LOGIN);
				}
				model.addAttribute(OTP_UUID, otpUuid);
				return new ModelAndView(VERIFY_OTP);
			}
			HttpSession session = request.getSession();
			String authtoken = loginController.login(loginRequestModal);
			
			session.setAttribute(AUTH_TOKEN, authtoken);
			session.setAttribute("userLoginDetails", userLoginDetails);

			addUserKycDetailsInSession(userLoginDetails, session);
			
			UserBankDetails bank= userController.getUserBankDetails(userLoginDetails.getUserUuid());
			session.setAttribute("bank", bank);
					
			String roleName = StringUtils.EMPTY;
			for (Role roles : userLoginDetails.getRoles()) {
				roleName = roles.getName().name();
			}

			if (authtoken != null && roleName.equals(RoleEum.ADMIN.name())) {
				{
					request.getSession().setAttribute("homeScreen", "AdminDashboard");
					return new ModelAndView("AdminDashboard");
				}
			} else {
				if (!userLoginDetails.getIsSubAdmin()) {
					request.getSession().setAttribute("homeScreen", "Dashboard");
					return new ModelAndView("Dashboard");
				} else {
					request.getSession().setAttribute("homeScreen", "AdminDashboard");
					return new ModelAndView("AdminDashboard");
				}
			}

		} catch (Exception e) {
			model.addAttribute("msgs", "Either email or Password is incorrect, please try again.");
			return new ModelAndView(LOGIN);
		}
	} else if (loginRequestModal.getOtpflag().equalsIgnoreCase("yes"))

	{
		try {
			String emailorphone = request.getParameter("emailorphone");
			String byemail = null;
			String byphone = null;
			String flag = null;
			if (emailorphone.equalsIgnoreCase("EMAIL")) {
				byemail = request.getParameter("byemail");
				flag = userController.sendOTPForgotPassword(byemail, ForgotPassType.EMAIL);
				if (flag != null)
					model.addAttribute("msg", "OTP has sent to Your Email");
				else
					model.addAttribute("msgs", "Email Not Registered with us");
				model.addAttribute("byemail", byemail);
			} else {
				byphone = request.getParameter("byphone");
				flag = userController.sendOTPForgotPassword(byphone, ForgotPassType.PHONE);
				if (flag != null)
					model.addAttribute("msg", "OTP has sent to Your Phone");
				else
					model.addAttribute("msgs", "Mobile Number Not Registered with us");
				model.addAttribute("byphone", byphone);
			}

			if (flag != null) {
				model.addAttribute(OTP_UUID, flag);
				model.addAttribute("otp", "otp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	else if (loginRequestModal.getOtpflag().equalsIgnoreCase("verifytp")) {
		String otpUuid = request.getParameter(OTP_UUID);
		String emailphOtp = request.getParameter("emailphOtp");
		String newPass = request.getParameter("npassword");
		String confirmPass = request.getParameter("cpassword");
		boolean flag = false;
		String byemail = request.getParameter("byemail");
		if (byemail != null)
			model.addAttribute("byemail", byemail);
		String byphone = request.getParameter("byphone");
		if (byphone != null)
			model.addAttribute("byphone", byphone);

		flag = userController.matchOtpForgotPassword(emailphOtp, otpUuid, model.containsAttribute("byphone") ? "byPhone" : "byEmail");
		if (flag) {
			boolean Flag2 = false;
			if (byemail != null)
				Flag2 = userController.updatePasswordForgotPassword(byemail, newPass, confirmPass);
			if (byphone != null)
				Flag2 = userController.updatePasswordForgotPassword(byphone, newPass, confirmPass);

			if (Flag2) {
				model.addAttribute("msg", "Password has been Changed");
			} else {
				model.addAttribute("msgs", "due to some error password didn't change. Try after some time");
				model.addAttribute("otp", "otp");
			}
		} else {
			model.addAttribute("msgs", "OTP not Matched");
			model.addAttribute("otp", "otp");
		}
	}

	return new ModelAndView(LOGIN);
}

	private void addUserKycDetailsInSession(User userLoginDetails, HttpSession session) {
		if (userLoginDetails.getKycStatus().name().equals(KycStatus.PENDING.name())) {
			session.setAttribute("userDoc", null);
			session.setAttribute("userDocs", null);
			session.setAttribute("userDocx", null);
			session.setAttribute("bkyc", null);
			return;
		}
		UserDoc userDoc = userController.getUserDoc(DocType.DOCUMENT_PAN, userLoginDetails.getUserUuid());
		UserDoc userDocs = userController.getUserDoc(DocType.DOCUMENT_AADHAR, userLoginDetails.getUserUuid());
		UserDoc userDocx = userController.getUserDoc(DocType.DOCUMENT_GST, userLoginDetails.getUserUuid());
		session.setAttribute("userDoc", userDoc);
		session.setAttribute("userDocs", userDocs);
		session.setAttribute("userDocx", userDocx);
		
		UserBusinessKycModal bkyc= userController.getUserBusnessKyc(userLoginDetails.getUserUuid());
		session.setAttribute("bkyc", bkyc);
	}

	@PostMapping(value = "/pkycupload")
	public ModelAndView pkyc(Model model, HttpServletRequest request,  MultipartFile[] fileUpload) {
		try {
			HttpSession session = request.getSession();
			String userUuid=request.getParameter("userUuid");
			if (fileUpload[0] != null) {
				userController.saveOrUpdateUserDoc(fileUpload[0], DocType.DOCUMENT_PAN, userUuid);
			}
			if (fileUpload[1] != null) {
				userController.saveOrUpdateUserDoc(fileUpload[1], DocType.DOCUMENT_AADHAR, userUuid);
			}
			model.addAttribute("msg", "Pan & Aadhar card successfully uploaded");

			List<UserDoc> getUserAllKyc = userController.getUserAllKyc(userUuid);
			if (getUserAllKyc.size() == 3 || getUserAllKyc.size() > 3) {
				session.setAttribute("kyc", "Done");
			} else {
				session.setAttribute("kyc", "Pending");
			}
			User userLoginDetails = userController.getUserDetail(userUuid);
			session.setAttribute("userLoginDetails", userLoginDetails);

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
				userController.saveOrUpdateUserDoc(fileUpload, DocType.DOCUMENT_GST, userBusinessKycRequestModal.getUserUuid());
			}
			HttpSession session = request.getSession();

			ResponseEntity<Object> res = userController.saveOrUpdateUserBusnessKyc(userBusinessKycRequestModal);
			if (res.getStatusCode().equals(HttpStatus.PRECONDITION_FAILED) 
					|| res.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				model.addAttribute("msgs", "Duplicate Pan Number");
				return new ModelAndView("Bkyc");
			}
			UserDoc userDoc = userController.getUserDoc(DocType.DOCUMENT_PAN, userBusinessKycRequestModal.getUserUuid());
			UserDoc userDocs = userController.getUserDoc(DocType.DOCUMENT_AADHAR, userBusinessKycRequestModal.getUserUuid());
			UserDoc userDocx = userController.getUserDoc(DocType.DOCUMENT_GST, userBusinessKycRequestModal.getUserUuid());

			session.setAttribute("userDoc", userDoc);
			session.setAttribute("userDocs", userDocs);
			session.setAttribute("userDocx", userDocx);

			User userLoginDetails = userController.getUserDetail(userBusinessKycRequestModal.getUserUuid());
			session.removeAttribute("userLoginDetails");
			session.setAttribute("userLoginDetails", userLoginDetails);

			model.addAttribute("msg", "Business Details Succesfully Uploaded");
			return new ModelAndView("UserBank");
		} catch (Exception e) {
			return new ModelAndView("Pkyc");
		}
	}

	//bank 
	@PostMapping(value = "/user-bank-account")
	public ModelAndView saveOrUpdateUserBankDetail(Model model,@ModelAttribute UserBankModal userBankModal,HttpServletRequest request) {
		UserBankDetails bank=userController.saveOrUpdateUserBankDetails(userBankModal);
		HttpSession session = request.getSession();
		if(bank!=null)
		{
			User userLoginDetails = userController.getUserDetail(userBankModal.getUserUuid());
			session.setAttribute("userLoginDetails", userLoginDetails);
			model.addAttribute("msg", "Bank Details Succesfully Uploaded");
			request.getSession().setAttribute("homeScreen", "Dashboard");
			return new ModelAndView("Dashboard");
		}
		else
		{
			model.addAttribute("msg", "Business Details Not Uploaded");
			request.getSession().setAttribute("homeScreen", "Dashboard");
			return new ModelAndView("Dashboard");
		}
		
		
	}
	
	
	@PostMapping(value = "/updateEmailpass")
	public ModelAndView updateEmailpass(Model model, HttpServletRequest request) throws Exception {
		User users=null;
		String userUuid=request.getParameter("userUuid");
		String email = request.getParameter("userEmail");
		String password = request.getParameter("password");
		String respose = userController.changeEmailOrPassword(email, password, userUuid);
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String fullName = request.getParameter("fullName");
		String dob=request.getParameter("dob");
		
		UserUpdateModal userUpdateModal=new UserUpdateModal();
		userUpdateModal.setFirstName(firstName);
		userUpdateModal.setLastName(lastName);
		userUpdateModal.setFullName(fullName);
		userUpdateModal.setDob(dob);
		userUpdateModal.setUserUuid(userUuid);
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
	public ModelAndView getUserAccountStatementService(Model model, HttpServletRequest request,@RequestParam String userUuid) {
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		List<UserAccountStatement> userAccountStatement = userController.getUserAccountStatementService(fromDate,
				toDate,userUuid);
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
	public ModelAndView getAllClint(@RequestParam("userUuid") String userUuid,Model model, HttpServletRequest request) {
		UserRequestFilterModel userRequestFilterModel = new UserRequestFilterModel();
		userRequestFilterModel.setPage(1);
		userRequestFilterModel.setLimit(Integer.MAX_VALUE);
		userRequestFilterModel.setIsAdmin(false);
		userRequestFilterModel.setIsSubAdmin(false);
		userRequestFilterModel.setUserUuid(userUuid);
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
			@RequestParam("kycResponse") Boolean kycResponse,@RequestParam("adminuid") String adminuid, @RequestParam(name = "kycRejectReason", required = false) String kycRejectReason,
			Model model, HttpServletRequest request) {
	boolean a=	userController.approveOrDisApproveKyc(userUuid, kycResponse, kycRejectReason, DocType.DOCUMENT_PAN);
	boolean b=	userController.approveOrDisApproveKyc(userUuid, kycResponse, kycRejectReason, DocType.DOCUMENT_AADHAR);
	boolean c=	userController.approveOrDisApproveKyc(userUuid, kycResponse, kycRejectReason, DocType.DOCUMENT_GST);

		UserRequestFilterModel userRequestFilterModel = new UserRequestFilterModel();
		userRequestFilterModel.setPage(1);
		userRequestFilterModel.setLimit(Integer.MAX_VALUE);
		userRequestFilterModel.setIsAdmin(false);
		userRequestFilterModel.setIsSubAdmin(false);
		userRequestFilterModel.setUserUuid(adminuid);
		
		Map<String, Object> users = userController.getAllUser(userRequestFilterModel);
		if (users != null) {
			User user=userservice.getUserByUserUuid(userUuid);
			if(a && b && c)
			{
			model.addAttribute("msg", user.getFullName() ==null ? "record has been verified" : user.getFullName() + " has been verified");
			}
			else
			{
				model.addAttribute("msgs", user.getFullName() ==null ? "record has been verified" : user.getFullName() + " has not been verified");
			}
			model.addAttribute("userList", users.get("data"));
			model.addAttribute("init", true);
			return new ModelAndView("AdminPendingClient");
		} else {
			model.addAttribute("init", false);
		}
		return new ModelAndView("AdminPendingClient");
	}

	
	@PostMapping(value = "/kyc-authReject")
	public ModelAndView approveOrRejectKycReject(Model model, HttpServletRequest request, @RequestParam(required = false, name = "adminuid") String adminUuid) {
		
		String userUuid=request.getParameter("userUuid");
		String kycRejectReason=request.getParameter("kycRejectReason").trim();
		
		userController.approveOrDisApproveKyc(userUuid, false, kycRejectReason, DocType.DOCUMENT_PAN);
		userController.approveOrDisApproveKyc(userUuid, false, kycRejectReason, DocType.DOCUMENT_AADHAR);
		userController.approveOrDisApproveKyc(userUuid, false, kycRejectReason, DocType.DOCUMENT_GST);

		UserRequestFilterModel userRequestFilterModel = new UserRequestFilterModel();
		userRequestFilterModel.setPage(1);
		userRequestFilterModel.setLimit(Integer.MAX_VALUE);
		userRequestFilterModel.setIsAdmin(false);
		userRequestFilterModel.setIsSubAdmin(false);
		userRequestFilterModel.setUserUuid(userUuid);
		userRequestFilterModel.setAdminUuid(adminUuid);
		
		Map<String, Object> users = userController.getAllUser(userRequestFilterModel);
		if (users != null) {
			User user=userservice.getUserByUserUuid(userUuid);
			model.addAttribute("msgs", user.getFullName() ==null ? "record has been Rejected" : user.getFullName() + " has been Rejected");
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
			HttpServletRequest request) throws Exception {
		String userUuid = request.getParameter("userUuid");
		User user = userservice.getUserByUserUuid(userUuid);
		if (user.getIsAdmin() || user.getIsSubAdmin()) {
			userCreateModal.setIsCreatedByAdmin(true);
			String respose = userController.userSignUp(userCreateModal, model);
			model.addAttribute("msg", respose);
			if (respose != null && respose.equalsIgnoreCase("username : user already exist by mobile or email.")) {
				return new ModelAndView("AdminCreateNew");
			} else {
				model.addAttribute("msg", "user has been created");
				return new ModelAndView("AdminCreateNew");

			}
		} else {
			model.addAttribute("msgs", "Admin or Subadmin can create User");
			return new ModelAndView("AdminCreateNew");
		}

	}

	@PostMapping(value = "/get-user-search")
	public ModelAndView getAllClintss(Model model, HttpServletRequest request) {
		String userUuid=request.getParameter("userUuid");
		String merchantId = request.getParameter("merchantId");
		String pancard = request.getParameter("pancard");
		
		String userEmail = request.getParameter("userEmail");
		String contactNumber = request.getParameter("contactNumber");
		
		
		if (StringUtils.isAllBlank(merchantId, pancard, userEmail, contactNumber)) {
			List<User> list = userController.getAllUsers(userUuid);
			if (CollectionUtils.isNotEmpty(list)) {
				 model.addAttribute("init", true);
					
					model.addAttribute("merchantId", merchantId);
					model.addAttribute("pancard", pancard);
					model.addAttribute("userEmail", userEmail);
					model.addAttribute("contactNumber", contactNumber);
					
					model.addAttribute("userList", list);
			} else {
				model.addAttribute("init", false);
			}
				return new ModelAndView("AdminmanageClint");
		}
		
		
		List<Object> list = userController.getUserByPanAndMarchantId(pancard, merchantId, userUuid);
			list.addAll(userController.getUserByUserEmailAndContactNumber(userEmail, contactNumber, userUuid));
		if (CollectionUtils.isNotEmpty(list)) {
           model.addAttribute("init", true);
			
			model.addAttribute("merchantId", merchantId);
			model.addAttribute("pancard", pancard);
			model.addAttribute("userEmail", userEmail);
			model.addAttribute("contactNumber", contactNumber);
			
			model.addAttribute("userList", list);
		} else {
			model.addAttribute("init", false);
		}
		return new ModelAndView("AdminmanageClint");
	}

	@PostMapping(value = "/get-subadmin")
	public ModelAndView getSubadmin(Model model, HttpServletRequest request) {
		UserRequestFilterModel userRequestFilterModel = new UserRequestFilterModel();
		userRequestFilterModel.setPage(1);
		
		String userUuid=request.getParameter("userUuid");
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
		userRequestFilterModel.setUserUuid(userUuid);
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
		   //  userController.userPaymentMode(userPaymentModeModal);
		     model.addAttribute("msg", "payment Mode has been added");
		return new ModelAndView("AdminProductFeaturing");
	}
	
	@PostMapping(value = "/admin-whitelist-add")
	public ModelAndView adminwhitelistAdd(Model model, HttpServletRequest request) {
		String userUuid = request.getParameter("userUuid");
		String ip = request.getParameter("ip");
		boolean flag = false;
			flag = userController.apiWhiteListing(userUuid, ip);
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
			return new ModelAndView("Setting");
	}
	
	
	@GetMapping(value = "/get-privilegeList")
	public ModelAndView getListOfPrivilege(@RequestParam("userUuid") String userUuid,Model model, HttpServletRequest request, HttpSession session) {
		User user = userservice.getUserByUserUuid(userUuid);
		model.addAttribute("user",user);
		List<SystemPrivilege> list= userController.getSystemPrivlegeList(userUuid);
		model.addAttribute("privilegeList",list);
		model.addAttribute("init",list.size());
		return new ModelAndView("AdminAddPrivilege");
	}
	
	
	@PostMapping(value = "/admin-add-privilege")
	public ModelAndView addAccessPrivilegesIntoSystem(Model model, HttpServletRequest request) {
		String privilegeName = request.getParameter("privilegeName");
		SystemPrivilege systemPrivilege = null;
		String adminUuid=request.getParameter("adminUuid");
			systemPrivilege = userController.addAccessPrivilegesIntoSystem(privilegeName, adminUuid);
			List<SystemPrivilege> list= userController.getSystemPrivlegeList(adminUuid);
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
		String adminUuid=request.getParameter("adminUuid");
		SystemPrivilege systemPrivilege = null;
			systemPrivilege = userController.updateAccessPrivilegesIntoSystem(privilegeName,nprivilegeName, adminUuid);
			List<SystemPrivilege> list= userController.getSystemPrivlegeList(adminUuid);
			model.addAttribute("privilegeList",list);
			model.addAttribute("init",list.size());
		if (systemPrivilege!=null) {
			model.addAttribute("msg", "System Privilege has been Updated");
		} else
			model.addAttribute("msgs", "Privilege Name didn't Update. ");
		return new ModelAndView("AdminAddPrivilege");
	}

	
	@GetMapping(value = "/deletePrivlege")
	public ModelAndView deleteAccessPrivilegesIntoSystem(@RequestParam("privilegeName") String privilegeName,@RequestParam("adminUuid") String adminUuid,Model model, HttpServletRequest request, HttpSession session) {
		userController.deleteAccessPrivilegesIntoSystem(privilegeName,adminUuid);
		List<SystemPrivilege> list= userController.getSystemPrivlegeList(adminUuid);
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
			HttpServletRequest request) throws Exception {
		String userUuid=request.getParameter("userUuid");
		User user=userController.createSubAdmin(subAdminCreateModal,userUuid);
			List<SystemPrivilege> list= userController.getSystemPrivlegeList(userUuid);
			model.addAttribute("privilegeList",list);
			if(user!=null)
			model.addAttribute("msg", "Subadmin has been created");
			else
			model.addAttribute("msgs", "Phone Or Email already Exist");	
			return new ModelAndView("SubAdminAccount");

		

	}
	
	
	

	
	
	@GetMapping(value = "/get-userDetails")
	public ModelAndView userDetails(@RequestParam("userUuid") String userUuid,Model model, HttpServletRequest request, HttpSession session) {
		User user = userservice.getUserDetailByUserUuid(userUuid);
		String[] userPrivilages = user.getPrivilageNames().split(",");
		model.addAttribute("privilegeList",userPrivilages);
		model.addAttribute("user",user);
		List<SystemPrivilege> list= userController.getSystemPrivlegeList(userUuid);
		model.addAttribute("listprivlege",list);
		model.addAttribute("plist",user.getPrivilageNames());
		return new ModelAndView("SubAdminAccountUpdate");
	}
	
	@PostMapping(value = "/subadminUpdate")
	public ModelAndView subadminUpdate(Model model, HttpServletRequest request) throws Exception {
		User users=null;
		String userUuid=request.getParameter("userUuid");
		String email = request.getParameter("userEmail");
		String password = request.getParameter("password");
		String respose = userController.changeEmailOrPassword(email, password, userUuid);
		String mobileNumber = request.getParameter("mobileNumber");
		String fullName = request.getParameter("fullName");
		String[] privilageNames = request.getParameterValues("privilageNames");
		
		UserUpdateModal userUpdateModal=new UserUpdateModal();
		userUpdateModal.setFullName(fullName);
		userUpdateModal.setUserPrivileges(privilageNames);
		User user = userservice.getUserDetailByUserUuid(request.getParameter("userUuid"));
			users=userController.updateSubadminuser(user,userUpdateModal);
		model.addAttribute("msg", "Information Updated");
		model.addAttribute("user",users);
		String[] userPrivilages = user.getPrivilageNames().split(",");
		model.addAttribute("privilegeList",userPrivilages);
		model.addAttribute("user",user);
		List<SystemPrivilege> list= userController.getSystemPrivlegeList(request.getParameter("userUuid"));
		model.addAttribute("listprivlege",list);
		model.addAttribute("plist",user.getPrivilageNames());
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
	public ModelAndView kycDivData(@RequestParam("userUuid") String userUuid,@RequestParam("adminUuid") String adminUuid,@RequestParam("id") int id,Model model, HttpServletRequest request)
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
		List<UserPaymentMode> paylist=userController.getUserAllPaymentModeDetails(adminUuid, userUuid);
		model.addAttribute("paylist",paylist);
		
		if (paylist != null) {
			for (UserPaymentMode userPaymentMode : paylist) 
			{
				if(userPaymentMode.getPaymentMode()==PaymentMode.RTG)
				{
					model.addAttribute("RTGfeePercent",userPaymentMode.getFee());
					model.addAttribute("rtgsstatus",userPaymentMode.getIsActive());
					model.addAttribute("billChargeType",userPaymentMode.getPaymentModeFeeType());
				}
				if(userPaymentMode.getPaymentMode()==PaymentMode.RGS)
				{
					model.addAttribute("RGSfeePercent",userPaymentMode.getFee());
					model.addAttribute("neftstatus",userPaymentMode.getIsActive());
					model.addAttribute("billChargeType1",userPaymentMode.getPaymentModeFeeType());
				}
				if(userPaymentMode.getPaymentMode()==PaymentMode.IFS)
				{
					model.addAttribute("IFSfeePercent",userPaymentMode.getFee());
					model.addAttribute("impsstatus",userPaymentMode.getIsActive());
					model.addAttribute("billChargeType2",userPaymentMode.getPaymentModeFeeType());
				}
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

			userController.saveOrUpdateUserBusnessKyc2(userBusinessKycRequestModal,user,true);
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
		String billChargeType=request.getParameter("billChargeType");
		String RTGfeePercent=request.getParameter("RTGfeePercent");
		String rtgsstatus=request.getParameter("rtgsstatus");
		
		boolean flag=false;
		if(rtgsstatus.equalsIgnoreCase("Active"))flag=true;
		userPaymentModeModalReqModal.setPaymentMode(PaymentMode.RTG);
		userPaymentModeModalReqModal.setFee(Double.valueOf(RTGfeePercent));
		userPaymentModeModalReqModal.setActive(flag);
		userPaymentModeModalReqModal.setPaymentModeFeeType(EnumUtils.getEnum(PaymentModeFeeType.class, billChargeType));
		 userController.saveOrUpdateUserPaymentMode(userPaymentModeModalReqModal);
		
		String IFS=request.getParameter("IFS");
		String billChargeType1=request.getParameter("billChargeType1");
		String IFSfeePercent=request.getParameter("IFSfeePercent");
		String impsstatus=request.getParameter("impsstatus");
		boolean flag1=false;
		if(impsstatus.equalsIgnoreCase("Active"))flag1=true;
		userPaymentModeModalReqModal.setPaymentMode(PaymentMode.IFS);
		userPaymentModeModalReqModal.setFee(Double.valueOf(IFSfeePercent));
		userPaymentModeModalReqModal.setActive(flag1);
		userPaymentModeModalReqModal.setPaymentModeFeeType(EnumUtils.getEnum(PaymentModeFeeType.class, billChargeType1));
		 userController.saveOrUpdateUserPaymentMode(userPaymentModeModalReqModal);
		
		String RGS=request.getParameter("RGS");
		String billChargeType2=request.getParameter("billChargeType2");
		String RGSfeePercent=request.getParameter("RGSfeePercent");
		String neftstatus=request.getParameter("neftstatus");
		
		boolean flag2=false;
		if(neftstatus.equalsIgnoreCase("Active"))flag2=true;
		userPaymentModeModalReqModal.setPaymentMode(PaymentMode.RGS);
		userPaymentModeModalReqModal.setFee(Double.valueOf(RGSfeePercent));
		userPaymentModeModalReqModal.setActive(flag2);
		userPaymentModeModalReqModal.setPaymentModeFeeType(EnumUtils.getEnum(PaymentModeFeeType.class, billChargeType2));
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
			User user = userservice.getUserByUserUuid(userUuid);
			model.addAttribute("user",user);
			List<UserPaymentMode> paylist=userController.getUserAllPaymentModeDetails(userPaymentModeModalReqModal.getAdminUuid(), userUuid);
			model.addAttribute("paylist",paylist);
			
			Map<String, Object> modelMap = new HashMap<>();
			for (UserPaymentMode userPaymentMode : paylist) 
			{
				if(userPaymentMode.getPaymentMode()==PaymentMode.RTG)
				{
					model.addAttribute("RTGfeePercent",userPaymentMode.getFee());
					model.addAttribute("rtgsstatus",userPaymentMode.getIsActive());
					model.addAttribute("billChargeType",userPaymentMode.getPaymentModeFeeType());
					modelMap.put("RTGfeePercent", userPaymentMode.getFee());
					modelMap.put("RTGbillChargeType", userPaymentMode.getPaymentModeFeeType());
					modelMap.put("RTGStatus", userPaymentMode.getIsActive() == null ? Boolean.FALSE : userPaymentMode.getIsActive());
				}
				if(userPaymentMode.getPaymentMode()==PaymentMode.RGS)
				{
					model.addAttribute("RGSfeePercent",userPaymentMode.getFee());
					model.addAttribute("neftstatus",userPaymentMode.getIsActive());
					model.addAttribute("billChargeType1",userPaymentMode.getPaymentModeFeeType());
					modelMap.put("RGSfeePercent", userPaymentMode.getFee());
					modelMap.put("RGSbillChargeType", userPaymentMode.getPaymentModeFeeType());
					modelMap.put("RGSStatus", userPaymentMode.getIsActive() == null ? Boolean.FALSE : userPaymentMode.getIsActive());

				}
				if(userPaymentMode.getPaymentMode()==PaymentMode.IFS)
				{
					model.addAttribute("IFSfeePercent",userPaymentMode.getFee());
					model.addAttribute("impsstatus",userPaymentMode.getIsActive());
					model.addAttribute("billChargeType2",userPaymentMode.getPaymentModeFeeType());
					modelMap.put("IFSfeePercent", userPaymentMode.getFee());
					modelMap.put("IFSbillChargeType", userPaymentMode.getPaymentModeFeeType());
					modelMap.put("IFSStatus", userPaymentMode.getIsActive() == null ? Boolean.FALSE : userPaymentMode.getIsActive());

				}
			}
			triggerBillingChargesNotifications(user, modelMap);
			return new ModelAndView("UserUpdateAdmin");
		
	}
	
	private void triggerBillingChargesNotifications(User user, Map<String, Object> modelMap) {
		if (StringUtils.isBlank(user.getUserEmail())) {
			return;
		}
			MailRequest request = new MailRequest();
			request.setName(user.getFullName());
			request.setSubject("Billing Charges Updated on your NidhiCMS Account");
			request.setTo(new String[] { user.getUserEmail() });
			modelMap.put("name", user.getFullName());
			emailService.sendMailAsync(request, modelMap, null, EmailTemplateConstants.BILLING_CHARGE_SET);
	}
	
	@PostMapping(value = "/admin-whitelist-update")
	public ModelAndView adminwhitelistupdate(Model model, HttpServletRequest request) {
		String userUuid = request.getParameter("userUuid");
		String ip = request.getParameter("ip");
		boolean flag = false;
			flag = userController.apiWhiteListing(userUuid, ip);
		if (flag) {
			model.addAttribute("msg", "IP has been added");
			
			model.addAttribute("id",6);
			
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
		} else
			model.addAttribute("msgs", "IP didn't add. ");
		return new ModelAndView("UserUpdateAdmin");
	}
	
	
	@GetMapping(value = "/generateApiKey")
	public ModelAndView generateApiKey(@RequestParam("userUuid") String userUuid, Model model,HttpServletRequest request) {
		String apiandToken = userController.generateApiKey(userUuid);
		
		if(apiandToken!=null)
		{
			String[] api_token=apiandToken.split("dev_Ankur");
			model.addAttribute("api",api_token[0]);
			model.addAttribute("token",api_token[1]);
			model.addAttribute("msg","Press view to show Data");
			User userLoginDetails = userservice.getUserByUserUuid(userUuid);
			model.addAttribute("userLoginDetails", userLoginDetails);
		}
		else {
			model.addAttribute("msgs","Your KYC is not verified");
		}
		
		return new ModelAndView("AccessSetting");
	}
	
	@GetMapping(value = "/getGeneratedApiKey")
	public ModelAndView getGeneratedApiKey( @RequestParam("userUuid") String userUuid, Model model,HttpServletRequest request) {
		String apiandToken = userController.getGeneratedApiKey(userUuid);
		
		if(apiandToken!=null)
		{
			String[] api_token=apiandToken.split("dev_Ankur");
			model.addAttribute("api",api_token[0]);
			model.addAttribute("token",api_token[1]);
		}
	
		return new ModelAndView("AccessSetting");
	}
	
@GetMapping("/logout")
    public ModelAndView fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        HttpSession session = request.getSession();
        session.removeAttribute("userLoginDetails");
        request.getSession().invalidate();
        return new ModelAndView(LOGIN);
	}


	@GetMapping(value = "/getTransactionToday")
	public ModelAndView getTransactionToday(@RequestParam("userUuid") String userUuid, Model model,
			HttpServletRequest request) {
		LocalDate toady = LocalDate.now();
		UserWallet userWallet = userController.getUserWallet(userUuid);
		List<Transaction> trans = userController.getUserTransactionsBytoadyDate(userUuid, toady);
		double total = 0.0;
		for (Transaction transaction : trans) {
			total = total + transaction.getAmountPlusfee();
		}
		if (!trans.isEmpty()) {
			model.addAttribute("todayAmt", total);
			model.addAttribute("todayCount", trans.size());
			model.addAttribute("avFund", userWallet.getAmount());
		}
		return new ModelAndView("PayOutSummary");
	}

	@PostMapping(value = "/userTransactionOnDates")
	public ModelAndView userTransactionDates(Model model, HttpServletRequest request) throws ParseException {
		String userUuid = request.getParameter("userUuid");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		List<Transaction> trans = userController.getUserTransactionsByDates(userUuid,
				Utility.stringToLocalDate(startDate), Utility.stringToLocalDate(endDate));
		if (!trans.isEmpty()) {
			model.addAttribute("trans", trans);
			model.addAttribute("init", true);
		} else {
			model.addAttribute("init", false);
		}
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return new ModelAndView("AccountStatement");
	}
//userPayoutReport

@PostMapping(value = "/userPayoutReport")
public ModelAndView userPayoutReport(Model model,HttpServletRequest request) throws ParseException {
	String userUuid=request.getParameter("userUuid");
	String startDate=request.getParameter("startDate");
	String endDate=request.getParameter("endDate");
	
	
	List<Transaction> trans=userController.getUserTransactionsByDates(userUuid,  Utility.stringToLocalDate(startDate),   Utility.stringToLocalDate(endDate));
	List<Transaction> filteredList = trans.stream().filter(t -> BooleanUtils.isNotTrue(t.getIsFeeTx())).collect(Collectors.toList());

	if(!filteredList.isEmpty())
	{
		model.addAttribute("trans",filteredList);
		model.addAttribute("init",true);
	}
	else {
		model.addAttribute("init",false);
	}
	model.addAttribute("startDate",startDate);
	model.addAttribute("endDate",endDate);
	
	return new ModelAndView("payoutReport");
}

@PostMapping(value = "/findbyMerchantId")
public ModelAndView findbyMerchantId(Model model,HttpServletRequest request) throws ParseException {
	String clientname=request.getParameter("clientname");
	String startDate=request.getParameter("startDate");
	String endDate=request.getParameter("endDate");
	
	String[] cname=clientname.split("-");
	String mids=cname[0];
	List<Transaction> trans=userController.findByMerchantIdAndTxDateBetween(mids,  Utility.stringToLocalDate(startDate),   Utility.stringToLocalDate(endDate));
	if(!trans.isEmpty())
	{
		model.addAttribute("trans",trans);
		model.addAttribute("init",true);
	}
	else {
		model.addAttribute("init",false);
	}
	model.addAttribute("startDate",startDate);
	model.addAttribute("endDate",endDate);
	model.addAttribute("clientname",clientname);
	return new ModelAndView("AdminTransactionReport");
}


@PostMapping(value = "/adminBillingReport")
public ModelAndView adminBillingReport(Model model,HttpServletRequest request) {
	String clientname=request.getParameter("clientname");
	String startDate=request.getParameter("startDate");
	String endDate=request.getParameter("endDate");
	
	String[] cname=clientname.split("-");
	String mids=cname[0];
	List<Transaction> trans=userController.findByMerchantIdAndTxDateBetween(mids,  Utility.stringToLocalDate(startDate),   Utility.stringToLocalDate(endDate));
	List<Transaction> filteredList = trans.stream().filter(t -> t.getIsFeeTx()).collect(Collectors.toList());
	if(filteredList != null && !filteredList.isEmpty())
	{
		model.addAttribute("trans",filteredList);
		model.addAttribute("init",true);
	}
	else {
		model.addAttribute("init",false);
	}
	model.addAttribute("startDate",startDate);
	model.addAttribute("endDate",endDate);
	model.addAttribute("clientname",clientname);
	return new ModelAndView("AdminBillingReport");
}



@PostMapping(value = "/findByUnique")
public ModelAndView findByUnique(Model model, HttpServletRequest request) throws ParseException {
	String uniqueNum = request.getParameter("uniqueNum");
	String userUuid = request.getParameter("userUuid");
	List<Transaction> trans = userController.getTransactionsByUniqueId(userUuid, uniqueNum);
	List<Transaction> filteredList = trans.stream().filter(t -> BooleanUtils.isFalse(t.getIsFeeTx())).collect(Collectors.toList());
	if (filteredList != null && !filteredList.isEmpty()) {
		model.addAttribute("trans", filteredList);
		model.addAttribute("init", true);
	} else {
		model.addAttribute("init", false);
	}
	model.addAttribute("uniqueNum", uniqueNum);
	return new ModelAndView("AdminTransactionInqReport");
}

@PostMapping(value = "/getAllTranaction")
public ModelAndView getAllTranaction(Model model,HttpServletRequest request) throws ParseException {
	String userUuid=request.getParameter("userUuid");
	String startDate=request.getParameter("startDate");
	String endDate=request.getParameter("endDate");
	
	List<Transaction> trans=userController.getAllTransactionsByDates(userUuid,  Utility.stringToLocalDate(startDate),   Utility.stringToLocalDate(endDate));
	if(!trans.isEmpty())
	{
		model.addAttribute("trans",trans);
		model.addAttribute("init",true);
	}
	else {
		model.addAttribute("init",false);
	}
	model.addAttribute("startDate",startDate);
	model.addAttribute("endDate",endDate);
	return new ModelAndView("AdminACStatement");
}


@PostMapping(value = "/AdminAcstatement")
public ModelAndView adminAcStatement(Model model, HttpServletRequest request) throws ParseException {
	String clientname = request.getParameter("clientname");
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");

	String[] cname = clientname.split("-");
	String mids = cname[0];
	List<Transaction> trans = userController.findByMerchantIdAndTxDateBetween(mids, Utility.stringToLocalDate(startDate), Utility.stringToLocalDate(endDate));

	if (CollectionUtils.isNotEmpty(trans)) {
		model.addAttribute("trans", trans);
		model.addAttribute("init", true);
	} else {
		model.addAttribute("init", false);
	}
	model.addAttribute("startDate", startDate);
	model.addAttribute("endDate", endDate);
	model.addAttribute("clientname", clientname);
	return new ModelAndView("AdminACStatement");
}


@PostMapping(value = "/getUserNameByMarchantIds")
public ModelAndView getUserNameByMarchantIds(Model model,HttpServletRequest request)  {
	String empName = request.getParameter("emp_rmanager");

	List<String> merchant = userController.getUserNameByMarchantId(empName);
	if (!merchant.isEmpty()) {
		model.addAttribute("merchant", merchant);
	}

	return new ModelAndView("allsupervisor");
}

//<c:if test="${us.txnType eq 'RTG'}">RTGS</c:if>
//<c:if test="${us.txnType eq 'IFS'}">IMPS</c:if>
//	<c:if test="${us.txnType eq 'RGS'}">NEFT</c:if>

@PostMapping(value = "/getStatus")
public @ResponseBody String getStatus(HttpServletRequest request) {
	String userUuid = request.getParameter("userUuid");
	String utr = request.getParameter("utr");
	String transtype = request.getParameter("transtype");
	String uniqueId = request.getParameter("uniqueId");
	String obj = null;
	if (transtype.equalsIgnoreCase("RTG"))
		obj = userController.getTransactionStatus(userUuid, uniqueId, PaymentMode.RTG);
	if (transtype.equalsIgnoreCase("IFS"))
		obj = userController.getTransactionStatus(userUuid, uniqueId, PaymentMode.IFS);
	if (transtype.equalsIgnoreCase("RGS"))
		obj = userController.getTransactionStatus(userUuid, utr, PaymentMode.RGS);

	System.out.println("=============================" + obj);
	
	if (obj == null) {
		return null;
	}

	JSONObject jsonObject = new JSONObject(obj);
	if (jsonObject.has("STATUS")) {
		jsonObject.put("STATUS", jsonObject.getString("STATUS"));
	}
	if (jsonObject.has("URN")) {
		jsonObject.put("URN", jsonObject.getString("URN"));
	}
	if (jsonObject.has("UNIQUEID")) {
		jsonObject.put("UNIQUEID", jsonObject.getString("UNIQUEID"));
	}
	if (jsonObject.has("UTRNUMBER")) {
		jsonObject.put("UTRNUMBER", jsonObject.getString("UTRNUMBER"));
	}
	if (jsonObject.has("RESPONSE")) {
		jsonObject.put("RESPONSE", jsonObject.getString("RESPONSE"));
	}
	if (jsonObject.has("REASON")) {
		jsonObject.put("REASON", jsonObject.getString("REASON"));
	}
	if (jsonObject.has("CreditDate")) {
		jsonObject.put("CreditDate", jsonObject.getString("CreditDate"));
	}

	return jsonObject.toString();

}
}
