package com.nidhi.cms.config;

import java.util.Arrays;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Configuration
public class DozerConfig {

	@Bean(name = "org.dozer.Mapper")
	public DozerBeanMapper dozerBean() {
		final DozerBeanMapper dozerBean = new DozerBeanMapper();
		final List<String> mappingFiles = Arrays.asList("dozer-configration-mapping.xml");
		dozerBean.setMappingFiles(mappingFiles);
		return dozerBean;
	}

}
