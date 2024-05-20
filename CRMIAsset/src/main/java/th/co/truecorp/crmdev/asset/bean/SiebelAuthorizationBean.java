package th.co.truecorp.crmdev.asset.bean;

public class SiebelAuthorizationBean {

	private String siebelUserName;
	private String siebelPassword;
	
	public SiebelAuthorizationBean() {
	}

	public String getSiebelUserName() {
		return siebelUserName;
	}

	public void setSiebelUserName(String siebelUserName) {
		this.siebelUserName = siebelUserName;
	}

	public String getSiebelPassword() {
		return siebelPassword;
	}

	public void setSiebelPassword(String siebelPassword) {
		this.siebelPassword = siebelPassword;
	}
}