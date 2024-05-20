package th.co.truecorp.crmdev.asset.exception;

public class RequiredFieldException extends Exception {

	private String fieldName;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RequiredFieldException(String fieldName) {
		super();
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	
}
