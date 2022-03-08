package com.nidhi.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.domain.UpiRegistrationDetail;
import com.nidhi.cms.repository.UpiRegistrationDetailRepo;

@Service
public class UpiRegistrationDetailServiceImpl implements UpiRegistrationDetailService {
	
	@Autowired
	private UpiRegistrationDetailRepo upiRegistrationDetailRepo;
	

	@Override
	public void save(UpiRegistrationDetail upiRegistrationDetail) {
		upiRegistrationDetailRepo.save(upiRegistrationDetail);
	}

}
