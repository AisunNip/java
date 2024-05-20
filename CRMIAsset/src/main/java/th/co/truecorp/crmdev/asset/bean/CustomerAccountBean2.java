package th.co.truecorp.crmdev.asset.bean;

import java.util.Calendar;

public class CustomerAccountBean2 {

	private String accountRowID;
    private String masterFlag;
	private String status;	
	private Calendar statusDate;
	private String type;
	private String accountType;
	private String accountSubType;	
	private Calendar customerSince;	
	private String grading;
	private String customerScore;
	private String accountServiceLevel;
	private String trueCardId;
	private Calendar trueCardExpDate;
	private String trueCardStatus;
	private Calendar trueCardUpdateDate;
	private String specialNote;
	private Calendar createdDate;
	private String createdBy;
	private Calendar lastUpdatedDate;
	private String lastUpdatedBy;
	private IndividualBean2 individual;
	private BusinessBean2 business;
	private String contactRowId;
	private String goldenId;
	private String sourceSystem;
	private String integrationID;
	private AddressBean[] addressList;
	
	public CustomerAccountBean2() {
	}

	public String getAccountRowID() {
		return accountRowID;
	}

	public void setAccountRowID(String accountRowID) {
		this.accountRowID = accountRowID;
	}

	public String getMasterFlag() {
		return masterFlag;
	}

	public void setMasterFlag(String masterFlag) {
		this.masterFlag = masterFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Calendar getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Calendar statusDate) {
		this.statusDate = statusDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountSubType() {
		return accountSubType;
	}

	public void setAccountSubType(String accountSubType) {
		this.accountSubType = accountSubType;
	}

	public Calendar getCustomerSince() {
		return customerSince;
	}

	public void setCustomerSince(Calendar customerSince) {
		this.customerSince = customerSince;
	}

	public String getGrading() {
		return grading;
	}

	public void setGrading(String grading) {
		this.grading = grading;
	}

	public String getCustomerScore() {
		return customerScore;
	}

	public void setCustomerScore(String customerScore) {
		this.customerScore = customerScore;
	}

	public String getAccountServiceLevel() {
		return accountServiceLevel;
	}

	public void setAccountServiceLevel(String accountServiceLevel) {
		this.accountServiceLevel = accountServiceLevel;
	}

	public String getTrueCardId() {
		return trueCardId;
	}

	public void setTrueCardId(String trueCardId) {
		this.trueCardId = trueCardId;
	}

	public Calendar getTrueCardExpDate() {
		return trueCardExpDate;
	}

	public void setTrueCardExpDate(Calendar trueCardExpDate) {
		this.trueCardExpDate = trueCardExpDate;
	}

	public String getTrueCardStatus() {
		return trueCardStatus;
	}

	public void setTrueCardStatus(String trueCardStatus) {
		this.trueCardStatus = trueCardStatus;
	}

	public Calendar getTrueCardUpdateDate() {
		return trueCardUpdateDate;
	}

	public void setTrueCardUpdateDate(Calendar trueCardUpdateDate) {
		this.trueCardUpdateDate = trueCardUpdateDate;
	}

	public String getSpecialNote() {
		return specialNote;
	}

	public void setSpecialNote(String specialNote) {
		this.specialNote = specialNote;
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

	public IndividualBean2 getIndividual() {
		return individual;
	}

	public void setIndividual(IndividualBean2 individual) {
		this.individual = individual;
	}

	public BusinessBean2 getBusiness() {
		return business;
	}

	public void setBusiness(BusinessBean2 business) {
		this.business = business;
	}

	public String getContactRowId() {
		return contactRowId;
	}

	public void setContactRowId(String contactRowId) {
		this.contactRowId = contactRowId;
	}

	public String getGoldenId() {
		return goldenId;
	}

	public void setGoldenId(String goldenId) {
		this.goldenId = goldenId;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getIntegrationID() {
		return integrationID;
	}

	public void setIntegrationID(String integrationID) {
		this.integrationID = integrationID;
	}

	public AddressBean[] getAddressList() {
		return addressList;
	}

	public void setAddressList(AddressBean[] addressList) {
		this.addressList = addressList;
	}
}