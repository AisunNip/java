package th.co.truecorp.crmdev.asset.validate;

import java.util.Arrays;

import th.co.truecorp.crmdev.asset.bean.AssetDataBean;
import th.co.truecorp.crmdev.asset.bean.AssetFindByBean;
import th.co.truecorp.crmdev.asset.bean.AssetRelationShipReqBean;
import th.co.truecorp.crmdev.asset.bean.AssetReqBean;
import th.co.truecorp.crmdev.asset.bean.CapMaxReqBean;
import th.co.truecorp.crmdev.asset.bean.Constants;
import th.co.truecorp.crmdev.asset.bean.CustomerAccountBean;
import th.co.truecorp.crmdev.asset.bean.DeleteComponentReqBean;
import th.co.truecorp.crmdev.asset.bean.DummyMsisdnReqBean;
import th.co.truecorp.crmdev.asset.bean.HuntingFLPReqBean;
import th.co.truecorp.crmdev.asset.bean.LivingReqBean;
import th.co.truecorp.crmdev.asset.bean.ParentCustomerBean;
import th.co.truecorp.crmdev.asset.bean.PromiseToPayReqBean;
import th.co.truecorp.crmdev.asset.bean.RedZoneReqBean;
import th.co.truecorp.crmdev.asset.bean.SubscriberNameBean;
import th.co.truecorp.crmdev.asset.exception.ValidationException;
import th.co.truecorp.crmdev.asset.util.AssetErrorCode;
import th.co.truecorp.crmdev.asset.util.StringUtil;
import th.co.truecorp.crmdev.util.common.CalendarManager;
import th.co.truecorp.crmdev.util.common.Validator;
import th.co.truecorp.crmdev.util.common.bean.ErrorCodeResp;
import th.co.truecorp.crmdev.util.db.config.CassandraConfig;

public class AssetValidation {

	private AssetErrorCode assetErrorCode;
	private Validator validator;
	
	public AssetValidation() {
		this.assetErrorCode = new AssetErrorCode();
		this.validator = new Validator();
	}

	public void throwRequiredFieldException(String fieldName) throws ValidationException {
		ErrorCodeResp errorCodeResp = this.assetErrorCode.generateRequiredField(fieldName);
		throw new ValidationException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
	}
	
	public void throwParamInvalidException(String fieldName) throws ValidationException {
		ErrorCodeResp errorCodeResp = this.assetErrorCode.generateParamInvalid(fieldName);
		throw new ValidationException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
	}
	
	public void validateGetAssetRootList(AssetFindByBean assetFindByBean) throws ValidationException {
		
		if (assetFindByBean == null) {
			this.throwRequiredFieldException("AssetFindByBean");
		}
		
		if (assetFindByBean.getPageNo() == null) {
			this.throwRequiredFieldException("AssetFindByBean.pageNo");
		}
		
		if (assetFindByBean.getPageSize() == null) {
			this.throwRequiredFieldException("AssetFindByBean.pageSize");
		}
		
		if (assetFindByBean.getPageNo() < 1) {
			this.throwParamInvalidException("AssetFindByBean.pageNo");
		}
		
		if (assetFindByBean.getPageSize() < 1) {
			this.throwParamInvalidException("AssetFindByBean.pageSize");
		}
		
		if (assetFindByBean.getPageSize() > 200) {
			this.throwParamInvalidException("AssetFindByBean.pageSize");
		}
		
		if (!this.validator.hasStringValue(assetFindByBean.getAssetRowID())
			&& !this.validator.hasStringValue(assetFindByBean.getAssetNo())
			&& !this.validator.hasStringValue(assetFindByBean.getIdNo())
			&& !this.validator.hasStringValue(assetFindByBean.getCustomerRowID())
			&& !this.validator.hasStringValue(assetFindByBean.getServiceID())
			&& !this.validator.hasStringValue(assetFindByBean.getRelatedServiceID())
			&& !this.validator.hasStringValue(assetFindByBean.getQrunAddressID())
			&& !this.validator.hasStringValue(assetFindByBean.getBan())
			&& !this.validator.hasStringValue(assetFindByBean.getCvgBan())
			&& !this.validator.hasStringValue(assetFindByBean.getTaxID())
			&& !this.validator.hasStringValue(assetFindByBean.getBranchNumber())) {
			
			this.throwRequiredFieldException("AssetRowID, AssetNo, IdNo, CustomerRowID, ServiceID"
				+ ", QrunAddressID, RelatedServiceID, Ban, CvgBan, TaxID or BranchNumber");
		}
	}
	
