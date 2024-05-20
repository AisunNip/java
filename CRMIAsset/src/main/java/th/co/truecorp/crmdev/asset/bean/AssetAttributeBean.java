package th.co.truecorp.crmdev.asset.bean;

public class AssetAttributeBean {
	
	private String attributeRowID;
	private String name;
	private String value;
	private String assetRowID;
	
	public AssetAttributeBean() {
	}

	public String getAttributeRowID() {
		return attributeRowID;
	}

	public void setAttributeRowID(String attributeRowID) {
		this.attributeRowID = attributeRowID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAssetRowID() {
		return assetRowID;
	}

	public void setAssetRowID(String assetRowID) {
		this.assetRowID = assetRowID;
	}
}