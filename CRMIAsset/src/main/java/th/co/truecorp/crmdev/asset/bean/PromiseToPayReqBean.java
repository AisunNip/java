package th.co.truecorp.crmdev.asset.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import th.co.truecorp.crmdev.asset.bean.AssetDataBean;
import th.co.truecorp.crmdev.util.common.JsonFacade;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PromiseToPayReqBean extends JsonFacade<PromiseToPayReqBean>{

	public PromiseToPayReqBean() {
		super(PromiseToPayReqBean.class);
	}
	
	private String transactionId;
	private AssetDataBean asset;
	
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
	
	@Override
	public String toString() {
		return "PromiseToPayReqBean [transactionId=" + transactionId + ", asset=" + asset + "]";
	}
	
}
