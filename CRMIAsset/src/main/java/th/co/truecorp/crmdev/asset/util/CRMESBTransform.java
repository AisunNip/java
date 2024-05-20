package th.co.truecorp.crmdev.asset.util;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import th.co.truecorp.crmdev.asset.bean.AddressBean;
import th.co.truecorp.crmdev.asset.bean.AssetDataBean;
import th.co.truecorp.crmdev.asset.bean.CustomerAccountBean;
import th.co.truecorp.crmdev.asset.bean.RedZoneReqBean;
import th.co.truecorp.crmdev.util.common.CalendarManager;
import th.co.truecorp.crmdev.util.common.XmlBuilderUtil;
import th.co.truecorp.crmdev.util.db.config.CassandraConfig;
import th.co.truecorp.crmdev.util.db.mapping.CassandarMapping;

public class CRMESBTransform {
    
	public static RedZoneReqBean generateCreateUpdateCustomerIndyRedZoneOrdertype35(String jsonString)
			throws Exception {

		RedZoneReqBean redZoneReqBean = new RedZoneReqBean();
		CustomerAccountBean customerAccountBean = new CustomerAccountBean();
    	AddressBean addressBean = new AddressBean();
    	AssetDataBean assetDataBean = new AssetDataBean();    	
    	
    	JSONObject jsonRespObj = new JSONObject(jsonString);
    	JSONObject returnJSonObj = jsonRespObj.getJSONObject("getAllPrepaidProfileListResponse").getJSONObject("return");
    	JSONObject profileJSonObj = returnJSonObj.getJSONObject("profileList").getJSONArray("profileInfoArray").getJSONObject(0);
		
		JSONObject customerJsonObj = profileJSonObj.getJSONObject("customer");
		
		if(jsonGetString(customerJsonObj, "birthDate") != null) {
			Date date = CalendarManager.stringToDate(jsonGetString(customerJsonObj, "birthDate"),CalendarManager.WEB_SERV_DATE_TIME_PATTERN,CalendarManager.LOCALE_EN);
		    String birthDate = CalendarManager.dateToString(date,CalendarManager.IWD_DATE_TIME_PATTERN,CalendarManager.LOCALE_EN);
		    customerAccountBean.setBirthDate(birthDate);    
		}
		
		customerAccountBean.setIdNumber(customerJsonObj.getString("certificateNumber"));
		customerAccountBean.setIdType(CassandarMapping.getMapping("IDTYPE",customerJsonObj.getString("certificateType")));
		//customerAccountBean.setIdType(customerJsonObj.getString("certificateType"));
		
		JSONObject customerNameJsonObj = customerJsonObj.getJSONObject("name");
		customerAccountBean.setTitle(jsonGetString(customerNameJsonObj, "title"));
		customerAccountBean.setFirstName(jsonGetString(customerNameJsonObj, "firstName"));
		customerAccountBean.setLastName(jsonGetString(customerNameJsonObj, "lastName"));
		
		JSONObject customerAddressJsonObj = customerJsonObj.getJSONObject("address");
		
		addressBean.setHouseNo(jsonGetString(customerAddressJsonObj, "houseNo"));
		addressBean.setRoomNo(jsonGetString(customerAddressJsonObj, "roomNo"));
		addressBean.setMoo(jsonGetString(customerAddressJsonObj, "moo"));
		addressBean.setStreet(jsonGetString(customerAddressJsonObj, "street"));		
		addressBean.setSubdistrict(jsonGetString(customerAddressJsonObj, "subDistrict"));
		addressBean.setDistrict(jsonGetString(customerAddressJsonObj, "district"));		
		addressBean.setProvince(jsonGetString(customerAddressJsonObj, "city"));	
		addressBean.setPostalCode(jsonGetString(customerAddressJsonObj, "zipCode"));	
		
		customerAccountBean.setAddress(addressBean);
		
		JSONObject subscriberJsonObj = profileJSonObj.getJSONObject("subscriber");		
		assetDataBean.setProductType("Prepay");
		assetDataBean.setSubscriberNo(jsonGetString(subscriberJsonObj, "subscriberId"));
		
		redZoneReqBean.setCustomerAccount(customerAccountBean);
		redZoneReqBean.setAsset(assetDataBean);
		
		return redZoneReqBean; 			
    }
    
    private static String jsonGetString(JSONObject jsonObject,String key) throws JSONException {
		return jsonObject.has(key)?jsonObject.getString(key):null;    	
    }
    
