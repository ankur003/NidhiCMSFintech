package com.nidhi.cms.controller.react;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nidhi.cms.constants.ApiConstants;
import com.nidhi.cms.constants.enums.ErrorCode;
import com.nidhi.cms.controller.AbstractController;
import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.SystemPrivilege;
import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserBankDetails;
import com.nidhi.cms.domain.UserBusinessKyc;
import com.nidhi.cms.domain.UserDoc;
import com.nidhi.cms.domain.UserPaymentMode;
import com.nidhi.cms.modal.request.SubAdminCreateModal;
import com.nidhi.cms.modal.request.UserCreateModal;
import com.nidhi.cms.modal.request.UserPaymentModeModalReqModal;
import com.nidhi.cms.modal.request.UserRequestFilterModel;
import com.nidhi.cms.modal.response.SystemPrivilegesModel;
import com.nidhi.cms.modal.response.TransactionResponseModel;
import com.nidhi.cms.modal.response.UserBankDetailModel;
import com.nidhi.cms.modal.response.UserBusinessKycModel;
import com.nidhi.cms.modal.response.UserDetailModal;
import com.nidhi.cms.modal.response.UserModel;
import com.nidhi.cms.react.request.ReportRequestModel;
import com.nidhi.cms.react.request.UserActivateOrDeActivateReqModel;
import com.nidhi.cms.react.request.UserBusnessKycRequestModel;
import com.nidhi.cms.react.request.UserChargesRequestModel;
import com.nidhi.cms.react.request.UserFilterModel;
import com.nidhi.cms.service.DocService;
import com.nidhi.cms.service.OtpService;
import com.nidhi.cms.service.TransactionService;
import com.nidhi.cms.service.UserBankService;
import com.nidhi.cms.service.UserBusnessKycService;
import com.nidhi.cms.service.UserPaymentModeService;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.service.UserWalletService;
import com.nidhi.cms.utils.ResponseHandler;
import com.nidhi.cms.utils.ResponseMapper;
import com.nidhi.cms.utils.Utility;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = ApiConstants.API_VERSION + "/admin")
public class AdminReactController extends AbstractController{
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private DocService docService;
	
	@Autowired
	private OtpService otpService;
	
	@Autowired
	private UserBusnessKycService userBusnessKycService;
	
	@Autowired
	private UserBankService userBankService;
	