	public void validateGetLatestAssetRoot(AssetFindByBean assetFindByBean) throws ValidationException {
		if (assetFindByBean == null) {
			this.throwRequiredFieldException("AssetFindByBean");
		}
		
		if (!this.validator.hasStringValue(assetFindByBean.getAssetRowID())
			&& !this.validator.hasStringValue(assetFindByBean.getServiceID())
			&& !this.validator.hasStringValue(assetFindByBean.getRelatedServiceID())
			&& !this.validator.hasStringValue(assetFindByBean.getQrunAddressID())) {
			this.throwRequiredFieldException("AssetRowID, ServiceID, RelatedServiceID or QrunAddressID");
		}
	}
	
	public void validateGetAssetComponentList(AssetFindByBean assetFindByBean) throws ValidationException {
		if (assetFindByBean == null) {
			this.throwRequiredFieldException("AssetFindByBean");
		}
		
		if (!this.validator.hasStringValue(assetFindByBean.getAssetRowID())
			&& !this.validator.hasStringValue(assetFindByBean.getAssetNo())
			&& !this.validator.hasStringValue(assetFindByBean.getServiceID())) {
			this.throwRequiredFieldException("AssetRowID, AssetNo or ServiceID");
		}
		
		if (this.validator.hasStringValue(assetFindByBean.getMasterFlag())) {
			if (!"Y".equalsIgnoreCase(assetFindByBean.getMasterFlag()) && !"N".equalsIgnoreCase(assetFindByBean.getMasterFlag())) {
				this.throwParamInvalidException("MasterFlag");
			}
		}
	}
	
	public void validateGetHuntingFLP(HuntingFLPReqBean huntingFLPReqBean) throws ValidationException {
		if (huntingFLPReqBean == null) {
			this.throwRequiredFieldException("HuntingFLPReqBean");
		}
		
		if (!this.validator.hasStringValue(huntingFLPReqBean.getPilotServiceID())
			&& !this.validator.hasStringValue(huntingFLPReqBean.getPilotAssetRowID())) {
			this.throwRequiredFieldException("PilotServiceID or PilotAssetRowID");
		}
	}

	public void validateCommonAsset(AssetReqBean asset) throws ValidationException {
		if (asset == null) this.throwRequiredFieldException("AssetReqBean");
		
		if (asset.getAsset() == null) this.throwRequiredFieldException("AssetDataBean");

		if (this.validator.hasStringValue(asset.getAsset().getAssetId())) {
			return;
		}
		
		if (!this.validator.hasStringValue(asset.getAsset().getServiceId())) {
			this.throwRequiredFieldException("asset.assetId or asset.serviceId");
		}
	}

