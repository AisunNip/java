package th.co.truecorp.crmdev.asset.rest;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.NodeList;

import th.co.truecorp.crmdev.asset.AssetLogger;
import th.co.truecorp.crmdev.asset.bean.AccountResponseBean;
import th.co.truecorp.crmdev.asset.bean.AddressBean;
import th.co.truecorp.crmdev.asset.bean.AddressRespBean;
import th.co.truecorp.crmdev.asset.bean.AssetBean;
import th.co.truecorp.crmdev.asset.bean.AssetCompBean;
import th.co.truecorp.crmdev.asset.bean.AssetCompForOrderBean;
import th.co.truecorp.crmdev.asset.bean.AssetCompForOrderRespBean;
import th.co.truecorp.crmdev.asset.bean.AssetCompRespBean;
import th.co.truecorp.crmdev.asset.bean.AssetComponentBean;
import th.co.truecorp.crmdev.asset.bean.AssetDataBean;
import th.co.truecorp.crmdev.asset.bean.AssetFindByBean;
import th.co.truecorp.crmdev.asset.bean.AssetProductBean;
import th.co.truecorp.crmdev.asset.bean.AssetRelationShipReqBean;
import th.co.truecorp.crmdev.asset.bean.AssetReqBean;
import th.co.truecorp.crmdev.asset.bean.AssetRespBean;
import th.co.truecorp.crmdev.asset.bean.AssetResponseBean;
import th.co.truecorp.crmdev.asset.bean.BillingAccountBean;
import th.co.truecorp.crmdev.asset.bean.CapMaxReqBean;
import th.co.truecorp.crmdev.asset.bean.CapMaxRespBean;
import th.co.truecorp.crmdev.asset.bean.Constants;
import th.co.truecorp.crmdev.asset.bean.ConvergenceRelationReqBean;
import th.co.truecorp.crmdev.asset.bean.ConvergenceRelationRespBean;
import th.co.truecorp.crmdev.asset.bean.CustomerAccountBean;
import th.co.truecorp.crmdev.asset.bean.CustomerAccountBean2;
import th.co.truecorp.crmdev.asset.bean.CustomerRowIDRespBean;
import th.co.truecorp.crmdev.asset.bean.DeleteComponentReqBean;
import th.co.truecorp.crmdev.asset.bean.DeleteComponentRespBean;
import th.co.truecorp.crmdev.asset.bean.DummyMsisdnReqBean;
import th.co.truecorp.crmdev.asset.bean.HuntingFLPReqBean;
import th.co.truecorp.crmdev.asset.bean.HuntingFLPRespBean;
import th.co.truecorp.crmdev.asset.bean.LivingReqBean;
import th.co.truecorp.crmdev.asset.bean.LivingRespBean;
import th.co.truecorp.crmdev.asset.bean.MappingRespBean;
import th.co.truecorp.crmdev.asset.bean.MaxAssetVipRespBean;
import th.co.truecorp.crmdev.asset.bean.ProductBean;
import th.co.truecorp.crmdev.asset.bean.PromiseToPayReqBean;
import th.co.truecorp.crmdev.asset.bean.RedZoneReqBean;
import th.co.truecorp.crmdev.asset.bean.ResponseBean;
import th.co.truecorp.crmdev.asset.bean.SiebelAuthorizationBean;
import th.co.truecorp.crmdev.asset.dao.AccountDAO;
import th.co.truecorp.crmdev.asset.dao.AddressDAO;
import th.co.truecorp.crmdev.asset.dao.AssetDAO;
import th.co.truecorp.crmdev.asset.dao.MonitorDAO;
import th.co.truecorp.crmdev.asset.exception.CRMESBException;
import th.co.truecorp.crmdev.asset.exception.DatabaseException;
import th.co.truecorp.crmdev.asset.exception.INTRestException;
import th.co.truecorp.crmdev.asset.exception.SiebelException;
import th.co.truecorp.crmdev.asset.exception.ValidationException;
import th.co.truecorp.crmdev.asset.logic.AssetLogic;
import th.co.truecorp.crmdev.asset.mapping.AssetMapping;
import th.co.truecorp.crmdev.asset.proxy.CCBSProxy;
import th.co.truecorp.crmdev.asset.proxy.CRMInboundProxy;
import th.co.truecorp.crmdev.asset.proxy.INTProxy;
import th.co.truecorp.crmdev.asset.proxy.SiebelProxy;
import th.co.truecorp.crmdev.asset.util.AssetErrorCode;
import th.co.truecorp.crmdev.asset.util.AssetUtil;
import th.co.truecorp.crmdev.asset.util.AuthenticationUtil;
import th.co.truecorp.crmdev.asset.util.CRMESBTransform;
import th.co.truecorp.crmdev.asset.util.INTRestTransform;
import th.co.truecorp.crmdev.asset.util.SiebelTransform;
import th.co.truecorp.crmdev.asset.util.StringUtil;
import th.co.truecorp.crmdev.asset.validate.AssetValidation;
import th.co.truecorp.crmdev.util.common.UUIDManager;
import th.co.truecorp.crmdev.util.common.Validator;
import th.co.truecorp.crmdev.util.common.Xpath;
import th.co.truecorp.crmdev.util.common.bean.ErrorCodeResp;
import th.co.truecorp.crmdev.util.db.config.CassandraConfig;
import th.co.truecorp.crmdev.util.db.mapping.CassandarMapping;
import th.co.truecorp.crmdev.util.db.mapping.MappingBean;
import th.co.truecorp.crmdev.util.logging.LogProductName;
import th.co.truecorp.crmdev.util.logging.LogSystem;
import th.co.truecorp.crmdev.util.net.http.HttpResponse;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/CRMIAsset")
public class AssetController {

	@Autowired
	private HttpServletRequest httpRequest;
	
	@Qualifier("olcrmDS")
	@Autowired
	private DataSource olcrmDS;
	
	@Autowired
	private MonitorDAO monitorDAO;
	
	@Autowired
	private AssetDAO assetDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private AddressDAO addressDAO;
	
	private AssetLogger logger;
	private AssetErrorCode assetErrorCode;
	private AssetValidation assetValidation;
	private Validator validator;
	private AssetMapping assetMapping;
	
	public AssetController() throws UnknownHostException {
		this.logger = new AssetLogger(LogProductName.All, LogSystem.CRM_INBOUND, LogSystem.CRM_INBOUND);
		
		this.assetErrorCode = new AssetErrorCode();
		this.assetValidation = new AssetValidation();
		
		this.validator = new Validator();
		this.assetMapping = new AssetMapping();
	}
	
	@RequestMapping(value="/monitoring", method=RequestMethod.GET)
	public ResponseEntity<Object> monitoring() {
		ResponseEntity<Object> responseEntity = null;
		String transID = null;
		Calendar startREST = null;
		
		try {
			transID = UUID.randomUUID().toString();
			startREST = logger.logRequestRESTProvider(transID);
			
			ResponseBean respBean = this.monitorDAO.monitorDB(transID);
			logger.writeResponseMsg(transID, respBean);
			
			if (Constants.ErrorCode.SUCCESS_CODE.equals(respBean.getCode())) {
				responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
			}
			else {
				this.logger.error(transID, respBean.getMsg());
				responseEntity = new ResponseEntity<Object>(HttpStatus.SERVICE_UNAVAILABLE);
			}
		}
		catch (Throwable t) {
			this.logger.error(transID, t.getMessage(), t);
			responseEntity = new ResponseEntity<Object>(HttpStatus.SERVICE_UNAVAILABLE);
		}
		finally {
			logger.logResponseRESTProvider(transID, String.valueOf(responseEntity.getStatusCode().value()), startREST);
		}
		
		return responseEntity;
	}

