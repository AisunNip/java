package th.co.truecorp.crmdev.asset.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import th.co.truecorp.crmdev.asset.bean.Constants;
import th.co.truecorp.crmdev.asset.exception.CRMOsbException;
import th.co.truecorp.crmdev.asset.exception.INTRestException;
import th.co.truecorp.crmdev.asset.exception.SiebelException;
import th.co.truecorp.crmdev.util.common.ErrorCodeManager;
import th.co.truecorp.crmdev.util.common.Xpath;
import th.co.truecorp.crmdev.util.common.bean.BackendInfo;
import th.co.truecorp.crmdev.util.common.bean.ErrorCodeResp;
import th.co.truecorp.crmdev.util.net.http.HttpResponse;

public class AssetErrorCode {

	private ErrorCodeManager errorCodeManager;
	
	public AssetErrorCode() {
		this.errorCodeManager = ErrorCodeManager.getInstance();
	}
	
	public boolean isDataNotFound(String errorCode) {
		return this.errorCodeManager.isDataNotFound(errorCode);
	}

	public ErrorCodeResp generate(String errorCode, String errorMessage) {
		return this.errorCodeManager.generate(Constants.SystemCode.CRM_INBOUND
				, Constants.ModuleCode.ASSET, errorCode, errorMessage);
	}
	
	public ErrorCodeResp generate(String errorCode, String[] errorMessage) {
		return this.errorCodeManager.generate(Constants.SystemCode.CRM_INBOUND
				, Constants.ModuleCode.ASSET, errorCode, errorMessage);
	}
	
	public ErrorCodeResp generateByAPI(String errorCode, BackendInfo backendInfo) {
		return this.errorCodeManager.generateByAPI(Constants.SystemCode.CRM_INBOUND
				, Constants.ModuleCode.ASSET, errorCode, backendInfo);
	}
	
	public ErrorCodeResp generateRequiredField(String fieldName) {
		return this.errorCodeManager.generate(Constants.SystemCode.CRM_INBOUND
				, Constants.ModuleCode.ASSET, Constants.ErrorCode.REQUIRED_FIELDS
				, fieldName);
	}
	
	public ErrorCodeResp generateParamInvalid(String fieldName) {
		return this.errorCodeManager.generate(Constants.SystemCode.CRM_INBOUND
				, Constants.ModuleCode.ASSET, Constants.ErrorCode.INVALID_CODE
				, fieldName);
	}
	
	public ErrorCodeResp generateDataInvalid(String fieldName, String systemName) {
		String[] errorMsg = {fieldName, systemName};
		return this.errorCodeManager.generate(Constants.SystemCode.CRM_INBOUND
				, Constants.ModuleCode.ASSET, Constants.ErrorCode.DATA_INVALID_CODE
				, errorMsg);
	}
	
	public ErrorCodeResp generateBusinessLogic(String message) {
		return this.errorCodeManager.generate(Constants.SystemCode.CRM_INBOUND
				, Constants.ModuleCode.ASSET, Constants.ErrorCode.BUSINESS_CODE
				, message);
	}
	
	public ErrorCodeResp generateApplication(Throwable t) {
		String errorMessage[] = {Constants.APPLICATION_NAME, t.getMessage()};
		return this.errorCodeManager.generate(Constants.SystemCode.CRM_INBOUND
				, Constants.ModuleCode.ASSET, Constants.ErrorCode.APPLICATION
				, errorMessage);
	}
	
	public SiebelException generateSiebelException(HttpResponse httpResponse
		, String siebelURL, String methodName, String moduleCode)
		throws Exception {
		
		SiebelException siebelException = null;
		
		if (httpResponse.getHttpStatusCode() == 500) {
			Xpath xpath = new Xpath(httpResponse.getResponseText());
			String faultcode = xpath.getVal("/Envelope/Body/Fault/faultcode");
			String faultString = xpath.getVal("/Envelope/Body/Fault/faultstring");

			NodeList nodeList = xpath.getList("/Envelope/Body/Fault/detail//errorstack/error");
			Node node = nodeList.item(nodeList.getLength() - 1);
			faultString = xpath.getVal(node, "./errormsg");

			BackendInfo backendInfo = new BackendInfo();
			backendInfo.setErrorCode("FaultCode: " + faultcode);
			backendInfo.setErrorMessage("FaultMsg: " + faultString);
			backendInfo.setServiceName(siebelURL);
			backendInfo.setMethodName(methodName);

			ErrorCodeResp errorCodeResp = this.generateByAPI(Constants.ErrorCode.CRM_EAI_ERROR, backendInfo);

			siebelException = new SiebelException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		}
		else if (httpResponse.getHttpStatusCode() != 200) {
			BackendInfo backendInfo = new BackendInfo();
			backendInfo.setErrorCode("HttpStatusCode: " + httpResponse.getHttpStatusCode());
			backendInfo.setErrorMessage("HttpStatusMsg: " + httpResponse.getHttpStatusMsg());
			backendInfo.setServiceName(siebelURL);
			backendInfo.setMethodName(methodName);

			ErrorCodeResp errorCodeResp = this.generateByAPI(Constants.ErrorCode.CRM_EAI_ERROR, backendInfo);
			
			siebelException = new SiebelException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		}
		
		return siebelException;
	}

	public CRMOsbException generateCRMOSBException(HttpResponse httpResponse
		,String url, String methodName) {
		
		BackendInfo backendInfo = new BackendInfo();
		backendInfo.setErrorCode("HttpStatusCode: " + httpResponse.getHttpStatusCode());
		backendInfo.setErrorMessage("HttpStatusMsg: " + httpResponse.getHttpStatusMsg());
		backendInfo.setServiceName(url);
		backendInfo.setMethodName(methodName);

		ErrorCodeResp errorCodeResp = this.generateByAPI(Constants.SystemCode.CRM_INBOUND, backendInfo);
		return new CRMOsbException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
	}
	
	public INTRestException generateINTRestException(HttpResponse httpResponse
			,String url, String methodName) {
			
			BackendInfo backendInfo = new BackendInfo();
			backendInfo.setErrorCode("HttpStatusCode: " + httpResponse.getHttpStatusCode());
			backendInfo.setErrorMessage("HttpStatusMsg: " + httpResponse.getHttpStatusMsg());
			backendInfo.setServiceName(url);
			backendInfo.setMethodName(methodName);

			ErrorCodeResp errorCodeResp = this.generateByAPI(Constants.SystemCode.CRM_INBOUND, backendInfo);
			return new INTRestException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		}
}