	public void validateUpdateAsset(AssetReqBean asset) throws ValidationException {
		// Common field
		validateCommonAsset(asset);

		// Specify by function
		if (asset.getAsset().getSubscriberName() != null) {
			SubscriberNameBean subName = asset.getAsset().getSubscriberName();
			if (StringUtil.isEmpty(subName.getTitle())
				&& StringUtil.isEmpty(subName.getFirstName())
				&& StringUtil.isEmpty(subName.getLastName())
				&& StringUtil.isEmpty(subName.getOrganizationName())) {
				this.throwRequiredFieldException("SubscriberNameBean.title firstName lastName or organizationName");
			}
		}
		
		/* U18 */
		ParentCustomerBean parentCustomer = asset.getAsset().getParentCustomer();
		if (parentCustomer != null) {
			
			// Mobile
			if(!this.validator.hasStringValue(parentCustomer.getContactPhoneType())){					
				if (this.validator.hasStringValue(parentCustomer.getContactPhone())) 
					this.throwRequiredFieldException("parentCustomer.contactPhone");
			}

			if(!StringUtil.isEmpty(parentCustomer.getContactPhone())){					
				if (StringUtil.isEmpty(parentCustomer.getContactPhoneType())) 
					this.throwRequiredFieldException("parentCustomer.contactPhoneType");
			}
			
			if(!StringUtil.isEmpty(parentCustomer.getContactPhoneType())){
				
				//contactPhoneType : Auto Fax|Manual Fax|Mobile|Home|Work|Fax
				String contactPhoneType = CassandraConfig.getVal("crm.contactPhoneType");			
				String[] contactPhoneArray = contactPhoneType.split("\\|");
				boolean result = Arrays.stream(contactPhoneArray).anyMatch(parentCustomer.getContactPhoneType()::equals);
				if(!result)
					this.throwRequiredFieldException("contactPhoneType invalid value");
				
				//contactPhoneType
				/*
				crm.contactPhoneType.Auto Fax	: (\+66|66|0)\d{8}
				crm.contactPhoneType.Manual Fax	: (\+66|66|0)\d{8}
				crm.contactPhoneType.Mobile 	: (\+66|66|0)\d{9}
				crm.contactPhoneType.Home 		: (\+66|66|0)\d{8}
				crm.contactPhoneType.Work 		: (\+66|66|0)\d{8}
				crm.contactPhoneType.Fax		: (\+66|66|0)\d{8}
				*/
				String contactPhoneRegularExpression = CassandraConfig.getVal("crm.contactPhoneType."+parentCustomer.getContactPhoneType());
				if(!validator.validator(parentCustomer.getContactPhone(), contactPhoneRegularExpression))
					this.throwRequiredFieldException("format contactPhone : " + contactPhoneRegularExpression);			
			}
		}
	}

//	public void validateUpsertAssetRedZone(RedZoneReqBean redZoneReqBean) throws ValidationException {
//
//		AssetDataBean assetDataBean = redZoneReqBean.getAsset();
//		CustomerAccountBean customerAccountBean = redZoneReqBean.getCustomerAccount();
//		
//		if (assetDataBean == null) {
//			this.throwRequiredFieldException("asset");
//		}
//
////		if(!this.validator.hasStringValue(assetDataBean.getSubscriberNo())) {
////			this.throwRequiredFieldException("SubscriberNo");
////		}
//
//		if(!this.validator.hasStringValue(assetDataBean.getServiceId())) {
//			this.throwRequiredFieldException("asset.serviceId");
//		}
//
//		if(!this.validator.hasStringValue(assetDataBean.getProductType())) {
//			this.throwRequiredFieldException("asset.productType");
//		}else{
//			String tempProductType = CassandraConfig.getVal("crm.crmiasset.upsertassetredzone.productType");
//			if(tempProductType.indexOf(assetDataBean.getProductType()) < 0){
//				this.throwRequiredFieldException("asset.productType : Prepay|Postpay");
//			}
////			else{
////				if("Postpay".equals(assetDataBean.getProductType())){
////					if(!this.validator.hasStringValue(assetDataBean.getSubscriberNo())) {
////						this.throwRequiredFieldException("SubscriberNo");
////					}
////				}
////			}
//		}
//
//		if(this.validator.hasStringValue(assetDataBean.getVerifyMethod())) {
//			String tempVerifyMethod = CassandraConfig.getVal("crm.crmiasset.upsertassetredzone.verifymethod");
//			if(tempVerifyMethod.indexOf(assetDataBean.getVerifyMethod()) < 0){
//				this.throwRequiredFieldException("asset.verifyMethod : "+tempVerifyMethod);
//			}
//		}
//
//		if(this.validator.hasStringValue(assetDataBean.getVerifyResult())) {
//			String tempVerifyResult = CassandraConfig.getVal("crm.crmiasset.upsertassetredzone.verifyresult");
//			if(tempVerifyResult.indexOf(assetDataBean.getVerifyResult()) < 0){
//				this.throwRequiredFieldException("asset.verifyResult : 1|2");
//			}
//		}
//
//		if(this.validator.hasStringValue(assetDataBean.getIsCardReader())) {
//			String tempIsCardReader = CassandraConfig.getVal("crm.crmiasset.upsertassetredzone.iscardreader");
//			if(tempIsCardReader.indexOf(assetDataBean.getIsCardReader()) < 0){
//				this.throwRequiredFieldException("asset.isCardReader : Y|N");
//			}
//		}
//		
//		if(assetDataBean.getIssueDate() == null) {
//			this.throwRequiredFieldException("asset.issueDate(format : yyyy-MM-dd'T'HH:mm:ss.SSSZ)");
//		}
//
//		if(assetDataBean.getExpireDate() == null) {
//			this.throwRequiredFieldException("asset.expireDate(format : yyyy-MM-dd'T'HH:mm:ss.SSSZ)");
//		}
//		
//		if(customerAccountBean != null) {
//			if(!this.validator.hasStringValue(customerAccountBean.getIdType())) {
//				this.throwRequiredFieldException("customerAccount.idType");
//			}
//			
//			if(!this.validator.hasStringValue(customerAccountBean.getIdNumber())) {
//				this.throwRequiredFieldException("customerAccount.idNumber");
//			}
//			
//			if(!this.validator.hasStringValue(customerAccountBean.getTitle())) {
//				this.throwRequiredFieldException("customerAccount.title");
//			}
//			
//			if(!this.validator.hasStringValue(customerAccountBean.getFirstName())) {
//				this.throwRequiredFieldException("customerAccount.firstName");
//			}
//			
//			if(!this.validator.hasStringValue(customerAccountBean.getLastName())) {
//				this.throwRequiredFieldException("customerAccount.lastName");
//			}
//			
//			if(!this.validator.hasStringValue(customerAccountBean.getGender())) {
//				this.throwRequiredFieldException("customerAccount.gender");
//			}
//			
//			if(!this.validator.hasStringValue(customerAccountBean.getBirthDate())) {
//				this.throwRequiredFieldException("customerAccount.birthDate");
//				
//				if(!isValidFormat(customerAccountBean.getBirthDate())){
//					this.throwRequiredFieldException("customerAccount.birthDate :yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//				}
//			}
//			
//			if("Postpay".equals(assetDataBean.getProductType())) {
//				if(!this.validator.hasStringValue(customerAccountBean.getCcbsCustId())) {
//					this.throwRequiredFieldException("customerAccount.ccbsCustId");
//				}
//			}
//			
//			if(!this.validator.hasStringValue(assetDataBean.getSubscriberNo())) {
//				this.throwRequiredFieldException("asset.subscriberNo");
//			}
//		}
//	}
	
