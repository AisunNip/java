package th.co.truecorp.crmdev.asset.util;

public class StringUtil {

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isBlankValue(Object object) {
		if (object == null)
			return true;
		
		if (object instanceof String) {
			String objString = (String) object;
			
			if (objString == null || objString.length() == 0 || "".equals(objString.trim())) {
				return true;
			}
			else {
				if ("?".equalsIgnoreCase(objString.trim()))
					return true;
			}
		}
		
		return false;
	}

}