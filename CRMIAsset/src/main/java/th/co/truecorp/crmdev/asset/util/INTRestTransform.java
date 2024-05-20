package th.co.truecorp.crmdev.asset.util;

import th.co.truecorp.crmdev.util.db.config.CassandraConfig;

public class INTRestTransform {
	
	public static String generateAllPrepaidProfileList(String serviceId) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("{" + 
				"    \"pageSize\": \""+CassandraConfig.getVal("int.rest.getallprepaidprofilelist.pagesize")+"\"," + //100
				"    \"pageNumber\": \""+CassandraConfig.getVal("int.rest.getallprepaidprofilelist.pagenumber")+"\"," + //1
				"    \"searchList\": {" + 
				"        \"searchInfoArray\": [" + 
				"            {" + 
				"                \"type\": \"MSISDN\"," + 
				"                \"value\": \""+serviceId+"\"" + 
				"            }" + 
				"        ]" + 
				"    }" + 
				"}");
		return builder.toString();		 
	 }
}
