package th.co.truecorp.crmdev.asset.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import th.co.truecorp.crmdev.asset.bean.AssetCompBean;
import th.co.truecorp.crmdev.asset.bean.AssetComponentBean;
import th.co.truecorp.crmdev.asset.bean.AssetDataBean;
import th.co.truecorp.crmdev.asset.bean.AssetRelationShipReqBean;
import th.co.truecorp.crmdev.asset.bean.AssetReqBean;
import th.co.truecorp.crmdev.asset.bean.DummyMsisdnReqBean;
import th.co.truecorp.crmdev.asset.bean.PromiseToPayReqBean;
import th.co.truecorp.crmdev.asset.bean.RedZoneReqBean;
import th.co.truecorp.crmdev.asset.bean.SiebelAuthorizationBean;
import th.co.truecorp.crmdev.util.common.CalendarManager;
import th.co.truecorp.crmdev.util.common.XmlBuilderUtil;
import th.co.truecorp.crmdev.util.db.config.CassandraConfig;

public class SiebelTransform {

	private static String getHeader(SiebelAuthorizationBean siebelAuthenBean) {
		StringBuilder builder = new StringBuilder();
		builder.append(XmlBuilderUtil.node("soapenv:Header"))
				.append(XmlBuilderUtil.node("UsernameToken xmlns=\"http://siebel.com/webservices\""))
				.append(siebelAuthenBean.getSiebelUserName())
				.append(XmlBuilderUtil.node("/UsernameToken"))
				.append(XmlBuilderUtil.node("PasswordText xmlns=\"http://siebel.com/webservices\""))
				.append(siebelAuthenBean.getSiebelPassword())
				.append(XmlBuilderUtil.node("/PasswordText"))
				.append(XmlBuilderUtil.node("SessionType xmlns=\"http://siebel.com/webservices\""))
				.append("None")
				.append(XmlBuilderUtil.node("/SessionType"))
				.append(XmlBuilderUtil.node("/soapenv:Header"));

		return builder.toString();
	}

	public static String generateSyncAssetReq(AssetReqBean asset, SiebelAuthorizationBean siebelAuthenBean) {
		StringBuilder builder = new StringBuilder();
		builder.append(XmlBuilderUtil.node("soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tru1=\"http://siebel.com/TRU1EAIAssetWS\" xmlns:tru11=\"http://www.siebel.com/xml/TRU1%20EAI%20Asset%20IO\""))
				.append(getHeader(siebelAuthenBean))
				.append(XmlBuilderUtil.node("soapenv:Body"))
				.append(getXmlAsset(asset))
				.append(XmlBuilderUtil.node("/soapenv:Body"))
				.append(XmlBuilderUtil.node("/soapenv:Envelope"));
		return builder.toString();
	}

