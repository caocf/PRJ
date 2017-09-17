package com.channel.bean;

import com.channel.model.hd.CHdHduanjcxx;

public class HangDuanDT {
	private CHdHduanjcxx hangduan;
	private String xzqh;

	public CHdHduanjcxx getHangduan() {
		return hangduan;
	}

	public void setHangduan(CHdHduanjcxx hangduan) {
		this.hangduan = hangduan;
	}

	public String getXzqh() {
		return xzqh;
	}

	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}

	public HangDuanDT() {
		super();
	}

	public HangDuanDT(CHdHduanjcxx hangduan, String xzqh) {
		super();
		this.hangduan = hangduan;
		this.xzqh = xzqh;
	}

}
