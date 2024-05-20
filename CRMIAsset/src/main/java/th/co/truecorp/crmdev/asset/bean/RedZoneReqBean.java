package th.co.truecorp.crmdev.asset.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.core.JsonProcessingException;

import th.co.truecorp.crmdev.util.common.JsonFacade;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RedZoneReqBean extends JsonFacade<RedZoneReqBean> {

	public RedZoneReqBean() {
		super(RedZoneReqBean.class);
	}

	private String transactionId;
	private AssetDataBean asset;
	private CustomerAccountBean customerAccount;
	private ParentCustomerBean parentCustomer;
	
	@Override
	public String toString() {
		try {
			return this.objectToString(this);
		}
		catch (JsonProcessingException e) {
		}
		
		return null;
	}
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public AssetDataBean getAsset() {
		return asset;
	}

	public void setAsset(AssetDataBean asset) {
		this.asset = asset;
	}

	public CustomerAccountBean getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(CustomerAccountBean customerAccount) {
		this.customerAccount = customerAccount;
	}

	public ParentCustomerBean getParentCustomer() {
		return parentCustomer;
	}

	public void setParentCustomer(ParentCustomerBean parentCustomer) {
		this.parentCustomer = parentCustomer;
	}
}