	@RequestMapping(value="/getAssetRootList", method=RequestMethod.POST)
    public AssetRespBean getAssetRootList(@RequestBody AssetFindByBean assetFindByBean) {
		AssetRespBean assetRespBean = new AssetRespBean();
		List<String> accountRowIDList = null;
		List<AddressBean> addressBeanList = null;
		ErrorCodeResp errorCodeResp = null;
		String transID = null;
		Calendar startREST = null;
		
		try {
	    	transID = UUID.randomUUID().toString();
	    	startREST = logger.logRequestRESTProvider(transID);
	    	logger.writeRequestMsg(transID, this.httpRequest.getRequestURL().toString(), assetFindByBean);
	    	
	    	this.assetValidation.validateGetAssetRootList(assetFindByBean);
	    	
	    	if (this.validator.hasStringValue(assetFindByBean.getIdNo())) {
	    		this.accountDAO.setTransID(transID);
	    		CustomerRowIDRespBean customerRowIDRespBean = accountDAO.getCustomerRowIDList(assetFindByBean.getIdNo());
	    		
	    		if (Constants.ErrorCode.SUCCESS_CODE.equals(customerRowIDRespBean.getCode())) {
	    			String[] customerRowIDList = customerRowIDRespBean.getCustomerRowIDList();
	    			
	    			if (customerRowIDList != null && customerRowIDList.length <= Constants.LIMIT_MAX_EXPRESSION) {
	    				assetFindByBean.setCustomerRowIDList(customerRowIDList);
	    			}
	    		}
	    		else {
	    			assetRespBean.setCode(customerRowIDRespBean.getCode());
	    			assetRespBean.setMsg(customerRowIDRespBean.getMsg());
	    			return assetRespBean;
	    		}
	    	}
	    	
	    	// Get Mapping all IDType
	    	MappingBean[] mappingBeanList = null;
	    	
	    	if ("Y".equalsIgnoreCase(assetFindByBean.getIdTypeCodeFlag())) {
	    		this.accountDAO.setTransID(transID);
    			MappingRespBean mappingRespBean = this.accountDAO.getMappingIDType();
    			
    			if (!Constants.ErrorCode.SUCCESS_CODE.equals(mappingRespBean.getCode())) {
    				assetRespBean.setCode(mappingRespBean.getCode());
	    			assetRespBean.setMsg(mappingRespBean.getMsg());
	    			return assetRespBean;
    			}
    			
    			mappingBeanList = mappingRespBean.getMappingList();
    		}
	    	
	    	assetDAO.setTransID(transID);
	    	assetRespBean = assetDAO.getAssetRootList(assetFindByBean, mappingBeanList);
	    	
	    	if (Constants.ErrorCode.SUCCESS_CODE.equals(assetRespBean.getCode())) {
	    		if ("Y".equalsIgnoreCase(assetFindByBean.getCustomerAddressFlag())
	    			|| "Y".equalsIgnoreCase(assetFindByBean.getBillingAddressFlag())) {
	    			
	    			logger.info(transID, "Start Find Address");
	    			accountRowIDList = new ArrayList<String>();
	    			
	    			AssetBean[] assetBeanList = assetRespBean.getAssetBeanList();
	    			
	    			if ("Y".equalsIgnoreCase(assetFindByBean.getCustomerAddressFlag())) {
		    			for (AssetBean assetBean : assetBeanList) {
		    				if (assetBean.getCustomerAccountBean() != null) {
		    					accountRowIDList.add(assetBean.getCustomerAccountBean().getAccountRowID());
		    				}
		    			}
	    			}
	    			
	    			if ("Y".equalsIgnoreCase(assetFindByBean.getBillingAddressFlag())) {
	    				for (AssetBean assetBean : assetBeanList) {
	    					if (assetBean.getBillingAccountBean() != null) {
	    						accountRowIDList.add(assetBean.getBillingAccountBean().getAccountRowID());
	    					}
		    			}
	    			}
	    			
	    			if (accountRowIDList.size() > 0) {
		    			addressDAO.setTransID(transID);
		    			AddressRespBean addressRespBean = addressDAO.getAddressList(accountRowIDList,null);
		    			
		    			if (Constants.ErrorCode.SUCCESS_CODE.equals(addressRespBean.getCode())) {
		    				addressBeanList = addressRespBean.getAddressBeanList();
		    				
		    				if ("Y".equalsIgnoreCase(assetFindByBean.getCustomerAddressFlag())) {
		    					for (AssetBean assetBean : assetBeanList) {
		    						CustomerAccountBean2 custAccountBean = assetBean.getCustomerAccountBean();
		    						
		    						if (custAccountBean != null) {
		    							custAccountBean.setAddressList(addressDAO.filterAddress(addressBeanList, custAccountBean.getAccountRowID()));
		    						}
		    					}
		    				}
		    				
		    				if ("Y".equalsIgnoreCase(assetFindByBean.getBillingAddressFlag())) {
		    					for (AssetBean assetBean : assetBeanList) {
		    						BillingAccountBean billAccountBean = assetBean.getBillingAccountBean();
		    						
		    						if (billAccountBean != null) {
		    							billAccountBean.setAddressList(addressDAO.filterAddress(addressBeanList, billAccountBean.getAccountRowID()));
		    						}
		    					}
		    				}
		    			}
		    			else if (!this.assetErrorCode.isDataNotFound(addressRespBean.getCode())) {
		    				assetRespBean.setCode(addressRespBean.getCode());
		    				assetRespBean.setMsg(addressRespBean.getMsg());
		    			}
	    			}
	    		}
	    	}
		}
		catch (ValidationException validEx) {
			assetRespBean.setCode(validEx.getErrorCode());
			assetRespBean.setMsg(validEx.getMessage());
		}
		catch (DatabaseException dbEx) {
			assetRespBean.setCode(dbEx.getErrorCode());
			assetRespBean.setMsg(dbEx.getMessage());
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			assetRespBean.setTransID(transID);
			
			if (errorCodeResp != null) {
				assetRespBean.setCode(errorCodeResp.getErrorCode());
				assetRespBean.setMsg(errorCodeResp.getErrorMessage());
			}
			
			if (accountRowIDList != null) {
				accountRowIDList.clear();
				accountRowIDList = null;
			}
			
			if (addressBeanList != null) {
				addressBeanList.clear();
				addressBeanList = null;
			}
			
			logger.writeResponseMsg(transID, assetRespBean);
			logger.logResponseRESTProvider(transID, assetRespBean.getCode(), startREST);
		}
		
    	return assetRespBean;
    }
	
	@RequestMapping(value="/getLatestAssetRoot", method=RequestMethod.POST)
    public AssetRespBean getLatestAssetRoot(@RequestBody AssetFindByBean assetFindByBean) {
		AssetRespBean assetRespBean = new AssetRespBean();
		List<String> accountRowIDList = null;
		List<AddressBean> addressBeanList = null;
		ErrorCodeResp errorCodeResp = null;
		String transID = null;
		Calendar startREST = null;
		
		try {
			transID = UUID.randomUUID().toString();
	    	startREST = logger.logRequestRESTProvider(transID);
	    	logger.writeRequestMsg(transID, this.httpRequest.getRequestURL().toString(), assetFindByBean);
	    	
	    	this.assetValidation.validateGetLatestAssetRoot(assetFindByBean);
	    	
	    	if (this.validator.hasStringValue(assetFindByBean.getIdNo())) {
	    		accountDAO.setTransID(transID);
	    		CustomerRowIDRespBean customerRowIDRespBean = accountDAO.getCustomerRowIDList(assetFindByBean.getIdNo(),assetFindByBean.getOlprdDBFlag());
	    		
	    		if (Constants.ErrorCode.SUCCESS_CODE.equals(customerRowIDRespBean.getCode())) {
	    			String[] customerRowIDList = customerRowIDRespBean.getCustomerRowIDList();
	    			
	    			if (customerRowIDList != null && customerRowIDList.length <= Constants.LIMIT_MAX_EXPRESSION) {
	    				assetFindByBean.setCustomerRowIDList(customerRowIDList);
	    			}
	    		}
	    		else {
	    			assetRespBean.setCode(customerRowIDRespBean.getCode());
	    			assetRespBean.setMsg(customerRowIDRespBean.getMsg());
	    			return assetRespBean;
	    		}
	    	}
	    	
	    	// Get Mapping all IDType
	    	MappingBean[] mappingBeanList = null;
	    	
	    	if ("Y".equalsIgnoreCase(assetFindByBean.getIdTypeCodeFlag())) {
	    		this.accountDAO.setTransID(transID);
    			MappingRespBean mappingRespBean = this.accountDAO.getMappingIDType();
    			
    			if (!Constants.ErrorCode.SUCCESS_CODE.equals(mappingRespBean.getCode())) {
    				assetRespBean.setCode(mappingRespBean.getCode());
	    			assetRespBean.setMsg(mappingRespBean.getMsg());
	    			return assetRespBean;
    			}
    			
    			mappingBeanList = mappingRespBean.getMappingList();
    		}
	    	
	    	this.assetDAO.setTransID(transID);
	    	assetRespBean = this.assetDAO.getLatestAssetRoot(assetFindByBean, mappingBeanList);
	    	
	    	if (Constants.ErrorCode.SUCCESS_CODE.equals(assetRespBean.getCode())) {
	    		if ("Y".equalsIgnoreCase(assetFindByBean.getCustomerAddressFlag())
	    			|| "Y".equalsIgnoreCase(assetFindByBean.getBillingAddressFlag())) {
	    			
	    			logger.info(transID, "Start Find Address");
	    			accountRowIDList = new ArrayList<String>();
	    			
	    			AssetBean[] assetBeanList = assetRespBean.getAssetBeanList();
	    			
	    			if ("Y".equalsIgnoreCase(assetFindByBean.getCustomerAddressFlag())) {
		    			for (AssetBean assetBean : assetBeanList) {
		    				if (assetBean.getCustomerAccountBean() != null) {
		    					accountRowIDList.add(assetBean.getCustomerAccountBean().getAccountRowID());
		    				}
		    			}
	    			}
	    			
	    			if ("Y".equalsIgnoreCase(assetFindByBean.getBillingAddressFlag())) {
	    				for (AssetBean assetBean : assetBeanList) {
	    					if (assetBean.getBillingAccountBean() != null) {
	    						accountRowIDList.add(assetBean.getBillingAccountBean().getAccountRowID());
	    					}
		    			}
	    			}
	    			
	    			if (accountRowIDList.size() > 0) {
		    			AddressRespBean addressRespBean = addressDAO.getAddressList(accountRowIDList, assetFindByBean.getOlprdDBFlag());
		    			
		    			if (Constants.ErrorCode.SUCCESS_CODE.equals(addressRespBean.getCode())) {
		    				addressBeanList = addressRespBean.getAddressBeanList();
		    				
		    				if ("Y".equalsIgnoreCase(assetFindByBean.getCustomerAddressFlag())) {
		    					for (AssetBean assetBean : assetBeanList) {
		    						CustomerAccountBean2 custAccountBean = assetBean.getCustomerAccountBean();
		    						
		    						if (custAccountBean != null) {
		    							custAccountBean.setAddressList(addressDAO.filterAddress(addressBeanList, custAccountBean.getAccountRowID()));
		    						}
		    					}
		    				}
		    				
		    				if ("Y".equalsIgnoreCase(assetFindByBean.getBillingAddressFlag())) {
		    					for (AssetBean assetBean : assetBeanList) {
		    						BillingAccountBean billAccountBean = assetBean.getBillingAccountBean();
		    						
		    						if (billAccountBean != null) {
		    							billAccountBean.setAddressList(addressDAO.filterAddress(addressBeanList, billAccountBean.getAccountRowID()));
		    						}
		    					}
		    				}
		    			}
		    			else if (!this.assetErrorCode.isDataNotFound(addressRespBean.getCode())) {
		    				assetRespBean.setCode(addressRespBean.getCode());
		    				assetRespBean.setMsg(addressRespBean.getMsg());
		    			}
	    			}
	    		}
	    	}
		}
		catch (ValidationException validEx) {
			assetRespBean.setCode(validEx.getErrorCode());
			assetRespBean.setMsg(validEx.getMessage());
		}
		catch (DatabaseException dbEx) {
			assetRespBean.setCode(dbEx.getErrorCode());
			assetRespBean.setMsg(dbEx.getMessage());
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			assetRespBean.setTransID(transID);
			
			if (errorCodeResp != null) {
				assetRespBean.setCode(errorCodeResp.getErrorCode());
				assetRespBean.setMsg(errorCodeResp.getErrorMessage());
			}
			
			if (accountRowIDList != null) {
				accountRowIDList.clear();
				accountRowIDList = null;
			}
			
			if (addressBeanList != null) {
				addressBeanList.clear();
				addressBeanList = null;
			}
			
			logger.writeResponseMsg(transID, assetRespBean);
			logger.logResponseRESTProvider(transID, assetRespBean.getCode(), startREST);
		}
		
    	return assetRespBean;
	}
	