	public void validateUpsertAssetRedZone(RedZoneReqBean redZoneReqBean) throws ValidationException {

		AssetDataBean assetDataBean = redZoneReqBean.getAsset();
		CustomerAccountBean customerAccountBean = redZoneReqBean.getCustomerAccount();
		ParentCustomerBean parentCustomer = redZoneReqBean.getParentCustomer();
		
		if (assetDataBean == null) {
			this.throwRequiredFieldException("asset");
		}		
		
		if(!this.validator.hasStringValue(assetDataBean.getServiceId()) 
				&& !this.validator.hasStringValue(assetDataBean.getDummyMsisdn())) {
			this.throwRequiredFieldException("asset.serviceId or asset.dummyMsisdn");
		}

		if(assetDataBean.isDummyMsisdnFlag() && !this.validator.hasStringValue(assetDataBean.getDummyMsisdn())) {
			this.throwRequiredFieldException("asset.dummyMsisdn");
		}
		
		if(!this.validator.hasStringValue(assetDataBean.getProductType())) {
			this.throwRequiredFieldException("asset.productType");
		}else{
			String tempProductType = CassandraConfig.getVal("crm.crmiasset.upsertassetredzone.productType");
			if(tempProductType.indexOf(assetDataBean.getProductType()) < 0){
				this.throwRequiredFieldException("asset.productType : Prepay|Postpay");
			}
		}

		if(this.validator.hasStringValue(assetDataBean.getVerifyMethod())) {
			String tempVerifyMethod = CassandraConfig.getVal("crm.crmiasset.upsertassetredzone.verifymethod");
			if(tempVerifyMethod.indexOf(assetDataBean.getVerifyMethod()) < 0){
				this.throwRequiredFieldException("asset.verifyMethod : "+tempVerifyMethod);
			}
		}

		if(this.validator.hasStringValue(assetDataBean.getVerifyResult())) {
			String tempVerifyResult = CassandraConfig.getVal("crm.crmiasset.upsertassetredzone.verifyresult");
			if(tempVerifyResult.indexOf(assetDataBean.getVerifyResult()) < 0){
				this.throwRequiredFieldException("asset.verifyResult : 1|2");
			}
		}

		if(this.validator.hasStringValue(assetDataBean.getIsCardReader())) {
			String tempIsCardReader = CassandraConfig.getVal("crm.crmiasset.upsertassetredzone.iscardreader");
			if(tempIsCardReader.indexOf(assetDataBean.getIsCardReader()) < 0){
				this.throwRequiredFieldException("asset.isCardReader : Y|N");
			}
		}
		
		if(assetDataBean.getIssueDate() != null) {
			if(!isValidDate(assetDataBean.getIssueDate())) {
				this.throwRequiredFieldException("asset.issueDate(format : yyyy-MM-dd)");
			}
			
		}

		if(assetDataBean.getExpireDate() != null) {
			if(!isValidDate(assetDataBean.getExpireDate())) {
				this.throwRequiredFieldException("asset.expireDate(format : yyyy-MM-dd)");
			}
		}
		
		if(customerAccountBean != null) {
			if(!this.validator.hasStringValue(customerAccountBean.getIdType())) {
				this.throwRequiredFieldException("customerAccount.idType");
			}
			
			if(!this.validator.hasStringValue(customerAccountBean.getIdNumber())) {
				this.throwRequiredFieldException("customerAccount.idNumber");
			}
			
			if(!this.validator.hasStringValue(customerAccountBean.getTitle())) {
				this.throwRequiredFieldException("customerAccount.title");
			}
			
			if(!this.validator.hasStringValue(customerAccountBean.getFirstName())) {
				this.throwRequiredFieldException("customerAccount.firstName");
			}
			
			if(!this.validator.hasStringValue(customerAccountBean.getLastName())) {
				this.throwRequiredFieldException("customerAccount.lastName");
			}
			
			if(!this.validator.hasStringValue(customerAccountBean.getBirthDate())) {
				if(!isValidDate(customerAccountBean.getBirthDate())) {
					this.throwRequiredFieldException("customerAccount.birthDate(format : yyyy-MM-dd)");
				}
			}
			
			if("Postpay".equals(assetDataBean.getProductType())) {
				if(!this.validator.hasStringValue(customerAccountBean.getCcbsCustId())) {
					this.throwRequiredFieldException("customerAccount.ccbsCustId");
				}
			}
			
			if(!this.validator.hasStringValue(assetDataBean.getSubscriberNo())) {
				this.throwRequiredFieldException("asset.subscriberNo");
			}
			
			if(customerAccountBean.getAddress() != null) {
			
				if(!this.validator.hasStringValue(customerAccountBean.getAddress().getHouseNo())) {
					this.throwRequiredFieldException("customerAccount.address.houseNo");
				}
				
				if(!this.validator.hasStringValue(customerAccountBean.getAddress().getSubdistrict())) {
					this.throwRequiredFieldException("customerAccount.address.subdistrict");
				}
				
				if(!this.validator.hasStringValue(customerAccountBean.getAddress().getDistrict())) {
					this.throwRequiredFieldException("customerAccount.address.district");
				}
				
				if(!this.validator.hasStringValue(customerAccountBean.getAddress().getProvince())) {
					this.throwRequiredFieldException("customerAccount.address.province");
				}
				
				if(!this.validator.hasStringValue(customerAccountBean.getAddress().getPostalCode())) {
					this.throwRequiredFieldException("customerAccount.address.postalCode");
				}
			}
		}
		
		if (parentCustomer != null) {
			
			if(!this.validator.hasStringValue(parentCustomer.getIdType())) {
				this.throwRequiredFieldException("parentCustomer.idType");
			}
			
			if(!this.validator.hasStringValue(parentCustomer.getIdNumber())) {
				this.throwRequiredFieldException("parentCustomer.idNumber");
			}
			
			if(!this.validator.hasStringValue(parentCustomer.getFirstName())) {
				this.throwRequiredFieldException("parentCustomer.firstName");
			}
			
			if(!this.validator.hasStringValue(parentCustomer.getLastName())) {
				this.throwRequiredFieldException("parentCustomer.lastName");
			}
			
			// Mobile

			if(!this.validator.hasStringValue(parentCustomer.getContactPhoneType())){					
				if (this.validator.hasStringValue(parentCustomer.getContactPhone())) this.throwRequiredFieldException("parentCustomer.contactPhone");
			}

			if(!StringUtil.isEmpty(parentCustomer.getContactPhone())){					
				if (StringUtil.isEmpty(parentCustomer.getContactPhoneType())) this.throwRequiredFieldException("parentCustomer.contactPhoneType");
			}
			
			if(!StringUtil.isEmpty(parentCustomer.getContactPhoneType())){
				
				//contactPhoneType : Auto Fax|Manual Fax|Mobile|Home|Work|Fax
				String contactPhoneType = CassandraConfig.getVal("crm.contactPhoneType");			
				String[] contactPhoneArray = contactPhoneType.split("\\|");
				boolean result = Arrays.stream(contactPhoneArray).anyMatch(parentCustomer.getContactPhoneType()::equals);
				if(!result)
					this.throwRequiredFieldException("contactPhoneType invalid value");
				
				//contactPhoneType
				/*
				crm.contactPhoneType.Auto Fax	: (\+66|66|0)\d{8}
				crm.contactPhoneType.Manual Fax	: (\+66|66|0)\d{8}
				crm.contactPhoneType.Mobile 	: (\+66|66|0)\d{9}
				crm.contactPhoneType.Home 		: (\+66|66|0)\d{8}
				crm.contactPhoneType.Work 		: (\+66|66|0)\d{8}
				crm.contactPhoneType.Fax		: (\+66|66|0)\d{8}
				*/
				String contactPhoneRegularExpression = CassandraConfig.getVal("crm.contactPhoneType."+parentCustomer.getContactPhoneType());
				if(!validator.validator(parentCustomer.getContactPhone(), contactPhoneRegularExpression))
					this.throwRequiredFieldException("format contactPhone : " + contactPhoneRegularExpression);			
			}
			
			if(parentCustomer.getAddress() != null) {
				
				if(!this.validator.hasStringValue(parentCustomer.getAddress().getHouseNo())) {
					this.throwRequiredFieldException("parentCustomer.address.houseNo");
				}
				
				if(!this.validator.hasStringValue(parentCustomer.getAddress().getSubdistrict())) {
					this.throwRequiredFieldException("parentCustomer.address.subdistrict");
				}
				
				if(!this.validator.hasStringValue(parentCustomer.getAddress().getDistrict())) {
					this.throwRequiredFieldException("parentCustomer.address.district");
				}
				
				if(!this.validator.hasStringValue(parentCustomer.getAddress().getProvince())) {
					this.throwRequiredFieldException("parentCustomer.address.province");
				}
				
				if(!this.validator.hasStringValue(parentCustomer.getAddress().getPostalCode())) {
					this.throwRequiredFieldException("parentCustomer.address.postalCode");
				}
			}
		}
	}
	
