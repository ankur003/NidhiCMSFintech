package com.nidhi.cms.controller.fe;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.nidhi.cms.constants.ApiConstants;
import com.nidhi.cms.controller.UserController;
import com.nidhi.cms.domain.SystemPrivilege;

@RestController
@RequestMapping(value = ApiConstants.API_VERSION + "/fe")
public class forwardController {
	
	@Autowired
	private UserController userController;


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
	@GetMapping(value = "/PayOutSummary")
	public ModelAndView PayOutSummary(Model model) {
		return new ModelAndView("PayOutSummary");
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

	@GetMapping(value = "/AdminCreateNew")
	public ModelAndView AdminCreateNew(Model model) {
		return new ModelAndView("AdminCreateNew");
	}
	@GetMapping(value = "/AdminPendingClient")
	public ModelAndView AdminPendingClient(Model model) {
		return new ModelAndView("AdminPendingClient");
	}
	
	@GetMapping(value = "/AdminmanageClint")
	public ModelAndView AdminmanageClint(Model model) {
		return new ModelAndView("AdminmanageClint");
	}
	
	@GetMapping(value = "/AdminProductFeaturing")
	public ModelAndView AdminProductFeaturing(Model model) {
		return new ModelAndView("AdminProductFeaturing");
	}
	
	@GetMapping(value = "/SubAdminAccount")
	public ModelAndView SubAdminAccount(Model model) {
		List<SystemPrivilege> list= userController.getSystemPrivlegeList();
       	model.addAttribute("privilegeList",list);
	    model.addAttribute("init",list.size());
		return new ModelAndView("SubAdminAccount");
	}
	
	@GetMapping(value = "/AdminTransactionReport")
	public ModelAndView AdminTransactionReport(Model model) {
		return new ModelAndView("AdminTransactionReport");
	}
	
	@GetMapping(value = "/AdminBankACverification")
	public ModelAndView AdminBankACverification(Model model) {
		return new ModelAndView("AdminBankACverification");
	}
	
	@GetMapping(value = "/AdminBillingReport")
	public ModelAndView AdminBillingReport(Model model) {
		return new ModelAndView("AdminBillingReport");
	}
	
	@GetMapping(value = "/AdminTransactionInqReport")
	public ModelAndView AdminTransactionInqReport(Model model) {
		return new ModelAndView("AdminTransactionInqReport");
	}
	
	@GetMapping(value = "/AdminACStatement")
	public ModelAndView AdminACStatement(Model model) {
		return new ModelAndView("AdminACStatement");
	}
	@GetMapping(value = "/AdminSetting")
	public ModelAndView AdminSetting(Model model) {
		return new ModelAndView("AdminSetting");
	}
	@GetMapping(value = "/SubAdminAccountUpdate")
	public ModelAndView SubAdminAccountUpdate(Model model) {
		return new ModelAndView("SubAdminAccountUpdate");
	}
	
	@GetMapping(value = "/docsView")
	public ModelAndView docsView(Model model) {
		return new ModelAndView("docsView");
	}
	@GetMapping(value = "/UserBank")
	public ModelAndView UserBank(Model model) {
		return new ModelAndView("UserBank");
	}
	@GetMapping(value = "/AdminWhiteListpage")
	public ModelAndView admin(Model model) {
		return new ModelAndView("AdminWhiteListpage");
	}
	@GetMapping(value = "/userCompInfo")
	public ModelAndView userCompInfo(Model model) {
		return new ModelAndView("userCompInfo");
	}
	@GetMapping(value = "/AdminAddPrivilege")
	public ModelAndView AdminAddPrivilege(Model model) {
		return new ModelAndView("AdminAddPrivilege");
	}
	@GetMapping(value = "/Adminupdateprivilege")
	public ModelAndView Adminupdateprivilege(Model model) {
		return new ModelAndView("Adminupdateprivilege");
	}
	@GetMapping(value = "/UserUpdateAdmin")
	public ModelAndView UserUpdateAdmin(Model model) {
		return new ModelAndView("UserUpdateAdmin");
	}
	
	
}