	@RequestMapping(value="/getAssetComponentList", method=RequestMethod.POST)
    public AssetCompRespBean getAssetComponentList(@RequestBody AssetFindByBean assetFindByBean) {
		AssetCompRespBean responseBean = new AssetCompRespBean();
		ErrorCodeResp errorCodeResp = null;
		String transID = null;
		Calendar startREST = null;
		
		try {
	    	transID = UUID.randomUUID().toString();
	    	startREST = logger.logRequestRESTProvider(transID);
	    	logger.writeRequestMsg(transID, this.httpRequest.getRequestURL().toString(), assetFindByBean);
	    	
	    	this.assetValidation.validateGetAssetComponentList(assetFindByBean);
	    	
	    	assetDAO.setTransID(transID);
	    	
	    	// Find AssetRoot RowID
	    	if (this.validator.hasStringValue(assetFindByBean.getServiceID())) {
	    		AssetRespBean assetRespBean = assetDAO.getLatestAssetRootRowID(assetFindByBean);
	    		
	    		if (!Constants.ErrorCode.SUCCESS_CODE.equals(assetRespBean.getCode())) {
	    			responseBean.setCode(assetRespBean.getCode());
	    			responseBean.setMsg(assetRespBean.getMsg());
	    			return responseBean;
	    		}
	    		
	    		AssetBean[] assetBeanList = assetRespBean.getAssetBeanList();
	    		assetFindByBean.setAssetRowID(assetBeanList[0].getAssetRowID());
	    	}
	    	
	    	responseBean = assetDAO.getAssetComponentAttributeList(assetFindByBean);
		}
		catch (ValidationException validEx) {
			responseBean.setCode(validEx.getErrorCode());
			responseBean.setMsg(validEx.getMessage());
		}
		catch (DatabaseException dbEx) {
			responseBean.setCode(dbEx.getErrorCode());
			responseBean.setMsg(dbEx.getMessage());
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			responseBean.setTransID(transID);
			
			if (errorCodeResp != null) {
				responseBean.setCode(errorCodeResp.getErrorCode());
				responseBean.setMsg(errorCodeResp.getErrorMessage());
			}
			
			logger.writeResponseMsg(transID, responseBean);
			logger.logResponseRESTProvider(transID, responseBean.getCode(), startREST);
		}
		
    	return responseBean;
    }
	
	@RequestMapping(value="/getHuntingFLP", method=RequestMethod.POST)
	public HuntingFLPRespBean getHuntingFLP(@RequestBody HuntingFLPReqBean huntingFLPReqBean) {
		HuntingFLPRespBean huntingFLPRespBean = new HuntingFLPRespBean();
		ErrorCodeResp errorCodeResp = null;
		String transID = null;
		Calendar startREST = null;
		
		try {
	    	transID = UUID.randomUUID().toString();
	    	startREST = logger.logRequestRESTProvider(transID);
	    	logger.writeRequestMsg(transID, this.httpRequest.getRequestURL().toString(), huntingFLPReqBean);
	    	
	    	this.assetValidation.validateGetHuntingFLP(huntingFLPReqBean);
	    	
	    	assetDAO.setTransID(transID);
	    	
	    	if (!this.validator.hasStringValue(huntingFLPReqBean.getPilotAssetRowID())) {
	    		logger.info(transID, "Find Latest Asset Row ID");
		    	AssetFindByBean assetFindByBean = new AssetFindByBean();
		    	assetFindByBean.setServiceID(huntingFLPReqBean.getPilotServiceID());
		    	assetFindByBean.setBan(huntingFLPReqBean.getPilotBan());
		    	assetFindByBean.setMasterFlag(huntingFLPReqBean.getPilotMasterFlag());
		    	
		    	AssetRespBean assetRespBean = assetDAO.getLatestAssetRootRowID(assetFindByBean);
	    		
	    		if (!Constants.ErrorCode.SUCCESS_CODE.equals(assetRespBean.getCode())) {
	    			huntingFLPRespBean.setCode(assetRespBean.getCode());
	    			huntingFLPRespBean.setMsg(assetRespBean.getMsg());
	    			return huntingFLPRespBean;
	    		}
	    		
	    		AssetBean[] assetBeanList = assetRespBean.getAssetBeanList();
	    		String assetRowID = assetBeanList[0].getAssetRowID();
	    		logger.info(transID, "Found AssetRowID: " + assetRowID);
	    		
	    		huntingFLPReqBean.setPilotAssetRowID(assetRowID);
	    	}
	    	
    		huntingFLPRespBean = assetDAO.getParentHuntingFLP(huntingFLPReqBean);
    		
			if (Constants.ErrorCode.SUCCESS_CODE.equals(huntingFLPRespBean.getCode())
				&& Boolean.TRUE.equals(huntingFLPRespBean.getIsPilotServiceID())) {
				
				HuntingFLPRespBean childHuntingFLPRespBean = assetDAO.getChildHuntingFLP(huntingFLPReqBean);
				
				if (Constants.ErrorCode.SUCCESS_CODE.equals(childHuntingFLPRespBean.getCode())) {
					huntingFLPRespBean.setChildAssetList(childHuntingFLPRespBean.getChildAssetList());
				}
			}
		}
		catch (ValidationException validEx) {
			huntingFLPRespBean.setCode(validEx.getErrorCode());
			huntingFLPRespBean.setMsg(validEx.getMessage());
		}
		catch (DatabaseException dbEx) {
			huntingFLPRespBean.setCode(dbEx.getErrorCode());
			huntingFLPRespBean.setMsg(dbEx.getMessage());
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			huntingFLPRespBean.setTransID(transID);
			
			if (errorCodeResp != null) {
				huntingFLPRespBean.setCode(errorCodeResp.getErrorCode());
				huntingFLPRespBean.setMsg(errorCodeResp.getErrorMessage());
			}
			
			logger.writeResponseMsg(transID, huntingFLPRespBean);
			logger.logResponseRESTProvider(transID, huntingFLPRespBean.getCode(), startREST);
		}
		
		return huntingFLPRespBean;
	}

	@RequestMapping(value="/updateAsset", method=RequestMethod.POST)
	public AssetResponseBean updateAsset(@RequestHeader(value="SiebelAuthorization") String siebelAuthorize
		, @RequestBody AssetReqBean assetReqBean) {
		
		AssetResponseBean responseBean = new AssetResponseBean();
		ErrorCodeResp errorCodeResp = null;
		String transID = null;
		Calendar startREST = null;
		
		try {
			transID = UUIDManager.getUUID().toString();
			
			startREST = logger.logRequestRESTProvider(transID);
			logger.writeRequestMsg(transID, this.httpRequest.getRequestURL().toString(), assetReqBean);
			
			// Validation
			this.assetValidation.validateUpdateAsset(assetReqBean);
			
			AssetDataBean assetDataBean = assetReqBean.getAsset();

			AssetFindByBean assetFindByBean = new AssetFindByBean();
			assetFindByBean.setServiceID(assetDataBean.getServiceId());
			assetFindByBean.setBan(assetDataBean.getBan());
			assetFindByBean.setAssetRowID(assetDataBean.getAssetId());
			
			assetDAO.setTransID(transID);
			AssetRespBean assetLatestRespDAO = assetDAO.getLatestAssetRoot(assetFindByBean, null);
			
			if (!Constants.ErrorCode.SUCCESS_CODE.equals(assetLatestRespDAO.getCode())) {
				responseBean.setCode(assetLatestRespDAO.getCode());
				responseBean.setMsg(assetLatestRespDAO.getMsg());
				return responseBean;
			}

			AssetBean assetLastestBean = assetLatestRespDAO.getAssetBeanList()[0];
			
			assetDataBean.setAssetId(assetLastestBean.getAssetRowID());
			
			if (StringUtil.isEmpty(assetDataBean.getAssetNo())) {
				assetDataBean.setAssetNo(assetLastestBean.getAssetNo());
			}

			if (StringUtil.isEmpty(assetDataBean.getCustomerRowId())) {
				assetDataBean.setCustomerRowId(assetLastestBean.getCustomerRowID());
			}

			if (StringUtil.isEmpty(assetDataBean.getBan())) {
				if (assetLastestBean.getBillingAccountBean() != null) {
					assetDataBean.setBan(assetLastestBean.getBillingAccountBean().getBan());
				}				
			}

			if (StringUtil.isEmpty(assetDataBean.getIdType())) {
				if (assetLastestBean.getBillingAccountBean() != null) {
					assetDataBean.setIdType(assetLastestBean.getBillingAccountBean().getAccountType());
				}
			}

			AssetProductBean assetProductBean = new AssetProductBean();
			assetDataBean.setAssetProductBean(assetProductBean);
			
			ProductBean productBean = assetLastestBean.getProductBean();
			if (null != productBean) {
				if (StringUtil.isNotEmpty(productBean.getProductPartNo())){
					assetDataBean.getAssetProductBean().setProductPartNo(productBean.getProductPartNo());
				}
			}

			if (StringUtil.isNotEmpty(assetLastestBean.getStatus())){
				assetDataBean.setStatus(assetLastestBean.getStatus());
			}

			if (assetDataBean.getAssetComponent() != null) {
				// Validate upsertComponent
				if (assetDataBean.getAssetComponent() == null) {
					errorCodeResp = this.assetErrorCode.generateRequiredField("AssetComponentList");
					return responseBean;
				}

				// Generate Asset No
				AssetComponentBean[] assetComponentBeanList = assetDataBean.getAssetComponent();
				// equipmentType = Modem|AP|Handset|Accessories|Settop Box|Laptop
				String equipmentType = CassandraConfig.getVal("assetcomponent.type.equipment");

				for (AssetComponentBean assetComponent : assetComponentBeanList) {

					if (this.validator.validator(assetComponent.getType(), equipmentType) 
						&& StringUtil.isEmpty(assetComponent.getAssetNo())) {
						
						assetComponent.setPartNo("EQ_QRUN");
						assetComponent.setAssetNo("EQ_" + assetDataBean.getServiceId() + "_" + assetComponent.getSerialNo());
					}
				}
			}

			// Mapping
			AssetMapping.mappingUpdateAsset(assetReqBean);

			// Call Siebel API
			AuthenticationUtil authenticationUtil = new AuthenticationUtil();
			SiebelAuthorizationBean siebelAuthenBean = authenticationUtil.getSiebelAuthorization(siebelAuthorize);

			String xmlRequest = SiebelTransform.generateSyncAssetReq(assetReqBean, siebelAuthenBean);
			
			SiebelProxy siebelProxy = new SiebelProxy();
			HttpResponse httpResponse = siebelProxy.syncAsset(transID, xmlRequest);
			
			// Roll Up CA // Update CA accountServiceLevel FOR  New Loyalty
			if (assetDataBean.getAssetVIP() != null) {
				// GetMaxVip
				MaxAssetVipRespBean maxAssetVipRespBean = assetDAO.getMaxAssetVip(assetDataBean.getCustomerRowId(), assetDataBean.getAssetId());

				// Change Value Ex VIPA TO VIP3
				String assetVipConvert = CassandarMapping.getVal("VIP", assetDataBean.getAssetVIP());
				// Change Value For Compare VIP Ex VIP3 to 1
				String assetVipToInt = CassandarMapping.getVal("VIPCONVERT", assetVipConvert);

				if (Integer.parseInt(maxAssetVipRespBean.getMaxAssetVip()) > Integer.parseInt(assetVipToInt)){
					if (Integer.parseInt(maxAssetVipRespBean.getMaxAssetVip()) == 0) {
						maxAssetVipRespBean.setMaxAssetVip("%CLEAR%");
					}
					else if (Integer.parseInt(maxAssetVipRespBean.getMaxAssetVip()) == 1 ){
						maxAssetVipRespBean.setMaxAssetVip("VIP3");
					}
					else if (Integer.parseInt(maxAssetVipRespBean.getMaxAssetVip()) == 2 ){
						maxAssetVipRespBean.setMaxAssetVip("VIP2");
					}
					else if (Integer.parseInt(maxAssetVipRespBean.getMaxAssetVip()) == 3 ){
						maxAssetVipRespBean.setMaxAssetVip("VIP1");
					}
					else {
						maxAssetVipRespBean.setMaxAssetVip("%CLEAR%");
					}
				}
				else{
					maxAssetVipRespBean.setMaxAssetVip(assetVipConvert);
				}
				
				// TODO: Develop call Siebel API directly. 
				// Update CA
				ResponseBean updateCaResponse = updateCustomerAccount(transID, assetDataBean.getBan()
												, maxAssetVipRespBean.getMaxAssetVip(), assetLastestBean.getCustomerAccountBean().getAccountType()
												, assetDataBean.getCustomerRowId());
			}

			AssetDataBean assetdata = new AssetDataBean();
			assetdata.setAssetId(assetDataBean.getAssetId());
			responseBean.setAsset(assetdata);
			
			responseBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
			responseBean.setMsg(Constants.SUCCESS_MSG);
			responseBean.setTransactionId(transID);
		}
		catch (SiebelException siebelEx) {
			responseBean.setCode(siebelEx.getErrorCode());
			responseBean.setMsg(siebelEx.getMessage());
		}
		catch (ValidationException validEx) {
			responseBean.setCode(validEx.getErrorCode());
			responseBean.setMsg(validEx.getMessage());
		}
		catch (DatabaseException dbEx) {
			responseBean.setCode(dbEx.getErrorCode());
			responseBean.setMsg(dbEx.getMessage());
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			responseBean.setTransactionId(transID);
			
			if (errorCodeResp != null) {
				responseBean.setCode(errorCodeResp.getErrorCode());
				responseBean.setMsg(errorCodeResp.getErrorMessage());
			}
			
			logger.writeResponseMsg(transID, responseBean);
			logger.logResponseRESTProvider(transID, responseBean.getCode(), startREST);
		}

		return responseBean;
	}