	public static boolean isValidDate(String date) {
		boolean isValid = false;
		
		if (date != null && !"".equals(date)) {
			isValid = date.matches("\\d{4}-\\d{2}-\\d{2}");
			if (isValid) {
				String[] dd_mm_yyyy = date.split("-");
				isValid = CalendarManager.isDate(Integer.parseInt(dd_mm_yyyy[2]), Integer.parseInt(dd_mm_yyyy[1])
						  , Integer.parseInt(dd_mm_yyyy[0]), CalendarManager.LOCALE_EN);
			}
		}
		
		return isValid;
	}

	
	public void validateUpsertAssetRelationship(AssetRelationShipReqBean assetRelationShipReqBean) throws ValidationException {
		
		if (assetRelationShipReqBean == null) {
			this.throwRequiredFieldException("assetRelationShipReqBean");
		}
		
		if (!this.validator.hasStringValue(assetRelationShipReqBean.getFieldName())) {
			this.throwRequiredFieldException("fieldName");
		}
		
		if (!this.validator.hasStringValue(assetRelationShipReqBean.getFieldValue())) {
			this.throwRequiredFieldException("fieldValue");
		}
		
		if (!this.validator.hasStringValue(assetRelationShipReqBean.getOperation())) {
			this.throwRequiredFieldException("operation");
		}
		
		if (!this.validator.hasStringValue(assetRelationShipReqBean.getParentFieldName())) {
			this.throwRequiredFieldException("parentFieldName");
		}
		
		if (!this.validator.hasStringValue(assetRelationShipReqBean.getParentFieldValue())) {
			this.throwRequiredFieldException("parentFieldValue");
		}
		
		if (!this.validator.hasStringValue(assetRelationShipReqBean.getRelationshipType())) {
			this.throwRequiredFieldException("relationshipType");
		}
				
		String parentfieldName = CassandraConfig.getVal("crm.crmiasset.upsertassetrelationship.parentfieldname");
		if (parentfieldName.indexOf(assetRelationShipReqBean.getParentFieldName()) < 0){
			this.throwRequiredFieldException("parentFieldName : "+parentfieldName);
		}
		
		String fieldName = CassandraConfig.getVal("crm.crmiasset.upsertassetrelationship.fieldname");
		if (fieldName.indexOf(assetRelationShipReqBean.getFieldName()) < 0){
			this.throwRequiredFieldException("fieldName : "+fieldName);
		}	
		
		String operation = CassandraConfig.getVal("crm.crmiasset.upsertassetrelationship.operation");
		if (operation.indexOf(assetRelationShipReqBean.getOperation()) < 0){
			this.throwRequiredFieldException("operation : "+operation);
		}	
		
		String relationshipType = CassandraConfig.getVal("crm.crmiasset.upsertassetrelationship.relationshiptype");
		if (relationshipType.indexOf(assetRelationShipReqBean.getRelationshipType()) < 0){
			this.throwRequiredFieldException("relationshipType : "+relationshipType);
		}
	}
	

