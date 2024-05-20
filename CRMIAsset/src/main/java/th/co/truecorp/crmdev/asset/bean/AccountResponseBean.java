package th.co.truecorp.crmdev.asset.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import th.co.truecorp.crmdev.util.common.JsonFacade;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AccountResponseBean extends JsonFacade<AccountResponseBean> {

	protected String code;
	protected String msg;
	protected String transactionId;
	protected AccountBean account;
	
	public AccountResponseBean() {
		super(AccountResponseBean.class);
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public AccountBean getAccount() {
		return account;
	}

	public void setAccount(AccountBean account) {
		this.account = account;
	}

}
