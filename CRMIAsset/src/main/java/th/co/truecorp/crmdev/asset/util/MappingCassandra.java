package th.co.truecorp.crmdev.asset.util;

import th.co.truecorp.crmdev.asset.bean.Constants;
import th.co.truecorp.crmdev.asset.exception.MappingException;
import th.co.truecorp.crmdev.util.common.Validator;
import th.co.truecorp.crmdev.util.common.bean.ErrorCodeResp;
import th.co.truecorp.crmdev.util.db.exception.MappingFailedException;
import th.co.truecorp.crmdev.util.db.mapping.CassandarMapping;

public class MappingCassandra {

	private Validator validator;
	private AssetErrorCode assetErrorCode;
	
	public MappingCassandra() {
		this.validator = new Validator();
		this.assetErrorCode =  new AssetErrorCode();
	}
	
	public String getMappingCassandra(String fromValue, String type) throws MappingFailedException, MappingException {
		String result = null;
		if(this.validator.hasStringValue(fromValue) && this.validator.hasStringValue(type)) {
			try {
				result = CassandarMapping.getMapping(type, fromValue);
			}
			catch (MappingFailedException mapEx) {
				String errorMessage[] = {"Mapping cassandra fromValue : "+fromValue+", type : "+type,"Cassandra in CRM"};
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_INVALID_CODE, errorMessage);
				throw new MappingException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
			}
		}
		return result;
	}
}
