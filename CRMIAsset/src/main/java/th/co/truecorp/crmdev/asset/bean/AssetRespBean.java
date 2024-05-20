package th.co.truecorp.crmdev.asset.bean;

import java.util.Arrays;

public class AssetRespBean extends ResponseBean {

	private AssetBean[] assetBeanList;
	private Integer totalRecords;
	
	public AssetRespBean() {
	}
	
	@Override
	public String toString() {
		return super.toString() + ", AssetRespBean [assetBeanList=" + Arrays.toString(assetBeanList) + "]";
	}

	public AssetBean[] getAssetBeanList() {
		return assetBeanList;
	}

	public void setAssetBeanList(AssetBean[] assetBeanList) {
		this.assetBeanList = assetBeanList;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
}