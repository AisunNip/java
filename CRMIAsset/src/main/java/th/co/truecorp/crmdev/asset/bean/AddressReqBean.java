package th.co.truecorp.crmdev.asset.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AddressReqBean {

	private String country;
	private String postalCode;
	private String province;
	private String street;
	private String building;
	private String floor;
	private String house;
	private String khet;
	private String khwang;
	private String moo;
	private String room;
	private String soi;
	private String type;
	private Double latitude;
	private Double longitude;
	private String qAddressId;
	private String qBuildingId;
	private String rowID;
	private String addressName;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getKhet() {
		return khet;
	}

	public void setKhet(String khet) {
		this.khet = khet;
	}

	public String getKhwang() {
		return khwang;
	}

	public void setKhwang(String khwang) {
		this.khwang = khwang;
	}

	public String getMoo() {
		return moo;
	}

	public void setMoo(String moo) {
		this.moo = moo;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getSoi() {
		return soi;
	}

	public void setSoi(String soi) {
		this.soi = soi;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getqAddressId() {
		return qAddressId;
	}

	public void setqAddressId(String qAddressId) {
		this.qAddressId = qAddressId;
	}

	public String getqBuildingId() {
		return qBuildingId;
	}

	public void setqBuildingId(String qBuildingId) {
		this.qBuildingId = qBuildingId;
	}

	public String getRowID() {
		return rowID;
	}

	public void setRowID(String rowID) {
		this.rowID = rowID;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

}
