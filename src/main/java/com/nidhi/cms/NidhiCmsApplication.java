package com.nidhi.cms;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import com.nidhi.cms.constants.EmailTemplateConstants;
import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.domain.SystemPrivilege;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserBankDetails;
import com.nidhi.cms.domain.email.MailRequest;
import com.nidhi.cms.domain.email.MailResponse;
import com.nidhi.cms.modal.request.UserUpdateModal;
import com.nidhi.cms.repository.SystemPrivilegeRepo;
import com.nidhi.cms.repository.UserRepository;
import com.nidhi.cms.service.email.EmailService;
import com.nidhi.cms.utils.Utility;

/**
 * 
 *
 * @author Ankur Bansala
 */

@SpringBootApplication
public class NidhiCmsApplication extends SpringBootServletInitializer {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SystemPrivilegeRepo systemPrivilegeRepo;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private EmailService emailService;

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NidhiCmsApplication.class);
    }
	
	
	public static void main(String[] args) {
		SpringApplication.run(NidhiCmsApplication.class, args);
	}

	@PostConstruct
	public void runOninit() {
		createAdmin();
		createSystemPrivilages();
		testEmails();
	}
	
	private void testEmails() {
		signUp();
//		terminateAcc();
//		payout();
//		debitAcc();
//		billingChargesSet();
//		UserUpdateDetails();
//		kycApproved();
//		rejected();
//		otp();
//		otpPass();
		
		
	}


	private void otpPass() {
		MailRequest request = new MailRequest();
		request.setName("Dev-Ankur");
		request.setSubject("Email Verification | Nidhi CMS");
		request.setTo(new String[] {"ankurbanssla@gmail.com", "devendra.gread@gmail.com"  });
		Map<String, Object> modal = new HashMap<>();
		modal.put("name", "Dev-Ankur");
		modal.put("OTP", "7248");
		modal.put("password", "45efdc");
		emailService.sendMailAsync(request, modal, "", EmailTemplateConstants.OTP_PASSWORD);
		
	}


	private void otp() {
		MailRequest request = new MailRequest();
		request.setName("Dev-Ankur");
		request.setSubject("Email Verification | Nidhi CMS Account");
		request.setTo(new String[] {"ankurbanssla@gmail.com", "devendra.gread@gmail.com"  });
		Map<String, Object> model = new HashMap<>();
		model.put("name", "Dev-Ankur");
		model.put("OTP", "7248");
		emailService.sendMailAsync(request, model, null, EmailTemplateConstants.OTP);
		
	}


	private void rejected() {
		MailRequest request = new MailRequest();
		request.setName("Dev-Ankur");
		request.setTo(new String[] {"ankurbanssla@gmail.com", "devendra.gread@gmail.com"  });
		request.setSubject("Issue With Account");
		Map<String, Object> model = new HashMap<>();
		model.put("name", "Dev-Ankur");
		model.put("remarks", "rejected the kyc");

		emailService.sendEmail(request, model, null, EmailTemplateConstants.KYC_REJACTED);
		
	}


	private void kycApproved() {
		MailRequest request = new MailRequest();
		request.setName("Dev-Ankur");
		request.setTo(new String[] {"ankurbanssla@gmail.com", "devendra.gread@gmail.com"  });
		request.setSubject("Account Activation");
		Map<String, Object> model = new HashMap<>();
		model.put("name", "Dev-Ankur");
		model.put("clientId", "MID_0004");
		model.put("contactPerson","sonam");
		model.put("accountNo", "486512345613");
		model.put("ifsc", "PUNB003r4");
		model.put("bankName", "PNG");
		emailService.sendEmail(request, model, null, EmailTemplateConstants.KYC_APPROVED);
	}


	private void UserUpdateDetails() {
		MailRequest request = new MailRequest();
		request.setName("Dev-Ankur");
		request.setSubject("Account Update");
		request.setTo(new String[] {"ankurbanssla@gmail.com", "devendra.gread@gmail.com"  });
		Map<String, Object> model = new HashMap<>();
		model.put("name", "Dev-Ankur");
		model.put("fname", "-");
		model.put("lname", "-");
		model.put("mname", "-");
		model.put("dob", "-");
		model.put("fullname", "Dev-Ankur");
		emailService.sendEmail(request, model, null, EmailTemplateConstants.USER_UPDATE_DETAILS);
		
	}


	private void billingChargesSet() {
		MailRequest request = new MailRequest();
		request.setName("Dev-Ankur");
		request.setSubject("Billing Charges Updated on your NidhiCMS Account");
		request.setTo(new String[] { "ankurbanssla@gmail.com", "devendra.gread@gmail.com"  });
		Map<String, Object> model = new HashMap<>();
		model.put("name", "Dev-Ankur");
		model.put("fee", 12.0);
		model.put("type", "FLAT");
		model.put("mode", "RTGS");
		emailService.sendEmail(request, model, null, EmailTemplateConstants.BILLING_CHARGE_SET);
		
	}


	private void debitAcc() {
		MailRequest request = new MailRequest();
		request.setName("Dev-Ankur");
		request.setSubject("Your NidhiCMS Account Debited with Rs." +22.00);
		request.setTo(new String[] { "ankurbanssla@gmail.com", "devendra.gread@gmail.com"  });
		Map<String, Object> model = new HashMap<>();
		model.put("name", "Dev-Ankur");
		model.put("txAmt", "22.00");
		model.put("accNo", "78965142358");
		model.put("createdAt", LocalDateTime.now().toString().replace("T", " "));
		model.put("amt", "122.00");
		emailService.sendEmail(request, model, null, EmailTemplateConstants.DEBIT_ACC);
		
	}


	private void payout() {
		MailRequest request = new MailRequest();
		request.setName("Dev-Ankur");
		request.setSubject("Payment Request Initiation");
		request.setTo(new String[] {"ankurbanssla@gmail.com", "devendra.gread@gmail.com" });
		Map<String, Object> model = new HashMap<>();
		model.put("name", "Dev-Ankur");
		model.put("amt", "22.00");
		model.put("beneficiary_name", "mahipal");
		model.put("account_number", "78965142358");
		model.put("ifsc", "PUNB00543");
		model.put("remark", "Payout request for test");
		model.put("utr", "2189475623");
		model.put("Status", "Success");
		emailService.sendEmail(request, model, null, EmailTemplateConstants.PAYOUT_REQUEST_INITIATION);
		
	}


	private void terminateAcc() {
		MailRequest request = new MailRequest();
		request.setName("Dev-Ankur");
		request.setSubject("Your NidhiCMS Account Suspended");
		request.setTo(new String[] {"ankurbanssla@gmail.com", "devendra.gread@gmail.com" });
		Map<String, Object> model = new HashMap<>();
		model.put("name", "Dev-Ankur");
		emailService.sendEmail(request, model, null, EmailTemplateConstants.TERMINATE_ACCOUNT);
		
	}


	private void signUp() {
		MailRequest request = new MailRequest();
		request.setName("Dev-Ankur");
		request.setSubject("Your NidhiCMS Account | Sign Up");
		request.setTo(new String[] { "ankurbanssla@gmail.com", "devendra.gread@gmail.com" });
		Map<String, Object> model = new HashMap<>();
		model.put("name", "Dev-Ankur");
		emailService.sendEmail(request, model, null, EmailTemplateConstants.SIGN_UP);
		
	}


	public void createAdmin() {
		User admin = userRepository.findByUserEmail("admin@gmail.com"); 
		if (admin == null) 
		{
			admin = new User(); 
			admin.setUserEmail("admin@gmail.com");
			admin.setMobileNumber("1234567890");
			admin.setUserUuid(Utility.getUniqueUuid());
			admin.setPassword(encoder.encode("Admin@123")); 
			admin.setIsAdmin(true);
			admin.setFirstName("Super");
			admin.setLastName("Admin");
			admin.setFullName(admin.getFirstName() + " " + admin.getLastName());
			admin.setIsActive(true); 
			admin.setIsUserVerified(true);
			admin.setRoles(Utility.getRole(RoleEum.ADMIN));
			userRepository.save(admin);
		}
	}
	
	private void createSystemPrivilages() { 
		List<String> systemPrivilages = Arrays.asList("Onboarding","Create New","Pending Client","Manage Client", "Product Featuring", "SubAdmin", "Report","Transaction Report","Bank A/c Verification","Billing/Charges Report","Transaction Inquiry","Account Statement", "Setting","Add Privilege");
		
		if (systemPrivilegeRepo.count() == systemPrivilages.size()) {
			return; 
		}
		
		systemPrivilages.forEach(systemPrivilage -> {
			SystemPrivilege systemPrivilegeDetail = systemPrivilegeRepo.findByPrivilegeName(systemPrivilage);
			if (systemPrivilegeDetail == null) {
				systemPrivilegeDetail = new SystemPrivilege();
				systemPrivilegeDetail.setPrivilegeName(systemPrivilage);
				systemPrivilegeRepo.save(systemPrivilegeDetail);
			}
		});		
	}
}
