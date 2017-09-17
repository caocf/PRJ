package com.elc.transfer.cache;

import com.elc.transfer.entity.Request;

/**
 * 
 * @author Administrator
 *
 */
public interface IResultCache {
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public CacheResult queryResutlFromCache(Request request);
}