	public void validateGetConverganceRelation(AssetFindByBean assetFindByBean) throws ValidationException {

		if (assetFindByBean == null) {
			this.throwRequiredFieldException("AssetFindByBean");
		}

		if (!this.validator.hasStringValue(assetFindByBean.getServiceID())
				&& !this.validator.hasStringValue(assetFindByBean.getAssetNo())
				&& !this.validator.hasStringValue(assetFindByBean.getAssetRowID())) {
			this.throwRequiredFieldException("serviceID or assetNo or assetRowId");
		}
		
		if (assetFindByBean.getPageNo() == null) {
			this.throwRequiredFieldException("AssetFindByBean.pageNo");
		}
		
		if (assetFindByBean.getPageSize() == null) {
			this.throwRequiredFieldException("AssetFindByBean.pageSize");
		}
	}

	public void validateChangeDummyMSISDNPrepay(DummyMsisdnReqBean dummyMsisdnReqBean) throws ValidationException {

		if (dummyMsisdnReqBean == null) {
			this.throwRequiredFieldException("DummyMsisdnReqBean");
		}

		if (!this.validator.hasStringValue(dummyMsisdnReqBean.getDummyServiceID())) {
			this.throwRequiredFieldException("DummyServiceID");
		}

		if (!this.validator.hasStringValue(dummyMsisdnReqBean.getServiceID())) {
			this.throwRequiredFieldException("ServiceID");
		}

		if (dummyMsisdnReqBean.getEffectiveDate() == null) {
			this.throwRequiredFieldException("EffectiveDate");
		}
	}
	
