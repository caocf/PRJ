package com.huzhouport.publicuser.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.publicuser.model.PublicUser;
import com.huzhouport.publicuser.model.ShipBinding;
import com.huzhouport.publicuser.model.ShipCheck;
import com.huzhouport.publicuser.model.ShipFile;
import com.huzhouport.publicuser.model.WharfBinding;
import com.huzhouport.publicuser.model.WharfCheck;
import com.huzhouport.publicuser.model.WharfFile;

public interface PublicUserService {
	
	public String DeletePublicUser(PublicUser publicUser) throws Exception;
	public String DeleteWharfUser(PublicUser publicUser) throws Exception;

	public Map<String, Integer> CountPublicUserByWharf(PublicUser publicUser,
			int pagesize)throws Exception;

	public List<?> ShowPublicUserPageByWharf(PublicUser publicUser, Integer valueOf,
			int pagesize)throws Exception;
	
	public Map<String, Integer> CountPublicUserByShip(PublicUser publicUser,
			int pagesize)throws Exception;

	public List<?> ShowPublicUserPageByShip(PublicUser publicUser, Integer valueOf,
			int pagesize)throws Exception;
	
	public List<?> ShipCheckList() throws Exception;
	
	public List<?> WharfCheckList() throws Exception;
	
	//新增船户
	public String AddPublicByShip(PublicUser publicUser,ShipBinding shipBinding,ShipFile shipFile) throws Exception;
	//新增码头组
	public String AddPublicByWharf(PublicUser publicUser,WharfBinding wharfBinding,WharfFile wharfFile) throws Exception;
	//新增船户签证
	public String AddShipBook(ShipCheck shipBook) throws Exception;
	//新增码头组签证
	public String AddWharfBook(WharfCheck wharfBook) throws Exception;
	//删除船户签证
	public String DeleteShipBook(ShipCheck shipBook) throws Exception;
	//删除码头组签证
	public String DeleteWharfBook(WharfCheck wharfBook) throws Exception;
	//船户
	public List<?> PublicInfoByShip(PublicUser publicUser) throws Exception;
	//码头组
	public List<?> PublicInfoByWharf(PublicUser publicUser) throws Exception;
	//修改船户签证
	public List<?> UpdatePublicInfoByShip(PublicUser publicUser,ShipBinding shipBinding) throws Exception;
	//修改码头组签证
	public List<?> UpdatePublicInfoByWharf(PublicUser publicUser,WharfBinding wharfBinding) throws Exception;
	
	public List<PublicUser> FindPublicUserByName(String userName)throws Exception;
	
	public List<ShipBinding> FindPublicUserByShip(int userId)throws Exception;
	
	public List<WharfBinding> FindPublicUserByWharf(int userId)throws Exception;
	//修改密码
	public String CheckPsdById(PublicUser publicUser)throws Exception;
	//公众信息
	public List<PublicUser> PublicUserInfoById(PublicUser publicUser) throws Exception;
	//登陆
	public String LoginToSaveTime(PublicUser publicUser)throws Exception;
	
	public List<?> publicUserById(PublicUser publicUser)throws Exception;
	public List<?> publicUserByIdWharf(PublicUser publicUser)throws Exception;
	
	/**
	 * 删除绑定的船号（主键）
	 * @return
	 */
	public void deleteShipBindingById(ShipBinding shipBinding)throws Exception;
	/**
	 * 删除文件（主键）
	 * @return
	 */
	public void deleteShipFileById(ShipFile shipFile) throws Exception;
	
	/**
	 * 删除绑定的码头（主键）
	 * @return
	 */
	public void deleteWharfBindingById(WharfBinding wharfBinding)throws Exception;
	/**
	 * 删除文件（主键）
	 * @return
	 */
	public void deleteWharfFileById(WharfFile wharfFile) throws Exception;
	
	//修改bindName
	public String UpdateBindName(PublicUser publicUser)throws Exception;
	
	//注册，审核
	
	public String addPublicUser(PublicUser user);
	
	public List<PublicUser> findRegList(String name) throws Exception;
	
	public List<?> publicUserId(int id) throws Exception ;
	
	public String updatep(ShipBinding user);
	
	public int addUser(PublicUser user);
	
	public int addShipBinding(ShipBinding user);
	
	public int addShipFile(ShipFile sf);
	
}
