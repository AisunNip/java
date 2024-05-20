package th.co.truecorp.crmdev.asset.dao;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import th.co.truecorp.crmdev.util.db.CassandraTransaction;

public class CassandraTrans {

	public static String getConfigVal(String key) {
		ResultSet rs = null;
		String value = "";
		
		try {
			Session session = CassandraTransaction.getSessionInstance();
			
			PreparedStatement configPstm = session.prepare("select value from config where key = ?");
			
			rs = session.execute(configPstm.bind(key));
			
			Row row = rs.one();
			
			if (row != null) {
				value = row.getString("value");
			}
		}
		finally {
			rs = null;
		}
		
		return value;
	}
}