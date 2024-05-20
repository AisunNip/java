package th.co.truecorp.crmdev.asset.bean;

import java.util.Calendar;

public class DummyMsisdnReqBean {

	private String dummyServiceID;
	private String serviceID;
	private Calendar effectiveDate;

	public DummyMsisdnReqBean() {
	}
	
	@Override
	public String toString() {
		return "DummyMsisdnReqBean [dummyServiceID=" + dummyServiceID + ", serviceID=" + serviceID + ", effectiveDate="
				+ effectiveDate + "]";
	}

	public String getDummyServiceID() {
		return dummyServiceID;
	}

	public void setDummyServiceID(String dummyServiceID) {
		this.dummyServiceID = dummyServiceID;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public Calendar getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Calendar effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}