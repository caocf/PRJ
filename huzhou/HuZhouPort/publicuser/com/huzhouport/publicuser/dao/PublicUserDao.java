package com.huzhouport.publicuser.dao;

import java.util.List;

import com.huzhouport.common.dao.BaseDao;
import com.huzhouport.publicuser.model.PublicUser;
import com.huzhouport.publicuser.model.ShipBinding;
import com.huzhouport.publicuser.model.ShipCheck;
import com.huzhouport.publicuser.model.ShipFile;
import com.huzhouport.publicuser.model.WharfBinding;
import com.huzhouport.publicuser.model.WharfCheck;

public interface PublicUserDao extends BaseDao<PublicUser>{
	public String DeletePublicUser(int userId) throws Exception;

	public int CountPublicUserByWharf(PublicUser publicUser, String qcs)
			throws Exception;

	public List<?> ShowPublicUserPageByWharf(PublicUser publicUser, String qcs,
			String sequence, int beginIndex, int pagesize) throws Exception;

	public int CountPublicUserByShip(PublicUser publicUser, String qcs)
			throws Exception;

	public List<?> ShowPublicUserPageByShip(PublicUser publicUser, String qcs,
			String sequence, int beginIndex, int pagesize) throws Exception;

	public List<ShipBinding> FindPublicUserByShip(int userId) throws Exception;

	public List<WharfBinding> FindPublicUserByWharf(int userId) throws Exception;
	
	public String DeleteWharfBinding(WharfBinding wb) throws Exception;
	
	public String DeleteShipBinding(ShipBinding sb) throws Exception;
	
	public List<?> ShipCheckList() throws Exception;
	
	public List<?> WharfCheckList() throws Exception;
	
	//增加客户表
	public String AddPublicTable(PublicUser publicUser) throws Exception;
	
	//增加船舶绑定表
	public String AddShipBinding(ShipBinding shipBinding) throws Exception;
	//增加码头绑定表
	public String AddWharfBinding(WharfBinding whartbinding) throws Exception;
	
	//新增船户签证
	public String AddShipCheckTable(ShipCheck shipBook) throws Exception;
	//新增码头组签证
	public String AddWharfCheckTable(WharfCheck wharfBook) throws Exception;
	
	//删除船户签证
	public String DeleteShipCheckTable(ShipCheck shipBook) throws Exception;
	//删除码头组签证
	public String DeleteWharfCheckTable(WharfCheck wharfBook) throws Exception;
	//修改船户签证
	public List<?> PublicInfoByShip(PublicUser publicUser) throws Exception;
	//修改码头组签证
	public List<?> PublicInfoByWharf(PublicUser publicUser) throws Exception;
	//修改公众用户
	public String UpdatePublicInfo(PublicUser publicUser) throws Exception;
	//修改船舶
	public String UpdateShipBinding(ShipBinding shipBinding) throws Exception;
	//修改码头
	public String UpdateWharfBinding(WharfBinding wharfBinding) throws Exception;
	
	public List<PublicUser> FindPublicUserByName(String userName) throws Exception;
	public List<PublicUser> FindPublicUserByStatus(String  st) throws Exception;
	
	public List<ShipBinding> FindPublicUserByShipName(String shipNum,int shipId) throws Exception;
	
	public List<WharfBinding> FindPublicUserByWharfName(String wharfNum) throws Exception;
	//修改密码
	public String CheckPsdById(PublicUser publicUser)throws Exception;
	//公众信息
	public List<PublicUser> PublicUserInfoById(PublicUser publicUser) throws Exception;
	//登陆
	public String LoginToSaveTime(PublicUser publicUser);
	
	public List<?> publicUserById(PublicUser publicUser) throws Exception;
	public List<?> publicUserByIdWharf(PublicUser publicUser) throws Exception;
	//修改bindName
	public String UpdateBindName(PublicUser publicUser)throws Exception;
	
	public List<?> publicUserId(int id) throws Exception;
	
	//注册，审核
	public int AddPublicUser(PublicUser publicUser);
	
	public int AddShipBind(ShipBinding shipBinding);
	
	public int AddShipFile(ShipFile sf);
	
}
