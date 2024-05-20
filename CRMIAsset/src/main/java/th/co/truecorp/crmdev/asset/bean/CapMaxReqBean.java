package th.co.truecorp.crmdev.asset.bean;

import java.util.Calendar;

import com.fasterxml.jackson.core.JsonProcessingException;

import th.co.truecorp.crmdev.util.common.JsonFacade;

public class CapMaxReqBean extends JsonFacade<CapMaxReqBean> {

	private String serviceID;
	private String ban;
	private String masterFlag;
	private Calendar cycleDate;
	private String[] cpcCategoryCodeList;
	
	public CapMaxReqBean() {
		super(CapMaxReqBean.class);
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

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public String getMasterFlag() {
		return masterFlag;
	}

	public void setMasterFlag(String masterFlag) {
		this.masterFlag = masterFlag;
	}

	public Calendar getCycleDate() {
		return cycleDate;
	}

	public void setCycleDate(Calendar cycleDate) {
		this.cycleDate = cycleDate;
	}

	public String[] getCpcCategoryCodeList() {
		return cpcCategoryCodeList;
	}

	public void setCpcCategoryCodeList(String[] cpcCategoryCodeList) {
		this.cpcCategoryCodeList = cpcCategoryCodeList;
	}
}