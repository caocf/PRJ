package com.huzhouport.publicuser.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.QueryConditionSentence;
import com.huzhouport.publicuser.dao.PublicUserDao;
import com.huzhouport.publicuser.model.PublicUser;
import com.huzhouport.publicuser.model.ShipBinding;
import com.huzhouport.publicuser.model.ShipCheck;
import com.huzhouport.publicuser.model.ShipFile;
import com.huzhouport.publicuser.model.WharfBinding;
import com.huzhouport.publicuser.model.WharfCheck;
import com.huzhouport.publicuser.model.WharfFile;
import com.huzhouport.publicuser.service.PublicUserService;

public class PublicUserServiceImpl extends BaseServiceImpl<PublicUser>
		implements PublicUserService {
	private PublicUserDao publicUserDao;
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件

	public void setPublicUserDao(PublicUserDao publicUserDao) {
		this.publicUserDao = publicUserDao;
	}

	public void setQcs(QueryConditionSentence qcs) {
		this.qcs = qcs;
	}

	@SuppressWarnings("unchecked")
	public String DeletePublicUser(PublicUser publicUser) throws Exception {
		String[] userId = publicUser.getUserList().split(",");
		ShipBinding shipBinding = new ShipBinding();
		ShipFile shipFile = new ShipFile();
		for (int i = 0; i < userId.length; i++) {
			int intId = Integer.parseInt(userId[i].trim());
			List<ShipBinding> sbLists = (List<ShipBinding>) this.publicUserDao
					.queryDataByConditionsObject(shipBinding, "shipUser",
							intId);
			for (int l = 0; l < sbLists.size(); l++) {
				shipBinding = sbLists.get(l);
				List<ShipFile> splists = (List<ShipFile>) this.publicUserDao
						.queryDataByConditionsObject(shipFile, "shipId",
								shipBinding.getShipId());
				for (int f = 0; f < splists.size(); f++) {
					shipFile = splists.get(f);
					String root = ServletActionContext.getServletContext()
							.getRealPath(GlobalVar.FilePATH)
							+ "/" + splists.get(0).getFilePath();
					File file = new File(root);
					if (file.exists()) {
						file.delete();
					}
					this.publicUserDao.deleteObject(shipFile);
				}
				this.publicUserDao.deleteObject(shipBinding);
			}

			this.publicUserDao.DeletePublicUser(intId);
		}

		return null;
	}

	// 码头
	public Map<String, Integer> CountPublicUserByWharf(PublicUser publicUser,
			int pagesize) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int total = this.publicUserDao.CountPublicUserByWharf(publicUser, qcs
				.QCS(publicUser.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pagesize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}

	public List<?> ShowPublicUserPageByWharf(PublicUser publicUser,
			Integer pageNo, int pagesize) throws Exception {
		int beginIndex = (pageNo - 1) * pagesize;
		return this.publicUserDao.ShowPublicUserPageByWharf(publicUser, qcs
				.QCS(publicUser.getQueryCondition()), qcs.Sequence(publicUser
				.getQueryCondition()), beginIndex, pagesize);
	}

	// 船户
	public Map<String, Integer> CountPublicUserByShip(PublicUser publicUser,
			int pagesize) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int total = this.publicUserDao.CountPublicUserByShip(publicUser, qcs
				.QCS(publicUser.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pagesize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}

	public List<?> ShowPublicUserPageByShip(PublicUser publicUser,
			Integer pageNo, int pagesize) throws Exception {
		int beginIndex = (pageNo - 1) * pagesize;
		return this.publicUserDao.ShowPublicUserPageByShip(publicUser, qcs
				.QCS(publicUser.getQueryCondition()), qcs.Sequence(publicUser
				.getQueryCondition()), beginIndex, pagesize);
	}

	public List<?> ShipCheckList() throws Exception {
		return this.publicUserDao.ShipCheckList();
	}

	public List<?> WharfCheckList() throws Exception {
		return this.publicUserDao.WharfCheckList();
	}

	// 新增船户
	public String AddPublicByShip(PublicUser publicUser,
			ShipBinding shipBinding, ShipFile shipFile) throws Exception {
		List<PublicUser> puList = this.publicUserDao
				.FindPublicUserByName(publicUser.getUserName());
		if (puList.size() > 0) {
			throw new Exception("用户名不能重复！");
		}
		// 先增加客户表
		publicUser.setPhoneNumber(publicUser.getUserName());
		if(publicUser.getLists().size()>0)
			publicUser.setBindName(publicUser.getLists().get(0).getShipNum());
		String Message = this.publicUserDao.AddPublicTable(publicUser);
		if (Message.equals("false")) {
			throw new Exception("发生错误！请刷新重试");
		}

		// 增加绑定表
		List<ShipBinding> shipList = publicUser.getLists();
		for (int i = 0; i < shipList.size(); i++) {
			ShipBinding shipb = new ShipBinding();
			if (null == shipList.get(i)) {
				continue;
			}
			shipb = shipList.get(i);
			for (int j = i + 1; j < shipList.size(); j++) {
				if (shipb.getShipNum().equals(shipList.get(j).getShipNum())) {
					throw new Exception("该船名已被绑定！请输入其他船舶！");
				}
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			shipb.setBindingTime(df.format(new Date()));
			shipb.setShipUser(publicUser.getUserId());

			Message = this.publicUserDao.AddShipBinding(shipb);
			if (Message.equals("false")) {
				this.publicUserDao.DeletePublicUser(publicUser.getUserId());
				throw new Exception("发生错误！请刷新重试");
			}
			List<ShipFile> ls = shipb.getListf();
			for (int j = 0; j < ls.size(); j++) {
				ShipFile sf = new ShipFile();
				if (null == ls.get(j)) {
					continue;
				}
				sf = ls.get(j);
				if (null == sf.getFileName() || sf.getFileName().equals("")) {
					continue;
				}
				// 增加附件
				sf.setShipId(shipb.getShipId());
				addEvendice(sf);
			}
		}
		return null;

	}

	// 新增码头组
	public String AddPublicByWharf(PublicUser publicUser,
			WharfBinding wharfBinding, WharfFile wharfFile) throws Exception {
		List<PublicUser> puList = this.publicUserDao
				.FindPublicUserByName(publicUser.getUserName());
		if (puList.size() > 0) {
			throw new Exception("用户名不能重复！");
		}
		// 先增加客户表
		publicUser.setPhoneNumber(publicUser.getUserName());
		if(publicUser.getListw().size()>0)
			publicUser.setBindName(publicUser.getListw().get(0).getWharfNum());
		String Message = this.publicUserDao.AddPublicTable(publicUser);
		if (Message.equals("false")) {
			throw new Exception("发生错误！请刷新重试！");
		}
		// 增加绑定表
		List<WharfBinding> wharfList = publicUser.getListw();
		for (int i = 0; i < wharfList.size(); i++) {
			WharfBinding wharfpb = new WharfBinding();
			if (null == wharfList.get(i)) {
				continue;
			}
			wharfpb = wharfList.get(i);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			wharfpb.setBindingTime(df.format(new Date()));
			wharfpb.setWharfUser(publicUser.getUserId());

			Message = this.publicUserDao.AddWharfBinding(wharfpb);
			if (Message.equals("false")) {
				this.publicUserDao.DeletePublicUser(publicUser.getUserId());
				throw new Exception("发生错误！请刷新重试");
			}
			List<WharfFile> ls = wharfpb.getListf();
			for (int j = 0; j < ls.size(); j++) {
				WharfFile sf = new WharfFile();
				if (null == ls.get(j)) {
					continue;
				}
				sf = ls.get(j);
				if (null == sf.getFileName() || sf.getFileName().equals("")) {
					continue;
				}
				// 增加附件
				sf.setWharfId(wharfpb.getWharfId());
				addEvendiceW(sf);
			}
		}
		return null;

	}

	// 新增船户签证
	public String AddShipBook(ShipCheck shipBook) throws Exception {
		String Message = this.publicUserDao.AddShipCheckTable(shipBook);
		if (Message.equals("false")) {
			throw new Exception("该证书已存在！");
		}
		return null;
	}

	// 新增码头组签证
	public String AddWharfBook(WharfCheck wharfBook) throws Exception {
		String Message = this.publicUserDao.AddWharfCheckTable(wharfBook);
		if (Message.equals("false")) {
			throw new Exception("该证书已存在！");
		}
		return null;
	}

	// 删除船户签证
	public String DeleteShipBook(ShipCheck shipBook) throws Exception {
		String Message = this.publicUserDao.DeleteShipCheckTable(shipBook);
		if (Message.equals("false")) {
			throw new Exception("删除失败！");
		}
		return null;

	}

	// 删除码头组签证
	public String DeleteWharfBook(WharfCheck wharfBook) throws Exception {

		String Message = this.publicUserDao.DeleteWharfCheckTable(wharfBook);
		if (Message.equals("false")) {
			throw new Exception("删除失败！");
		}
		return null;

	}

	// 船户信息
	public List<?> PublicInfoByShip(PublicUser publicUser) throws Exception {
		return this.publicUserDao.PublicInfoByShip(publicUser);
	}

	// 码头组信息
	public List<?> PublicInfoByWharf(PublicUser publicUser) throws Exception {
		return this.publicUserDao.PublicInfoByWharf(publicUser);
	}

	// 修改船户签证
	public List<?> UpdatePublicInfoByShip(PublicUser publicUser,
			ShipBinding shipB) throws Exception {
		List<PublicUser> puList = this.publicUserDao
				.FindPublicUserByName(publicUser.getUserName());
		if (puList.size() > 0) {
			if (puList.get(0).getUserId() != publicUser.getUserId()) {
				throw new Exception("用户名不能重复！");
			}
			publicUser.setIccid(puList.get(0).getIccid());
			publicUser.setImei(puList.get(0).getImei());
			
			List<ShipBinding> shipList = publicUser.getLists();
			if(shipList.size()>0){
				int a=0;
				for (int i = 0; i < shipList.size(); i++) {
					if(shipList.get(i).getShipNum().equals(puList.get(0).getBindName()))
					{
						publicUser.setBindName(puList.get(0).getBindName());
						a=1;
						break;
					}
				}
				if(a==0)
					publicUser.setBindName(shipList.get(0).getShipNum());
			}
		}
		publicUser.setPhoneNumber(publicUser.getUserName());
		// 先修改客户表
		String Message = this.publicUserDao.UpdatePublicInfo(publicUser);
		if (Message.equals("false")) {
			throw new Exception("发生错误！请刷新重试！");
		}
		// 增加绑定表
		List<ShipBinding> shipList = publicUser.getLists();
		for (int i = 0; i < shipList.size(); i++) {
			ShipBinding shipb = new ShipBinding();
			if (null == shipList.get(i)) {
				continue;
			}
			shipb = shipList.get(i);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			shipb.setBindingTime(df.format(new Date()));
			shipb.setShipUser(publicUser.getUserId());
			// List<ShipBinding> shipListByN = this.publicUserDao
			// .FindPublicUserByShipName(shipb.getShipNum(),publicUser.getUserId());
			for (int j = i + 1; j < shipList.size(); j++) {
				if (shipb.getShipNum().equals(shipList.get(j).getShipNum())) {
					throw new Exception("该船名已被绑定！请输入其他船舶！");
				}
			}
			if (shipb.getShipId() != 0) {
				// if (shipListByN.size() > 0
				// && shipListByN.get(0).getShipId() != shipb.getShipId()) {
				// throw new Exception("该船名已被绑定！请输入其他船舶！");
				// } else {
				Message = publicUserDao.UpdateShipBinding(shipb);
				if (Message.equals("false")) {
					throw new Exception("发生错误！请刷新重试");
				}
				// }
				// } else if (shipListByN.size() > 0 && shipb.getShipId() == 0)
				// {
				// throw new Exception("该船名已被绑定！请输入其他船舶！");
			} else {
				Message = this.publicUserDao.AddShipBinding(shipb);
				if (Message.equals("false")) {
					throw new Exception("发生错误！请刷新重试");
				}
			}

			List<ShipFile> ls = shipb.getListf();
			for (int j = 0; j < ls.size(); j++) {
				ShipFile sf = new ShipFile();
				if (null == ls.get(j)) {
					continue;
				}
				sf = ls.get(j);
				if (null == sf.getFileName() || sf.getFileName().equals("")) {
					continue;
				}
				// 增加附件
				sf.setShipId(shipb.getShipId());
				if (sf.getFileId() == 0) {
					addEvendice(sf);
				}
			}
		}

		return null;
	}

	// 修改码头组签证
	public List<?> UpdatePublicInfoByWharf(PublicUser publicUser,
			WharfBinding wharfBinding) throws Exception {
		List<PublicUser> puList = this.publicUserDao
				.FindPublicUserByName(publicUser.getUserName());
		if (puList.size() > 0) {
			if (puList.get(0).getUserId() != publicUser.getUserId()) {
				throw new Exception("用户名不能重复！");
			}
			publicUser.setIccid(puList.get(0).getIccid());
			publicUser.setImei(puList.get(0).getImei());
			
			List<WharfBinding> wharfList = publicUser.getListw();
			if(wharfList.size()>0){
				int a=0;
				for (int i = 0; i < wharfList.size(); i++) {
					if(wharfList.get(i).getWharfNum().equals(puList.get(0).getBindName()))
					{
						publicUser.setBindName(puList.get(0).getBindName());
						a=1;
						break;
					}
				}
				if(a==0)
					publicUser.setBindName(wharfList.get(0).getWharfNum());
			}
		}
		publicUser.setPhoneNumber(publicUser.getUserName());
		// 先修改客户表
		String Message = this.publicUserDao.UpdatePublicInfo(publicUser);
		if (Message.equals("false")) {
			throw new Exception("发生错误！请刷新重试！");
		}
		// 增加绑定表
		List<WharfBinding> wharfList = publicUser.getListw();
		for (int i = 0; i < wharfList.size(); i++) {
			WharfBinding wharfpb = new WharfBinding();
			if (null == wharfList.get(i)) {
				continue;
			}
			wharfpb = wharfList.get(i);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			wharfpb.setBindingTime(df.format(new Date()));
			wharfpb.setWharfUser(publicUser.getUserId());
			List<WharfBinding> wharfListByN = this.publicUserDao
					.FindPublicUserByWharfName(wharfpb.getWharfNum());
			if (wharfpb.getWharfId() != 0) {
				if (wharfListByN.size() > 0
						&& wharfListByN.get(0).getWharfId() != wharfpb
								.getWharfId()) {
					throw new Exception("该码头已被绑定！请输入其他码头！");
				} else {
					publicUserDao.UpdateWharfBinding(wharfpb);
				}
			} else if (wharfListByN.size() > 0 && wharfpb.getWharfId() == 0) {
				throw new Exception("该码头已被绑定！请输入其他码头！");
			} else {
				Message = this.publicUserDao.AddWharfBinding(wharfpb);
				if (Message.equals("false")) {
					throw new Exception("发生错误！请刷新重试");
				}
			}

			List<WharfFile> ls = wharfpb.getListf();
			for (int j = 0; j < ls.size(); j++) {
				WharfFile sf = new WharfFile();
				if (null == ls.get(j)) {
					continue;
				}
				sf = ls.get(j);
				if (null == sf.getFileName() || sf.getFileName().equals("")) {
					continue;
				}
				// 增加附件
				sf.setWharfId(wharfpb.getWharfId());
				if (sf.getFileId() == 0) {
					addEvendiceW(sf);
				}
			}
		}

		return null;
	}

	public List<PublicUser> FindPublicUserByName(String userName)
			throws Exception {
		return this.publicUserDao.FindPublicUserByName(userName);

	}

	public List<ShipBinding> FindPublicUserByShip(int userId) throws Exception {
		return this.publicUserDao.FindPublicUserByShip(userId);

	}

	public List<WharfBinding> FindPublicUserByWharf(int userId)
			throws Exception {
		return this.publicUserDao.FindPublicUserByWharf(userId);

	}

	// 修改密码
	public String CheckPsdById(PublicUser publicUser) throws Exception {
		return this.publicUserDao.CheckPsdById(publicUser);
	}

	// 公众信息
	public List<PublicUser> PublicUserInfoById(PublicUser publicUser)
			throws Exception {
		return this.publicUserDao.PublicUserInfoById(publicUser);
	}

	// 登陆
	public String LoginToSaveTime(PublicUser publicUser) throws Exception {
		//this.publicUserDao.update(publicUser);
		return this.publicUserDao.LoginToSaveTime(publicUser);
	}

	// 附件上传
	public void addEvendice(ShipFile shipFile) throws Exception {
		String uploadsName = shipFile.getFileName();
		if (shipFile.getFile() != null) {
			java.io.InputStream is = new java.io.FileInputStream(shipFile
					.getFile());
			int beginIndex = uploadsName.lastIndexOf('.');
			String stype = uploadsName.substring(beginIndex + 1);
			String sname = (new Date()).getTime() + "." + stype;
			String root = ServletActionContext.getServletContext().getRealPath(
					GlobalVar.FilePATH)
					+ "/" + sname;
			java.io.OutputStream os = new java.io.FileOutputStream(root);
			byte buffer[] = new byte[8192];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				os.write(buffer, 0, count);
			}
			os.close();
			is.close();
			shipFile.setFilePath(sname);
		}
		this.publicUserDao.savaObject(shipFile);

	}

	// 附件上传
	public void addEvendiceW(WharfFile wharfFile) throws Exception {
		String uploadsName = wharfFile.getFileName();
		if (wharfFile.getFile() != null) {
			java.io.InputStream is = new java.io.FileInputStream(wharfFile
					.getFile());
			int beginIndex = uploadsName.lastIndexOf('.');
			String stype = uploadsName.substring(beginIndex + 1);
			String sname = (new Date()).getTime() + "." + stype;
			String root = ServletActionContext.getServletContext().getRealPath(
					GlobalVar.FilePATH)
					+ "/" + sname;
			java.io.OutputStream os = new java.io.FileOutputStream(root);
			byte buffer[] = new byte[8192];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				os.write(buffer, 0, count);
			}
			os.close();
			is.close();
			wharfFile.setFilePath(sname);
		}
		this.publicUserDao.savaObject(wharfFile);

	}

	public List<?> publicUserById(PublicUser publicUser) throws Exception {

		return this.publicUserDao.publicUserById(publicUser);
	}

	public List<?> publicUserByIdWharf(PublicUser publicUser) throws Exception {

		return this.publicUserDao.publicUserByIdWharf(publicUser);
	}

	@SuppressWarnings("unchecked")
	public void deleteShipBindingById(ShipBinding shipBinding) throws Exception {
		ShipFile shipFile = new ShipFile();
		List<ShipFile> list = (List<ShipFile>) this.publicUserDao
				.queryDataByConditionsObject(shipFile, "shipId", shipBinding
						.getShipId());
		for (int i = 0; i < list.size(); i++) {
			shipFile = list.get(i);
			String root = ServletActionContext.getServletContext().getRealPath(
					GlobalVar.FilePATH)
					+ "/" + list.get(0).getFilePath();
			File file = new File(root);
			if (file.exists()) {
				boolean d = file.delete();
				if (d) {

				} else {

				}
			}
			this.publicUserDao.deleteObject(shipFile);
		}
		this.publicUserDao.deleteObject(shipBinding);

	}

	@SuppressWarnings("unchecked")
	public void deleteShipFileById(ShipFile shipFile) throws Exception {
		List<ShipFile> list = (List<ShipFile>) this.publicUserDao
				.queryDataByConditionsObject(shipFile, "fileId", shipFile
						.getFileId());
		if (list.size() != 0) {
			String root = ServletActionContext.getServletContext().getRealPath(
					GlobalVar.FilePATH)
					+ "/" + list.get(0).getFilePath();
			File file = new File(root);
			if (file.exists()) {
				boolean d = file.delete();
				if (d) {

				} else {

				}
			}
			this.publicUserDao.deleteObject(shipFile);
		}
	}

	@SuppressWarnings("unchecked")
	public void deleteWharfBindingById(WharfBinding wharfBinding)
			throws Exception {
		WharfFile wharfFile = new WharfFile();
		List<WharfFile> list = (List<WharfFile>) this.publicUserDao
				.queryDataByConditionsObject(wharfFile, "wharfId", wharfBinding
						.getWharfId());
		for (int i = 0; i < list.size(); i++) {
			wharfFile = list.get(i);
			String root = ServletActionContext.getServletContext().getRealPath(
					GlobalVar.FilePATH)
					+ "/" + list.get(0).getFilePath();
			File file = new File(root);
			if (file.exists()) {
				boolean d = file.delete();
				if (d) {

				} else {

				}
			}
			this.publicUserDao.deleteObject(wharfFile);
		}
		this.publicUserDao.deleteObject(wharfBinding);

	}

	@SuppressWarnings("unchecked")
	public void deleteWharfFileById(WharfFile wharfFile) throws Exception {
		List<WharfFile> list = (List<WharfFile>) this.publicUserDao
				.queryDataByConditionsObject(wharfFile, "fileId", wharfFile
						.getFileId());
		if (list.size() != 0) {
			String root = ServletActionContext.getServletContext().getRealPath(
					GlobalVar.FilePATH)
					+ "/" + list.get(0).getFilePath();
			File file = new File(root);
			if (file.exists()) {
				boolean d = file.delete();
				if (d) {

				} else {

				}
			}
			this.publicUserDao.deleteObject(wharfFile);
		}
	}

	@SuppressWarnings("unchecked")
	public String DeleteWharfUser(PublicUser publicUser) throws Exception {
		String[] userId = publicUser.getUserList().split(",");
		WharfBinding wharfBinding = new WharfBinding();
		WharfFile wharfFile = new WharfFile();
		for (int i = 0; i < userId.length; i++) {
			int intId = Integer.parseInt(userId[i].trim());
			List<WharfBinding> sbLists = (List<WharfBinding>) this.publicUserDao
					.queryDataByConditionsObject(wharfBinding, "wharfUser",
							intId);
			for (int l = 0; l < sbLists.size(); l++) {
				wharfBinding = sbLists.get(l);
				List<WharfFile> splists = (List<WharfFile>) this.publicUserDao
						.queryDataByConditionsObject(wharfFile, "wharfId",
								wharfBinding.getWharfId());
				for (int f = 0; f < splists.size(); f++) {
					wharfFile = splists.get(f);
					String root = ServletActionContext.getServletContext()
							.getRealPath(GlobalVar.FilePATH)
							+ "/" + splists.get(0).getFilePath();
					File file = new File(root);
					if (file.exists()) {
						file.delete();
					}
					this.publicUserDao.deleteObject(wharfFile);
				}
				this.publicUserDao.deleteObject(wharfBinding);
			}

			this.publicUserDao.DeletePublicUser(intId);
		}

		return null;
	}
	//修改bindName
	public String UpdateBindName(PublicUser publicUser)throws Exception{
		return this.publicUserDao.UpdateBindName(publicUser);
	}

	public String addPublicUser(PublicUser user)  
	{
		String s="";
		try {
			s=publicUserDao.AddPublicTable(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	public List<PublicUser> findRegList(String name) throws Exception 
	{
		
		return publicUserDao.FindPublicUserByStatus(name);
	}

	public List<?> publicUserId(int id) throws Exception {
		// TODO Auto-generated method stub
		return publicUserDao.publicUserId(id);
	}

	public String updatep(ShipBinding user)
	{
		
		try {
			return publicUserDao.UpdateShipBinding(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public int addUser(PublicUser user) 
	{
		// TODO Auto-generated method stub
		return publicUserDao.AddPublicUser(user);
	}

	public int addShipBinding(ShipBinding user) 
	{
		// TODO Auto-generated method stub
		return publicUserDao.AddShipBind(user);
	}

	public int addShipFile(ShipFile sf) 
	{
		// TODO Auto-generated method stub
		return publicUserDao.AddShipFile(sf);
	}
}
