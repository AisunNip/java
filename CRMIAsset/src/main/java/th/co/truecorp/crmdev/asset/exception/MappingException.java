package th.co.truecorp.crmdev.asset.exception;

public class MappingException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	
	public MappingException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}