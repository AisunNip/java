package th.co.truecorp.crmdev.asset.bean;

import java.util.Calendar;

import com.fasterxml.jackson.core.JsonProcessingException;

import th.co.truecorp.crmdev.util.common.JsonFacade;

public class DCBBean extends JsonFacade<DCBBean> {
	
	public DCBBean() {
		super(DCBBean.class);
	}
	
	@Override
	public String toString() {
		try {
			return this.objectToString(this);
		}
		catch (JsonProcessingException e) {
		}
		
		return null;
	}
	
	private String prefixTH;	
	private String fstnameTH;		
	private String lastnameTH;		
	private String prefixEN;		
	private String fstnameEN;		
	private String lastnameEN;	
	private Calendar birthDate;
	private String email;		
	private String flagKYC;		
	private String flagDOPA;
	private AddressBean address;	
	private String idNumber;
	private Calendar timeStamp;		
	private String token;		
	private String ipAddress;
	
	public String getPrefixTH() {
		return prefixTH;
	}

	public void setPrefixTH(String prefixTH) {
		this.prefixTH = prefixTH;
	}

	public String getFstnameTH() {
		return fstnameTH;
	}

	public void setFstnameTH(String fstnameTH) {
		this.fstnameTH = fstnameTH;
	}

	public String getLastnameTH() {
		return lastnameTH;
	}

	public void setLastnameTH(String lastnameTH) {
		this.lastnameTH = lastnameTH;
	}

	public String getPrefixEN() {
		return prefixEN;
	}

	public void setPrefixEN(String prefixEN) {
		this.prefixEN = prefixEN;
	}

	public String getFstnameEN() {
		return fstnameEN;
	}

	public void setFstnameEN(String fstnameEN) {
		this.fstnameEN = fstnameEN;
	}

	public String getLastnameEN() {
		return lastnameEN;
	}

	public void setLastnameEN(String lastnameEN) {
		this.lastnameEN = lastnameEN;
	}

	public Calendar getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Calendar birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFlagKYC() {
		return flagKYC;
	}

	public void setFlagKYC(String flagKYC) {
		this.flagKYC = flagKYC;
	}

	public String getFlagDOPA() {
		return flagDOPA;
	}

	public void setFlagDOPA(String flagDOPA) {
		this.flagDOPA = flagDOPA;
	}

	public AddressBean getAddress() {
		return address;
	}

	public void setAddress(AddressBean address) {
		this.address = address;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Calendar getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Calendar timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}		
}