	private static String getXmlAsset(AssetReqBean asset) {
		StringBuilder builder = new StringBuilder();
		String subscriberTitle = null;
		String subscriberFirstName = null;
		String subscriberLastName = null;

		AssetDataBean assetData = new AssetDataBean();
		if (asset.getAsset() != null) {
			assetData = asset.getAsset();
		} else {
			return null;
		}

		if (assetData.getSubscriberName() != null) {
			if (assetData.getSubscriberName().getFirstName() != null) {
				subscriberTitle = assetData.getSubscriberName().getTitle();
				subscriberFirstName = assetData.getSubscriberName().getFirstName();
				subscriberLastName = assetData.getSubscriberName().getLastName();
			} else if (assetData.getSubscriberName().getOrganizationName() != null) {
				subscriberFirstName = assetData.getSubscriberName().getOrganizationName();
				subscriberTitle = "";
				subscriberLastName = "";
			}
		}

		builder.append(XmlBuilderUtil.node("tru1:SyncAsset_Input"))
				.append(XmlBuilderUtil.node("tru11:ListOfTru1EaiAssetIo"))
				.append(XmlBuilderUtil.node("tru11:AssetMgmt-Asset Operation=\"\""));
		if (assetData.getAssetComponent() == null) { // If update assetComponent dont append AssetId
			builder.append(XmlBuilderUtil.node("tru11:AssetId", assetData.getAssetId()));
		}
		
		builder.append(XmlBuilderUtil.node("tru11:TRU2SubscriberTitle", subscriberTitle))
				.append(XmlBuilderUtil.node("tru11:TRU2SubscriberFirstName", subscriberFirstName))
				.append(XmlBuilderUtil.node("tru11:TRU2SubscriberLastName", subscriberLastName))
				.append(XmlBuilderUtil.node("tru11:TRU2OneBillFlag", assetData.getOneBillFlag()))
				.append(XmlBuilderUtil.node("tru11:TRU2AssetFraud", assetData.getFraud()))
				.append(XmlBuilderUtil.node("tru11:TRU2AssetFraudReason", assetData.getFraudReason()))
				.append(XmlBuilderUtil.node("tru11:TRU2AssetFraudDate", assetData.getFruadDate()))
				.append(XmlBuilderUtil.node("tru11:TRU2AssetVIP", assetData.getAssetVIP()))
				.append(XmlBuilderUtil.node("tru11:AssetNumber", assetData.getAssetNo()));

		/* For U18 Asset parent*/
		if (assetData.getParentCustomer() != null) {
					
			builder.append(XmlBuilderUtil.node("tru11:TRU2ParentFstname", assetData.getParentCustomer().getFirstName()));
			builder.append(XmlBuilderUtil.node("tru11:TRU2ParentLastname", assetData.getParentCustomer().getLastName()));
			builder.append(XmlBuilderUtil.node("tru11:TRU2ParentIdType", assetData.getParentCustomer().getIdType()));
			builder.append(XmlBuilderUtil.node("tru11:TRU2ParentIdNumber", assetData.getParentCustomer().getIdNumber()));
			builder.append(XmlBuilderUtil.node("tru11:TRU2ParentPhone", assetData.getParentCustomer().getContactPhone()));	
			builder.append(XmlBuilderUtil.node("tru11:TRU2ParentUserUpdate", assetData.getParentCustomer().getUpdatedBy()));	

			if (assetData.getParentCustomer().getAddress() != null ) {
				
				builder.append(XmlBuilderUtil.node("tru11:TRU2ParentHouse", assetData.getParentCustomer().getAddress().getHouseNo()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2ParentFloor", assetData.getParentCustomer().getAddress().getFloor()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2ParentRoom", assetData.getParentCustomer().getAddress().getRoomNo()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2ParentBuilding", assetData.getParentCustomer().getAddress().getBuilding()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2ParentMoo", assetData.getParentCustomer().getAddress().getMoo()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2ParentSubSoi", assetData.getParentCustomer().getAddress().getSubSoi()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2ParentSoi", assetData.getParentCustomer().getAddress().getSoi()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2ParentStreetAddress", assetData.getParentCustomer().getAddress().getStreet()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2ParentKhwang", assetData.getParentCustomer().getAddress().getSubdistrict()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2ParentKhet", assetData.getParentCustomer().getAddress().getDistrict()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2ParentProvince", assetData.getParentCustomer().getAddress().getProvince()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2ParentPostalCode", assetData.getParentCustomer().getAddress().getPostalCode()));
			}
		}
		/* End */
		
		/* For DCB KYC*/
		if (assetData.getDcbBean() != null) {
					
			builder.append(XmlBuilderUtil.node("tru11:TRU2DCBPrefixTH", assetData.getDcbBean().getPrefixTH()));
			builder.append(XmlBuilderUtil.node("tru11:TRU2DCBFstnameTH", assetData.getDcbBean().getFstnameTH()));
			builder.append(XmlBuilderUtil.node("tru11:TRU2DCBLastnameTH", assetData.getDcbBean().getLastnameTH()));
			builder.append(XmlBuilderUtil.node("tru11:TRU2DCBPrefixEN", assetData.getDcbBean().getPrefixEN()));
			builder.append(XmlBuilderUtil.node("tru11:TRU2DCBFstnameEN", assetData.getDcbBean().getFstnameEN()));	
			builder.append(XmlBuilderUtil.node("tru11:TRU2DCBLastnameEN", assetData.getDcbBean().getLastnameEN()));	
			builder.append(XmlBuilderUtil.node("tru11:TRU2DCBBirthDate",
					CalendarManager.calendarToString(assetData.getDcbBean().getBirthDate(),
							CalendarManager.SIEBEL_DATE_TIME_PATTERN, CalendarManager.LOCALE_EN)));
			builder.append(XmlBuilderUtil.node("tru11:TRU2DCBEmail", assetData.getDcbBean().getEmail()));	
			builder.append(XmlBuilderUtil.node("tru11:TRU2DCBFlagKYC ", assetData.getDcbBean().getFlagKYC()));	
			builder.append(XmlBuilderUtil.node("tru11:TRU2DCBFlagDOPA ", assetData.getDcbBean().getFlagDOPA()));	

			if (assetData.getDcbBean().getAddress() != null ) {
				
				builder.append(XmlBuilderUtil.node("tru11:TRU2DCBHouse", assetData.getDcbBean().getAddress().getHouseNo()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2DCBFloor", assetData.getDcbBean().getAddress().getFloor()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2DCBRoom", assetData.getDcbBean().getAddress().getRoomNo()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2DCBBuilding", assetData.getDcbBean().getAddress().getBuilding()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2DCBMoo", assetData.getDcbBean().getAddress().getMoo()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2DCBSubSoi", assetData.getDcbBean().getAddress().getSubSoi()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2DCBSoi", assetData.getDcbBean().getAddress().getSoi()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2DCBStreetAddress", assetData.getDcbBean().getAddress().getStreet()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2DCBKhwang", assetData.getDcbBean().getAddress().getSubdistrict()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2DCBKhet", assetData.getDcbBean().getAddress().getDistrict()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2DCBProvince", assetData.getDcbBean().getAddress().getProvince()));
				builder.append(XmlBuilderUtil.node("tru11:TRU2DCBPostalCode", assetData.getDcbBean().getAddress().getPostalCode()));
			}
			
			builder.append(XmlBuilderUtil.node("tru11:TRU2DCBIdNumber", assetData.getDcbBean().getIdNumber()));	
			builder.append(XmlBuilderUtil.node("tru11:TRU2DCBTimeStamp", CalendarManager.calendarToString(assetData.getDcbBean().getTimeStamp(),
					CalendarManager.SIEBEL_DATE_TIME_PATTERN, CalendarManager.LOCALE_EN)));
			builder.append(XmlBuilderUtil.node("tru11:TRU2DCBToken", assetData.getDcbBean().getToken()));	
			builder.append(XmlBuilderUtil.node("tru11:TRU2DCBIPAddress", assetData.getDcbBean().getIpAddress()));
		}
		/* End */
		
		// Two Box Case
		if (assetData.getAssetComponent() != null) {
			//Check 2 Box flag
//			String str2Box = ConfigUtil.getVal("inbound.UpdateAsset.Check2box.DeviceType");
//			boolean Check2Box = Boolean.FALSE;
//			if(null != asset.getAsset() && null != asset.getAsset().getDeviceType()) {
//				if (findBySplitString(asset.getAsset().getDeviceType(),str2Box,"\\|")) {
//					Check2Box = Boolean.TRUE;
//				}
//			}
			//Check 2 Box flag
			
			builder.append(XmlBuilderUtil.node("tru11:SerialNumber", assetData.getServiceId()))
					.append(XmlBuilderUtil.node("tru11:TRU2DeviceType", assetData.getDeviceType()));

			for (AssetComponentBean assetComponentBean : assetData.getAssetComponent()) {
					builder.append(XmlBuilderUtil.node("tru11:AssetMgmt-Asset"));
//						if(Check2Box) {
//							builder.append(XmlBuilderUtil.nodeWithoutNull("tru11:AssetNumber", 
//									assetComponentBean.getAssetNo()+"_"+assetComponentBean.getMaterialCode()));
//						} else {
//							builder.append(XmlBuilderUtil.nodeWithoutNull("tru11:AssetNumber", assetComponentBean.getAssetNo()));
//						}
					builder.append(XmlBuilderUtil.node("tru11:AssetNumber", assetComponentBean.getAssetNo()))
						.append(XmlBuilderUtil.node("tru11:SerialNumber", assetComponentBean.getSerialNo()))
						.append(XmlBuilderUtil.node("tru11:TRU1RootSerialNumber", assetData.getServiceId()))
						.append(XmlBuilderUtil.node("tru11:ProductPartNumber",
								assetComponentBean.getPartNo()))
						.append(XmlBuilderUtil.node("tru11:Status", assetComponentBean.getStatus()))
						.append(XmlBuilderUtil.node("tru11:AutoType", assetComponentBean.getType()))
						.append(XmlBuilderUtil.node("tru11:TRU2ModelName",
								assetComponentBean.getModelName()))
						.append(XmlBuilderUtil.node("tru11:TRU2DeviceType",
								assetComponentBean.getDeviceType()))
						.append(XmlBuilderUtil.node("tru11:TRU2MatCode",
								assetComponentBean.getMaterialCode()))
						.append(XmlBuilderUtil.node("tru11:TRU2Brand", assetComponentBean.getBrand()));
				if ("Inactive".equals(assetComponentBean.getStatus())) {
					// Date date = new Date();
					SimpleDateFormat d = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.US);
					String date = d.format(new Date());
					builder.append(
							XmlBuilderUtil.node("tru11:TRU1EndDate", convertOmxToCrmDateFormat(date)));
				} else {
					SimpleDateFormat d = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.US);
					String date = d.format(new Date());
					builder.append(XmlBuilderUtil.node("tru11:TRU2CustomerAssetFlag", "Y"))
							.append(XmlBuilderUtil.node("tru11:PurchaseDate",
									convertOmxToCrmDateFormat(date)))
							.append(XmlBuilderUtil.node("tru11:TRU1StartDate",
									convertOmxToCrmDateFormat(date)))
							.append(XmlBuilderUtil.node("tru11:ListOfCustomerAssetAttribute"))
							.append(XmlBuilderUtil.node("tru11:CustomerAssetAttribute"))
							.append(XmlBuilderUtil.node("tru11:SerialNumber",
									assetComponentBean.getSerialNo()));
							//assetData.getDeviceType() != ('All IN ONE' OR '2Boxes') ###not Send Node SerialNumber# 20190227 Two Box Case
//							if(Check2Box) {
//								builder.append(XmlBuilderUtil.nodeWithoutNull("tru11:TRU2AssetNumber",
//										assetComponentBean.getAssetNo()+"_"+assetComponentBean.getMaterialCode()));
//							}
//							else {
//								builder.append(XmlBuilderUtil.nodeWithoutNull("tru11:SerialNumber",
//										assetComponentBean.getSerialNo()));
//							}
							//assetData.getDeviceType() != All IN ONE OR 2Boxes ###not Send Node SerialNumber# 20190227 Two Box Case
							builder.append(XmlBuilderUtil.node("tru11:AttributeType", "Model"));
							String tempAttributeValue = null;
							if(null != assetComponentBean.getBrand()) {
								if(assetComponentBean.getBrand().equalsIgnoreCase(assetComponentBean.getModelName())) {
									tempAttributeValue = assetComponentBean.getBrand();
								} else {
									if(null != assetComponentBean.getModelName()) {
										tempAttributeValue = assetComponentBean.getBrand() + "_" + assetComponentBean.getModelName();
									}									
								}
							} else {
								if(null != assetComponentBean.getModelName()) {
									tempAttributeValue = assetComponentBean.getModelName();
								}
								
							}
							
							builder.append(XmlBuilderUtil.node("tru11:AttributeValue",tempAttributeValue));
							
							builder.append(XmlBuilderUtil.node("/tru11:CustomerAssetAttribute"))
							.append(XmlBuilderUtil.node("/tru11:ListOfCustomerAssetAttribute"));
				}
				builder.append(XmlBuilderUtil.node("/tru11:AssetMgmt-Asset"));
			}
		}

		if (assetData.getInstallationAddress() != null) {
			builder.append(XmlBuilderUtil.node("tru11:TRU1AddrCountry",
					assetData.getInstallationAddress().getCountry()))
					.append(XmlBuilderUtil.node("tru11:TRU1AddrPostalCode",
							assetData.getInstallationAddress().getPostalCode()))
					.append(XmlBuilderUtil.node("tru11:TRU1AddrProvince",
							assetData.getInstallationAddress().getProvince()))
					.append(XmlBuilderUtil.node("tru11:TRU1AddrStreetAddress",
							assetData.getInstallationAddress().getStreet()))
					.append(XmlBuilderUtil.node("tru11:TRU1AddrBuilding",
							assetData.getInstallationAddress().getBuilding()))
					.append(XmlBuilderUtil.node("tru11:TRU1AddrFloor",
							assetData.getInstallationAddress().getFloor()))
					.append(XmlBuilderUtil.node("tru11:TRU1AddrHouse",
							assetData.getInstallationAddress().getHouse()))
					.append(XmlBuilderUtil.node("tru11:TRU1AddrKhet",
							assetData.getInstallationAddress().getKhet()))
					.append(XmlBuilderUtil.node("tru11:TRU1AddrKhwang",
							assetData.getInstallationAddress().getKhwang()))
					.append(XmlBuilderUtil.node("tru11:TRU1AddrMoo",
							assetData.getInstallationAddress().getMoo()))
					.append(XmlBuilderUtil.node("tru11:TRU1AddrRoom",
							assetData.getInstallationAddress().getRoom()))
					.append(XmlBuilderUtil.node("tru11:TRU1AddrSoi",
							assetData.getInstallationAddress().getSoi()))
					.append(XmlBuilderUtil.node("tru11:TRU2Latitude",
							String.valueOf(assetData.getInstallationAddress().getLatitude())))
					.append(XmlBuilderUtil.node("tru11:TRU2Longitude",
							String.valueOf(assetData.getInstallationAddress().getLongitude())))
					.append(XmlBuilderUtil.node("tru11:TRU2QAddressIdA",
							assetData.getInstallationAddress().getqAddressId()));
		}
		builder.append(XmlBuilderUtil.node("/tru11:AssetMgmt-Asset"))
				.append(XmlBuilderUtil.node("/tru11:ListOfTru1EaiAssetIo"))
				.append(XmlBuilderUtil.node("/tru1:SyncAsset_Input"));
		return builder.toString();
	}

