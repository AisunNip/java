package th.co.truecorp.crmdev.asset.dao;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import th.co.truecorp.crmdev.asset.AssetLogger;
import th.co.truecorp.crmdev.asset.bean.AddressBean;
import th.co.truecorp.crmdev.asset.bean.AssetAttributeBean;
import th.co.truecorp.crmdev.asset.bean.AssetAttributeRespBean;
import th.co.truecorp.crmdev.asset.bean.AssetBean;
import th.co.truecorp.crmdev.asset.bean.AssetCompBean;
import th.co.truecorp.crmdev.asset.bean.AssetCompRespBean;
import th.co.truecorp.crmdev.asset.bean.AssetFindByBean;
import th.co.truecorp.crmdev.asset.bean.AssetRespBean;
import th.co.truecorp.crmdev.asset.bean.BillingAccountBean;
import th.co.truecorp.crmdev.asset.bean.BusinessBean2;
import th.co.truecorp.crmdev.asset.bean.CapMaxBean;
import th.co.truecorp.crmdev.asset.bean.CapMaxRespBean;
import th.co.truecorp.crmdev.asset.bean.Constants;
import th.co.truecorp.crmdev.asset.bean.ConvergenceRelationBean;
import th.co.truecorp.crmdev.asset.bean.ConvergenceRelationReqBean;
import th.co.truecorp.crmdev.asset.bean.ConvergenceRelationRespBean;
import th.co.truecorp.crmdev.asset.bean.CustomerAccountBean2;
import th.co.truecorp.crmdev.asset.bean.DCBBean;
import th.co.truecorp.crmdev.asset.bean.DeleteComponentReqBean;
import th.co.truecorp.crmdev.asset.bean.DeleteComponentRespBean;
import th.co.truecorp.crmdev.asset.bean.HuntingAssetBean;
import th.co.truecorp.crmdev.asset.bean.HuntingFLPReqBean;
import th.co.truecorp.crmdev.asset.bean.HuntingFLPRespBean;
import th.co.truecorp.crmdev.asset.bean.IndividualBean2;
import th.co.truecorp.crmdev.asset.bean.LivingReqBean;
import th.co.truecorp.crmdev.asset.bean.LivingRespBean;
import th.co.truecorp.crmdev.asset.bean.MaxAssetVipRespBean;
import th.co.truecorp.crmdev.asset.bean.MnpInfoBean;
import th.co.truecorp.crmdev.asset.bean.NameBean;
import th.co.truecorp.crmdev.asset.bean.ParentCustomerBean;
import th.co.truecorp.crmdev.asset.bean.ProductBean;
import th.co.truecorp.crmdev.asset.bean.ProductXMBean;
import th.co.truecorp.crmdev.asset.bean.ProductXMRespBean;
import th.co.truecorp.crmdev.asset.bean.ResponseBean;
import th.co.truecorp.crmdev.asset.exception.DatabaseException;
import th.co.truecorp.crmdev.asset.util.AssetErrorCode;
import th.co.truecorp.crmdev.util.common.CalendarManager;
import th.co.truecorp.crmdev.util.common.Validator;
import th.co.truecorp.crmdev.util.common.bean.ErrorCodeResp;
import th.co.truecorp.crmdev.util.db.mapping.MappingBean;
import th.co.truecorp.crmdev.util.logging.LogProductName;
import th.co.truecorp.crmdev.util.logging.LogSystem;

@Repository
public class AssetDAO extends BaseDAO {

	@Qualifier("dcrmDS")
	@Autowired
	private DataSource dcrmDS;

	@Qualifier("olcrmDS")
	@Autowired
	private DataSource olcrmDS;

	@Qualifier("cklistDS")
	@Autowired
	private DataSource cklistDS;

	@Qualifier("dcrmbatchrecDS")
	@Autowired
	private DataSource dcrmbatchrecDS;

	private String transID;
	private AssetLogger logger;
	private AssetErrorCode assetErrorCode;
	private Validator validator;

	public AssetDAO() throws UnknownHostException {
		this.logger = new AssetLogger(LogProductName.All, LogSystem.CRM_INBOUND, LogSystem.CRM_DATABASE);

		this.assetErrorCode = new AssetErrorCode();
		this.validator = new Validator();
	}

	public String getTransID() {
		return transID;
	}

	public void setTransID(String transID) {
		this.transID = transID;
	}

	public ProductXMBean[] filterProductXM(ProductXMBean[] productXMList, String productRowID) {
		ProductXMBean[] resultProductXMList = null;

		if (productXMList != null && productRowID != null) {
			resultProductXMList = Arrays.stream(productXMList)
					.filter(productXMBean -> productRowID.equals(productXMBean.getParProductRowID()))
					.toArray(ProductXMBean[]::new);

			if (resultProductXMList.length == 0) {
				resultProductXMList = null;
			}
		}

		return resultProductXMList;
	}

	public AssetAttributeBean[] filterAssetAttribute(AssetAttributeBean[] attribList, String assetRowID) {
		AssetAttributeBean[] resultAttribList = null;

		if (attribList != null && assetRowID != null) {
			resultAttribList = Arrays.stream(attribList)
					.filter(assetAttributeBean -> assetRowID.equals(assetAttributeBean.getAssetRowID()))
					.toArray(AssetAttributeBean[]::new);

			if (resultAttribList.length == 0) {
				resultAttribList = null;
			}
		}

		return resultAttribList;
	}

	public String mappingIDType(String idType, MappingBean[] mappingBeanList) {
		String idTypeCode = null;

		if (mappingBeanList != null && this.validator.hasStringValue(idType)) {
			for (MappingBean mappingBean : mappingBeanList) {
				if (idType.equalsIgnoreCase(mappingBean.getFromValue())) {
					idTypeCode = mappingBean.getToValue();
					break;
				}
			}
		}

		return idTypeCode;
	}

	public String calculateAssetLifeTime(Calendar assetStartdate) {
		String assetLifeTime = null;

		if (assetStartdate != null) {
			Calendar startCal = (Calendar) assetStartdate.clone();
			startCal = CalendarManager.setStartDay(startCal);

			Calendar currentCal = CalendarManager.getEngCalendar();
			currentCal = CalendarManager.setStartDay(currentCal);

			LocalDate startDate = CalendarManager.calendarToLocalDate(startCal);
			LocalDate todayDate = CalendarManager.calendarToLocalDate(currentCal);
			Period diff = Period.between(startDate, todayDate);
			assetLifeTime = diff.getYears() + " years " + diff.getMonths() + " months " + diff.getDays() + " days";
		}

		return assetLifeTime;
	}

