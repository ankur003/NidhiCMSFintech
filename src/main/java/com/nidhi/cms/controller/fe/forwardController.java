package com.nidhi.cms.controller.fe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.nidhi.cms.constants.ApiConstants;

@RestController
@RequestMapping(value = ApiConstants.API_VERSION + "/fe")
public class forwardController {

	@GetMapping(value = "/")
	public ModelAndView main(Model model) {
		return new ModelAndView("index");
	}

	@GetMapping(value = "/index")
	public ModelAndView index(Model model) {
		return new ModelAndView("index");
	}

	@GetMapping(value = "/VerifyOtp")
	public ModelAndView verifyOtp(Model model) {
		return new ModelAndView("VerifyOtp");
	}

	@GetMapping(value = "/Dashboard")
	public ModelAndView dashboard(Model model) {
		return new ModelAndView("Dashboard");
	}

	@GetMapping(value = "/Pkyc")
	public ModelAndView pkyc(Model model) {
		return new ModelAndView("Pkyc");
	}

	@GetMapping(value = "/Bkyc")
	public ModelAndView bkyc(Model model) {
		return new ModelAndView("Bkyc");
	}

	@GetMapping(value = "/AccountStatement")
	public ModelAndView accountStatement(Model model) {
		return new ModelAndView("AccountStatement");
	}

	@GetMapping(value = "/payoutReport")
	public ModelAndView payoutReport(Model model) {
		return new ModelAndView("payoutReport");
	}

	@GetMapping(value = "/bankAcVerifyreport")
	public ModelAndView bankAcVerifyreport(Model model) {
		return new ModelAndView("bankAcVerifyreport");
	}

	@GetMapping(value = "/AccessSetting")
	public ModelAndView AccessSetting(Model model) {
		return new ModelAndView("AccessSetting");
	}

	@GetMapping(value = "/FundAccount")
	public ModelAndView fundAccount(Model model) {
		return new ModelAndView("FundAccount");
	}

	@GetMapping(value = "/Setting")
	public ModelAndView setting(Model model) {
		return new ModelAndView("Setting");
	}

	@GetMapping(value = "/contactus")
	public ModelAndView contact(Model model) {
		return new ModelAndView("contactus");
	}

	@GetMapping(value = "/aboutus")
	public ModelAndView course(Model model) {
		return new ModelAndView("aboutus");
	}

	@GetMapping(value = "/Signup")
	public ModelAndView gallery(Model model) {
		return new ModelAndView("Signup");
	}

	@GetMapping(value = "/login")
	public ModelAndView academics(Model model) {
		return new ModelAndView("login");
	}

	@GetMapping(value = "/AdminDashboard")
	public ModelAndView AdminDashboard(Model model) {
		return new ModelAndView("AdminDashboard");
	}

	@GetMapping(value = "/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "0");

			session.invalidate();
		}
		return "redirect:/";
	}
}
