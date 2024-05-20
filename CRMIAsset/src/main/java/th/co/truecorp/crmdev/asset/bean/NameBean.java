package th.co.truecorp.crmdev.asset.bean;

public class NameBean {

	private String accountRowID;
	private String title;
	private String firstName;
	private String lastName;
	private String organizationName;
	private String accountType;
	
	public NameBean() {
	}

	public String getAccountRowID() {
		return accountRowID;
	}

	public void setAccountRowID(String accountRowID) {
		this.accountRowID = accountRowID;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
}