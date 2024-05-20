package th.co.truecorp.crmdev.asset.proxy;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Properties;

import th.co.truecorp.crmdev.asset.AssetLogger;
import th.co.truecorp.crmdev.asset.bean.AssetCompBean;
import th.co.truecorp.crmdev.asset.bean.Constants;
import th.co.truecorp.crmdev.asset.bean.PromiseToPayReqBean;
import th.co.truecorp.crmdev.asset.bean.SiebelAuthorizationBean;
import th.co.truecorp.crmdev.asset.util.AssetErrorCode;
import th.co.truecorp.crmdev.asset.util.SiebelTransform;
import th.co.truecorp.crmdev.util.db.config.CassandraConfig;
import th.co.truecorp.crmdev.util.logging.LogProductName;
import th.co.truecorp.crmdev.util.logging.LogSystem;
import th.co.truecorp.crmdev.util.net.http.HttpRequest;
import th.co.truecorp.crmdev.util.net.http.HttpResponse;

public class SiebelProxy {

	private AssetLogger logger;
	private AssetErrorCode assetErrorCode;

	public SiebelProxy() throws UnknownHostException {
		this.logger = new AssetLogger(LogProductName.All, LogSystem.CRM_INBOUND, LogSystem.CRM_EAI);
		this.assetErrorCode = new AssetErrorCode();
	}

	public HttpResponse syncAsset(String transID, String xmlRequest) throws Exception {
		String siebelURL = CassandraConfig.getVal("siebel.url");
		String siebelMethod = "SyncAsset";
		
		logger.writeRequestMsg(transID, siebelURL, xmlRequest);
		Calendar startSiebel = logger.logRequestWSClient(transID, siebelURL, siebelMethod);
		
		Properties httpHeader = new Properties();
		httpHeader.setProperty("SOAPAction", "\"document/http://siebel.com/TRU1EAIAssetWS:SyncAsset\"");
		
		HttpRequest httpRequest = new HttpRequest();
		HttpResponse httpResponse = httpRequest.postXML(siebelURL, httpHeader, xmlRequest);
		
		logger.logResponseWSClient(transID, siebelURL, siebelMethod, "-", startSiebel);
		logger.writeResponseMsg(transID, httpResponse.getResponseText());
		
		if (httpResponse.getHttpStatusCode() != 200) {
			throw assetErrorCode.generateSiebelException(httpResponse, siebelURL, siebelMethod, Constants.ModuleCode.ASSET);
		}
		
		return httpResponse;
	}
	
	public HttpResponse upsertAssetRelationship(String transID, String xmlRequest) throws Exception {
		String siebelURL = CassandraConfig.getVal("siebel.url");
		String siebelMethod = "UpsertAssetRelationship";
		
		logger.writeRequestMsg(transID, siebelURL, xmlRequest);		
		Calendar startSiebel = logger.logRequestWSClient(transID, siebelURL, siebelMethod);
		
		Properties httpHeader = new Properties();
		httpHeader.setProperty("SOAPAction", "\"document/http://siebel.com/TRU2EAIUpsertAssetRelationshipWS:UpsertAssetRelationship\"");
		
		HttpRequest httpRequest = new HttpRequest();
		HttpResponse httpResponse = httpRequest.postXML(siebelURL, httpHeader, xmlRequest);
		
		logger.logResponseWSClient(transID, siebelURL, siebelMethod, "-", startSiebel);
		logger.writeResponseMsg(transID, httpResponse.getResponseText());
		
		if (httpResponse.getHttpStatusCode() != 200) {
			throw assetErrorCode.generateSiebelException(httpResponse, siebelURL, siebelMethod, Constants.ModuleCode.ASSET);
		}
		
		return httpResponse;
	}

