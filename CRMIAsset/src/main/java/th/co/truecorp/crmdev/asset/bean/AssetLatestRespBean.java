package th.co.truecorp.crmdev.asset.bean;

import java.util.Arrays;
import java.util.Calendar;

public class AssetLatestRespBean extends ResponseBean {
	
	private static final long serialVersionUID = 1L;

	private String assetRowId;
	private String customerRowId;
	private String billingRowId;
	private String serviceId;
	
	private String assetNo;
	private String preferLanguage;
	private String sourceSystem;
	private Calendar startDate;
	private Calendar endDate;
	private String status;
	private String qrunProductId;
	private String qrunAddressId;
	private AssetTOLSubscriber subscriberName;
	
	private String shareBAN;
	private String ccbsSubscriberId;
	private String companyCode;
	private String ban;
	private String dealerCode;
	private String disconnectType;
	private String relatedServiceId;
	private AssetTOLProduct product;
	private Calendar createdDate;
	private String createdBy;
	private Calendar lastUpdatedDate;
	private String lastUpdatedBy;
	private String cvip;
	private String trueId;
	private AssetContact3CJ[]  assetContact3CJList;
	private String note;
	private String idType;
	private String assetPassword;
	
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	public AssetLatestRespBean() {
	}

	public String getAssetRowId() {
		return assetRowId;
	}

	public void setAssetRowId(String assetRowId) {
		this.assetRowId = assetRowId;
	}

	public String getCustomerRowId() {
		return customerRowId;
	}

	public void setCustomerRowId(String customerRowId) {
		this.customerRowId = customerRowId;
	}

	public String getBillingRowId() {
		return billingRowId;
	}

	public void setBillingRowId(String billingRowId) {
		this.billingRowId = billingRowId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getAssetNo() {
		return assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public String getPreferLanguage() {
		return preferLanguage;
	}

	public void setPreferLanguage(String preferLanguage) {
		this.preferLanguage = preferLanguage;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	
	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getQrunProductId() {
		return qrunProductId;
	}

	public void setQrunProductId(String qrunProductId) {
		this.qrunProductId = qrunProductId;
	}

	public AssetTOLSubscriber getSubscriberName() {
		return subscriberName;
	}

	public void setSubscriberName(AssetTOLSubscriber subscriberName) {
		this.subscriberName = subscriberName;
	}

	public String getShareBAN() {
		return shareBAN;
	}

	public void setShareBAN(String shareBAN) {
		this.shareBAN = shareBAN;
	}

	public String getCcbsSubscriberId() {
		return ccbsSubscriberId;
	}

	public void setCcbsSubscriberId(String ccbsSubscriberId) {
		this.ccbsSubscriberId = ccbsSubscriberId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDisconnectType() {
		return disconnectType;
	}

	public void setDisconnectType(String disconnectType) {
		this.disconnectType = disconnectType;
	}

	public String getRelatedServiceId() {
		return relatedServiceId;
	}

	public void setRelatedServiceId(String relatedServiceId) {
		this.relatedServiceId = relatedServiceId;
	}

	public AssetTOLProduct getProduct() {
		return product;
	}

	public void setProduct(AssetTOLProduct product) {
		this.product = product;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Calendar getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Calendar lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getCvip() {
		return cvip;
	}

	public void setCvip(String cvip) {
		this.cvip = cvip;
	}

	public AssetContact3CJ[] getAssetContact3CJList() {
		return assetContact3CJList;
	}

	public void setAssetContact3CJList(AssetContact3CJ[] assetContact3CJList) {
		this.assetContact3CJList = assetContact3CJList;
	}

	public String getTrueId() {
		return trueId;
	}

	public void setTrueId(String trueId) {
		this.trueId = trueId;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getQrunAddressId() {
		return qrunAddressId;
	}

	public void setQrunAddressId(String qrunAddressId) {
		this.qrunAddressId = qrunAddressId;
	}

	public String getAssetPassword() {
		return assetPassword;
	}

	public void setAssetPassword(String assetPassword) {
		this.assetPassword = assetPassword;
	}

	@Override
	public String toString() {
		return "AssetLatestRespBean [assetRowId=" + assetRowId + ", customerRowId=" + customerRowId + ", billingRowId="
				+ billingRowId + ", serviceId=" + serviceId + ", assetNo=" + assetNo + ", preferLanguage="
				+ preferLanguage + ", sourceSystem=" + sourceSystem + ", startDate=" + startDate + ", endDate="
				+ endDate + ", status=" + status + ", qrunProductId=" + qrunProductId + ", qrunAddressId="
				+ qrunAddressId + ", subscriberName=" + subscriberName + ", shareBAN=" + shareBAN
				+ ", ccbsSubscriberId=" + ccbsSubscriberId + ", companyCode=" + companyCode + ", ban=" + ban
				+ ", dealerCode=" + dealerCode + ", disconnectType=" + disconnectType + ", relatedServiceId="
				+ relatedServiceId + ", product=" + product + ", createdDate=" + createdDate + ", createdBy="
				+ createdBy + ", lastUpdatedDate=" + lastUpdatedDate + ", lastUpdatedBy=" + lastUpdatedBy + ", cvip="
				+ cvip + ", trueId=" + trueId + ", assetContact3CJList=" + Arrays.toString(assetContact3CJList)
				+ ", note=" + note + ", idType=" + idType + ", assetPassword=" + assetPassword + "]";
	}		
}