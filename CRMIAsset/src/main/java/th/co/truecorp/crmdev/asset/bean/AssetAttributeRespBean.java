package th.co.truecorp.crmdev.asset.bean;

public class AssetAttributeRespBean extends ResponseBean {
	
	private static final long serialVersionUID = 1L;
	
	private AssetAttributeBean[] attributeList;

	public AssetAttributeRespBean() {
	}

	public AssetAttributeBean[] getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(AssetAttributeBean[] attributeList) {
		this.attributeList = attributeList;
	}
}