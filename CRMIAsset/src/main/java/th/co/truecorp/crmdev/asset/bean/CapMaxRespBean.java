package th.co.truecorp.crmdev.asset.bean;

public class CapMaxRespBean extends ResponseBean {

	private CapMaxBean[] capMaxBeanList;

	public CapMaxRespBean() {
	}

	public CapMaxBean[] getCapMaxBeanList() {
		return capMaxBeanList;
	}

	public void setCapMaxBeanList(CapMaxBean[] capMaxBeanList) {
		this.capMaxBeanList = capMaxBeanList;
	}
}