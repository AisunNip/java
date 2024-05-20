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
import th.co.truecorp.crmdev.asset.bean.AddressBean;
import th.co.truecorp.crmdev.asset.bean.AddressRespBean;
import th.co.truecorp.crmdev.asset.bean.Constants;
import th.co.truecorp.crmdev.asset.exception.DatabaseException;
import th.co.truecorp.crmdev.asset.util.AssetErrorCode;
import th.co.truecorp.crmdev.util.common.Validator;
import th.co.truecorp.crmdev.util.common.bean.ErrorCodeResp;
import th.co.truecorp.crmdev.util.logging.LogProductName;
import th.co.truecorp.crmdev.util.logging.LogSystem;

@Repository
public class AddressDAO extends BaseDAO {

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
	
	public AddressDAO() throws UnknownHostException {
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
	
	public AddressBean[] filterAddress(List<AddressBean> addressList, String accountRowID) {
		AddressBean[] resultAddressList = null;
		
		if (addressList != null && accountRowID != null) {
			resultAddressList = addressList.stream()
					  		.filter(addressBean -> accountRowID.equals(addressBean.getAccountRowID()))
					  		.toArray(AddressBean[]::new);
			
			if (resultAddressList.length == 0) {
				resultAddressList = null;
			}
		}
		
		return resultAddressList;
	}
	
	private final String QUERY_ADDRESS_LIST =
		"SELECT ADDR.ROW_ID"
		+ ", ADDR.ADDR AS STREET"
		+ ", ADDR.ADDR_NUM AS HOUSE_NO"
		+ ", ADDR.X_MOO"
		+ ", ADDR.ZIPCODE"
		+ ", ADDR.CITY AS PROVINCE"
		+ ", ADDR.X_KHET_AMPHUR AS KHET"
		+ ", ADDR.X_KHWANG_TUMBON AS KHWANG"
		+ ", ADDR.COUNTRY"
		+ ", ADDR.ADDR_LINE_3 AS ROOM"
		+ ", ADDR.X_FLOOR"
		+ ", ADDR.ADDR_LINE_4 AS BUILDING"
		+ ", ADDR.ADDR_LINE_5 AS SOI"
		+ ", ADDR.X_LATITUDE, ADDR.X_LONGITUDE"
		+ ", REL.RELATION_TYPE_CD AS ADDR_TYPE"
		+ ", REL.ACCNT_ID AS ACCOUNT_ROW_ID "
		+ "FROM SIEBEL.S_CON_ADDR REL "
		+ "INNER JOIN SIEBEL.S_ADDR_PER ADDR ON REL.ADDR_PER_ID = ADDR.ROW_ID " 
		+ "WHERE REL.ACCNT_ID IN ";
	
	public AddressRespBean getAddressList(List<String> accountRowIDList, String olprdDBFlag) throws DatabaseException {
		AddressRespBean addrRespBean = new AddressRespBean();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AddressBean> addressBeanList = null;
		Calendar startDAO = null;
		
		try {
			startDAO = logger.logRequestDBClient(this.transID);
			
			StringBuilder sqlBuilder = new StringBuilder(QUERY_ADDRESS_LIST);
			sqlBuilder.append("(");
			
			for (int i=0; i < accountRowIDList.size(); i++) {
				if (i == 0) {
					sqlBuilder.append("?");
				}
				else {
					sqlBuilder.append(",?");
				}
			}
			
			sqlBuilder.append(")");
			logger.debug(this.transID, "SQL: " + sqlBuilder.toString());
			
			int index = 1;
			
			if(this.validator.hasStringValue(olprdDBFlag) && "Y".equalsIgnoreCase(olprdDBFlag)) {
				conn = olcrmDS.getConnection();
			} else {
				conn = dcrmDS.getConnection();
			}	
			
			pstmt = conn.prepareStatement(sqlBuilder.toString());
			pstmt.setQueryTimeout(Constants.QUERY_TIMEOUT_IN_SEC);
			
			for (String addressRowID : accountRowIDList) {
				pstmt.setString(index++, addressRowID);
			}
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				addressBeanList = new ArrayList<AddressBean>();
				
				do {
					AddressBean addressBean = new AddressBean();
					addressBean.setAddressRowID(rs.getString("ROW_ID"));
					addressBean.setHouseNo(rs.getString("HOUSE_NO"));
					addressBean.setRoomNo(rs.getString("ROOM"));
					addressBean.setFloor(rs.getString("X_FLOOR"));
					addressBean.setBuilding(rs.getString("BUILDING"));
					addressBean.setMoo(rs.getString("X_MOO"));
					addressBean.setSoi(rs.getString("SOI"));
					addressBean.setStreet(rs.getString("STREET"));
					addressBean.setDistrict(rs.getString("KHET"));
					addressBean.setSubdistrict(rs.getString("KHWANG"));
					addressBean.setProvince(rs.getString("PROVINCE"));
					addressBean.setPostalCode(rs.getString("ZIPCODE"));
					addressBean.setCountry(rs.getString("COUNTRY"));
					addressBean.setLatitude(rs.getString("X_LATITUDE"));
					addressBean.setLongitude(rs.getString("X_LONGITUDE"));
					addressBean.setType(rs.getString("ADDR_TYPE"));
					addressBean.setAccountRowID(rs.getString("ACCOUNT_ROW_ID"));
					
					addressBeanList.add(addressBean);
				} while(rs.next());
				
				addrRespBean.setAddressBeanList(addressBeanList);
				addrRespBean.setCode(Constants.ErrorCode.SUCCESS_CODE);
				addrRespBean.setMsg(Constants.SUCCESS_MSG);
			}
			else {
				String[] message = {"Address", "CRM"};
				ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.DATA_NOT_FOUND
											, message);
				
				addrRespBean.setCode(errorCodeResp.getErrorCode());
				addrRespBean.setMsg(errorCodeResp.getErrorMessage());
			}
		}
		catch (Throwable t) {
			logger.error(this.transID, t.getMessage(), t);
			
			ErrorCodeResp errorCodeResp = this.assetErrorCode.generate(Constants.ErrorCode.CRM_DATABASE_CODE, t.getMessage());
			throw new DatabaseException(errorCodeResp.getErrorCode(), errorCodeResp.getErrorMessage());
		}
		finally {
			this.closeResultSet(rs);
			this.closeStatement(pstmt);
			this.closeConnection(conn);
			
			logger.logResponseDBClient(this.transID, addrRespBean.getCode(), startDAO);
		}
		
		return addrRespBean;
	}
}