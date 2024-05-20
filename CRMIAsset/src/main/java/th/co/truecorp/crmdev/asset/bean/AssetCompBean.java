package th.co.truecorp.crmdev.asset.bean;

import java.util.Arrays;
import java.util.Calendar;

public class AssetCompBean {

	private String assetRowID;
	private String parAssetRowID;
	private String rootAssetRowID;
	private String assetNo;
	private String serialNo;
	private String productCode;
	private String partNum;
	private String offerCode;
	private String offerName;
	private String productRowID;
	private String productCategory;
	private String productName;
	private String productType;
	private String productSubType;
	private String productLine;
	private Calendar effectiveDate;
	private Calendar expiryDate;
	private String status;
	private Calendar installDate;
	private String materialCode;
	private String deviceModel;
	private String deviceType; // Rental, Sold, Retention
	private String type; // Modem, AP
	private String overrideOTAmount;
	private String overrideAmount;
	private String overrideDesc;
	
	private String trueLifeID;
	private String cvgGroupID;
	private String cvgCode;
	private String cpcCategoryCode;
	private String cpcProductType;
	
	private AssetAttributeBean[] attributeList;
	private ProductXMBean[] productXMList;
	
	private String offerType;
	private String chargePeriod;
	
	private String oldServiceID;
	private String overrideOCDesc;
	private String price;
	private String discountValue;
	private String discountType;
	private String newPeriodInd;
	private String dataSource;
	private String quantity;
	private String ccbsSoc;
	private String effectiveDateStr;
	
	public AssetCompBean() {
	}

	@Override
	public String toString() {
		return "AssetCompBean [assetRowID=" + assetRowID + ", parAssetRowID=" + parAssetRowID + ", rootAssetRowID="
				+ rootAssetRowID + ", assetNo=" + assetNo + ", serialNo=" + serialNo + ", productCode=" + productCode
				+ ", partNum=" + partNum + ", offerCode=" + offerCode + ", offerName=" + offerName + ", productRowID="
				+ productRowID + ", productCategory=" + productCategory + ", productName=" + productName
				+ ", productType=" + productType + ", productSubType=" + productSubType + ", productLine=" + productLine
				+ ", effectiveDate=" + effectiveDate + ", expiryDate=" + expiryDate + ", status=" + status
				+ ", installDate=" + installDate + ", materialCode=" + materialCode + ", deviceModel=" + deviceModel
				+ ", deviceType=" + deviceType + ", type=" + type + ", overrideOTAmount=" + overrideOTAmount
				+ ", overrideAmount=" + overrideAmount + ", overrideDesc=" + overrideDesc + ", trueLifeID=" + trueLifeID
				+ ", cvgGroupID=" + cvgGroupID + ", cvgCode=" + cvgCode + ", cpcCategoryCode=" + cpcCategoryCode
				+ ", cpcProductType=" + cpcProductType + ", attributeList=" + Arrays.toString(attributeList)
				+ ", productXMList=" + Arrays.toString(productXMList) + ", offerType=" + offerType + ", chargePeriod="
				+ chargePeriod + ", oldServiceID=" + oldServiceID + ", overrideOCDesc=" + overrideOCDesc + ", price="
				+ price + ", discountValue=" + discountValue + ", discountType=" + discountType + ", newPeriodInd="
				+ newPeriodInd + ", dataSource=" + dataSource + ", quantity=" + quantity + ", ccbsSoc=" + ccbsSoc
				+ ", effectiveDateStr=" + effectiveDateStr + "]";
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

	public String getRootAssetRowID() {
		return rootAssetRowID;
	}

	public void setRootAssetRowID(String rootAssetRowID) {
		this.rootAssetRowID = rootAssetRowID;
	}

	public String getAssetNo() {
		return assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getPartNum() {
		return partNum;
	}

	public void setPartNum(String partNum) {
		this.partNum = partNum;
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

	public String getProductRowID() {
		return productRowID;
	}

	public void setProductRowID(String productRowID) {
		this.productRowID = productRowID;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductSubType() {
		return productSubType;
	}

	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public Calendar getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Calendar effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Calendar getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Calendar expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Calendar getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Calendar installDate) {
		this.installDate = installDate;
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

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOverrideOTAmount() {
		return overrideOTAmount;
	}

	public void setOverrideOTAmount(String overrideOTAmount) {
		this.overrideOTAmount = overrideOTAmount;
	}

	public String getOverrideAmount() {
		return overrideAmount;
	}

	public void setOverrideAmount(String overrideAmount) {
		this.overrideAmount = overrideAmount;
	}

	public String getOverrideDesc() {
		return overrideDesc;
	}

	public void setOverrideDesc(String overrideDesc) {
		this.overrideDesc = overrideDesc;
	}

	public String getTrueLifeID() {
		return trueLifeID;
	}

	public void setTrueLifeID(String trueLifeID) {
		this.trueLifeID = trueLifeID;
	}

	public String getCvgGroupID() {
		return cvgGroupID;
	}

	public void setCvgGroupID(String cvgGroupID) {
		this.cvgGroupID = cvgGroupID;
	}

	public String getCvgCode() {
		return cvgCode;
	}

	public void setCvgCode(String cvgCode) {
		this.cvgCode = cvgCode;
	}

	public String getCpcCategoryCode() {
		return cpcCategoryCode;
	}

	public void setCpcCategoryCode(String cpcCategoryCode) {
		this.cpcCategoryCode = cpcCategoryCode;
	}

	public String getCpcProductType() {
		return cpcProductType;
	}

	public void setCpcProductType(String cpcProductType) {
		this.cpcProductType = cpcProductType;
	}

	public AssetAttributeBean[] getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(AssetAttributeBean[] attributeList) {
		this.attributeList = attributeList;
	}

	public ProductXMBean[] getProductXMList() {
		return productXMList;
	}

	public void setProductXMList(ProductXMBean[] productXMList) {
		this.productXMList = productXMList;
	}

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public String getChargePeriod() {
		return chargePeriod;
	}

	public void setChargePeriod(String chargePeriod) {
		this.chargePeriod = chargePeriod;
	}

	public String getOldServiceID() {
		return oldServiceID;
	}

	public void setOldServiceID(String oldServiceID) {
		this.oldServiceID = oldServiceID;
	}

	public String getOverrideOCDesc() {
		return overrideOCDesc;
	}

	public void setOverrideOCDesc(String overrideOCDesc) {
		this.overrideOCDesc = overrideOCDesc;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getNewPeriodInd() {
		return newPeriodInd;
	}

	public void setNewPeriodInd(String newPeriodInd) {
		this.newPeriodInd = newPeriodInd;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public String getCcbsSoc() {
		return ccbsSoc;
	}

	public void setCcbsSoc(String ccbsSoc) {
		this.ccbsSoc = ccbsSoc;
	}

	public String getEffectiveDateStr() {
		return effectiveDateStr;
	}

	public void setEffectiveDateStr(String effectiveDateStr) {
		this.effectiveDateStr = effectiveDateStr;
	}
}