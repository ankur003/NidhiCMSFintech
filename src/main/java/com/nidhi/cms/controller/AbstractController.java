package com.nidhi.cms.controller;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

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

}
