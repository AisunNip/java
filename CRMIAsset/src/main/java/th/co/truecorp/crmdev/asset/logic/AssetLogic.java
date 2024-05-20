package th.co.truecorp.crmdev.asset.logic;

import java.net.UnknownHostException;

import th.co.truecorp.crmdev.asset.AssetLogger;
import th.co.truecorp.crmdev.asset.bean.AssetDataBean;
import th.co.truecorp.crmdev.asset.bean.AssetProductBean;
import th.co.truecorp.crmdev.asset.bean.AssetRelationShipReqBean;
import th.co.truecorp.crmdev.asset.bean.Constants;
import th.co.truecorp.crmdev.asset.bean.CustomerAccountBean;
import th.co.truecorp.crmdev.asset.bean.RedZoneReqBean;
import th.co.truecorp.crmdev.asset.bean.ResponseBean;
import th.co.truecorp.crmdev.asset.bean.SiebelAuthorizationBean;
import th.co.truecorp.crmdev.asset.proxy.CRMESBProxy;
import th.co.truecorp.crmdev.asset.proxy.SiebelProxy;
import th.co.truecorp.crmdev.asset.util.AssetErrorCode;
import th.co.truecorp.crmdev.asset.util.CRMESBTransform;
import th.co.truecorp.crmdev.asset.util.SiebelTransform;
import th.co.truecorp.crmdev.util.common.Validator;
import th.co.truecorp.crmdev.util.common.Xpath;
import th.co.truecorp.crmdev.util.common.bean.BackendInfo;
import th.co.truecorp.crmdev.util.common.bean.ErrorCodeResp;
import th.co.truecorp.crmdev.util.logging.LogProductName;
import th.co.truecorp.crmdev.util.logging.LogSystem;
import th.co.truecorp.crmdev.util.net.http.HttpResponse;
import th.co.truecorp.crmdev.util.common.UUIDManager;

public class AssetLogic {
	
    private Validator validator;
    private AssetErrorCode assetErrorCode;
    private AssetLogger logger;
    
    public AssetLogic() throws UnknownHostException {
        this.validator = new Validator();
        this.assetErrorCode = new AssetErrorCode();
        this.logger = new AssetLogger(LogProductName.All, LogSystem.CRM_INBOUND, LogSystem.CRM_INBOUND);
    }

    public CustomerAccountBean createUpdateCustomerIndyRedZone(RedZoneReqBean redZoneReqBean) throws Exception {
    	
        String xmlRequest;
        HttpResponse httpResponse;
        Xpath respXpath;
        
        CustomerAccountBean customerAccountBean = new CustomerAccountBean();
        //Change Transaction id 
        redZoneReqBean.setTransactionId(UUIDManager.getUUID().toString());
        xmlRequest = CRMESBTransform.generateCreateUpdateCustomerIndyRedZone(redZoneReqBean);
        CRMESBProxy crmesbProxy = new CRMESBProxy();
        httpResponse = crmesbProxy.createUpdateCustomerIndy(redZoneReqBean.getTransactionId(), xmlRequest);
        respXpath = new Xpath(httpResponse.getResponseText());
        String goldenId = respXpath.getVal("/Envelope/Body/createUpd_Indv_CustomerRegistrationResponse/TransactionResp/GoldenId");
        String errorCode = respXpath.getVal("/Envelope/Body/createUpd_Indv_CustomerRegistrationResponse/TransactionResp/ErrorObjects/ErrorCode");
        String errorMsg = respXpath.getVal("/Envelope/Body/createUpd_Indv_CustomerRegistrationResponse/TransactionResp/ErrorObjects/ErrorDescription");
        customerAccountBean.setGoldenId(goldenId);
        customerAccountBean.setErrorCode(errorCode);
        customerAccountBean.setErrorMsg(errorMsg);
        return customerAccountBean;
    }
    
