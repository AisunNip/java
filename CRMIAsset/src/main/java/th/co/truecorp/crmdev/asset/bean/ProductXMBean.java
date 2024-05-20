package th.co.truecorp.crmdev.asset.bean;

public class ProductXMBean {

	private String productXMRowID;
	private String parProductRowID;
	private String type; // Product, Offer Group
	private String composeProductRowID;
	private String productType; // Offer Group, Function Product, Access Product
	private String offerCode; // ProductCode, OfferGroupCode
	private String offerName; // ProductName, OfferGroupName
	
	public ProductXMBean() {
	}

	public String getProductXMRowID() {
		return productXMRowID;
	}

	public void setProductXMRowID(String productXMRowID) {
		this.productXMRowID = productXMRowID;
	}

	public String getParProductRowID() {
		return parProductRowID;
	}

	public void setParProductRowID(String parProductRowID) {
		this.parProductRowID = parProductRowID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComposeProductRowID() {
		return composeProductRowID;
	}

	public void setComposeProductRowID(String composeProductRowID) {
		this.composeProductRowID = composeProductRowID;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getOfferCode() {
		return offerCode;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
}