	public static String convertOmxToCrmDateFormat(String dateStr) {

		if (StringUtil.isEmpty(dateStr)) {
			return null;
		}

		SimpleDateFormat crmDf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat omxDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Date date = null;
		try {
			date = omxDf.parse(dateStr);
			return crmDf.format(date);

		} catch (Exception e) {
			return dateStr;
		}

	}
	
	public static String componentConvertDateToCrmDateFormat(String dateStr) {
		if (StringUtil.isEmpty(dateStr)) {
			return null;
		}

		SimpleDateFormat crmDf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat omxDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+07:00");
		Date date = null;
		try {
			date = omxDf.parse(dateStr);
			return crmDf.format(date);

		} catch (Exception e) {
			return dateStr;
		}

	}
	
	public static String componentConvertDateToReponseOmxFormat(String dateStr) {
		if (StringUtil.isEmpty(dateStr)) {
			return null;
		}

		SimpleDateFormat crmDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+0700");
		SimpleDateFormat omxDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+07:00");
		Date date = null;
		try {
			date = omxDf.parse(dateStr);
			return crmDf.format(date);

		} catch (Exception e) {
			return dateStr;
		}
	}

	public static String convertOmxToCrmDateFormat(Calendar dateStr) {
		return CalendarManager.calendarToString(dateStr, CalendarManager.SIEBEL_DATE_TIME_PATTERN, CalendarManager.LOCALE_EN);		
	}
	
