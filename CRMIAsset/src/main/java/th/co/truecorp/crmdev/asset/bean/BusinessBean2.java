package th.co.truecorp.crmdev.asset.bean;

import java.util.Calendar;

public class BusinessBean2 {

	private String idType;
	private String idTypeCode;
	private String idNumber;
	private String organizationName;
	private String phoneNumber;
	private String faxNumber;
	private String emailAddress;
	private String annualRevenue;
	private Calendar idExpireDate;
	
	public BusinessBean2() {
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdTypeCode() {
		return idTypeCode;
	}

	public void setIdTypeCode(String idTypeCode) {
		this.idTypeCode = idTypeCode;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getAnnualRevenue() {
		return annualRevenue;
	}

	public void setAnnualRevenue(String annualRevenue) {
		this.annualRevenue = annualRevenue;
	}

	public Calendar getIdExpireDate() {
		return idExpireDate;
	}

	public void setIdExpireDate(Calendar idExpireDate) {
		this.idExpireDate = idExpireDate;
	}	
	
}