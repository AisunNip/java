package th.co.truecorp.crmdev.asset.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONException;
import org.json.JSONObject;
import th.co.truecorp.crmdev.asset.bean.AssetDataBean;
import th.co.truecorp.crmdev.asset.bean.AssetLatestRespBean;
import th.co.truecorp.crmdev.asset.bean.AssetProductBean;
import th.co.truecorp.crmdev.asset.bean.SubscriberNameBean;
import th.co.truecorp.crmdev.asset.proxy.SiebelProxy;
import th.co.truecorp.crmdev.util.common.CalendarManager;
import th.co.truecorp.crmdev.util.common.Validator;
import th.co.truecorp.crmdev.util.net.http.HttpResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AssetUtil {

    public ObjectMapper createObjectMapper() {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.setDateFormat(new SimpleDateFormat(CalendarManager.IWD_DATE_TIME_PATTERN));
        objMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objMapper.setSerializationInclusion(Include.NON_NULL);

        return objMapper;
    }

    public AssetDataBean setUpAssetdataBean(AssetLatestRespBean assetLatestRespBean) {
        AssetDataBean assetDataBean = new AssetDataBean();
        assetDataBean.setAssetId(assetLatestRespBean.getAssetRowId());
        assetDataBean.setServiceId(assetLatestRespBean.getServiceId());
        assetDataBean.setPreferLanguage(assetLatestRespBean.getPreferLanguage());
        assetDataBean.setSourceSystem(assetLatestRespBean.getSourceSystem());
        assetDataBean.setCustomerRowId(assetLatestRespBean.getCustomerRowId());
        assetDataBean.setBillingRowId(assetLatestRespBean.getBillingRowId());
        assetDataBean.setShareBAN(assetLatestRespBean.getShareBAN());
        assetDataBean.setCvip(assetLatestRespBean.getCvip());
        assetDataBean.setAssetNo(assetLatestRespBean.getAssetNo());

        if (assetLatestRespBean.getSubscriberName() != null) {
            SubscriberNameBean SubscriberNameBean = new SubscriberNameBean();
            SubscriberNameBean.setFirstName(assetLatestRespBean.getSubscriberName().getFirstName());
            SubscriberNameBean.setLastName(assetLatestRespBean.getSubscriberName().getLastName());
            SubscriberNameBean.setTitle(assetLatestRespBean.getSubscriberName().getTitle());
        }

        if (assetLatestRespBean.getStartDate() != null) {
            String startDate = CalendarManager.calendarToString(assetLatestRespBean.getStartDate()
                    , CalendarManager.IWD_DATE_TIME_PATTERN, CalendarManager.LOCALE_EN);
            assetDataBean.setStartDate(startDate);
        }

        if (assetLatestRespBean.getEndDate() != null) {
            String endDate = CalendarManager.calendarToString(assetLatestRespBean.getEndDate()
                    , CalendarManager.IWD_DATE_TIME_PATTERN, CalendarManager.LOCALE_EN);
            assetDataBean.setEndDate(endDate);
        }

        assetDataBean.setStatus(assetLatestRespBean.getStatus());
        assetDataBean.setQrunProductId(assetLatestRespBean.getQrunProductId());
        assetDataBean.setQrunAddressId(assetLatestRespBean.getQrunAddressId());
        assetDataBean.setCompanyCode(assetLatestRespBean.getCompanyCode());
        assetDataBean.setBan(assetLatestRespBean.getBan());
        assetDataBean.setIdType(assetLatestRespBean.getIdType());
        assetDataBean.setDealerCode(assetLatestRespBean.getDealerCode());
        assetDataBean.setDisconnectType(assetLatestRespBean.getDisconnectType());
        assetDataBean.setRelatedServiceId(assetLatestRespBean.getRelatedServiceId());
        assetDataBean.setCreatedBy(assetLatestRespBean.getLastUpdatedBy());

        if (assetLatestRespBean.getCreatedDate() != null) {
            String createdDate = CalendarManager.calendarToString(assetLatestRespBean.getCreatedDate(),
                    CalendarManager.IWD_DATE_TIME_PATTERN, CalendarManager.LOCALE_EN);
            assetDataBean.setCreatedDate(createdDate);
        }
        assetDataBean.setLastUpdatedBy(assetLatestRespBean.getLastUpdatedBy());

        if (assetLatestRespBean.getLastUpdatedDate() != null) {
            String lastUpdatedDate = CalendarManager.calendarToString(assetLatestRespBean.getLastUpdatedDate(),
                    CalendarManager.IWD_DATE_TIME_PATTERN, CalendarManager.LOCALE_EN);
            assetDataBean.setLastUpdatedDate(lastUpdatedDate);
        }

        if (assetLatestRespBean.getProduct() != null) {
            AssetProductBean assetProductBean = new AssetProductBean();
            assetProductBean.setProductName(assetLatestRespBean.getProduct().getProductName());
            assetProductBean.setProductPartNo(assetLatestRespBean.getProduct().getProductPartNo());
            assetProductBean.setProductLine(assetLatestRespBean.getProduct().getProductLine());
            assetProductBean.setProductType(assetLatestRespBean.getProduct().getProductType());
            assetProductBean.setProductCategory(assetLatestRespBean.getProduct().getProductCategory());

            assetDataBean.setAssetProductBean(assetProductBean);
        }

        assetDataBean.setAssetPassword(assetLatestRespBean.getAssetPassword());

        return assetDataBean;
    }

    public JSONObject generateCreateUpdateAccountReq(String transactionId, String ban
    	, String accountServiceLevel, String accountType, String customerRowId) throws JSONException {
       
        JSONObject jsonAction = new JSONObject();
        jsonAction.put("accountServiceLevel", accountServiceLevel);
        jsonAction.put("accountType", accountType);
        jsonAction.put("ban", ban);
        jsonAction.put("customerRowId", customerRowId);
    
        JSONObject jsonObjectRequest = new JSONObject();
        jsonObjectRequest.put("customer", jsonAction);
        jsonObjectRequest.put("transactionId", transactionId);

        return jsonObjectRequest;
    }

	public JSONObject generateGetLastestAssetReq(String serviceId, String ban) {
		
		JSONObject jsonObjectRequest = new JSONObject();
		
		try {
			JSONObject jsonAction = new JSONObject();
			jsonAction.put("serviceId", serviceId);
			jsonAction.put("ban", ban);
			jsonObjectRequest.put("findBy", jsonAction);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObjectRequest;
	}
	
	public static String findMaxValueSubNo(String subNo, String subNoMax) {
		Validator validator = new Validator();
		if (validator.hasStringValue(subNoMax)) {
			if (validator.hasStringValue(subNo)) {
				if (Integer.parseInt(subNoMax) > Integer.parseInt(subNo)) {
					subNo = subNoMax;
				}
			} else {
				subNo = subNoMax;
			}
		}
		return subNo;
	}
}