	public static String convertOmxToCrmDateFormatRedZone(String dateStr) {

		if (StringUtil.isEmpty(dateStr)) {
			return null;
		}
		
		Calendar dateStrCalendar = CalendarManager.stringToCalendar(dateStr, "yyyy-MM-dd", CalendarManager.LOCALE_EN);
		return CalendarManager.calendarToString(dateStrCalendar, "dd/MM/yyyy", CalendarManager.LOCALE_EN);
	}
	
	public static String getXmlUpsertPromiseToPay(AssetReqBean asset, SiebelAuthorizationBean siebelAuthenBean) {
		StringBuilder builder = new StringBuilder();
		builder.append(XmlBuilderUtil.node(
				"soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tru2=\"http://siebel.com/TRU2EAIAssetXMWS\" xmlns:tru21=\"http://www.siebel.com/xml/TRU2 EAI Asset XM IO\""))
				.append(getHeader(siebelAuthenBean)).append(XmlBuilderUtil.node("soapenv:Body"))
				/*
				 * <AssetId>1-2ISXIZ</AssetId> <TRU2ActiveFlag>Y</TRU2ActiveFlag>
				 * <TRU2Channel>CRM</TRU2Channel> <TRU2SubmitDate>01/07/2019
				 * 17:29:11</TRU2SubmitDate> <SRNumber>2-11122</Name>
				 */
				.append(XmlBuilderUtil.node("tru2:UpsertAssetXM_Input"))
				.append(XmlBuilderUtil.node("tru21:ListOfTru2EaiAssetXmIo"))
				.append(XmlBuilderUtil.node("tru21:Tru2AssetMgmt-AssetXm Operation=\"?\""))
				.append(XmlBuilderUtil.node("tru21:ParRowId", asset.getAsset().getAssetId()))
				.append(XmlBuilderUtil.node("tru21:Attrib04", "Y"))
				.append(XmlBuilderUtil.node("tru21:Attrib03", asset.getAsset().getAssetXM().getChannel()))
				.append(XmlBuilderUtil.node("tru21:Attrib12",
						convertOmxToCrmDateFormat(asset.getAsset().getAssetXM().getSubmitDate())))
				.append(XmlBuilderUtil.node("tru21:Name", asset.getAsset().getAssetXM().getSrNumber()))
				.append(XmlBuilderUtil.node("tru21:Type", "PromiseToPay"))
				.append(XmlBuilderUtil.node("/tru21:Tru2AssetMgmt-AssetXm"))
				.append(XmlBuilderUtil.node("/tru21:ListOfTru2EaiAssetXmIo"))
				.append(XmlBuilderUtil.node("/tru2:UpsertAssetXM_Input")).append(XmlBuilderUtil.node("/soapenv:Body"))
				.append(XmlBuilderUtil.node("/soapenv:Envelope"));
		return builder.toString();
	}

