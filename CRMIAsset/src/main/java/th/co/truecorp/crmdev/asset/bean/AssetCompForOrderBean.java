package th.co.truecorp.crmdev.asset.bean;


public class AssetCompForOrderBean {

	private String assetRowID;
	private String serialNo;
	private String partNum;
	private String offerName;
	private String productType;
	private String effectiveDate;
	private String expiryDate;
	private String ccbsSoc;

	public AssetCompForOrderBean() {
	}

	@Override
	public String toString() {
		return "AssetCompForOrderBean [assetRowID=" + assetRowID + ", serialNo=" + serialNo + ", partNum=" + partNum
				+ ", offerName=" + offerName + ", productType=" + productType + ", effectiveDate=" + effectiveDate
				+ ", expiryDate=" + expiryDate + ", ccbsSoc=" + ccbsSoc + "]";
	}

	public String getAssetRowID() {
		return assetRowID;
	}

	public void setAssetRowID(String assetRowID) {
		this.assetRowID = assetRowID;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getPartNum() {
		return partNum;
	}

	public void setPartNum(String partNum) {
		this.partNum = partNum;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCcbsSoc() {
		return ccbsSoc;
	}

	public void setCcbsSoc(String ccbsSoc) {
		this.ccbsSoc = ccbsSoc;
	}
}