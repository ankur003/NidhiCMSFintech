package com.nidhi.cms.controller;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Validated
public class AbstractController {
	
    @Autowired
    protected Mapper beanMapper;

}
