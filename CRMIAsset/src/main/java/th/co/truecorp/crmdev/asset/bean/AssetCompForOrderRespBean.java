package th.co.truecorp.crmdev.asset.bean;

import java.util.Arrays;

public class AssetCompForOrderRespBean extends ResponseBean {
	
	private static final long serialVersionUID = 1L;

	private AssetCompForOrderBean[] assetComponentList;

	public AssetCompForOrderRespBean() {
	}

	public AssetCompForOrderBean[] getAssetComponentList() {
		return assetComponentList;
	}

	public void setAssetComponentList(AssetCompForOrderBean[] assetComponentList) {
		this.assetComponentList = assetComponentList;
	}

	@Override
	public String toString() {
		return "AssetCompForOrderRespBean [assetComponentList=" + Arrays.toString(assetComponentList) + "]";
	}
}