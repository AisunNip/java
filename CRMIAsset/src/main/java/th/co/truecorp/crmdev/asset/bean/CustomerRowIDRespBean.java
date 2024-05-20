package th.co.truecorp.crmdev.asset.bean;

import java.util.Arrays;

public class CustomerRowIDRespBean extends ResponseBean {

	private String[] customerRowIDList;

	public CustomerRowIDRespBean() {
	}

	@Override
	public String toString() {
		return "CustomerRowIDRespBean [customerRowIDList=" + Arrays.toString(customerRowIDList) + "]";
	}

	public String[] getCustomerRowIDList() {
		return customerRowIDList;
	}

	public void setCustomerRowIDList(String[] customerRowIDList) {
		this.customerRowIDList = customerRowIDList;
	}
}