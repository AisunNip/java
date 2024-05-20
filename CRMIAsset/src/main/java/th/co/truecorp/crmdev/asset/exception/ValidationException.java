package th.co.truecorp.crmdev.asset.exception;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	
	public ValidationException(String errorCode, String errorMessage) {
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