	public ResponseBean updateCustomerAccount(String transID, String ban, String accountServiceLevel
		, String accountType, String customerRowId) {
		
		ErrorCodeResp errorCodeResp = null;
		String url = null;
		ResponseBean responseBean = new ResponseBean();

		try {
			// Validate
			if (StringUtil.isEmpty(ban)) {
				errorCodeResp = this.assetErrorCode.generateRequiredField("BAN");
				return responseBean;
			}

			// Update CA
			url = CassandraConfig.getVal("inbound.account.createUpdateAccount.url");
			
			AssetUtil assetUtil = new AssetUtil();
			JSONObject jsonObjectRequest = assetUtil.generateCreateUpdateAccountReq(transID, ban, accountServiceLevel, accountType, customerRowId);

			String requestXML = jsonObjectRequest.toString(1);
			
			CRMInboundProxy crmInboundProxy = new CRMInboundProxy();
			String responseXML = crmInboundProxy.createUpdateAccount(transID, requestXML, url);
			
			AccountResponseBean accountResponseBean = new AccountResponseBean();
			accountResponseBean = accountResponseBean.stringToObject(responseXML);

			responseBean.setCode(accountResponseBean.getCode());
			responseBean.setMsg(accountResponseBean.getMsg());
			responseBean.setTransID(accountResponseBean.getTransactionId());
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			responseBean.setTransID(transID);
			
			if (errorCodeResp != null) {
				responseBean.setCode(errorCodeResp.getErrorCode());
				responseBean.setMsg(errorCodeResp.getErrorMessage());
			}
		}
		
		return responseBean;
	}
	
	@RequestMapping(value="/upsertAssetRedZone", method=RequestMethod.POST)
	public AssetResponseBean upsertAssetRedZone(@RequestHeader(value="SiebelAuthorization") String siebelAuthorize
		, @RequestBody RedZoneReqBean redZoneReqBean){

		AssetResponseBean responseBean = new AssetResponseBean();
		ErrorCodeResp errorCodeResp = null;
		String transID = null;
		Calendar startREST = null;
		String goldenId = null;
		try {
			
			transID = UUIDManager.getUUID().toString();
			logger.info(transID, "### Start upsertAssetRedZone ###");
			
			startREST = logger.logRequestRESTProvider(transID);
			logger.writeRequestMsg(transID, this.httpRequest.getRequestURL().toString(), redZoneReqBean);
			
			logger.info(transID, "JSON Request : "+redZoneReqBean.objectToString(redZoneReqBean));
			
			// validate
			this.assetValidation.validateUpsertAssetRedZone(redZoneReqBean);

			// Mapping
			this.assetMapping.mappingupsertAssetRedZone(redZoneReqBean);
			
			AssetDataBean resultAssetdata = new AssetDataBean();
			AssetLogic assetLogic = new AssetLogic();
			AuthenticationUtil authenticationUtil = new AuthenticationUtil();
			SiebelAuthorizationBean siebelAuthenBean = authenticationUtil.getSiebelAuthorization(siebelAuthorize);
			
			AssetDataBean assetDataBean = redZoneReqBean.getAsset();
			
			logger.info(transID, "isDummyMsisdnFlag "+assetDataBean.isDummyMsisdnFlag());
			logger.info(transID, "productType "+assetDataBean.getProductType());
			
			if(assetDataBean.isDummyMsisdnFlag()) {
				logger.info(transID, "A.Dummy Msisdn Prepaid Only[Order Type 34]");
				logger.info(transID, "1.Upsert CA "+assetDataBean.isDummyMsisdnFlag());
				
				CustomerAccountBean customerAccountBean = assetLogic.createUpdateCustomerIndyRedZone(redZoneReqBean);
				
				if(!Constants.ErrorCode.SUCCESS_CODE_EAI.equals(customerAccountBean.getErrorCode())) {
					throw new CRMESBException(customerAccountBean.getErrorCode(),customerAccountBean.getErrorMsg());
				}
				
				goldenId = customerAccountBean.getGoldenId();
				logger.info(transID, "GoldenId : "+goldenId);
				
				redZoneReqBean.getAsset().setGoldenId(goldenId);
				
				logger.info(transID, "2.Upsert Asset redzone profile");
				resultAssetdata = assetLogic.createUpdateAssetMobileRedZone(redZoneReqBean,siebelAuthenBean);
			}
			else if(redZoneReqBean.getCustomerAccount() == null) {
				
				logger.info(transID, "B.CustomerAccount is null");
				AssetFindByBean assetFindByBean = new AssetFindByBean();
				
				if ("Prepay".equals(assetDataBean.getProductType())) { 
					/* old
					assetFindByBean.setIntegrationId("A"+redZoneReqBean.getAsset().getServiceId());
					*/
					
					/*
					 * Prepaid Redesign
					 */
					
					String[] status = {Constants.AssetStatus.ALL_ACTIVE};
					assetFindByBean.setServiceID(redZoneReqBean.getAsset().getServiceId());
					assetFindByBean.setStatus(status);
					
					/*
					 * End 
					 */
				}
				else {
					assetFindByBean.setAssetNo("CMH"+redZoneReqBean.getAsset().getSubscriberNo());
					
				}
				assetFindByBean.setPageNo(1);
				assetFindByBean.setPageSize(1);	
				
				logger.info(transID, "1.Query Asset Prepay(assetNo) : "+ assetFindByBean.toString());
				
				AssetRespBean assetRespBean = assetDAO.getAssetRootList(assetFindByBean, null);
				
				logger.info(transID, "response  : "+ assetRespBean.getCode()+","+assetRespBean.getMsg());
				
				/*
				 * Prepaid Redesign
				 */
				if ("Prepay".equals(assetDataBean.getProductType())) {
					
					if(!Constants.ErrorCode.SUCCESS_CODE.equals(assetRespBean.getCode())) {
						responseBean.setCode(assetRespBean.getCode());
						responseBean.setMsg(assetRespBean.getMsg());
						return responseBean;
					}
					
					redZoneReqBean.getAsset().setAssetId(assetRespBean.getAssetBeanList()[0].getAssetRowID());
				}
				/*
				 * End 
				 */
				
				if(Constants.ErrorCode.SUCCESS_CODE.equals(assetRespBean.getCode())) {
					if(assetRespBean.getTotalRecords() != 0) {
						AssetBean assetBean = assetRespBean.getAssetBeanList()[0];
						if(assetBean.getCustomerAccountBean() != null) {
							if(validator.hasStringValue(assetBean.getCustomerAccountBean().getGoldenId())
									&& validator.hasStringValue(assetBean.getCustomerAccountBean().getIntegrationID())) {
								redZoneReqBean.getAsset().setGoldenId(assetBean.getCustomerAccountBean().getGoldenId());
							}
						}
					}
				}
					
				logger.info(transID, "2.Upsert Asset redzone profile");
				resultAssetdata = assetLogic.createUpdateAssetMobileRedZone(redZoneReqBean,siebelAuthenBean);
			}
			else {
				logger.info(transID, "C.Update profile Redzone");
				AssetFindByBean assetFindByBean = new AssetFindByBean();
				
				CustomerAccountBean customerAccount = redZoneReqBean.getCustomerAccount();			
				assetDAO.setTransID(transID);
				
				if ("Prepay".equals(assetDataBean.getProductType())) { 
					
					/* old
					assetFindByBean.setIntegrationId("A"+redZoneReqBean.getAsset().getServiceId());
					*/
					
					/*
					 * Prepaid Redesign
					 */
					
					String[] status = {Constants.AssetStatus.ALL_ACTIVE};
					assetFindByBean.setServiceID(redZoneReqBean.getAsset().getServiceId());
					assetFindByBean.setStatus(status);
					
					/*
					 * End 
					 */
					assetFindByBean.setIdNo(customerAccount.getIdNumber());
					assetFindByBean.setPageNo(1);
					assetFindByBean.setPageSize(1);		
					
					logger.info(transID, "1.Query Asset Prepay(integrationId,idNo) : ("
							+ redZoneReqBean.getAsset().getServiceId() + "," + customerAccount.getIdNumber() + ")");
					
					AssetRespBean assetRespBean = assetDAO.getAssetRootList(assetFindByBean, null);
					
					logger.info(transID, "Result : "+assetRespBean.toString());
					
					/*
					 * Prepaid Redesign
					 */
					if ("Prepay".equals(assetDataBean.getProductType())) {
						
						if(!Constants.ErrorCode.SUCCESS_CODE.equals(assetRespBean.getCode())) {
							responseBean.setCode(assetRespBean.getCode());
							responseBean.setMsg(assetRespBean.getMsg());
							return responseBean;
						}
						
						redZoneReqBean.getAsset().setAssetId(assetRespBean.getAssetBeanList()[0].getAssetRowID());
					}
					/*
					 * End 
					 */
					
					if(Constants.ErrorCode.SUCCESS_CODE.equals(assetRespBean.getCode())) {
						logger.info(transID, "2.Upsert Asset redzone profile when data found");
						resultAssetdata = assetLogic.createUpdateAssetMobileRedZone(redZoneReqBean,siebelAuthenBean);
					}
					else {
						logger.info(transID, "2.Upsert CA when data not found");
						CustomerAccountBean customerAccountBean = assetLogic.createUpdateCustomerIndyRedZone(redZoneReqBean);
						
						if(!Constants.ErrorCode.SUCCESS_CODE_EAI.equals(customerAccountBean.getErrorCode())) {
							throw new CRMESBException(customerAccountBean.getErrorCode(),customerAccountBean.getErrorMsg());
						}
						goldenId = customerAccountBean.getGoldenId();
						redZoneReqBean.getAsset().setGoldenId(goldenId);
						logger.info(transID, "GoldenId : "+goldenId);
						
						logger.info(transID, "3.Upsert Asset redzone profile");
						resultAssetdata = assetLogic.createUpdateAssetMobileRedZone(redZoneReqBean,siebelAuthenBean);
					}
				}
				else {
					assetFindByBean.setAssetNo("CMH"+redZoneReqBean.getAsset().getSubscriberNo());
					assetFindByBean.setIdNo(customerAccount.getIdNumber());
					assetFindByBean.setPageNo(1);
					assetFindByBean.setPageSize(1);	
					
					logger.info(transID, "1.Query Asset Prepay(assetNo,idNo) : ("
							+ assetFindByBean.getAssetNo() + "," + customerAccount.getIdNumber() + ")");
					
					AssetRespBean assetRespBean = assetDAO.getAssetRootList(assetFindByBean, null);
					
					if (Constants.ErrorCode.SUCCESS_CODE.equals(assetRespBean.getCode())) {
						logger.info(transID, "2.Upsert Asset redzone profile when data found");
						resultAssetdata = assetLogic.createUpdateAssetMobileRedZone(redZoneReqBean,siebelAuthenBean);
					}
					else {
						logger.info(transID, "2.Upsert CA when data not found");
						CustomerAccountBean customerAccountBean = assetLogic.createUpdateCustomerIndyRedZone(redZoneReqBean);
						
						if(!Constants.ErrorCode.SUCCESS_CODE_EAI.equals(customerAccountBean.getErrorCode())) {
							throw new CRMESBException(customerAccountBean.getErrorCode(),customerAccountBean.getErrorMsg());
						}
						
						goldenId = customerAccountBean.getGoldenId();
						redZoneReqBean.getAsset().setGoldenId(goldenId);
						logger.info(transID, "GoldenId : "+goldenId);
						
						logger.info(transID, "3.Upsert Asset redzone profile");
						resultAssetdata = assetLogic.createUpdateAssetMobileRedZone(redZoneReqBean,siebelAuthenBean);
					}
				}
			}
			
			responseBean.setAsset(resultAssetdata);
			responseBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
			responseBean.setMsg(Constants.SUCCESS_MSG);
			responseBean.setTransactionId(transID);
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			responseBean.setTransactionId(transID);

			if (errorCodeResp != null) {
				responseBean.setCode(errorCodeResp.getErrorCode());
				responseBean.setMsg(errorCodeResp.getErrorMessage());
			}

			logger.writeResponseMsg(transID, responseBean);
			logger.logResponseRESTProvider(transID, responseBean.getCode(), startREST);
		}

		return responseBean;
	}
	
