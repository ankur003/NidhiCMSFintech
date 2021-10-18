/**
 * 
 */
package com.nidhi.cms.controller.fe;

import static com.nidhi.cms.constants.JwtConstants.AUTH_TOKEN;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.nidhi.cms.domain.UserAccountStatement;
import com.nidhi.cms.domain.UserDoc;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.modal.request.LoginRequestModal;
import com.nidhi.cms.modal.request.UserBusinessKycRequestModal;
import com.nidhi.cms.modal.request.UserCreateModal;
import com.nidhi.cms.modal.request.UserPaymentModeModal;
import com.nidhi.cms.modal.request.UserRequestFilterModel;
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
		userCreateModal.setIsCreatedByAdmin(false);
		String respose = userController.userSignUp(userCreateModal);
		model.addAttribute("msg", respose);
		if (respose != null && respose.equalsIgnoreCase("username : user already exist by mobile or email.")) {
			return new ModelAndView("Signup");
		} else {
			return new ModelAndView("VerifyOtp");

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

			List<UserDoc> getUserAllKyc = userController.getUserAllKyc();

			if (!getUserAllKyc.isEmpty() && getUserAllKyc.size() == 3 || getUserAllKyc.size() > 3) {
				session.setAttribute("kyc", "Done");
			} else {
				session.setAttribute("kyc", "Pending");
			}

			if (authtoken != null && roleName.equals(RoleEum.ADMIN.name())) {
				return new ModelAndView("AdminDashboard");
			} else {
				return new ModelAndView("Dashboard");
			}
		} catch (Exception e) {
			model.addAttribute("msg", "Either email or Password is incorrect, please try again.");
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

			List<UserDoc> getUserAllKyc = userController.getUserAllKyc();
			if (getUserAllKyc.size() == 3 || getUserAllKyc.size() > 3) {
				session.setAttribute("kyc", "Done");
			} else {
				session.setAttribute("kyc", "Pending");
			}

			model.addAttribute("msg", "Business Details Succesfully Uploaded");
			return new ModelAndView("Dashboard");
		} catch (Exception e) {
			return new ModelAndView("Pkyc");
		}
	}

	@PostMapping(value = "/updateEmailpass")
	public ModelAndView updateEmailpass(Model model, HttpServletRequest request) {
		String email = request.getParameter("userEmail");
		String password = request.getParameter("password");
		String respose = userController.changeEmailOrPassword(email, password);
		model.addAttribute("msg", respose);
		if (respose != null && respose.equalsIgnoreCase("Email Or Password changed")) {
			return new ModelAndView("Setting");
		}
		return new ModelAndView("Setting");

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
		Map<String, Object> users = userController.getAllUser(userRequestFilterModel);
		if (users != null) {
			model.addAttribute("msg", "same has been verified");
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
}
