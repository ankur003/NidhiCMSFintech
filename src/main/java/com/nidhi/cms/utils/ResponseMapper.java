package com.nidhi.cms.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.domain.UserBankDetails;
import com.nidhi.cms.domain.UserBusinessKyc;
import com.nidhi.cms.modal.response.TransactionResponseModel;
import com.nidhi.cms.modal.response.UserBankDetailModel;
import com.nidhi.cms.modal.response.UserBusinessKycModel;

public class ResponseMapper {

	public static ResponseEntity<Object> mapUserBusinessKyc(UserBusinessKyc userBusnessKycDetail) {
		UserBusinessKycModel userBusinessKycModel = new UserBusinessKycModel();
		userBusinessKycModel.setAddress1(userBusnessKycDetail.getAddress1());
		userBusinessKycModel.setAddress2(userBusnessKycDetail.getAddress2());
		userBusinessKycModel.setCin(userBusnessKycDetail.getCin());
		userBusinessKycModel.setCity(userBusnessKycDetail.getCity());
		userBusinessKycModel.setCompnayName(userBusnessKycDetail.getCompnayName());
		userBusinessKycModel.setEntityType(userBusnessKycDetail.getEntityType());
		userBusinessKycModel.setGstNo(userBusnessKycDetail.getGstNo());
		userBusinessKycModel.setIndividualPan(userBusnessKycDetail.getIndividualPan());
		userBusinessKycModel.setIndustry(userBusnessKycDetail.getIndustry());
		userBusinessKycModel.setNoOfEmp(userBusnessKycDetail.getNoOfEmp());
		userBusinessKycModel.setPincode(userBusnessKycDetail.getPincode());
		userBusinessKycModel.setState(userBusnessKycDetail.getState());
		userBusinessKycModel.setWebsiteLink(userBusnessKycDetail.getWebsiteLink());
		userBusinessKycModel.setYearOfInc(userBusnessKycDetail.getYearOfInc());
		return ResponseHandler.getContentResponse(userBusinessKycModel);
	}

	public static ResponseEntity<Object> mapUserBankDetail(UserBankDetails userBankDetail) {
		UserBankDetailModel UserBankDetailModel = new UserBankDetailModel();
		UserBankDetailModel.setAccountNumber(userBankDetail.getAccountNumber());
		UserBankDetailModel.setBankAccHolderName(userBankDetail.getBankAccHolderName());
		UserBankDetailModel.setBankName(userBankDetail.getBankName());
		UserBankDetailModel.setIfsc(userBankDetail.getIfsc());
		return ResponseHandler.getContentResponse(UserBankDetailModel);
	}

	public static List<TransactionResponseModel>  mapTransactionReport(List<Transaction> transactions) {
		List<TransactionResponseModel> transactionResponseModelList = new ArrayList<>();
		for (Transaction transaction : transactions) {
			TransactionResponseModel transactionResponseModel = new TransactionResponseModel();
			transactionResponseModel.setTxDate(transaction.getTxDate());
			transactionResponseModel.setAmount(transaction.getAmount());
			transactionResponseModel.setCurrency(transaction.getCurrency());
			transactionResponseModel.setFee(transaction.getFee());
			transactionResponseModel.setTxType(transaction.getTxType());
			transactionResponseModel.setUniqueId(transaction.getUniqueId());
			transactionResponseModelList.add(transactionResponseModel);
		}
		return transactionResponseModelList;
	}

}
