package th.co.truecorp.crmdev.asset.proxy;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Properties;

import th.co.truecorp.crmdev.asset.AssetLogger;
import th.co.truecorp.crmdev.asset.bean.AuthenticateBean;
import th.co.truecorp.crmdev.asset.util.AssetErrorCode;
import th.co.truecorp.crmdev.util.db.config.CassandraConfig;
import th.co.truecorp.crmdev.util.logging.LogProductName;
import th.co.truecorp.crmdev.util.logging.LogSystem;
import th.co.truecorp.crmdev.util.net.http.HttpRequest;
import th.co.truecorp.crmdev.util.net.http.HttpResponse;

public class INTProxy {

	private AssetLogger logger;
	private AssetErrorCode assetErrorCode;
	
	public INTProxy() throws UnknownHostException {
		this.logger = new AssetLogger(LogProductName.All, LogSystem.CRM_INBOUND, LogSystem.CCB_INT);
		this.assetErrorCode = new AssetErrorCode();
	}
	
	public AuthenticateBean getIntAuthentication() {
		AuthenticateBean authenBean = new AuthenticateBean();
		authenBean.setUserName(CassandraConfig.getVal("int.rest.username"));
		authenBean.setPassword(CassandraConfig.getVal("int.rest.password"));		
		return authenBean;
	}
	
	public HttpResponse getAllPrepaidProfileList(String transID, String xmlRequest) throws Exception {
		String url = CassandraConfig.getVal("int.rest.getallprepaidprofilelist.url");
		String method = "get_AllPrepaidProfileList";
		
		logger.writeRequestMsg(transID, url, xmlRequest);
		Calendar startOsb = logger.logRequestWSClient(transID, url, method);

		HttpRequest httpRequest = new HttpRequest();
		Properties httpHeader = httpRequest.generateBasicAuthorization(this.getIntAuthentication().getUserName(), this.getIntAuthentication().getPassword());
		HttpResponse httpResponse = httpRequest.postXML(url, httpHeader, xmlRequest);
		
		logger.logResponseWSClient(transID, url, method, "-", startOsb);
		logger.writeResponseMsg(transID, httpResponse.getResponseText());
		
		if (httpResponse.getHttpStatusCode() != 200) {
			throw assetErrorCode.generateINTRestException(httpResponse, url, method);
		}
		return httpResponse;
	}
}
