package th.co.truecorp.crmdev.asset.bean;

public class ConvergenceRelationBean {
	
	private String assetRowID;
	private String assetNo;
	private String serviceID;
	private String status;
	private String relationType;
    private String ban;
	
	@Override
	public String toString() {
		return "ConvergenceRelationBean [assetRowID=" + assetRowID + ", assetNo=" + assetNo + ", serviceID=" + serviceID
				+ ", status=" + status + ", relationType=" + relationType + ", ban=" + ban + "]";
	}
	
	public ConvergenceRelationBean() {
		
	}
	public String getAssetRowID() {
		return assetRowID;
	}
	public void setAssetRowID(String assetRowID) {
		this.assetRowID = assetRowID;
	}
	public String getAssetNo() {
		return assetNo;
	}
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}	
	
}