	public AssetRespBean countAssetRootList(AssetFindByBean assetFindByBean) throws DatabaseException {
		AssetRespBean assetRespBean = new AssetRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Calendar startDAO = null;
		List<String> statusList = null;

		try {
			startDAO = logger.logRequestDBClient(this.transID);

			StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(AST.ROW_ID) AS TOTAL_RECORDS ");
			sqlBuilder.append("FROM SIEBEL.S_ASSET AST ");
			sqlBuilder.append("LEFT JOIN SIEBEL.S_ASSET_X ASTX ON AST.ROW_ID = ASTX.PAR_ROW_ID ");

			if (this.validator.hasStringValue(assetFindByBean.getMasterFlag())) {
				sqlBuilder.append(" LEFT JOIN SIEBEL.S_ORG_EXT CA ON AST.OWNER_ACCNT_ID = CA.ROW_ID ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getRelatedServiceID())) {
				sqlBuilder.append(
						" LEFT JOIN SIEBEL.S_ASSET_REL REL ON AST.ROW_ID = REL.ASSET_ID AND REL.RELATION_TYPE_CD = 'Dependent' ");
				sqlBuilder.append(" LEFT JOIN SIEBEL.S_ASSET PAR ON REL.PAR_ASSET_ID = PAR.ROW_ID ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getBan())
					|| this.validator.hasStringValue(assetFindByBean.getCvgBan())
					|| this.validator.hasStringValue(assetFindByBean.getTaxID())
					|| this.validator.hasStringValue(assetFindByBean.getBranchNumber())) {
				sqlBuilder.append(" LEFT JOIN SIEBEL.S_ORG_EXT BA ON AST.BILL_ACCNT_ID = BA.ROW_ID ");
				sqlBuilder.append(" LEFT JOIN SIEBEL.S_ORG_EXT_X BAX ON BA.ROW_ID = BAX.PAR_ROW_ID ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getProductLine())) {
				sqlBuilder.append(" LEFT JOIN S_PROD_INT PRD ON AST.PROD_ID = PRD.ROW_ID ");
				sqlBuilder.append(" LEFT JOIN S_PROD_LN PRDLN ON PRD.PR_PROD_LN_ID = PRDLN.ROW_ID ");
			}

			sqlBuilder.append("WHERE AST.ROW_ID = AST.ROOT_ASSET_ID ");
			sqlBuilder.append("AND AST.PAR_ASSET_ID IS NULL ");

			if (this.validator.hasStringValue(assetFindByBean.getAssetRowID())) {
				sqlBuilder.append("AND AST.ROW_ID = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getAssetNo())) {
				sqlBuilder.append("AND AST.ASSET_NUM = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getCustomerRowID())) {
				sqlBuilder.append("AND AST.OWNER_ACCNT_ID = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getServiceID())) {
				sqlBuilder.append("AND AST.SERIAL_NUM = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getRelatedServiceID())) {
				sqlBuilder.append("AND PAR.SERIAL_NUM = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getQrunAddressID())) {
				sqlBuilder.append("AND ASTX.X_ATTRIB_56 = ? ");
			}

			if (assetFindByBean.getFromEndDate() != null) {
				sqlBuilder.append("AND AST.X_DISCONNECT_DT >= ? ");
			}

			if (this.validator.hasArrayValue(assetFindByBean.getStatus())) {
				String[] status = assetFindByBean.getStatus();

				statusList = new ArrayList<String>(Arrays.asList(status));

				if (statusList.remove(Constants.AssetStatus.ALL_ACTIVE)) {
					sqlBuilder.append("AND AST.STATUS_CD NOT IN ('" + Constants.AssetStatus.INACTIVE);
					sqlBuilder.append("','");
					sqlBuilder.append(Constants.AssetStatus.TERMINATE);
					sqlBuilder.append("','");
					sqlBuilder.append(Constants.AssetStatus.CANCELLED);
					sqlBuilder.append("') ");
				}

				if (!statusList.isEmpty()) {
					sqlBuilder.append("AND AST.STATUS_CD IN (");

					for (int i = 0; i < statusList.size(); i++) {
						if (i == 0) {
							sqlBuilder.append("?");
						} else {
							sqlBuilder.append(",?");
						}
					}

					sqlBuilder.append(") ");
				}
			}

			if ("Y".equalsIgnoreCase(assetFindByBean.getMasterFlag())) {
				sqlBuilder.append("AND CA.X_MASTER_FLG = 'Y' ");
			} else if ("N".equalsIgnoreCase(assetFindByBean.getMasterFlag())) {
				sqlBuilder.append("AND CA.X_MASTER_FLG <> 'Y' ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getBan())) {
				sqlBuilder.append("AND BA.OU_NUM = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getCvgBan())) {
				sqlBuilder.append("AND BAX.X_BC_ID = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getTaxID())) {
				sqlBuilder.append("AND BA.TAX_IDEN_NUM = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getBranchNumber())) {
				sqlBuilder.append("AND BAX.ATTRIB_35 = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getProductLine())) {
				sqlBuilder.append("AND PRDLN.NAME = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getIntegrationId())) {
				sqlBuilder.append("AND AST.INTEGRATION_ID = ? ");
			}

			String[] customerRowIDList = assetFindByBean.getCustomerRowIDList();
			if (this.validator.hasArrayValue(customerRowIDList)) {
				sqlBuilder.append("AND AST.OWNER_ACCNT_ID IN (");

				for (int i = 0; i < customerRowIDList.length; i++) {
					if (i == 0) {
						sqlBuilder.append("?");
					} else {
						sqlBuilder.append(",?");
					}
				}

				sqlBuilder.append(") ");
			} else if (this.validator.hasStringValue(assetFindByBean.getIdNo())) {
				sqlBuilder.append("AND AST.OWNER_ACCNT_ID IN (");
				sqlBuilder.append("SELECT CA2.ROW_ID FROM SIEBEL.S_ORG_EXT CA2 ");
				sqlBuilder.append("INNER JOIN SIEBEL.S_CONTACT CON2 ON CA2.LOC = CON2.PERSON_UID ");
				sqlBuilder.append("INNER JOIN SIEBEL.S_CONTACT_XM CONID2 ON CON2.X_PR_ID = CONID2.ROW_ID ");
				sqlBuilder.append(
						"WHERE CA2.ACCNT_TYPE_CD = 'Customer' AND CA2.OU_TYPE_CD = 'Individual' AND CONID2.ATTRIB_03 = ? ");
				sqlBuilder.append("UNION ");
				sqlBuilder.append("SELECT CA2.ROW_ID FROM SIEBEL.S_ORG_EXT CA2 ");
				sqlBuilder.append(
						"WHERE CA2.ACCNT_TYPE_CD = 'Customer' AND CA2.OU_TYPE_CD = 'Business' AND CA2.X_ID_NUM = ?");
				sqlBuilder.append(") ");
			}

			logger.debug(this.transID, "SQL: " + sqlBuilder.toString());

			Integer totalRecords = new Integer(0);
			int index = 1;
			if (this.validator.hasStringValue(assetFindByBean.getOlprdDBFlag())
					&& "Y".equalsIgnoreCase(assetFindByBean.getOlprdDBFlag())) {
				conn = olcrmDS.getConnection();
			} else {
				conn = dcrmDS.getConnection();
			}
			pstmt = conn.prepareStatement(sqlBuilder.toString());
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);

			if (this.validator.hasStringValue(assetFindByBean.getAssetRowID())) {
				pstmt.setString(index++, assetFindByBean.getAssetRowID());
			}

			if (this.validator.hasStringValue(assetFindByBean.getAssetNo())) {
				pstmt.setString(index++, assetFindByBean.getAssetNo());
			}

			if (this.validator.hasStringValue(assetFindByBean.getCustomerRowID())) {
				pstmt.setString(index++, assetFindByBean.getCustomerRowID());
			}

			if (this.validator.hasStringValue(assetFindByBean.getServiceID())) {
				pstmt.setString(index++, assetFindByBean.getServiceID());
			}

			if (this.validator.hasStringValue(assetFindByBean.getRelatedServiceID())) {
				pstmt.setString(index++, assetFindByBean.getRelatedServiceID());
			}

			if (this.validator.hasStringValue(assetFindByBean.getQrunAddressID())) {
				pstmt.setString(index++, assetFindByBean.getQrunAddressID());
			}

			if (assetFindByBean.getFromEndDate() != null) {
				Calendar fromEndDateCal = (Calendar) assetFindByBean.getFromEndDate().clone();
				fromEndDateCal.add(Calendar.HOUR_OF_DAY, -7);

				pstmt.setTimestamp(index++, CalendarManager.calendarToTimestamp(fromEndDateCal));
			}

			if (statusList != null && !statusList.isEmpty()) {
				for (String status : statusList) {
					pstmt.setString(index++, status);
				}
			}

			if (this.validator.hasStringValue(assetFindByBean.getBan())) {
				pstmt.setString(index++, assetFindByBean.getBan());
			}

			if (this.validator.hasStringValue(assetFindByBean.getCvgBan())) {
				pstmt.setString(index++, assetFindByBean.getCvgBan());
			}

			if (this.validator.hasStringValue(assetFindByBean.getTaxID())) {
				pstmt.setString(index++, assetFindByBean.getTaxID());
			}

			if (this.validator.hasStringValue(assetFindByBean.getBranchNumber())) {
				pstmt.setString(index++, assetFindByBean.getBranchNumber());
			}

			if (this.validator.hasStringValue(assetFindByBean.getProductLine())) {
				pstmt.setString(index++, assetFindByBean.getProductLine());
			}

			if (this.validator.hasStringValue(assetFindByBean.getIntegrationId())) {
				pstmt.setString(index++, assetFindByBean.getIntegrationId());
			}

			if (this.validator.hasArrayValue(customerRowIDList)) {
				for (String customerRowID : customerRowIDList) {
					pstmt.setString(index++, customerRowID);
				}
			} else if (this.validator.hasStringValue(assetFindByBean.getIdNo())) {
				pstmt.setString(index++, assetFindByBean.getIdNo());
				pstmt.setString(index++, assetFindByBean.getIdNo());
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				totalRecords = new Integer(rs.getInt("TOTAL_RECORDS"));
			}

			assetRespBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
			assetRespBean.setMsg(Constants.SUCCESS_MSG);
			assetRespBean.setTotalRecords(totalRecords);
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);

			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.clearList(statusList);

			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);

			logger.logResponseDBClient(this.transID, assetRespBean.getCode(), startDAO);
		}

		return assetRespBean;
	}

	private final String QUERY_ASSET_ROOT_LIST = 
		"SELECT AST.ROW_ID AS ASSET_ROW_ID, AST.X_MASTER_FLG, AST.INTEGRATION_ID"
		+ ", AST.OWNER_ACCNT_ID AS CUSTOMER_ROW_ID, AST.BILL_ACCNT_ID AS BILLING_ROW_ID"
		+ ", AST.SERIAL_NUM AS SERVICE_ID, AST.STATUS_CD AS STATUS"
		+ ", NVL2(AST.X_ORIGINAL_EFFECTIVE_DATE, AST.X_ORIGINAL_EFFECTIVE_DATE, AST.X_START_DT) AS STARTDATE"
		+ ", AST.X_DISCONNECT_DT AS ENDDATE, AST.INSTALL_DT"
		+ ", AST.ASSET_NUM, AST.X_PREF_LANG, AST.DATA_SRC AS SOURCE_SYSTEM"
		+ ", AST.X_SPECIAL_NOTE"
		+ ", CA.OU_TYPE_CD AS CA_ACCOUNT_TYPE, CA.X_ACCNT_SUBTYPE AS CA_ACCOUNT_SUB_TYPE"
		+ ", CA.CUST_STAT_CD AS CA_STATUS, CA.STAT_CHG_DT AS CA_STATUS_DATE"
		+ ", CA.X_ID_TYPE AS BUS_ID_TYPE, CA.X_ID_NUM AS BUS_ID_NUM, CA.NAME AS BUS_ORGANIZATION_NAME"
		+ ", CA.MAIN_PH_NUM AS BUS_PHONE_NUMBER, CA.MAIN_FAX_PH_NUM AS BUS_FAX_NUMBER"
		+ ", CA.MAIN_EMAIL_ADDR AS BUS_EMAIL_ADDRESS"
		+ ", CA.CUST_SINCE_DT AS CA_CUSTOMER_SINCE"
		+ ", CA.LOC AS CA_GOLDEN_ID, CA.X_SOURCE_SYS AS CA_SOURCE_SYSTEM, CA.INTEGRATION_ID AS CA_INTEGRATION_ID"
		+ ", CA.X_TRUE_CARD_ID, CA.X_TRUE_CARD_STATUS, CA.X_TRUE_CARD_EXP_DT, CA.X_TRUE_CARD_UPD_DT"
		+ ", CASE WHEN CA.ACCNT_TYPE_CD = 'Customer' AND CA.OU_TYPE_CD = 'Business' THEN CA.X_CUST_GRADING " 
		+       " WHEN CA.X_ACCNT_SERVICE_LEVEL IN ('VIP1', 'VIP2', 'VIP3') THEN CA.X_ACCNT_SERVICE_LEVEL "
		+       " WHEN CA.X_CUST_GRADING IS NULL THEN 'White' "
		+ " ELSE CA.X_CUST_GRADING END AS CA_GRADING"
		+ ", CASE WHEN CA.X_ACCNT_SERVICE_LEVEL IS NULL THEN 'White' "
		+ " ELSE CA.X_ACCNT_SERVICE_LEVEL "
		+ " END AS CA_ACCNT_SERVICE_LEVEL"
		+ ", CONID.ATTRIB_04 AS ID_TYPE, CONID.ATTRIB_03 AS ID_NUM"
		+ ", CON.X_SALUTATION AS CA_TITLE, CON.FST_NAME AS CA_FIRST_NAME, CON.LAST_NAME AS CA_LAST_NAME"
		+ ", CON.JOB_TITLE AS CA_JOB_TITLE, CON.SEX_MF AS CA_GENDER, CON.MARITAL_STAT_CD AS CA_MARITAL_STATUS"
		+ ", CON.BIRTH_DT AS CA_BIRTH_DATE, CON.EMAIL_ADDR AS CA_EMAIL_ADDRESS"
		+ ", BA.PAR_OU_ID AS BAG_ROW_ID, BA.OU_NUM AS BAN, BA.OU_TYPE_CD AS BA_ACCOUNT_TYPE"
		+ ", BA.X_ACCNT_SUBTYPE AS BA_ACCOUNT_SUB_TYPE"
		+ ", BA.CUST_STAT_CD AS BA_STATUS, BA.NAME AS BA_ORG_NAME, BA.TAX_IDEN_NUM AS TAX_ID"
		+ ", BA.X_SOURCE_SYS AS BA_SOURCE_SYSTEM, BA.INTEGRATION_ID AS BA_INTEGRATION_ID"
		+ ", BAX.X_ATTRIB_82 AS BA_TITLE, BAX.X_ATTRIB_83 AS BA_FIRST_NAME, BAX.X_ATTRIB_84 AS BA_LAST_NAME"      
		+ ", BAX.X_ATTRIB_86 AS COMPANY_CODE, BAX.X_ATTRIB_91 AS CCBS_OU_ID"
		+ ", BAX.X_BC_ID AS CVG_BAN, BAX.ATTRIB_35 AS BRANCH_NUM"
		+ ", DEALER.OU_NUM AS DEALER_CODE"
		+ ", CASE WHEN AST.X_DISCONNECT_REASON IS NOT NULL THEN "
		+		"(SELECT LOV.VAL "
		+		"FROM SIEBEL.S_LST_OF_VAL LOV "
		+		"WHERE LOV.TYPE = 'TRU1_DISCONNECT_TYPE' "
		+		"AND LOV.NAME = AST.X_DISCONNECT_REASON "
		+		"AND LOV.ACTIVE_FLG = 'Y' "
		+		"AND ROWNUM = 1) "
		+ " END AS DISCONNECT_TYPE "
		+ ", PAR.SERIAL_NUM AS RELATED_SERVICE_ID"
		+ ", CASE WHEN REL.CREATED IS NULL THEN 1 "
        + " ELSE ROW_NUMBER() OVER(PARTITION BY AST.ROW_ID ORDER BY REL.CREATED DESC) "
        + " END AS RELATED_PRIORITY"
        + ", ROW_NUMBER() OVER(PARTITION BY AST.SERIAL_NUM ORDER BY DECODE(AST.STATUS_CD, 'Inactive', 2, 'Cancelled', 2, 'Terminate', 2, 1) ASC, AST.X_START_DT DESC , AST.X_END_DT DESC NULLS LAST) AS STATUS_PRIORITY"
		+ ", AST.X_REQUIRE_TYPE"
		+ ", AST.X_PAYMNT_TYPE_CD"
		+ ", AST.CREATED AS CREATED_DATE, AST.CREATED_BY"
		+ ", AST.LAST_UPD AS UPDATED_DATE, AST.LAST_UPD_BY AS UPDATED_BY"
		+ ", ASTX.ATTRIB_05 AS PREPAID_COMPANY_CODE"
		+ ", ASTX.ATTRIB_40 AS CVIP"
		+ ", ASTX.ATTRIB_42 AS MEDIA"
		+ ", ASTX.ATTRIB_35 AS SHARE_BAN"
		+ ", ASTX.ATTRIB_07 AS CCBS_SUBSCRIBER_ID"
		+ ", ASTX.X_FRAUD, ASTX.X_FRAUD_REASON, ASTX.X_FRAUD_DATE"
		+ ", ASTX.X_ATTRIB_50 AS SUBSCRIBER_NAME_TITLE"
		+ ", ASTX.X_ATTRIB_51 AS SUBSCRIBER_NAME_FIRSTNAME"
		+ ", ASTX.X_ATTRIB_52 AS SUBSCRIBER_NAME_LASTNAME"
		+ ", ASTX.ATTRIB_44 AS MOST_USAGE"
		+ ", ASTX.X_ATTRIB_56 AS QRUN_ADDRESS_ID"
		+ ", ASTX.X_ATTRIB_158 AS QRUN_PRODUCT_ID"
		+ ", ASTX.X_ATTRIB_79 AS CHURN_SCORE, ASTX.X_ATTRIB_80 AS CHURN_REASON, ASTX.X_ATTRIB_81 AS CHURN_FLAG"
		+ ", ASTX.X_ATTRIB_73 AS FR_CHANNEL, ASTX.X_ATTRIB_71 AS FR_VERIFY_METHOD, ASTX.X_ATTRIB_69 AS FR_KYC"
		+ ", ASTX.X_ATTRIB_72 AS FR_VERIFY_RESULT, ASTX.X_ATTRIB_74 AS FR_TRANS_DOC_ID"
		+ ", ASTX.X_ATTRIB_77 AS FR_CARD_READER, ASTX.X_ATTRIB_75 AS FR_ISSUE_DATE, ASTX.X_ATTRIB_76 AS FR_EXPIRE_DATE"
		+ ", PRD.ROW_ID AS PRODUCT_ROW_ID"
		+ ", PRD.NAME AS PRODUCT_NAME"
		+ ", PRD.PART_NUM AS PRODUCT_PART_NO"
		+ ", PRD.VENDR_PART_NUM AS PRODUCT_CODE"
		+ ", PRDLN.NAME AS PRODUCT_LINE"
		+ ", PRD.TYPE AS PRODUCT_TYPE"
		+ ", PRD.SUB_TYPE_CD AS PRODUCT_SUB_TYPE"
		+ ", PRD.PROD_CATG_CD AS PRODUCT_CATEGORY"
		+ ", ADDR.ROW_ID AS ADDRESS_ROW_ID"
		+ ", ADDR.COUNTRY"
		+ ", ADDR.ZIPCODE AS POSTAL_CODE"
		+ ", ADDR.CITY AS PROVINCE"
		+ ", ADDR.ADDR AS STREET"
		+ ", ADDR.ADDR_LINE_4 AS BUILING"
		+ ", ADDR.X_FLOOR"
		+ ", ADDR.ADDR_NUM AS HOUSE"
		+ ", ADDR.X_KHET_AMPHUR AS KHET"
		+ ", ADDR.X_KHWANG_TUMBON AS KHWANG"
		+ ", ADDR.X_MOO"
		+ ", ADDR.ADDR_LINE_3 AS ROOM"
		+ ", ADDR.ADDR_LINE_5 AS SOI"
		+ ", ADDR.X_LATITUDE, ADDR.X_LONGITUDE "
		+ ", ASTX.X_ATTRIB_82 AS PARENT_FIRST_NAME"
		+ ", ASTX.X_ATTRIB_83 AS PARENT_LAST_NAME"
		+ ", ASTX.X_ATTRIB_84 AS PARENT_ID_TYPE"
		+ ", ASTX.X_ATTRIB_85 AS PARENT_ID_NUMBER" 
		+ ", ASTX.X_ATTRIB_86 AS PARENT_CONTACT_NUMBER" 
		+ ", ASTX.X_ATTRIB_87 AS PARENT_HOUSE"
		+ ", ASTX.X_ATTRIB_88 AS PARENT_FLOOR"
		+ ", ASTX.X_ATTRIB_89 AS PARENT_ROOM"
		+ ", ASTX.X_ATTRIB_90 AS PARENT_BUILDING"
		+ ", ASTX.X_ATTRIB_91 AS PARENT_MOO"
		+ ", ASTX.X_ATTRIB_92 AS PARENT_SOI"
		+ ", ASTX.X_ATTRIB_93 AS PARENT_SUB_SOI"
		+ ", ASTX.X_ATTRIB_94 AS PARENT_STREET" 
		+ ", ASTX.X_ATTRIB_95 AS PARENT_KHWANG"
		+ ", ASTX.X_ATTRIB_96 AS PARENT_KHET"
		+ ", ASTX.X_ATTRIB_97 AS PARENT_PROVINCE"
		+ ", ASTX.X_ATTRIB_98 AS PARENT_POSTAL_CODE"
		+ ", ASTX.X_ATTRIB_99 AS PARENT_UPDATED_BY"
		//+ ", ASTX.X_ATTRIB_134 AS DCB_ID_NUM"
		+ ", ASTX.X_ATTRIB_135 AS DCB_PREFIX_TH"
		+ ", ASTX.X_ATTRIB_136 AS DCB_FNAME_TH"
		+ ", ASTX.X_ATTRIB_137 AS DCB_LNAME_TH"
		+ ", ASTX.X_ATTRIB_138 AS DCB_PREFIX_EN"
		+ ", ASTX.X_ATTRIB_139 AS DCB_FNAME_EN"
		+ ", ASTX.X_ATTRIB_140 AS DCB_LNAME_EN"
		+ ", ASTX.X_ATTRIB_141 AS DCB_DOB"
		+ ", ASTX.X_ATTRIB_142 AS DCB_EMAIL"
		+ ", ASTX.X_ATTRIB_143 AS DCB_KYC_FLAG"
		+ ", ASTX.X_ATTRIB_144 AS DCB_DOPA_FLAG"
		+ ", ASTX.X_ATTRIB_145 AS DCB_HOUSE"
		+ ", ASTX.X_ATTRIB_146 AS DCB_FLOOR"
		+ ", ASTX.X_ATTRIB_147 AS DCB_ROOM"
		+ ", ASTX.X_ATTRIB_148 AS DCB_BUILDING"
		+ ", ASTX.X_ATTRIB_149 AS DCB_MOO"
		+ ", ASTX.X_ATTRIB_150 AS DCB_SOI"
		+ ", ASTX.X_ATTRIB_151 AS DCB_SUB_SOI"
		+ ", ASTX.X_ATTRIB_152 AS DCB_STREET"
		+ ", ASTX.X_ATTRIB_153 AS DCB_KHWANG"
		+ ", ASTX.X_ATTRIB_154 AS DCB_KHET"
		+ ", ASTX.X_ATTRIB_155 AS DCB_PROVINCE"
        + ", ASTX.X_ATTRIB_156 AS DCB_POSTAL_CODE "        
        + ", ASTX.X_ATTRIB_161 AS DCB_ID_NUMBER "    
        + ", ASTX.X_ATTRIB_162 AS DCB_TIMESTAMP " 
        + ", ASTX.X_ATTRIB_212 AS SPECIAL "   
        + ", ASTX.X_ATTRIB_206 AS DOC_MGMT_ID "
        + ", ASTX.X_ATTRIB_163 AS DCB_TOKEN "    
        + ", ASTX.X_ATTRIB_164 AS DCB_IP_ADDR "
        + ", AST.X_START_DT AS X_START_DATE "
        + ", AST.X_ORIGINAL_EFFECTIVE_DATE AS ORIGINAL_START_DATE "  
        + ", ASTX.X_ATTRIB_131 AS LAST_MAIN_BALANCE "
        + ", ASTX.X_ATTRIB_132 AS DOC_ID "        
        + ", ASTX.X_ATTRIB_106 AS MNP_PORT_TYPE "
        + ", ASTX.X_ATTRIB_107 AS MNP_DONOR_OPER "
        + ", ASTX.X_ATTRIB_108 AS MNP_DONOR_ZONE "
        + ", ASTX.X_ATTRIB_109 AS MNP_REC_OPER "
        + ", ASTX.X_ATTRIB_110 AS MNP_REC_ZONE " 
		+ ", ASTX.X_ATTRIB_180 AS PREPAIDDEALERCODE "     
        + ", CONID.ATTRIB_12 AS ID_EXPIRE_DATE "
        + ", CONXID.X_ATTRIB_73 AS INDI_ADDITIONAL_TITLE "
        + ", CONXID.X_ATTRIB_70 AS INDI_MIDDLE_NAME "
        + ", CONXID.X_ATTRIB_74 AS INDI_NATIONNALITY "        
        + ", SPADDR_WORK.ADDR AS INDI_WORK_PHONE_NO "
        + ", SPADDR_HOME.ADDR AS INDI_HOME_PHONE_NO "
        + ", SPADDR_FAX.ADDR AS INDI_FAX_NUMBER "
        + ", SPADDR_MOBILE.ADDR AS INDI_MOBILE_NUMBER "        
        + ", ADDR.X_TYPE_OF_ACCOMMODATION AS TYPE_OF_ACCOMMODATION "
        + ", AST.X_INITIAL_ACT_DATE AS INIACTIVATINDATE "
		+ "FROM SIEBEL.S_ASSET AST "
		+ "LEFT JOIN SIEBEL.S_ASSET_X ASTX ON AST.ROW_ID = ASTX.PAR_ROW_ID "
		+ "LEFT JOIN SIEBEL.S_ASSET_REL REL ON AST.ROW_ID = REL.ASSET_ID AND REL.RELATION_TYPE_CD = 'Dependent' "
		+ "LEFT JOIN SIEBEL.S_ASSET PAR ON REL.PAR_ASSET_ID = PAR.ROW_ID "
		+ "LEFT JOIN SIEBEL.S_ORG_EXT CA ON AST.OWNER_ACCNT_ID = CA.ROW_ID "

			+ "LEFT JOIN SIEBEL.S_CONTACT CON ON CA.LOC = CON.PERSON_UID "
			+ "LEFT JOIN SIEBEL.S_CONTACT_XM CONID ON CON.X_PR_ID = CONID.ROW_ID "
			
			+ "LEFT JOIN SIEBEL.S_CONTACT_X CONXID ON CON.X_PR_ID = CONXID.ROW_ID "
			
			+ "LEFT JOIN SIEBEL.S_PROD_INT PRD ON AST.PROD_ID = PRD.ROW_ID "
			+ "LEFT JOIN SIEBEL.S_PROD_LN PRDLN ON PRD.PR_PROD_LN_ID = PRDLN.ROW_ID "
			 
			+ "LEFT JOIN SIEBEL.S_ORG_EXT BA ON AST.BILL_ACCNT_ID = BA.ROW_ID "
			+ "LEFT JOIN SIEBEL.S_ORG_EXT_X BAX ON BA.ROW_ID = BAX.PAR_ROW_ID "
			+ "LEFT JOIN SIEBEL.S_ORG_EXT DEALER ON ASTX.X_ATTRIB_48 = DEALER.ROW_ID "
			+ "LEFT JOIN SIEBEL.S_ASSET_OM AOM ON AOM.PAR_ROW_ID = AST.ROW_ID "
			+ "LEFT JOIN SIEBEL.S_ADDR_PER ADDR ON AOM.FROM_ADDR_ID = ADDR.ROW_ID "
			
			+ "LEFT JOIN "
			+ "(SELECT A.PER_ID, A.ADDR "
	        + " FROM ( SELECT SPADDR_WORK.ROW_ID, SPADDR_WORK.PER_ID, SPADDR_WORK.ADDR, "
	        + "        ROW_NUMBER() OVER(PARTITION BY SPADDR_WORK.PER_ID ORDER BY SPADDR_WORK.CREATED desc) AS SEQ1 "
	        + "        FROM SIEBEL.S_PER_COMM_ADDR SPADDR_WORK "
	        + "        WHERE SPADDR_WORK.COMM_MEDIUM_CD ='Phone' AND SPADDR_WORK.NAME = 'Work') A "
	        + " WHERE A.SEQ1 = 1 ) SPADDR_WORK ON CON.ROW_ID = SPADDR_WORK.PER_ID "
			
			+ "LEFT JOIN "
			+ "(SELECT A.PER_ID, A.ADDR "
	        + " FROM ( SELECT SPADDR_HOME.ROW_ID, SPADDR_HOME.PER_ID, SPADDR_HOME.ADDR, "
	        + "        ROW_NUMBER() OVER(PARTITION BY SPADDR_HOME.PER_ID ORDER BY SPADDR_HOME.CREATED desc) AS SEQ1 "
	        + "        FROM SIEBEL.S_PER_COMM_ADDR SPADDR_HOME "
	        + "        WHERE SPADDR_HOME.COMM_MEDIUM_CD ='Phone' AND SPADDR_HOME.NAME = 'Home') A "
	        + " WHERE A.SEQ1 = 1 ) SPADDR_HOME ON CON.ROW_ID = SPADDR_HOME.PER_ID "
			
			+ "LEFT JOIN "
			+ "(SELECT A.PER_ID, A.ADDR "
	        + " FROM ( SELECT SPADDR_FAX.ROW_ID, SPADDR_FAX.PER_ID, SPADDR_FAX.ADDR, "
	        + "        ROW_NUMBER() OVER(PARTITION BY SPADDR_FAX.PER_ID ORDER BY SPADDR_FAX.CREATED desc) AS SEQ1 "
	        + "        FROM SIEBEL.S_PER_COMM_ADDR SPADDR_FAX "
	        + "        WHERE SPADDR_FAX.COMM_MEDIUM_CD ='Phone' AND SPADDR_FAX.NAME = 'Fax') A "
	        + " WHERE A.SEQ1 = 1 ) SPADDR_FAX ON CON.ROW_ID = SPADDR_FAX.PER_ID "
			
			+ "LEFT JOIN "
			+ "(SELECT A.PER_ID, A.ADDR "
	        + " FROM ( SELECT SPADDR_MOBILE.ROW_ID, SPADDR_MOBILE.PER_ID, SPADDR_MOBILE.ADDR, "
	        + "        ROW_NUMBER() OVER(PARTITION BY SPADDR_MOBILE.PER_ID ORDER BY SPADDR_MOBILE.CREATED desc) AS SEQ1 "
	        + "        FROM SIEBEL.S_PER_COMM_ADDR SPADDR_MOBILE "
	        + "        WHERE SPADDR_MOBILE.COMM_MEDIUM_CD ='Phone' AND SPADDR_MOBILE.NAME = 'Mobile') A "
	        + " WHERE A.SEQ1 = 1 ) SPADDR_MOBILE ON CON.ROW_ID = SPADDR_MOBILE.PER_ID "

			
			+ "WHERE AST.ROW_ID = AST.ROOT_ASSET_ID "
			+ "AND AST.PAR_ASSET_ID IS NULL ";
		
	public AssetBean rsToAssetBean(ResultSet rs, MappingBean[] mappingBeanList) throws SQLException {
		AssetBean assetBean = new AssetBean();

		assetBean.setAssetRowID(rs.getString("ASSET_ROW_ID"));
		assetBean.setMasterFlag(rs.getString("X_MASTER_FLG"));
		assetBean.setAssetNo(rs.getString("ASSET_NUM"));
		assetBean.setCustomerRowID(rs.getString("CUSTOMER_ROW_ID"));
		assetBean.setBillingRowID(rs.getString("BILLING_ROW_ID"));
		assetBean.setServiceID(rs.getString("SERVICE_ID"));
		assetBean.setPreferLanguage(rs.getString("X_PREF_LANG"));
		assetBean.setSourceSystem(rs.getString("SOURCE_SYSTEM"));
		assetBean.setStatus(rs.getString("STATUS"));
		assetBean.setShareBAN(rs.getString("SHARE_BAN"));
		assetBean.setCcbsSubscriberID(rs.getString("CCBS_SUBSCRIBER_ID"));
		assetBean.setDisconnectType(rs.getString("DISCONNECT_TYPE"));

		assetBean.setCompanyCode(rs.getString("PREPAID_COMPANY_CODE"));
		assetBean.setCvip(rs.getString("CVIP"));
		assetBean.setMedia(rs.getString("MEDIA"));
		assetBean.setDealerCode(rs.getString("DEALER_CODE"));
		assetBean.setRelatedServiceID(rs.getString("RELATED_SERVICE_ID"));
		assetBean.setMostUsage(rs.getString("MOST_USAGE"));
		assetBean.setQrunAddressID(rs.getString("QRUN_ADDRESS_ID"));
		assetBean.setQrunProductID(rs.getString("QRUN_PRODUCT_ID"));

		assetBean.setChurnScore(rs.getString("CHURN_SCORE"));
		assetBean.setChurnReason(rs.getString("CHURN_REASON"));
		assetBean.setChurnFlag(rs.getString("CHURN_FLAG"));

		assetBean.setFaceRecChannel(rs.getString("FR_CHANNEL"));
		assetBean.setFaceRecVerifyMethod(rs.getString("FR_VERIFY_METHOD"));
		assetBean.setFaceRecKYC(rs.getString("FR_KYC"));
		assetBean.setFaceRecVerifyResult(rs.getString("FR_VERIFY_RESULT"));
		assetBean.setFaceRecTransDocID(rs.getString("FR_TRANS_DOC_ID"));
		assetBean.setFaceRecCardReader(rs.getString("FR_CARD_READER"));
		assetBean.setFaceRecIssueDate(rs.getString("FR_ISSUE_DATE"));
		assetBean.setFaceRecExpireDate(rs.getString("FR_EXPIRE_DATE"));

		assetBean.setFraud(rs.getString("X_FRAUD"));
		assetBean.setFraudReason(rs.getString("X_FRAUD_REASON"));
		assetBean.setFruadDate(this.getSiebelCalendar(rs, "X_FRAUD_DATE"));
		assetBean.setInstallDate(this.getSiebelCalendar(rs, "INSTALL_DT"));
		assetBean.setStartDate(this.getSiebelCalendar(rs, "STARTDATE"));
		assetBean.setEndDate(this.getSiebelCalendar(rs, "ENDDATE"));
		assetBean.setCpcRequiredType(rs.getString("X_REQUIRE_TYPE"));
		assetBean.setPaymentType(rs.getString("X_PAYMNT_TYPE_CD"));

		assetBean.setCreatedDate(this.getSiebelCalendar(rs, "CREATED_DATE"));
		assetBean.setCreatedBy(rs.getString("CREATED_BY"));
		assetBean.setLastUpdatedDate(this.getSiebelCalendar(rs, "UPDATED_DATE"));
		assetBean.setLastUpdatedBy(rs.getString("UPDATED_BY"));
		assetBean.setSpecialNote(rs.getString("X_SPECIAL_NOTE"));
		assetBean.setAssetLifeTime(this.calculateAssetLifeTime(assetBean.getStartDate()));
		assetBean.setIntegrationId(rs.getString("INTEGRATION_ID"));
		assetBean.setDocMgmtID(rs.getString("DOC_MGMT_ID"));

		assetBean.setxStartDate(this.getSiebelCalendar(rs, ("X_START_DATE")));
		assetBean.setOriginalStartDate(this.getSiebelCalendar(rs, ("ORIGINAL_START_DATE")));
		assetBean.setLastMainBalance(rs.getString("LAST_MAIN_BALANCE"));
		assetBean.setDocID(rs.getString("DOC_ID"));
		assetBean.setInitActivationDate(rs.getString("INIACTIVATINDATE"));
		assetBean.setPrepaidDealerCode(rs.getString("PREPAIDDEALERCODE"));

		if (this.validator.hasStringValue(assetBean.getBillingRowID())) {
			BillingAccountBean billingAccountBean = new BillingAccountBean();
			billingAccountBean.setBagRowID(rs.getString("BAG_ROW_ID"));
			billingAccountBean.setAccountRowID(assetBean.getBillingRowID());
			billingAccountBean.setAccountType(rs.getString("BA_ACCOUNT_TYPE"));
			billingAccountBean.setAccountSubType(rs.getString("BA_ACCOUNT_SUB_TYPE"));
			billingAccountBean.setStatus(rs.getString("BA_STATUS"));
			billingAccountBean.setBan(rs.getString("BAN"));
			billingAccountBean.setCvgBan(rs.getString("CVG_BAN"));
			billingAccountBean.setCompanyCode(rs.getString("COMPANY_CODE"));
			billingAccountBean.setCcbsOuID(rs.getString("CCBS_OU_ID"));

			// Receipt Name
			billingAccountBean.setTitle(rs.getString("BA_TITLE"));
			billingAccountBean.setFirstName(rs.getString("BA_FIRST_NAME"));
			billingAccountBean.setLastName(rs.getString("BA_LAST_NAME"));

			// Billing Name
			billingAccountBean.setOrganizationName(rs.getString("BA_ORG_NAME"));
			billingAccountBean.setTaxID(rs.getString("TAX_ID"));
			billingAccountBean.setBranchNumber(rs.getString("BRANCH_NUM"));
			billingAccountBean.setSourceSystem(rs.getString("BA_SOURCE_SYSTEM"));
			billingAccountBean.setIntegrationID(rs.getString("BA_INTEGRATION_ID"));

			assetBean.setBillingAccountBean(billingAccountBean);
		}

		// Customer Account
		if (this.validator.hasStringValue(assetBean.getCustomerRowID())) {
			CustomerAccountBean2 customerAccountBean = new CustomerAccountBean2();
			customerAccountBean.setAccountRowID(rs.getString("CUSTOMER_ROW_ID"));
			customerAccountBean.setAccountType(rs.getString("CA_ACCOUNT_TYPE"));
			customerAccountBean.setAccountSubType(rs.getString("CA_ACCOUNT_SUB_TYPE"));
			customerAccountBean.setStatus(rs.getString("CA_STATUS"));
			customerAccountBean.setStatusDate(this.getSiebelCalendar(rs, "CA_STATUS_DATE"));
			customerAccountBean.setCustomerSince(this.getSiebelCalendar(rs, "CA_CUSTOMER_SINCE"));
			customerAccountBean.setGoldenId(rs.getString("CA_GOLDEN_ID"));
			customerAccountBean.setSourceSystem(rs.getString("CA_SOURCE_SYSTEM"));
			customerAccountBean.setIntegrationID(rs.getString("CA_INTEGRATION_ID"));
			customerAccountBean.setTrueCardId(rs.getString("X_TRUE_CARD_ID"));
			customerAccountBean.setTrueCardStatus(rs.getString("X_TRUE_CARD_STATUS"));
			customerAccountBean.setTrueCardExpDate(this.getCalendar(rs, "X_TRUE_CARD_EXP_DT"));
			customerAccountBean.setTrueCardUpdateDate(this.getSiebelCalendar(rs, "X_TRUE_CARD_UPD_DT"));
			customerAccountBean.setGrading(rs.getString("CA_GRADING"));
			customerAccountBean.setAccountServiceLevel(rs.getString("CA_ACCNT_SERVICE_LEVEL"));

			if (Constants.CustomerAccountType.INDIVIDUAL.equalsIgnoreCase(customerAccountBean.getAccountType())) {
				IndividualBean2 individual = new IndividualBean2();
				individual.setIdType(rs.getString("ID_TYPE"));
				individual.setIdNumber(rs.getString("ID_NUM"));
				individual.setTitle(rs.getString("CA_TITLE"));
				individual.setFirstName(rs.getString("CA_FIRST_NAME"));
				individual.setLastName(rs.getString("CA_LAST_NAME"));
				individual.setJobTitle(rs.getString("CA_JOB_TITLE"));
				individual.setBirthDate(this.getSiebelCalendar(rs, "CA_BIRTH_DATE"));
				individual.setGender(rs.getString("CA_GENDER"));
				individual.setMaritalStatus(rs.getString("CA_MARITAL_STATUS"));
				individual.setEmailAddress(rs.getString("CA_EMAIL_ADDRESS"));

				individual.setIdTypeCode(this.mappingIDType(individual.getIdType(), mappingBeanList));

				individual.setIdExpireDate(this.getSiebelCalendar(rs, "ID_EXPIRE_DATE"));
				individual.setAdditionalTitle(rs.getString("INDI_ADDITIONAL_TITLE"));
				individual.setMiddleName(rs.getString("INDI_MIDDLE_NAME"));
				individual.setWorkPhoneNo(rs.getString("INDI_WORK_PHONE_NO"));
				individual.setHomePhoneNo(rs.getString("INDI_HOME_PHONE_NO"));
				individual.setFaxNumber(rs.getString("INDI_FAX_NUMBER"));
				individual.setMobileNo(rs.getString("INDI_MOBILE_NUMBER"));
				individual.setNationality(rs.getString("INDI_NATIONNALITY"));

				customerAccountBean.setIndividual(individual);
			} else {
				BusinessBean2 business = new BusinessBean2();
				business.setIdType(rs.getString("BUS_ID_TYPE"));
				business.setIdNumber(rs.getString("BUS_ID_NUM"));
				business.setOrganizationName(rs.getString("BUS_ORGANIZATION_NAME"));
				business.setPhoneNumber(rs.getString("BUS_PHONE_NUMBER"));
				business.setFaxNumber(rs.getString("BUS_FAX_NUMBER"));
				business.setEmailAddress(rs.getString("BUS_EMAIL_ADDRESS"));

				business.setIdTypeCode(this.mappingIDType(business.getIdType(), mappingBeanList));

				business.setIdExpireDate(this.getSiebelCalendar(rs, "ID_EXPIRE_DATE"));

				customerAccountBean.setBusiness(business);
			}

			assetBean.setCustomerAccountBean(customerAccountBean);
		}

		// Subscriber Name
		NameBean subscriberNameBean = new NameBean();
		subscriberNameBean.setTitle(rs.getString("SUBSCRIBER_NAME_TITLE"));
		subscriberNameBean.setFirstName(rs.getString("SUBSCRIBER_NAME_FIRSTNAME"));
		subscriberNameBean.setLastName(rs.getString("SUBSCRIBER_NAME_LASTNAME"));

		assetBean.setSubscriberNameBean(subscriberNameBean);

		// Product
		ProductBean productBean = new ProductBean();
		productBean.setProductRowID(rs.getString("PRODUCT_ROW_ID"));
		productBean.setProductName(rs.getString("PRODUCT_NAME"));
		productBean.setProductPartNo(rs.getString("PRODUCT_PART_NO"));
		productBean.setProductCode(rs.getString("PRODUCT_CODE"));
		productBean.setProductLine(rs.getString("PRODUCT_LINE"));
		productBean.setProductType(rs.getString("PRODUCT_TYPE"));
		productBean.setProductSubType(rs.getString("PRODUCT_SUB_TYPE"));
		productBean.setProductCategory(rs.getString("PRODUCT_CATEGORY"));

		assetBean.setProductBean(productBean);

		// MnpInfo
		MnpInfoBean mnpInfoBean = new MnpInfoBean();
		mnpInfoBean.setMnpPortType(rs.getString("MNP_PORT_TYPE"));
		mnpInfoBean.setMnpDonorOper(rs.getString("MNP_DONOR_OPER"));
		mnpInfoBean.setMnpDonorZone(rs.getString("MNP_DONOR_ZONE"));
		mnpInfoBean.setMnpRecOper(rs.getString("MNP_REC_OPER"));
		mnpInfoBean.setMnpRecZone(rs.getString("MNP_REC_ZONE"));

		assetBean.setMnpInfoBean(mnpInfoBean);

		if (rs.getString("ADDRESS_ROW_ID") != null) {
			// Installation Address
			AddressBean addressBean = new AddressBean();
			addressBean.setAddressRowID(rs.getString("ADDRESS_ROW_ID"));
			addressBean.setHouseNo(rs.getString("HOUSE"));
			addressBean.setRoomNo(rs.getString("ROOM"));
			addressBean.setFloor(rs.getString("X_FLOOR"));
			addressBean.setBuilding(rs.getString("BUILING"));
			addressBean.setMoo(rs.getString("X_MOO"));
			addressBean.setSoi(rs.getString("SOI"));
			addressBean.setStreet(rs.getString("STREET"));
			addressBean.setDistrict(rs.getString("KHET"));
			addressBean.setSubdistrict(rs.getString("KHWANG"));
			addressBean.setProvince(rs.getString("PROVINCE"));
			addressBean.setPostalCode(rs.getString("POSTAL_CODE"));
			addressBean.setCountry(rs.getString("COUNTRY"));
			addressBean.setLatitude(rs.getString("X_LATITUDE"));
			addressBean.setLongitude(rs.getString("X_LONGITUDE"));

			addressBean.setTypeOfAccommodation(rs.getString("TYPE_OF_ACCOMMODATION"));

			assetBean.setInstallationAddress(addressBean);
		}

		if (this.validator.hasStringValue(rs.getString("PARENT_FIRST_NAME"))
				|| this.validator.hasStringValue(rs.getString("PARENT_LAST_NAME"))
				|| this.validator.hasStringValue(rs.getString("PARENT_ID_TYPE"))
				|| this.validator.hasStringValue(rs.getString("PARENT_ID_NUMBER"))
				|| this.validator.hasStringValue(rs.getString("PARENT_CONTACT_NUMBER"))) {

			ParentCustomerBean parentCustomerBean = new ParentCustomerBean();
			parentCustomerBean.setFirstName(rs.getString("PARENT_FIRST_NAME"));
			parentCustomerBean.setLastName(rs.getString("PARENT_LAST_NAME"));
			parentCustomerBean.setIdType(rs.getString("PARENT_ID_TYPE"));
			parentCustomerBean.setIdNumber(rs.getString("PARENT_ID_NUMBER"));
			parentCustomerBean.setContactPhone(rs.getString("PARENT_CONTACT_NUMBER"));
			parentCustomerBean.setUpdatedBy(rs.getString("PARENT_UPDATED_BY"));

			if (this.validator.hasStringValue(rs.getString("PARENT_POSTAL_CODE"))) {

				AddressBean addressBean = new AddressBean();
				addressBean.setHouseNo(rs.getString("PARENT_HOUSE"));
				addressBean.setRoomNo(rs.getString("PARENT_ROOM"));
				addressBean.setFloor(rs.getString("PARENT_FLOOR"));
				addressBean.setBuilding(rs.getString("PARENT_BUILDING"));
				addressBean.setMoo(rs.getString("PARENT_MOO"));
				addressBean.setSubSoi(rs.getString("PARENT_SUB_SOI"));
				addressBean.setSoi(rs.getString("PARENT_SOI"));
				addressBean.setStreet(rs.getString("PARENT_STREET"));
				addressBean.setDistrict(rs.getString("PARENT_KHET"));
				addressBean.setSubdistrict(rs.getString("PARENT_KHWANG"));
				addressBean.setProvince(rs.getString("PARENT_PROVINCE"));
				addressBean.setPostalCode(rs.getString("PARENT_POSTAL_CODE"));
				parentCustomerBean.setAddress(addressBean);
			}

			assetBean.setParentCustomerBean(parentCustomerBean);
		}

		if (this.validator.hasStringValue(rs.getString("DCB_PREFIX_TH"))
				|| this.validator.hasStringValue(rs.getString("DCB_FNAME_TH"))
				|| this.validator.hasStringValue(rs.getString("DCB_LNAME_TH"))
				|| this.validator.hasStringValue(rs.getString("DCB_PREFIX_EN"))
				|| this.validator.hasStringValue(rs.getString("DCB_FNAME_EN"))
				|| this.validator.hasStringValue(rs.getString("DCB_LNAME_EN"))
				|| this.validator.hasStringValue(rs.getString("DCB_DOB"))
				|| this.validator.hasStringValue(rs.getString("DCB_EMAIL"))
				|| this.validator.hasStringValue(rs.getString("DCB_KYC_FLAG"))
				|| this.validator.hasStringValue(rs.getString("DCB_DOPA_FLAG"))) {

			DCBBean dcbBean = new DCBBean();
			dcbBean.setPrefixTH(rs.getString("DCB_PREFIX_TH"));
			dcbBean.setFstnameTH(rs.getString("DCB_FNAME_TH"));
			dcbBean.setLastnameTH(rs.getString("DCB_LNAME_TH"));
			dcbBean.setPrefixEN(rs.getString("DCB_PREFIX_EN"));
			dcbBean.setFstnameEN(rs.getString("DCB_FNAME_EN"));
			dcbBean.setLastnameEN(rs.getString("DCB_LNAME_EN"));
			dcbBean.setBirthDate(
					CalendarManager.stringToCalendar(rs.getString("DCB_DOB"), "dd/MM/yyyy", CalendarManager.LOCALE_TH));
			dcbBean.setEmail(rs.getString("DCB_EMAIL"));
			dcbBean.setFlagKYC(rs.getString("DCB_KYC_FLAG"));
			dcbBean.setFlagDOPA(rs.getString("DCB_DOPA_FLAG"));

			if (this.validator.hasStringValue(rs.getString("DCB_POSTAL_CODE"))) {

				AddressBean addressBean = new AddressBean();
				addressBean.setHouseNo(rs.getString("DCB_HOUSE"));
				addressBean.setRoomNo(rs.getString("DCB_ROOM"));
				addressBean.setFloor(rs.getString("DCB_FLOOR"));
				addressBean.setBuilding(rs.getString("DCB_BUILDING"));
				addressBean.setMoo(rs.getString("DCB_MOO"));
				addressBean.setSubSoi(rs.getString("DCB_SUB_SOI"));
				addressBean.setSoi(rs.getString("DCB_SOI"));
				addressBean.setStreet(rs.getString("DCB_STREET"));
				addressBean.setDistrict(rs.getString("DCB_KHET"));
				addressBean.setSubdistrict(rs.getString("DCB_KHWANG"));
				addressBean.setProvince(rs.getString("DCB_PROVINCE"));
				addressBean.setPostalCode(rs.getString("DCB_POSTAL_CODE"));
				dcbBean.setAddress(addressBean);
			}

			dcbBean.setIdNumber(rs.getString("DCB_ID_NUMBER"));
			dcbBean.setTimeStamp(CalendarManager.stringToCalendar(rs.getString("DCB_TIMESTAMP"),
					CalendarManager.SIEBEL_DATE_TIME_PATTERN, CalendarManager.LOCALE_TH));
			dcbBean.setToken(rs.getString("DCB_TOKEN"));
			dcbBean.setIpAddress(rs.getString("DCB_IP_ADDR"));

			assetBean.setDcbBean(dcbBean);
		}
		assetBean.setSpecial(rs.getString("SPECIAL"));

		return assetBean;
	}

	public AssetRespBean getAssetRootList(AssetFindByBean assetFindByBean, MappingBean[] mappingBeanList)
			throws DatabaseException {
		AssetRespBean assetRespBean = new AssetRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AssetBean> assetBeanList = null;
		List<String> statusList = null;
		Calendar startDAO = null;

		try {
			startDAO = logger.logRequestDBClient(this.transID);

			assetRespBean = this.countAssetRootList(assetFindByBean);

			if (!Constants.ErrorCode.SUCCESS_CODE.equals(assetRespBean.getCode())) {
				return assetRespBean;
			}

			if (assetRespBean.getTotalRecords() < 1) {
				String[] message = { "AssetRoot", "CRM" };
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);

				assetRespBean.setCode(errorCodeResp.getErrorCode());
				assetRespBean.setMsg(errorCodeResp.getErrorMessage());
				return assetRespBean;
			}

			StringBuilder sqlBuilder = new StringBuilder(QUERY_ASSET_ROOT_LIST);

			if (this.validator.hasStringValue(assetFindByBean.getAssetRowID())) {
				sqlBuilder.append("AND AST.ROW_ID = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getAssetNo())) {
				sqlBuilder.append("AND AST.ASSET_NUM = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getCustomerRowID())) {
				sqlBuilder.append("AND AST.OWNER_ACCNT_ID = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getServiceID())) {
				sqlBuilder.append("AND AST.SERIAL_NUM = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getRelatedServiceID())) {
				sqlBuilder.append("AND PAR.SERIAL_NUM = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getQrunAddressID())) {
				sqlBuilder.append("AND ASTX.X_ATTRIB_56 = ? ");
			}

			if (assetFindByBean.getFromEndDate() != null) {
				sqlBuilder.append("AND AST.X_DISCONNECT_DT >= ? ");
			}

			if (this.validator.hasArrayValue(assetFindByBean.getStatus())) {
				String[] status = assetFindByBean.getStatus();

				statusList = new ArrayList<String>(Arrays.asList(status));

				if (statusList.remove(Constants.AssetStatus.ALL_ACTIVE)) {
					sqlBuilder.append("AND AST.STATUS_CD NOT IN ('" + Constants.AssetStatus.INACTIVE);
					sqlBuilder.append("','");
					sqlBuilder.append(Constants.AssetStatus.TERMINATE);
					sqlBuilder.append("','");
					sqlBuilder.append(Constants.AssetStatus.CANCELLED);
					sqlBuilder.append("') ");
				}

				if (!statusList.isEmpty()) {
					sqlBuilder.append("AND AST.STATUS_CD IN (");

					for (int i = 0; i < statusList.size(); i++) {
						if (i == 0) {
							sqlBuilder.append("?");
						} else {
							sqlBuilder.append(",?");
						}
					}

					sqlBuilder.append(") ");
				}
			}

			if ("Y".equalsIgnoreCase(assetFindByBean.getMasterFlag())) {
				sqlBuilder.append("AND CA.X_MASTER_FLG = 'Y' ");
			} else if ("N".equalsIgnoreCase(assetFindByBean.getMasterFlag())) {
				sqlBuilder.append("AND CA.X_MASTER_FLG <> 'Y' ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getBan())) {
				sqlBuilder.append("AND BA.OU_NUM = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getCvgBan())) {
				sqlBuilder.append("AND BAX.X_BC_ID = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getTaxID())) {
				sqlBuilder.append("AND BA.TAX_IDEN_NUM = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getBranchNumber())) {
				sqlBuilder.append("AND BAX.ATTRIB_35 = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getProductLine())) {
				sqlBuilder.append("AND PRDLN.NAME = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getIntegrationId())) {
				sqlBuilder.append("AND AST.INTEGRATION_ID = ? ");
			}

			// Case : ID No
			String[] customerRowIDList = assetFindByBean.getCustomerRowIDList();
			if (this.validator.hasArrayValue(customerRowIDList)) {
				sqlBuilder.append("AND AST.OWNER_ACCNT_ID IN (");

				for (int i = 0; i < customerRowIDList.length; i++) {
					if (i == 0) {
						sqlBuilder.append("?");
					} else {
						sqlBuilder.append(",?");
					}
				}

				sqlBuilder.append(") ");
			} else if (this.validator.hasStringValue(assetFindByBean.getIdNo())) {
				sqlBuilder.append("AND AST.OWNER_ACCNT_ID IN (");
				sqlBuilder.append("SELECT CA2.ROW_ID FROM SIEBEL.S_ORG_EXT CA2 ");
				sqlBuilder.append("INNER JOIN SIEBEL.S_CONTACT CON2 ON CA2.LOC = CON2.PERSON_UID ");
				sqlBuilder.append("INNER JOIN SIEBEL.S_CONTACT_XM CONID2 ON CON2.X_PR_ID = CONID2.ROW_ID ");
				sqlBuilder.append(
						"WHERE CA2.ACCNT_TYPE_CD = 'Customer' AND CA2.OU_TYPE_CD = 'Individual' AND CONID2.ATTRIB_03 = ? ");
				sqlBuilder.append("UNION ");
				sqlBuilder.append("SELECT CA2.ROW_ID FROM SIEBEL.S_ORG_EXT CA2 ");
				sqlBuilder.append(
						"WHERE CA2.ACCNT_TYPE_CD = 'Customer' AND CA2.OU_TYPE_CD = 'Business' AND CA2.X_ID_NUM = ?");
				sqlBuilder.append(") ");
			}

			sqlBuilder.append("ORDER BY AST.X_START_DT DESC ");

			String condition = "RELATED_PRIORITY = 1";
			String sql = this.convertToSQLPaging(sqlBuilder.toString(), condition, assetFindByBean.getPageNo(),
					assetFindByBean.getPageSize());
			logger.debug(this.transID, "SQL: " + sql);

			int index = 1;
			if (this.validator.hasStringValue(assetFindByBean.getOlprdDBFlag())
					&& "Y".equalsIgnoreCase(assetFindByBean.getOlprdDBFlag())) {
				conn = olcrmDS.getConnection();
			} else {
				conn = dcrmDS.getConnection();
			}

			pstmt = conn.prepareStatement(sql);
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);

			if (this.validator.hasStringValue(assetFindByBean.getAssetRowID())) {
				pstmt.setString(index++, assetFindByBean.getAssetRowID());
			}

			if (this.validator.hasStringValue(assetFindByBean.getAssetNo())) {
				pstmt.setString(index++, assetFindByBean.getAssetNo());
			}

			if (this.validator.hasStringValue(assetFindByBean.getCustomerRowID())) {
				pstmt.setString(index++, assetFindByBean.getCustomerRowID());
			}

			if (this.validator.hasStringValue(assetFindByBean.getServiceID())) {
				pstmt.setString(index++, assetFindByBean.getServiceID());
			}

			if (this.validator.hasStringValue(assetFindByBean.getRelatedServiceID())) {
				pstmt.setString(index++, assetFindByBean.getRelatedServiceID());
			}

			if (this.validator.hasStringValue(assetFindByBean.getQrunAddressID())) {
				pstmt.setString(index++, assetFindByBean.getQrunAddressID());
			}

			if (assetFindByBean.getFromEndDate() != null) {
				Calendar fromEndDateCal = (Calendar) assetFindByBean.getFromEndDate().clone();
				fromEndDateCal.add(Calendar.HOUR_OF_DAY, -7);

				pstmt.setTimestamp(index++, CalendarManager.calendarToTimestamp(fromEndDateCal));
			}

			if (statusList != null && !statusList.isEmpty()) {
				for (String status : statusList) {
					pstmt.setString(index++, status);
				}
			}

			if (this.validator.hasStringValue(assetFindByBean.getBan())) {
				pstmt.setString(index++, assetFindByBean.getBan());
			}

			if (this.validator.hasStringValue(assetFindByBean.getCvgBan())) {
				pstmt.setString(index++, assetFindByBean.getCvgBan());
			}

			if (this.validator.hasStringValue(assetFindByBean.getTaxID())) {
				pstmt.setString(index++, assetFindByBean.getTaxID());
			}

			if (this.validator.hasStringValue(assetFindByBean.getBranchNumber())) {
				pstmt.setString(index++, assetFindByBean.getBranchNumber());
			}

			if (this.validator.hasStringValue(assetFindByBean.getProductLine())) {
				pstmt.setString(index++, assetFindByBean.getProductLine());
			}

			if (this.validator.hasStringValue(assetFindByBean.getIntegrationId())) {
				pstmt.setString(index++, assetFindByBean.getIntegrationId());
			}

			if (this.validator.hasArrayValue(customerRowIDList)) {
				for (String customerRowID : customerRowIDList) {
					pstmt.setString(index++, customerRowID);
				}
			} else if (this.validator.hasStringValue(assetFindByBean.getIdNo())) {
				pstmt.setString(index++, assetFindByBean.getIdNo());
				pstmt.setString(index++, assetFindByBean.getIdNo());
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				assetBeanList = new ArrayList<AssetBean>();

				do {
					assetBeanList.add(this.rsToAssetBean(rs, mappingBeanList));
				} while (rs.next());

				assetRespBean.setAssetBeanList(assetBeanList.toArray(new AssetBean[assetBeanList.size()]));
				assetRespBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				assetRespBean.setMsg(Constants.SUCCESS_MSG);
			} else {
				String[] message = { "AssetRoot", "CRM" };
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);

				assetRespBean.setCode(errorCodeResp.getErrorCode());
				assetRespBean.setMsg(errorCodeResp.getErrorMessage());
			}
		} catch (DatabaseException dbEx) {
			logger.error(this.transID, dbEx.getMessage(), dbEx);
			throw dbEx;
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);

			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.clearList(assetBeanList);
			this.clearList(statusList);

			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);

			logger.logResponseDBClient(this.transID, assetRespBean.getCode(), startDAO);
		}

		return assetRespBean;
	}

	/**
	 * 
	 * @param assetFindByBean
	 *            CASE 1. ASSET_ROW_ID CASE 2. SERVICE ID + ID NO CASE 3.
	 *            SERVICE ID CASE 4. SERVICE ID + BAN CASE 5. RELATED SERVICE ID
	 *            CASE 6. RELATED SERVICE ID + BAN
	 * @param mappingBeanList
	 *            are all id type.
	 * @return
	 * @throws DatabaseException
	 */
	public AssetRespBean getLatestAssetRoot(AssetFindByBean assetFindByBean, MappingBean[] mappingBeanList)
			throws DatabaseException {

		DataSource dataSource = null;

		if ("Y".equalsIgnoreCase(assetFindByBean.getOlprdDBFlag())) {
			dataSource = olcrmDS;
		} else {
			dataSource = dcrmDS;
		}

		return this.getLatestAssetRoot(assetFindByBean, dataSource, mappingBeanList);
	}

	public AssetRespBean getLatestAssetRoot(AssetFindByBean assetFindByBean) throws DatabaseException {

		DataSource dataSource = null;

		if (this.validator.hasStringValue(assetFindByBean.getOlprdDBFlag())
				&& "Y".equalsIgnoreCase(assetFindByBean.getOlprdDBFlag())) {
			dataSource = olcrmDS;
		} else {
			dataSource = dcrmDS;
		}

		return this.getLatestAssetRoot(assetFindByBean, dataSource, null);
	}

	public AssetRespBean getLatestAssetRoot(AssetFindByBean assetFindByBean, DataSource dataSource,
			MappingBean[] mappingBeanList) throws DatabaseException {
		AssetRespBean assetRespBean = new AssetRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Calendar startDAO = null;

		try {
			startDAO = logger.logRequestDBClient(this.transID);

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("SELECT DATA1.* FROM (");
			sqlBuilder.append(QUERY_ASSET_ROOT_LIST);

			if (this.validator.hasStringValue(assetFindByBean.getAssetRowID())) {
				sqlBuilder.append("AND AST.ROW_ID = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getRelatedServiceID())) {
				sqlBuilder.append("AND PAR.SERIAL_NUM = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getQrunAddressID())) {
				sqlBuilder.append("AND ASTX.X_ATTRIB_56 = ? ");
			}

			// Case : ID No
			String[] customerRowIDList = assetFindByBean.getCustomerRowIDList();
			if (this.validator.hasArrayValue(customerRowIDList)) {
				sqlBuilder.append("AND AST.OWNER_ACCNT_ID IN (");

				for (int i = 0; i < customerRowIDList.length; i++) {
					if (i == 0) {
						sqlBuilder.append("?");
					} else {
						sqlBuilder.append(",?");
					}
				}

				sqlBuilder.append(") ");
			} else if (this.validator.hasStringValue(assetFindByBean.getIdNo())) {
				sqlBuilder.append("AND AST.OWNER_ACCNT_ID IN (");
				sqlBuilder.append("SELECT CA2.ROW_ID FROM SIEBEL.S_ORG_EXT CA2 ");
				sqlBuilder.append("INNER JOIN SIEBEL.S_CONTACT CON2 ON CA2.LOC = CON2.PERSON_UID ");
				sqlBuilder.append("INNER JOIN SIEBEL.S_CONTACT_XM CONID2 ON CON2.X_PR_ID = CONID2.ROW_ID ");
				sqlBuilder.append(
						"WHERE CA2.ACCNT_TYPE_CD = 'Customer' AND CA2.OU_TYPE_CD = 'Individual' AND CONID2.ATTRIB_03 = ? ");
				sqlBuilder.append("UNION ");
				sqlBuilder.append("SELECT CA2.ROW_ID FROM SIEBEL.S_ORG_EXT CA2 ");
				sqlBuilder.append(
						"WHERE CA2.ACCNT_TYPE_CD = 'Customer' AND CA2.OU_TYPE_CD = 'Business' AND CA2.X_ID_NUM = ?");
				sqlBuilder.append(") ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getServiceID())) {
				sqlBuilder.append("AND AST.SERIAL_NUM = ? ");
			}

			if (this.validator.hasStringValue(assetFindByBean.getBan())) {
				sqlBuilder.append("AND BA.OU_NUM = ? ");
			}

			sqlBuilder.append(") DATA1 ");
			sqlBuilder.append("WHERE RELATED_PRIORITY = 1 AND STATUS_PRIORITY = 1");

			logger.debug(this.transID, "SQL: " + sqlBuilder.toString());

			int index = 1;
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sqlBuilder.toString());
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);

			if (this.validator.hasStringValue(assetFindByBean.getAssetRowID())) {
				pstmt.setString(index++, assetFindByBean.getAssetRowID());
			}

			if (this.validator.hasStringValue(assetFindByBean.getRelatedServiceID())) {
				pstmt.setString(index++, assetFindByBean.getRelatedServiceID());
			}

			if (this.validator.hasStringValue(assetFindByBean.getQrunAddressID())) {
				pstmt.setString(index++, assetFindByBean.getQrunAddressID());
			}

			if (this.validator.hasArrayValue(customerRowIDList)) {
				for (String customerRowID : customerRowIDList) {
					pstmt.setString(index++, customerRowID);
				}
			} else if (this.validator.hasStringValue(assetFindByBean.getIdNo())) {
				pstmt.setString(index++, assetFindByBean.getIdNo());
				pstmt.setString(index++, assetFindByBean.getIdNo());
			}

			if (this.validator.hasStringValue(assetFindByBean.getServiceID())) {
				pstmt.setString(index++, assetFindByBean.getServiceID());
			}

			if (this.validator.hasStringValue(assetFindByBean.getBan())) {
				pstmt.setString(index++, assetFindByBean.getBan());
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				AssetBean assetBean = this.rsToAssetBean(rs, mappingBeanList);
				AssetBean[] assetBeanList = { assetBean };

				assetRespBean.setAssetBeanList(assetBeanList);
				assetRespBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				assetRespBean.setMsg(Constants.SUCCESS_MSG);
			} else {
				String[] message = { "AssetRoot", "CRM" };
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);

				assetRespBean.setCode(errorCodeResp.getErrorCode());
				assetRespBean.setMsg(errorCodeResp.getErrorMessage());
			}
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);

			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);

			logger.logResponseDBClient(this.transID, assetRespBean.getCode(), startDAO);
		}

		return assetRespBean;
	}

	/**
	 * Case 1: ServiceID Case 2: ServiceID + BAN
	 * 
	 * @param assetFindByBean
	 * @return AssetRespBean object
	 * @throws DatabaseException
	 */
	public AssetRespBean getLatestAssetRootRowID(AssetFindByBean assetFindByBean) throws DatabaseException {
		AssetRespBean assetRespBean = new AssetRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Calendar startDAO = null;

		try {
			startDAO = logger.logRequestDBClient(this.transID);

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("SELECT AST.ROW_ID ");
			sqlBuilder.append("FROM SIEBEL.S_ASSET AST ");

			if (this.validator.hasStringValue(assetFindByBean.getBan())) {
				sqlBuilder.append("LEFT JOIN SIEBEL.S_ORG_EXT BA ON AST.BILL_ACCNT_ID = BA.ROW_ID ");
			}
		
			
			sqlBuilder.append("WHERE AST.PAR_ASSET_ID IS NULL ");
			sqlBuilder.append("AND AST.SERIAL_NUM = ? ");

			if (this.validator.hasStringValue(assetFindByBean.getBan())) {
				sqlBuilder.append("AND BA.OU_NUM = ? ");
			}

			if ("Y".equalsIgnoreCase(assetFindByBean.getMasterFlag())) {
				sqlBuilder.append("AND AST.X_MASTER_FLG = 'Y' ");
			}
			else if ("N".equalsIgnoreCase(assetFindByBean.getMasterFlag())) {
				sqlBuilder.append("AND AST.X_MASTER_FLG <> 'Y' ");
			}

			sqlBuilder.append("AND AST.X_START_DT = (SELECT MAX(AST2.X_START_DT) ");
			sqlBuilder.append("FROM SIEBEL.S_ASSET AST2 ");

			if (this.validator.hasStringValue(assetFindByBean.getBan())) {
				sqlBuilder.append(" LEFT JOIN SIEBEL.S_ORG_EXT BA2 ON AST2.BILL_ACCNT_ID = BA2.ROW_ID ");
			}

			sqlBuilder.append("WHERE AST2.SERIAL_NUM = AST.SERIAL_NUM ");

			if (this.validator.hasStringValue(assetFindByBean.getBan())) {
				sqlBuilder.append("AND BA2.OU_NUM = BA.OU_NUM ");
			}

			sqlBuilder.append(")");
			logger.debug(this.transID, "SQL: " + sqlBuilder.toString());

			int index = 1;

			if (this.validator.hasStringValue(assetFindByBean.getOlprdDBFlag())
					&& "Y".equalsIgnoreCase(assetFindByBean.getOlprdDBFlag())) {
				conn = olcrmDS.getConnection();
			} else {
				conn = dcrmDS.getConnection();
			}

			pstmt = conn.prepareStatement(sqlBuilder.toString());
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);

			pstmt.setString(index++, assetFindByBean.getServiceID());

			if (this.validator.hasStringValue(assetFindByBean.getBan())) {
				pstmt.setString(index++, assetFindByBean.getBan());
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				AssetBean assetBean = new AssetBean();
				assetBean.setAssetRowID(rs.getString("ROW_ID"));

				AssetBean[] assetBeanList = { assetBean };
				assetRespBean.setAssetBeanList(assetBeanList);
				assetRespBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				assetRespBean.setMsg(Constants.SUCCESS_MSG);
			} else {
				String[] message = { "AssetRoot", "CRM" };
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);

				assetRespBean.setCode(errorCodeResp.getErrorCode());
				assetRespBean.setMsg(errorCodeResp.getErrorMessage());
			}
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);

			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);

			logger.logResponseDBClient(this.transID, assetRespBean.getCode(), startDAO);
		}

		return assetRespBean;
	}

	private final String QUERY_GET_MAX_ASSET_VIP = 
			"SELECT MAX(MAX_ASSET_VIP) FROM (" 
	        + " SELECT CASE "
			+ " WHEN ASTX.X_ATTRIB_66 IS NULL THEN '0' " 
	        + " WHEN ASTX.X_ATTRIB_66 = 'VIP3' THEN '1' "
			+ " WHEN ASTX.X_ATTRIB_66 = 'VIP2' THEN '2' " 
	        + " WHEN ASTX.X_ATTRIB_66 = 'VIP1' THEN '3' "
			+ " WHEN ASTX.X_ATTRIB_66 = 'VIPA' THEN '1' " 
	        + " WHEN ASTX.X_ATTRIB_66 = 'VIPC' THEN '1' "
			+ " WHEN ASTX.X_ATTRIB_66 = 'VIPE' THEN '3' " 
	        + " WHEN ASTX.X_ATTRIB_66 = 'VIPR' THEN '3' "
			+ " WHEN ASTX.X_ATTRIB_66 = 'VIPF' THEN '1' " 
	        + " END AS MAX_ASSET_VIP " 
			+ " FROM SIEBEL.S_ASSET AST "
			+ " LEFT JOIN SIEBEL.S_ASSET_X ASTX ON AST.ROW_ID = ASTX.PAR_ROW_ID " 
	        + " WHERE AST.OWNER_ACCNT_ID = ? "
			+ " AND AST.STATUS_CD NOT IN ('Inactive','Terminate','Cancelled') AND AST.ROW_ID <> ? )";

	public MaxAssetVipRespBean getMaxAssetVip(String customerRowId, String assetRowId) {
		MaxAssetVipRespBean maxAssetVipRespBean = new MaxAssetVipRespBean();
		ErrorCodeResp errorCodeResp = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			if (!this.validator.hasStringValue(customerRowId)) {
				errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.REQUIRED_FIELDS, "CustomerRowId");
				return maxAssetVipRespBean;
			}

			conn = dcrmDS.getConnection();
			pstmt = conn.prepareStatement(QUERY_GET_MAX_ASSET_VIP);
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);
			pstmt.setString(1, customerRowId);
			pstmt.setString(2, assetRowId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				maxAssetVipRespBean.setMaxAssetVip(rs.getString("MAX(MAX_ASSET_VIP)"));

				maxAssetVipRespBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				maxAssetVipRespBean.setMsg(Constants.SUCCESS_MSG);
			} else {
				String[] errorMsg = { "Asset(CustomerRowId=" + customerRowId + ")", "CRM" };
				errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, errorMsg);
			}
		} catch (Throwable t) {
			errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE, t.getMessage());
		} finally {
			if (errorCodeResp != null) {
				maxAssetVipRespBean.setCode(errorCodeResp.getErrorCode());
				maxAssetVipRespBean.setMsg(errorCodeResp.getErrorMessage());
			}

			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);
		}

		return maxAssetVipRespBean;
	}

	private final String QUERY_ASSET_COMPONENT = "SELECT AC.ROW_ID, AC.PAR_ASSET_ID, AC.ROOT_ASSET_ID"
			+ ", AC.ASSET_NUM, AC.SERIAL_NUM, AC.STATUS_CD AS STATUS, AC.TYPE_CD"
			+ ", AC.X_START_DT AS EFFECTIVE_DATE, AC.X_END_DT AS EXPIRY_DATE, AC.INSTALL_DT INSTALL_DATE"
			+ ", AC.X_OVERRIDE_OTRATE, AC.X_OVERRIDE_FRATE, AC.X_PP_FEATURE_DESC"
			+ ", AC.X_TRUE_LIFE_ID, AC.X_CVG_GROUP_ID, AC.X_CVG_CODE, AC.X_CATEGORY_CODE"
			+ ", PC.ROW_ID AS PRODUCT_ROW_ID, PC.PROD_CATG_CD, PC.PROD_CD, PC.PART_NUM"
			+ ", PC.VENDR_PART_NUM AS OFFER_CODE, PC.NAME AS PRODUCT_NAME"
			+ ", PC.TYPE AS PRODUCT_TYPE, PC.SUB_TYPE_CD AS PRODUCT_SUB_TYPE"
			+ ", PC.DESC_TEXT_LONG, PC.X_DESC_TEXT_BILL_EN, PL.NAME AS PRODUCT_LINE"
			+ ", AX.X_ATTRIB_62 MATERIAL_CODE, AX.X_MODEL DEVICE_MODEL, AX.X_ATTRIB_61 DEVICE_TYPE"
			+ ", PC.X_OFFER_TYPE OFFER_TYPE, LOV.VAL CHARGE_PERIOD "
			+ ", AX.X_ATTRIB_157 AS OLDSERVICEID, AC.X_OVERRIDE_OC_DESC AS OVERRIDEOCDESCRIPTION "
			+ ", AC.X_PRICE AS PRICE, AC.X_DISCOUNT_VAL AS DISCOUNTVALUE "
			+ ", AC.X_DISCOUNT_TYPE AS DISCOUNTTYPE, AC.X_NEW_PERIOD_IND AS NEWPERIODIND "
			+ ", AC.DATA_SRC AS DATASOURCE, AC.QTY AS QUANTITY, PC.X_CPC_PRODUCT_TYPE AS CPCPRODUCTTYPE "
			+ ", PC.X_SOC_CD AS CCBS_SOC " 
			+ "FROM SIEBEL.S_ASSET AC "
			+ "LEFT JOIN SIEBEL.S_ASSET_X AX ON AX.PAR_ROW_ID = AC.ROW_ID ";

	/**
	 * 
	 * @param assetFindByBean
	 *            Case 1: assetRowID Case 2: assetNo
	 * @return AssetComponentRespBean
	 * @throws DatabaseException
	 */
	public AssetCompRespBean getAssetComponentAttributeList(AssetFindByBean assetFindByBean) throws DatabaseException {
		AssetCompRespBean responseBean = new AssetCompRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<AssetCompBean> assetComponentList = null;
		ArrayList<String> assetCompRowIDList = null;
		ArrayList<String> productRowIDList = null;
		List<String> statusList = null;
		Calendar startDAO = null;

		try {
			startDAO = logger.logRequestDBClient(this.transID);

			StringBuilder sqlBuilder = new StringBuilder(QUERY_ASSET_COMPONENT);

			if (assetFindByBean.getCpcOfferFlag() != null && assetFindByBean.getCpcOfferFlag().equalsIgnoreCase("Y")) {
				sqlBuilder.append(
						"INNER JOIN SIEBEL.S_PROD_INT PC ON PC.ROW_ID = AC.PROD_ID AND PC.PART_NUM like 'CPC%' ");
			} else {
				sqlBuilder.append("INNER JOIN SIEBEL.S_PROD_INT PC ON PC.ROW_ID = AC.PROD_ID ");
			}
			sqlBuilder.append("INNER JOIN SIEBEL.S_PROD_LN PL ON PL.ROW_ID = PC.PR_PROD_LN_ID "
					+ "LEFT JOIN SIEBEL.S_LST_OF_VAL LOV ON LOV.NAME = AX.X_ATTRIB_55 AND LOV.TYPE = 'TRU2_CHANGE_CHARGE_PERIOD' "
					+ "WHERE AC.PAR_ASSET_ID IS NOT NULL ");

			if (this.validator.hasStringValue(assetFindByBean.getAssetRowID())) {
				sqlBuilder.append(" AND AC.ROOT_ASSET_ID = ?");
			}

			if (this.validator.hasStringValue(assetFindByBean.getAssetNo())) {
				sqlBuilder.append(
						" AND AC.ROOT_ASSET_ID IN (SELECT A.ROW_ID FROM SIEBEL.S_ASSET A WHERE A.ASSET_NUM = ?)");
			}

			if (this.validator.hasArrayValue(assetFindByBean.getStatus())) {
				String[] status = assetFindByBean.getStatus();

				statusList = new ArrayList<String>(Arrays.asList(status));

				if (statusList.remove(Constants.AssetStatus.ALL_ACTIVE)) {
					sqlBuilder.append(" AND AC.STATUS_CD NOT IN ('" + Constants.AssetStatus.INACTIVE);
					sqlBuilder.append("','");
					sqlBuilder.append(Constants.AssetStatus.TERMINATE);
					sqlBuilder.append("','");
					sqlBuilder.append(Constants.AssetStatus.CANCELLED);
					sqlBuilder.append("')");
				}

				if (!statusList.isEmpty()) {
					sqlBuilder.append(" AND AC.STATUS_CD IN (");

					for (int i = 0; i < statusList.size(); i++) {
						if (i == 0) {
							sqlBuilder.append("?");
						} else {
							sqlBuilder.append(",?");
						}
					}

					sqlBuilder.append(")");
				}
			}

			if (this.validator.hasArrayValue(assetFindByBean.getProductTypeList())) {
				sqlBuilder.append(" AND PC.TYPE IN (");

				for (int i = 0; i < assetFindByBean.getProductTypeList().length; i++) {
					if (i == 0) {
						sqlBuilder.append("?");
					} else {
						sqlBuilder.append(",?");
					}
				}

				sqlBuilder.append(")");
			}

			sqlBuilder.append(" ORDER BY AC.X_START_DT DESC");

			logger.debug(this.transID, "getAssetComponentAttributeList SQL: " + sqlBuilder.toString());

			int index = 1;

			if (this.validator.hasStringValue(assetFindByBean.getOlprdDBFlag())
					&& "Y".equalsIgnoreCase(assetFindByBean.getOlprdDBFlag())) {
				conn = olcrmDS.getConnection();
			} else {
				conn = dcrmDS.getConnection();
			}

			pstmt = conn.prepareStatement(sqlBuilder.toString());
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);

			if (this.validator.hasStringValue(assetFindByBean.getAssetRowID())) {
				pstmt.setString(index++, assetFindByBean.getAssetRowID());
			}

			if (this.validator.hasStringValue(assetFindByBean.getAssetNo())) {
				pstmt.setString(index++, assetFindByBean.getAssetNo());
			}

			if (statusList != null && !statusList.isEmpty()) {
				for (String status : statusList) {
					pstmt.setString(index++, status);
				}
			}

			if (this.validator.hasArrayValue(assetFindByBean.getProductTypeList())) {
				for (String productType : assetFindByBean.getProductTypeList()) {
					pstmt.setString(index++, productType);
				}
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				assetComponentList = new ArrayList<AssetCompBean>();

				do {
					AssetCompBean bean = new AssetCompBean();
					bean.setAssetRowID(rs.getString("ROW_ID"));
					bean.setParAssetRowID(rs.getString("PAR_ASSET_ID"));
					bean.setRootAssetRowID(rs.getString("ROOT_ASSET_ID"));
					bean.setAssetNo(rs.getString("ASSET_NUM"));
					bean.setSerialNo(rs.getString("SERIAL_NUM"));
					bean.setStatus(rs.getString("STATUS"));
					bean.setType(rs.getString("TYPE_CD"));
					bean.setEffectiveDate(this.getSiebelCalendar(rs, "EFFECTIVE_DATE"));
					bean.setExpiryDate(this.getSiebelCalendar(rs, "EXPIRY_DATE"));
					bean.setInstallDate(this.getSiebelCalendar(rs, "INSTALL_DATE"));
					bean.setOverrideOTAmount(rs.getString("X_OVERRIDE_OTRATE"));
					bean.setOverrideAmount(rs.getString("X_OVERRIDE_FRATE"));
					bean.setOverrideDesc(rs.getString("X_PP_FEATURE_DESC"));
					bean.setMaterialCode(rs.getString("MATERIAL_CODE"));
					bean.setDeviceModel(rs.getString("DEVICE_MODEL"));
					bean.setDeviceType(rs.getString("DEVICE_TYPE"));
					bean.setTrueLifeID(rs.getString("X_TRUE_LIFE_ID"));
					bean.setCvgGroupID(rs.getString("X_CVG_GROUP_ID"));
					bean.setCvgCode(rs.getString("X_CVG_CODE"));
					bean.setCpcCategoryCode(rs.getString("X_CATEGORY_CODE"));
					bean.setCpcProductType(rs.getString("CPCPRODUCTTYPE"));

					bean.setProductRowID(rs.getString("PRODUCT_ROW_ID"));
					bean.setProductCategory(rs.getString("PROD_CATG_CD"));
					bean.setProductCode(rs.getString("PROD_CD"));
					bean.setPartNum(rs.getString("PART_NUM"));
					bean.setOfferCode(rs.getString("OFFER_CODE"));
					bean.setProductName(rs.getString("PRODUCT_NAME"));
					bean.setProductType(rs.getString("PRODUCT_TYPE"));
					bean.setProductSubType(rs.getString("PRODUCT_SUB_TYPE"));
					bean.setProductLine(rs.getString("PRODUCT_LINE"));

					bean.setOfferType(rs.getString("OFFER_TYPE"));
					bean.setChargePeriod(rs.getString("CHARGE_PERIOD"));

					bean.setOldServiceID(rs.getString("OLDSERVICEID"));
					bean.setOverrideOCDesc(rs.getString("OVERRIDEOCDESCRIPTION"));
					bean.setPrice(rs.getString("PRICE"));
					bean.setDiscountValue(rs.getString("DISCOUNTVALUE"));
					bean.setDiscountType(rs.getString("DISCOUNTTYPE"));
					bean.setNewPeriodInd(rs.getString("NEWPERIODIND"));
					bean.setDataSource(rs.getString("DATASOURCE"));
					bean.setQuantity(rs.getString("QUANTITY"));
					bean.setCcbsSoc(rs.getString("CCBS_SOC"));

					// Calculate OfferName
					String productDescText = rs.getString("DESC_TEXT_LONG");
					String productDescTextBill = rs.getString("X_DESC_TEXT_BILL_EN");

					StringBuilder offerNameBuilder = new StringBuilder();

					if ("True Online".equalsIgnoreCase(bean.getProductLine())) {
						offerNameBuilder.append(bean.getProductName());
					} else {
						String productName = bean.getProductName();
						productName = productName.substring(productName.indexOf("-") + 1);
						offerNameBuilder.append(productName);
					}

					if (this.validator.hasStringValue(productDescText)) {
						offerNameBuilder.append("-");
						offerNameBuilder.append(productDescText);
					}

					if (this.validator.hasStringValue(productDescTextBill)) {
						offerNameBuilder.append("-");
						offerNameBuilder.append(productDescTextBill);
					}

					bean.setOfferName(offerNameBuilder.toString());

					assetComponentList.add(bean);
				} while (rs.next());

				// Get ProductXM
				if ("Y".equalsIgnoreCase(assetFindByBean.getProductXMFlag())) {
					logger.info(this.transID, "Invoke getProductXMList");

					productRowIDList = new ArrayList<String>();

					for (AssetCompBean assetCompBean : assetComponentList) {
						productRowIDList.add(assetCompBean.getProductRowID());
					}

					ProductXMRespBean prodXMRespBean = this.getProductXMList(productRowIDList);

					if (Constants.ErrorCode.SUCCESS_CODE.equals(prodXMRespBean.getCode())) {

						ProductXMBean[] productXMBeanList = prodXMRespBean.getProductXMList();

						for (AssetCompBean assetCompBean : assetComponentList) {
							assetCompBean.setProductXMList(
									this.filterProductXM(productXMBeanList, assetCompBean.getProductRowID()));
						}
					} else if (!this.assetErrorCode.isDataNotFound(prodXMRespBean.getCode())) {
						responseBean.setCode(prodXMRespBean.getCode());
						responseBean.setMsg(prodXMRespBean.getMsg());
						return responseBean;
					}
				}

				// Get Asset Attribute
				if ("Y".equalsIgnoreCase(assetFindByBean.getAttributeFlag())) {
					logger.info(this.transID, "Invoke getAssetAttributeList");

					assetCompRowIDList = new ArrayList<String>();

					for (AssetCompBean assetCompBean : assetComponentList) {
						assetCompRowIDList.add(assetCompBean.getAssetRowID());
					}

					AssetAttributeRespBean attribRespBean = this.getAssetAttributeList(assetCompRowIDList);

					if (Constants.ErrorCode.SUCCESS_CODE.equals(attribRespBean.getCode())) {

						AssetAttributeBean[] attribList = attribRespBean.getAttributeList();

						for (AssetCompBean assetCompBean : assetComponentList) {
							assetCompBean.setAttributeList(
									this.filterAssetAttribute(attribList, assetCompBean.getAssetRowID()));
						}
					} else if (!this.assetErrorCode.isDataNotFound(attribRespBean.getCode())) {
						responseBean.setCode(attribRespBean.getCode());
						responseBean.setMsg(attribRespBean.getMsg());
						return responseBean;
					}
				}

				responseBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				responseBean.setMsg(Constants.SUCCESS_MSG);
				responseBean.setAssetComponentList(
						assetComponentList.toArray(new AssetCompBean[assetComponentList.size()]));
			} else {
				String[] message = { "AssetComponent", "CRM" };
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);

				responseBean.setCode(errorCodeResp.getErrorCode());
				responseBean.setMsg(errorCodeResp.getErrorMessage());
			}
		} catch (DatabaseException dbEx) {
			responseBean.setCode(dbEx.getErrorCode());
			responseBean.setMsg(dbEx.getMessage());
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);

			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.clearList(assetComponentList);
			this.clearList(assetCompRowIDList);
			this.clearList(productRowIDList);
			this.clearList(statusList);

			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);

			logger.logResponseDBClient(this.transID, responseBean.getCode(), startDAO);
		}

		return responseBean;
	}

	public ProductXMRespBean getProductXMList(ArrayList<String> productRowIDList) throws DatabaseException {
		ProductXMRespBean respBean = new ProductXMRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProductXMBean> productXMList = null;
		Calendar startDAO = null;

		try {
			startDAO = logger.logRequestDBClient(this.transID);

			StringBuilder sqlBuilder = new StringBuilder("SELECT XM.ROW_ID, XM.PAR_ROW_ID, XM.TYPE, XM.NAME");
			sqlBuilder.append(", PC.TYPE AS PRODUCT_TYPE, PC.VENDR_PART_NUM AS OFFER_CODE, PC.DESC_TEXT ");
			sqlBuilder.append("FROM SIEBEL.S_PROD_INT_XM XM ");
			sqlBuilder.append("INNER JOIN SIEBEL.S_PROD_INT PC ON PC.ROW_ID = XM.ATTRIB_35 ");
			sqlBuilder.append("WHERE XM.PAR_ROW_ID IN (");

			for (int i = 0; i < productRowIDList.size(); i++) {
				if (i == 0) {
					sqlBuilder.append("?");
				} else {
					sqlBuilder.append(",?");
				}
			}

			sqlBuilder.append(")");

			int index = 1;

			conn = dcrmDS.getConnection();
			pstmt = conn.prepareStatement(sqlBuilder.toString());
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);

			for (String productRowID : productRowIDList) {
				pstmt.setString(index++, productRowID);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				productXMList = new ArrayList<ProductXMBean>();

				do {
					ProductXMBean bean = new ProductXMBean();
					bean.setProductXMRowID(rs.getString("ROW_ID"));
					bean.setParProductRowID(rs.getString("PAR_ROW_ID"));
					bean.setType(rs.getString("TYPE"));
					bean.setComposeProductRowID(rs.getString("NAME"));
					bean.setProductType(rs.getString("PRODUCT_TYPE"));
					bean.setOfferCode(rs.getString("OFFER_CODE"));
					bean.setOfferName(rs.getString("DESC_TEXT"));

					productXMList.add(bean);
				} while (rs.next());

				respBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				respBean.setMsg(Constants.SUCCESS_MSG);
				respBean.setProductXMList(productXMList.toArray(new ProductXMBean[productXMList.size()]));
			} else {
				String[] message = { "ProductXM", "CRM" };
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);

				respBean.setCode(errorCodeResp.getErrorCode());
				respBean.setMsg(errorCodeResp.getErrorMessage());
			}
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);

			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.clearList(productXMList);

			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);
			logger.logResponseDBClient(this.transID, respBean.getCode(), startDAO);
		}

		return respBean;
	}

	public AssetAttributeRespBean getAssetAttributeList(ArrayList<String> assetCompRowIDList) throws DatabaseException {
		AssetAttributeRespBean responseBean = new AssetAttributeRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<AssetAttributeBean> assetAttribList = null;
		Calendar startDAO = null;

		try {
			startDAO = logger.logRequestDBClient(this.transID);

			StringBuilder sqlBuilder = new StringBuilder("SELECT ROW_ID, ATTR_NAME, CHAR_VAL AS DESC_TEXT, ASSET_ID ");
			sqlBuilder.append("FROM SIEBEL.S_ASSET_XA ");
			sqlBuilder.append("WHERE ASSET_ID IN (");

			for (int i = 0; i < assetCompRowIDList.size(); i++) {
				if (i == 0) {
					sqlBuilder.append("?");
				} else {
					sqlBuilder.append(",?");
				}
			}

			sqlBuilder.append(")");

			int index = 1;

			conn = dcrmDS.getConnection();
			pstmt = conn.prepareStatement(sqlBuilder.toString());
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);

			for (String assetCompRowID : assetCompRowIDList) {
				pstmt.setString(index++, assetCompRowID);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				assetAttribList = new ArrayList<AssetAttributeBean>();

				do {
					AssetAttributeBean bean = new AssetAttributeBean();
					bean.setAttributeRowID(rs.getString("ROW_ID"));
					bean.setName(rs.getString("ATTR_NAME"));
					bean.setValue(rs.getString("DESC_TEXT"));
					bean.setAssetRowID(rs.getString("ASSET_ID"));

					assetAttribList.add(bean);
				} while (rs.next());

				responseBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				responseBean.setMsg(Constants.SUCCESS_MSG);
				responseBean.setAttributeList(assetAttribList.toArray(new AssetAttributeBean[assetAttribList.size()]));
			} else {
				String[] message = { "AssetAttribute", "CRM" };
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);

				responseBean.setCode(errorCodeResp.getErrorCode());
				responseBean.setMsg(errorCodeResp.getErrorMessage());
			}
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);

			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.clearList(assetAttribList);

			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);
			logger.logResponseDBClient(this.transID, responseBean.getCode(), startDAO);
		}

		return responseBean;
	}

	private String QUERY_PARENT_HUNTING_FLP = "SELECT NVL2(XA.DESC_TEXT, 'N', 'Y') AS IS_PILOT_NO "
			+ "FROM SIEBEL.S_ASSET_XA XA " 
			+ "INNER JOIN SIEBEL.S_ASSET AC ON AC.ROW_ID = XA.ASSET_ID "
			+ "INNER JOIN SIEBEL.S_ASSET AST ON AST.ROW_ID = AC.ROOT_ASSET_ID "
			+ "INNER JOIN SIEBEL.S_PROD_INT PC ON PC.ROW_ID = AC.PROD_ID "
			+ "LEFT JOIN SIEBEL.S_ORG_EXT BA ON AST.BILL_ACCNT_ID = BA.ROW_ID " 
			+ "WHERE AST.ROW_ID = ? "
			+ "AND AC.STATUS_CD = 'Active' " 
			+ "AND PC.ROW_ID IN (SELECT XM.PAR_ROW_ID "
			+ "FROM SIEBEL.S_PROD_INT_XM XM " 
			+ "INNER JOIN SIEBEL.S_PROD_INT PC2 ON PC2.ROW_ID = XM.ATTRIB_35 "
			+ "WHERE XM.NAME = 'HUNTING' " 
			+ "AND XM.PAR_ROW_ID = PC.ROW_ID) " 
			+ "AND XA.ATTR_NAME = 'Pilot No.'";

	public HuntingFLPRespBean getParentHuntingFLP(HuntingFLPReqBean huntingFLPReqBean) throws DatabaseException {
		HuntingFLPRespBean huntingFLPRespBean = new HuntingFLPRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<HuntingAssetBean> childAssetList = null;
		Calendar startDAO = null;

		try {
			startDAO = logger.logRequestDBClient(this.transID);

			huntingFLPRespBean.setIsHunting(Boolean.FALSE);
			huntingFLPRespBean.setIsPilotServiceID(Boolean.FALSE);

			int index = 1;

			conn = dcrmDS.getConnection();
			pstmt = conn.prepareStatement(QUERY_PARENT_HUNTING_FLP);
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);
			pstmt.setString(index++, huntingFLPReqBean.getPilotAssetRowID());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				huntingFLPRespBean.setIsHunting(Boolean.TRUE);

				if ("Y".equalsIgnoreCase(rs.getString("IS_PILOT_NO"))) {
					huntingFLPRespBean.setIsPilotServiceID(Boolean.TRUE);
				}
			}

			huntingFLPRespBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
			huntingFLPRespBean.setMsg(Constants.SUCCESS_MSG);
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);

			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.clearList(childAssetList);

			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);
			logger.logResponseDBClient(this.transID, huntingFLPRespBean.getCode(), startDAO);
		}

		return huntingFLPRespBean;
	}

	private String QUERY_CHILD_HUNTING = 
			"SELECT AST.ROW_ID, AST.SERIAL_NUM, BA.OU_NUM" 
			+ "FROM SIEBEL.S_ASSET_XA XA "
			+ "INNER JOIN SIEBEL.S_ASSET AC ON AC.ROW_ID = XA.ASSET_ID "
			+ "INNER JOIN SIEBEL.S_ASSET AST ON AST.ROW_ID = AC.ROOT_ASSET_ID "
			+ "INNER JOIN SIEBEL.S_PROD_INT PC ON PC.ROW_ID = AC.PROD_ID "
			+ "LEFT JOIN SIEBEL.S_ORG_EXT BA ON AST.BILL_ACCNT_ID = BA.ROW_ID " 
			+ "WHERE AC.STATUS_CD = 'Active' "
			+ "AND PC.ROW_ID IN (SELECT XM.PAR_ROW_ID " 
			+ "FROM SIEBEL.S_PROD_INT_XM XM "
			+ "INNER JOIN SIEBEL.S_PROD_INT PC2 ON PC2.ROW_ID = XM.ATTRIB_35 " 
			+ "WHERE XM.NAME = 'HUNTING' "
			+ "AND XM.PAR_ROW_ID = PC.ROW_ID) " 
			+ "AND XA.ATTR_NAME = 'Pilot No.' " 
			+ "AND XA.DESC_TEXT = ?";

	public HuntingFLPRespBean getChildHuntingFLP(HuntingFLPReqBean huntingFLPReqBean) throws DatabaseException {
		HuntingFLPRespBean huntingFLPRespBean = new HuntingFLPRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<HuntingAssetBean> childAssetList = null;
		Calendar startDAO = null;

		try {
			startDAO = logger.logRequestDBClient(this.transID);

			int index = 1;

			conn = dcrmDS.getConnection();
			pstmt = conn.prepareStatement(QUERY_CHILD_HUNTING);
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);
			pstmt.setString(index++, huntingFLPReqBean.getPilotServiceID());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				childAssetList = new ArrayList<HuntingAssetBean>();

				do {
					HuntingAssetBean bean = new HuntingAssetBean();
					bean.setAssetRowID(rs.getString("ROW_ID"));
					bean.setServiceID(rs.getString("SERIAL_NUM"));
					bean.setBan(rs.getString("OU_NUM"));

					childAssetList.add(bean);
				} while (rs.next());

				huntingFLPRespBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				huntingFLPRespBean.setMsg(Constants.SUCCESS_MSG);
				huntingFLPRespBean
						.setChildAssetList(childAssetList.toArray(new HuntingAssetBean[childAssetList.size()]));
			} else {
				String[] message = { "Child Hunting FLP", "CRM" };
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);

				huntingFLPRespBean.setCode(errorCodeResp.getErrorCode());
				huntingFLPRespBean.setMsg(errorCodeResp.getErrorMessage());
			}
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);

			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.clearList(childAssetList);

			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);
			logger.logResponseDBClient(this.transID, huntingFLPRespBean.getCode(), startDAO);
		}

		return huntingFLPRespBean;
	}

	private String QUERY_COVERGANCE_RALTION = 
			"SELECT AST.ROW_ID," 
					+ "       AST.SERIAL_NUM," 
					+ "       AST.ASSET_NUM,"
					+ "       AST.STATUS_CD AS STATUS," 
					+ "       REL.RELATION_TYPE_CD AS RALTION_TYPE,"
					+ "       BILL.OU_NUM AS BAN" 
					+ " FROM SIEBEL.S_ASSET AST"
					+ " INNER JOIN SIEBEL.S_ASSET_REL REL ON AST.ROW_ID = REL.ASSET_ID"
					+ " INNER JOIN SIEBEL.s_ORG_EXT BILL ON AST.BILL_ACCNT_ID = BILL.ROW_ID" 
					+ " WHERE REL.PAR_ASSET_ID = ?";

	public ConvergenceRelationRespBean getConverganceRelation(ConvergenceRelationReqBean coverganceRelationReqBean)
			throws DatabaseException {
		ConvergenceRelationRespBean coverganceRelationRespBean = new ConvergenceRelationRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ConvergenceRelationBean> coverganceRelationList = null;
		Calendar startDAO = null;

		try {
			startDAO = logger.logRequestDBClient(this.transID);

			int index = 1;

			conn = dcrmDS.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(QUERY_COVERGANCE_RALTION);

			if (this.validator.hasStringValue(coverganceRelationReqBean.getAssetRowID())) {
				sqlBuilder.append("AND R.ASSET_ID = ? ");
			}

			pstmt = conn.prepareStatement(sqlBuilder.toString());

			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);
			pstmt.setString(index++, coverganceRelationReqBean.getParAssetRowID());

			if (this.validator.hasStringValue(coverganceRelationReqBean.getAssetRowID())) {
				pstmt.setString(index++, coverganceRelationReqBean.getAssetRowID());
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				coverganceRelationList = new ArrayList<ConvergenceRelationBean>();

				do {
					ConvergenceRelationBean bean = new ConvergenceRelationBean();
					bean.setAssetRowID(rs.getString("ROW_ID"));
					bean.setAssetNo(rs.getString("ASSET_NUM"));
					bean.setServiceID(rs.getString("SERIAL_NUM"));
					bean.setStatus(rs.getString("STATUS"));
					bean.setRelationType(rs.getString("RALTION_TYPE"));
					bean.setBan(rs.getString("BAN"));

					coverganceRelationList.add(bean);
				} while (rs.next());

				coverganceRelationRespBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				coverganceRelationRespBean.setMsg(Constants.SUCCESS_MSG);
				coverganceRelationRespBean.setConvergenceRelationBean(
						coverganceRelationList.toArray(new ConvergenceRelationBean[coverganceRelationList.size()]));

			} else {
				String[] message = { "Convergance Relation", "CRM" };
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);

				coverganceRelationRespBean.setCode(errorCodeResp.getErrorCode());
				coverganceRelationRespBean.setMsg(errorCodeResp.getErrorMessage());
			}
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);

			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.clearList(coverganceRelationList);

			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);
			logger.logResponseDBClient(this.transID, coverganceRelationRespBean.getCode(), startDAO);
		}

		return coverganceRelationRespBean;
	}

	public CapMaxRespBean countCapMax(String assetRowID, Calendar cycleDate, String[] cpcCategoryCodeList)
			throws DatabaseException {

		CapMaxRespBean capMaxRespBean = new CapMaxRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<CapMaxBean> capMaxBeanList = null;
		Calendar startDAO = null;

		try {
			startDAO = logger.logRequestDBClient(this.transID);

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append(
					"SELECT AC.X_CATEGORY_CODE, COUNT(AC.X_CATEGORY_CODE) AS CURR_COUNT, SUM(AC.X_PRICE) AS CURR_PRICE ");
			sqlBuilder.append("FROM SIEBEL.S_ASSET AC ");
			sqlBuilder.append("WHERE AC.PAR_ASSET_ID IS NOT NULL ");
			sqlBuilder.append("AND AC.ROOT_ASSET_ID = ? ");
			sqlBuilder.append("AND AC.X_START_DT >= ? ");
			sqlBuilder.append("AND AC.X_CATEGORY_CODE IN (");

			for (int i = 0; i < cpcCategoryCodeList.length; i++) {
				if (i == 0) {
					sqlBuilder.append("?");
				} else {
					sqlBuilder.append(",?");
				}
			}

			sqlBuilder.append(") ");
			sqlBuilder.append("GROUP BY AC.X_CATEGORY_CODE");

			logger.info(this.transID, "SQL: " + sqlBuilder.toString());

			Calendar effectiveDate = (Calendar) cycleDate.clone();
			effectiveDate.add(Calendar.HOUR_OF_DAY, -7);

			capMaxBeanList = new ArrayList<CapMaxBean>();

			int index = 1;
			conn = dcrmDS.getConnection();
			pstmt = conn.prepareStatement(sqlBuilder.toString());
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);
			pstmt.setString(index++, assetRowID);
			pstmt.setTimestamp(index++, CalendarManager.calendarToTimestamp(effectiveDate));

			for (String cpcCategoryCode : cpcCategoryCodeList) {
				pstmt.setString(index++, cpcCategoryCode);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				CapMaxBean bean = new CapMaxBean();
				bean.setCpcCategoryCode(rs.getString("X_CATEGORY_CODE"));
				bean.setCurrentCount(rs.getInt("CURR_COUNT"));
				bean.setCurrentPrice(rs.getDouble("CURR_PRICE"));

				capMaxBeanList.add(bean);
			}

			if (capMaxBeanList.size() != cpcCategoryCodeList.length) {
				boolean isFound = false;

				for (String cpcCategoryCode : cpcCategoryCodeList) {
					isFound = false;

					for (CapMaxBean capMaxBean : capMaxBeanList) {
						if (capMaxBean.getCpcCategoryCode().equals(cpcCategoryCode)) {
							isFound = true;
							break;
						}
					}

					if (!isFound) {
						CapMaxBean bean = new CapMaxBean();
						bean.setCpcCategoryCode(cpcCategoryCode);
						bean.setCurrentCount(new Integer(0));
						bean.setCurrentPrice(new Double(0.00D));

						capMaxBeanList.add(bean);
					}
				}
			}

			capMaxRespBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
			capMaxRespBean.setMsg(Constants.SUCCESS_MSG);
			capMaxRespBean.setCapMaxBeanList(capMaxBeanList.toArray(new CapMaxBean[capMaxBeanList.size()]));
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);

			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.clearList(capMaxBeanList);

			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);
			logger.logResponseDBClient(this.transID, capMaxRespBean.getCode(), startDAO);
		}

		return capMaxRespBean;
	}

	private final String QUERY_LIVING = "(SELECT A.TYPE_OF_ADDRESS AS LIVING_STYLE, A.TYPE_OF_AREA AS LIVING_TYPE, C.CREATED_DATE "
			+ "FROM AREA A " 
			+ "INNER JOIN CHECKLIST C ON C.CHECKLIST_ID = A.CHECKLIST_ID " 
			+ "WHERE A.ADDRESS_ID = ? "
			+ "AND C.STATUS = 'done' " 
			+ "ORDER BY C.CREATED_DATE DESC " 
			+ "LIMIT 1) " 
			+ "UNION ALL "
			+ "(SELECT TYPE_OF_ADDRESS AS LIVING_STYLE, TYPE_OF_AREA AS LIVING_TYPE, CREATED_DATE " 
			+ "FROM HOUSE_TYPE "
			+ "WHERE ADDRESS_ID = ? " 
			+ "ORDER BY CREATED_DATE DESC " 
			+ "LIMIT 1) " 
			+ "ORDER BY CREATED_DATE DESC";

	public LivingRespBean getLiving(LivingReqBean livingReqBean) throws DatabaseException {
		LivingRespBean respBean = new LivingRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Calendar startDAO = null;

		try {
			startDAO = logger.logRequestDBClient(this.transID);

			conn = cklistDS.getConnection();
			pstmt = conn.prepareStatement(QUERY_LIVING);
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);
			pstmt.setString(1, livingReqBean.getQrunAddressID());
			pstmt.setString(2, livingReqBean.getQrunAddressID());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				respBean.setLivingStyle(rs.getString("LIVING_STYLE"));
				respBean.setLivingType(rs.getString("LIVING_TYPE"));
				respBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				respBean.setMsg(Constants.SUCCESS_MSG);
			} else {
				String[] message = { "Living", "CheckList" };
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);

				respBean.setCode(errorCodeResp.getErrorCode());
				respBean.setMsg(errorCodeResp.getErrorMessage());
			}
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);
			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);

			logger.logResponseDBClient(this.transID, respBean.getCode(), startDAO);
		}

		return respBean;
	}

	private final String QUERY_GETPRODUCTPARTNUMBYCCBSSOC = "select a.soc_name as socName from csm_offer@admprdlink a where soc_cd = ? ";

	public String getProductPartNumByCcbsSoc(String soc) throws DatabaseException {
		String respBean = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			StringBuilder sqlBuilder = new StringBuilder(QUERY_GETPRODUCTPARTNUMBYCCBSSOC);

			logger.debug(this.transID, "GetProductPartNumByCcbsSoc SQL: " + sqlBuilder.toString() + " Soc : " + soc);

			conn = dcrmbatchrecDS.getConnection();
			pstmt = conn.prepareStatement(sqlBuilder.toString());
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);
			pstmt.setString(1, soc);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				respBean = rs.getString("socName");
			}
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);
			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);
		}

		return respBean;
	}

	private final String QUERY_GETPRODUCTTYPEOFFERCODEBYPARTNUM = "select i.type as product_type ,i.vendr_part_num as offer_code "
			+ "from s_prod_int i " 
			+ "where 1=1 " 
			+ "and i.part_num = ? " 
			+ "and rownum = 1 ";

	public AssetCompBean getProductTypeOfferCodeByPartNum(String partNum) throws DatabaseException {
		AssetCompBean respBean = new AssetCompBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			StringBuilder sqlBuilder = new StringBuilder(QUERY_GETPRODUCTTYPEOFFERCODEBYPARTNUM);

			logger.debug(this.transID,
					"getProductTypeOfferCodeByPartNum SQL: " + sqlBuilder.toString() + " partNum : " + partNum);

			conn = dcrmDS.getConnection();
			pstmt = conn.prepareStatement(sqlBuilder.toString());
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);
			pstmt.setString(1, partNum);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				respBean.setProductType(rs.getString("product_type"));
				respBean.setOfferName(rs.getString("offer_code"));
			}
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);
			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);
		}

		return respBean;
	}

	private final String QUERY_PRODUCT_BY_CCBS_SOC = "SELECT PART_NUM, TYPE AS PRODUCT_TYPE, VENDR_PART_NUM AS OFFER_CODE "
			+ "FROM SIEBEL.S_PROD_INT " 
			+ "WHERE NAME LIKE ? ";

	public AssetCompRespBean getProductByCcbsSoc(String soc) throws DatabaseException {
		AssetCompRespBean assetCompRespBean = new AssetCompRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String socParam = "PRE" + soc + "-%";

			conn = dcrmDS.getConnection();
			pstmt = conn.prepareStatement(QUERY_PRODUCT_BY_CCBS_SOC);
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);
			pstmt.setString(1, socParam);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				AssetCompBean assetCompBean = new AssetCompBean();
				assetCompBean.setPartNum(rs.getString("PART_NUM"));
				assetCompBean.setProductType(rs.getString("PRODUCT_TYPE"));
				assetCompBean.setOfferName(rs.getString("OFFER_CODE"));

				AssetCompBean[] assetComponentList = { assetCompBean };

				assetCompRespBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				assetCompRespBean.setMsg(Constants.SUCCESS_MSG);
				assetCompRespBean.setAssetComponentList(assetComponentList);
			} else {
				String[] message = { "Product (" + socParam + ")", "CRM" };
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, message);
				assetCompRespBean.setCode(errorCodeResp.getErrorCode());
				assetCompRespBean.setMsg(errorCodeResp.getErrorMessage());
			}
		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);
			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);
		}

		return assetCompRespBean;
	}

	// 20230803
	private final String DELETE_COMPONENT = "DELETE FROM siebel.s_asset a WHERE a.row_id = ?";

	public DeleteComponentRespBean deleteAssetComponent(DeleteComponentReqBean deleteComponentReqBean)
			throws DatabaseException {
		DeleteComponentRespBean respBean = new DeleteComponentRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		// ResultSet rs = null;
		Calendar startDAO = null;

		// 20230804
		int rowsDeleted = 0;
		int countRowsAffects = 0;

		try {
			startDAO = logger.logRequestDBClient(this.transID);

			conn = olcrmDS.getConnection();
			pstmt = conn.prepareStatement(DELETE_COMPONENT);
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);
			for (int i = 0; i < deleteComponentReqBean.getAssetRowIDList().length; i++) {
				pstmt.setString(1, deleteComponentReqBean.getAssetRowIDList()[i]);

				rowsDeleted = pstmt.executeUpdate();

				// if (rowsDeleted == 0) {
				// System.out.println("No matching rows found to delete.");

				if (rowsDeleted >= 1) {
					countRowsAffects += rowsDeleted;
				}

				// 20230804 -- Need commit for DELETE AND UPSERT
				conn.commit();
			}
			// Always Success Except Throwable
			respBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
			respBean.setMsg(Constants.SUCCESS_MSG);

		} catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);
			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE,
					t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		} finally {
			// this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);

			respBean.setRowsAffected(countRowsAffects);

			logger.logResponseDBClient(this.transID, respBean.getCode(), startDAO);
		}

		return respBean;
	}
}