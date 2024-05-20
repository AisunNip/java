package th.co.truecorp.crmdev.asset.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AccountBean {

	private String transactionId;
	private String rowId;
	private CustomerAccountBean customer;
	private BillingAggregatorAccountBean bag;
	private BillingAccountBean billing;
	private String consolidateFlag;
	private String mode;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public CustomerAccountBean getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerAccountBean customer) {
		this.customer = customer;
	}

	public BillingAggregatorAccountBean getBag() {
		return bag;
	}

	public void setBag(BillingAggregatorAccountBean bag) {
		this.bag = bag;
	}

	public BillingAccountBean getBilling() {
		return billing;
	}

	public void setBilling(BillingAccountBean billing) {
		this.billing = billing;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getConsolidateFlag() {
		return consolidateFlag;
	}

	public void setConsolidateFlag(String consolidateFlag) {
		this.consolidateFlag = consolidateFlag;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