	@Autowired
	private UserPaymentModeService userPaymentModeService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private UserWalletService userWalletService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminReactController.class);
	
	@GetMapping(value = "/get-all-user")
	public ResponseEntity<Object> getAllUser(@Valid @ModelAttribute final UserRequestFilterModel userRequestFilterModel) {
		Page<User> users = userservice.getAllUsers(userRequestFilterModel);
		if (users == null || CollectionUtils.isEmpty(users.getContent())) {
			return ResponseHandler.get204Response();
		}
		Map<String, Object> map = ResponseHandler.getpaginationResponse(beanMapper, users, UserDetailModal.class);
		return ResponseHandler.getContentResponse(map);
	}
	
	@PostMapping(value = "/create-client-by-admin")
	public ResponseEntity<Object> createClientByAdmin(@Valid @RequestBody UserCreateModal userCreateModal) throws Exception {
		if (BooleanUtils.isFalse(userCreateModal.getIsCreatedByAdmin())) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "isCreatedByAdmin : isCreatedByAdmin always be true", HttpStatus.BAD_REQUEST);
		}
		
		final User existingUser = userservice.getUserByUserEmailOrMobileNumber(userCreateModal.getUserEmail(), userCreateModal.getMobileNumber());
		if (existingUser == null) {
			CompletableFuture.runAsync(() -> {
				try {
					User user = new User();
					user.setIsUserCreatedByAdmin(userCreateModal.getIsCreatedByAdmin());
					user.setUserEmail(userCreateModal.getUserEmail());
					user.setMobileNumber(userCreateModal.getMobileNumber());
					user.setFullName(userCreateModal.getFullName());
					userservice.createUser(user, userCreateModal);
				} catch (Exception e) {
					LOGGER.error("An error ocurred during create Client By Admin", e);
				}
			});

			return ResponseHandler.getMapResponse("message", "User created, please check email for the temp password");
		}
		if (BooleanUtils.isFalse(existingUser.getIsUserCreatedByAdmin())) {
			return ResponseHandler.getResponseEntity(ErrorCode.UNPROCESSABLE_ENTITY, "Can't be proceed, try to signUp again", HttpStatus.PRECONDITION_FAILED);
		}
		if (BooleanUtils.isTrue(existingUser.getIsUserVerified())) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Either Email or Mobile already Exist.", HttpStatus.PRECONDITION_FAILED);
		}
		if (BooleanUtils.isFalse(existingUser.getIsUserVerified())) {
			CompletableFuture.runAsync(() -> {
				try {
					existingUser.setPassword(encoder.encode(userCreateModal.getPassword()));
					otpService.sendPasswordOnEmail(existingUser, userCreateModal.getPassword());
					userservice.changePassword(existingUser);
				} catch (Exception e) {
					LOGGER.error("An error ocurred updating password for client", e);
				}
			});
			
			return ResponseHandler.getMapResponse("message", "temp password generated again, check mail for the password");
		}
		return ResponseHandler.getResponseEntity(ErrorCode.GENERIC_SERVER_ERROR, "Some thing went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ApiResponses(value = {
	        @ApiResponse(code = 200, response = UserModel.class, message = "", responseContainer = "List")
	         })
	@GetMapping(value = "/get-filter-users")
	public ResponseEntity<Object> getFiletrUsers(@Valid @ModelAttribute final UserFilterModel UserFilterModel) {
		if(StringUtils.isAllBlank(UserFilterModel.getContactNumber(), UserFilterModel.getMerchantId(), UserFilterModel.getPancard(), UserFilterModel.getUserEmail())) {
			List<User> userDetails = userservice.getAllUsers();
			if (CollectionUtils.isEmpty(userDetails)) {
				return ResponseHandler.get204Response(); 
			}
			List<UserModel>  userModels = mapUser(userDetails);
			return ResponseHandler.getContentResponse(userModels);
		}
		List<UserModel> userDetails = userservice.getfilterUsers(UserFilterModel);
		return ResponseHandler.getContentResponse(userDetails);
	}
	
	private List<UserModel> mapUser(List<User> userDetails) {
		List<UserModel> userModels = new ArrayList<>();
		for (User user : userDetails) {
			UserModel userModel = new UserModel();
			userModel.setEmail(user.getUserEmail());
			userModel.setFullName(user.getFullName());
			userModel.setMobile(user.getMobileNumber());
			userModel.setUserUuid(user.getUserUuid());
			userModels.add(userModel);
		}
		return userModels;
	}
	
	@PostMapping(value = "/create-sub-admin")
	public ResponseEntity<Object> createSubAdmin(@Valid @RequestBody SubAdminCreateModal subAdminCreateModal) throws Exception {
		if (CollectionUtils.isEmpty(subAdminCreateModal.getPrivilageNames())) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "privilageNames is empty", HttpStatus.BAD_REQUEST);
		}
		User savedUser = userservice.createSubAdmin(subAdminCreateModal);
		if (savedUser == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "user already exist or privilageNames invalid", HttpStatus.BAD_REQUEST);

		}
		return ResponseHandler.get201Response();
	}
	
	@ApiResponses(value = { @ApiResponse(code = 200, response = SystemPrivilegesModel.class, message = "", responseContainer = "List") })
	@GetMapping(value = "/system-privileges")
	public ResponseEntity<Object> getSystemPrivileges() throws Exception {
		List<SystemPrivilege> list = userservice.getSystemPrivilegeList();
		if (CollectionUtils.isEmpty(list)) {
			return ResponseHandler.get204Response();
		}
		List<SystemPrivilegesModel> systemPrivilegesModels = list.stream().map(SystemPrivilegesModel::new).collect(Collectors.toList());
		return ResponseHandler.getContentResponse(systemPrivilegesModels);
	}
	
	@GetMapping(value = "/get/document/{documentUuid}")
	public ResponseEntity<Object> getDocument(@PathVariable("documentUuid") final String documentUuid) throws Exception {
		UserDoc userDoc = docService.getUserDocByDocumentUuid(documentUuid);
		if (userDoc == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User", HttpStatus.BAD_REQUEST);
		}
		if (userDoc.getData() == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect file data", HttpStatus.BAD_REQUEST);
		}
		final Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("document", userDoc.getData());
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
	
	@GetMapping(value = "/get/document/user/{userUuid}")
	public ResponseEntity<Object> getDocument(@PathVariable("userUuid") final String userUuid,
			@RequestParam("docType") DocType docType) throws Exception {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User", HttpStatus.BAD_REQUEST);
		}
		UserDoc doc = docService.getUserDocByUserIdAndDocType(user.getUserId(), docType);
		if (doc == null || doc.getData() == null) {
			return ResponseHandler.get204Response();
		}
		final Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("document", doc.getData());
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
	
	@GetMapping(value = "/get/all-documents/user/{userUuid}")
	public ResponseEntity<Object> getUserAllDocuments(@PathVariable("userUuid") final String userUuid) throws Exception {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User", HttpStatus.BAD_REQUEST);
		}
		List<UserDoc> docs = docService.getUserAllKyc(user.getUserId());
		if (CollectionUtils.isEmpty(docs)) {
			return ResponseHandler.get204Response();
		}
		final Map<String, Object> responseMap = new HashMap<>();
		docs.forEach(doc -> {
			responseMap.put(doc.getDocType().name(), doc.getData());
		});
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
	
	@PostMapping("/upload/document-by-doc-type")
	public ResponseEntity<Object> uploadDocumentByDocType(@RequestParam("file") final MultipartFile multipartFile,
			@RequestParam("userUuid") final String userUuid, @RequestParam("docType") DocType docType) throws IOException {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null || BooleanUtils.isFalse(user.getIsActive())) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User", HttpStatus.BAD_REQUEST);
		}
		String documentUuid = userservice.saveOrUpdateUserDoc(user, multipartFile, docType);
		if (StringUtils.isBlank(documentUuid)) {
			return ResponseHandler.getResponseEntity(ErrorCode.GENERIC_SERVER_ERROR, "document not saved", HttpStatus.NOT_MODIFIED);
		}
		final Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("documentUuid", documentUuid);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
	}
	
    @PostMapping("/upload/document")
    public ResponseEntity<Object> uploadDocument(
    		@RequestParam("pan") final MultipartFile multipartFilePan,
    		@RequestParam("adhar") final MultipartFile multipartFileAdhar,
    		@RequestParam("userUuid") final String userUuid) throws IOException {
    	User user = userservice.getUserByUserUuid(userUuid);
    	if (user == null || BooleanUtils.isFalse(user.getIsActive())) {
    		return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User", HttpStatus.BAD_REQUEST);
    	}
    	String panUuid = userservice.saveOrUpdateUserDoc(user, multipartFilePan, DocType.DOCUMENT_PAN);
    	if (StringUtils.isBlank(panUuid)) {
			return ResponseHandler.getResponseEntity(ErrorCode.GENERIC_SERVER_ERROR, "document not saved", HttpStatus.NOT_MODIFIED);
    	}
    	String adharUuid = userservice.saveOrUpdateUserDoc(user, multipartFileAdhar, DocType.DOCUMENT_AADHAR);
    	if (StringUtils.isBlank(adharUuid)) {
			return ResponseHandler.getResponseEntity(ErrorCode.GENERIC_SERVER_ERROR, "document not saved", HttpStatus.NOT_MODIFIED);
    	}
		final Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("panUuid", panUuid);
		responseMap.put("adharUuid", adharUuid);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
    }
    
    @ApiResponses(value = { @ApiResponse(code = 200, response = UserBusinessKycModel.class, message = "") })
	@GetMapping(value = "/get/business-detail/user/{userUuid}")
	public ResponseEntity<Object> getUserbusinessDetail(@PathVariable("userUuid") final String userUuid) throws Exception {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User", HttpStatus.BAD_REQUEST);
		}
		UserBusinessKyc userBusnessKycDetail = userBusnessKycService.getUserBusnessKyc(user.getUserId());
		if (userBusnessKycDetail == null) {
			return ResponseHandler.get204Response();
		}
		return ResponseMapper.mapUserBusinessKyc(userBusnessKycDetail);
	}
	
	@PutMapping(value = "/business-detail/user/{userUuid}")
	public ResponseEntity<Object> saveOrUpdateUserbusinessDetail(@PathVariable("userUuid") final String userUuid,
			@Valid @RequestBody UserBusnessKycRequestModel userBusnessKycRequestModel) throws Exception {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User", HttpStatus.BAD_REQUEST);
		}
		UserBusinessKyc userBusnessKycDetail = userBusnessKycService.getUserBusnessKyc(user.getUserId());
		userBusnessKycService.saveOrUpdateUserBusnessKyc(user, userBusnessKycDetail, userBusnessKycRequestModel);
		return ResponseHandler.getOkResponse();
	}
	
	@ApiResponses(value = { @ApiResponse(code = 200, response = UserBankDetailModel.class, message = "") })
	@GetMapping(value = "/get/bank/user/{userUuid}")
	public ResponseEntity<Object> getUserBankDetail(@PathVariable("userUuid") final String userUuid) throws Exception {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User", HttpStatus.BAD_REQUEST);
		}
		UserBankDetails userBankDetail = userBankService.getUserById(user.getUserId());
		if (userBankDetail == null) {
			return ResponseHandler.get204Response();
		}
		return ResponseMapper.mapUserBankDetail(userBankDetail);
	}
	
	@PutMapping(value = "/bank-detail/user/{userUuid}")
	public ResponseEntity<Object> saveOrUpdateUserbankDetail(@PathVariable("userUuid") final String userUuid,
			@Valid @RequestBody UserBankDetailModel userBankDetailModel) throws Exception {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User", HttpStatus.BAD_REQUEST);
		}
		UserBankDetails userBankDetail = userBankService.getUserById(user.getUserId());
		if (userBankDetail == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User.", HttpStatus.BAD_REQUEST);
		}
		userBankService.saveOrUpdateUserBankDetails(user, userBankDetail, userBankDetailModel);
		return ResponseHandler.getOkResponse();
	}
	
	@PutMapping(value = "/activate-or-deActivate/user/{userUuid}")
	public ResponseEntity<Object> activateORdeActivateUser(@PathVariable("userUuid") final String userUuid,
			@Valid @RequestBody UserActivateOrDeActivateReqModel actOrDeActModel) throws Exception {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User", HttpStatus.BAD_REQUEST);
		}
		if (BooleanUtils.isFalse(actOrDeActModel.getIsActivate()) && StringUtils.isBlank(actOrDeActModel.getReason())) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "reason mandatory in case of user de-activation", HttpStatus.BAD_REQUEST);
		}
		userservice.userReactActivateOrDeactivate(user, actOrDeActModel.getIsActivate());
		return ResponseHandler.getOkResponse();
	}
	
	@ApiResponses(value = { @ApiResponse(code = 200, response = UserPaymentMode.class, message = "", responseContainer = "List") })
	@GetMapping(value = "/get-charges/user/{userUuid}")
	public ResponseEntity<Object> getUserCharges(@PathVariable("userUuid") final String userUuid) throws Exception {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User", HttpStatus.BAD_REQUEST);
		}
		List<UserPaymentMode> details = userPaymentModeService.getUserAllPaymentMode(user);
		if (CollectionUtils.isEmpty(details)) {
			return ResponseHandler.get204Response();
		}
		return ResponseHandler.getContentResponse(details);
	}
	
	@PutMapping(value = "/set-charges/user/{userUuid}")
	public ResponseEntity<Object> setUserCharges(@PathVariable("userUuid") final String userUuid,
			@Valid @RequestBody List<UserChargesRequestModel> userChargesRequestModels) throws Exception {
		if (userChargesRequestModels == null || userChargesRequestModels.size() <= 0) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect Request Data", HttpStatus.BAD_REQUEST);
		}
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User", HttpStatus.BAD_REQUEST);
		}
		for (UserChargesRequestModel userChargesRequestModel : userChargesRequestModels) {
			UserPaymentModeModalReqModal userPaymentModeModalReqModal = new UserPaymentModeModalReqModal();
			userPaymentModeModalReqModal.setActive(userChargesRequestModel.isActive());
			userPaymentModeModalReqModal.setPaymentMode(userPaymentModeModalReqModal.getPaymentMode());
			userPaymentModeModalReqModal.setFee(userPaymentModeModalReqModal.getFee());
			userPaymentModeModalReqModal.setPaymentModeFeeType(userChargesRequestModel.getPaymentModeFeeType());
			userPaymentModeService.saveOrUpdateUserPaymentMode(user, userPaymentModeModalReqModal);
		}
		return ResponseHandler.getOkResponse();
	}
	
	@GetMapping(value = "/get-ip/user/{userUuid}")
	public ResponseEntity<Object> getUserIp(@PathVariable("userUuid") final String userUuid) throws Exception {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User", HttpStatus.BAD_REQUEST);
		}
		String ip = user.getWhiteListIp();
		final Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("ip", ip);
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
	
	@PutMapping(value = "/set-ip/user/{userUuid}/{ip}")
	public ResponseEntity<Object> setUserIp(@PathVariable("userUuid") final String userUuid, @PathVariable("ip") final String ip) throws Exception {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect User", HttpStatus.BAD_REQUEST);
		}
		userservice.apiWhiteListing(user, ip);
		return ResponseHandler.getOkResponse();
	}
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "") })
	@GetMapping(value = "/get/client-suggestions/")
	public ResponseEntity<Object> getClientNameSuggestions(@RequestParam ("name") String name) throws Exception {
		final List<String> merchantIdAndName = userWalletService.getUserNameByMarchantId(name);
		return ResponseHandler.getContentResponse(merchantIdAndName);
	}
	
	@ApiResponses(value = { @ApiResponse(code = 200, response = TransactionResponseModel.class, message = "", responseContainer = "List") })
	@GetMapping(value = "/transaction-report/")
	public ResponseEntity<Object> getTransactionReport(@Valid @RequestBody ReportRequestModel reportRequestModel) throws Exception {
		List<Transaction> transactions = transactionService.findByMerchantIdAndTxDateBetween(reportRequestModel.getMid(), Utility.stringToLocalDate(reportRequestModel.getStartDate()),
				 Utility.stringToLocalDate(reportRequestModel.getEndDate()));
		if (CollectionUtils.isEmpty(transactions)) {
			return ResponseHandler.get204Response();
		}
		List<TransactionResponseModel>  transactionResponseModel = ResponseMapper.mapTransactionReport(transactions);
		return ResponseHandler.getContentResponse(transactionResponseModel);
	}
	
}
