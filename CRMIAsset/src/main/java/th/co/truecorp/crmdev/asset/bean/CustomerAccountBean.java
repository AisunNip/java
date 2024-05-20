package th.co.truecorp.crmdev.asset.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import th.co.truecorp.crmdev.util.common.JsonFacade;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CustomerAccountBean extends JsonFacade<CustomerAccountBean> {

	public CustomerAccountBean() {
		super(CustomerAccountBean.class);
	}

	private String status;

	private String statusDate;
	private String accountType;
	private String accountSubType;
	private String customerSince;
	private IdentificationBean identification;
	private List<AddressBean> listOfAddress;
	private IndividualBean individual;
	private BusinessBean business;
	private FraudBean fraud;
	private BlacklistBean blacklist;
	private String grading;
	private String createdDate;
	private String createdBy;
	private String lastUpdatedDate;
	private String lastUpdatedBy;
	private String customerRowId;
	private String type;
	private BillingAccountBean billingAccountBean;
	private List<BillingAccountBean> listOfBilling;
	private String mdmSourceId;
	private String mdmSourceDb;
	private String ban;
	private String goldenId;

	private String accountServiceLevel;
	private String trueCardNo;
	private String trueCardUpdatedDate;
	private String trueCardStatus;
	private String trueCardExpiryDate;
	private String gradeStartDate;
	private String arpa;

	private String idNum;
	private String idType;

	private String maxVip;

	private String ignoreMapping;
	private String contactRowId;

	private String idNumber;
	private String title;
	private String firstName;
	private String lastName;
	private String gender;
	private String birthDate;
	private String ccbsCustId;
	private String productType;

	private String errorCode;
	private String errorMsg;

	private AddressBean address;
	
	@Override
	public String toString() {
		return "CustomerAccountBean [status=" + status + ", statusDate=" + statusDate + ", accountType=" + accountType
				+ ", accountSubType=" + accountSubType + ", customerSince=" + customerSince + ", identification="
				+ identification + ", listOfAddress=" + listOfAddress + ", individual=" + individual + ", business="
				+ business + ", fraud=" + fraud + ", blacklist=" + blacklist + ", grading=" + grading + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy + ", lastUpdatedDate=" + lastUpdatedDate + ", lastUpdatedBy="
				+ lastUpdatedBy + ", customerRowId=" + customerRowId + ", type=" + type + ", billingAccountBean="
				+ billingAccountBean + ", listOfBilling=" + listOfBilling + ", mdmSourceId=" + mdmSourceId
				+ ", mdmSourceDb=" + mdmSourceDb + ", ban=" + ban + ", goldenId=" + goldenId + ", accountServiceLevel="
				+ accountServiceLevel + ", trueCardNo=" + trueCardNo + ", trueCardUpdatedDate=" + trueCardUpdatedDate
				+ ", trueCardStatus=" + trueCardStatus + ", trueCardExpiryDate=" + trueCardExpiryDate
				+ ", gradeStartDate=" + gradeStartDate + ", arpa=" + arpa + ", idNum=" + idNum + ", idType=" + idType
				+ ", maxVip=" + maxVip + ", ignoreMapping=" + ignoreMapping + ", contactRowId=" + contactRowId
				+ ", idNumber=" + idNumber + ", title=" + title + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", gender=" + gender + ", birthDate=" + birthDate + ", ccbsCustId=" + ccbsCustId + ", productType="
				+ productType + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", address=" + address + "]";
	}

	public String getIgnoreMapping() {
		return ignoreMapping;
	}

	public void setIgnoreMapping(String ignoreMapping) {
		this.ignoreMapping = ignoreMapping;
	}

	public String getMaxVip() {
		return maxVip;
	}

	public void setMaxVip(String maxVip) {
		this.maxVip = maxVip;
	}

	public String getAccountServiceLevel() {
		return accountServiceLevel;
	}

	public void setAccountServiceLevel(String accountServiceLevel) {
		this.accountServiceLevel = accountServiceLevel;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getTrueCardNo() {
		return trueCardNo;
	}

	public void setTrueCardNo(String trueCardNo) {
		this.trueCardNo = trueCardNo;
	}

	public String getTrueCardUpdatedDate() {
		return trueCardUpdatedDate;
	}

	public void setTrueCardUpdatedDate(String trueCardUpdatedDate) {
		this.trueCardUpdatedDate = trueCardUpdatedDate;
	}

	public String getTrueCardStatus() {
		return trueCardStatus;
	}

	public void setTrueCardStatus(String trueCardStatus) {
		this.trueCardStatus = trueCardStatus;
	}

	public String getTrueCardExpiryDate() {
		return trueCardExpiryDate;
	}

	public void setTrueCardExpiryDate(String trueCardExpiryDate) {
		this.trueCardExpiryDate = trueCardExpiryDate;
	}

	public String getGradeStartDate() {
		return gradeStartDate;
	}

	public void setGradeStartDate(String gradeStartDate) {
		this.gradeStartDate = gradeStartDate;
	}

	public String getArpa() {
		return arpa;
	}

	public void setArpa(String arpa) {
		this.arpa = arpa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
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

	public IdentificationBean getIdentification() {
		return identification;
	}

	public void setIdentification(IdentificationBean identification) {
		this.identification = identification;
	}

	public List<AddressBean> getListOfAddress() {
		return listOfAddress;
	}

	public void setListOfAddress(List<AddressBean> listOfAddress) {
		this.listOfAddress = listOfAddress;
	}

	public IndividualBean getIndividual() {
		return individual;
	}

	public void setIndividual(IndividualBean individual) {
		this.individual = individual;
	}

	public BusinessBean getBusiness() {
		return business;
	}

	public void setBusiness(BusinessBean business) {
		this.business = business;
	}

	public FraudBean getFraud() {
		return fraud;
	}

	public void setFraud(FraudBean fraud) {
		this.fraud = fraud;
	}

	public BlacklistBean getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(BlacklistBean blacklist) {
		this.blacklist = blacklist;
	}

	public String getGrading() {
		return grading;
	}

	public void setGrading(String grading) {
		this.grading = grading;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getCustomerRowId() {
		return customerRowId;
	}

	public void setCustomerRowId(String customerRowId) {
		this.customerRowId = customerRowId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("billing")
	public BillingAccountBean getBillingAccountBean() {
		return billingAccountBean;
	}

	public void setBillingAccountBean(BillingAccountBean billingAccountBean) {
		this.billingAccountBean = billingAccountBean;
	}

	public List<BillingAccountBean> getListOfBilling() {
		return listOfBilling;
	}

	public void setListOfBilling(List<BillingAccountBean> listOfBilling) {
		this.listOfBilling = listOfBilling;
	}

	public String getCustomerSince() {
		return customerSince;
	}

	public void setCustomerSince(String customerSince) {
		this.customerSince = customerSince;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getMdmSourceId() {
		return mdmSourceId;
	}

	public void setMdmSourceId(String mdmSourceId) {
		this.mdmSourceId = mdmSourceId;
	}

	public String getMdmSourceDb() {
		return mdmSourceDb;
	}

	public void setMdmSourceDb(String mdmSourceDb) {
		this.mdmSourceDb = mdmSourceDb;
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public String getGoldenId() {
		return goldenId;
	}

	public void setGoldenId(String goldenId) {
		this.goldenId = goldenId;
	}

	public String getContactRowId() {
		return contactRowId;
	}

	public void setContactRowId(String contactRowId) {
		this.contactRowId = contactRowId;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getCcbsCustId() {
		return ccbsCustId;
	}

	public void setCcbsCustId(String ccbsCustId) {
		this.ccbsCustId = ccbsCustId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public AddressBean getAddress() {
		return address;
	}

	public void setAddress(AddressBean address) {
		this.address = address;
	}
}
