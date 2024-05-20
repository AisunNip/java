package th.co.truecorp.crmdev.asset.bean;

import com.fasterxml.jackson.core.JsonProcessingException;

import th.co.truecorp.crmdev.util.common.JsonFacade;

public class LivingReqBean extends JsonFacade<LivingReqBean> {

	private String qrunAddressID;

	public LivingReqBean() {
		super(LivingReqBean.class);
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

	public String getQrunAddressID() {
		return qrunAddressID;
	}

	public void setQrunAddressID(String qrunAddressID) {
		this.qrunAddressID = qrunAddressID;
	}
}