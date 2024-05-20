package th.co.truecorp.crmdev.asset.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import th.co.truecorp.crmdev.util.common.JsonFacade;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetResponseBean extends JsonFacade<AssetResponseBean> {

	private String code;
	private String msg;
	private String transactionId;
	protected AssetDataBean asset;
	
	public AssetResponseBean() {
		super(AssetResponseBean.class);
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

	public AssetDataBean getAsset() {
		return asset;
	}

	public void setAsset(AssetDataBean asset) {
		this.asset = asset;
	}
}