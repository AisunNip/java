package th.co.truecorp.crmdev.asset.bean;

public class DeleteComponentRespBean extends ResponseBean {
	private int rowsAffected;

	public int getRowsAffected() {
		return rowsAffected;
	}

	public void setRowsAffected(int rowsAffected) {
		this.rowsAffected = rowsAffected;
	}
}
