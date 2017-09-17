package com.common.utils.uiinput.anotation.position;

import com.common.utils.uiinput.anotation.AnotationModel;
import com.common.utils.uiinput.anotation.other.LineWidth;

public class ModelPosition implements AnotationModel<Position, ModelPosition> {
	// 分组
	private int group;

	// 组名字
	private String groupname;

	// 分组排序
	private int order;

	// 用于自定义排序
	private int suborder;

	// 该字段表示该属性占有一行中的多少，只能取1或0.5，1表示占一行，0.5表示占一半
	private LineWidth linewidth;

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getSuborder() {
		return suborder;
	}

	public void setSuborder(int suborder) {
		this.suborder = suborder;
	}

	@Override
	public ModelPosition fromAnotation(Position an) {
		if (an != null) {
			this.setGroup(an.group());
			this.setGroupname(an.groupname());
			this.setLinewidth(an.linewidth());
			this.setOrder(an.order());
			this.setSuborder(an.suborder());
		}
		return this;
	}

	public LineWidth getLinewidth() {
		return linewidth;
	}

	public void setLinewidth(LineWidth linewidth) {
		this.linewidth = linewidth;
	}
}
