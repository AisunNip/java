package th.co.truecorp.crmdev.asset.bean;

public class ProductXMRespBean extends ResponseBean {

	private ProductXMBean[] productXMList;

	public ProductXMRespBean() {
	}

	public ProductXMBean[] getProductXMList() {
		return productXMList;
	}

	public void setProductXMList(ProductXMBean[] productXMList) {
		this.productXMList = productXMList;
	}
}