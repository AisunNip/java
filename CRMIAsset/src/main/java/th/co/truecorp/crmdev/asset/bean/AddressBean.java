package th.co.truecorp.crmdev.asset.bean;

public class AddressBean {

	private String addressRowID;
	private String type;
	private String houseNo;
	private String roomNo;
	private String floor;
	private String building;
	private String moo;
	private String subSoi;
	private String soi;
	private String street;
	private String district;
	private String subdistrict;
	private String province;
	private String postalCode;
	private String country;
	private String latitude;
	private String longitude;
	private String accountRowID;
	
	private String typeOfAccommodation;
	
	public AddressBean() {
	}

	@Override
	public String toString() {
		return "AddressBean [addressRowID=" + addressRowID + ", type=" + type + ", houseNo=" + houseNo + ", roomNo="
				+ roomNo + ", floor=" + floor + ", building=" + building + ", moo=" + moo + ", subSoi=" + subSoi
				+ ", soi=" + soi + ", street=" + street + ", district=" + district + ", subdistrict=" + subdistrict
				+ ", province=" + province + ", postalCode=" + postalCode + ", country=" + country + ", latitude="
				+ latitude + ", longitude=" + longitude + ", accountRowID=" + accountRowID + "]";
	}

	public String getAddressRowID() {
		return addressRowID;
	}

	public void setAddressRowID(String addressRowID) {
		this.addressRowID = addressRowID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getMoo() {
		return moo;
	}

	public void setMoo(String moo) {
		this.moo = moo;
	}

	public String getSoi() {
		return soi;
	}

	public void setSoi(String soi) {
		this.soi = soi;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getSubdistrict() {
		return subdistrict;
	}

	public void setSubdistrict(String subdistrict) {
		this.subdistrict = subdistrict;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAccountRowID() {
		return accountRowID;
	}

	public void setAccountRowID(String accountRowID) {
		this.accountRowID = accountRowID;
	}

	public String getSubSoi() {
		return subSoi;
	}

	public void setSubSoi(String subSoi) {
		this.subSoi = subSoi;
	}

	public String getTypeOfAccommodation() {
		return typeOfAccommodation;
	}

	public void setTypeOfAccommodation(String typeOfAccommodation) {
		this.typeOfAccommodation = typeOfAccommodation;
	}
	
}