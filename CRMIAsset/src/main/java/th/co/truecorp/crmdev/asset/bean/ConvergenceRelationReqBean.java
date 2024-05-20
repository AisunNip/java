package th.co.truecorp.crmdev.asset.bean;

public class ConvergenceRelationReqBean {
	
	private String assetRowID;
	private String parAssetRowID;
	
	public ConvergenceRelationReqBean() {
		
	}

	@Override
	public String toString() {
		return "ConvergenceRelationReqBean [assetRowID=" + assetRowID + ", parAssetRowID=" + parAssetRowID + "]";
	}

	public String getAssetRowID() {
		return assetRowID;
	}

	public void setAssetRowID(String assetRowID) {
		this.assetRowID = assetRowID;
	}

	public String getParAssetRowID() {
		return parAssetRowID;
	}

	public void setParAssetRowID(String parAssetRowID) {
		this.parAssetRowID = parAssetRowID;
	}	
}
