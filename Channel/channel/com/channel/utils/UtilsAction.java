package com.channel.utils;

import javax.annotation.Resource;

import com.common.action.BaseAction;

public class UtilsAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8912763735206107266L;

	@Resource(name = "utilsservice")
	private UtilsService utilsService;

	public String test() {
		this.utilsService.test();
		return SUCCESS;
	}
}
