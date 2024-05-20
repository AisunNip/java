package th.co.truecorp.crmdev.asset.bean;

import th.co.truecorp.crmdev.util.db.mapping.MappingBean;

public class MappingRespBean extends ResponseBean {

	private MappingBean[] mappingList;

	public MappingRespBean() {
	}

	public MappingBean[] getMappingList() {
		return mappingList;
	}

	public void setMappingList(MappingBean[] mappingList) {
		this.mappingList = mappingList;
	}
}