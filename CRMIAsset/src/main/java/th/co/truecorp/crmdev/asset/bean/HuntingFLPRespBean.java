package th.co.truecorp.crmdev.asset.bean;

import java.util.Arrays;

public class HuntingFLPRespBean extends ResponseBean {

	private Boolean isHunting;
	private Boolean isPilotServiceID;
	private HuntingAssetBean[] childAssetList;
	
	public HuntingFLPRespBean() {
	}

	@Override
	public String toString() {
		return "HuntingFLPRespBean [isHunting=" + isHunting + ", isPilotServiceID=" + isPilotServiceID
				+ ", childAssetList=" + Arrays.toString(childAssetList) + ", getCode()=" + getCode() + ", getMsg()="
				+ getMsg() + ", getTransID()=" + getTransID() + "]";
	}

	public Boolean getIsHunting() {
		return isHunting;
	}

	public void setIsHunting(Boolean isHunting) {
		this.isHunting = isHunting;
	}

	public Boolean getIsPilotServiceID() {
		return isPilotServiceID;
	}

	public void setIsPilotServiceID(Boolean isPilotServiceID) {
		this.isPilotServiceID = isPilotServiceID;
	}

	public HuntingAssetBean[] getChildAssetList() {
		return childAssetList;
	}

	public void setChildAssetList(HuntingAssetBean[] childAssetList) {
		this.childAssetList = childAssetList;
	}
}