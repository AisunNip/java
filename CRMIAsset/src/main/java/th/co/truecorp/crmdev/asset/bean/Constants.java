package th.co.truecorp.crmdev.asset.bean;

public interface Constants {

	public static String APPLICATION_NAME 	= "CRMIAsset";
	public static int QUERY_TIMEOUT_IN_SEC  = 30;
	public static String SUCCESS_MSG 		= "Success";
	
	public static int LIMIT_MAX_EXPRESSION	= 900;
	
	public static interface CustomerAccountType {
		public static String INDIVIDUAL 	= "Individual";
		public static String BUSINESS 		= "Business";
	}
	
	public static interface ProductType {
		public static String PREPAY 	= "Prepay";
		public static String POSTPAY 	= "Postpay";
	}
	
	public static interface AssetStatus {
		public static String ALL_ACTIVE = "All Active";
		public static String ACTIVE 	= "Active";
		public static String TEMPORARY_DISCONNECTED = "Temporary Disconnected";
		public static String INACTIVE 	= "Inactive";
		public static String TERMINATE 	= "Terminate";
		public static String CANCELLED	= "Cancelled";
	}
	
	public static interface SystemCode {
		public static String CRM_DATABASE 	= "CDB";
		public static String CRM_INBOUND 	= "CIB";
		public static String CRM_EAI 		= "CRMEAI";
	}
	
	public static interface ModuleCode {
		public static String ASSET 			= "AS";
	}

	public static interface Method {
		public static String UPDATE_ASSET = "UpdateAsset";
	}
	
	public static interface ErrorCode {
		public static final String SUCCESS_CODE 		= "0";
		public static final String SUCCESS_CODE_EAI		= "00";
		public static final String REQUIRED_FIELDS 		= "101000";
		public static final String INVALID_CODE			= "102000";
		public static final String NO_MATCH_CODE		= "104000";
		public static final String OVER_RANGE_CODE		= "105000";
		public static final String DATA_NOT_FOUND 		= "201000";
		public static final String MANY_ROWS_CODE 		= "202000";
		public static final String DATA_INVALID_CODE 	= "203000";
		public static final String BUSINESS_CODE		= "300000";
		public static final String APPLICATION 			= "400000";
		
		public static final String CRM_DATABASE_CODE 	= "802014";
		
		public static final String CRM_EAI_ERROR 		= "900025";
		public static final String XML_API_CODE 		= "920000"; 
		public static final String WEB_SERVICE_API_CODE = "920001";
		public static final String REST_API_CODE 		= "920003";
		public static final String CRM_INBOUND_ERROR 	= "900026";
		
		public static final String INT_SUCCESS_CODE 	= "OSBbllngA00001";
		public static final String INT_NOT_FOUND_CODE 	= "OSBbllngA10001";
	}
	
	public static interface MassageCode {
		public static final String SUB_NOT_FOUND  		= "SubNo not found";
	}
}