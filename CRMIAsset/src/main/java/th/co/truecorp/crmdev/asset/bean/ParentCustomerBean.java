package th.co.truecorp.crmdev.asset.bean;

import com.fasterxml.jackson.core.JsonProcessingException;

import th.co.truecorp.crmdev.util.common.JsonFacade;

public class ParentCustomerBean extends JsonFacade<ParentCustomerBean> {
	
	public ParentCustomerBean() {
		super(ParentCustomerBean.class);
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
	
	private String firstName;
	private String lastName;
	private String idType;
	private String idNumber;
	private String contactPhoneType;
	private String contactPhone;
	private String updatedBy;
	
	private AddressBean address;
	
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

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getContactPhoneType() {
		return contactPhoneType;
	}

	public void setContactPhoneType(String contactPhoneType) {
		this.contactPhoneType = contactPhoneType;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public AddressBean getAddress() {
		return address;
	}

	public void setAddress(AddressBean address) {
		this.address = address;
	}		
}