	public void validateUpsertPromiseToPay(PromiseToPayReqBean promiseToPayReqBean) throws ValidationException {

		if (promiseToPayReqBean == null) {
			this.throwRequiredFieldException("PromiseToPayReqBean");
		}
		
		if (promiseToPayReqBean.getAsset() == null) {
			this.throwRequiredFieldException("AssetDataBean");
		}
		
		if (!this.validator.hasStringValue(promiseToPayReqBean.getAsset().getAssetId())) {
			if (!this.validator.hasStringValue(promiseToPayReqBean.getAsset().getServiceId())) {
				this.throwRequiredFieldException("AssetBean.serviceId");
			}
			
			if (!this.validator.hasStringValue(promiseToPayReqBean.getAsset().getBan())) {
				this.throwRequiredFieldException("AssetBean.ban");
			}
		}
		
		if (!this.validator.hasStringValue(promiseToPayReqBean.getAsset().getChannel())) {
			this.throwRequiredFieldException("AssetBean.channel");
		}
		
		if (!this.validator.hasStringValue(promiseToPayReqBean.getAsset().getSubmitDate())) {
			this.throwRequiredFieldException("AssetBean.submitDate");
		}
		
		if (!this.validator.hasStringValue(promiseToPayReqBean.getAsset().getSrNumber())) {
			this.throwRequiredFieldException("AssetBean.srNumber");
		}
	}
	