	public static boolean findBySplitString(String findBy,String strCheck,String split) {
		  String[] items = strCheck.split(split);
	      for (String item : items) {
	    	 if (item.equals(findBy)) {    	   
	    	   return true;
	    	 }        
	      }		 		
		  return false;
	}

	public static String generateSyncAssetRedZone(RedZoneReqBean asset, SiebelAuthorizationBean siebelAuthenBean) {
		String builder = XmlBuilderUtil.node(
				"soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tru1=\"http://siebel.com/TRU1EAIAssetWS\" xmlns:tru11=\"http://www.siebel.com/xml/TRU1%20EAI%20Asset%20IO\"") +
				getHeader(siebelAuthenBean) +
				XmlBuilderUtil.node("soapenv:Body") +
				getXmlAssetRedZone(asset) +
				XmlBuilderUtil.node("/soapenv:Body") +
				XmlBuilderUtil.node("/soapenv:Envelope");
		return builder;
	}

	private static String getXmlAssetRedZone(RedZoneReqBean asset) {
		
		AssetDataBean assetData = asset.getAsset();
		
		StringBuffer requestXML = new StringBuffer();
		requestXML.append(XmlBuilderUtil.node("tru1:SyncAsset_Input"));
		requestXML.append(XmlBuilderUtil.node("tru11:ListOfTru1EaiAssetIo"));
		requestXML.append(XmlBuilderUtil.node("tru11:AssetMgmt-Asset Operation=\"?\""));
		requestXML.append(XmlBuilderUtil.node("tru11:AssetNumber", assetData.getAssetNo()));
		requestXML.append(XmlBuilderUtil.node("tru11:IntegrationId", assetData.getIntegrationId()));
		requestXML.append(XmlBuilderUtil.node("tru11:AssetId", assetData.getAssetId()));
		requestXML.append(XmlBuilderUtil.node("tru11:DataSource", assetData.getDataSource()));
		requestXML.append(XmlBuilderUtil.node("tru11:SerialNumber", assetData.getSerialNo()));
		requestXML.append(XmlBuilderUtil.node("tru11:ProductPartNumber", assetData.getAssetProductBean().getProductPartNo()));
		requestXML.append(XmlBuilderUtil.node("tru11:Status", assetData.getStatus()));
		requestXML.append(XmlBuilderUtil.node("tru11:TRU2KYC", assetData.getKycData()));
		requestXML.append(XmlBuilderUtil.node("tru11:TRU2FaceRegDealerCode", assetData.getDealerCode()));
		requestXML.append(XmlBuilderUtil.node("tru11:TRU2IssueDate", convertOmxToCrmDateFormatRedZone(assetData.getIssueDate())));
		requestXML.append(XmlBuilderUtil.node("tru11:TRU2ExpireDate", convertOmxToCrmDateFormatRedZone(assetData.getExpireDate())));
		requestXML.append(XmlBuilderUtil.node("tru11:TRU2ChannelOrder", assetData.getChannelOrder()));
		requestXML.append(XmlBuilderUtil.node("tru11:TRU2TransactionDocId", assetData.getTransDocID()));
		requestXML.append(XmlBuilderUtil.node("tru11:TRU2VerifyMethod", assetData.getVerifyMethod()));
		requestXML.append(XmlBuilderUtil.node("tru11:TRU2VerifyResult", assetData.getVerifyResult()));
		requestXML.append(XmlBuilderUtil.node("tru11:TRU2CardReader", assetData.getIsCardReader()));			
				
		/* For U18 Asset parent*/
		if (asset.getParentCustomer() != null) {
					
			requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentFstname", asset.getParentCustomer().getFirstName()));
			requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentLastname", asset.getParentCustomer().getLastName()));
			requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentIdType", asset.getParentCustomer().getIdType()));
			requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentIdNumber", asset.getParentCustomer().getIdNumber()));
			requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentPhone", asset.getParentCustomer().getContactPhone()));			

			if (asset.getParentCustomer().getAddress() != null ) {
				
				requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentHouse", asset.getParentCustomer().getAddress().getHouseNo()));
				requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentFloor", asset.getParentCustomer().getAddress().getFloor()));
				requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentRoom", asset.getParentCustomer().getAddress().getRoomNo()));
				requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentBuilding", asset.getParentCustomer().getAddress().getBuilding()));
				requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentMoo", asset.getParentCustomer().getAddress().getMoo()));
				requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentSoi", asset.getParentCustomer().getAddress().getSoi()));
				requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentStreetAddress", asset.getParentCustomer().getAddress().getStreet()));
				requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentKhwang", asset.getParentCustomer().getAddress().getSubdistrict()));
				requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentKhet", asset.getParentCustomer().getAddress().getDistrict()));
				requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentProvince", asset.getParentCustomer().getAddress().getProvince()));
				requestXML.append(XmlBuilderUtil.node("tru11:TRU2ParentPostalCode", asset.getParentCustomer().getAddress().getPostalCode()));
			}
		}
		/* End */
		
		requestXML.append(XmlBuilderUtil.node("tru1:AccountIntegrationId", assetData.getGoldenId()));		
		requestXML.append(XmlBuilderUtil.node("/tru11:AssetMgmt-Asset"));
		requestXML.append(XmlBuilderUtil.node("/tru11:ListOfTru1EaiAssetIo"));
		requestXML.append(XmlBuilderUtil.node("/tru1:SyncAsset_Input"));
		
		return requestXML.toString();
	}
	
