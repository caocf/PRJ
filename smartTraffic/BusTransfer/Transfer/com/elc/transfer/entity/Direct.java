package com.elc.transfer.entity;

/**
 * 直达缓存类
 * @author Administrator
 *
 */
public class Direct {
	private int start;
	private int end;
	private int line;
	private String startName;
	private String endName;
	private String lineName;
	
	public int getStart() {
		return start;
	}
	public int getEnd() {
		return end;
	}
	public int getLine() {
		return line;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public String getStartName() {
		return startName;
	}
	public void setStartName(String startName) {
		this.startName = startName;
	}
	public String getEndName() {
		return endName;
	}
	public void setEndName(String endName) {
		this.endName = endName;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
}
