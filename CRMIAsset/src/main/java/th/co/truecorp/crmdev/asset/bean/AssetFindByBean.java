package th.co.truecorp.crmdev.asset.bean;

import java.util.Calendar;

import com.fasterxml.jackson.core.JsonProcessingException;

import th.co.truecorp.crmdev.util.common.JsonFacade;

public class AssetFindByBean extends JsonFacade<AssetFindByBean>{

	private String assetRowID;
	private String assetNo;
	private String customerRowID;
	private String integrationId;
	private String idNo;
	private String[] customerRowIDList;
	private String serviceID;
	private String relatedServiceID;
	private String qrunAddressID;
	
	// productLine = "True Visions", "True Mobile", "True Online"
	private String productLine;
	private String ban;
	private String cvgBan;
	private String taxID;
	private String branchNumber;
	private String masterFlag;
	private String[] status;
	private String[] productTypeList;
	private Calendar fromEndDate;
	
	private String idTypeCodeFlag; // Y,N
	private String customerAddressFlag; // Y,N
	private String billingAddressFlag; // Y,N
	private String attributeFlag; // Y,N
	private String productXMFlag; // Y,N
	private String olprdDBFlag;// Y,N
    private String cpcOfferFlag;// Y,N
	
	private Integer pageNo;
	private Integer pageSize;
	

	
	public AssetFindByBean() {
		super(AssetFindByBean.class);
	}
	
	@Override
	public String toString() {
		try {
			return this.objectToString(this);
		}
		catch (JsonProcessingException e) {
		}
		
		return null;
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

	public String getCustomerRowID() {
		return customerRowID;
	}

	public void setCustomerRowID(String customerRowID) {
		this.customerRowID = customerRowID;
	}

	public String getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String[] getCustomerRowIDList() {
		return customerRowIDList;
	}

	public void setCustomerRowIDList(String[] customerRowIDList) {
		this.customerRowIDList = customerRowIDList;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getRelatedServiceID() {
		return relatedServiceID;
	}

	public void setRelatedServiceID(String relatedServiceID) {
		this.relatedServiceID = relatedServiceID;
	}

	public String getQrunAddressID() {
		return qrunAddressID;
	}

	public void setQrunAddressID(String qrunAddressID) {
		this.qrunAddressID = qrunAddressID;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public String getCvgBan() {
		return cvgBan;
	}

	public void setCvgBan(String cvgBan) {
		this.cvgBan = cvgBan;
	}

	public String getTaxID() {
		return taxID;
	}

	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}

	public String getBranchNumber() {
		return branchNumber;
	}

	public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}

	public String getMasterFlag() {
		return masterFlag;
	}

	public void setMasterFlag(String masterFlag) {
		this.masterFlag = masterFlag;
	}

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

	public String[] getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(String[] productTypeList) {
		this.productTypeList = productTypeList;
	}

	public Calendar getFromEndDate() {
		return fromEndDate;
	}

	public void setFromEndDate(Calendar fromEndDate) {
		this.fromEndDate = fromEndDate;
	}

	public String getIdTypeCodeFlag() {
		return idTypeCodeFlag;
	}

	public void setIdTypeCodeFlag(String idTypeCodeFlag) {
		this.idTypeCodeFlag = idTypeCodeFlag;
	}

	public String getCustomerAddressFlag() {
		return customerAddressFlag;
	}

	public void setCustomerAddressFlag(String customerAddressFlag) {
		this.customerAddressFlag = customerAddressFlag;
	}

	public String getBillingAddressFlag() {
		return billingAddressFlag;
	}

	public void setBillingAddressFlag(String billingAddressFlag) {
		this.billingAddressFlag = billingAddressFlag;
	}

	public String getAttributeFlag() {
		return attributeFlag;
	}

	public void setAttributeFlag(String attributeFlag) {
		this.attributeFlag = attributeFlag;
	}

	public String getProductXMFlag() {
		return productXMFlag;
	}

	public void setProductXMFlag(String productXMFlag) {
		this.productXMFlag = productXMFlag;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOlprdDBFlag() {
		return olprdDBFlag;
	}

	public void setOlprdDBFlag(String olprdDBFlag) {
		this.olprdDBFlag = olprdDBFlag;
	}

	public String getCpcOfferFlag() {
		return cpcOfferFlag;
	}

	public void setCpcOfferFlag(String cpcOfferFlag) {
		this.cpcOfferFlag = cpcOfferFlag;
	}	
}