	public static String generateUpsertAssetRelationship(AssetRelationShipReqBean assetRelationShipReqBean, SiebelAuthorizationBean siebelAuthenBean) throws Exception {
		String builder = XmlBuilderUtil.node(
				"soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tru2=\"http://siebel.com/TRU2EAIUpsertAssetRelationshipWS\"") +
				getHeader(siebelAuthenBean) +
				XmlBuilderUtil.node("soapenv:Body") +
				getXmlUpsertAssetRelationship(assetRelationShipReqBean) +
				XmlBuilderUtil.node("/soapenv:Body") +
				XmlBuilderUtil.node("/soapenv:Envelope");
		return builder;
	}

	private static String getXmlUpsertAssetRelationship(AssetRelationShipReqBean assetRelationShipReqBean) throws Exception {
		MappingCassandra mappingCassandra = new MappingCassandra();
		
		StringBuilder builder = new StringBuilder();
		builder.append(XmlBuilderUtil.node("tru2:UpsertAssetRelationship_Input"));
		builder.append(XmlBuilderUtil.node("tru2:RelationshipTypeCode", assetRelationShipReqBean.getRelationshipType()));
		builder.append(XmlBuilderUtil.node("tru2:Operation", assetRelationShipReqBean.getOperation()));
		builder.append(XmlBuilderUtil.node("tru2:ParentFieldName", mappingCassandra.getMappingCassandra(assetRelationShipReqBean.getParentFieldName(), "CVG_RELATIONSHIP_FIELDNAME")));
		builder.append(XmlBuilderUtil.node("tru2:ParentFieldValue", assetRelationShipReqBean.getParentFieldValue()));
		builder.append(XmlBuilderUtil.node("tru2:FieldName", mappingCassandra.getMappingCassandra(assetRelationShipReqBean.getFieldName(), "CVG_RELATIONSHIP_FIELDNAME")));
		builder.append(XmlBuilderUtil.node("tru2:FieldValue", assetRelationShipReqBean.getFieldValue()));
		builder.append(XmlBuilderUtil.node("/tru2:UpsertAssetRelationship_Input"));
		
		return builder.toString();
	}
	
	public static String generateChangeDummyMSISDNPrepay(SiebelAuthorizationBean siebelAuthenBean
		, DummyMsisdnReqBean dummyMsisdnReqBean, String assetRowID, String GoldenId) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(XmlBuilderUtil.node("soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tru1=\"http://siebel.com/TRU1EAIAssetWS\" xmlns:tru11=\"http://www.siebel.com/xml/TRU1%20EAI%20Asset%20IO\""))
				.append(getHeader(siebelAuthenBean))
				.append(XmlBuilderUtil.node("soapenv:Body"))
				.append(XmlBuilderUtil.node("tru1:SyncAsset_Input"))
					.append(XmlBuilderUtil.node("tru11:ListOfTru1EaiAssetIo"))
						.append(XmlBuilderUtil.node("tru11:AssetMgmt-Asset Operation=\"\""))
							.append(XmlBuilderUtil.node("tru11:AssetId", assetRowID))
							.append(XmlBuilderUtil.node("tru11:SerialNumber", dummyMsisdnReqBean.getServiceID()))
							.append(XmlBuilderUtil.node("tru11:Status", "Active"))
							.append(XmlBuilderUtil.node("tru11:TRU1StartDate", SiebelTransform.convertOmxToCrmDateFormat(dummyMsisdnReqBean.getEffectiveDate())))
							.append(XmlBuilderUtil.node("tru11:IntegrationId", "A" + dummyMsisdnReqBean.getServiceID()))
							.append(XmlBuilderUtil.node("tru1:AccountIntegrationId", GoldenId))
						.append(XmlBuilderUtil.node("/tru11:AssetMgmt-Asset"))
					.append(XmlBuilderUtil.node("/tru11:ListOfTru1EaiAssetIo"))
				.append(XmlBuilderUtil.node("/tru1:SyncAsset_Input"))
				.append(XmlBuilderUtil.node("/soapenv:Body"))
				.append(XmlBuilderUtil.node("/soapenv:Envelope"));
		
