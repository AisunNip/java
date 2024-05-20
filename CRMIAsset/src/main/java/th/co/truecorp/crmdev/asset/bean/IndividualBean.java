package th.co.truecorp.crmdev.asset.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class IndividualBean {

	private String title;
	private String firstName;
	private String lastName;
	private String personalIncome;
	private String birthDate;
	private String emailAddress;
	private String education;
	private String occupation;
	private String jobTitle;
	private String gender;
	private String maritalStatus;
	private String preferredCommunication;
	private String preferredLanguage;
	private String workPhoneNo;
	private String homePhoneNo;
	private String faxNumber;
	private String mobileNo;
	private String department;
	private String company;
	private String employeeId;
	private String cvip;
	
	public IndividualBean() {
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

	public String getPersonalIncome() {
		return personalIncome;
	}

	public void setPersonalIncome(String personalIncome) {
		this.personalIncome = personalIncome;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getPreferredCommunication() {
		return preferredCommunication;
	}

	public void setPreferredCommunication(String preferredCommunication) {
		this.preferredCommunication = preferredCommunication;
	}

	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	public String getWorkPhoneNo() {
		return workPhoneNo;
	}

	public void setWorkPhoneNo(String workPhoneNo) {
		this.workPhoneNo = workPhoneNo;
	}

	public String getHomePhoneNo() {
		return homePhoneNo;
	}

	public void setHomePhoneNo(String homePhoneNo) {
		this.homePhoneNo = homePhoneNo;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getCvip() {
		return cvip;
	}

	public void setCvip(String cvip) {
		this.cvip = cvip;
	}
}