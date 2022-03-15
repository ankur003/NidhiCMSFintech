package com.nidhi.cms;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nidhi.cms.constants.EmailTemplateConstants;
import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.domain.Role;
import com.nidhi.cms.domain.SystemPrivilege;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.email.MailRequest;
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
@EnableScheduling
public class NidhiCmsApplication extends SpringBootServletInitializer {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SystemPrivilegeRepo systemPrivilegeRepo;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private EmailService emailService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NidhiCmsApplication.class);


	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NidhiCmsApplication.class);
    }
	
	
	public static void main(String[] args) {
		SpringApplication.run(NidhiCmsApplication.class, args);
	}

	@PostConstruct
	public void runOninit() throws Exception {
		createAdmin();
		createSystemPrivilages();
		//testEmails();
		test();
	}
	
	private void test() {
		LOGGER.info("@@@@@@@@@@@@@@@@@@@ ------ {} ", "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  15 MARCH 2022");
		deCryptResponse();
		
	}
	
	public void deCryptResponse()  {
		try {
			String keyFile = "/home/nidhicms/public_html/keys/privateKey.txt";
			InputStream inStream = new FileInputStream(keyFile);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			byte[] encKey = new byte[inStream.available()];
			inStream.read(encKey);
			String pvtKey = new String(encKey);
			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(pvtKey));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey priv = keyFactory.generatePrivate(privKeySpec);
			byte[] cipherByte = org.bouncycastle.util.encoders.Base64.decode(
					"hfG7lIsyL78BbDMsvOueUyLg6ipOYaF3RGKehtRIVun4eJZCnwN8KnZpJfh1aTTkjjjUlcj5A891qYQhyVvlCoDfNgM4XJPn+UCo12EbKhkZ0OhLBqo+ifjxpO2iJX2owbqvdjmgbpoveCuusP9RyH52ab6AidV/+ozPWc9ff6iFbZL+yMWwia+PTPOyrTOzHhVCOXOBljG9jhAgb8x5+65xabKDY3ww/s5KtF5owo1Qr0/IVvXpRVLpx2avhwSezUcGlN4uj49hUGkaErQGQ18snAbihL7liHJSkTP60Md3WCtrBHbcq02gz2RoT85fXMy8tX8T3L2LqOC20y7375Q8Su1J6pB/RtHI6oRtUT65Q+Hd/Qcp94g3uDsW2In3l1eE40tDTL5QinzI4z3fLSclT8JtmPt9WYRYROfArFP+BIeNSTPrAbmWkY44FKm9UPrI212/WaZIJgNkbRHg/SKIn0chmZBFDhzSK7rUwPVOdPaUz7oJmOJ5e4ZtUdkSKyOa6u91CXyg8FWg7vR52VaG0P2MHBaQ9DZ7yYL8qod4T4mv6bvxNo/MjM3pCF4tQaK+c+E3yLLxdMWXReBKpoF4RbhzLpL4stc2qQhk6z1RtanVvsy1MwmTztpa7+mJoYWsP3Y4hNRzurGI6+mi801Q+Ieji3fPvoyKMB9Q5II="
							.getBytes("UTF-8"));
			SecureRandom secureRandom = new SecureRandom();
			cipher.init(Cipher.DECRYPT_MODE, priv, secureRandom);
			String dd = new String(cipher.doFinal(cipherByte));
			System.out.println("de-crypted msg ------->  " + dd);
			inStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	private void testEmails() {
		signUp();
		terminateAcc();
		payout();
		debitAcc();
		billingChargesSet();
		UserUpdateDetails();
		kycApproved();
		rejected();
		otp();
		otpPass();
		
		
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
		model.put("otp", "7248");
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


	public void createAdmin() throws Exception {
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
			return;
		}
		
		Set<Role> roles = admin.getRoles();
		for (Role role : roles) {
			System.out.println(role.getName());
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
