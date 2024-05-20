package th.co.truecorp.crmdev.asset.util;

import th.co.truecorp.crmdev.asset.bean.SiebelAuthorizationBean;
import th.co.truecorp.crmdev.asset.exception.RequiredFieldException;
import th.co.truecorp.crmdev.util.common.TextEncoding;
import th.co.truecorp.crmdev.util.crypto.Cryptography;

public class AuthenticationUtil {

	public SiebelAuthorizationBean getSiebelAuthorization(String siebelAuthen) throws Exception {
		SiebelAuthorizationBean siebelAuthorizeBean = null;

		if (siebelAuthen != null && !"".equals(siebelAuthen)) {
			String decodedSiebelAuthor = TextEncoding.decodeBASE64(siebelAuthen);
			String[] authorParts = decodedSiebelAuthor.split("\\:");

			Cryptography crypto = new Cryptography();

			siebelAuthorizeBean = new SiebelAuthorizationBean();
			siebelAuthorizeBean.setSiebelUserName(authorParts[0]);
			siebelAuthorizeBean.setSiebelPassword(crypto.decryption(authorParts[1], null));
		}
		else {
			throw new RequiredFieldException("Required SiebelAuthorization");
		}
		
		return siebelAuthorizeBean;
	}

	public static void main(String[] args) throws Exception {
		Cryptography crypto = new Cryptography();
		String username = "PSAADMIN";
		String password = "PSAADMIN";
		String password_encrypt = crypto.encryption(password).getEncryptedData();
		System.out.println("username : " + username);
		System.out.println("password : " + password);
		System.out.println("password_encrypt : " + password_encrypt);

		String s1 = username + ":" + password_encrypt;
		String siebelAuthorization = TextEncoding.encodeBASE64(s1);
		System.out.println("SiebelAuthorization : " + siebelAuthorization);

		AuthenticationUtil a = new AuthenticationUtil();
		SiebelAuthorizationBean s = a.getSiebelAuthorization(siebelAuthorization);
		System.out.println("Check decncrypt : " + s.getSiebelUserName() + " " + s.getSiebelPassword());
	}
}