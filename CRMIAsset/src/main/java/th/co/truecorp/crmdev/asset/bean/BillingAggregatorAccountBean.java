package th.co.truecorp.crmdev.asset.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingAggregatorAccountBean {
	
	private String status;
	private String statusDate;
	private String accountType;
	private String ccbsCustId;
	private String customerRowId;
	private IdentificationBean identification;
	private IndividualBean individual;
	private BusinessBean business;
	private List<AddressBean> listOfAddress;
	private String mdmSourceId;
	private String mdmSourceDb;
	private String ban;
	private String goldenId;
	private String sourceSystem;
	
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

	public String getCcbsCustId() {
		return ccbsCustId;
	}

	public void setCcbsCustId(String ccbsCustId) {
		this.ccbsCustId = ccbsCustId;
	}

	public String getCustomerRowId() {
		return customerRowId;
	}

	public void setCustomerRowId(String customerRowId) {
		this.customerRowId = customerRowId;
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

}
