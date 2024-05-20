package th.co.truecorp.crmdev.asset.exception;

import th.co.truecorp.crmdev.util.common.bean.ErrorCodeResp;

public class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ErrorCodeResp errorCodeResp;

	public ApplicationException(ErrorCodeResp errorCodeResp) {
		super(errorCodeResp.getErrorMessage());
		this.errorCodeResp = errorCodeResp;
	}
	
	public ErrorCodeResp getErrorCodeResp() {
		return errorCodeResp;
	}

	public void setErrorCodeResp(ErrorCodeResp errorCodeResp) {
		this.errorCodeResp = errorCodeResp;
	}

}
