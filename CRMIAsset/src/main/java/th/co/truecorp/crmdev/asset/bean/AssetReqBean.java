package th.co.truecorp.crmdev.asset.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.core.JsonProcessingException;

import th.co.truecorp.crmdev.util.common.JsonFacade;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AssetReqBean extends JsonFacade<AssetReqBean> {

	public AssetReqBean() {
		super(AssetReqBean.class);
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
}
