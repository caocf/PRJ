package com.huzhouport.publicuser.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.log.service.LogsaveServer;
import com.huzhouport.publicuser.model.PublicUser;
import com.huzhouport.publicuser.model.ShipBinding;
import com.huzhouport.publicuser.model.ShipCheck;
import com.huzhouport.publicuser.model.ShipFile;
import com.huzhouport.publicuser.model.WharfBinding;
import com.huzhouport.publicuser.model.WharfCheck;
import com.huzhouport.publicuser.model.WharfFile;
import com.huzhouport.publicuser.service.PublicUserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class PublicUserAction extends BaseAction implements
		ModelDriven<PublicUser> {
	
	private File file;
	private String fname;
	private static final long serialVersionUID = 1L;
	private PublicUser publicUser = new PublicUser();
	private ShipBinding shipBinding = new ShipBinding();
	private WharfBinding wharfBinding = new WharfBinding();
	private ShipCheck shipBook = new ShipCheck();
	private WharfCheck wharfBook = new WharfCheck();
	private ShipFile shipFile = new ShipFile();
	private WharfFile wharfFile = new WharfFile();
	private PublicUserService publicUserService;
	private int totalPage;// 总页数
	private int allTotal;// 显示总条数
	private List<?> list;
	private int t;
	private String state;
	private int result;

	private LogsaveServer logsaveserver;
	
	private List<ShipBinding> shipBindingList = new ArrayList<ShipBinding>();
	private List<WharfBinding> wharfBindingList = new ArrayList<WharfBinding>();

	public void setLogsaveserver(LogsaveServer logsaveserver) {
		this.logsaveserver = logsaveserver;
	}

	public WharfFile getWharfFile() {
		return wharfFile;
	}

	public void setWharfFile(WharfFile wharfFile) {
		this.wharfFile = wharfFile;
	}
	
	public void setFname(String fname)
	{
		this.fname=fname;
	}
	
	public String getFname()
	{
		return fname;
	}
	
	public void setResult(int  result)
	{
		this.result=result;
	}
	
	public int getResult()
	{
		return result;
	}
	
	public void setFile(File file)
	{
		this.file=file;
	}
	
	public File geFile()
	{
		return file;
	}

	public void setT(int t)
	{
		this.t=t;
	}
	
	public int getT()
	{
		return t;
	}
	
	public void setState(String state)
	{
		this.state=state;
	}
	
	public String getState()
	{
		return state;
	}
	
	public PublicUser getModel() {
		// TODO Auto-generated method stub
		return publicUser;
	}

	public PublicUser getPublicUser() {
		return publicUser;
	}

	public void setPublicUser(PublicUser publicUser) {
		this.publicUser = publicUser;
	}

	public ShipFile getShipFile() {
		return shipFile;
	}

	public void setShipFile(ShipFile shipFile) {
		this.shipFile = shipFile;
	}

	public void setPublicUserService(PublicUserService publicUserService) {
		this.publicUserService = publicUserService;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getAllTotal() {
		return allTotal;
	}

	public void setAllTotal(int allTotal) {
		this.allTotal = allTotal;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public ShipBinding getShipBinding() {
		return shipBinding;
	}

	public void setShipBinding(ShipBinding shipBinding) {
		this.shipBinding = shipBinding;
	}

	public WharfBinding getWharfBinding() {
		return wharfBinding;
	}

	public void setWharfBinding(WharfBinding wharfBinding) {
		this.wharfBinding = wharfBinding;
	}

	public ShipCheck getShipBook() {
		return shipBook;
	}

	public void setShipBook(ShipCheck shipBook) {
		this.shipBook = shipBook;
	}

	public WharfCheck getWharfBook() {
		return wharfBook;
	}

	public void setWharfBook(WharfCheck wharfBook) {
		this.wharfBook = wharfBook;
	}

	// 码头列表或搜索

	public List<ShipBinding> getShipBindingList() {
		return shipBindingList;
	}

	public void setShipBindingList(List<ShipBinding> shipBindingList) {
		this.shipBindingList = shipBindingList;
	}

	public List<WharfBinding> getWharfBindingList() {
		return wharfBindingList;
	}

	public void setWharfBindingList(List<WharfBinding> wharfBindingList) {
		this.wharfBindingList = wharfBindingList;
	}

	public String PublicUserListByWharf() throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map = this.publicUserService.CountPublicUserByWharf(publicUser,
				GlobalVar.PAGESIZE);
		totalPage = map.get("pages");
		allTotal = map.get("allTotal");
		if (totalPage != 0) {
			list = this.publicUserService.ShowPublicUserPageByWharf(publicUser,
					Integer.valueOf(this.getCpage()), GlobalVar.PAGESIZE);

		}
		return SUCCESS;
	}

	// 船户用户列表或搜索
	public String PublicUserListByShip() throws Exception 
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map = this.publicUserService.CountPublicUserByShip(publicUser,
				GlobalVar.PAGESIZE);
		totalPage = map.get("pages");
		allTotal = map.get("allTotal");
		if (totalPage != 0) {
			list = this.publicUserService.ShowPublicUserPageByShip(publicUser,
					Integer.valueOf(this.getCpage()), GlobalVar.PAGESIZE);

		}
		return SUCCESS;
	}

	// 删除公众用户
	public String DeletePublicUser() throws Exception {
		try {
			this.publicUserService.DeletePublicUser(publicUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 删除公众用户
	public String DeleteWharfUser() throws Exception {
		try {
			this.publicUserService.DeleteWharfUser(publicUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String ShipCheckList() throws Exception {
		list = this.publicUserService.ShipCheckList();
		return SUCCESS;
	}

	public String WharfCheckList() throws Exception {
		list = this.publicUserService.WharfCheckList();
		return SUCCESS;
	}

	// 新增船户
	public String AddPublicByShip() throws Exception {
		try{
		this.publicUserService.AddPublicByShip(publicUser, shipBinding,
				shipFile);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 新增码头组
	public String AddPublicByWharf() throws Exception {
		try{
		this.publicUserService.AddPublicByWharf(publicUser, wharfBinding,
				wharfFile);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 新增船户签证
	public String AddShipBook() throws Exception {
		this.publicUserService.AddShipBook(shipBook);
		return SUCCESS;
	}

	// 新增码头组签证
	public String AddWharfBook() throws Exception {
		this.publicUserService.AddWharfBook(wharfBook);
		return SUCCESS;
	}

	// 删除船户签证
	public String DeleteShipBook() throws Exception {
		this.publicUserService.DeleteShipBook(shipBook);
		return SUCCESS;
	}

	// 删除码头组签证
	public String DeleteWharfBook() throws Exception {
		this.publicUserService.DeleteWharfBook(wharfBook);
		return SUCCESS;
	}

	// 船户
	public String PublicInfoByShip() throws Exception {
		list = this.publicUserService.PublicInfoByShip(publicUser);
		return SUCCESS;
	}

	// 码头组
	public String PublicInfoByWharf() throws Exception {
		list = this.publicUserService.PublicInfoByWharf(publicUser);
		return SUCCESS;
	}

	// 修改船户签证
	public String UpdatePublicInfoByShip() throws Exception {
		this.publicUserService.UpdatePublicInfoByShip(publicUser, shipBinding);
		return SUCCESS;
	}

	// 修改码头组签证
	public String UpdatePublicInfoByWharf() throws Exception {
		this.publicUserService
				.UpdatePublicInfoByWharf(publicUser, wharfBinding);
		return SUCCESS;
	}

	public String LoginPublicMobi() {
		try {
			List<PublicUser> puList = this.publicUserService
					.FindPublicUserByName(publicUser.getUserName());
			if (puList.size() == 0) {
				allTotal = 2;// 用户名不存在
				return SUCCESS;
			}
			if (!puList.get(0).getPsd().equals(publicUser.getPsd())) {
				allTotal = 3;// 密码错误
				return SUCCESS;
			}
			// 测试是否是测试账号 Imei==-1
			if(puList.get(0).getImei() != null){
				if (puList.get(0).getImei().equals("-1")) {
					allTotal = 1;// 密码错误
					publicUser = puList.get(0);
					List<ShipBinding> sbList = this.publicUserService
							.FindPublicUserByShip(puList.get(0).getUserId());
					if (sbList.size() == 0) {
						List<WharfBinding> wbList = this.publicUserService
								.FindPublicUserByWharf(puList.get(0).getUserId());
						wharfBindingList = wbList;
					} else {
						shipBindingList = sbList;
					}
					logsaveserver.logsave(publicUser.getUserName(), "登录公众app系统",
							GlobalVar.LOGLOGIN, GlobalVar.LOGAPP,"");
					return SUCCESS;
				}
			}
			if (puList.get(0).getImei() == null
					|| puList.get(0).getImei().equals("")) {
				puList.get(0).setImei(publicUser.getImei());
				puList.get(0).setIccid(publicUser.getIccid());
				this.publicUserService.LoginToSaveTime(puList.get(0));
				allTotal = 1;
				publicUser = puList.get(0);
				List<ShipBinding> sbList = this.publicUserService
						.FindPublicUserByShip(puList.get(0).getUserId());
				if (sbList.size() == 0) {
					List<WharfBinding> wbList = this.publicUserService
							.FindPublicUserByWharf(puList.get(0).getUserId());
					wharfBindingList = wbList;
				} else {
					shipBindingList = sbList;
				}

				logsaveserver.logsave(publicUser.getUserName(), "登录公众app系统",
						GlobalVar.LOGLOGIN, GlobalVar.LOGAPP,"");

				return SUCCESS;
			} else {
				if (puList.get(0).getImei().equals(publicUser.getImei())
						|| puList.get(0).getIccid().equals(
								publicUser.getIccid())) {
					this.publicUserService.LoginToSaveTime(puList.get(0));
					allTotal = 1;
					publicUser = puList.get(0);
					List<ShipBinding> sbList = this.publicUserService
							.FindPublicUserByShip(puList.get(0).getUserId());
					if (sbList.size() == 0) {
						List<WharfBinding> wbList = this.publicUserService
								.FindPublicUserByWharf(puList.get(0)
										.getUserId());
						wharfBindingList = wbList;
					} else {
						shipBindingList = sbList;
					}
					logsaveserver.logsave(publicUser.getUserName(),
							"登录公众app系统", GlobalVar.LOGLOGIN, GlobalVar.LOGAPP,"");
				} else {
					allTotal = 5;// imei或iccid号不正确
					return SUCCESS;
				}
			}

		} catch (Exception e) {
			allTotal = 0;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String ChangePsdPublicMobi() {

		try {
			List<PublicUser> puList = this.publicUserService
					.PublicUserInfoById(publicUser);
			if (puList.size() < 1) {
				allTotal = 3;
				return SUCCESS;
			}
			if (!puList.get(0).getPsd().equals(publicUser.getUserName())) {
				allTotal = 2;
				return SUCCESS;
			}
			String message = this.publicUserService.CheckPsdById(publicUser);
			if (message.equals("false")) {
				allTotal = 3;
				return SUCCESS;
			} else {
				logsaveserver.logsave(publicUser.getUserName(), "修改公众app密码",
						GlobalVar.LOGUPDATE, GlobalVar.LOGAPP,"");
				allTotal = 1;
				return SUCCESS;
			}
		} catch (Exception e) {
			allTotal = 3;
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public String publicUserById() throws Exception {
		list = publicUserService.publicUserById(publicUser);
		return SUCCESS;
	}

	public String publicUserByIdWharf() throws Exception {
		list = publicUserService.publicUserByIdWharf(publicUser);
		return SUCCESS;
	}

	/**
	 * 删除绑定的船号（主键）
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteShipBindingById() throws Exception {
		publicUserService.deleteShipBindingById(shipBinding);
		return SUCCESS;
	}

	/**
	 * 删除文件（主键）
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteShipFileById() throws Exception {
		publicUserService.deleteShipFileById(shipFile);
		return SUCCESS;
	}

	/**
	 * 删除绑定的码头（主键）
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteWharfBindingById() throws Exception {
		publicUserService.deleteWharfBindingById(wharfBinding);
		return SUCCESS;
	}

	/**
	 * 删除文件（主键）
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteWharfFileById() throws Exception {
		publicUserService.deleteWharfFileById(wharfFile);
		return SUCCESS;
	}
	
	
	
	//船户登陆
	public String LoginPcByPublic() throws Exception {
		try {
			List<PublicUser> puList = this.publicUserService
					.FindPublicUserByName(publicUser.getUserName());
			if (puList.size() == 0) {
				allTotal = 2;// 用户名不存在
				return SUCCESS;
			}
			if (!puList.get(0).getPsd().equals(publicUser.getPsd())) {
				allTotal = 3;// 密码错误
				return SUCCESS;
			}
			String a, b;
			a = this.publicUser.getImei().toLowerCase();
			b = (String) session.get("validateString");
			if (!a.equals(b.toLowerCase())) {
				allTotal = 4;// 验证码不正确!;
				return SUCCESS;
			}
			this.publicUserService.LoginToSaveTime(puList.get(0));

			publicUser = puList.get(0);
			List<ShipBinding> sbList = this.publicUserService
					.FindPublicUserByShip(puList.get(0).getUserId());
			if (sbList.size() == 0) {
				allTotal = 5;// 用户没有绑定的船舶!;
				return SUCCESS;
			} else {
				allTotal = 1;
				if(publicUser.getBindName().equals("null")||publicUser.getBindName()==null){
					publicUser.setBindName(sbList.get(0).getShipNum());
				}
				Map session = ActionContext.getContext().getSession(); // 获得session
				session.put("pUserName", publicUser.getUserName());
				session.put("pUserId", publicUser.getUserId());
				session.put("shipName", publicUser.getBindName());
				logsaveserver.logsave(publicUser.getUserName(), "登录网站",
						GlobalVar.LOGLOGIN, GlobalVar.LOGPC,"");
				return SUCCESS;
			}
		} catch (Exception e) {
			allTotal = 0;
			e.printStackTrace();
		}

		return SUCCESS;

	}
	public String SelctBoatManForReportByUser(){

		try {
			List<ShipBinding> sbList = this.publicUserService
					.FindPublicUserByShip(publicUser.getUserId());
			if (sbList.size() == 0) {
				allTotal = 5;// 用户没有绑定的船舶!;
				return SUCCESS;
			} else {
				allTotal = 1;
				Map session = ActionContext.getContext().getSession(); // 获得session
				session.put("pUserName", publicUser.getUserName());
				session.put("pUserId", publicUser.getUserId());
				session.put("shipName", sbList.get(0).getShipNum());
				return SUCCESS;
			}
		} catch (Exception e) {
			allTotal = 0;
			e.printStackTrace();
		}

		return SUCCESS;

	
	}
	
	
	public String DoReg() throws IOException 
	{
		
		System.out.println(this.getFname());			
	        
	    //查询user信息
	    PublicUser pu=null;
	    try 
	    {
			pu=publicUserService.findRegList(publicUser.getUserName()).get(0);
		} 
	    catch (Exception e) 
		{
			
			this.setResult(1);
			
		}
	    if(pu!=null)//用户已存在
	    {
	    	this.setResult(0);
	    	return SUCCESS;
	    }
	    
	    InputStream is;
		is = new FileInputStream(file);
		String path="image/common";
		String root = ServletActionContext.getServletContext().getRealPath(path);
		OutputStream os = new FileOutputStream(new File(root, fname));
		byte[] buffer = new byte[500];		
		while(-1 !=is.read(buffer, 0, buffer.length))
	    {
	       os.write(buffer);
	    }
		os.close();
		is.close();	
		
		int id=publicUserService.addUser(publicUser);
		
		ShipBinding sb=new ShipBinding();
		sb.setShipNum(publicUser.getBindName());
		sb.setShipUser(id);
		sb.setStatus("0");	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		sb.setBindingTime(df.format(new Date()));
		int shipid=publicUserService.addShipBinding(sb);
		
		ShipFile sf=new ShipFile();
		sf.setShipId(shipid);
		sf.setFileName(fname);
		sf.setFilePath(path+"/"+fname);
		publicUserService.addShipFile(sf);		
		
		return SUCCESS;
	}
	
	public String RegisterList() throws IOException
	{		
		/*try {
			list=publicUserService.findRegList("0");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(list.size());*/
		
		return SUCCESS;
	}
	
	public String regid()//t
	{
		/*try {
			list=publicUserService.publicUserId(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return SUCCESS;
	}
	
	public String Audit()//t//state
	{
		
		ShipBinding user3=null;
		try {
			user3=(ShipBinding) publicUserService.publicUserId(t).get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(user3!=null)
		user3.setStatus(state);
		
		publicUserService.updatep(user3);
		
		return SUCCESS;
	}
	
	
	//获取公众用户的绑定船舶
	public String FindPublicUserByShip(){
		try {
			if(session.get("pUserId")!=null){
				int userid=Integer.parseInt(session.get("pUserId").toString());
				list= this.publicUserService.FindPublicUserByShip(userid);
			}
			else{
				return ERROR;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
		}
	//修改bindName
	public String UpdateBindNameBySession(){
		try {
			if(session.get("shipName")!=null){
				int userid=Integer.parseInt(session.get("pUserId").toString());
				publicUser.setUserId(userid);
				this.publicUserService.UpdateBindName(publicUser);
				session.put("shipName", publicUser.getBindName());
			}
			else{
				return ERROR;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
		}
	//修改bindName
	public String UpdateBindName() {
		try {
			this.publicUserService.UpdateBindName(publicUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
