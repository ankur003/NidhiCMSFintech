package com.nidhi.cms.controller;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.nidhi.cms.domain.User;
import com.nidhi.cms.service.UserService;
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
    
    public User getLoggedInUserDetails() {
    	return loggedInUserUtil.getLoggedInUserDetails();
    }

}
