package com.ruisi.vdop.service.frame;

import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.init.ExtXMLLoader;
import com.ruisi.ext.engine.view.exception.ExtConfigException;

/**
 * 对于报表、仪表盘、多维分析等工具生成的mv对象，直接从数据库中读取xml文件 
 * @author hq
 * @date 2015-5-14
 */
public class LoadXmlFromDB implements ExtXMLLoader {

	@Override
	public InputStream load(String mvId, String absolutePath, String xmlResource, ServletContext sctx)
			throws Exception {
		
		/*DaoHelper dao = (DaoHelper)SpringUtil.getApplicationContext(sctx).getBean("daoHelper");*/
		DaoHelper dao = (DaoHelper)WebApplicationContextUtils.getRequiredWebApplicationContext(sctx).getBean("daoHelper");
		
		List ls = dao.queryForList("select mv_content ctx from bi_demo_sys.mv_page_info where mv_id ='" +mvId+"'");
		System.out.println(ls);
		if(ls == null || ls.size() == 0){
			throw new ExtConfigException("id = " + mvId + " 的文件不存在。");
		}
		Map data = (Map)ls.get(0);
		Object pctx = data.get("ctx");
		if(pctx instanceof String){
			String pageInfo = (String)pctx;
			return IOUtils.toInputStream(pageInfo, "utf-8");
		}else if(pctx instanceof oracle.sql.CLOB){
			oracle.sql.CLOB clob = (oracle.sql.CLOB)pctx;
			Reader is = clob.getCharacterStream();
			String pageInfo = IOUtils.toString(is);
			is.close();
			return IOUtils.toInputStream(pageInfo, "utf-8");
		}
		throw new Exception("类型未定义....");
		
	 
		
	}

}
