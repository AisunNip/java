package th.co.truecorp.crmdev.asset.bean;

public class AssetCompRespBean extends ResponseBean {
	
	private static final long serialVersionUID = 1L;

	private AssetCompBean[] assetComponentList;

	public AssetCompRespBean() {
	}

	public AssetCompBean[] getAssetComponentList() {
		return assetComponentList;
	}

	public void setAssetComponentList(AssetCompBean[] assetComponentList) {
		this.assetComponentList = assetComponentList;
	}
}