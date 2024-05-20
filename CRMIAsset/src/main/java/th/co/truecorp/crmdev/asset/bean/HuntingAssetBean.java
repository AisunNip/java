package th.co.truecorp.crmdev.asset.bean;

public class HuntingAssetBean {
	
	private String assetRowID;
	private String serviceID;
	private String ban;
	
	public HuntingAssetBean() {
	}

	public String getAssetRowID() {
		return assetRowID;
	}

	public void setAssetRowID(String assetRowID) {
		this.assetRowID = assetRowID;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}
}