	@RequestMapping(value="/upsertAssetRelationship", method=RequestMethod.POST)
	public ResponseBean upsertAssetRelationship(@RequestHeader(value="SiebelAuthorization") String siebelAuthorize
		, @RequestBody AssetRelationShipReqBean assetRelationShipReqBean) {
		
		ResponseBean responseBean = new ResponseBean();
		ErrorCodeResp errorCodeResp = null;
		String transID = null;
		Calendar startREST = null;

		try {
			transID = UUIDManager.getUUID().toString();

			startREST = logger.logRequestRESTProvider(transID);
			logger.writeRequestMsg(transID, this.httpRequest.getRequestURL().toString(), assetRelationShipReqBean);
			
			this.assetValidation.validateUpsertAssetRelationship(assetRelationShipReqBean);

			AuthenticationUtil authenticationUtil = new AuthenticationUtil();
			SiebelAuthorizationBean siebelAuthenBean = authenticationUtil.getSiebelAuthorization(siebelAuthorize);
			
			AssetLogic assetLogic = new AssetLogic();
			responseBean = assetLogic.createUpdateAssetRelationship(assetRelationShipReqBean,siebelAuthenBean);
		}
		catch (SiebelException siebelEx) {
			responseBean.setCode(siebelEx.getErrorCode());
			responseBean.setMsg(siebelEx.getMessage());
		}
		catch (ValidationException validEx) {
			responseBean.setCode(validEx.getErrorCode());
			responseBean.setMsg(validEx.getMessage());
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			responseBean.setTransID(transID);

			if (errorCodeResp != null) {
				responseBean.setCode(errorCodeResp.getErrorCode());
				responseBean.setMsg(errorCodeResp.getErrorMessage());
			}

			logger.writeResponseMsg(transID, responseBean);
			logger.logResponseRESTProvider(transID, responseBean.getCode(), startREST);
		}

		return responseBean;
	}	
	
	/**
	 * @param assetFindByBean
	 * @return assetRespBean
	 */
	@RequestMapping(value = "/getConvergenceRelation", method = RequestMethod.POST)
	public AssetRespBean getConvergenceRelation(@RequestBody AssetFindByBean assetFindByBean) {
		AssetRespBean assetRespBean = new AssetRespBean();
		ErrorCodeResp errorCodeResp = null;
		String transID = null;
		Calendar startREST = null;

		try {
			transID = UUID.randomUUID().toString();
			startREST = logger.logRequestRESTProvider(transID);
			logger.writeRequestMsg(transID, this.httpRequest.getRequestURL().toString(), assetFindByBean);

			this.assetValidation.validateGetConverganceRelation(assetFindByBean);

			assetDAO.setTransID(transID);
			//assetRespBean = assetDAO.getLatestAssetRoot(assetFindByBean);
			assetRespBean = assetDAO.getAssetRootList(assetFindByBean, null);

			if (Constants.ErrorCode.SUCCESS_CODE.equals(assetRespBean.getCode())) {

				for (AssetBean assetBean : assetRespBean.getAssetBeanList()) {

					ConvergenceRelationReqBean coverganceRelationReqBean = new ConvergenceRelationReqBean();
					coverganceRelationReqBean.setParAssetRowID(assetBean.getAssetRowID());

					ConvergenceRelationRespBean coverganceRelationRespBean = assetDAO.getConverganceRelation(coverganceRelationReqBean);

					if (Constants.ErrorCode.SUCCESS_CODE.equals(coverganceRelationRespBean.getCode())) {
						assetBean.setCoverganceRelationBean(coverganceRelationRespBean.getConvergenceRelationBean());
					}
					else if (this.assetErrorCode.isDataNotFound(coverganceRelationRespBean.getCode())) {
						assetRespBean.setCode(coverganceRelationRespBean.getCode());
						assetRespBean.setMsg(coverganceRelationRespBean.getMsg());
						assetRespBean.setAssetBeanList(null);
					}
				}
			}
		}
		catch (ValidationException validEx) {
			assetRespBean.setCode(validEx.getErrorCode());
			assetRespBean.setMsg(validEx.getMessage());
		}
		catch (DatabaseException dbEx) {
			assetRespBean.setCode(dbEx.getErrorCode());
			assetRespBean.setMsg(dbEx.getMessage());
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			assetRespBean.setTransID(transID);

			if (errorCodeResp != null) {
				assetRespBean.setCode(errorCodeResp.getErrorCode());
				assetRespBean.setMsg(errorCodeResp.getErrorMessage());
			}

			logger.writeResponseMsg(transID, assetRespBean);
			logger.logResponseRESTProvider(transID, assetRespBean.getCode(), startREST);
		}

		return assetRespBean;
	}
	
