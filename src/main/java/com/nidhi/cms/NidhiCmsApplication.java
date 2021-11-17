package com.nidhi.cms;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.repository.UserRepository;
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
	private BCryptPasswordEncoder encoder;

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NidhiCmsApplication.class);
    }
	
	
	public static void main(String[] args) {
		SpringApplication.run(NidhiCmsApplication.class, args);
	}

	@PostConstruct
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
}
