package com.gh.modol;

public class WaterInfo {
	/*
	 * 水文信息
	 * */
	private Long id;
	private String updatetime;
	private String whatchpoint;
	private double swsz;
	private double syl;
	private int ryl;
	private int cjjx;
	private int cwj;
	private String region;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getWhatchpoint() {
		return whatchpoint;
	}
	public void setWhatchpoint(String whatchpoint) {
		this.whatchpoint = whatchpoint;
	}
	public double getSwsz() {
		return swsz;
	}
	public void setSwsz(double swsz) {
		this.swsz = swsz;
	}
	public double getSyl() {
		return syl;
	}
	public void setSyl(double syl) {
		this.syl = syl;
	}
	public int getRyl() {
		return ryl;
	}
	public void setRyl(int ryl) {
		this.ryl = ryl;
	}
	public int getCjjx() {
		return cjjx;
	}
	public void setCjjx(int cjjx) {
		this.cjjx = cjjx;
	}
	public int getCwj() {
		return cwj;
	}
	public void setCwj(int cwj) {
		this.cwj = cwj;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

}
