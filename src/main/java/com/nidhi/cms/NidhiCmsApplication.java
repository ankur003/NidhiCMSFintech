package com.nidhi.cms;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class NidhiCmsApplication {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(NidhiCmsApplication.class, args);
	}

	@PostConstruct
	public void createAdmin() {
		User admin = userRepository.findByUsernameAndIsBlocked("admin", false);
		if (admin == null) {
			admin = new User();
			admin.setUsername("admin");
			admin.setUserUuid(Utility.getUniqueUuid());
			admin.setPassword(encoder.encode("admin"));
			admin.setIsAdmin(true);
			admin.setIsBlocked(false);
			admin.setRoles(Utility.getRole(RoleEum.ADMIN));
			userRepository.save(admin);
		}
	}
}
