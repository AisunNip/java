package th.co.truecorp.crmdev.asset.bean;

import java.util.Arrays;

public class ConvergenceRelationRespBean extends ResponseBean {

	ConvergenceRelationBean[] convergenceRelationBean;

	@Override
	public String toString() {
		return "ConvergenceRelationRespBean [convergenceRelationBean=" + Arrays.toString(convergenceRelationBean)
				+ ", toString()=" + super.toString() + ", getCode()=" + getCode() + ", getMsg()=" + getMsg()
				+ ", getTransID()=" + getTransID() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

	public ConvergenceRelationBean[] getConvergenceRelationBean() {
		return convergenceRelationBean;
	}

	public void setConvergenceRelationBean(ConvergenceRelationBean[] convergenceRelationBean) {
		this.convergenceRelationBean = convergenceRelationBean;
	}	
}
