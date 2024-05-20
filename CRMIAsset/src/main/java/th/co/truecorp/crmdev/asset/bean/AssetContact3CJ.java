package th.co.truecorp.crmdev.asset.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AssetContact3CJ {
	
	private String channel;
	private String preferenceContactNo;
	private String status;
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getPreferenceContactNo() {
		return preferenceContactNo;
	}
	public void setPreferenceContactNo(String preferenceContactNo) {
		this.preferenceContactNo = preferenceContactNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "AssetContact3CJ [channel=" + channel + ", preferenceContactNo=" + preferenceContactNo + ", status="
				+ status + "]";
	}	
}
