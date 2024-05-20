package th.co.truecorp.crmdev.asset.dao;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import th.co.truecorp.crmdev.asset.AssetLogger;
import th.co.truecorp.crmdev.asset.bean.Constants;
import th.co.truecorp.crmdev.asset.bean.ResponseBean;
import th.co.truecorp.crmdev.asset.exception.DatabaseException;
import th.co.truecorp.crmdev.asset.util.AssetErrorCode;
import th.co.truecorp.crmdev.util.common.bean.ErrorCodeResp;
import th.co.truecorp.crmdev.util.logging.LogProductName;
import th.co.truecorp.crmdev.util.logging.LogSystem;

@Repository
public class MonitorDAO extends BaseDAO {

	@Qualifier("dcrmDS")
	@Autowired
	private DataSource dcrmDS;
	
	private AssetLogger logger;
	private AssetErrorCode assetErrorCode;
	
	private final String QUERY_DCRM = "SELECT 1 FROM DUAL";
	
	public MonitorDAO() throws UnknownHostException {
		this.logger = new AssetLogger(LogProductName.All, LogSystem.CRM_INBOUND, LogSystem.CRM_DATABASE);
		this.assetErrorCode = new AssetErrorCode();
	}
	
	public ResponseBean monitorDB(String transID) throws DatabaseException {
		ResponseBean respBean = new ResponseBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Calendar startDAO = null;
		
		try {
			startDAO = logger.logRequestDBClient(transID);
			
			conn = dcrmDS.getConnection();
			pstmt = conn.prepareStatement(QUERY_DCRM);
			pstmt.setQueryTimeout(6);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				respBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				respBean.setMsg(Constants.SUCCESS_MSG);
			}
			else {
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE, "Query Oracle DB Fail");
				
				respBean.setCode(errorCodeResp.getErrorCode());
				respBean.setMsg(errorCodeResp.getErrorMessage());
			}
		}
		catch (Throwable t) {
			logger.error(transID, t.getMessage(), t);
			
			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE, t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		}
		finally {
			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);
			
			logger.logResponseDBClient(transID, respBean.getCode(), startDAO);
		}
		
		return respBean;
	}
}