package th.co.truecorp.crmdev.asset.bean;

import com.fasterxml.jackson.core.JsonProcessingException;

import th.co.truecorp.crmdev.util.common.JsonFacade;

public class DeleteComponentReqBean extends JsonFacade<DeleteComponentReqBean>{
	private String[] assetRowIDList;
	
	public DeleteComponentReqBean() {
		super(DeleteComponentReqBean.class);
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

	public String[] getAssetRowIDList() {
		return assetRowIDList;
	}

	public void setAssetRowIDList(String[] assetRowIDList) {
		this.assetRowIDList = assetRowIDList;
	}
}
