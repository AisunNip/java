package th.co.truecorp.crmdev.asset.validate;

import th.co.truecorp.crmdev.asset.bean.CustomerAccountBean;
import th.co.truecorp.crmdev.asset.exception.ValidationException;
import th.co.truecorp.crmdev.asset.util.AssetErrorCode;
import th.co.truecorp.crmdev.util.common.Validator;
import th.co.truecorp.crmdev.util.common.bean.ErrorCodeResp;

public class CustomerAccountValidation {

	private AssetErrorCode assetErrorCode;
	private Validator validator;

	public CustomerAccountValidation() {
		this.assetErrorCode = new AssetErrorCode();
		this.validator = new Validator();
	}

	private void throwRequiredFieldException(String fieldName) throws ValidationException {
		ErrorCodeResp errorCodeResp = this.assetErrorCode.generateRequiredField(fieldName);
		throw new ValidationException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
	}

	private void throwDataInvalidException(String fieldName) throws ValidationException {
		ErrorCodeResp errorCodeResp = this.assetErrorCode.generateDataInvalid(fieldName, "CRM");
		throw new ValidationException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
	}
	
	public void validateUpsertAssetRedZone(CustomerAccountBean customerAccountBean) throws ValidationException {
		if (customerAccountBean == null) {
			this.throwRequiredFieldException("customerAccountBean");
		}
		else{
			if (this.validator.hasStringValue(customerAccountBean.getIdType())) {
				if (!"I".equals(customerAccountBean.getIdType())){
					this.throwDataInvalidException("IdType: " + customerAccountBean.getIdType());
				}
			}
			else{
				this.throwRequiredFieldException("IdType");
			}

			if(!this.validator.hasStringValue(customerAccountBean.getIdNumber())) {
				this.throwRequiredFieldException("IdNumber");
			}

			if(!this.validator.hasStringValue(customerAccountBean.getTitle())) {
				this.throwRequiredFieldException("Title");
			}

			if(!this.validator.hasStringValue(customerAccountBean.getFirstName())) {
				this.throwRequiredFieldException("FirstName");
			}

			if(!this.validator.hasStringValue(customerAccountBean.getLastName())) {
				this.throwRequiredFieldException("LastName");
			}

			if(!this.validator.hasStringValue(customerAccountBean.getGender())) {
				this.throwRequiredFieldException("Gender");
			}

			if(this.validator.hasStringValue(customerAccountBean.getBirthDate())) {
				if(!isValidFormat(customerAccountBean.getBirthDate())){
					this.throwRequiredFieldException("BirthDate :yyyy-MM-dd'T'HH:mm:ss.SSSZ");
				}
			}else{
				this.throwRequiredFieldException("BirthDate");
			}

			if(this.validator.hasStringValue(customerAccountBean.getProductType())) {
				if("Postpay".equals(customerAccountBean.getProductType())){
					if(!this.validator.hasStringValue(customerAccountBean.getCcbsCustId())) {
						this.throwRequiredFieldException("CcbsCustId");
					}
				}
			}else{
				this.throwRequiredFieldException("ProductType");
			}
		}
	}

	private boolean isValidFormat(String value) {
		//yyyy-MM-dd'T'HH:mm:ss.SSSZ
		//2019-10-01T09:45:00.000+0700
		boolean isValid = true;
		if (!value.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}[+,-]\\d{4}")) {
			isValid = false;
		}
		return isValid;
	}
}