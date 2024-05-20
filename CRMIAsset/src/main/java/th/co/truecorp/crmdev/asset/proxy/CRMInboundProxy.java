package th.co.truecorp.crmdev.asset.proxy;

import th.co.truecorp.crmdev.asset.exception.HttpException;
import th.co.truecorp.crmdev.util.db.config.CassandraConfig;
import th.co.truecorp.crmdev.util.net.http.HttpRequest;
import th.co.truecorp.crmdev.util.net.http.HttpResponse;

import java.util.Properties;

public class CRMInboundProxy {
	
	public String createUpdateAccount(String TransID, String reqXml, String url) throws Exception {
		Properties httpHeader = new Properties();
		httpHeader.setProperty("siebelauthorization", CassandraConfig.getVal("crm.inbound.api.asset.updateCa"));
		
		HttpRequest httpRequest = new HttpRequest();
		HttpResponse httpResponse = httpRequest.postJSON(url, httpHeader, reqXml);
		
		if (httpResponse.getHttpStatusCode() != 200) {
			throw new HttpException(String.valueOf(httpResponse.getHttpStatusCode()), httpResponse.getHttpStatusMsg());
		}
		
		return httpResponse.getResponseText();
	}
}