package com.nidhi.cms.controller;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;

import com.nidhi.cms.domain.User;
import com.nidhi.cms.utils.LoggedInUserUtil;

import io.swagger.annotations.Api;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Validated
@Api
public class AbstractController {
	
    @Autowired
    protected Mapper beanMapper;
    
    @Autowired
    public LoggedInUserUtil loggedInUserUtil;
    
	@Autowired
	protected BCryptPasswordEncoder encoder;
    
    public User getLoggedInUserDetails1() {
    	return loggedInUserUtil.getLoggedInUserDetails();
    }

}