    public static String generateCreateUpdateCustomerIndyRedZone(RedZoneReqBean redZoneReqBean) throws Exception {
        String builder = XmlBuilderUtil.node(
                "soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sieb=\"http://crm/SiebelService/\" "
                + "xmlns:sieb1=\"http://crm/mbo/SiebelMBO\" xmlns:shar=\"http://crm/SharedMBO\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"") +
                XmlBuilderUtil.node("soapenv:Body") +
                getXmlCreateCustomerIndyRedZone(redZoneReqBean) +
                XmlBuilderUtil.node("/soapenv:Body") +
                XmlBuilderUtil.node("/soapenv:Envelope");
        return builder;
    }       
    
    private static String getXmlCreateCustomerIndyRedZone(RedZoneReqBean redZoneReqBean) throws Exception {
    	
        CustomerAccountBean customerAccountBean = redZoneReqBean.getCustomerAccount();
        AssetDataBean assetDataBean = redZoneReqBean.getAsset();
        String sourceSystemDB;
        String sourceSystemAccountId;
        
        if ("Prepay".equals(assetDataBean.getProductType())) {
            sourceSystemDB = "NPP";
        } else {
            sourceSystemDB = "CCBS";
        }
        String legacySystemId;
        if ("Prepay".equals(assetDataBean.getProductType())) {
            legacySystemId = "Prepay_CCBS";
        } else {
            legacySystemId = "CCBS";
        }
        if ("Prepay".equals(assetDataBean.getProductType())) {
            sourceSystemAccountId = assetDataBean.getSubscriberNo();
        } else {
            sourceSystemAccountId = customerAccountBean.getCcbsCustId();
        }
        Date date = CalendarManager.stringToDate(customerAccountBean.getBirthDate(),CalendarManager.IWD_DATE_TIME_PATTERN,CalendarManager.LOCALE_EN);
        String bDate = CalendarManager.dateToString(date,CalendarManager.DATE_PATTERN,CalendarManager.LOCALE_EN);

        StringBuilder builder = new StringBuilder();
		builder.append(XmlBuilderUtil.node("sieb:createUpd_Indv_CustomerRegistration"))
				.append(XmlBuilderUtil.node("CustomerIdentification"))
				.append(XmlBuilderUtil.node("sieb1:IdType", customerAccountBean.getIdType()))
				.append(XmlBuilderUtil.node("sieb1:IdNumber", customerAccountBean.getIdNumber()))
				.append(XmlBuilderUtil.node("sieb1:FirstName", customerAccountBean.getFirstName()))
				.append(XmlBuilderUtil.node("sieb1:LastName", customerAccountBean.getLastName()))
				.append(XmlBuilderUtil.node("sieb1:SourceSystemDB", sourceSystemDB))
				.append(XmlBuilderUtil.node("sieb1:SourceSystemAccountId", sourceSystemAccountId))
				.append(XmlBuilderUtil.node("/CustomerIdentification"));
				
		builder.append(XmlBuilderUtil.node("Customer"))
		
				.append(XmlBuilderUtil.node("sieb1:UpdateOnMDM", "Y"))
				.append(XmlBuilderUtil.node("sieb1:SourceSystemDB", sourceSystemDB))
				.append(XmlBuilderUtil.node("sieb1:Title", customerAccountBean.getTitle()))
				.append(XmlBuilderUtil.node("sieb1:FirstName", customerAccountBean.getFirstName()))
				.append(XmlBuilderUtil.node("sieb1:LastName", customerAccountBean.getLastName()))
				.append(XmlBuilderUtil.node("sieb1:Gender", customerAccountBean.getGender()))
				.append(XmlBuilderUtil.node("sieb1:BirthDate", bDate))
				.append(XmlBuilderUtil.node("sieb1:SourceSystemAccountId", sourceSystemAccountId))
				.append(XmlBuilderUtil.node("sieb1:Identifications"))
				.append(XmlBuilderUtil.node("sieb1:IdNumber", customerAccountBean.getIdNumber()))
				.append(XmlBuilderUtil.node("sieb1:IdType", customerAccountBean.getIdType()))
				.append(XmlBuilderUtil.node("/sieb1:Identifications"));
		
		if(customerAccountBean.getAddress() != null) {
			AddressBean addressBean = customerAccountBean.getAddress();
			builder.append(XmlBuilderUtil.node("sieb1:Addresses"))
					.append(XmlBuilderUtil.node("sieb1:AddressSource","SBL"))
					.append(XmlBuilderUtil.node("sieb1:AddressType","Customer"))
					.append(XmlBuilderUtil.node("sieb1:House",addressBean.getHouseNo()))
					.append(XmlBuilderUtil.node("sieb1:Moo",addressBean.getMoo()))
					.append(XmlBuilderUtil.node("sieb1:Room",addressBean.getRoomNo()))
					.append(XmlBuilderUtil.node("sieb1:Floor",addressBean.getFloor()))
					.append(XmlBuilderUtil.node("sieb1:Building",addressBean.getBuilding()))
					.append(XmlBuilderUtil.node("sieb1:Soi",addressBean.getSoi()))
					.append(XmlBuilderUtil.node("sieb1:StreetAddress",addressBean.getStreet()))
					.append(XmlBuilderUtil.node("sieb1:Khwang",addressBean.getSubdistrict()))
					.append(XmlBuilderUtil.node("sieb1:Khet",addressBean.getDistrict()))
					.append(XmlBuilderUtil.node("sieb1:Province",addressBean.getProvince()))
					.append(XmlBuilderUtil.node("sieb1:PostalCode",addressBean.getPostalCode()))
					.append(XmlBuilderUtil.node("/sieb1:Addresses"));
		}
		
		builder.append(XmlBuilderUtil.node("/Customer"));
		
		builder.append(XmlBuilderUtil.node("TransactionInfo"))
				.append(XmlBuilderUtil.node("shar:Username", CassandraConfig.getVal("esb.siebel.user")))
				.append(XmlBuilderUtil.node("shar:Password", CassandraConfig.getVal("esb.siebel.password")))
				.append(XmlBuilderUtil.node("shar:TransactionId", redZoneReqBean.getTransactionId()))
				.append(XmlBuilderUtil.node("shar:LegacySystemId", legacySystemId))
				.append(XmlBuilderUtil.node("shar:DataDate", CalendarManager.dateToString(new Date(), CalendarManager.DATE_TIME_PATTERN, CalendarManager.LOCALE_EN)))
				.append(XmlBuilderUtil.node("shar:IgnoreNullAttribute", "Y"))
				.append(XmlBuilderUtil.node("/TransactionInfo"))
				.append(XmlBuilderUtil.node("/sieb:createUpd_Indv_CustomerRegistration"));
				
        return builder.toString();
    }
}
