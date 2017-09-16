package com.zjport.supervise.model;

import java.sql.Timestamp;

public class TWarnLog
{

	private Integer id;
	private String area;
	private String zwcm;
	private int type; //报警类型 1危险品船舶驶入 2船舶状态异常
	private Timestamp createtime;

	private Integer peccancy; //违章
	private Integer overdue; //证书过期
	private Integer arrearage; //缴费

	/** default constructor */
	public TWarnLog()
	{
	}

	public TWarnLog(String area, String zwcm, int type, Timestamp createtime, Integer peccancy, Integer overdue, Integer arrearage) {
		this.area = area;
		this.zwcm = zwcm;
		this.type = type;
		this.createtime = createtime;
		this.peccancy = peccancy;
		this.overdue = overdue;
		this.arrearage = arrearage;
	}

	/** full constructor */

	public Integer getId()
	{
		return this.id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getArea()
	{
		return this.area;
	}

	public void setArea(String area)
	{
		this.area = area;
	}

	public String getZwcm()
	{
		return this.zwcm;
	}

	public void setZwcm(String zwcm)
	{
		this.zwcm = zwcm;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Timestamp getCreatetime()
	{
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime)
	{
		this.createtime = createtime;
	}

	public Integer getPeccancy() {
		return peccancy;
	}

	public void setPeccancy(Integer peccancy) {
		this.peccancy = peccancy;
	}

	public Integer getOverdue() {
		return overdue;
	}

	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}

	public Integer getArrearage() {
		return arrearage;
	}

	public void setArrearage(Integer arrearage) {
		this.arrearage = arrearage;
	}
}