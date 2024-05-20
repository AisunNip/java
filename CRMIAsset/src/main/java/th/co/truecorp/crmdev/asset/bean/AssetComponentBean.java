package th.co.truecorp.crmdev.asset.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AssetComponentBean {
	
	private String assetNo;
	private String dataSource;
	private String installDate;
	private String productPartNumber;
	private String serialNo;
	private String status;	// Active, Inactive
	private String startDate;
	private String modelName;
	private String type; // Modem, AP
	private String deviceType; // Rental, Sold, Retention
	private String brand;
	private String materialCode;
	private String purchaseDate;
	private String customerAssetFlag;
	private String lastCallDate;
	private String rowID;
	private String offerCode;
	private String offerName;
	private String productType;
	private String effectiveDate;
	private String expiryDate;
	private String deviceModel;
	private String partNo;
	
	
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getRowID() {
		return rowID;
	}
	public void setRowID(String rowID) {
		this.rowID = rowID;
	}
	public String getAssetNo() {
		return assetNo;
	}
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getInstallDate() {
		return installDate;
	}
	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}
	public String getProductPartNumber() {
		return productPartNumber;
	}
	public void setProductPartNumber(String productPartNumber) {
		this.productPartNumber = productPartNumber;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getCustomerAssetFlag() {
		return customerAssetFlag;
	}
	public void setCustomerAssetFlag(String customerAssetFlag) {
		this.customerAssetFlag = customerAssetFlag;
	}
	public String getLastCallDate() {
		return lastCallDate;
	}
	public void setLastCallDate(String lastCallDate) {
		this.lastCallDate = lastCallDate;
	}
	public String getOfferCode() {
		return offerCode;
	}
	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
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
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}


}