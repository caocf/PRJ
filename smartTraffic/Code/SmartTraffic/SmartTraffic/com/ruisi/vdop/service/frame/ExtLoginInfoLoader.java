package com.ruisi.vdop.service.frame;

import java.util.HashMap;
import java.util.Map;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.service.loginuser.LoginUserInfoLoader;
import com.ruisi.ext.engine.wrapper.ExtRequest;

/**
 * ext 获取登录信息的方法
 * @author hq
 * @date Mar 25, 2010
 */
public class ExtLoginInfoLoader  implements LoginUserInfoLoader {

	public String getUserId() {
		return null;
	}

	public Map<String, Object> loadUserInfo(ExtRequest request, DaoHelper dao) {
		
		Map<String, Object> m = new HashMap();
		
		return m;
	}

}
