/**
 * 
 */
package com.nidhi.cms.controller.fe;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.nidhi.cms.constants.ApiConstants;
import com.nidhi.cms.controller.LoginController;
import com.nidhi.cms.controller.OtpController;
import com.nidhi.cms.controller.UserController;
import com.nidhi.cms.modal.request.LoginRequestModal;
import com.nidhi.cms.modal.request.UserCreateModal;
import com.nidhi.cms.modal.request.VerifyOtpRequestModal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import static com.nidhi.cms.constants.JwtConstants.AUTH_TOKEN;

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
			HttpServletRequest request) throws IOException {
		try {
			HttpSession session = request.getSession();
			String authtoken = loginController.login(loginRequestModal);
			session.getServletContext().setAttribute(AUTH_TOKEN, authtoken);
			if (authtoken != null) {
				return new ModelAndView("Dashboard");
			}
		} catch (Exception e) {
			return new ModelAndView("login");
		}
		return null;
	}
}
