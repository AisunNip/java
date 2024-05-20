package th.co.truecorp.crmdev.asset.proxy;

import th.co.truecorp.crmdev.asset.AssetLogger;
import th.co.truecorp.crmdev.asset.util.AssetErrorCode;
import th.co.truecorp.crmdev.util.db.config.CassandraConfig;
import th.co.truecorp.crmdev.util.logging.LogProductName;
import th.co.truecorp.crmdev.util.logging.LogSystem;
import th.co.truecorp.crmdev.util.net.http.HttpRequest;
import th.co.truecorp.crmdev.util.net.http.HttpResponse;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Properties;

public class CRMESBProxy {
	private AssetLogger logger;
	private AssetErrorCode assetErrorCode;

	public CRMESBProxy() throws UnknownHostException {
		this.logger = new AssetLogger(LogProductName.All, LogSystem.CRM_INBOUND, LogSystem.CRM_INBOUND);
		this.assetErrorCode = new AssetErrorCode();
	}

	public HttpResponse createUpdateCustomerIndy(String transID, String xmlRequest) throws Exception {
		
		String url = CassandraConfig.getVal("crmesb.siebelservice.url");
		String method = "createUpdateCustomerIndy";

		logger.writeRequestMsg(transID, url, xmlRequest);
		Calendar start = logger.logRequestWSClient(transID, url, method);

		Properties httpHeader = new Properties();

		HttpRequest httpRequest = new HttpRequest();
		HttpResponse httpResponse = httpRequest.postXML(url, httpHeader, xmlRequest);

		logger.logResponseWSClient(transID, url, method, "-", start);
		logger.writeResponseMsg(transID, httpResponse.getResponseText());

		if (httpResponse.getHttpStatusCode() != 200) {
			throw assetErrorCode.generateCRMOSBException(httpResponse, url, method);
		}
		return httpResponse;
	}
}
