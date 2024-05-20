package th.co.truecorp.crmdev.asset.bean;

import java.util.Calendar;

public class AssetBean {

	private String assetRowID;
	private String masterFlag;
	private String assetNo;
	private String customerRowID;
	private String billingRowID;
	private String serviceID;
	private String preferLanguage;
	private String sourceSystem;
	private BillingAccountBean billingAccountBean;
	private CustomerAccountBean2 customerAccountBean; 
	private NameBean subscriberNameBean;
	private AddressBean installationAddress;
	private ProductBean productBean;
	private String oneBillFlag;
	private String fraud;
	private String fraudReason;
	private Calendar fruadDate;
	private String assetVIP;
	private Calendar installDate;
	private Calendar startDate;
	private Calendar endDate;
	private String status;
	private String mostUsage;
	private String qrunProductID;
	private String qrunAddressID;
	private String qrunBuildingID;
	private String churnScore;
	private String churnReason;
	private String churnFlag;
	private String faceRecChannel;
	private String faceRecVerifyMethod;
	private String faceRecKYC;
	private String faceRecVerifyResult;
	private String faceRecTransDocID;
	private String faceRecCardReader;
	private String faceRecIssueDate;
	private String faceRecExpireDate;
	private String shareBAN;
	private String ccbsSubscriberID;
	private String dealerCode;
	private String disconnectType;
	private String relatedServiceID;
	private String cpcRequiredType;
	private String cpcVersionID;
	private String paymentType;
	private Calendar createdDate;
	private String createdBy;
	private Calendar lastUpdatedDate;
	private String lastUpdatedBy;
	private String companyCode;
	private String cvip;
	private String media;
	private String specialNote;
	private String dataSource;
	private String serialNo;
	private String deviceType;
	private String trueID;
	private String assetLifeTime;
	private ConvergenceRelationBean[] coverganceRelationBean;
	private String integrationId;
	private String initActivationDate;
	private String prepaidDealerCode;
	/*
	 * U18
	 */
	private ParentCustomerBean parentCustomerBean;
	/*
	 * DCB KYC
	 */
	private DCBBean dcbBean;
	private String docMgmtID;
	
	/* 
	 * RVIP
	 */
	private String special;
	
	private Calendar xStartDate;
	private Calendar originalStartDate;
	private String lastMainBalance;
	private String docID;	
	private MnpInfoBean mnpInfoBean;
		
	public AssetBean() {
	}

	public String getAssetRowID() {
		return assetRowID;
	}

	public void setAssetRowID(String assetRowID) {
		this.assetRowID = assetRowID;
	}

	public String getMasterFlag() {
		return masterFlag;
	}

