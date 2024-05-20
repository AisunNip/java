package th.co.truecorp.crmdev.asset.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import th.co.truecorp.crmdev.util.common.CalendarManager;

public class BaseDAO {
	
	public String convertToSQLPaging(String sql, String condition, Integer pageNo, Integer pageSize) {
		int startRecord = (pageNo - 1) * pageSize + 1;
		int endRecord = pageNo * pageSize;
		
		StringBuilder sqlPaging = new StringBuilder();
		sqlPaging.append("SELECT DATA2.* ");
		sqlPaging.append("FROM (SELECT ROWNUM MYNUM, DATA1.* ");
		sqlPaging.append("FROM (");
		sqlPaging.append(sql);
		sqlPaging.append(") DATA1 ");
		sqlPaging.append("WHERE ");
		
		if (condition != null && !"".equals(condition)) {
			sqlPaging.append(condition);
			sqlPaging.append(" AND ROWNUM <= ");
			sqlPaging.append(endRecord);
		}
		else {
			sqlPaging.append("ROWNUM <= ");
			sqlPaging.append(endRecord);
		}
		
		sqlPaging.append(") DATA2 WHERE MYNUM >= ");
		sqlPaging.append(startRecord);
		
		return sqlPaging.toString();
	}

	public Calendar getCalendar(ResultSet rs, String fieldName) throws SQLException {
		return CalendarManager.timestampToCalendar(rs.getTimestamp(fieldName, CalendarManager.getEngCalendar())
				, CalendarManager.LOCALE_EN);
	}
	
	public Calendar getSiebelCalendar(ResultSet rs, String fieldName) throws SQLException {
		Calendar cal = CalendarManager.timestampToCalendar(rs.getTimestamp(fieldName, CalendarManager.getEngCalendar())
						, CalendarManager.LOCALE_EN);
		
		if (cal != null) {
			cal.add(Calendar.HOUR_OF_DAY, 7);
		}
		
		return cal;
	}
	
	public void clearList(List<?> objList) {
		if (objList != null) {
			objList.clear();
			objList = null;
		}
	}
	
	public boolean closeResultSet(ResultSet rs) {
		boolean isClose = false;

		try {
			if (rs != null) {
				rs.close();
				rs = null;
				isClose = true;
			}
		} catch (Exception e) {
		}

		return isClose;
	}

	public boolean closeStatement(PreparedStatement pstmt) {
		boolean isClose = false;

		try {
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
				isClose = true;
			}
		} catch (Exception e) {
		}

		return isClose;
	}

	public boolean closeConnection(Connection conn) {
		boolean isClose = false;

		try {
			if (conn != null) {
				conn.close();
				conn = null;
				isClose = true;
			}
		} catch (Exception e) {
		}

		return isClose;
	}
}