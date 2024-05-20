package th.co.truecorp.crmdev.asset.bean;

public class CapMaxBean {

	private String cpcCategoryCode;
	private Integer currentCount;
	private Double currentPrice;
	
	public CapMaxBean() {
	}

	public String getCpcCategoryCode() {
		return cpcCategoryCode;
	}

	public void setCpcCategoryCode(String cpcCategoryCode) {
		this.cpcCategoryCode = cpcCategoryCode;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
}