	public void setMasterFlag(String masterFlag) {
		this.masterFlag = masterFlag;
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

	public String getBillingRowID() {
		return billingRowID;
	}

	public void setBillingRowID(String billingRowID) {
		this.billingRowID = billingRowID;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
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

	public BillingAccountBean getBillingAccountBean() {
		return billingAccountBean;
	}

	public void setBillingAccountBean(BillingAccountBean billingAccountBean) {
		this.billingAccountBean = billingAccountBean;
	}

	public CustomerAccountBean2 getCustomerAccountBean() {
		return customerAccountBean;
	}

	public void setCustomerAccountBean(CustomerAccountBean2 customerAccountBean) {
		this.customerAccountBean = customerAccountBean;
	}

	public NameBean getSubscriberNameBean() {
		return subscriberNameBean;
	}

	public void setSubscriberNameBean(NameBean subscriberNameBean) {
		this.subscriberNameBean = subscriberNameBean;
	}

	public AddressBean getInstallationAddress() {
		return installationAddress;
	}

	public void setInstallationAddress(AddressBean installationAddress) {
		this.installationAddress = installationAddress;
	}

	public ProductBean getProductBean() {
		return productBean;
	}

	public void setProductBean(ProductBean productBean) {
		this.productBean = productBean;
	}

	public String getOneBillFlag() {
		return oneBillFlag;
	}

	public void setOneBillFlag(String oneBillFlag) {
		this.oneBillFlag = oneBillFlag;
	}

	public String getFraud() {
		return fraud;
	}

	public void setFraud(String fraud) {
		this.fraud = fraud;
	}

	public String getFraudReason() {
		return fraudReason;
	}

	public void setFraudReason(String fraudReason) {
		this.fraudReason = fraudReason;
	}

	public Calendar getFruadDate() {
		return fruadDate;
	}

	public void setFruadDate(Calendar fruadDate) {
		this.fruadDate = fruadDate;
	}

	public String getAssetVIP() {
		return assetVIP;
	}

	public void setAssetVIP(String assetVIP) {
		this.assetVIP = assetVIP;
	}

	public Calendar getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Calendar installDate) {
		this.installDate = installDate;
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

	public String getMostUsage() {
		return mostUsage;
	}

	public void setMostUsage(String mostUsage) {
		this.mostUsage = mostUsage;
	}

	public String getQrunProductID() {
		return qrunProductID;
	}

	public void setQrunProductID(String qrunProductID) {
		this.qrunProductID = qrunProductID;
	}

	public String getQrunAddressID() {
		return qrunAddressID;
	}

	public void setQrunAddressID(String qrunAddressID) {
		this.qrunAddressID = qrunAddressID;
	}

	public String getQrunBuildingID() {
		return qrunBuildingID;
	}

	public void setQrunBuildingID(String qrunBuildingID) {
		this.qrunBuildingID = qrunBuildingID;
	}

	public String getChurnScore() {
		return churnScore;
	}

	public void setChurnScore(String churnScore) {
		this.churnScore = churnScore;
	}

	public String getChurnReason() {
		return churnReason;
	}

	public void setChurnReason(String churnReason) {
		this.churnReason = churnReason;
	}

	public String getChurnFlag() {
		return churnFlag;
	}

	public void setChurnFlag(String churnFlag) {
		this.churnFlag = churnFlag;
	}

	public String getFaceRecChannel() {
		return faceRecChannel;
	}

	public void setFaceRecChannel(String faceRecChannel) {
		this.faceRecChannel = faceRecChannel;
	}

	public String getFaceRecVerifyMethod() {
		return faceRecVerifyMethod;
	}

	public void setFaceRecVerifyMethod(String faceRecVerifyMethod) {
		this.faceRecVerifyMethod = faceRecVerifyMethod;
	}

	public String getFaceRecKYC() {
		return faceRecKYC;
	}

	public void setFaceRecKYC(String faceRecKYC) {
		this.faceRecKYC = faceRecKYC;
	}

	public String getFaceRecVerifyResult() {
		return faceRecVerifyResult;
	}

	public void setFaceRecVerifyResult(String faceRecVerifyResult) {
		this.faceRecVerifyResult = faceRecVerifyResult;
	}

	public String getFaceRecTransDocID() {
		return faceRecTransDocID;
	}

	public void setFaceRecTransDocID(String faceRecTransDocID) {
		this.faceRecTransDocID = faceRecTransDocID;
	}

	public String getFaceRecCardReader() {
		return faceRecCardReader;
	}

	public void setFaceRecCardReader(String faceRecCardReader) {
		this.faceRecCardReader = faceRecCardReader;
	}

	public String getFaceRecIssueDate() {
		return faceRecIssueDate;
	}

	public void setFaceRecIssueDate(String faceRecIssueDate) {
		this.faceRecIssueDate = faceRecIssueDate;
	}

	public String getFaceRecExpireDate() {
		return faceRecExpireDate;
	}

	public void setFaceRecExpireDate(String faceRecExpireDate) {
		this.faceRecExpireDate = faceRecExpireDate;
	}

	public String getShareBAN() {
		return shareBAN;
	}

	public void setShareBAN(String shareBAN) {
		this.shareBAN = shareBAN;
	}

	public String getCcbsSubscriberID() {
		return ccbsSubscriberID;
	}

	public void setCcbsSubscriberID(String ccbsSubscriberID) {
		this.ccbsSubscriberID = ccbsSubscriberID;
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

	public String getRelatedServiceID() {
		return relatedServiceID;
	}

	public void setRelatedServiceID(String relatedServiceID) {
		this.relatedServiceID = relatedServiceID;
	}

	public String getCpcRequiredType() {
		return cpcRequiredType;
	}

	public void setCpcRequiredType(String cpcRequiredType) {
		this.cpcRequiredType = cpcRequiredType;
	}

	public String getCpcVersionID() {
		return cpcVersionID;
	}

	public void setCpcVersionID(String cpcVersionID) {
		this.cpcVersionID = cpcVersionID;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCvip() {
		return cvip;
	}

	public void setCvip(String cvip) {
		this.cvip = cvip;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getSpecialNote() {
		return specialNote;
	}

	public void setSpecialNote(String specialNote) {
		this.specialNote = specialNote;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getTrueID() {
		return trueID;
	}

	public void setTrueID(String trueID) {
		this.trueID = trueID;
	}

	public String getAssetLifeTime() {
		return assetLifeTime;
	}

	public void setAssetLifeTime(String assetLifeTime) {
		this.assetLifeTime = assetLifeTime;
	}

	public ConvergenceRelationBean[] getCoverganceRelationBean() {
		return coverganceRelationBean;
	}

	public void setCoverganceRelationBean(ConvergenceRelationBean[] coverganceRelationBean) {
		this.coverganceRelationBean = coverganceRelationBean;
	}

	public String getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	public ParentCustomerBean getParentCustomerBean() {
		return parentCustomerBean;
	}

	public void setParentCustomerBean(ParentCustomerBean parentCustomerBean) {
		this.parentCustomerBean = parentCustomerBean;
	}

	public DCBBean getDcbBean() {
		return dcbBean;
	}

	public void setDcbBean(DCBBean dcbBean) {
		this.dcbBean = dcbBean;
	}
	
	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getDocMgmtID() {
		return docMgmtID;
	}

	public void setDocMgmtID(String docMgmtID) {
		this.docMgmtID = docMgmtID;
	}
	
	public Calendar getxStartDate() {
		return xStartDate;
	}

	public void setxStartDate(Calendar xStartDate) {
		this.xStartDate = xStartDate;
	}

	public Calendar getOriginalStartDate() {
		return originalStartDate;
	}

	public void setOriginalStartDate(Calendar originalStartDate) {
		this.originalStartDate = originalStartDate;
	}

	public String getLastMainBalance() {
		return lastMainBalance;
	}

	public void setLastMainBalance(String lastMainBalance) {
		this.lastMainBalance = lastMainBalance;
	}

	public String getDocID() {
		return docID;
	}

	public void setDocID(String docID) {
		this.docID = docID;
	}

	public MnpInfoBean getMnpInfoBean() {
		return mnpInfoBean;
	}

	public void setMnpInfoBean(MnpInfoBean mnpInfoBean) {
		this.mnpInfoBean = mnpInfoBean;
	}

	public String getInitActivationDate() {
		return initActivationDate;
	}

	public void setInitActivationDate(String initActivationDate) {
		this.initActivationDate = initActivationDate;
	}

	public String getPrepaidDealerCode() {
		return prepaidDealerCode;
	}

	public void setPrepaidDealerCode(String prepaidDealerCode) {
		this.prepaidDealerCode = prepaidDealerCode;
	}
}