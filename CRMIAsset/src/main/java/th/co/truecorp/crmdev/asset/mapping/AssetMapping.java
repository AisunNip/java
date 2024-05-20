package th.co.truecorp.crmdev.asset.mapping;

import th.co.truecorp.crmdev.asset.bean.AssetReqBean;
import th.co.truecorp.crmdev.asset.bean.RedZoneReqBean;
import th.co.truecorp.crmdev.util.common.Validator;
import th.co.truecorp.crmdev.util.db.exception.MappingFailedException;
import th.co.truecorp.crmdev.util.db.mapping.CassandarMapping;

public class AssetMapping {
	
	public static void mappingCommonAsset(AssetReqBean asset) {
		if (asset != null && asset.getAsset().getSubscriberName() != null) {
			if (asset.getAsset().getSubscriberName().getOrganizationName()==null||
					asset.getAsset().getSubscriberName().getOrganizationName().equals("")	) {
//			  asset.getAsset().getSubscriberName().setTitle(MappingUtil.getVal("CUSTOMERTITLE", asset.getAsset().getSubscriberName().getTitle()));
			}
		}
	}
	
	public static void mappingUpdateAsset(AssetReqBean asset) {
		mappingCommonAsset(asset);
	}
	
	public void mappingupsertAssetRedZone(RedZoneReqBean redZoneReqBean) throws MappingFailedException {
		Validator validator = new Validator();
		
		if(redZoneReqBean.getCustomerAccount() != null) {
			redZoneReqBean.getCustomerAccount().setIdType(CassandarMapping.getMapping("IDTYPE", redZoneReqBean.getCustomerAccount().getIdType()));
		}
		
		if (validator.hasStringValue(redZoneReqBean.getAsset().getVerifyResult())) {
			redZoneReqBean.getAsset().setVerifyResult(CassandarMapping.getVal("REDZONE.VERIFY.RESULT", redZoneReqBean.getAsset().getVerifyResult()));
		}
		
		if(validator.hasStringValue(redZoneReqBean.getAsset().getKycData())) {
			if(redZoneReqBean.getAsset().getKycData().trim().length() > 0) {
				redZoneReqBean.getAsset().setKycData("Yes");
			}
		}
		
		if(validator.hasStringValue(redZoneReqBean.getAsset().getTransDocID())
				&& redZoneReqBean.getAsset().getTransDocID().trim().length() > 30) {
			String transDocID = redZoneReqBean.getAsset().getTransDocID().substring(0, 30);
			redZoneReqBean.getAsset().setTransDocID(transDocID);
		}
	}
}
