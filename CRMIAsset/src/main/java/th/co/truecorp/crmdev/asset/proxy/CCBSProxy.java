package th.co.truecorp.crmdev.asset.proxy;

import java.net.UnknownHostException;
import java.util.Properties;

import th.co.truecorp.crmdev.asset.AssetLogger;
import th.co.truecorp.crmdev.asset.bean.Constants;
import th.co.truecorp.crmdev.asset.dao.CassandraTrans;
import th.co.truecorp.crmdev.asset.exception.SiebelException;
import th.co.truecorp.crmdev.asset.util.AssetErrorCode;
import th.co.truecorp.crmdev.asset.util.SiebelTransform;
import th.co.truecorp.crmdev.util.common.Validator;
import th.co.truecorp.crmdev.util.common.Xpath;
import th.co.truecorp.crmdev.util.common.bean.BackendInfo;
import th.co.truecorp.crmdev.util.common.bean.ErrorCodeResp;
import th.co.truecorp.crmdev.util.db.config.CassandraConfig;
import th.co.truecorp.crmdev.util.logging.LogProductName;
import th.co.truecorp.crmdev.util.logging.LogSystem;
import th.co.truecorp.crmdev.util.net.http.HttpRequest;
import th.co.truecorp.crmdev.util.net.http.HttpResponse;

public class CCBSProxy {

	private AssetLogger logger;
	private AssetErrorCode assetErrorCode;
	private Validator validator;
	
	public CCBSProxy() throws UnknownHostException {
		this.logger = new AssetLogger(LogProductName.All, LogSystem.CRM_INBOUND, LogSystem.CCBS);
		this.assetErrorCode = new AssetErrorCode();
		this.validator = new Validator();
	}
	
	public HttpResponse getTokenLoginCCBS(String transID, String method) throws Exception {
		String url = CassandraConfig.getVal("ccbs.login.url");
		String user = CassandraConfig.getVal("ccbs.user.crm");
		String password = CassandraConfig.getVal("ccbs.password.crm");
		
		String xmlRequest = SiebelTransform.generateLoginReq(user, password);
		logger.info(transID, " GetTokenLoginCCBS Request URL: " + url);
		logger.info(transID, " GetTokenLoginCCBS SOAP Request: " +xmlRequest);
		
		HttpRequest httpRequest = new HttpRequest();
		Properties httpHeader = new Properties();
		httpHeader.setProperty("SOAPAction", "");
		HttpResponse httpResponse = httpRequest.postXML(url, httpHeader, xmlRequest);
		
		if (httpResponse.getHttpStatusCode() != 200) {
			throw assetErrorCode.generateSiebelException(httpResponse, url, method, Constants.ModuleCode.ASSET);
		}
		
		return httpResponse;
	}
	
	public HttpResponse l9SearchPrepaidSubscriberByResource(String transID, String method, String serviceId) throws Exception {
		String url = CassandraConfig.getVal("ccbs.prepay.url");
		String token = CassandraTrans.getConfigVal("crm.ccbs.token");
		logger.info(transID, "Token CCBS from cassandra : " + token);
		
		String xmlRequest = SiebelTransform.generateSearchPrepaidSubscriberByResourceReq(token, serviceId);
		logger.info(transID, " L9SearchPrepaidSubscriberByResource Request URL: " + url);
		logger.info(transID, " L9SearchPrepaidSubscriberByResource SOAP Request: " + xmlRequest);
		
		HttpRequest httpRequest = new HttpRequest();
		Properties httpHeader = new Properties();
		httpHeader.setProperty("SOAPAction", "");
		HttpResponse httpResponse = httpRequest.postXML(url, httpHeader, xmlRequest);
		logger.info(transID, "L9SearchPrepaidSubscriberByResource Response : "+httpResponse.getResponseText());
		
		if (httpResponse.getHttpStatusCode() != 200) {
			Xpath l9SearchRespXpath = new Xpath(httpResponse.getResponseText());
			String faultCode = l9SearchRespXpath.getVal("/Envelope/Body/Fault/faultcode");
			
			if (this.validator.hasStringValue(faultCode)) {
				String faultMessage = l9SearchRespXpath.getVal("/Envelope/Body/Fault/faultstring");
				
				BackendInfo backendInfo = new BackendInfo();
				backendInfo.setErrorCode(faultCode);
				backendInfo.setErrorMessage(faultMessage);
				backendInfo.setServiceName(url);
				backendInfo.setMethodName(method);
				
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generateByAPI(Constants.ErrorCode.CRM_EAI_ERROR, backendInfo);
				throw new SiebelException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
			}
			else {
				throw assetErrorCode.generateSiebelException(httpResponse, url, method, Constants.ModuleCode.ASSET);
			}
		}
		
		return httpResponse;
	}
	
	public HttpResponse l9GetPrepaidSubscriber(String transID, String method, String subNo) throws Exception {
		String url = CassandraConfig.getVal("ccbs.prepay.url");
		String token = CassandraTrans.getConfigVal("crm.ccbs.token");
		logger.info(transID, "Token CCBS from cassandra : " + token);
		
		String xmlRequest = SiebelTransform.generatel9GetPrepaidSubscriberReq(token, subNo);
		logger.info(transID, " L9GetPrepaidSubscriber Request URL: " + url);
		logger.info(transID, " L9GetPrepaidSubscriber SOAP Request: " + xmlRequest);
		
		HttpRequest httpRequest = new HttpRequest();
		Properties httpHeader = new Properties();
		httpHeader.setProperty("SOAPAction", "");
		HttpResponse httpResponse = httpRequest.postXML(url, httpHeader, xmlRequest);
		logger.info(transID, " L9GetPrepaidSubscriber Response: " + httpResponse.getResponseText());
		
		if (httpResponse.getHttpStatusCode() != 200) {
			Xpath l9GetRespXpath = new Xpath(httpResponse.getResponseText());
			String faultCode = l9GetRespXpath.getVal("/Envelope/Body/Fault/faultcode");
			
			if (this.validator.hasStringValue(faultCode)) {
				String faultMessage = l9GetRespXpath.getVal("/Envelope/Body/Fault/faultstring");
				
				BackendInfo backendInfo = new BackendInfo();
				backendInfo.setErrorCode(faultCode);
				backendInfo.setErrorMessage(faultMessage);
				backendInfo.setServiceName(url);
				backendInfo.setMethodName(method);
				
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generateByAPI(Constants.ErrorCode.CRM_EAI_ERROR, backendInfo);
				throw new SiebelException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
			}
			else {
				throw assetErrorCode.generateSiebelException(httpResponse, url, method, Constants.ModuleCode.ASSET);
			}
		}
		
		return httpResponse;
	}
	
	public HttpResponse getTokenLogoutCCBS(String transID, String method) throws Exception {
		String url = CassandraConfig.getVal("ccbs.login.url");
		String token = CassandraTrans.getConfigVal("crm.ccbs.token");
		
		String xmlRequest = SiebelTransform.generateLogOutReq(token);
		logger.info(transID, " GetTokenLogOutCCBS Request URL: " + url);
		logger.info(transID, " GetTokenLogOutCCBS SOAP Request: " +xmlRequest);
		
		HttpRequest httpRequest = new HttpRequest();
		Properties httpHeader = new Properties();
		httpHeader.setProperty("SOAPAction", "");
		HttpResponse httpResponse = httpRequest.postXML(url, httpHeader, xmlRequest);
		
		if (httpResponse.getHttpStatusCode() != 200) {
			throw assetErrorCode.generateSiebelException(httpResponse, url, method, Constants.ModuleCode.ASSET);
		}
		
		return httpResponse;
	}
}