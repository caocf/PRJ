package com.huzhouport.log.service;
import java.io.File;
import java.util.List;

import com.huzhouport.log.model.Log;
import com.huzhouport.log.model.PageModel;





public interface Server {
	
	
	
	
	
	
	
	
	
	
	public PageModel<Log> findByScrollServer(int currentPage,int maxPage ,String action); //日志分页
	public void delete(String id); //日志删除
	public PageModel<Log> findByScrollServer1(int currentPage,int maxPage ,String action,String loguser,String logtime,String logcontent,String stylename);
	
	
//	
//	public PageModel<Log> findByScrollServer1(int currentPage,int maxPage,String styleName ,String action);
//	public PageModel<Log> findByScrollServer2(int currentPage,int maxPage,String logUser ,String action);
//	public PageModel<Log> findByScrollServer3(int currentPage,int maxPage,String styleName,String logUser ,String action);
//	public void update(int	Individuation  ,int userid);
//	public void delete(String id);

}
