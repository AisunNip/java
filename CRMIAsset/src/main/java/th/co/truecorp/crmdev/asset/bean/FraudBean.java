package th.co.truecorp.crmdev.asset.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FraudBean {

	private String fraudTOL;
	private String fraudTMV;
	private String fraudTVS;

	public String getFraudTOL() {
		return fraudTOL;
	}

	public void setFraudTOL(String fraudTOL) {
		this.fraudTOL = fraudTOL;
	}

	public String getFraudTMV() {
		return fraudTMV;
	}

	public void setFraudTMV(String fraudTMV) {
		this.fraudTMV = fraudTMV;
	}

	public String getFraudTVS() {
		return fraudTVS;
	}

	public void setFraudTVS(String fraudTVS) {
		this.fraudTVS = fraudTVS;
	}

}