	@RequestMapping(value="/changeDummyMSISDNPrepay", method=RequestMethod.POST)
	public ResponseBean changeDummyMSISDNPrepay(@RequestHeader(value="SiebelAuthorization") String siebelAuthorize
		, @RequestBody DummyMsisdnReqBean dummyMsisdnReqBean) {
		
		ResponseBean responseBean = new ResponseBean();
		ErrorCodeResp errorCodeResp = null;
		String transID = null;
		Calendar startREST = null;
		String xmlRequest = null;
		String assetRowID = null;
		String goldenId = null;
		boolean newAssetFlag = false;
		
		try {
			transID = UUIDManager.getUUID().toString();

			startREST = logger.logRequestRESTProvider(transID);
			logger.writeRequestMsg(transID, this.httpRequest.getRequestURL().toString(), dummyMsisdnReqBean);
			
			this.assetValidation.validateChangeDummyMSISDNPrepay(dummyMsisdnReqBean);
			
			AssetFindByBean assetFindByBean = new AssetFindByBean();
			assetFindByBean.setServiceID(dummyMsisdnReqBean.getDummyServiceID());
			
			logger.info(transID,"1.getLatestAssetRoot DCRM");
			AssetRespBean assetRespBean = this.assetDAO.getLatestAssetRoot(assetFindByBean, null);
			
			//Query OLCRMPRD when data not found in DCRM
			if (assetRespBean.getCode().contains(Constants.ErrorCode.DATA_NOT_FOUND)) {
				logger.info(transID,"2.getLatestAssetRoot OLCRMPRD");
				assetRespBean = this.assetDAO.getLatestAssetRoot(assetFindByBean, olcrmDS, null);
			}
			
			//notfound api int ccbs
			if (assetRespBean.getCode().contains(Constants.ErrorCode.DATA_NOT_FOUND)) {
				
				logger.info(transID,"3.geAllPrepaidProfileList");
				xmlRequest = INTRestTransform.generateAllPrepaidProfileList(dummyMsisdnReqBean.getDummyServiceID());
				logger.info(transID,"get AllPrepaidProfileList request : "+xmlRequest);
				INTProxy intProxy = new INTProxy();
				HttpResponse httpResponse = intProxy.getAllPrepaidProfileList(transID, xmlRequest);
				
				logger.info(transID,"get AllPrepaidProfileList reponse : "+httpResponse.getResponseText());
				
				JSONObject jsonRespObj = new JSONObject(httpResponse.getResponseText());
				JSONObject returnJSonObj = jsonRespObj.getJSONObject("getAllPrepaidProfileListResponse").getJSONObject("return");
				
				if (Constants.ErrorCode.INT_NOT_FOUND_CODE.equals(returnJSonObj.getString("errorCode"))) {
					throw new INTRestException(returnJSonObj.getString("errorCode"),returnJSonObj.getString("errorCode"));
				}				

				RedZoneReqBean redZoneReqBean = new RedZoneReqBean();
				redZoneReqBean = CRMESBTransform.generateCreateUpdateCustomerIndyRedZoneOrdertype35(httpResponse.getResponseText());
				
				logger.info(transID,"3.1 createUpdateCustomer");
				AssetLogic assetLogic = new AssetLogic();
				CustomerAccountBean customerAccountBean = assetLogic.createUpdateCustomerIndyRedZone(redZoneReqBean);
				
				if(!Constants.ErrorCode.SUCCESS_CODE_EAI.equals(customerAccountBean.getErrorCode())) {
					throw new CRMESBException(customerAccountBean.getErrorCode(),customerAccountBean.getErrorMsg());
				}
				
				goldenId = customerAccountBean.getGoldenId();
				logger.info(transID, "GoldenId : "+goldenId);
				newAssetFlag = true;
			}		
			
			AuthenticationUtil authenticationUtil = new AuthenticationUtil();
			SiebelAuthorizationBean siebelAuthenBean = authenticationUtil.getSiebelAuthorization(siebelAuthorize);
			
			if(!newAssetFlag) { 
				AssetBean assetBean = assetRespBean.getAssetBeanList()[0];
				ProductBean productBean = assetBean.getProductBean();
				
				assetRowID = assetBean.getAssetRowID();
				logger.info(transID, "Found AssetRowID: " + assetRowID + ", ProductType: " + productBean.getProductType()
							+ ", DummyServiceID: " + dummyMsisdnReqBean.getDummyServiceID());
				
				if (!Constants.ProductType.PREPAY.equalsIgnoreCase(productBean.getProductType())) {
					errorCodeResp = this.assetErrorCode.generateBusinessLogic("DummyServiceID " + dummyMsisdnReqBean.getDummyServiceID() 
									+ " is not " + Constants.ProductType.PREPAY + " product");
					return responseBean;
				}
				xmlRequest = SiebelTransform.generateChangeDummyMSISDNPrepay(siebelAuthenBean, dummyMsisdnReqBean, assetRowID, goldenId);
			}
			else {
				xmlRequest = SiebelTransform.generateNewDummyMSISDNPrepay(siebelAuthenBean, dummyMsisdnReqBean, goldenId);
			}			
			
			SiebelProxy siebelProxy = new SiebelProxy();
			siebelProxy.syncAsset(transID, xmlRequest);
			
			responseBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
			responseBean.setMsg(Constants.SUCCESS_MSG);
		}
		catch (SiebelException siebelEx) {
			responseBean.setCode(siebelEx.getErrorCode());
			responseBean.setMsg(siebelEx.getMessage());
		}
		catch (ValidationException validEx) {
			responseBean.setCode(validEx.getErrorCode());
			responseBean.setMsg(validEx.getMessage());
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			responseBean.setTransID(transID);

			if (errorCodeResp != null) {
				responseBean.setCode(errorCodeResp.getErrorCode());
				responseBean.setMsg(errorCodeResp.getErrorMessage());
			}

			logger.writeResponseMsg(transID, responseBean);
			logger.logResponseRESTProvider(transID, responseBean.getCode(), startREST);
		}

		return responseBean;
	}
	
	@RequestMapping(value="/upsertPromiseToPay", method=RequestMethod.POST)
	public AssetResponseBean upsertPromiseToPay(@RequestHeader(value="SiebelAuthorization") String siebelAuthorize
		, @RequestBody PromiseToPayReqBean promiseToPayReqBean){
		
		AssetResponseBean responseBean = new AssetResponseBean();
		ErrorCodeResp errorCodeResp = null;
		String transID = null;
		Calendar startREST = null;
		
		try {
			if (promiseToPayReqBean != null) {
				transID = promiseToPayReqBean.getTransactionId();
			}
			
			if (StringUtil.isEmpty(transID)) {
				transID = UUIDManager.getUUID().toString();
			}
			
			startREST = logger.logRequestRESTProvider(transID);
			logger.writeRequestMsg(transID, this.httpRequest.getRequestURL().toString(), promiseToPayReqBean);
			
			// Validation
			this.assetValidation.validateUpsertPromiseToPay(promiseToPayReqBean);
			
			// Find AssetRowID
			AssetDataBean assetDataBean = promiseToPayReqBean.getAsset();
			
			if (!this.validator.hasStringValue(assetDataBean.getAssetId())) {
				
				AssetFindByBean assetFindByBean = new AssetFindByBean();
				assetFindByBean.setServiceID(assetDataBean.getServiceId());
				assetFindByBean.setBan(assetDataBean.getBan());
				
				this.assetDAO.setTransID(transID);
				AssetRespBean assetRespBean = this.assetDAO.getLatestAssetRootRowID(assetFindByBean);
				
				if (!Constants.ErrorCode.SUCCESS_CODE.equals(assetRespBean.getCode())) {
					responseBean.setCode(assetRespBean.getCode());
					responseBean.setMsg(assetRespBean.getMsg());
					return responseBean;
				}
				
				promiseToPayReqBean.getAsset().setAssetId(assetRespBean.getAssetBeanList()[0].getAssetRowID());
			}
			
			AuthenticationUtil authenticationUtil = new AuthenticationUtil();
			SiebelAuthorizationBean siebelAuthenBean = authenticationUtil.getSiebelAuthorization(siebelAuthorize);
			
			SiebelProxy siebelProxy = new SiebelProxy();
			HttpResponse response = siebelProxy.upsertPromiseToPay(transID, promiseToPayReqBean, siebelAuthenBean);
			
			// Read output of Siebel
			Xpath respXpath = new Xpath(response.getResponseText());
			
			String errorCode = respXpath.getVal("/Envelope/Body/UpsertAssetXM_Output/Error_spcCode");
			String errorMessage = respXpath.getVal("/Envelope/Body/UpsertAssetXM_Output/Error_spcMessage");
			
			responseBean.setCode(errorCode);
			responseBean.setMsg(errorMessage);
		}
		catch (ValidationException validEx) {
			responseBean.setCode(validEx.getErrorCode());
			responseBean.setMsg(validEx.getMessage());
		}
		catch (SiebelException sbEx) {
			responseBean.setCode(sbEx.getErrorCode());
			responseBean.setMsg(sbEx.getMessage());
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			responseBean.setTransactionId(transID);

			if (errorCodeResp != null) {
				responseBean.setCode(errorCodeResp.getErrorCode());
				responseBean.setMsg(errorCodeResp.getErrorMessage());
			}
			
			logger.writeResponseMsg(transID, responseBean);
			logger.logResponseRESTProvider(transID, responseBean.getCode(), startREST);
		}
		
		return responseBean;
	}
	
	@RequestMapping(value="/countCapMax", method=RequestMethod.POST)
	public CapMaxRespBean countCapMax(@RequestBody CapMaxReqBean capMaxReqBean) {
		CapMaxRespBean capMaxRespBean = new CapMaxRespBean();
		ErrorCodeResp errorCodeResp = null;
		String transID = null;
		Calendar startREST = null;
		
		try {
			transID = UUIDManager.getUUID().toString();
			
			startREST = logger.logRequestRESTProvider(transID);
	    	logger.writeRequestMsg(transID, this.httpRequest.getRequestURL().toString(), capMaxReqBean);
	    	
	    	// Validation
	    	this.assetValidation.validateCountCapMax(capMaxReqBean);
	    				
	    	AssetFindByBean assetFindByBean = new AssetFindByBean();
	    	assetFindByBean.setServiceID(capMaxReqBean.getServiceID());
	    	assetFindByBean.setBan(capMaxReqBean.getBan());
	    	assetFindByBean.setMasterFlag(capMaxReqBean.getMasterFlag());
	    	
	    	this.assetDAO.setTransID(transID);
	    	AssetRespBean assetRespBean = this.assetDAO.getLatestAssetRootRowID(assetFindByBean);
	    	
	    	if (!Constants.ErrorCode.SUCCESS_CODE.equals(assetRespBean.getCode())) {
	    		capMaxRespBean.setCode(assetRespBean.getCode());
				capMaxRespBean.setMsg(assetRespBean.getMsg());
				return capMaxRespBean;
	    	}
	    	
	    	AssetBean assetBean = assetRespBean.getAssetBeanList()[0];
	    	String assetRowID = assetBean.getAssetRowID();
	    	
	    	capMaxRespBean = this.assetDAO.countCapMax(assetRowID, capMaxReqBean.getCycleDate()
	    					, capMaxReqBean.getCpcCategoryCodeList());
		}
		catch (ValidationException validEx) {
			capMaxRespBean.setCode(validEx.getErrorCode());
			capMaxRespBean.setMsg(validEx.getMessage());
		}
		catch (DatabaseException dbEx) {
			capMaxRespBean.setCode(dbEx.getErrorCode());
			capMaxRespBean.setMsg(dbEx.getMessage());
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			capMaxRespBean.setTransID(transID);
			
			if (errorCodeResp != null) {
				capMaxRespBean.setCode(errorCodeResp.getErrorCode());
				capMaxRespBean.setMsg(errorCodeResp.getErrorMessage());
			}
			
			logger.writeResponseMsg(transID, capMaxRespBean);
			logger.logResponseRESTProvider(transID, capMaxRespBean.getCode(), startREST);
		}
		
		return capMaxRespBean;
	}
	