		return builder.toString();
	}
	
	public static String generateNewDummyMSISDNPrepay(SiebelAuthorizationBean siebelAuthenBean
			, DummyMsisdnReqBean dummyMsisdnReqBean, String GoldenId) {
			
			StringBuilder builder = new StringBuilder();
			builder.append(XmlBuilderUtil.node("soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tru1=\"http://siebel.com/TRU1EAIAssetWS\" xmlns:tru11=\"http://www.siebel.com/xml/TRU1%20EAI%20Asset%20IO\""))
					.append(getHeader(siebelAuthenBean))
					.append(XmlBuilderUtil.node("soapenv:Body"))
					.append(XmlBuilderUtil.node("tru1:SyncAsset_Input"))
						.append(XmlBuilderUtil.node("tru11:ListOfTru1EaiAssetIo"))
							.append(XmlBuilderUtil.node("tru11:AssetMgmt-Asset Operation=\"\""))
								.append(XmlBuilderUtil.node("tru11:SerialNumber", dummyMsisdnReqBean.getServiceID()))
								.append(XmlBuilderUtil.node("tru11:ProductPartNumber", "Mobile Prepay"))
								.append(XmlBuilderUtil.node("tru11:Status", "Active"))
								.append(XmlBuilderUtil.node("tru11:TRU1StartDate", SiebelTransform.convertOmxToCrmDateFormat(dummyMsisdnReqBean.getEffectiveDate())))
								.append(XmlBuilderUtil.node("tru11:IntegrationId", "A" +dummyMsisdnReqBean.getServiceID()))
								.append(XmlBuilderUtil.node("tru1:AccountIntegrationId", GoldenId))
							.append(XmlBuilderUtil.node("/tru11:AssetMgmt-Asset"))
						.append(XmlBuilderUtil.node("/tru11:ListOfTru1EaiAssetIo"))
					.append(XmlBuilderUtil.node("/tru1:SyncAsset_Input"))
					.append(XmlBuilderUtil.node("/soapenv:Body"))
					.append(XmlBuilderUtil.node("/soapenv:Envelope"));
		return builder.toString();
	}
	
	public static String getXMLUpsertPromiseToPay(PromiseToPayReqBean promiseToPayReqBean,
			SiebelAuthorizationBean siebelAuthenBean) {
		StringBuilder builder = new StringBuilder();
		builder.append(XmlBuilderUtil.node("soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tru2=\"http://siebel.com/TRU2EAIAssetXMWS\" xmlns:tru21=\"http://www.siebel.com/xml/TRU2%20EAI%20Asset%20XM%20IO\""))
				.append(getHeader(siebelAuthenBean))
				.append(XmlBuilderUtil.node("soapenv:Body"))
				.append(XmlBuilderUtil.node("tru2:UpsertAssetXM_Input"))
				.append(XmlBuilderUtil.node("tru21:ListOfTru2EaiAssetXmIo"))
				.append(XmlBuilderUtil.node("tru21:Tru2AssetMgmt-AssetXm Operation=\"?\""))
				.append(XmlBuilderUtil.node("tru21:ParRowId", promiseToPayReqBean.getAsset().getAssetId()))
				.append(XmlBuilderUtil.node("tru21:Attrib04", "Y"))
				.append(XmlBuilderUtil.node("tru21:Attrib03", promiseToPayReqBean.getAsset().getChannel()))
				.append(XmlBuilderUtil.node("tru21:Attrib12",
						convertOmxToCrmDateFormat(promiseToPayReqBean.getAsset().getSubmitDate())))
				.append(XmlBuilderUtil.node("tru21:Name", promiseToPayReqBean.getAsset().getSrNumber()))
				.append(XmlBuilderUtil.node("tru21:Type", "PromiseToPay"))
				.append(XmlBuilderUtil.node("/tru21:Tru2AssetMgmt-AssetXm"))
				.append(XmlBuilderUtil.node("/tru21:ListOfTru2EaiAssetXmIo"))
				.append(XmlBuilderUtil.node("/tru2:UpsertAssetXM_Input"))
				.append(XmlBuilderUtil.node("/soapenv:Body"))
				.append(XmlBuilderUtil.node("/soapenv:Envelope"));
		return builder.toString();
	}
	
	public static String generateLoginReq(String user,String password) {
		StringBuilder loginXml = new StringBuilder()
				.append(XmlBuilderUtil.node("soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.ident.uams.amdocs\""))
				.append(XmlBuilderUtil.node("soapenv:Body"))
				.append(XmlBuilderUtil.node("ws:login"))
				.append(XmlBuilderUtil.node("ws:userId", user ))
				.append(XmlBuilderUtil.node("ws:password",password ))
				.append(XmlBuilderUtil.node("ws:appId xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/"))
				.append(XmlBuilderUtil.node("ws:env xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/"))
				.append(XmlBuilderUtil.node("/ws:login"))
				.append(XmlBuilderUtil.node("/soapenv:Body"))
				.append(XmlBuilderUtil.node("/soapenv:Envelope"));
		return loginXml.toString();
    }
	
	public static String generateSearchPrepaidSubscriberByResourceReq(String token, String serviceId) {
		StringBuilder loginXml = new StringBuilder()
				.append(XmlBuilderUtil.node("soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.api.interfaces.sessions.csm3g.amdocs\" xmlns:dat=\"http://datatypes.csm3g.amdocs\""))
				.append(getCCBSHeader(token))
				.append(XmlBuilderUtil.node("soapenv:Body"))
				.append(XmlBuilderUtil.node("ws:l9SearchPrepaidSubscriberByResource"))
				.append(XmlBuilderUtil.node("ws:resourceValues"))
				.append(XmlBuilderUtil.node("dat:primResourceVal", serviceId))
				.append(XmlBuilderUtil.node("dat:primResourceTp", "C"))
				.append(XmlBuilderUtil.node("/ws:resourceValues"))
				.append(XmlBuilderUtil.node("/ws:l9SearchPrepaidSubscriberByResource"))
				.append(XmlBuilderUtil.node("/soapenv:Body"))
				.append(XmlBuilderUtil.node("/soapenv:Envelope"));
		return loginXml.toString();
    }
	
	public static String generatel9GetPrepaidSubscriberReq(String token, String subNo) {
		StringBuilder loginXml = new StringBuilder()
				.append(XmlBuilderUtil.node("soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.api.interfaces.sessions.csm3g.amdocs\" xmlns:dat=\"http://datatypes.csm3g.amdocs\""))
				.append(getCCBSHeader(token))
				.append(XmlBuilderUtil.node("soapenv:Body"))
				.append(XmlBuilderUtil.node("ws:l9GetPrepaidSubscriber"))
				.append(XmlBuilderUtil.node("ws:hbpSubscriberNo"))
				.append(XmlBuilderUtil.node("dat:hbpSubscriberNo", subNo))
				.append(XmlBuilderUtil.node("/ws:hbpSubscriberNo"))
				.append(XmlBuilderUtil.node("/ws:l9GetPrepaidSubscriber"))
				.append(XmlBuilderUtil.node("/soapenv:Body"))
				.append(XmlBuilderUtil.node("/soapenv:Envelope"));
		return loginXml.toString();
    }
	
	private static String getCCBSHeader(String token) {
		StringBuilder loginXml = new StringBuilder()
				.append(XmlBuilderUtil.node("soapenv:Header"))
				.append(XmlBuilderUtil.node("wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\""))
				.append(XmlBuilderUtil.node("asm:AsmUserPrincipal xmlns:asm=\"http://asm.amdocs.com\""))
				.append(token)
				.append(XmlBuilderUtil.node("/asm:AsmUserPrincipal"))
				.append(XmlBuilderUtil.node("/wsse:Security"))
				.append(XmlBuilderUtil.node("/soapenv:Header"));
		return loginXml.toString();
	}
	
	public static String generateLogOutReq(String token) {
		String logoutXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.ident.uams.amdocs\">"
				+ "<soapenv:Header/>" + "<soapenv:Body>" + "<ws:logout>" + "<ws:ticket>" + token
				+ "</ws:ticket>" + "</ws:logout>" + "</soapenv:Body>" + "</soapenv:Envelope>";
		return logoutXml.toString();
    }
	
	public static String generateAddRemoveComponentAsset(String action, AssetCompBean assetComp) {
		StringBuilder loginXml = new StringBuilder()
				.append(XmlBuilderUtil.node("soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tru1=\"http://siebel.com/TRU1EAIAssetWS\" xmlns:tru11=\"http://www.siebel.com/xml/TRU1%20EAI%20Asset%20IO\""))
				.append(getAddRemoveComponentAssetHeader())
				.append(XmlBuilderUtil.node("soapenv:Body"))
				.append(getAddRemoveComponentAssetBody(action, assetComp))
				.append(XmlBuilderUtil.node("/soapenv:Body"))
				.append(XmlBuilderUtil.node("/soapenv:Envelope"));
		return loginXml.toString();
    }
	
	private static String getAddRemoveComponentAssetHeader() {
		StringBuilder loginXml = new StringBuilder()
				.append(XmlBuilderUtil.node("soapenv:Header"))
				.append(XmlBuilderUtil.node("UsernameToken xmlns=\"http://siebel.com/webservices\""))
				.append(CassandraConfig.getVal("siebel.header.user"))
				.append(XmlBuilderUtil.node("/UsernameToken"))
				.append(XmlBuilderUtil.node("PasswordText xmlns=\"http://siebel.com/webservices\""))
				.append(CassandraConfig.getVal("siebel.header.pass"))
				.append(XmlBuilderUtil.node("/PasswordText"))
				.append("<SessionType xmlns=\"http://siebel.com/webservices\">None</SessionType>")
				.append(XmlBuilderUtil.node("/soapenv:Header"));
		return loginXml.toString();
	}
	
	private static String getAddRemoveComponentAssetBody(String action, AssetCompBean assetComp) {
		StringBuilder loginXml = new StringBuilder()
				.append(XmlBuilderUtil.node("tru1:SyncAsset_Input"))
				.append(XmlBuilderUtil.node("tru11:ListOfTru1EaiAssetIo"));
				if(action.equalsIgnoreCase("add")) {
					loginXml.append(generateXmlAddComponentAsset(assetComp));
				} else {
					loginXml.append(generateXmlRemoveComponentAsset(assetComp));
				}
				loginXml.append(XmlBuilderUtil.node("/tru11:ListOfTru1EaiAssetIo"))
				.append(XmlBuilderUtil.node("/tru1:SyncAsset_Input"));
		return loginXml.toString();
	}
	
	private static String generateXmlAddComponentAsset(AssetCompBean assetComp) {
		StringBuilder loginXml = new StringBuilder()
				.append(XmlBuilderUtil.node("tru11:AssetMgmt-Asset Operation=\"?\""))
				.append(XmlBuilderUtil.node("tru11:AssetId", assetComp.getRootAssetRowID()))
				.append(XmlBuilderUtil.node("tru11:AssetMgmt-Asset Operation=\"\""))
				.append(XmlBuilderUtil.node("tru11:AssetNumber", assetComp.getAssetNo()));
				if(assetComp.getSerialNo() != null) {
					loginXml.append(XmlBuilderUtil.node("tru11:SerialNumber", assetComp.getSerialNo()));
				}
				if(assetComp.getSerialNo() != null) {
					loginXml.append(XmlBuilderUtil.node("tru11:SerialNumber", assetComp.getSerialNo()));
				}
				loginXml.append(XmlBuilderUtil.node("tru11:ProductPartNumber", assetComp.getPartNum()))
				.append(XmlBuilderUtil.node("tru11:Status", "Active"))
				.append(XmlBuilderUtil.node("tru11:TRU1StartDate", componentConvertDateToCrmDateFormat(assetComp.getEffectiveDateStr())))
				.append(XmlBuilderUtil.node("/tru11:AssetMgmt-Asset"))
				.append(XmlBuilderUtil.node("/tru11:AssetMgmt-Asset"));
		return loginXml.toString();
	}
	
	private static String generateXmlRemoveComponentAsset(AssetCompBean assetComp) {
		SimpleDateFormat d = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.US);
		String date = d.format(new Date());
		StringBuilder loginXml = new StringBuilder()
				.append(XmlBuilderUtil.node("tru11:AssetMgmt-Asset Operation=\"?\""))
				.append(XmlBuilderUtil.node("tru11:AssetId", assetComp.getRootAssetRowID()))
				.append(XmlBuilderUtil.node("tru11:AssetMgmt-Asset Operation=\"\""))
				.append(XmlBuilderUtil.node("tru11:AssetId", assetComp.getAssetRowID()))
				.append(XmlBuilderUtil.node("tru11:ProductPartNumber", assetComp.getPartNum()))
				.append(XmlBuilderUtil.node("tru11:IntegrationId/"))
				.append(XmlBuilderUtil.node("tru11:Status", "Inactive"))
				.append(XmlBuilderUtil.node("tru11:TRU1EndDate", date))
				.append(XmlBuilderUtil.node("/tru11:AssetMgmt-Asset"))
				.append(XmlBuilderUtil.node("/tru11:AssetMgmt-Asset"));
		return loginXml.toString();
	}
}