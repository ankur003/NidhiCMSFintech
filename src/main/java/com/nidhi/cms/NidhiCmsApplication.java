package com.nidhi.cms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.domain.SystemPrivilege;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.email.MailRequest;
import com.nidhi.cms.domain.email.MailResponse;
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
		List<String> systemPrivilages = Arrays.asList("Onboarding", "Product Featuring", "SubAdmin", "Report", "Setting");
		
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