	@RequestMapping(value="/getLiving", method=RequestMethod.POST)
	public LivingRespBean getLiving(@RequestBody LivingReqBean livingReqBean) {
		LivingRespBean respBean = new LivingRespBean();
		ErrorCodeResp errorCodeResp = null;
		String transID = null;		
		Calendar startREST = null;	
		
		try {
			transID = UUID.randomUUID().toString();
			startREST = logger.logRequestRESTProvider(transID);
			logger.writeRequestMsg(transID, this.httpRequest.getRequestURL().toString(), livingReqBean);
			
			this.assetValidation.validateGetLiving(livingReqBean);
			
			this.assetDAO.setTransID(transID);
			respBean = this.assetDAO.getLiving(livingReqBean);
		}
		catch (ValidationException validEx) {
			respBean.setCode(validEx.getErrorCode());
			respBean.setMsg(validEx.getMessage());			
		}
		catch (DatabaseException dbEx) {
			respBean.setCode(dbEx.getErrorCode());
			respBean.setMsg(dbEx.getMessage());
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			respBean.setTransID(transID);
			
			if (errorCodeResp != null) {
				respBean.setCode(errorCodeResp.getErrorCode());
				respBean.setMsg(errorCodeResp.getErrorMessage());
			}

			logger.writeResponseMsg(transID, respBean);
			logger.logResponseRESTProvider(transID, respBean.getCode(), startREST);
		}
		
		return respBean;
	}
	
	@RequestMapping(value="/getAssetComponentListForOrder", method=RequestMethod.POST)
    public AssetCompForOrderRespBean getAssetComponentListForOrder(@RequestBody AssetFindByBean assetFindByBean) throws Exception {
		AssetCompForOrderRespBean responseBean = new AssetCompForOrderRespBean();
		List<AssetCompForOrderBean> assetCompList = null;
		ErrorCodeResp errorCodeResp = null;
		String transID = null;
		Calendar startREST = null;
		
		try {
	    	transID = UUID.randomUUID().toString();
	    	startREST = logger.logRequestRESTProvider(transID);
	    	logger.writeRequestMsg(transID, this.httpRequest.getRequestURL().toString(), assetFindByBean);
	    	
	    	this.assetValidation.validateGetAssetComponentListForOrder(assetFindByBean);
	    	
	    	// Find AssetRoot
			AssetFindByBean assetRootFindByBean = new AssetFindByBean();
			assetRootFindByBean.setAssetRowID(assetFindByBean.getAssetRowID());
			assetRootFindByBean.setOlprdDBFlag(assetFindByBean.getOlprdDBFlag());
			assetRootFindByBean.setPageNo(1);
			assetRootFindByBean.setPageSize(1);
			
			assetDAO.setTransID(transID);
			AssetRespBean assetRespBean = assetDAO.getAssetRootList(assetRootFindByBean, null);
			
			if (!Constants.ErrorCode.SUCCESS_CODE.equals(assetRespBean.getCode())) {
    			responseBean.setCode(assetRespBean.getCode());
    			responseBean.setMsg(assetRespBean.getMsg());
    			return responseBean;
    		}
    		
			String serviceId = assetRespBean.getAssetBeanList()[0].getServiceID();
			
			CCBSProxy ccbsProxy = new CCBSProxy();
			HttpResponse l9SearchHttpResponse = ccbsProxy.l9SearchPrepaidSubscriberByResource(transID, "getAssetComponentListForOrder", serviceId);
		
			logger.info(transID, "Call CCBS API l9SearchPrepaidSubscriberByResource Success");
			Xpath l9SearchRespXpath = new Xpath(l9SearchHttpResponse.getResponseText());
		
			String countService = l9SearchRespXpath.getVal("/Envelope/Body/l9SearchPrepaidSubscriberByResourceResponse[count(child::l9SearchPrepaidSubscriberByResourceReturn)>1]");
			String subNo = null;
			
			if (validator.hasStringValue(countService)) {
				// record > 1
				// Find subStatus='65' mean that Subscriber Active
				subNo = l9SearchRespXpath.getVal("/Envelope/Body/l9SearchPrepaidSubscriberByResourceResponse/l9SearchPrepaidSubscriberByResourceReturn[./hbpGeneralInfo/subStatus='65'][./hbpGeneralInfo/primResourceVal='"+serviceId+"']/hbpSubscriberNo/hbpSubscriberNo");
				
				if (!validator.hasStringValue(subNo)) {
					// Find max value of SubscriberNo
					NodeList l9SearchPrepaidList = l9SearchRespXpath.getList("/Envelope/Body/l9SearchPrepaidSubscriberByResourceResponse/l9SearchPrepaidSubscriberByResourceReturn");
					
					if (l9SearchPrepaidList == null || l9SearchPrepaidList.getLength() == 0) {
						String[] message = {"l9SearchPrepaidSubscriberByResourceReturn", "CCBSPrepaid"};
						errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);
						return responseBean;
					}
					
					for (int i = 1; i <= l9SearchPrepaidList.getLength(); i++) {
						String subNoMax = l9SearchRespXpath.getVal("/Envelope/Body/l9SearchPrepaidSubscriberByResourceResponse/l9SearchPrepaidSubscriberByResourceReturn[" + i + "]/hbpSubscriberNo/hbpSubscriberNo");
						subNo = AssetUtil.findMaxValueSubNo(subNo, subNoMax);
					}
				}
			}
			else {
				subNo = l9SearchRespXpath.getVal("/Envelope/Body/l9SearchPrepaidSubscriberByResourceResponse/l9SearchPrepaidSubscriberByResourceReturn[./hbpGeneralInfo/primResourceVal='"+serviceId+"']/hbpSubscriberNo/hbpSubscriberNo");
			}
			
			if (!validator.hasStringValue(subNo)) {
				String[] message = {"SubscriberNo", "CCBSPrepaid"};
				errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);
				return responseBean;
			}
			
			// Get profile By Token And SubNo
			HttpResponse l9GetHttpResponse = ccbsProxy.l9GetPrepaidSubscriber(transID, "getAssetComponentListForOrder", subNo);
			Xpath l9GetRespXpath = new Xpath(l9GetHttpResponse.getResponseText());
			
			String statusForXpath = "";
			if (assetFindByBean.getStatus() != null && assetFindByBean.getStatus().length > 0) {
				// set serviceAgreementList and resourceInfoList by status request
				String statusReq = Arrays.toString(assetFindByBean.getStatus());
				int statusActive = statusReq.indexOf("Active");
				int statusInactive = statusReq.indexOf("Inactive");
				
				if (statusActive != -1 && statusInactive == -1) {// status active
					statusForXpath = "[count(child::expirationDate)=0]";
				}
				else if (statusInactive != -1 && statusActive == -1) {// status inactive
					statusForXpath = "[count(child::expirationDate)>0]";
				}
			}
			
			NodeList serviceAgreementList = l9GetRespXpath.getList("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/serviceAgreementArray"+statusForXpath);
			NodeList resourceInfoList = l9GetRespXpath.getList("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/resourceInfoArray"+statusForXpath);
			NodeList serviceAgreementCompareList = l9GetRespXpath.getList("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/serviceAgreementArray[count(child::expirationDate)=0]");
			NodeList resourceInfoCompareList = l9GetRespXpath.getList("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/resourceInfoArray[count(child::expirationDate)=0]");
			
