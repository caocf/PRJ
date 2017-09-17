package com.channel.bean;

public class ZxgcForm implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer zxgcid;
	private String name;
	private double investment;
	private String progress;
	private double amount;
	private double totalamount;
	private String remark;

	public Integer getZxgcid() {
		return zxgcid;
	}

	public void setZxgcid(Integer zxgcid) {
		this.zxgcid = zxgcid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getInvestment() {
		return investment;
	}

	public void setInvestment(double investment) {
		this.investment = investment;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(double totalamount) {
		this.totalamount = totalamount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ZxgcForm() {
		super();
	}

	public ZxgcForm(Integer zxgcid, String name, double investment,
			String progress, double amount, double totalamount, String remark) {
		super();
		this.zxgcid = zxgcid;
		this.name = name;
		this.investment = investment;
		this.progress = progress;
		this.amount = amount;
		this.totalamount = totalamount;
		this.remark = remark;
	}

}
