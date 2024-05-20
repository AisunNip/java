package th.co.truecorp.crmdev.asset.dao;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import th.co.truecorp.crmdev.asset.AssetLogger;
import th.co.truecorp.crmdev.asset.bean.Constants;
import th.co.truecorp.crmdev.asset.bean.CustomerRowIDRespBean;
import th.co.truecorp.crmdev.asset.bean.MappingRespBean;
import th.co.truecorp.crmdev.asset.exception.DatabaseException;
import th.co.truecorp.crmdev.asset.util.AssetErrorCode;
import th.co.truecorp.crmdev.util.common.Validator;
import th.co.truecorp.crmdev.util.common.bean.ErrorCodeResp;
import th.co.truecorp.crmdev.util.db.exception.MappingFailedException;
import th.co.truecorp.crmdev.util.db.mapping.CassandarMapping;
import th.co.truecorp.crmdev.util.db.mapping.MappingBean;
import th.co.truecorp.crmdev.util.logging.LogProductName;
import th.co.truecorp.crmdev.util.logging.LogSystem;

@Repository
public class AccountDAO extends BaseDAO {

	@Qualifier("dcrmDS")
	@Autowired
	private DataSource dcrmDS;
	
	@Qualifier("olcrmDS")
	@Autowired
	private DataSource olcrmDS;
	
	private String transID;
	private AssetLogger logger;
	private AssetErrorCode assetErrorCode;
	private Validator validator;
	
	public AccountDAO() throws UnknownHostException {
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
	
	private final String QUERY_CUST_ROW_ID_LIST =
		"SELECT * "
		+ "FROM (SELECT CA2.ROW_ID FROM SIEBEL.S_ORG_EXT CA2 "
		+ "INNER JOIN SIEBEL.S_CONTACT CON2 ON CA2.LOC = CON2.PERSON_UID "
		+ "INNER JOIN SIEBEL.S_CONTACT_XM CONID2 ON CON2.X_PR_ID = CONID2.ROW_ID "
		+ "WHERE CA2.ACCNT_TYPE_CD = 'Customer' AND CA2.OU_TYPE_CD = 'Individual' AND CONID2.ATTRIB_03 = ? "
		+ "UNION "
		+ "SELECT CA2.ROW_ID FROM SIEBEL.S_ORG_EXT CA2 "
		+ "WHERE CA2.ACCNT_TYPE_CD = 'Customer' AND CA2.OU_TYPE_CD = 'Business' AND CA2.X_ID_NUM = ?) TMP "
		+ "WHERE ROWNUM <= ?";
	
	public CustomerRowIDRespBean getCustomerRowIDList(String idNo) throws DatabaseException {
		return this.getCustomerRowIDList(idNo, null);
	}
	
	public CustomerRowIDRespBean getCustomerRowIDList(String idNo, String olprdDBFlag) throws DatabaseException {
		CustomerRowIDRespBean respBean = new CustomerRowIDRespBean();
		List<String> customerRowIDList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Calendar startDAO = null;
		
		try {
			startDAO = logger.logRequestDBClient(this.transID);
			
			logger.debug(this.transID, "SQL: " + QUERY_CUST_ROW_ID_LIST);
			
			int index = 1;

			if(this.validator.hasStringValue(olprdDBFlag) && "Y".equalsIgnoreCase(olprdDBFlag)) {
				conn = olcrmDS.getConnection();
			} else {
				conn = dcrmDS.getConnection();
			}	
			
			pstmt = conn.prepareStatement(QUERY_CUST_ROW_ID_LIST);
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);
			pstmt.setString(index++, idNo);
			pstmt.setString(index++, idNo);
			pstmt.setInt(index++, Constants.LIMIT_MAX_EXPRESSION + 1);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				customerRowIDList = new ArrayList<String>();
				
				do {
					customerRowIDList.add(rs.getString("ROW_ID"));
				} while(rs.next());
				
				respBean.setCustomerRowIDList(customerRowIDList.toArray(new String[customerRowIDList.size()]));
				respBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				respBean.setMsg(Constants.SUCCESS_MSG);
			}
			else {
				String[] message = {"CustomerAccount", "CRM"};
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND
											, message);
				
				respBean.setCode(errorCodeResp.getErrorCode());
				respBean.setMsg(errorCodeResp.getErrorMessage());
			}
		}
		catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);
			
			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE, t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		}
		finally {
			this.clearList(customerRowIDList);
			
			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);
			
			logger.logResponseDBClient(this.transID, respBean.getCode(), startDAO);
		}
		
		return respBean;
	}
	
	public MappingRespBean getMappingIDType() throws DatabaseException {
		MappingRespBean mappingRespBean = new MappingRespBean();
		Calendar startDAO = null;
		String type = "IDTYPE";
		
		try {
			startDAO = logger.logRequestDBClient(this.transID);
			
			MappingBean[] mappingList = CassandarMapping.getMapping(type);
			
			mappingRespBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
			mappingRespBean.setMsg(Constants.SUCCESS_MSG);
			mappingRespBean.setMappingList(mappingList);
		}
		catch (MappingFailedException mapEx) {
			logger.error(this.transID, mapEx.getMessage(), mapEx);
			
			String[] errorMsg = {"Mapping (Type=" + type + ")", "CRM"};
			
			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND, errorMsg);
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		}
		catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);
			
			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE, t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		}
		finally {
			logger.logResponseDBClient(this.transID, mappingRespBean.getCode(), startDAO);
		}
		
		return mappingRespBean;
	}
}