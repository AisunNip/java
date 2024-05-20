package th.co.truecorp.crmdev.asset.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import th.co.truecorp.crmdev.util.common.JsonFacade;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AssetDataBean extends JsonFacade<AssetDataBean> {

	public AssetDataBean() {
		super(AssetDataBean.class);
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
	
	private String assetId;
	private String serviceId;
	private String preferLanguage;
	private String sourceSystem;
	private SubscriberNameBean subscriberName;
	private String oneBillFlag;
	private AddressReqBean installationAddress;
	private String fraud;
	private String fraudReason;
	private String fruadDate;
	private String assetVIP;
	
	private String customerRowId;
	private String billingRowId;
	private String startDate;
	private String endDate;
	private String status;
	private String qrunProductId;
	private String qrunAddressId;
	private String shareBAN;
	private String ccbsSubscriberId;
	private String companyCode;
	private String ban;
	private String dealerCode;
	private String disconnectType;
	private String relatedServiceId;
	private String createdDate;
	private String createdBy;
	private String lastUpdatedDate;
	private String lastUpdatedBy;
	private String cvip;
	
	@JsonProperty("assetProduct")
	private AssetProductBean assetProductBean;
	private String note;
	
	
	private String accountIntegrationId;
	private String assetNo;
	private String dataSource;
	private String serialNo;
	private String deviceType;
	
	private AssetComponentBean[] assetComponent;

	private String srNumber;
	private String channel;
	private String submitDate;
	
	private AssetXMBean assetXM;
	
	private String trueId;
	@JsonProperty("listOfAssetContact3CJ")
	private AssetContact3CJ[] assetContact3CJ;
	
	private String maxAssetVIP;
	private String idType;
	
	private String grading;
	private String assetPassword;

    private String thaiId;
	private String channelOrder;
	private String verifyMethod;
	private String verifyResult;
	private String kycData;
	private String transDocID;
	private String issueDate;
	private String expireDate;
	private String isCardReader;
	private String integrationId;
	private String productType;
	private String subscriberNo;
	private String goldenId;
	
	/*
	 * Red Zone order type 34,35
	 */
	private String dummyMsisdn;
	private boolean dummyMsisdnFlag;
	
	/*
	 * U18
	 */
	
	private ParentCustomerBean parentCustomer;
	
	/*
	 * U18
	 */
	private DCBBean dcbBean;
	
	public String getGrading() {
		return grading;
	}

	public void setGrading(String grading) {
		this.grading = grading;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getMaxAssetVIP() {
		return maxAssetVIP;
	}

	public void setMaxAssetVIP(String maxAssetVIP) {
		this.maxAssetVIP = maxAssetVIP;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
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
	public SubscriberNameBean getSubscriberName() {
		return subscriberName;
	}

	public void setSubscriberName(SubscriberNameBean subscriberName) {
		this.subscriberName = subscriberName;
	}

	public String getOneBillFlag() {
		return oneBillFlag;
	}

	public void setOneBillFlag(String oneBillFlag) {
		this.oneBillFlag = oneBillFlag;
	}

	public AddressReqBean getInstallationAddress() {
		return installationAddress;
	}

	public void setInstallationAddress(AddressReqBean installationAddress) {
		this.installationAddress = installationAddress;
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

	public String getFruadDate() {
		return fruadDate;
	}

	public void setFruadDate(String fruadDate) {
		this.fruadDate = fruadDate;
	}

	public String getAssetVIP() {
		return assetVIP;
	}

	public void setAssetVIP(String assetVIP) {
		this.assetVIP = assetVIP;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
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

	public AssetProductBean getAssetProductBean() {
		return assetProductBean;
	}

	public void setAssetProductBean(AssetProductBean assetProductBean) {
		this.assetProductBean = assetProductBean;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAccountIntegrationId() {
		return accountIntegrationId;
	}

	public void setAccountIntegrationId(String accountIntegrationId) {
		this.accountIntegrationId = accountIntegrationId;
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

	public AssetComponentBean[] getAssetComponent() {
		return assetComponent;
	}

	public void setAssetComponent(AssetComponentBean[] assetComponent) {
		this.assetComponent = assetComponent;
	}

	public String getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public AssetXMBean getAssetXM() {
		return assetXM;
	}

	public void setAssetXM(AssetXMBean assetXM) {
		this.assetXM = assetXM;
	}
	
	public AssetContact3CJ[] getAssetContact3CJ() {
		return assetContact3CJ;
	}

	public void setAssetContact3CJ(AssetContact3CJ[] assetContact3CJ) {
		this.assetContact3CJ = assetContact3CJ;
	}

	public String getTrueId() {
		return trueId;
	}

	public void setTrueId(String tureId) {
		this.trueId = tureId;
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

    public String getThaiId() {
        return thaiId;
    }

    public void setThaiId(String thaiId) {
        this.thaiId = thaiId;
    }

    public String getChannelOrder() {
		return channelOrder;
	}

	public void setChannelOrder(String channelOrder) {
		this.channelOrder = channelOrder;
	}

	public String getVerifyMethod() {
		return verifyMethod;
	}

	public void setVerifyMethod(String verifyMethod) {
		this.verifyMethod = verifyMethod;
	}

	public String getVerifyResult() {
		return verifyResult;
	}

	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}

	public String getKycData() {
		return kycData;
	}

	public void setKycData(String kycData) {
		this.kycData = kycData;
	}

	public String getTransDocID() {
		return transDocID;
	}

	public void setTransDocID(String transDocID) {
		this.transDocID = transDocID;
	}

	public String getIsCardReader() {
		return isCardReader;
	}

	public void setIsCardReader(String isCardReader) {
		this.isCardReader = isCardReader;
	}

	public String getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getSubscriberNo() {
		return subscriberNo;
	}

	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}

	public String getGoldenId() {
		return goldenId;
	}

	public void setGoldenId(String goldenId) {
		this.goldenId = goldenId;
	}
	
	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getDummyMsisdn() {
		return dummyMsisdn;
	}

	public void setDummyMsisdn(String dummyMsisdn) {
		this.dummyMsisdn = dummyMsisdn;
	}

	public boolean isDummyMsisdnFlag() {
		return dummyMsisdnFlag;
	}

	public void setDummyMsisdnFlag(boolean dummyMsisdnFlag) {
		this.dummyMsisdnFlag = dummyMsisdnFlag;
	}

	public ParentCustomerBean getParentCustomer() {
		return parentCustomer;
	}

	public void setParentCustomer(ParentCustomerBean parentCustomer) {
		this.parentCustomer = parentCustomer;
	}

	public DCBBean getDcbBean() {
		return dcbBean;
	}

	public void setDcbBean(DCBBean dcbBean) {
		this.dcbBean = dcbBean;
	}
}