	public HttpResponse upsertPromiseToPay(String transID, PromiseToPayReqBean promiseToPayReqBean,
			SiebelAuthorizationBean siebelAuthenBean) throws Exception {
		
		String siebelMethod = "UpsertAssetXM";
		String reqXml = SiebelTransform.getXMLUpsertPromiseToPay(promiseToPayReqBean,siebelAuthenBean);
		String siebelURL = CassandraConfig.getVal("siebel.url");
		
		logger.writeRequestMsg(transID, siebelURL, reqXml);
		Calendar startSiebel = logger.logRequestWSClient(transID, siebelURL, siebelMethod);
		
		Properties httpHeader = new Properties();
		httpHeader.setProperty("SOAPAction", "\"document/http://siebel.com/TRU2EAIAssetXMWS:UpsertAssetXM\"");
		
		HttpRequest httpRequest = new HttpRequest();
		HttpResponse response = httpRequest.postXML(siebelURL, httpHeader, reqXml);
		
		logger.logResponseWSClient(transID, siebelURL, siebelMethod, "-", startSiebel);
		logger.writeResponseMsg(transID, response.getResponseText());
		
		if (response.getHttpStatusCode() != 200) {
			throw assetErrorCode.generateSiebelException(response, siebelURL, siebelMethod, Constants.ModuleCode.ASSET);
		}
		
		return response;
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
	
	public HttpResponse l9SearchPrepaidSubscriberByResource(String transID, String method, String token, String serviceId) throws Exception {
		String url = CassandraConfig.getVal("ccbs.prepay.url");
		
		String xmlRequest = SiebelTransform.generateSearchPrepaidSubscriberByResourceReq(token, serviceId);
		logger.info(transID, " L9SearchPrepaidSubscriberByResource Request URL: " + url);
		logger.info(transID, " L9SearchPrepaidSubscriberByResource SOAP Request: " + xmlRequest);
		
		HttpRequest httpRequest = new HttpRequest();
		Properties httpHeader = new Properties();
		httpHeader.setProperty("SOAPAction", "");
		HttpResponse httpResponse = httpRequest.postXML(url, httpHeader, xmlRequest);
		logger.info(transID, "L9SearchPrepaidSubscriberByResource Response : "+httpResponse.getResponseText());
		
//		if (httpResponse.getHttpStatusCode() != 200) {
//			throw assetErrorCode.generateSiebelException(httpResponse, url, method, Constants.ModuleCode.ASSET);
//		}
		return httpResponse;
	}
	
	public HttpResponse l9GetPrepaidSubscriber(String transID, String method, String token, String subNo) throws Exception {
		String url = CassandraConfig.getVal("ccbs.prepay.url");
		
		String xmlRequest = SiebelTransform.generatel9GetPrepaidSubscriberReq(token, subNo);
		logger.info(transID, " L9GetPrepaidSubscriber Request URL: " + url);
		logger.info(transID, " L9GetPrepaidSubscriber SOAP Request: " + xmlRequest);
		
		HttpRequest httpRequest = new HttpRequest();
		Properties httpHeader = new Properties();
		httpHeader.setProperty("SOAPAction", "");
		HttpResponse httpResponse = httpRequest.postXML(url, httpHeader, xmlRequest);
		logger.info(transID, " L9GetPrepaidSubscriber Response: " + httpResponse.getResponseText());
		
//		if (httpResponse.getHttpStatusCode() != 200) {
//			throw assetErrorCode.generateSiebelException(httpResponse, url, method, Constants.ModuleCode.ASSET);
//		}
		return httpResponse;
	}
	
	public HttpResponse getTokenLogOutCCBS(String transID, String method, String token) throws Exception {
		String url = CassandraConfig.getVal("ccbs.login.url");
		
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
	
	public HttpResponse addRemoveComponentAsset(String transID, String method, String action, AssetCompBean assetComp) throws Exception {
		String url = CassandraConfig.getVal("siebel.url");
		
		String xmlRequest = SiebelTransform.generateAddRemoveComponentAsset(action, assetComp);
		logger.info(transID, " "+action+"ComponentAsset Request URL: " + url);
		logger.info(transID, " "+action+"ComponentAsset SOAP Request: " +xmlRequest);
		
		HttpRequest httpRequest = new HttpRequest();
		Properties httpHeader = new Properties();
		httpHeader.setProperty("SOAPAction", "\"document/http://siebel.com/TRU1EAIAssetWS:SyncAsset\"");
		HttpResponse httpResponse = httpRequest.postXML(url, httpHeader, xmlRequest);
		
		httpResponse.setHttpStatusCode(200);
		if (httpResponse.getHttpStatusCode() != 200) {
			throw assetErrorCode.generateSiebelException(httpResponse, url, method, Constants.ModuleCode.ASSET);
		}
		return httpResponse;
	}
}