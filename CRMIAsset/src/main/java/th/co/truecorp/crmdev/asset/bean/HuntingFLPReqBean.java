package th.co.truecorp.crmdev.asset.bean;

import com.fasterxml.jackson.core.JsonProcessingException;

import th.co.truecorp.crmdev.util.common.JsonFacade;

public class HuntingFLPReqBean extends JsonFacade<HuntingFLPReqBean> {

	private String pilotServiceID;
	private String pilotBan;
	private String pilotMasterFlag;
	private String pilotAssetRowID;

	public HuntingFLPReqBean() {
		super(HuntingFLPReqBean.class);
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

	public String getPilotServiceID() {
		return pilotServiceID;
	}

	public void setPilotServiceID(String pilotServiceID) {
		this.pilotServiceID = pilotServiceID;
	}

	public String getPilotBan() {
		return pilotBan;
	}

	public void setPilotBan(String pilotBan) {
		this.pilotBan = pilotBan;
	}

	public String getPilotMasterFlag() {
		return pilotMasterFlag;
	}

	public void setPilotMasterFlag(String pilotMasterFlag) {
		this.pilotMasterFlag = pilotMasterFlag;
	}

	public String getPilotAssetRowID() {
		return pilotAssetRowID;
	}

	public void setPilotAssetRowID(String pilotAssetRowID) {
		this.pilotAssetRowID = pilotAssetRowID;
	}
}