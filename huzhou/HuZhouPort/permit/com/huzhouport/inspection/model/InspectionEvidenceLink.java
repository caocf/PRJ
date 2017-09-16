package com.huzhouport.inspection.model;

public class InspectionEvidenceLink implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
	private int inspectionId;
	private int evidenceId;
	public int getInspectionId() {
		return inspectionId;
	}
	public void setInspectionId(int inspectionId) {
		this.inspectionId = inspectionId;
	}
	public int getEvidenceId() {
		return evidenceId;
	}
	public void setEvidenceId(int evidenceId) {
		this.evidenceId = evidenceId;
	}

}
