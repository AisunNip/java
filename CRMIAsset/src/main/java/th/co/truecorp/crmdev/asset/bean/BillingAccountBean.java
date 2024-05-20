package th.co.truecorp.crmdev.asset.bean;

public class BillingAccountBean {

	private String bagRowID;
	private String accountRowID;
	private String accountType;
	private String accountSubType;
	private String status;
	private String ban;
	private String cvgBan;
	private String companyCode;
	private String ccbsOuID;
	private String title;
	private String firstName;
	private String lastName;
	private String organizationName;
	private String taxID;
	private String branchNumber;
	private String sourceSystem;
	private String integrationID;
	private AddressBean[] addressList;
	
	public BillingAccountBean() {
	}

	public String getBagRowID() {
		return bagRowID;
	}

	public void setBagRowID(String bagRowID) {
		this.bagRowID = bagRowID;
	}

	public String getAccountRowID() {
		return accountRowID;
	}

	public void setAccountRowID(String accountRowID) {
		this.accountRowID = accountRowID;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCcbsOuID() {
		return ccbsOuID;
	}

	public void setCcbsOuID(String ccbsOuID) {
		this.ccbsOuID = ccbsOuID;
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

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
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