	public void validateCountCapMax(CapMaxReqBean capMaxReqBean) throws ValidationException {
		if (capMaxReqBean == null) {
			this.throwRequiredFieldException("CapMaxReqBean");
		}
		
		if (!this.validator.hasStringValue(capMaxReqBean.getServiceID())) {
			this.throwRequiredFieldException("ServiceID");
		}
		
		if (!this.validator.hasStringValue(capMaxReqBean.getBan())) {
			this.throwRequiredFieldException("Ban");
		}
		
		if (capMaxReqBean.getCycleDate() == null) {
			this.throwRequiredFieldException("CycleDate");
		}
		
		if (!this.validator.hasArrayValue(capMaxReqBean.getCpcCategoryCodeList())) {
			this.throwRequiredFieldException("CpcCategoryCodeList");
		}
	}
	
	public void validateGetLiving(LivingReqBean livingReqBean) throws ValidationException {
		if (livingReqBean == null) {
			this.throwRequiredFieldException("livingReqBean");
		}
		
		if (!this.validator.hasStringValue(livingReqBean.getQrunAddressID())) {
			this.throwRequiredFieldException("LivingReqBean.QrunAddressID");
		}
	}
	
	public void validateGetAssetComponentListForOrder(AssetFindByBean assetFindByBean) throws ValidationException {
		if (assetFindByBean == null) {
			this.throwRequiredFieldException("AssetFindByBean");
		}
		
		if (!this.validator.hasStringValue(assetFindByBean.getAssetRowID())
				|| !this.validator.hasStringValue(assetFindByBean.getOlprdDBFlag())) {
			this.throwRequiredFieldException("AssetRowID Or OlprdDBFlag");
		}
		
		if (this.validator.hasStringValue(assetFindByBean.getOlprdDBFlag())) {
			if (!"Y".equalsIgnoreCase(assetFindByBean.getOlprdDBFlag()) && !"N".equalsIgnoreCase(assetFindByBean.getOlprdDBFlag())) {
				this.throwParamInvalidException("OlprdDBFlag");
			}
		}
	}
	
	public void validateCaasandra(String key, String value) throws ValidationException {
		if (!this.validator.hasStringValue(value)) {
			throw new ValidationException(Constants.ErrorCode.DATA_NOT_FOUND, key+" not found from config cassandra");
		}
	}
	
	public void validateDeleteAssetComponentList(DeleteComponentReqBean deleteComponentReqBean) throws ValidationException {
		if (deleteComponentReqBean == null) {
			this.throwRequiredFieldException("deleteComponentReqBean");
		}
		
		if (!this.validator.hasArrayValue(deleteComponentReqBean.getAssetRowIDList())) {
			this.throwRequiredFieldException("assetRowIDList");
		}
		
	}
}