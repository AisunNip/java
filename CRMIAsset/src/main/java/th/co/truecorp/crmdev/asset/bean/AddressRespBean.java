package th.co.truecorp.crmdev.asset.bean;

import java.util.List;

public class AddressRespBean extends ResponseBean {

	private List<AddressBean> addressBeanList;

	public AddressRespBean() {
	}

	public List<AddressBean> getAddressBeanList() {
		return addressBeanList;
	}

	public void setAddressBeanList(List<AddressBean> addressBeanList) {
		this.addressBeanList = addressBeanList;
	}
}