    public AssetDataBean createUpdateAssetMobileRedZone(RedZoneReqBean redZoneReqBean, SiebelAuthorizationBean siebelAuthenBean) throws Exception {
    	
        String xmlRequest;
        AssetDataBean assetDataBean = redZoneReqBean.getAsset();
        
        if(redZoneReqBean.getAsset().isDummyMsisdnFlag()) {
        	assetDataBean.setIntegrationId(redZoneReqBean.getAsset().getDummyMsisdn());
        	assetDataBean.setSerialNo(redZoneReqBean.getAsset().getDummyMsisdn());   
        	AssetProductBean assetProductBean = new AssetProductBean();
            assetProductBean.setProductPartNo("Mobile Prepay");
            assetDataBean.setAssetProductBean(assetProductBean);
            assetDataBean.setStatus("Dummy");
        }
        else if ("Prepay".equals(assetDataBean.getProductType())) {
        	/* old
            assetDataBean.setIntegrationId("A" + assetDataBean.getServiceId());
            assetDataBean.setDataSource("CCBS");
            AssetProductBean assetProductBean = new AssetProductBean();
            assetProductBean.setProductPartNo("Mobile Prepay");
            assetDataBean.setAssetProductBean(assetProductBean);
            assetDataBean.setSerialNo(assetDataBean.getServiceId());
            assetDataBean.setStatus("Active");
            */
            /*
			 * Prepaid Redesign
			 */
        	 assetDataBean.setAssetId(assetDataBean.getAssetId());
             assetDataBean.setDataSource("CCBS");
             AssetProductBean assetProductBean = new AssetProductBean();
             assetProductBean.setProductPartNo("Mobile Prepay");
             assetDataBean.setAssetProductBean(assetProductBean);
             assetDataBean.setSerialNo(assetDataBean.getServiceId());
             assetDataBean.setStatus("Active");
            /*
             * 
             */
           
        } else {
            assetDataBean.setAssetNo("CMH" + assetDataBean.getSubscriberNo());
            assetDataBean.setDataSource("CCBS");
            AssetProductBean assetProductBean = new AssetProductBean();
            assetProductBean.setProductPartNo("Mobile Postpay");
            assetDataBean.setAssetProductBean(assetProductBean);
            assetDataBean.setSerialNo(assetDataBean.getServiceId());
            assetDataBean.setStatus("Active");
        }
        
        xmlRequest = SiebelTransform.generateSyncAssetRedZone(redZoneReqBean, siebelAuthenBean);
        
        return callCreateUpdateAsset(redZoneReqBean,xmlRequest);
    }

    private AssetDataBean callCreateUpdateAsset(RedZoneReqBean redZoneReqBean,String xmlRequest) throws Exception {
        SiebelProxy siebelProxy = new SiebelProxy();
        HttpResponse httpResponse = siebelProxy.syncAsset(redZoneReqBean.getTransactionId(), xmlRequest);
        Xpath respXpath = new Xpath(httpResponse.getResponseText());
        String assetId = respXpath.getVal("/Envelope/Body/SyncAsset_Output/ListOfTru1EaiAssetIo/AssetMgmt-Asset/RootAssetId");
        AssetDataBean resultBean = new AssetDataBean();
        resultBean.setAssetId(assetId);
        return resultBean;
    }
    
	public ResponseBean createUpdateAssetRelationship(AssetRelationShipReqBean assetRelationShipReqBean,
			SiebelAuthorizationBean siebelAuthenBean) throws Exception {
		
		ResponseBean responseBean = new ResponseBean();
		SiebelProxy siebelProxy = new SiebelProxy();
		String transID = assetRelationShipReqBean.getTransID();
		responseBean.setTransID(transID);
		
		String xmlRequest = SiebelTransform.generateUpsertAssetRelationship(assetRelationShipReqBean, siebelAuthenBean);
		HttpResponse httpResponse = siebelProxy.upsertAssetRelationship(transID, xmlRequest);
		
		Xpath respXpath = new Xpath(httpResponse.getResponseText());
		String assetId = respXpath.getVal("/Envelope/Body/UpsertAssetRelationship_Output/AssetId");
		String assetRelationshipId = respXpath.getVal("/Envelope/Body/UpsertAssetRelationship_Output/AssetRelationshipId");
		String errorCode = respXpath.getVal("/Envelope/Body/UpsertAssetRelationship_Output/Error_spcCode");
		String errorMessage = respXpath.getVal("/Envelope/Body/UpsertAssetRelationship_Output/Error_spcMessage");
		
		this.logger.info(transID, "CRMEAI Error_spcCode: " + errorCode 
						+ ", Error_spcMessage: " + errorMessage 
						+ ", AssetId: " + assetId);
		
		if (!this.validator.hasStringValue(assetId) && !this.validator.hasStringValue(assetRelationshipId)) {
			String[] message = {"AssetRelationShip", "CRM"};
			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_INVALID_CODE
										, message);
			responseBean.setCode(errorCodeResp.getErrorCode());
			responseBean.setMsg(errorCodeResp.getErrorMessage());
		}
		else if (!Constants.ErrorCode.SUCCESS_CODE_EAI.equals(errorCode)) {
			
			if("1".equals(errorCode) && !this.validator.hasStringValue(assetRelationshipId)) {
				
				BackendInfo backendInfo = new BackendInfo();
				backendInfo.setErrorCode(errorCode);
				backendInfo.setErrorMessage(errorMessage);
				backendInfo.setMethodName("UpsertAssetRelationship");
				backendInfo.setServiceName("CRMEAI");
				
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generateByAPI(errorMessage, backendInfo);
				
				responseBean.setCode(errorCodeResp.getErrorCode());
				responseBean.setMsg(errorCodeResp.getErrorMessage());
			}
			else {
				responseBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				responseBean.setMsg(Constants.SUCCESS_MSG);
			}
		}
		else {
			responseBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
			responseBean.setMsg(Constants.SUCCESS_MSG);
		}
		
		return responseBean;
	}
}