			if (serviceAgreementList == null || serviceAgreementList.getLength() == 0) {
				String[] message = {"ServiceAgreement", "CCBSPrepaid"};
				errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);
				return responseBean;
			}
			
			if (resourceInfoList == null || resourceInfoList.getLength() == 0) {
				String[] message = {"ResourceInfo", "CCBSPrepaid"};
				errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);
				return responseBean;
			}
			
			assetCompList = new ArrayList<AssetCompForOrderBean>();
			String assetRowID = CassandraConfig.getVal("asset.rowid.dummy");
			assetValidation.validateCaasandra("asset.rowid.dummy", assetRowID);
			
			for (int i = 1; i <= serviceAgreementList.getLength(); i++) {
				
				String soc = l9GetRespXpath.getVal("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/serviceAgreementArray" + statusForXpath + "["+ i + "]/soc");
				String effectiveDate = l9GetRespXpath.getVal("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/serviceAgreementArray" + statusForXpath + "["+ i + "]/effectiveDate");
				String expirationDate = l9GetRespXpath.getVal("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/serviceAgreementArray" + statusForXpath + "["+ i + "]/expirationDate");
				
				assetDAO.setTransID(transID);
				AssetCompRespBean assetCompRespBean = assetDAO.getProductByCcbsSoc(soc);
				
				if (!Constants.ErrorCode.SUCCESS_CODE.equals(assetCompRespBean.getCode())) {
	    			responseBean.setCode(assetCompRespBean.getCode());
	    			responseBean.setMsg(assetCompRespBean.getMsg());
	    			return responseBean;
	    		}
				
				AssetCompBean assetCompBean = assetCompRespBean.getAssetComponentList()[0];
				
				AssetCompForOrderBean assetComp = new AssetCompForOrderBean();
				assetComp.setAssetRowID(assetRowID);
				assetComp.setPartNum(assetCompBean.getPartNum());
				assetComp.setOfferName(assetCompBean.getOfferName());
				assetComp.setProductType(assetCompBean.getProductType());
				assetComp.setEffectiveDate(SiebelTransform.componentConvertDateToReponseOmxFormat(effectiveDate));
				assetComp.setExpiryDate(SiebelTransform.componentConvertDateToReponseOmxFormat(expirationDate));
				assetComp.setCcbsSoc(soc);
				
				assetCompList.add(assetComp);
			}
			
			for (int i = 1; i <= resourceInfoList.getLength(); i++) {
				AssetCompForOrderBean assetComp = new AssetCompForOrderBean();
				String primResourceVal = l9GetRespXpath.getVal("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/resourceInfoArray" + statusForXpath + "["+ i + "]/primResourceVal");
				String primResourceTp = l9GetRespXpath.getVal("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/resourceInfoArray" + statusForXpath + "["+ i + "]/primResourceTp");
				String effectiveDate = l9GetRespXpath.getVal("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/resourceInfoArray" + statusForXpath + "["+ i + "]/effectiveDate");
				String expirationDate = l9GetRespXpath.getVal("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/resourceInfoArray" + statusForXpath + "["+ i + "]/expirationDate");
				String partNum = CassandraConfig.getVal("crm.product.name.primResourcetp.partNum."+primResourceTp);
				String offerName = CassandraConfig.getVal("crm.product.name.primResourcetp.offerName."+primResourceTp);
				String productType = CassandraConfig.getVal("crm.product.name.primResourcetp.productType."+primResourceTp);
				
				assetValidation.validateCaasandra("crm.product.name.primResourcetp.partNum."+primResourceTp, partNum);
				assetValidation.validateCaasandra("crm.product.name.primResourcetp.offerName."+primResourceTp, offerName);
				assetValidation.validateCaasandra("crm.product.name.primResourcetp.productType."+primResourceTp, productType);
				
				assetComp.setAssetRowID(assetRowID);
				
				if ("C".equalsIgnoreCase(primResourceTp)) {
					// primResourceTp = C add MSISDN-
					assetComp.setSerialNo(partNum + "-" + primResourceVal);
				}
				else {
					assetComp.setSerialNo(primResourceVal);
				}
				
				assetComp.setPartNum(partNum);
				assetComp.setOfferName(offerName);
				assetComp.setProductType(productType);
				assetComp.setEffectiveDate(SiebelTransform.componentConvertDateToReponseOmxFormat(effectiveDate));
				assetComp.setExpiryDate(SiebelTransform.componentConvertDateToReponseOmxFormat(expirationDate));
				
				assetCompList.add(assetComp);
			}
			
			logger.info(transID, "assetCompList for response : "+assetCompList.toString());
			
			// Get Asset Component List
			AssetFindByBean assetCompFindByBean = new AssetFindByBean();
			assetCompFindByBean.setAssetRowID(assetFindByBean.getAssetRowID());
			assetCompFindByBean.setOlprdDBFlag(assetFindByBean.getOlprdDBFlag());
			assetCompFindByBean.setStatus(new String[] {"Active"});
			
			assetDAO.setTransID(transID);
			AssetCompRespBean assetCompResp = assetDAO.getAssetComponentAttributeList(assetCompFindByBean);
			logger.info(transID, "GetAssetComponentAttributeList Status=Active : " + assetCompResp.toString());
			
			// Compare Asset Component
			Boolean addServiceFlag = Boolean.FALSE;
			Boolean removeServiceFlag = Boolean.FALSE;
			Boolean duplicateFlag = Boolean.FALSE;
			Boolean addResourceFlag = Boolean.FALSE;
			Boolean removeResourceFlag = Boolean.FALSE;
			
			if (serviceAgreementCompareList != null) {
				if (serviceAgreementCompareList.getLength() > 0) { // step compare serviceAgreemen
					List<String> socTmpList = new ArrayList<String>();
					for (int i = 1; i <= serviceAgreementCompareList.getLength(); i++) {// add soc list
						String socTmp = l9GetRespXpath.getVal("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/serviceAgreementArray[count(child::expirationDate)=0]["+ i + "]/soc");
						socTmpList.add(socTmp);
					}
					
					List<String> ccbsSocAssetList = new ArrayList<String>();
					for (AssetCompBean assetComp : assetCompResp.getAssetComponentList()) {// find record for remove
						if (this.validator.hasStringValue(assetComp.getCcbsSoc())) {
							String ccbsSoc = assetComp.getCcbsSoc();
							
							if (!socTmpList.contains(ccbsSoc)) {// compare crm to ccbs for remove
								removeServiceFlag = Boolean.TRUE;
    						} else {
    							int count = 0;
    							for (AssetCompBean v : assetCompResp.getAssetComponentList()) {// check duplicate for remove
    								if (this.validator.hasStringValue(v.getCcbsSoc())) {
    									if (v.getCcbsSoc().equalsIgnoreCase(ccbsSoc)) {
    										count++;
    									}
    								}
    							}
    							if(count > 1) {
    								duplicateFlag = Boolean.TRUE;
    							}
    						}
    						ccbsSocAssetList.add(ccbsSoc);// add ccbsSoc list
						}
					}
					
					for (int i = 1; i <= serviceAgreementCompareList.getLength(); i++) {// find index for add
						String socTmp = l9GetRespXpath.getVal("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/serviceAgreementArray[count(child::expirationDate)=0]["+ i + "]/soc");
						if (!ccbsSocAssetList.contains(socTmp)) {// compare ccbs to crm for add
							addServiceFlag = Boolean.TRUE;
							break;
						}
					}
				}
			}
			
			
			if (resourceInfoCompareList != null) {
				if (resourceInfoCompareList.getLength() > 0) { // step compare resourceInfo
					String prepaidResource = CassandraConfig.getVal("crm.prepaid.resource");// check product name for compare resourceInfo
					
					List<String> primResourceValList = new ArrayList<String>();
					for (int i = 1; i <= resourceInfoCompareList.getLength(); i++) {// add primResourceVal list
						String primResourceVal = l9GetRespXpath.getVal("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/resourceInfoArray[count(child::expirationDate)=0]["+ i + "]/primResourceVal");
						primResourceValList.add(primResourceVal);
					}
					
					List<String> serialNoAssetList = new ArrayList<String>();
					for (AssetCompBean assetComp : assetCompResp.getAssetComponentList()) {// find record remove
						if (prepaidResource.indexOf(assetComp.getProductName()) != -1) {
							String serialNo = assetComp.getSerialNo();
							
							if (serialNo.indexOf("MSISDN-") != -1) {// replace string
								serialNo = serialNo.replaceAll("MSISDN-", "");
							}
							if (!primResourceValList.contains(serialNo)) {// compare crm to ccbs for remove
								removeResourceFlag = Boolean.TRUE;
    						} else if(!duplicateFlag) {
    							int count = 0;
    							for(AssetCompBean v : assetCompResp.getAssetComponentList()) {// check duplicate for remove
    								if(prepaidResource.indexOf(v.getProductName()) != -1) {
    									if(v.getSerialNo().equalsIgnoreCase(serialNo)) {
    										count++;
    									}
    								}
    							}
    							if(count > 1) {
    								duplicateFlag = Boolean.TRUE;
    							}
    						}
    						serialNoAssetList.add(serialNo);// add SerialNo list
						}
					}
					
					for (int i = 1; i <= resourceInfoCompareList.getLength(); i++) {// find index for add
						String primResourceVal = l9GetRespXpath.getVal("/Envelope/Body/l9GetPrepaidSubscriberResponse/l9GetPrepaidSubscriberReturn/resourceInfoArray[count(child::expirationDate)=0]["+ i + "]/primResourceVal");
						if(!serialNoAssetList.contains(primResourceVal)) {// compare ccbs to crm for add
							addResourceFlag = Boolean.TRUE;
							break;
						}
					}
				}
			}
			
			if (addServiceFlag || removeServiceFlag || addResourceFlag 
				|| removeResourceFlag || duplicateFlag) {
				logger.info(transID, "CompCompareResult : Diff");
				
				if(addServiceFlag) {
					logger.info(transID, "CompCompareCase : AddService");
				}
				if(removeServiceFlag) {
					logger.info(transID, "CompCompareCase : RemoveService");
				}
				if(addResourceFlag) {
					logger.info(transID, "CompCompareCase : AddResource");
				}
				if(removeResourceFlag) {
					logger.info(transID, "CompCompareCase : RemoveResource");
				}
				if(duplicateFlag) {
					logger.info(transID, "CompCompareCase : CRMDuplicate");
				}
			} else {
				logger.info(transID, "CompCompareResult : Equal");
				logger.info(transID, "CompCompareCase : Equal");
			}
			
			responseBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
			responseBean.setMsg(Constants.SUCCESS_MSG);
			responseBean.setAssetComponentList(assetCompList.toArray(new AssetCompForOrderBean[assetCompList.size()]));
    	}
		catch (ValidationException validEx) {
			responseBean.setCode(validEx.getErrorCode());
			responseBean.setMsg(validEx.getMessage());
		}
		catch (DatabaseException dbEx) {
			responseBean.setCode(dbEx.getErrorCode());
			responseBean.setMsg(dbEx.getMessage());
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			if (assetCompList != null) {
				assetCompList.clear();
				assetCompList = null;
			}
			
			responseBean.setTransID(transID);
			
			if (errorCodeResp != null) {
				responseBean.setCode(errorCodeResp.getErrorCode());
				responseBean.setMsg(errorCodeResp.getErrorMessage());
			}
			
			logger.writeResponseMsg(transID, responseBean);
			logger.logResponseRESTProvider(transID, responseBean.getCode(), startREST);
		}
		
    	return responseBean;
    }
	
//	20230803
	@RequestMapping(value="/deleteAssetComponentList", method=RequestMethod.POST)
    public DeleteComponentRespBean deleteAssetComponentList(@RequestBody DeleteComponentReqBean deleteComponentReqBean) {
		DeleteComponentRespBean respBean = new DeleteComponentRespBean();
		ErrorCodeResp errorCodeResp = null;
		String transID = null;		
		Calendar startREST = null;	
		
		try {
			transID = UUID.randomUUID().toString();
			startREST = logger.logRequestRESTProvider(transID);
			logger.writeRequestMsg(transID, this.httpRequest.getRequestURL().toString(), deleteComponentReqBean);
			
			this.assetValidation.validateDeleteAssetComponentList(deleteComponentReqBean);
			
			this.assetDAO.setTransID(transID);
			respBean = this.assetDAO.deleteAssetComponent(deleteComponentReqBean);
		}
		catch (ValidationException validEx) {
			respBean.setCode(validEx.getErrorCode());
			respBean.setMsg(validEx.getMessage());			
		}
		catch (DatabaseException dbEx) {
			respBean.setCode(dbEx.getErrorCode());
			respBean.setMsg(dbEx.getMessage());
		}
		catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generateApplication(t);
			logger.error(transID, errorCodeResp.getErrorMessage(), t);
		}
		finally {
			respBean.setTransID(transID);
			
			if (errorCodeResp != null) {
				respBean.setCode(errorCodeResp.getErrorCode());
				respBean.setMsg(errorCodeResp.getErrorMessage());
			}

			logger.writeResponseMsg(transID, respBean);
			logger.logResponseRESTProvider(transID, respBean.getCode(), startREST);
		}
		
		return respBean;
	
	}
}