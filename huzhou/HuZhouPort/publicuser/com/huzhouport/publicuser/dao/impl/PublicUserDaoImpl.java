package com.huzhouport.publicuser.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.publicuser.dao.PublicUserDao;
import com.huzhouport.publicuser.model.PublicUser;
import com.huzhouport.publicuser.model.ShipBinding;
import com.huzhouport.publicuser.model.ShipCheck;
import com.huzhouport.publicuser.model.ShipFile;
import com.huzhouport.publicuser.model.WharfBinding;
import com.huzhouport.publicuser.model.WharfCheck;

public class PublicUserDaoImpl extends BaseDaoImpl<PublicUser> implements
		PublicUserDao {
	@SuppressWarnings("unchecked")
	public List<PublicUser> FindPublicUserByName(String userName) throws Exception {
		String hql = "from PublicUser as pu where pu.userName='"+ userName+"'" ;
		return this.hibernateTemplate.find(hql);
	}
	@SuppressWarnings("unchecked")
	public List<ShipBinding> FindPublicUserByShipName(String shipNum,int shipId) throws Exception {
		String hql = "from ShipBinding as sb where sb.shipNum='"+ shipNum+"' and sb.shipId="+shipId ;
		return this.hibernateTemplate.find(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<WharfBinding> FindPublicUserByWharfName(String wharfNum) throws Exception {
		String hql = "from WharfBinding as wb where wb.wharfNum='"+ wharfNum+"'" ;
		return this.hibernateTemplate.find(hql);
	}
	@SuppressWarnings("unchecked")
	public List<ShipBinding> FindPublicUserByShip(int userId) throws Exception {
		String hql = "from ShipBinding as sb where sb.shipUser="+ userId ;
		return this.hibernateTemplate.find(hql);
	}
	@SuppressWarnings("unchecked")
	public List<WharfBinding> FindPublicUserByWharf(int userId) throws Exception {
		String hql = "from WharfBinding as wb where wb.wharfUser="+ userId ;
		return this.hibernateTemplate.find(hql);
	}
	
	public String DeleteWharfBinding(WharfBinding wb) throws Exception {
		this.hibernateTemplate.delete(wb);
		return null;
	}

	public String DeleteShipBinding(ShipBinding sb) throws Exception {
		this.hibernateTemplate.delete(sb);
		return null;
	}
	
	public String DeletePublicUser(int userId) throws Exception {
		PublicUser pu = new PublicUser();
		pu.setUserId(userId);
		this.hibernateTemplate.delete(pu);
		return null;
	}

	public int CountPublicUserByShip(PublicUser publicUser, String qcs)
			throws Exception {
		String hql = "SELECT COUNT(DISTINCT userId) from mm_PublicUser p, mm_ShipBinding s where s.ShipUser=p.UserId ";
		if (qcs != "") {
			hql += "and ( " + qcs+")";
		}
		return this.countSqlEForeignKey(publicUser, hql);
	}

	public List<?> ShowPublicUserPageByShip(PublicUser publicUser, String qcs,
			String sequence, int beginIndex, int pagesize) throws Exception {
		String hql = "select UserId,UserName,s.Status,group_concat(s.ShipNum),s.ShipId from mm_PublicUser p, mm_ShipBinding s where s.ShipUser=p.UserId ";
		if (qcs != "") {
			hql += " and ( " + qcs+")";
		}
		hql+=" group by UserName ";
		List<?> list = this.sqlQueryEForeignKey(publicUser, hql, beginIndex,
				pagesize);
		return list;
	}

	public int CountPublicUserByWharf(PublicUser publicUser, String qcs)
			throws Exception {
		String hql = "SELECT COUNT(DISTINCT userId) from mm_PublicUser p, mm_WharfBinding AS wb where wb.wharfUser=p.userId ";
		if (qcs != "") {
			hql += "and ( " + qcs+")";
		}
		return this.countSqlEForeignKey(publicUser, hql);
	}

	public List<?> ShowPublicUserPageByWharf(PublicUser publicUser, String qcs,
			String sequence, int beginIndex, int pagesize) throws Exception {
		String hql = "select UserId,UserName,group_concat(wb.wharfNum) from mm_PublicUser p, mm_WharfBinding wb where wb.wharfUser=p.UserId ";
		if (qcs != "") {
			hql += " and ( " + qcs+")";
		}
		hql+=" group by UserName ";
		List<?> list = this.sqlQueryEForeignKey(publicUser, hql, beginIndex,
				pagesize);
		return list;
	}
	public List<?> ShipCheckList() throws Exception{
		String hql="from ShipCheck";
		return this.hibernateTemplate.find(hql);
	}
	public List<?> WharfCheckList() throws Exception{
		String hql="from WharfCheck";
		return this.hibernateTemplate.find(hql);
	}
	//增加客户表
	public String AddPublicTable(PublicUser publicUser){
		try {
			this.hibernateTemplate.save(publicUser);
		} catch (Exception e) {
			return "false";
		}
		return "true";
	}
	//增加船舶绑定表
	public String AddShipBinding(ShipBinding shipBinding){
		try {
			this.hibernateTemplate.save(shipBinding);
		} catch (Exception e) {
			return "false";
		}
		return "true";
	}
	//增加码头绑定表
	public String AddWharfBinding(WharfBinding whartbinding){
		try {
			this.hibernateTemplate.save(whartbinding);
		} catch (Exception e) {
			return "false";
		}
		return  "true";
	}
	//增加码头绑定表
	public String AddShipCheckTable(ShipCheck shipBook){
		try {
			this.hibernateTemplate.save(shipBook);
		} catch (Exception e) {
			return "false";
		}
		return  "true";
	}
	//增加码头绑定表
	public String AddWharfCheckTable(WharfCheck wharfBook){
		try {
			this.hibernateTemplate.save(wharfBook);
		} catch (Exception e) {
			return "false";
		}
		return  "true";
	}
	//删除码头绑定表
	public String DeleteShipCheckTable(ShipCheck shipBook){
		try {
			this.hibernateTemplate.delete(shipBook);
		} catch (Exception e) {
			return "false";
		}
		return  "true";
	}
	//删除码头绑定表
	public String DeleteWharfCheckTable(WharfCheck wharfBook){
		try {
			this.hibernateTemplate.delete(wharfBook);
		} catch (Exception e) {
			return "false";
		}
		return  "true";
	}
	//船户信息
	public List<?> PublicInfoByShip(PublicUser publicUser) throws Exception{
		String hql="select pu,sb from PublicUser pu,ShipBinding sb where sb.shipUser=pu.userId and pu.userId="+publicUser.getUserId();
		return this.hibernateTemplate.find(hql);
	}
	//码头组信息
	public List<?> PublicInfoByWharf(PublicUser publicUser) throws Exception{
		String hql="select pu,wb from PublicUser pu,WharfBinding wb where wb.wharfUser=pu.userId  and pu.userId="+publicUser.getUserId();
		return this.hibernateTemplate.find(hql);
	}
	
	//修改公众信息
	public String UpdatePublicInfo(PublicUser publicUser) {
		try {
			this.hibernateTemplate.saveOrUpdate(publicUser);
		} catch (Exception e) {
			return "false";
		}
		return  "true";
	}
	//修改船舶绑定
	public String UpdateShipBinding(ShipBinding shipBinding) {
		try {
			this.hibernateTemplate.update(shipBinding);
		} catch (Exception e) {
			return "false";
		}
		return  "true";
	}
	//修改码头绑定
	public String UpdateWharfBinding(WharfBinding wharfBinding) {
		try {
			this.hibernateTemplate.update(wharfBinding);
		} catch (Exception e) {
			return "false";
		}
		return  "true";
	}
	//公众信息
	@SuppressWarnings("unchecked")
	public List<PublicUser> PublicUserInfoById(PublicUser publicUser) throws Exception{
		String hql="from PublicUser pu where pu.userId="+publicUser.getUserId();
		return this.hibernateTemplate.find(hql);
	}
	//修改密码
	public String CheckPsdById(PublicUser publicUser){
		try {
			Map<String, String> map = new HashMap<String, String>();
			Map<String, String> conditionMap = new HashMap<String, String>();
			map.put("psd",publicUser.getPsd());
			conditionMap.put("userId", String.valueOf(publicUser.getUserId()));
			this.modifyByConditions(publicUser,map,conditionMap,null);
		} catch (Exception e) {
			return "false";
		}
		return  "true";
	}
	//登陆
	public String LoginToSaveTime(PublicUser publicUser){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格
		try {
			Map<String, String> map = new HashMap<String, String>();
			Map<String, String> conditionMap = new HashMap<String, String>();
			map.put("loginTime",df.format(new Date()));
			conditionMap.put("userId", String.valueOf(publicUser.getUserId()));
			this.modifyByConditions(publicUser,map,conditionMap,null);
		} catch (Exception e) {
			return "false";
		}
		return  "true";
	}
	public List<?> publicUserById(PublicUser publicUser) throws Exception {
		String sql = "select ShipNum,group_concat(concat(sf.fileName) separator ';'),CONVERT(group_concat(sf.fileId separator ';')USING utf8),CONVERT(group_concat(sf.filePath separator ';')USING utf8),s.shipId " +
				"from mm_ShipBinding s left join mm_shipfile sf on s.ShipId=sf.shipId where s.ShipUser='"+publicUser.getUserId()+"' group by ShipNum";	
		return this.sqlForeignKey(sql);
	}
	public List<?> publicUserByIdWharf(PublicUser publicUser) throws Exception {
		String sql = "select wharfNum,wharfNumber,group_concat(concat(wf.fileName) separator ';'),CONVERT(group_concat(wf.fileId separator ';')USING utf8),CONVERT(group_concat(wf.filePath separator ';')USING utf8),w.wharfId " +
				"from mm_WharfBinding w left join mm_wharffile wf on w.WharfId=wf.wharfId where w.wharfUser='"+publicUser.getUserId()+"' group by wharfNum,wharfNumber";	
		return this.sqlForeignKey(sql);
	}
	//修改bindName
	public String UpdateBindName(PublicUser publicUser){
		String hql="update PublicUser p set p.bindName='"+publicUser.getBindName()+"' where p.userId="+publicUser.getUserId();
		this.hibernateTemplate.bulkUpdate(hql);
		return null;
	}
	public List<PublicUser> FindPublicUserByStatus(String name) throws Exception {
		String hql = "from PublicUser as pu where pu.userName='"+ name+"'" ;
		return this.hibernateTemplate.find(hql);		
	}
	public List<ShipBinding> publicUserId(int id) throws Exception 
	{
		String hql = "from ShipBinding as pu where pu.shipId='"+ id+"'" ;
		return this.hibernateTemplate.find(hql);		
	}
	public int AddPublicUser(PublicUser publicUser) 
	{
		int id;
		
		try {
			id=(Integer) this.hibernateTemplate.save(publicUser);
		} catch (Exception e) {
			return 0;
		}
		return id;
	}
	
	public int AddShipBind(ShipBinding shipBinding)
	{
		int id;
		
		try {
			id=(Integer) this.hibernateTemplate.save(shipBinding);
		} catch (Exception e) {
			return 0;
		}
		return id;
	}
	public int AddShipFile(ShipFile sf) 
	{
		int id;
		
		try {
			id=(Integer) this.hibernateTemplate.save(sf);
		} catch (Exception e) {
			return 0;
		}
		return id;
	}
	
}
