package th.co.truecorp.crmdev.asset.bean;

public class LivingRespBean extends ResponseBean {

	private String livingType;
	private String livingStyle;
	
	public LivingRespBean() {
	}

	public String getLivingType() {
		return livingType;
	}

	public void setLivingType(String livingType) {
		this.livingType = livingType;
	}

	public String getLivingStyle() {
		return livingStyle;
	}

	public void setLivingStyle(String livingStyle) {
		this.livingStyle = livingStyle;
	}
}