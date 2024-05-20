package th.co.truecorp.crmdev.asset.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BlacklistBean {

	private String blacklistTOL;
	private String blacklistTMV;
	private String blacklistTVS;

	public String getBlacklistTOL() {
		return blacklistTOL;
	}

	public void setBlacklistTOL(String blacklistTOL) {
		this.blacklistTOL = blacklistTOL;
	}

	public String getBlacklistTMV() {
		return blacklistTMV;
	}

	public void setBlacklistTMV(String blacklistTMV) {
		this.blacklistTMV = blacklistTMV;
	}

	public String getBlacklistTVS() {
		return blacklistTVS;
	}

	public void setBlacklistTVS(String blacklistTVS) {
		this.blacklistTVS = blacklistTVS;
	}

}
