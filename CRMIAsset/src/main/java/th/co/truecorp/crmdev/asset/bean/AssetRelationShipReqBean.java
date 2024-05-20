package th.co.truecorp.crmdev.asset.bean;

public class AssetRelationShipReqBean {
	
	private String transID;
	private String parentFieldName;
	private String parentFieldValue;
	private String fieldName;
	private String fieldValue;
	private String relationshipType;
	private String operation;
	
	public AssetRelationShipReqBean() {
	}

	@Override
	public String toString() {
		return "AssetRelationShipReqBean [transID=" + transID + ", parentFieldName=" + parentFieldName
				+ ", parentFieldValue=" + parentFieldValue + ", fieldName=" + fieldName + ", fieldValue=" + fieldValue
				+ ", relationshipType=" + relationshipType + ", operation=" + operation + "]";
	}

	public String getTransID() {
		return transID;
	}

	public void setTransID(String transID) {
		this.transID = transID;
	}

	public String getParentFieldName() {
		return parentFieldName;
	}

	public void setParentFieldName(String parentFieldName) {
		this.parentFieldName = parentFieldName;
	}

	public String getParentFieldValue() {
		return parentFieldValue;
	}

	public void setParentFieldValue(String parentFieldValue) {
		this.parentFieldValue = parentFieldValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
}